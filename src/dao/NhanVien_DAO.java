package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import connectDB.ConnectDB;
import entity.ChucVu;
import entity.NhanVien;
import entity.TaiKhoan;

public class NhanVien_DAO {
	public NhanVien_DAO() {

	}

	public ArrayList<NhanVien> getalltbNhanVien() {
		ArrayList<NhanVien> dsnv = new ArrayList<NhanVien>();
		class NhanVienRaw{
			String maNV;
			String tenNV;
			LocalDate ngaySinh;
			boolean phai;
			String sdt;
			String trangThaiLamViec;
			ChucVu chucVu;
			String tenTaiKhoan;
		}
		ArrayList<NhanVienRaw> dsnvRaw = new ArrayList<NhanVienRaw>();
		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();
			String sql = "Select * from NhanVien nv join ChucVu cv on nv.maChucVu = cv.maChucVu where trangthaixoa = 0";
			try (
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery()){
			while (rs.next()) {
				NhanVienRaw nvr = new NhanVienRaw();
				nvr.maNV = rs.getString(1);
				nvr.tenNV = rs.getString(2);
				nvr.ngaySinh  = rs.getDate(3).toLocalDate();
				nvr.phai= rs.getBoolean(4);
				nvr.sdt  = rs.getString(5);
				nvr.trangThaiLamViec = rs.getString(6);
				ChucVu cv = new ChucVu(rs.getString(7),rs.getString("tenChucVu"));
				nvr.chucVu = cv;
				nvr.tenTaiKhoan = rs.getString(8);
				dsnvRaw.add(nvr);
			}
		}
			TaiKhoan_DAO tk_dao = new TaiKhoan_DAO();
			Map<String, TaiKhoan> mapTenTK_TK = new HashMap<>();
			for(NhanVienRaw nvr : dsnvRaw) {
				TaiKhoan tk =null;
				if(nvr.tenTaiKhoan!=null) {
					tk = mapTenTK_TK.computeIfAbsent(nvr.tenTaiKhoan, k -> {
						try {
							return tk_dao.getTaiKhoanTheoTenTaiKhoan(k,con);
						} catch (SQLException e) {
							e.printStackTrace();
							return null;
						}
					});
					if(tk==null) 
						System.out.println("Lỗi: Không tìm thấy tài khoản với tên: " + nvr.tenTaiKhoan + " cho nhân viên: " + nvr.maNV);
					
					NhanVien nv = new NhanVien(nvr.maNV, nvr.tenNV, nvr.ngaySinh, nvr.phai, nvr.sdt, nvr.trangThaiLamViec, nvr.chucVu, tk);
					dsnv.add(nv);
				}
			}
			}catch(SQLException e) {
				e.printStackTrace();
			}
			return dsnv;
		
	}

