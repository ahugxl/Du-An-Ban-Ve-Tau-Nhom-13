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
    /**
     * Tìm một đoàn tàu theo mã.
     * Phương thức này tự quản lý việc kết nối đến CSDL.
     * @param maTau Mã của đoàn tàu cần tìm.
     * @return Đối tượng Tau nếu tìm thấy, ngược lại trả về null.
     * @throws SQLException
     */
    public Tau getTauTheoMa(String maTau) throws SQLException {
        Tau tau = null;
        Connection con = ConnectDB.getConnection(); // Tự lấy kết nối
        String sql = "SELECT maTau, tenTau, maLoaiTau, soLanSuaChua FROM Tau WHERE maTau = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, maTau);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String maTauDB = rs.getString("maTau");
                    String tenTau = rs.getNString("tenTau");
                    String maLoaiTau = rs.getString("maLoaiTau");
                    int soLanSuaChua = rs.getInt("soLanSuaChua");
                    
                    LoaiTau loaiTauEnum = null;
                    // Tối ưu: Dùng LoaiTau.valueOf() để chuyển đổi String sang enum
                    // Cách này yêu cầu tên hằng số trong enum phải khớp với dữ liệu trong DB
                    if (maLoaiTau != null) {
                        try {
                            loaiTauEnum = LoaiTau.valueOf(maLoaiTau.trim());
                        } catch (IllegalArgumentException e) {
                            System.err.println("Không tìm thấy enum cho mã loại tàu: " + maLoaiTau);
                            // Gán giá trị mặc định hoặc xử lý lỗi nếu cần
                        }
                    }
                    
                    tau = new Tau(maTauDB, tenTau, loaiTauEnum, soLanSuaChua);
                }
            }
        }
        return tau;
    }
}
