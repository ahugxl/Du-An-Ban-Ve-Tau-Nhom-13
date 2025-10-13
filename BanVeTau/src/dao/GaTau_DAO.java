package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import connectDB.ConnectDB;
import entity.GaTau;

public class GaTau_DAO {
	 // Lấy toàn bộ ga tàu
    public ArrayList<GaTau> getAllGaTau() throws SQLException {
    	ArrayList<GaTau> ds = new ArrayList<>();

        String sql = "SELECT maGaTau, tenGaTau, diaChiGa, soDienThoaiGa FROM GaTau";

        ConnectDB.getInstance();
        try (Connection con = ConnectDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String ma   = rs.getString("maGaTau");
                String ten  = rs.getNString("tenGaTau");      // NVARCHAR
                String diaC = rs.getNString("diaChiGa");      // NVARCHAR
                String sdt  = rs.getString("soDienThoaiGa");

                ds.add(new GaTau(ma, ten, diaC, sdt));
            }
        }
        return ds;
    }
 // Lấy 1 ga tàu theo mã
    public GaTau getGaTauTheoMa(String maGa, Connection con) throws SQLException {
        String sql = "SELECT maGaTau, tenGaTau, diaChiGa, soDienThoaiGa " +
                     "FROM GaTau WHERE maGaTau = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, maGa);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String ma   = rs.getString("maGaTau");
                    String ten  = rs.getNString("tenGaTau");
                    String diaC = rs.getNString("diaChiGa");
                    String sdt  = rs.getString("soDienThoaiGa");

                    return new GaTau(ma, ten, diaC, sdt);
                }
            }
        }
        return null; // không tìm thấy
    }
}
