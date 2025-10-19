package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import connectDB.ConnectDB;
import entity.HoaDon;
import entity.KhachHang;
import entity.NhanVien;

public class ThongKeNam_DAO {

    /**
     * Lấy tổng doanh thu của mỗi tháng trong một năm. Dùng cho biểu đồ.
     */
    public Map<Integer, Double> getDoanhThuTungThang(int nam) {
        Map<Integer, Double> doanhThuThang = new HashMap<>();
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            con = ConnectDB.getInstance().getConnection();
            String sql = "SELECT MONTH(ngayLapHoaDon) AS Thang, SUM(tongTien) AS TongDoanhThu " +
                         "FROM HoaDon " +
                         "WHERE YEAR(ngayLapHoaDon) = ? " +
                         "GROUP BY MONTH(ngayLapHoaDon)";
            
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, nam);
            rs = stmt.executeQuery();

            while(rs.next()) {
                int thang = rs.getInt("Thang");
                double tongDoanhThu = rs.getDouble("TongDoanhThu");
                doanhThuThang.put(thang, tongDoanhThu);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(rs, stmt); // Gọi hàm close
        }
        return doanhThuThang;
    }
    
    /**
     * Lấy TẤT CẢ hóa đơn chi tiết trong một năm cụ thể.
     * Dùng cho khung thông tin tổng hợp bên trái.
     */
    public List<HoaDon> getHoaDonTheoNam(int nam) {
        List<HoaDon> dsHoaDon = new ArrayList<>();
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            con = ConnectDB.getInstance().getConnection();
            String sql = "SELECT hd.maHoaDon, hd.ngayLapHoaDon, hd.tongTien, " +
                         "kh.maKhachHang, kh.hoTenKhachHang, kh.soDienThoai, " +
                         "nv.maNhanVien, nv.tenNhanVien " +
                         "FROM HoaDon hd " +
                         "JOIN KhachHang kh ON hd.maKhachHang = kh.maKhachHang " +
                         "JOIN NhanVien nv ON hd.maNhanVienLapHoaDon = nv.maNhanVien " +
                         "WHERE YEAR(hd.ngayLapHoaDon) = ?";
            
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, nam);
            rs = stmt.executeQuery();

            // ✅ BỔ SUNG PHẦN XỬ LÝ DỮ LIỆU BỊ THIẾU
            while (rs.next()) {
                NhanVien nv = new NhanVien();
                nv.setMaNhanVien(rs.getString("maNhanVien"));
                nv.setTenNV(rs.getString("tenNhanVien"));

                KhachHang kh = new KhachHang();
                kh.setMaKhachHang(rs.getString("maKhachHang"));
                kh.setHoTenKhachHang(rs.getString("hoTenKhachHang"));
                kh.setSoDienThoai(rs.getString("soDienThoai"));
                
                HoaDon hd = new HoaDon();
                hd.setMaHoaDon(rs.getString("maHoaDon"));
                hd.setNgayLapHoaDon(rs.getTimestamp("ngayLapHoaDon").toLocalDateTime());
                hd.setTongTien(rs.getDouble("tongTien"));
                hd.setNhanVienLapHoaDon(nv);
                hd.setKhachHang(kh);
                
                dsHoaDon.add(hd);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(rs, stmt); // Gọi hàm close
        }
        return dsHoaDon;
    }

    /**
     * ✅ THÊM PHƯƠNG THỨC NÀY ĐỂ ĐÓNG KẾT NỐI
     * Phương thức tiện ích để đóng ResultSet và PreparedStatement
     */
    private void close(ResultSet rs, PreparedStatement stmt) {
        try {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
        } catch (SQLException e2) {
            e2.printStackTrace();
        }
    }
}