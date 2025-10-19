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
	

		public KhachHang getKhachHangTheoMa(String ma) throws SQLException {
		    KhachHang khachHang = null;
		    Connection con = ConnectDB.getConnection(); // Tự lấy kết nối ở đầu phương thức
		    String sql = "SELECT maKhachHang, hoTenKhachHang, soGiayTo, ngaySinh, soDienThoai, gioiTinh " +
		                 "FROM KhachHang WHERE maKhachHang = ?";

		    try (PreparedStatement ps = con.prepareStatement(sql)) {

		        ps.setString(1, ma);
		        try (ResultSet rs = ps.executeQuery()) {
		            if (rs.next()) {
		                String maKH       = rs.getString("maKhachHang");
		                String hoTen      = rs.getNString("hoTenKhachHang");
		                String soGiayTo   = rs.getString("soGiayTo");
		                java.sql.Date d   = rs.getDate("ngaySinh");
		                LocalDate ngaySinh = (d != null) ? d.toLocalDate() : null;
		                String soDT       = rs.getString("soDienThoai");
		                boolean gioiTinh  = rs.getBoolean("gioiTinh");

		                khachHang = new KhachHang(maKH, hoTen, soGiayTo, ngaySinh, soDT, gioiTinh);
		            }
		        }
		    }
		    // Khối try-with-resources sẽ tự động đóng PreparedStatement và ResultSet.
		    // Lớp ConnectDB sẽ quản lý việc đóng Connection.
		    
		    return khachHang; // Trả về đối tượng khách hàng hoặc null nếu không tìm thấy
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
