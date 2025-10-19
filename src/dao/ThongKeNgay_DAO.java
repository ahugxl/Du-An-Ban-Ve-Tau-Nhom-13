package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import connectDB.ConnectDB;
import entity.HoaDon;
import entity.KhachHang;
import entity.NhanVien;

public class ThongKeNgay_DAO {

    public List<NhanVien> getAllNhanVien() {
        // ... phương thức này giữ nguyên như cũ ...
        List<NhanVien> dsNhanVien = new ArrayList<>();
        Connection con = null;
        try {
            con = ConnectDB.getInstance().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Statement stmt = null;
        ResultSet rs = null;

        try {
            String sql = "SELECT maNhanVien, tenNhanVien FROM NhanVien";
            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                NhanVien nv = new NhanVien();
                nv.setMaNhanVien(rs.getString("maNhanVien")); 
                nv.setTenNV(rs.getString("tenNhanVien"));
                dsNhanVien.add(nv);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (SQLException e2) {
                e2.printStackTrace();
            }
        }
        return dsNhanVien;
    }

    public List<HoaDon> getHoaDonTheoNgay(LocalDate ngayThongKe) {
        List<HoaDon> dsHoaDon = new ArrayList<>();
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            con = ConnectDB.getInstance().getConnection();
            
            // THAY ĐỔI SQL: Thêm 'hd.tongTien' vào danh sách SELECT
            String sql = "SELECT hd.maHoaDon, hd.tongTien, hd.ngayLapHoaDon, " +
                         "kh.maKhachHang, kh.hoTenKhachHang, kh.soDienThoai, " +
                         "nv.maNhanVien, nv.tenNhanVien " +
                         "FROM HoaDon hd " +
                         "JOIN KhachHang kh ON hd.maKhachHang = kh.maKhachHang " + 
                         // SỬA LẠI ĐIỀU KIỆN JOIN cho đúng với thiết kế CSDL
                         "JOIN NhanVien nv ON hd.maNhanVienLapHoaDon = nv.maNhanVien " +
                         "WHERE CAST(hd.ngayLapHoaDon AS DATE) = ?";
            
            stmt = con.prepareStatement(sql);
            stmt.setDate(1, java.sql.Date.valueOf(ngayThongKe));
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
                
                // THÊM LẠI: Lấy giá trị tổng tiền từ ResultSet và set cho đối tượng Hóa Đơn
                hd.setTongTien(rs.getDouble("tongTien"));
                
                hd.setNgayLapHoaDon(rs.getTimestamp("ngayLapHoaDon").toLocalDateTime());
                hd.setNhanVienLapHoaDon(nv);
                hd.setKhachHang(kh);
                
                dsHoaDon.add(hd);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (SQLException e2) {
                e2.printStackTrace();
            }
        }
        return dsHoaDon;
    }
}