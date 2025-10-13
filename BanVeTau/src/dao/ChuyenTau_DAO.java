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
	 private final Tau_DAO tauDAO = new Tau_DAO();
	 private final TuyenDuong_DAO tuyenDuongDAO = new TuyenDuong_DAO();
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
//    public List<ChuyenTau> getAllChuyenTau2() throws SQLException {
//        List<ChuyenTau> ds = new ArrayList<>();
//
//        String sql = "SELECT maChuyenTau, maTau, maTuyenDuong, ngayGioKhoiHanh, ngayGioDen, donGiaCoBan " +
//                     "FROM ChuyenTau";
//
//        ConnectDB.getInstance();
//        try (Connection con = ConnectDB.getConnection();
//             PreparedStatement ps = con.prepareStatement(sql);
//             ResultSet rs = ps.executeQuery()) {
//
//            while (rs.next()) {
//                String maChuyen = rs.getString("maChuyenTau");
//                String maTau    = rs.getString("maTau");
//                String maTD     = rs.getString("maTuyenDuong");
//
//                // resolve FK -> entity
//                Tau tau = tauDAO.getTauTheoMa(maTau);
//                TuyenDuong td = tuyenDuongDAO.getTuyenDuongTheoMa(maTD);
//
//                Timestamp tk = rs.getTimestamp("ngayGioKhoiHanh");
//                Timestamp tdn = rs.getTimestamp("ngayGioDen");
//                LocalDateTime khoiHanh = (tk != null)  ? tk.toLocalDateTime()  : null;
//                LocalDateTime den      = (tdn != null) ? tdn.toLocalDateTime() : null;
//
//                double donGiaCoBan = rs.getDouble("donGiaCoBan");
//
//                ds.add(new ChuyenTau(maChuyen, tau, td, khoiHanh, den, donGiaCoBan));
//            }
//        }
//        return ds;
//    }

    // Lấy 1 chuyến tàu theo mã
    public ChuyenTau getTheoMa(String maChuyenTau, Connection con) throws SQLException {
        String sql = "SELECT maChuyenTau, maTau, maTuyenDuong, ngayGioKhoiHanh, ngayGioDen, donGiaCoBan " +
                     "FROM ChuyenTau WHERE maChuyenTau = ?";

        // 1) Đọc dữ liệu thô trước
        String maCT = null, maTau = null, maTD = null;
        LocalDateTime khoiHanh = null, den = null;
        double donGiaCoBan = 0.0;

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, maChuyenTau);

            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return null;

                maCT  = rs.getString("maChuyenTau");
                maTau = rs.getString("maTau");
                maTD  = rs.getString("maTuyenDuong");

                Timestamp tk  = rs.getTimestamp("ngayGioKhoiHanh");
                Timestamp tdn = rs.getTimestamp("ngayGioDen");
                khoiHanh = (tk  != null) ? tk.toLocalDateTime()  : null;
                den      = (tdn != null) ? tdn.toLocalDateTime() : null;

                // Nếu cột là DECIMAL/NUMERIC, cân nhắc dùng getBigDecimal để chính xác hơn
                donGiaCoBan = rs.getBigDecimal("donGiaCoBan") != null
                            ? rs.getBigDecimal("donGiaCoBan").doubleValue()
                            : 0.0;
            }
            // rs/ps/con đều đóng ở đây
        }

        // 2) Resolve FK sau khi đã đóng ResultSet/Connection
        Tau tau = tauDAO.getTauTheoMa(maTau, con);
        // Tên hàm theo bạn đang dùng ở nơi khác: getTheoMa (nếu bạn đặt là getTuyenDuongTheoMa thì giữ nguyên)
        TuyenDuong td = tuyenDuongDAO.getTuyenDuongTheoMa(maTD, con);

        return new ChuyenTau(maCT, tau, td, khoiHanh, den, donGiaCoBan);
    }


    
}