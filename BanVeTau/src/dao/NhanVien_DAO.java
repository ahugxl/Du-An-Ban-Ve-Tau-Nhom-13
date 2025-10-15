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

	public ArrayList<NhanVien> getalltbNhanVien() {
		ArrayList<NhanVien> dsnv = new ArrayList<NhanVien>();
		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();
			String sql = "Select * from NhanVien";
			Statement statement = con.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			while (rs.next()) {
				String maNV = rs.getString(1);
				String tenNV = rs.getString(2);
				LocalDate ngaySinh = rs.getDate(3).toLocalDate();
				boolean phai = rs.getBoolean(4);
				String sdt = rs.getString(5);
				boolean trangThaiLamViec = rs.getBoolean(6);
				String chucVu = rs.getString(7);
				ChucVu cv = null;
				if (chucVu.equals("NhanVienBanVe")) {
					cv = ChucVu.NhanVienBanVe;
				} else {
					cv = ChucVu.NhanVienQuanLy;
				}
				TaiKhoan tk = new TaiKhoan(rs.getString(8));
				
				NhanVien nv = new NhanVien(maNV, tenNV, ngaySinh, phai, sdt, trangThaiLamViec, cv, tk);
				dsnv.add(nv);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dsnv;
	}

	public ArrayList<NhanVien> getNhanVienTheoMaNV(String id) throws SQLException {
		ArrayList<NhanVien> dsnv = new ArrayList<NhanVien>();
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement statement = null;
		try {

			String sql = "Select * from NhanVien where maNhanVien=?";
			statement = con.prepareStatement(sql);
			statement.setString(1, id);
			ResultSet rs = statement.executeQuery(sql);
			while (rs.next()) {
				String maNV = rs.getString(1);
				String tenNV = rs.getString(2);
				LocalDate ngaySinh = rs.getDate(3).toLocalDate();
				boolean phai = rs.getBoolean(4);
				String sdt = rs.getString(5);
				boolean trangThaiLamViec = rs.getBoolean(6);
				String chucVu = rs.getString(7);
				ChucVu cv = null;
				if (chucVu.equals("NhanVienBanVe")) {
					cv = ChucVu.NhanVienBanVe;
				} else {
					cv = ChucVu.NhanVienQuanLy;
				}
				TaiKhoan tk = new TaiKhoan(rs.getString(9));
				
				NhanVien nv = new NhanVien(maNV, tenNV, ngaySinh, phai, sdt, trangThaiLamViec, cv, tk);
				dsnv.add(nv);
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
		return dsnv;
	}

	public NhanVien getNhanVienTheoTaiKhoan(String tenTK) throws SQLException {
		NhanVien nv = new NhanVien();
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement statement = null;
		try {

			String sql = "Select * from NhanVien where tenTaiKhoan=?";
			statement = con.prepareStatement(sql);
			statement.setString(1, tenTK);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				String maNV = rs.getString(1);
				String tenNV = rs.getString(2);
				LocalDate ngaySinh = rs.getDate(3).toLocalDate();
				boolean phai = rs.getBoolean(4);
				String sdt = rs.getString(5);
				boolean trangThaiLamViec = rs.getBoolean(6);
				String chucVu = rs.getString(7);
				ChucVu cv = null;
				if (chucVu.equals("NhanVienBanVe")) {
					cv = ChucVu.NhanVienBanVe;
				} else {
					cv = ChucVu.NhanVienQuanLy;
				}
				TaiKhoan tk = new TaiKhoan(rs.getString(9));
				
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
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

		try {
			Connection con = ConnectDB.getConnection();
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, nv.getMaNhanVien());
			stmt.setString(2, nv.getTenNV());
			stmt.setDate(3, Date.valueOf(nv.getNgaySinh()));
			stmt.setBoolean(4, nv.isGioiTinh());
			stmt.setString(5, nv.getSdt());
			stmt.setString(6, nv.isTrangThaiLamViec() ? "Đang làm việc" : "Đã nghỉ");
			stmt.setString(7, nv.getCv().name());
			stmt.setString(8, nv.getTaiKhoan().getTenTaiKhoan());
			stmt.setBoolean(9, false); // trangThaiXoa mặc định là false

			return stmt.executeUpdate() > 0;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean capNhatNhanVien(NhanVien nv) {
		String sql = "UPDATE NhanVien SET tenNhanVien=?, ngaySinh=?, gioiTinh=?, soDienThoai=?, trangThaiLamViec=?, maChucVu=?, tenTaiKhoan=? "
				+ "WHERE maNhanVien=?";

		try (Connection con = ConnectDB.getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {

			stmt.setString(1, nv.getTenNV());
			stmt.setDate(2, Date.valueOf(nv.getNgaySinh()));
			stmt.setBoolean(3, nv.isGioiTinh());
			stmt.setString(4, nv.getSdt());
			stmt.setString(5, nv.isTrangThaiLamViec() ? "Đang làm việc" : "Đã nghỉ");
			stmt.setString(6, nv.getCv().name());
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

				String cvstr = rs.getString("maChucVu");
				ChucVu cv = ChucVu.valueOf(cvstr);
//				if (cvstr.equals("NhanVienQuanLy"))
//					cv = ChucVu.NhanVienQuanLy;
//				else
//					cv = ChucVu.NhanVienBanVe;
				TaiKhoan tk = new TaiKhoan(rs.getString("tenTaiKhoan"));
				NhanVien nv = new NhanVien(rs.getString("maNhanVien"), rs.getString("tenNhanVien"),
						rs.getDate("ngaySinh").toLocalDate(), rs.getBoolean("gioiTinh"), rs.getString("soDienThoai"),
						rs.getBoolean("trangThaiLamViec"), cv, tk);
				ds.add(nv);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ds;
	}

}
