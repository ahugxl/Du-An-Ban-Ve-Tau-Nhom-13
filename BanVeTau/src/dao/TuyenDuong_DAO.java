package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import connectDB.ConnectDB;
import entity.GaTau;
import entity.TuyenDuong;

public class TuyenDuong_DAO {
	private final GaTau_DAO gaTauDAO = new GaTau_DAO();

    // Lấy toàn bộ tuyến đường
//    public ArrayList<TuyenDuong> getAllTuyenDuong() throws SQLException {
//    	ArrayList<TuyenDuong> ds = new ArrayList<>();
//
//        String sql = "SELECT maTuyenDuong, tenTuyenDuong, gaKhoiHanh, gaKetThuc, thoiGianUocTinh FROM TuyenDuong";
//
//        ConnectDB.getInstance();
//        try (Connection con = ConnectDB.getConnection();
//             PreparedStatement ps = con.prepareStatement(sql);
//             ResultSet rs = ps.executeQuery()) {
//
//            while (rs.next()) {
//                String ma   = rs.getString("maTuyenDuong");
//                String ten  = rs.getNString("tenTuyenDuong");     // NVARCHAR
//                String maKH = rs.getString("gaKhoiHanh");
//                String maKT = rs.getString("gaKetThuc");
//                String tg   = rs.getString("thoiGianUocTinh");
//
//                GaTau gaKH = gaTauDAO.getGaTauTheoMa(maKH); // có thể null nếu DB không khớp
//                GaTau gaKT = gaTauDAO.getGaTauTheoMa(maKT);
//
//                ds.add(new TuyenDuong(ma, ten, gaKH, gaKT, tg));
//            }
//        }
//
//        return ds;
//    }

    // Lấy 1 tuyến đường theo mã
	/**
	 * Tìm một tuyến đường theo mã.
	 * Phương thức này tự quản lý việc kết nối CSDL và gọi các DAO phụ thuộc.
	 * @param maTuyen Mã của tuyến đường cần tìm.
	 * @return Đối tượng TuyenDuong nếu tìm thấy, ngược lại trả về null.
	 * @throws SQLException
	 */
	public TuyenDuong getTuyenDuongTheoMa(String maTuyen) throws SQLException {
	    TuyenDuong tuyenDuong = null;
	    Connection con = ConnectDB.getConnection(); // Tự lấy kết nối ở đầu phương thức
	    GaTau_DAO gaTauDAO = new GaTau_DAO();       // Khởi tạo DAO cần thiết

	    String sql = "SELECT maTuyenDuong, tenTuyenDuong, gaKhoiHanh, gaKetThuc, thoiGianUocTinh " +
	                 "FROM TuyenDuong WHERE maTuyenDuong = ?";

	    try (PreparedStatement ps = con.prepareStatement(sql)) {
	        ps.setString(1, maTuyen);

	        try (ResultSet rs = ps.executeQuery()) {
	            if (rs.next()) {
	                String ma   = rs.getString("maTuyenDuong");
	                String ten  = rs.getNString("tenTuyenDuong");
	                String maKH = rs.getString("gaKhoiHanh");
	                String maKT = rs.getString("gaKetThuc");
	                String tg   = rs.getString("thoiGianUocTinh");

	                // Gọi đến các phương thức DAO đã được sửa (không cần truyền `con`)
	                GaTau gaKH = gaTauDAO.getGaTauTheoMa(maKH);
	                GaTau gaKT = gaTauDAO.getGaTauTheoMa(maKT);

	                // Giả sử TuyenDuong có constructor phù hợp
	                tuyenDuong = new TuyenDuong(ma, ten, gaKH, gaKT, tg);
	            }
	        }
	    }
	    // Khối try-with-resources sẽ tự động đóng PreparedStatement và ResultSet.
	    // Connection sẽ được quản lý bởi lớp ConnectDB.

	    return tuyenDuong;
	}
}
