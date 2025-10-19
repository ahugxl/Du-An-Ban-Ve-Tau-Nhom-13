package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import connectDB.ConnectDB;
import entity.LoaiVe;

public class LoaiVe_DAO {
	// Trong file LoaiVe_DAO.java
	public LoaiVe getLoaiVeTheoMa(String maLoaiVe) throws SQLException {
	    LoaiVe loaiVe = null;
	    Connection con = ConnectDB.getConnection();
	    String sql = "SELECT * FROM LoaiVe WHERE maLoaiVe = ?";
	    
	    try (PreparedStatement pstmt = con.prepareStatement(sql)) {
	        pstmt.setString(1, maLoaiVe);
	        ResultSet rs = pstmt.executeQuery();
	        if (rs.next()) {
	            loaiVe = new LoaiVe(
	                rs.getString("maLoaiVe"),
	                rs.getString("tenLoaiVe"),
	                rs.getDouble("tiLeGiamGia")
	            );
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return loaiVe;
	}
	public ArrayList<LoaiVe> getAllLoaiVe() throws SQLException {
		ArrayList<LoaiVe> dsLoaiVe = new ArrayList<>();
        Connection con = ConnectDB.getConnection();
        String sql = "SELECT * FROM LoaiVe";
        
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                LoaiVe loaiVe = new LoaiVe(
                    rs.getString("maLoaiVe"),
                    rs.getNString("tenLoaiVe"),
                    rs.getDouble("tiLeGiamGia")
                );
                dsLoaiVe.add(loaiVe);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return dsLoaiVe;
    }
}
