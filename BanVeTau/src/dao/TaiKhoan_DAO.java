package dao;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import connectDB.ConnectDB;
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
	
}
