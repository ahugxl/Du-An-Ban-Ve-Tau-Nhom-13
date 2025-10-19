package dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import connectDB.ConnectDB;
import entity.Thue;

public class Thue_DAO {

    // Lấy toàn bộ thuế
    public ArrayList<Thue> getAllThue() throws SQLException {
    	ArrayList<Thue> ds = new ArrayList<>();

        String sql = "SELECT maSoThue, tenThue, mucThue, trangThai, ngayBatDau FROM Thue";

        ConnectDB.getInstance();
        try (Connection con = ConnectDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String ma   = rs.getString("maSoThue");
                String ten  = rs.getNString("tenThue");
               
                double muc  = rs.getDouble("mucThue");
                String tt   = rs.getNString("trangThai");
                Date d      = rs.getDate("ngayBatDau");
                LocalDate nb = (d != null) ? d.toLocalDate() : null;

                ds.add(new Thue(ma, ten, muc, tt, nb));
            }
        }
        return ds;
    }

    // Lấy 1 thuế theo mã
    /**
     * Tìm một loại thuế theo mã số thuế.
     * Phương thức này tự quản lý việc kết nối đến CSDL.
     * @param maSoThue Mã số thuế cần tìm.
     * @return Đối tượng Thue nếu tìm thấy, ngược lại trả về null.
     * @throws SQLException
     */
    public Thue getThueTheoMa(String maSoThue) throws SQLException {
        Thue thue = null;
        Connection con = ConnectDB.getConnection(); // Tự lấy kết nối

        String sql = "SELECT maSoThue, tenThue, mucThue, trangThai, ngayBatDau " +
                     "FROM Thue WHERE maSoThue = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, maSoThue);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String ma   = rs.getString("maSoThue");
                    String ten  = rs.getNString("tenThue");
                    double muc  = rs.getDouble("mucThue");
                    String tt   = rs.getNString("trangThai");
                    Date d      = rs.getDate("ngayBatDau");
                    LocalDate nb = (d != null) ? d.toLocalDate() : null;

                    thue = new Thue(ma, ten, muc, tt, nb);
                }
            }
        }
        return thue;
    }
    public boolean addThue(Thue thue) {
        Connection con = null;
        PreparedStatement stmt = null;
        int n = 0;
        try {
            con = ConnectDB.getInstance().getConnection();
            String sql = "INSERT INTO Thue(maSoThue, tenThue, mucThue, trangThai, ngayBatDau) VALUES(?, ?, ?, ?, ?)";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, thue.getMaSoThue());
            stmt.setString(2, thue.getTenThue());
            stmt.setDouble(3, thue.getMucThue());
            stmt.setString(4, thue.getTrangThai());
            stmt.setDate(5, java.sql.Date.valueOf(thue.getNgayBatDau()));
            n = stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return n > 0;
    }
    
    /**
     * Cập nhật thông tin của một mục thuế dựa vào mã số thuế.
     * @param thue Đối tượng Thue chứa thông tin mới cần cập nhật.
     * @return true nếu cập nhật thành công, false nếu thất bại.
     */
    public boolean updateThue(Thue thue) {
        Connection con = null;
        PreparedStatement stmt = null;
        int n = 0;
        try {
            con = ConnectDB.getInstance().getConnection();
            String sql = "UPDATE Thue SET tenThue = ?, mucThue = ?, trangThai = ?, ngayBatDau = ? "
                       + "WHERE maSoThue = ?";
            
            stmt = con.prepareStatement(sql);
            
            stmt.setString(1, thue.getTenThue());
            stmt.setDouble(2, thue.getMucThue());
            stmt.setString(3, thue.getTrangThai());
            stmt.setDate(4, java.sql.Date.valueOf(thue.getNgayBatDau()));
            stmt.setString(5, thue.getMaSoThue()); // Tham số cho mệnh đề WHERE
            
            n = stmt.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return n > 0;
    }
}
