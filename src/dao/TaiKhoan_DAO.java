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
		String sql = "SELECT tenTaiKhoan, matKhau, email " + "FROM TaiKhoan WHERE tenTaiKhoan = ?";

		try (PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setString(1, tenTK);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					String ten = rs.getString("tenTaiKhoan");
					String mk = rs.getString("matKhau");
					String email = rs.getString("email");

					return new TaiKhoan(ten, mk, email);
				}
			}
		}
		return null;
	}

	 public TaiKhoan getTaiKhoanTheoTen(String username) throws SQLException {
	        TaiKhoan tk = null;
	        Connection con = null;
	        PreparedStatement stmt = null;
	        ResultSet rs = null;
	        
	        try {
	            ConnectDB.getInstance();
	            con = ConnectDB.getConnection();
	            String sql = "SELECT * FROM TaiKhoan WHERE tenTaiKhoan = ?";
	            stmt = con.prepareStatement(sql);
	            stmt.setString(1, username);
	            rs = stmt.executeQuery();
	            
	            if (rs.next()) {
	                tk = new TaiKhoan(
	                    rs.getString("tenTaiKhoan"),
	                    rs.getString("matKhau"),
	                    rs.getString("email")
	                );
	            }
	        } finally {
	            if (rs != null) rs.close();
	            if (stmt != null) stmt.close();
	        }
	        
	        return tk;
	    }

	public ArrayList<TaiKhoan> getAllTaiKhoan() {
		ArrayList<TaiKhoan> ds = new ArrayList<TaiKhoan>();
		String sql = "select * from TaiKhoan where trangThaiXoa = 0";
		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				TaiKhoan tk = new TaiKhoan(rs.getString(1), rs.getString(2), rs.getString(3));
				ds.add(tk);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ds;
	}

	public boolean themTaiKhoan(TaiKhoan tk) {
		String sql = "insert into TaiKhoan (tenTaiKhoan, matKhau, email) values (?, ?, ?)";
		try {
			Connection con = ConnectDB.getConnection();
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, tk.getTenTaiKhoan());
			stmt.setString(2, tk.getMatKhau());
			stmt.setString(3, tk.getEmail());
			return stmt.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean capNhatEmail(String tenTaiKhoan, String emailMoi) {
		String sql = "update TaiKhoan set email = ? where tenTaiKhoan = ?";
		try {
			Connection con = ConnectDB.getConnection();
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, emailMoi);
			stmt.setString(2, tenTaiKhoan);
			return stmt.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean xoaTaiKhoan(String tenTaiKhoan) {
		String sql = "delete from TaiKhoan where tenTaiKhoan = ?";

		try {
			Connection con = ConnectDB.getConnection();
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, tenTaiKhoan);
			return stmt.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean anTaiKhoan(String tenTaiKhoan) {
		String sql = "update TaiKhoan set trangThaiXoa = 1 where tenTaiKhoan = ?";

		try {
			Connection con = ConnectDB.getConnection();
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, tenTaiKhoan);
			return stmt.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public ArrayList<TaiKhoan> timKiem(String tuKhoa) {
		ArrayList<TaiKhoan> ds = new ArrayList<TaiKhoan>();
		String sql = "select * from TaiKhoan where tenTaiKhoan like ? or email like ?";

		try {
			Connection con = ConnectDB.getConnection();
			PreparedStatement stmt = con.prepareStatement(sql);

			String keyword = "%" + tuKhoa + "%";
			stmt.setString(1, keyword);
			stmt.setString(2, keyword);

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				TaiKhoan tk = new TaiKhoan(rs.getString("tenTaiKhoan"), rs.getString("matKhau"), rs.getString("email"));
				ds.add(tk);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ds;
	}

	public String getEmail(String tenTaiKhoan) {
		String sql = "select email from TaiKhoan where tenTaiKhoan = ?";
		try {
			Connection con = ConnectDB.getConnection();
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, tenTaiKhoan);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return rs.getString(3);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

//	public boolean resetMatKhau(String tenTaiKhoan) {
//		String sql = "update TaiKhoan set matKhau = 1 where tenTaiKhoan = ?";
//		try {
//			Connection con = ConnectDB.getConnection();
//			PreparedStatement stmt = con.prepareStatement(sql);
//			stmt.setString(1, tenTaiKhoan);
//			return stmt.executeUpdate() > 0;
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return false;
//	}

//	public boolean doiMatKhau(String tenTaiKhoan, String matKhauCu, String matKhauMoi) {
//		String sqlCheck = "select matKhau from TaiKhoan where tenTaiKhoan = ?";
//		String sqlUpdate = "update TaiKhoan set matKhau = ? where tenTaiKhoan = ?";
//
//		try {
//			Connection con = ConnectDB.getConnection();
//
//			// Kiểm tra mật khẩu cũ
//			PreparedStatement stmtCheck = con.prepareStatement(sqlCheck);
//			stmtCheck.setString(1, tenTaiKhoan);
//			ResultSet rs = stmtCheck.executeQuery();
//
//			if (rs.next()) {
//				String currentPassword = rs.getString("matKhau");
//				// Nếu mật khẩu cũ không khớp
//				if (!currentPassword.equals(matKhauCu)) {
//					return false;
//				}
//				// Cập nhật mật khẩu mới
//				PreparedStatement stmtUpdate = con.prepareStatement(sqlUpdate);
//				stmtUpdate.setString(1, matKhauMoi);
//				stmtUpdate.setString(2, tenTaiKhoan);
//				return stmtUpdate.executeUpdate() > 0;
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return false;
//	}
}
