package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import connectDB.ConnectDB;
import entity.ChangTau;
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
 // Trong file ChuyenTau_DAO.java

    /**
     * Tìm một chuyến tàu duy nhất dựa vào mã chuyến tàu.
     * Phương thức này sẽ tải đầy đủ thông tin của ChuyenTau, bao gồm cả danh sách các ChangTau.
     * @param maChuyenTau Mã của chuyến tàu cần tìm.
     * @return Đối tượng ChuyenTau đã được nạp đầy đủ dữ liệu, hoặc null nếu không tìm thấy.
     * @throws SQLException 
     */
 // Trong file ChuyenTau_DAO.java

    /**
     * Tìm một chuyến tàu duy nhất dựa vào mã.
     * Phương thức này tải đầy đủ thông tin của ChuyenTau, bao gồm Tau, TuyenDuong, và List<ChangTau>.
     * @param maChuyenTau Mã của chuyến tàu cần tìm.
     * @return Đối tượng ChuyenTau đã được nạp đầy đủ dữ liệu, hoặc null nếu không tìm thấy.
     * @throws SQLException
     */
    public ChuyenTau getChuyenTauTheoMa(String maChuyenTau) throws SQLException {
        ChuyenTau chuyenTau = null;
        Connection con = ConnectDB.getConnection();
        ChangTau_DAO changTauDAO = new ChangTau_DAO(); 

        String sql = "SELECT ct.*, t.tenTau, t.maLoaiTau, t.soLanSuaChua, " +
                     "       td.tenTuyenDuong, td.gaKhoiHanh AS maGaKhoiHanh_TD, td.gaKetThuc AS maGaKetThuc_TD, " +
                     "       gaDi_td.tenGaTau AS tenGaDi_TuyenDuong, " +
                     "       gaDen_td.tenGaTau AS tenGaDen_TuyenDuong " +
                     "FROM ChuyenTau ct " +
                     "JOIN Tau t ON ct.maTau = t.maTau " +
                     "JOIN TuyenDuong td ON ct.maTuyenDuong = td.maTuyenDuong " +
                     "JOIN GaTau gaDi_td ON td.gaKhoiHanh = gaDi_td.maGaTau " +
                     "JOIN GaTau gaDen_td ON td.gaKetThuc = gaDen_td.maGaTau " +
                     "WHERE ct.maChuyenTau = ?";
                     
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, maChuyenTau);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                // 1. Tạo đối tượng ChuyenTau từ ResultSet
                chuyenTau = new ChuyenTau();
                chuyenTau.setMaChuyenTau(rs.getString("maChuyenTau"));
                chuyenTau.setDonGiaCoBan(rs.getDouble("donGiaCoBan"));
                chuyenTau.setNgayGioKhoiHanh(rs.getTimestamp("ngayGioKhoiHanh").toLocalDateTime());
                chuyenTau.setNgayGioDen(rs.getTimestamp("ngayGioDen").toLocalDateTime());

                // 2. Tạo các đối tượng con (Tau, TuyenDuong)
                Tau tau = new Tau();
                tau.setMaTau(rs.getString("maTau"));
                tau.setTenTau(rs.getString("tenTau"));
                
                // --- PHẦN HOÀN THIỆN ---
                tau.setSoLanSuaChua(rs.getInt("soLanSuaChua"));
                String maLoaiTau = rs.getString("maLoaiTau");
                if(maLoaiTau != null) {
                    tau.setLoaiTau(LoaiTau.valueOf(maLoaiTau.trim()));
                }
                // --- KẾT THÚC PHẦN HOÀN THIỆN ---

                GaTau gaDi_TuyenDuong = new GaTau(rs.getString("maGaKhoiHanh_TD"), rs.getString("tenGaDi_TuyenDuong"));
                GaTau gaDen_TuyenDuong = new GaTau(rs.getString("maGaKetThuc_TD"), rs.getString("tenGaDen_TuyenDuong"));
                
                TuyenDuong tuyenDuong = new TuyenDuong();
                tuyenDuong.setMaTuyenDuong(rs.getString("maTuyenDuong"));
                tuyenDuong.setTenTuyenDuong(rs.getString("tenTuyenDuong"));
                tuyenDuong.setGaKhoiHanh(gaDi_TuyenDuong);
                tuyenDuong.setGaKetThuc(gaDen_TuyenDuong);

                chuyenTau.setTau(tau);
                chuyenTau.setTuyenDuong(tuyenDuong);
                
                // 3. Lấy danh sách các chặng và gán vào chuyến tàu
                ArrayList<ChangTau> dsChang = changTauDAO.getChangTauByMaChuyenTau(chuyenTau.getMaChuyenTau());
                chuyenTau.setDanhSachChang(dsChang);
            }
        }
        
        return chuyenTau;
    }

    /**
     * Tìm kiếm các chuyến tàu dựa trên ga đi, ga đến và ngày khởi hành.
     * Phương thức này sẽ tải đầy đủ thông tin cho mỗi ChuyenTau, bao gồm cả danh sách các ChangTau.
     * @param gaDi Đối tượng GaTau của ga đi.
     * @param gaDen Đối tượng GaTau của ga đến.
     * @param ngayDi Ngày khởi hành mong muốn.
     * @return Danh sách các ChuyenTau phù hợp, mỗi ChuyenTau đã chứa List<ChangTau> của nó.
     * @throws SQLException 
     */
 // Trong file ChuyenTau_DAO.java

    /**
     * Tìm kiếm các chuyến tàu dựa trên ga đi, ga đến và ngày khởi hành.
     * PHIÊN BẢN MỚI: Tìm các chuyến tàu có lịch trình đi qua các ga đã chọn.
     * @param gaDi Đối tượng GaTau của ga đi.
     * @param gaDen Đối tượng GaTau của ga đến.
     * @param ngayDi Ngày khởi hành mong muốn.
     * @return Danh sách các ChuyenTau phù hợp.
     * @throws SQLException 
     */
    public ArrayList<ChuyenTau> findChuyenTau(GaTau gaDi, GaTau gaDen, LocalDate ngayDi) throws SQLException {
    	ArrayList<ChuyenTau> dsChuyenTau = new ArrayList<>();
        Connection con = ConnectDB.getConnection();
        ChangTau_DAO changTauDAO = new ChangTau_DAO();

        // Câu SQL này sử dụng EXISTS để kiểm tra xem một chuyến tàu có đi qua
        // ga đi và ga đến yêu cầu hay không, đồng thời kiểm tra thứ tự của chúng.
        String sql = "SELECT DISTINCT ct.* FROM ChuyenTau ct " +
                     "JOIN ChangTau c_di ON ct.maChuyenTau = c_di.maChuyenTau " +
                     "JOIN ChangTau c_den ON ct.maChuyenTau = c_den.maChuyenTau " +
                     "WHERE c_di.maGaDi = ? " +
                     "AND c_den.maGaDen = ? " +
                     "AND c_di.soThuTu < c_den.soThuTu " +
                     "AND CONVERT(date, ct.ngayGioKhoiHanh) = ?";

        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, gaDi.getMaGaTau());
            pstmt.setString(2, gaDen.getMaGaTau());
            pstmt.setDate(3, java.sql.Date.valueOf(ngayDi));
            
            ResultSet rs = pstmt.executeQuery();
            
            // Dùng DAO để lấy đối tượng ChuyenTau hoàn chỉnh từ mã tìm được
            while (rs.next()) {
                ChuyenTau chuyenTau = getChuyenTauTheoMa(rs.getString("maChuyenTau"));
                if (chuyenTau != null) {
                    dsChuyenTau.add(chuyenTau);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return dsChuyenTau;
    }

    
}