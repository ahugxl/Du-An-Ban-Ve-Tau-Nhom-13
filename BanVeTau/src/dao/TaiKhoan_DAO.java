package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import connectDB.ConnectDB;
import entity.GaTau;
import entity.TaiKhoan;
public class TaiKhoan_DAO {
	public ArrayList<TaiKhoan> getalltbPhongBan(){
		ArrayList<TaiKhoan> dstk = new ArrayList<TaiKhoan>();
		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();
			 String sql = "Select * from TaiKhoan";
			 Statement statement = con.createStatement();
			 
			 
			 ResultSet rs = statement.executeQuery(sql);
			 
			 while(rs.next()) {
				 String tenTK = rs.getString(1);
				 String mk = rs.getString(2);
				 String email = rs.getString(2);
				 TaiKhoan tk = new TaiKhoan(tenTK, mk, email);
				 dstk.add(tk);
			 }
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dstk;
	}
	public boolean capNhatMatKhauTheoTen(String tenTaiKhoan, String matKhauMoi) {
        boolean kq = false;
        PreparedStatement statement = null;
        try {
            ConnectDB.getInstance();
            Connection con = ConnectDB.getConnection();

            String sql = "UPDATE TaiKhoan SET matKhau = ? WHERE tenTaiKhoan = ?";
            statement = con.prepareStatement(sql);
            statement.setString(1, matKhauMoi);
            statement.setString(2, tenTaiKhoan);

            int n = statement.executeUpdate();
            kq = n > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        } 
        return kq;
    }
	 public TaiKhoan getTaiKhoanTheoTenTaiKhoan(String tenTK, Connection con) throws SQLException {
	        String sql = "SELECT tenTaiKhoan, matKhau, email " +
	                     "FROM TaiKhoan WHERE tenTaiKhoan = ?";

	        try (PreparedStatement ps = con.prepareStatement(sql)) {

	            ps.setString(1, tenTK);

	            try (ResultSet rs = ps.executeQuery()) {
	                if (rs.next()) {
	                    String ten   = rs.getString("tenTaiKhoan");
	                    String mk  = rs.getString("matKhau");
	                    String email = rs.getString("email");

	                    return new TaiKhoan(ten, mk, email);
	                }
	            }
	        }
	        return null; // không tìm thấy
	    }
}
