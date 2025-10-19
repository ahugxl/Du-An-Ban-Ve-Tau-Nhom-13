package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import connectDB.ConnectDB;
import entity.ChuyenTau;
import entity.GaTau;
import entity.GheNgoi_mthanh; // Sửa lại tên nếu entity của bạn là GheNgoi_mthanh
import entity.KhachHang;
import entity.LoaiHanhTrinh;
import entity.LoaiVe;
import entity.Thue;
import entity.Ve;

public class Ve_DAO_mthanh {

    /**
     * Lấy tất cả các vé từ CSDL một cách hiệu quả.
     * Phương thức này sử dụng kỹ thuật cache để tránh lỗi N+1 query,
     * giúp giảm số lần truy vấn đến database.
     * @return một ArrayList các đối tượng Ve.
     */
    public ArrayList<Ve> getAllVe() {
        ArrayList<Ve> dsVe = new ArrayList<>();
        
        // Lớp nội bộ để chứa dữ liệu thô, giúp việc đọc từ DB đơn giản hơn
        class VeRaw {
            String maVe, tenVe, maCT, maGheNgoi, maGaDi, maGaDen, trangThaiVe, maThue, maKH;
            String maLoaiHanhTrinh, maLoaiVe;
            LocalDateTime ngayInVe;
            boolean coPhongChoVip;
        }
        List<VeRaw> raws = new ArrayList<>();

        try {
            final Connection con = ConnectDB.getConnection();

            // Bước 1: Đọc tất cả dữ liệu thô từ bảng Ve
            String sql = "SELECT maVe, tenVe, maChuyenTau, maGheNgoi, maGaDi, maGaDen, " +
                         "ngayInVe, maLoaiHanhTrinh, maLoaiVe, trangThaiVe, coPhongChoVip, maThueApDung, maKhachHang " +
                         "FROM Ve WHERE ISNULL(trangThaiXoa, 0) = 0";
            
            try (PreparedStatement ps = con.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    VeRaw r = new VeRaw();
                    r.maVe             = rs.getString("maVe");
                    r.tenVe            = rs.getNString("tenVe");
                    r.maCT             = rs.getString("maChuyenTau");
                    r.maGheNgoi        = rs.getString("maGheNgoi");
                    r.maGaDi           = rs.getString("maGaDi");
                    r.maGaDen          = rs.getString("maGaDen");
                    Timestamp t        = rs.getTimestamp("ngayInVe");
                    r.ngayInVe         = (t != null) ? t.toLocalDateTime() : null;
                    r.maLoaiHanhTrinh  = rs.getString("maLoaiHanhTrinh");
                    r.maLoaiVe         = rs.getString("maLoaiVe");
                    r.trangThaiVe      = rs.getNString("trangThaiVe");
                    r.coPhongChoVip    = rs.getBoolean("coPhongChoVip");
                    r.maThue           = rs.getString("maThueApDung");
                    r.maKH             = rs.getString("maKhachHang");
                    raws.add(r);
                }
            }

            // Bước 2: Dùng cache để xây dựng các đối tượng phụ thuộc
            KhachHang_DAO_mthanh khDao = new KhachHang_DAO_mthanh();
            GaTau_DAO gaDao = new GaTau_DAO();
            ChuyenTau_DAO ctDao = new ChuyenTau_DAO();
            GheNgoi_DAO_mthanh gnDao = new GheNgoi_DAO_mthanh();
            Thue_DAO thueDao = new Thue_DAO();
            LoaiVe_DAO loaiVeDAO = new LoaiVe_DAO();

            Map<String, ChuyenTau> cacheCT = new HashMap<>();
            Map<String, GheNgoi_mthanh> cacheGN = new HashMap<>();
            Map<String, GaTau> cacheGa = new HashMap<>();
            Map<String, KhachHang> cacheKH = new HashMap<>();
            Map<String, Thue> cacheThue = new HashMap<>();
            Map<String, LoaiVe> cacheLoaiVe = new HashMap<>();

            for (VeRaw r : raws) {
                ChuyenTau ct = (r.maCT != null) ? cacheCT.computeIfAbsent(r.maCT, k -> {
                    try { return ctDao.getChuyenTauTheoMa(k); } 
                    catch (SQLException e) { e.printStackTrace(); return null; }
                }) : null;

                GheNgoi_mthanh ghe = (r.maGheNgoi != null) ? cacheGN.computeIfAbsent(r.maGheNgoi, k -> {
                    try { return gnDao.getGheNgoiTheoMa(k); } 
                    catch (SQLException e) { e.printStackTrace(); return null; }
                }) : null;

                GaTau gaDi = (r.maGaDi != null) ? cacheGa.computeIfAbsent(r.maGaDi, k -> {
                    try { return gaDao.getGaTauTheoMa(k); } 
                    catch (SQLException e) { e.printStackTrace(); return null; }
                }) : null;

                GaTau gaDen = (r.maGaDen != null) ? cacheGa.computeIfAbsent(r.maGaDen, k -> {
                    try { return gaDao.getGaTauTheoMa(k); } 
                    catch (SQLException e) { e.printStackTrace(); return null; }
                }) : null;

                Thue thue = (r.maThue != null) ? cacheThue.computeIfAbsent(r.maThue, k -> {
                    try { return thueDao.getThueTheoMa(k); } 
                    catch (SQLException e) { e.printStackTrace(); return null; }
                }) : null;

                KhachHang kh = (r.maKH != null) ? cacheKH.computeIfAbsent(r.maKH, k -> {
                    try { return khDao.getKhachHangTheoMa(k); } 
                    catch (SQLException e) { e.printStackTrace(); return null; }
                }) : null;

                LoaiVe loaiVe = (r.maLoaiVe != null) ? cacheLoaiVe.computeIfAbsent(r.maLoaiVe, k -> {
                    try { return loaiVeDAO.getLoaiVeTheoMa(k); } 
                    catch (SQLException e) { e.printStackTrace(); return null; }
                }) : null;
                
                Ve ve = new Ve(r.maVe, r.tenVe, null, ct, ghe, gaDi, gaDen,
                               r.ngayInVe, r.trangThaiVe, 
                               parseLoaiHanhTrinh(r.maLoaiHanhTrinh),
                               loaiVe,
                               r.coPhongChoVip, thue, kh);
                dsVe.add(ve);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsVe;
    }

    /**
     * Phương thức phụ để chuyển đổi mã String từ DB sang enum LoaiHanhTrinh.
     */
    private LoaiHanhTrinh parseLoaiHanhTrinh(String code) {
        if (code == null) return LoaiHanhTrinh.Thuong; // Mặc định
        switch (code.trim().toUpperCase()) {
            case "KH": return LoaiHanhTrinh.KhuHoiLuotDi;
            case "MC":
            default: return LoaiHanhTrinh.KhuHoiLuotVe;
        }
    }
}