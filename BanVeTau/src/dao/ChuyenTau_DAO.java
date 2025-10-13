package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import connectDB.ConnectDB;
import entity.ChuyenTau;
import entity.GaTau;
import entity.LoaiTau;
import entity.Tau;
import entity.TuyenDuong;

public class ChuyenTau_DAO {

    /**
     * Lấy tất cả các chuyến tàu, bao gồm thông tin chi tiết về Tàu và Tuyến Đường.
     * @return danh sách các đối tượng ChuyenTau
     * @throws SQLException 
     */
    public List<ChuyenTau> getAllChuyenTau() throws SQLException {
        List<ChuyenTau> dsChuyenTau = new ArrayList<>();
        Connection con = ConnectDB.getConnection();
        // Câu lệnh SQL JOIN nhiều bảng: ChuyenTau -> Tau, ChuyenTau -> TuyenDuong, TuyenDuong -> GaTau
        String sql = "SELECT ct.*, t.tenTau, t.maLoaiTau, td.*, gaDi.tenGaTau AS tenGaDi, gaDen.tenGaTau AS tenGaDen " +
                     "FROM ChuyenTau ct " +
                     "JOIN Tau t ON ct.maTau = t.maTau " +
                     "JOIN TuyenDuong td ON ct.maTuyenDuong = td.maTuyenDuong " +
                     "JOIN GaTau gaDi ON td.gaKhoiHanh = gaDi.maGaTau " +
                     "JOIN GaTau gaDen ON td.gaKetThuc = gaDen.maGaTau";
        
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                // 1. Xây dựng đối tượng Tau
                Tau tau = new Tau();
                tau.setMaTau(rs.getString("maTau"));
                tau.setTenTau(rs.getString("tenTau"));
                // Chuyển đổi maLoaiTau sang enum
                String maLoaiTauFromDB = rs.getString("maLoaiTau");
                if (maLoaiTauFromDB != null) {
                    tau.setLoaiTau(LoaiTau.valueOf(maLoaiTauFromDB)); // Cách chuyển đổi nhanh nếu tên enum khớp mã
                }
                
                // 2. Xây dựng các đối tượng GaTau
                GaTau gaDi = new GaTau(rs.getString("gaKhoiHanh"), rs.getString("tenGaDi"));
                GaTau gaDen = new GaTau(rs.getString("gaKetThuc"), rs.getString("tenGaDen"));	

                // 3. Xây dựng đối tượng TuyenDuong
                TuyenDuong tuyenDuong = new TuyenDuong();
                tuyenDuong.setMaTuyenDuong(rs.getString("maTuyenDuong"));
                tuyenDuong.setTenTuyenDuong(rs.getString("tenTuyenDuong"));
                tuyenDuong.setGaKhoiHanh(gaDi);
                tuyenDuong.setGaKetThuc(gaDen);

                // 4. Xây dựng đối tượng ChuyenTau
                ChuyenTau chuyenTau = new ChuyenTau();
                chuyenTau.setMaChuyenTau(rs.getString("maChuyenTau"));
                chuyenTau.setDonGiaCoBan(rs.getDouble("donGiaCoBan"));
                
                // Lấy Timestamp từ DB và chuyển đổi sang LocalDateTime
                Timestamp tsKhoiHanh = rs.getTimestamp("ngayGioKhoiHanh");
                if (tsKhoiHanh != null) {
                    chuyenTau.setNgayGioKhoiHanh(tsKhoiHanh.toLocalDateTime());
                }
                
                Timestamp tsDen = rs.getTimestamp("ngayGioDen");
                if (tsDen != null) {
                    chuyenTau.setNgayGioDen(tsDen.toLocalDateTime());
                }
                
                // Gán các đối tượng phức tạp vào ChuyenTau
                chuyenTau.setTau(tau);
                chuyenTau.setTuyenDuong(tuyenDuong);
                
                dsChuyenTau.add(chuyenTau);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return dsChuyenTau;
    }

    // Các phương thức getSoLuongGheDaDat và getTongSoGhe không thay đổi
    // vì chúng chỉ phụ thuộc vào maChuyenTau.
    
    public int getSoLuongGheDaDat(String maChuyenTau) throws SQLException {
        int soGheDat = 0;
        Connection con = ConnectDB.getConnection();
        String sql = "SELECT COUNT(*) FROM Ve WHERE maChuyenTau = ?";
        
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, maChuyenTau);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                soGheDat = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return soGheDat;
    }

    public int getTongSoGhe(String maChuyenTau) throws SQLException {
        int tongSoGhe = 0;
        Connection con = ConnectDB.getConnection();
        String sql = "SELECT SUM(tt.soLuongGhe) " +
                     "FROM ChuyenTau ct " +
                     "JOIN Tau t ON ct.maTau = t.maTau " +
                     "JOIN ToaTau tt ON t.maTau = tt.maTau " +
                     "WHERE ct.maChuyenTau = ?";
                     
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, maChuyenTau);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                tongSoGhe = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tongSoGhe;
    }
}