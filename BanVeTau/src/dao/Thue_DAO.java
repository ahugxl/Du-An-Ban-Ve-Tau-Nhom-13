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
    public Thue getThueTheoMa(String maSoThue, Connection con) throws SQLException {
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

                    return new Thue(ma, ten, muc, tt, nb);
                }
            }
        }
        return null; // không tìm thấy
    }
}
