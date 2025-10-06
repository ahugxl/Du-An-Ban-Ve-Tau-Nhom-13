package dao;

import entity.*;
import connectDB.ConnectDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Tau_DAO {
    public List<Tau> getAllTau() {
        List<Tau> list = new ArrayList<>();
        String sql = "SELECT maTau, tenTau, maLoaiTau, soLanSuaChua FROM Tau";

        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

        	while (rs.next()) {
            	String maLoaiTauFromDB = rs.getString("maLoaiTau");
            	LoaiTau loaiTauEnum;
            	switch (maLoaiTauFromDB) {
            	    case "SE":
            	        loaiTauEnum = LoaiTau.SE;
            	        break;
            	    case "SNT":
            	        loaiTauEnum = LoaiTau.SNT;
            	        break;
            	    case "SPT":
            	        loaiTauEnum = LoaiTau.SPT;
            	        break;
            	    default:
            	        // Xử lý trường hợp mã không hợp lệ
            	        throw new IllegalArgumentException("Loại tàu không hợp lệ: " + maLoaiTauFromDB);
            	}
                Tau tau = new Tau();
                tau.setMaTau(rs.getString("maTau"));
                tau.setTenTau(rs.getString("tenTau"));
                tau.setLoaiTau(loaiTauEnum); // enum
                tau.setSoLanSuaChua(rs.getInt("soLanSuaChua"));
                list.add(tau);
            }
        } catch (Exception e) {
			e.printStackTrace();
		}
        return list;
    }
}
