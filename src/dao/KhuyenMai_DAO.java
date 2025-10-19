package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import connectDB.ConnectDB;
import entity.KhuyenMai;
import entity.LoaiKhuyenMai;

public class KhuyenMai_DAO {

    /**
     * Lấy danh sách tất cả các chương trình khuyến mãi đang hợp lệ.
     * Hợp lệ nghĩa là: trạng thái 'Áp dụng' và còn trong thời hạn.
     * @return Danh sách các KhuyenMai hợp lệ.
     * @throws SQLException 
     */
    public List<KhuyenMai> getKhuyenMaiHopLe() throws SQLException {
        List<KhuyenMai> dsKhuyenMai = new ArrayList<>();
        Connection con = ConnectDB.getConnection();
        // GETDATE() là hàm lấy ngày giờ hiện tại của SQL Server
        String sql = "SELECT * FROM KhuyenMai WHERE trangThai = N'Áp dụng' AND GETDATE() BETWEEN ngayBatDau AND ngayKetThuc";
        
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                KhuyenMai km = new KhuyenMai();
                km.setMaKhuyenMai(rs.getString("maKhuyenMai"));
                km.setTenKhuyenMai(rs.getNString("tenKhuyenMai"));
                km.setMoTa(rs.getNString("moTa"));
                km.setGiaTriKhuyenMai(rs.getDouble("giaTriKhuyenMai"));
                // ... set các thuộc tính khác như ngayBatDau, ngayKetThuc ...

                // Chuyển đổi từ mã String sang enum LoaiKhuyenMai
                String maLoaiKM = rs.getString("maLoaiKhuyenMai");
                if (maLoaiKM != null) {
                    // Giả sử mã trong DB là "PHANTRAM" hoặc "SOTIENCODINH"
                    if (maLoaiKM.equalsIgnoreCase("PHANTRAM")) {
                        km.setLoaiKhuyenMai(LoaiKhuyenMai.PhanTram);
                    } else {
                        km.setLoaiKhuyenMai(LoaiKhuyenMai.SoTienCoDinh);
                    }
                }
                
                dsKhuyenMai.add(km);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return dsKhuyenMai;
    }

    /**
     * Lấy thông tin một chương trình khuyến mãi theo mã.
     * @param maKM Mã khuyến mãi cần tìm.
     * @return Đối tượng KhuyenMai hoặc null nếu không tìm thấy.
     * @throws SQLException 
     */
    public KhuyenMai getKhuyenMaiTheoMa(String maKM) throws SQLException {
        KhuyenMai km = null;
        Connection con = ConnectDB.getConnection();
        String sql = "SELECT * FROM KhuyenMai WHERE maKhuyenMai = ?";
        
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, maKM);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                km = new KhuyenMai();
                // ... code tạo đối tượng KhuyenMai tương tự như hàm trên ...
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return km;
    }
}