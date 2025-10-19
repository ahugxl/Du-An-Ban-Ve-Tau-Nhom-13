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

public class ThongKeThang_DAO {

    /**
     * Lấy tổng doanh thu của mỗi tháng trong một năm cụ thể.
     * Dùng cho việc vẽ biểu đồ tổng quan năm.
     * @param nam Năm cần thống kê.
     * @return Một Map với Key là tháng (Integer) và Value là tổng doanh thu (Double).
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
            close(rs, stmt, con);
        }
        return doanhThuThang;
    }
    
    /**
     * Lấy danh sách Hóa Đơn chi tiết trong một tháng và năm cụ thể.
     * Dùng cho việc hiển thị ở khung bên trái.
     * @param thang Tháng cần thống kê.
     * @param nam Năm cần thống kê.
     * @return Danh sách các Hóa Đơn.
     */
    public List<HoaDon> getHoaDonTheoThang(int thang, int nam) {
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
                         "WHERE YEAR(hd.ngayLapHoaDon) = ? AND MONTH(hd.ngayLapHoaDon) = ?";
            
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, nam);
            stmt.setInt(2, thang);
            rs = stmt.executeQuery();

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
            close(rs, stmt, con);
        }
        return dsHoaDon;
    }

    private void close(ResultSet rs, PreparedStatement stmt, Connection con) {
        try {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            // Không đóng connection ở đây nếu bạn dùng Singleton
        } catch (SQLException e2) {
            e2.printStackTrace();
        }
    }
}