//	public ArrayList<NhanVien> getalltbNhanVien() {
//		ArrayList<NhanVien> dsnv = new ArrayList<NhanVien>();
//		try {
//			ConnectDB.getInstance();
//			Connection con = ConnectDB.getConnection();
//			String sql = "Select * from NhanVien nv join ChucVu cv on nv.maChucVu = cv.maChucVu where trangthaixoa = 0";
//			Statement statement = con.createStatement();
//			ResultSet rs = statement.executeQuery(sql);
//			while (rs.next()) {
//				String maNV = rs.getString(1);
//				String tenNV = rs.getString(2);
//				LocalDate ngaySinh = rs.getDate(3).toLocalDate();
//				boolean phai = rs.getBoolean(4);
//				String sdt = rs.getString(5);
//				String trangThaiLamViec = rs.getString(6);
//				ChucVu cv = new ChucVu(rs.getString(7),rs.getString("tenChucVu"));
//				TaiKhoan tk = new TaiKhoan(rs.getString(8));
//				NhanVien nv = new NhanVien(maNV, tenNV, ngaySinh, phai, sdt, trangThaiLamViec, cv, tk);
//				dsnv.add(nv);
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return dsnv;
//	}


	public NhanVien getNhanVienTheoTaiKhoan(String tenTK) throws SQLException {
		NhanVien nv = new NhanVien();
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement statement = null;
		try {

//			String sql = "Select * from NhanVien where tenTaiKhoan=?";
			String sql = """
				    SELECT *
				    FROM NhanVien nv
				    JOIN ChucVu cv ON nv.maChucVu = cv.maChucVu
				    WHERE nv.trangThaiXoa = 0
				      AND nv.tenTaiKhoan = ?
				""";
			statement = con.prepareStatement(sql);
			statement.setString(1, tenTK);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				String maNV = rs.getString(1);
				String tenNV = rs.getString(2);
				LocalDate ngaySinh = rs.getDate(3).toLocalDate();
				boolean phai = rs.getBoolean(4);
				String sdt = rs.getString(5);
				String trangThaiLamViec = rs.getString(6);
				TaiKhoan tk = new TaiKhoan(rs.getString(8));
				ChucVu cv = new ChucVu(rs.getString(7),rs.getString("tenChucVu"));
				nv = new NhanVien(maNV, tenNV, ngaySinh, phai, sdt, trangThaiLamViec, cv, tk);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return nv;
	}
	public boolean themNhanVien(NhanVien nv) {
		String sql = "INSERT INTO NhanVien (maNhanVien, tenNhanVien, ngaySinh, gioiTinh, soDienThoai, trangThaiLamViec, maChucVu, tenTaiKhoan, trangThaiXoa) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, 0)";

		try {
			Connection con = ConnectDB.getConnection();
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, nv.getMaNhanVien());
			stmt.setString(2, nv.getTenNV());
			stmt.setDate(3, Date.valueOf(nv.getNgaySinh()));
			stmt.setBoolean(4, nv.isGioiTinh());
			stmt.setString(5, nv.getSdt());
			stmt.setString(6, nv.getTrangThaiLamViec());
			stmt.setString(7, nv.getCv().getMaChucVu());
			stmt.setString(8, nv.getTaiKhoan().getTenTaiKhoan());
			return stmt.executeUpdate() > 0;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean xoaNhanVien(NhanVien nv) {
		String sql = "update NhanVien set trangThaiXoa = 1 where maNhanVien = ?";
		try {
			Connection con = ConnectDB.getConnection();
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, nv.getMaNhanVien());
			return stmt.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean capNhatNhanVien(NhanVien nv) {
		String sql = "UPDATE NhanVien SET tenNhanVien=?, ngaySinh=?, gioiTinh=?, soDienThoai=?, trangThaiLamViec=?, maChucVu=?, tenTaiKhoan=? "
				+ "WHERE maNhanVien=?";

		try {
			Connection con = ConnectDB.getConnection();
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, nv.getTenNV());
			stmt.setDate(2, Date.valueOf(nv.getNgaySinh()));
			stmt.setBoolean(3, nv.isGioiTinh());
			stmt.setString(4, nv.getSdt());
			stmt.setString(5, nv.getTrangThaiLamViec());
			stmt.setString(6, nv.getCv().getMaChucVu());
			stmt.setString(7, nv.getTaiKhoan().getTenTaiKhoan());
			stmt.setString(8, nv.getMaNhanVien());

			return stmt.executeUpdate() > 0;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	public ArrayList<NhanVien> tim(String tuKhoa) {
		ArrayList<NhanVien> ds = new ArrayList<NhanVien>();
		String sql = "SELECT * FROM NhanVien WHERE (maNhanVien like ? or tenNhanVien like ? or soDienThoai like ?) AND trangThaiXoa = 0";

		try {
			Connection con = ConnectDB.getConnection();
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, "%" + tuKhoa + "%");
			stmt.setString(2, "%" + tuKhoa + "%");
			stmt.setString(3, "%" + tuKhoa + "%");
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				ChucVu cv= new ChucVu(rs.getString(7));
				TaiKhoan tk = new TaiKhoan(rs.getString(8));
				NhanVien nv = new NhanVien(rs.getString(1), rs.getString(2),
						rs.getDate(3).toLocalDate(), rs.getBoolean(4), rs.getString(5),
						rs.getString(6), cv, tk);
				ds.add(nv);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ds;
	}

}
