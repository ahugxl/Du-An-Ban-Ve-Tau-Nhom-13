package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

import connectDB.ConnectDB;
import entity.KhachHang;
import entity.NhanVien;

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
						rs.getDate(4).toLocalDate(), rs.getString(5), rs.getBoolean(6));
				ds.add(kh);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ds;
	}
	// Tìm theo đúng số
	public ArrayList<KhachHang> getDsKhachHangTheoSoDienThoai(String soDT) throws SQLException {
	    String sql = "SELECT maKhachHang, hoTenKhachHang, soGiayTo, ngaySinh, soDienThoai, gioiTinh " +
	                 "FROM KhachHang WHERE soDienThoai = ?";
	    ArrayList<KhachHang> ds = new ArrayList<>();
	    ConnectDB.getInstance();
	    try (Connection con = ConnectDB.getConnection();
	         PreparedStatement ps = con.prepareStatement(sql)) {
	        ps.setString(1, soDT);
	        try (ResultSet rs = ps.executeQuery()) {
	            while (rs.next()) {
	                ds.add(new KhachHang(
	                    rs.getString("maKhachHang"),
	                    rs.getNString("hoTenKhachHang"),
	                    rs.getString("soGiayTo"),
	                    rs.getDate("ngaySinh").toLocalDate(),
	                    rs.getString("soDienThoai"),
	                    rs.getBoolean("gioiTinh")
	                ));
	            }
	        }
	    }
	    return ds;
	}
	// Tìm theo đúng số
		public ArrayList<KhachHang> getDsKhachHangTheoCCCD(String cccd) throws SQLException {
		    String sql = "SELECT maKhachHang, hoTenKhachHang, soGiayTo, ngaySinh, soDienThoai, gioiTinh " +
		                 "FROM KhachHang WHERE soGiayTo = ?";
		    ArrayList<KhachHang> ds = new ArrayList<>();
		    ConnectDB.getInstance();
		    try (Connection con = ConnectDB.getConnection();
		         PreparedStatement ps = con.prepareStatement(sql)) {
		        ps.setString(1, cccd);
		        try (ResultSet rs = ps.executeQuery()) {
		            while (rs.next()) {
		                ds.add(new KhachHang(
		                    rs.getString("maKhachHang"),
		                    rs.getNString("hoTenKhachHang"),
		                    rs.getString("soGiayTo"),
		                    rs.getDate("ngaySinh").toLocalDate(),
		                    rs.getString("soDienThoai"),
		                    rs.getBoolean("gioiTinh")
		                ));
		            }
		        }
		    }
		    return ds;
		}
	

	public KhachHang getKhachHangTheoMa(String ma, Connection con) throws SQLException {
	    String sql = "SELECT maKhachHang, hoTenKhachHang, soGiayTo, ngaySinh, soDienThoai, gioiTinh " +
	                 "FROM KhachHang WHERE maKhachHang = ?";

	    try (PreparedStatement ps = con.prepareStatement(sql)) {

	        ps.setString(1, ma);
	        try (ResultSet rs = ps.executeQuery()) {
	            if (rs.next()) {
	                String maKH       = rs.getString("maKhachHang");
	                String hoTen      = rs.getNString("hoTenKhachHang"); // NVARCHAR
	                String soGiayTo   = rs.getString("soGiayTo");
	                java.sql.Date d   = rs.getDate("ngaySinh");
	                LocalDate ngaySinh = (d != null) ? d.toLocalDate() : null;
	                String soDT       = rs.getString("soDienThoai");
	                boolean gioiTinh  = rs.getBoolean("gioiTinh");

	                return new KhachHang(maKH, hoTen, soGiayTo, ngaySinh, soDT, gioiTinh);
	            }
	        }
	    }
	    return null; // không tìm thấy
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
	
	public boolean themKhachHang(KhachHang kh) {
		String sql = "insert into KhachHang (maKhachHang, hoTenKhachHang, soGiayTo, ngaySinh, soDienThoai, gioiTinh, trangThaiXoaa) VALUES (?, ?, ?, ?, ?, ?, 0)";

		try {
			Connection con = ConnectDB.getConnection();
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1,kh.getMaKhachHang());
			stmt.setString(2, kh.getHoTenKhachHang());
			stmt.setString(3, kh.getSoGiayTo());
			stmt.setDate(4, Date.valueOf(kh.getNgaySinh()));
			stmt.setString(5, kh.getSoDienThoai());
			stmt.setBoolean(6, kh.isGioiTinh());
			return stmt.executeUpdate() > 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean xoaKhachHang(KhachHang kh) {
		String sql = "update KhachHang set trangThaiXoa = 1 where maKhachHang = ?";
		try {
			Connection con = ConnectDB.getConnection();
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, kh.getMaKhachHang());
			return stmt.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean capNhatKhachHang(KhachHang kh) {
		String sql = "update KhachHang set maKhachHang=?, hoTenKhachHang=?, soGiayTo=?, ngaySinh=?, soDienThoai=?, gioiTinh=?, trangThaiXoa=? "
				+ "WHERE maKhachHang=?";

		try {
			Connection con = ConnectDB.getConnection();
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1,kh.getMaKhachHang());
			stmt.setString(2, kh.getHoTenKhachHang());
			stmt.setString(3, kh.getSoGiayTo());
			stmt.setDate(4, Date.valueOf(kh.getNgaySinh()));
			stmt.setString(5, kh.getSoDienThoai());
			stmt.setBoolean(6, kh.isGioiTinh());
			return stmt.executeUpdate() > 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
