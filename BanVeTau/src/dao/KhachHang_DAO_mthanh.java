package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

import connectDB.ConnectDB;
import entity.KhachHang;

public class KhachHang_DAO_mthanh {

	public KhachHang_DAO_mthanh() {
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
	
	public KhachHang getKhachHangTheoSDT(String sdt) throws SQLException {
        KhachHang kh = null;
        Connection con = ConnectDB.getConnection();
        String sql = "SELECT * FROM KhachHang WHERE soDienThoai = ?";
        
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, sdt); // Gán tham số một cách an toàn
            
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                kh = new KhachHang();
                kh.setMaKhachHang(rs.getString("maKhachHang"));
                kh.setHoTenKhachHang(rs.getNString("hoTenKhachHang"));
                kh.setSoGiayTo(rs.getString("soGiayTo"));
                kh.setNgaySinh(rs.getDate("ngaySinh").toLocalDate());
                kh.setSoDienThoai(rs.getString("soDienThoai"));
                kh.setGioiTinh(rs.getBoolean("gioiTinh"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return kh; // Sẽ là null nếu không có dòng nào trong ResultSet
    }
}
