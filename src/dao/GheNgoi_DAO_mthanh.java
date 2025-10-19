package dao;

import entity.*;
import connectDB.ConnectDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GheNgoi_DAO_mthanh {
	private final ToaTau_DAO toaTauDAO = new ToaTau_DAO();
	// Trong file GheNgoi_DAO.java

	public List<GheNgoi_mthanh> getGheByToa(String maToa) throws SQLException {
	    List<GheNgoi_mthanh> dsGhe = new ArrayList<>();
	    Connection con = ConnectDB.getConnection();

	    // 1. Tải đối tượng ToaTau hoàn chỉnh TRƯỚC TIÊN
	    ToaTau_DAO toaTauDAO = new ToaTau_DAO();
	    ToaTau toa = toaTauDAO.getToaTauTheoMa(maToa);

	    // Nếu không tìm thấy toa, không cần làm gì thêm, trả về danh sách rỗng
	    if (toa == null) {
	        System.err.println("Không tìm thấy Toa Tàu với mã: " + maToa);
	        return dsGhe;
	    }

	    // 2. Bây giờ mới lấy tất cả ghế thuộc về toa đó
	    String sql = "SELECT maGheNgoi, viTriGhe FROM GheNgoi WHERE maToaTau = ?";
	    try (PreparedStatement ps = con.prepareStatement(sql)) {
	        ps.setString(1, maToa);
	        ResultSet rs = ps.executeQuery();

	        while (rs.next()) {
	            // 3. Tạo đối tượng GheNgoi và dùng lại đối tượng `toa` đã hoàn chỉnh ở trên
	            GheNgoi_mthanh ghe = new GheNgoi_mthanh(
	                rs.getString("maGheNgoi"),
	                rs.getInt("viTriGhe"),
	                toa // <-- Dùng đối tượng `toa` đầy đủ thông tin cho tất cả các ghế
	            );
	            dsGhe.add(ghe);
	        }
	    }
	    return dsGhe;
	}
 // Lấy toàn bộ ghế ngồi
 // Trong file GheNgoi_DAO.java

    public ArrayList<GheNgoi_mthanh> getAllGheNgoi() throws SQLException {
        ArrayList<GheNgoi_mthanh> dsGheNgoi = new ArrayList<>();
        
        // Lớp nội bộ để chứa dữ liệu thô từ DB
        class GheNgoiRaw {
            String maGhe, maToaTau;
            int viTri;
            boolean daDat;
        }
        List<GheNgoiRaw> raws = new ArrayList<>();
        
        Connection con = ConnectDB.getConnection();
        String sql = "SELECT maGheNgoi, viTriGhe, maToaTau, daDat FROM GheNgoi";

        // Bước 1: Đọc tất cả dữ liệu thô vào danh sách `raws`
        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                GheNgoiRaw r = new GheNgoiRaw();
                r.maGhe = rs.getString("maGheNgoi");
                r.viTri = rs.getInt("viTriGhe");
                r.maToaTau = rs.getString("maToaTau");
                r.daDat = rs.getBoolean("daDat");
                raws.add(r);
            }
        }

        // Bước 2: Dùng cache để xây dựng đối tượng hoàn chỉnh, tránh N+1 query
        ToaTau_DAO toaTauDAO = new ToaTau_DAO();
        Map<String, ToaTau> cacheToa = new HashMap<>();

        for (GheNgoiRaw r : raws) {
            // Dùng cache.computeIfAbsent để chỉ truy vấn ToaTau một lần duy nhất cho mỗi mã
            ToaTau toa = cacheToa.computeIfAbsent(r.maToaTau, maToa -> {
                try {
                    // Phương thức này chỉ được gọi khi `maToa` chưa có trong cache
                    return toaTauDAO.getToaTauTheoMa(maToa);
                } catch (SQLException e) {
                    e.printStackTrace();
                    return null;
                }
            });

            if (toa != null) {
                GheNgoi_mthanh ghe = new GheNgoi_mthanh(r.maGhe, r.viTri, toa);
                dsGheNgoi.add(ghe);
            }
        }
        
        return dsGheNgoi;
    }

    // Lấy 1 ghế theo mã
    /**
     * Tìm một ghế ngồi theo mã.
     * Phương thức này tự quản lý việc kết nối CSDL và gọi các DAO phụ thuộc.
     * @param maGheNgoi Mã của ghế ngồi cần tìm.
     * @return Đối tượng GheNgoi nếu tìm thấy, ngược lại trả về null.
     * @throws SQLException
     */
 // Trong file GheNgoi_DAO.java

 // Trong file GheNgoi_DAO.java

 // Trong file GheNgoi_DAO.java

    public GheNgoi_mthanh getGheNgoiTheoMa(String maGheNgoi) throws SQLException {
        GheNgoi_mthanh gheNgoi = null;
        Connection con = ConnectDB.getConnection();
        ToaTau_DAO toaTauDAO = new ToaTau_DAO(); // Khởi tạo ToaTau_DAO

        String sql = "SELECT maGheNgoi, viTriGhe, maToaTau FROM GheNgoi WHERE maGheNgoi = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, maGheNgoi);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String maGhe = rs.getString("maGheNgoi");
                    int viTri = rs.getInt("viTriGhe");
                    String maToa = rs.getString("maToaTau");

                    // ĐÂY LÀ DÒNG QUAN TRỌNG NHẤT:
                    // Gọi đến ToaTau_DAO để lấy đối tượng ToaTau đầy đủ thông tin
                    ToaTau toa = toaTauDAO.getToaTauTheoMa(maToa);

                    // Kiểm tra để đảm bảo toa tồn tại
                    if (toa == null) {
                        System.err.println("Lỗi: Không tìm thấy Toa Tàu với mã: " + maToa);
                        return null; 
                    }

                    // Bây giờ, `toa` là một đối tượng hoàn chỉnh
                    gheNgoi = new GheNgoi_mthanh(maGhe, viTri, toa);
                    System.out.println(">>> DEBUG (GheNgoi_DAO): ToaTau nhận được từ ToaTau_DAO: " + toa);
                }
            }
        }
        return gheNgoi;
    }
}
