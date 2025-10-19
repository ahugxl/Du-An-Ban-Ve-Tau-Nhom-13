package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import connectDB.ConnectDB;
import entity.ChucVu;

public class ChucVu_DAO {
//    public ChucVu getChucVuTheoMa(String maChucVu) {
//        String sql = "SELECT * FROM ChucVu WHERE maChucVu = ?";
//        try {
//            PreparedStatement stmt = con.prepareStatement(sql);
//            stmt.setString(1, maChucVu);
//            ResultSet rs = stmt.executeQuery();
//            if (rs.next()) {
//                ChucVu chucVu = new ChucVu(
//                        rs.getString("maChucVu"),
//                        rs.getString("tenChucVu"));
//                return chucVu;
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

    public List<ChucVu> getAllChucVu() {
        List<ChucVu> danhSachChucVu = new ArrayList<>();
        String sql = "SELECT * FROM ChucVu";
        try {
        	ConnectDB.getInstance();
        	Connection con = ConnectDB.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                ChucVu chucVu = new ChucVu(
                        rs.getString("maChucVu"),
                        rs.getString("tenChucVu"));
                danhSachChucVu.add(chucVu);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return danhSachChucVu;
    }

//    public boolean kiemTraTonTai(String maChucVu) {
//        String sql = "SELECT COUNT(*) FROM ChucVu WHERE maChucVu = ?";
//        try {
//        	ConnectDB.getInstance();
//        	Connection con = ConnectDB.getConnection();
//            PreparedStatement stmt = con.prepareStatement(sql);
//            stmt.setString(1, maChucVu);
//            ResultSet rs = stmt.executeQuery();
//            if (rs.next()) {
//                return rs.getInt(1) > 0;
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return false;
//    }
}