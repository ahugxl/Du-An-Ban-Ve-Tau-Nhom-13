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
import entity.ChucVu;
import entity.NhanVien;
import entity.TaiKhoan;

public class NhanVien_DAO {
	public NhanVien_DAO() {
		
	}
	public ArrayList<NhanVien> getalltbNhanVien(){
		ArrayList<NhanVien> dsnv = new ArrayList<NhanVien>();
		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();
			String sql ="Select * from NhanVien";
			Statement statement = con.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			while(rs.next()) {
				String maNV = rs.getString(1);
				String tenNV = rs.getString(2);
				LocalDate ngaySinh = rs.getDate(3).toLocalDate();
				boolean phai = rs.getBoolean(4);
				String sdt = rs.getString(5);
				boolean trangThaiLamViec = rs.getBoolean(6);
				String chucVu = rs.getString(7);
				ChucVu cv = null;
				if(chucVu.equals("NhanVienBanVe")) {
					cv = ChucVu.NhanVienBanVe;
				}
				else {
					cv=ChucVu.NhanVienQuanLy;
				}
				TaiKhoan tk = new TaiKhoan(rs.getString(9));
				boolean trangThaiXoa = rs.getBoolean(9);
				NhanVien nv= new NhanVien(maNV, tenNV, ngaySinh, phai, sdt, trangThaiLamViec, cv, tk, trangThaiXoa);
				dsnv.add(nv);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dsnv;
	}
	public ArrayList<NhanVien> getNhanVienTheoMaNV(String id) throws SQLException{
		ArrayList<NhanVien> dsnv = new ArrayList<NhanVien>();
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement statement =null;
		try {
		
			String sql ="Select * from NhanVien where maNhanVien=?";
			statement=con.prepareStatement(sql);
			statement.setString(1, id);
			ResultSet rs = statement.executeQuery(sql);
			while(rs.next()) {
				String maNV = rs.getString(1);
				String tenNV = rs.getString(2);
				LocalDate ngaySinh = rs.getDate(3).toLocalDate();
				boolean phai = rs.getBoolean(4);
				String sdt = rs.getString(5);
				boolean trangThaiLamViec = rs.getBoolean(6);
				String chucVu = rs.getString(7);
				ChucVu cv = null;
				if(chucVu.equals("NhanVienBanVe")) {
					cv = ChucVu.NhanVienBanVe;
				}
				else {
					cv=ChucVu.NhanVienQuanLy;
				}
				TaiKhoan tk = new TaiKhoan(rs.getString(9));
				boolean trangThaiXoa = rs.getBoolean(9);
				NhanVien nv= new NhanVien(maNV, tenNV, ngaySinh, phai, sdt, trangThaiLamViec, cv, tk, trangThaiXoa);
				dsnv.add(nv);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			try {
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return dsnv;
	}
	
	public NhanVien getNhanVienTheoTaiKhoan(String tenTK) throws SQLException{
		NhanVien nv = new NhanVien();
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement statement =null;
		try {
		
			String sql ="Select * from NhanVien where tenTaiKhoan=?";
			statement=con.prepareStatement(sql);
			statement.setString(1, tenTK);
			ResultSet rs = statement.executeQuery();
			while(rs.next()) {
			String maNV = rs.getString(1);
			String tenNV = rs.getString(2);
			LocalDate ngaySinh = rs.getDate(3).toLocalDate();
			boolean phai = rs.getBoolean(4);
			String sdt = rs.getString(5);
			boolean trangThaiLamViec = rs.getBoolean(6);
			String chucVu = rs.getString(7);
			ChucVu cv = null;
			if(chucVu.equals("NhanVienBanVe")) {
				cv = ChucVu.NhanVienBanVe;
			}
			else {
				cv=ChucVu.NhanVienQuanLy;
			}
			TaiKhoan tk = new TaiKhoan(rs.getString(9));
			boolean trangThaiXoa = rs.getBoolean(9);
			nv= new NhanVien(maNV, tenNV, ngaySinh, phai, sdt, trangThaiLamViec, cv, tk, trangThaiXoa);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			try {
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return nv;
	}
//	public boolean create(NhanVien nv) {
//		ConnectDB.getInstance();
//		Connection con = ConnectDB.getConnection();
//		PreparedStatement stmt =null;
//		int n=0;
//		try {
//			stmt = con.prepareStatement("insert into "+"NhanVien values(?,?,?,?,?,?,?)");
//			stmt.setString(1, nv.getMaNV());
//			stmt.setString(2, nv.getHoNv());
//			stmt.setString(3, nv.getTenNv());
//			stmt.setInt(4, nv.getTuoi());
//			stmt.setBoolean(5, nv.isPhai());
//			stmt.setDouble(6, nv.getLuong());
//			stmt.setString(7, nv.getpBan().getMaPhong());
//			n=stmt.executeUpdate();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		finally {
//			try {
//				stmt.close();
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
//		return n>0;
//	}
//	public boolean update(NhanVien nv) {
//		ConnectDB.getInstance();
//		Connection con = ConnectDB.getConnection();
//		PreparedStatement stmt =null;
//		int n=0;
//		try {
//			stmt = con.prepareStatement("update NhanVien set hoNV=?,tenNV=?"+"tuoiNV=?,phai=?,luong=?,phongban=?"+"where maNV=?");
//			stmt.setString(1, nv.getMaNV());
//			stmt.setString(2, nv.getHoNv());
//			stmt.setString(3, nv.getTenNv());
//			stmt.setInt(4, nv.getTuoi());
//			stmt.setBoolean(5, nv.isPhai());
//			stmt.setDouble(6, nv.getLuong());
//			stmt.setString(7, nv.getpBan().getMaPhong());
//			n=stmt.executeUpdate();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		finally {
//			try {
//				stmt.close();
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
//		return n>0;
//	}
	
}
