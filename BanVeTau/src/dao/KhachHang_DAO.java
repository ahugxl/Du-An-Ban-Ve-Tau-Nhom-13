package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import connectDB.ConnectDB;
import entity.KhachHang;

public class KhachHang_DAO {

	public KhachHang_DAO() {
	}

	public ArrayList<KhachHang> getAllKhachHang() {
		ArrayList<KhachHang> ds = new ArrayList<KhachHang>();
		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();
			String sql = "select * from KhachHang";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				KhachHang kh = new KhachHang(rs.getString(1), rs.getString(2), rs.getString(3),
						rs.getDate(3).toLocalDate(), rs.getString(4), rs.getBoolean(5));
				ds.add(kh);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ds;
	}
	public ArrayList<KhachHang> tim(String tuKhoa){
		ArrayList<KhachHang> ds= new ArrayList<KhachHang>();
		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();
			String sql = "select * from KhachHang where maKhachHang like ? or hoTenKhachHang like ? or soGiayTo like ? or soDienThoai like ?";
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1,"%" + tuKhoa + "%");
			stmt.setString(2,"%" + tuKhoa + "%");
			stmt.setString(3,"%" + tuKhoa + "%");
			stmt.setString(5,"%" + tuKhoa + "%");
			ResultSet rs= stmt.executeQuery();
			while(rs.next()) {
				KhachHang kh= new KhachHang(rs.getString(1), rs.getString(2), rs.getString(3),
						rs.getDate(3).toLocalDate(), rs.getString(4), rs.getBoolean(5));
				ds.add(kh);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ds;
	}
}
