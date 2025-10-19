package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import connectDB.ConnectDB;
import entity.ChangTau;
import entity.GaTau;

public class ChangTau_DAO {
	// Trong file ChangTau_DAO.java
	// Trong file ChangTau_DAO.java

	public ArrayList<ChangTau> getChangTauByMaChuyenTau(String maChuyenTau) throws SQLException {
	    ArrayList<ChangTau> dsChang = new ArrayList<>();
	    Connection con = ConnectDB.getConnection();
	    String sql = "SELECT * FROM ChangTau WHERE maChuyenTau = ? ORDER BY soThuTu";
	    
	    try (PreparedStatement pstmt = con.prepareStatement(sql)) {
	        pstmt.setString(1, maChuyenTau);
	        ResultSet rs = pstmt.executeQuery();
	        
	        // 1. Khởi tạo DAO và cache TRƯỚC vòng lặp
	        GaTau_DAO gaTauDAO = new GaTau_DAO();
	        Map<String, GaTau> cacheGa = new HashMap<>();
	        
	        while (rs.next()) {
	            ChangTau chang = new ChangTau();
	            chang.setMaChangTau(rs.getString("maChangTau"));
	            chang.setSoKm(rs.getInt("soKm"));
	            chang.setSoThuTu(rs.getInt("soThuTu"));
	            // ... set các thuộc tính khác từ ResultSet ...
	            
	            String maGaDi = rs.getString("maGaDi");
	            String maGaDen = rs.getString("maGaDen");
	            
	            // 2. Sử dụng cache.computeIfAbsent để lấy đối tượng GaTau
	            // Nó chỉ gọi đến gaTauDAO.getGaTauTheoMa(k) nếu mã ga chưa có trong cache
	            GaTau gaDi = cacheGa.computeIfAbsent(maGaDi, k -> {
	                try {
	                    return gaTauDAO.getGaTauTheoMa(k);
	                } catch (SQLException e) {
	                    e.printStackTrace();
	                    return null;
	                }
	            });
	            
	            GaTau gaDen = cacheGa.computeIfAbsent(maGaDen, k -> {
	                try {
	                    return gaTauDAO.getGaTauTheoMa(k);
	                } catch (SQLException e) {
	                    e.printStackTrace();
	                    return null;
	                }
	            });

	            chang.setGaDi(gaDi);
	            chang.setGaDen(gaDen);
	            
	            dsChang.add(chang);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return dsChang;
	}
}
