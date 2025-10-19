package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import connectDB.ConnectDB;
import entity.ChuyenTau;
import entity.GaTau;
import entity.GheNgoi_mthanh;
import entity.KhachHang;
import entity.LoaiHanhTrinh;
import entity.LoaiVe;
import entity.Thue;
import entity.Ve;

public class Ve_DAO_mthanh {
	private KhachHang_DAO_mthanh kh_dao;
	private GaTau_DAO ga_dao;
	private ChuyenTau_DAO ct_dao;
	private GheNgoi_DAO_mthanh gn_dao;
//	public ArrayList<Ve> getalltbVe(){
//		kh_dao= new KhachHang_DAO();
//		ga_dao= new GaTau_DAO();
//		ct_dao= new ChuyenTau_DAO();
//		gn_dao= new GheNgoi_DAO();
//		ArrayList<Ve> dsVe = new ArrayList<Ve>();
//		try {
//			ConnectDB.getInstance();
//			Connection con = ConnectDB.getConnection();
//			String sql = "Select * from Ve";
//			Statement statement = con.createStatement();
//			ResultSet rs = statement.executeQuery(sql);
//			while(rs.next()) {
//	                String maVe        = rs.getString("maVe");
//	                String tenVe       = rs.getString("tenVe");
//	                String maCT = rs.getString("maChuyenTau");
//	                ChuyenTau ct     =  ct_dao.getTheoMa(maCT);
//	                if(ct==null) {
//	                	System.out.println("Chuyến tàu mã "+maCT+" không tồn tại trong CSDL");
//	                }
//	                String maGheNgoi = rs.getString("maGheNgoi");
//	                GheNgoi gnCheck = gn_dao.getGheNgoiTheoMa(maGheNgoi);
//	                if(gnCheck==null) {
//	                	System.out.println("Ghế ngồi mã "+maGheNgoi+" không tồn tại trong CSDL");
//	                }
//	                String maGaDi = rs.getString("maGaDi");
//	                GaTau gaDiCheck = ga_dao.getGaTauTheoMa(maGaDi);
//	                if(gaDiCheck==null) {
//	                	System.out.println("Ga đi mã "+maGaDi+" không tồn tại trong CSDL");
//	                }
//	                String maGaDen = rs.getString("maGaDen");
//	                GaTau gaDenCheck = ga_dao.getGaTauTheoMa(maGaDen);
//	                if(gaDenCheck==null) {
//	                	System.out.println("Ga đến mã "+maGaDen+" không tồn tại trong CSDL");
//	                }
//	                LocalDateTime ngayInVe = rs.getTimestamp("ngayInVe").toLocalDateTime();
//	                LoaiHanhTrinh loaiHanhTrinh;
//	                String lht = rs.getString("maLoaiHanhTrinh");
//	                if ("Thuong".equals(lht)) {
//	                    loaiHanhTrinh = LoaiHanhTrinh.Thuong;
//	                } else if ("KhuHoiLuotDi".equals(lht)) {
//	                    loaiHanhTrinh = LoaiHanhTrinh.KhuHoiLuotDi;
//	                } else {
//	                    loaiHanhTrinh = LoaiHanhTrinh.KhuHoiLuotVe;
//	                }
//	                LoaiVe loaiVe;
//	                String lve = rs.getString("maLoaiVe");
//	                if ("ToanVe".equals(lve)) {
//	                    loaiVe = LoaiVe.ToanVe;
//	                } else if ("TreEm".equals(lve)) {
//	                    loaiVe = LoaiVe.TreEm;
//	                } else if ("SinhVien".equals(lve)) {
//	                    loaiVe = LoaiVe.SinhVien;
//	                } else if ("MeVNAH".equals(lve)) {
//	                    loaiVe = LoaiVe.MeVNAH;
//	                } else {
//	                    loaiVe = LoaiVe.NguoiNuocNgoai;
//	                }
//	                
//	                String trangThaiVe     = rs.getString("trangThaiVe");
//	                boolean coPhongChoVip  = rs.getBoolean("coPhongChoVip");
//	                Thue thueApDung         = new Thue(rs.getString("maThueApDung"));
//	                String maKH= rs.getString("maKhachHang");
//	                KhachHang kh = kh_dao.getKhachHangTheoMa(maKH);
//	                if(kh==null) {
//	                	System.out.println("Khách hàng mã "+maKH+" không tồn tại trong CSDL");
//	                }
//	                // Tạo Ve bằng các đối tượng “mã”
//	                Ve ve = new Ve(maVe, tenVe, ct, gnCheck, gaDiCheck, gaDenCheck,
//	                               ngayInVe, loaiHanhTrinh, loaiVe,
//	                               trangThaiVe, coPhongChoVip, thueApDung, kh);
//	                dsVe.add(ve);
//			 }
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return dsVe;
//	}
	// Trong file Ve_DAO.java
//	public ArrayList<Ve> getalltbVe() {
//	    ArrayList<Ve> dsVe = new ArrayList<>();
//	    // Lớp nội bộ VeRaw không thay đổi
//	    class VeRaw {
//	        String maVe, tenVe, maCT, maGheNgoi, maGaDi, maGaDen, trangThaiVe, maThue, maKH;
//	        String maLoaiHanhTrinh, maLoaiVe;
//	        LocalDateTime ngayInVe;
//	        boolean coPhongChoVip;
//	    }
//	    List<VeRaw> raws = new ArrayList<>();
//	    
//	    try {
//	        final Connection con = ConnectDB.getConnection();
//	        String sql = "SELECT maVe, tenVe, maChuyenTau, maGheNgoi, maGaDi, maGaDen, " +
//	                     "ngayInVe, maLoaiHanhTrinh, maLoaiVe, trangThaiVe, coPhongChoVip, maThueApDung, maKhachHang " +
//	                     "FROM Ve";
//	        
//	        try (PreparedStatement ps = con.prepareStatement(sql);
//	             ResultSet rs = ps.executeQuery()) {
//	            while (rs.next()) {
//	                VeRaw r = new VeRaw();
//	                // ... (phần đọc dữ liệu thô không thay đổi) ...
//	                r.maVe = rs.getString("maVe");
//	                r.tenVe = rs.getNString("tenVe");
//	                r.maCT = rs.getString("maChuyenTau");
//	                r.maGheNgoi = rs.getString("maGheNgoi");
//	                r.maGaDi = rs.getString("maGaDi");
//	                r.maGaDen = rs.getString("maGaDen");
//	                Timestamp t = rs.getTimestamp("ngayInVe");
//	                r.ngayInVe = (t != null) ? t.toLocalDateTime() : null;
//	                r.maLoaiHanhTrinh = rs.getString("maLoaiHanhTrinh");
//	                r.maLoaiVe = rs.getString("maLoaiVe");
//	                r.trangThaiVe = rs.getNString("trangThaiVe");
//	                r.coPhongChoVip = rs.getBoolean("coPhongChoVip");
//	                r.maThue = rs.getString("maThueApDung");
//	                r.maKH = rs.getString("maKhachHang");
//	                raws.add(r);
//	            }
//	        }
//
//	        // --- CÁC THAY ĐỔI BẮT ĐẦU TỪ ĐÂY ---
//
//	        // 1. Khởi tạo LoaiVe_DAO
//	        LoaiVe_DAO loaiVeDAO = new LoaiVe_DAO();
//	        KhachHang_DAO khDao = new KhachHang_DAO();
//	        GaTau_DAO gaDao = new GaTau_DAO();
//	        ChuyenTau_DAO ctDao = new ChuyenTau_DAO();
//	        GheNgoi_DAO gnDao = new GheNgoi_DAO();
//	        Thue_DAO thueDao = new Thue_DAO();
//
//	        // 2. Thêm cache cho LoaiVe
//	        Map<String, LoaiVe> cacheLoaiVe = new HashMap<>();
//	        Map<String, ChuyenTau> cacheCT = new HashMap<>();
//	        Map<String, GheNgoi> cacheGN = new HashMap<>();
//	        Map<String, GaTau> cacheGa = new HashMap<>();
//	        Map<String, KhachHang> cacheKH = new HashMap<>();
//	        Map<String, Thue> cacheThue = new HashMap<>();
//
//	     // Bên trong phương thức getalltbVe()
//
//	        for (VeRaw r : raws) {
//	            // Gọi các phương thức DAO đã được sửa (không cần truyền 'con')
//	            // Mỗi lời gọi được đặt trong try-catch để xử lý SQLException nếu có
//	            
//	            ChuyenTau ct = (r.maCT != null) ? cacheCT.computeIfAbsent(r.maCT, k -> {
//	                try { return ctDao.getChuyenTauTheoMa(k); } 
//	                catch (SQLException e) { e.printStackTrace(); return null; }
//	            }) : null;
//
//	            GheNgoi ghe = (r.maGheNgoi != null) ? cacheGN.computeIfAbsent(r.maGheNgoi, k -> {
//	                try { return gnDao.getGheNgoiTheoMa(k); } 
//	                catch (SQLException e) { e.printStackTrace(); return null; }
//	            }) : null;
//
//	            GaTau gaDi = (r.maGaDi != null) ? cacheGa.computeIfAbsent(r.maGaDi, k -> {
//	                try { return gaDao.getGaTauTheoMa(k); } 
//	                catch (SQLException e) { e.printStackTrace(); return null; }
//	            }) : null;
//
//	            GaTau gaDen = (r.maGaDen != null) ? cacheGa.computeIfAbsent(r.maGaDen, k -> {
//	                try { return gaDao.getGaTauTheoMa(k); } 
//	                catch (SQLException e) { e.printStackTrace(); return null; }
//	            }) : null;
//
//	            Thue thue = (r.maThue != null) ? cacheThue.computeIfAbsent(r.maThue, k -> {
//	                try { return thueDao.getThueTheoMa(k); } 
//	                catch (SQLException e) { e.printStackTrace(); return null; }
//	            }) : null;
//
//	            KhachHang kh = (r.maKH != null) ? cacheKH.computeIfAbsent(r.maKH, k -> {
//	                try { return khDao.getKhachHangTheoMa(k); } 
//	                catch (SQLException e) { e.printStackTrace(); return null; }
//	            }) : null;
//
//	            LoaiVe loaiVe = (r.maLoaiVe != null) ? cacheLoaiVe.computeIfAbsent(r.maLoaiVe, k -> {
//	                try { return loaiVeDAO.getLoaiVeTheoMa(k); } 
//	                catch (SQLException e) { e.printStackTrace(); return null; }
//	            }) : null;
//	            
//	            // Phần constructor của Ve không thay đổi
//	            Ve ve = new Ve(r.maVe, r.tenVe, null, ct, ghe, gaDi, gaDen,
//	                           r.ngayInVe, r.trangThaiVe, 
//	                           parseLoaiHanhTrinh(r.maLoaiHanhTrinh),
//	                           loaiVe,
//	                           r.coPhongChoVip, thue, kh);
//	            dsVe.add(ve);
//	        }
//	    } catch (SQLException e) {
//	        e.printStackTrace();
//	    }
//	    return dsVe;
//	}

	// 5. Phương thức parseLoaiVe() giờ không còn cần thiết và có thể được xóa đi.

	/**
	 * Phương thức phụ để chuyển đổi mã loại hành trình (String) từ DB sang enum LoaiHanhTrinh.
	 * Phương thức này giữ nguyên vì LoaiHanhTrinh vẫn là enum.
	 */

	
	private LoaiHanhTrinh parseLoaiHanhTrinh(String code) {
	    if (code == null) return null;
	    switch (code.trim()) {
	        case "Thuong":         return LoaiHanhTrinh.Thuong;
	        case "KhuHoiLuotDi":   return LoaiHanhTrinh.KhuHoiLuotDi;
	        case "KhuHoiLuotVe":   return LoaiHanhTrinh.KhuHoiLuotVe;
	        default:               throw new IllegalArgumentException("Unknown maLoaiHanhTrinh: " + code);
	    }
	}
	

//	public Ve timVeTheoMaVe(String maVe) {
//		final String sql =
//			    "SELECT v.maVe, v.tenVe, v.maChuyenTau, v.maGheNgoi, v.maGaDi, v.maGaDen, " +
//			    "       v.ngayInVe, v.maLoaiHanhTrinh, v.maLoaiVe, v.trangThaiVe, v.coPhongChoVip, " +
//			    "       v.maThueApDung, v.maKhachHang, " +
//			    "       ct.maChuyenTau AS chuyen, t.tenTau AS tenTau, " +
//			    "       gn.viTriGhe     AS viTriGhe, " +
//			    "       gadi.tenGaTau   AS tenGaDi, " +
//			    "       gaden.tenGaTau  AS tenGaDen, " +
//			    "       kh.hoTenKhachHang AS hoTenKhachHang, " +
//			    "       kh.soGiayTo           AS soGiayTo, " +
//			    "       kh.soDienThoai    AS sdtKH " +
//			    "FROM Ve v " +
//			    "JOIN ChuyenTau ct ON v.maChuyenTau = ct.maChuyenTau " +
//			    "LEFT JOIN Tau t    ON ct.maTau      = t.maTau " +   // có thể null
//			    "JOIN GheNgoi gn   ON v.maGheNgoi   = gn.maGheNgoi " +
//			    "JOIN GaTau  gadi  ON v.maGaDi      = gadi.maGaTau " +
//			    "JOIN GaTau  gaden ON v.maGaDen     = gaden.maGaTau " +
//			    "LEFT JOIN KhachHang kh ON v.maKhachHang = kh.maKhachHang"+
//		        "WHERE v.maVe = ?";
//	    try {
//	        ConnectDB.getInstance();
//	        try (Connection con = ConnectDB.getConnection();
//	             java.sql.PreparedStatement ps = con.prepareStatement(sql)) {
//	            ps.setString(1, maVe);
//	            try (ResultSet rs = ps.executeQuery()) {
//	                if (!rs.next()) return null;
//
//	                // --- Lấy dữ liệu và tạo Ve ---
//	                String _maVe   = rs.getString("maVe");
//	                String tenVe   = rs.getString("tenVe");
//	                ChuyenTau ct   = new ChuyenTau(rs.getString("maChuyenTau"));
//	                GheNgoi gn     = new GheNgoi(rs.getString("maGheNgoi"));
//	                GaTau gaDi     = new GaTau(rs.getString("maGaDi"));
//	                GaTau gaDen    = new GaTau(rs.getString("maGaDen"));
//	                java.time.LocalDateTime ngayInVe =
//	                        rs.getTimestamp("ngayInVe").toLocalDateTime();
//
//	                // LoaiHanhTrinh
//	                String lht = rs.getString("maLoaiHanhTrinh");
//	                entity.LoaiHanhTrinh loaiHanhTrinh;
//	                if ("Thuong".equals(lht)) {
//	                    loaiHanhTrinh = entity.LoaiHanhTrinh.Thuong;
//	                } else if ("KhuHoiLuotDi".equals(lht)) {
//	                    loaiHanhTrinh = entity.LoaiHanhTrinh.KhuHoiLuotDi;
//	                } else {
//	                    loaiHanhTrinh = entity.LoaiHanhTrinh.KhuHoiLuotVe;
//	                }
//
//	                // LoaiVe
//	                String lve = rs.getString("maLoaiVe");
//	                entity.LoaiVe loaiVe;
//	                if      ("ToanVe".equals(lve))      loaiVe = entity.LoaiVe.ToanVe;
//	                else if ("TreEm".equals(lve))       loaiVe = entity.LoaiVe.TreEm;
//	                else if ("SinhVien".equals(lve))    loaiVe = entity.LoaiVe.SinhVien;
//	                else if ("MeVNAH".equals(lve))      loaiVe = entity.LoaiVe.MeVNAH;
//	                else                                 loaiVe = entity.LoaiVe.NguoiNuocNgoai;
//
//	                String trangThaiVe    = rs.getString("trangThaiVe");
//	                boolean coPhongChoVip = rs.getBoolean("coPhongChoVip");
//	                Thue thueApDung       = new Thue(rs.getString("maThueApDung"));
//	                KhachHang kh          = new KhachHang(rs.getString("maKhachHang"));
//
//	                Ve ve = new Ve(_maVe, tenVe, ct, gn, gaDi, gaDen,
//	                               ngayInVe, loaiHanhTrinh, loaiVe,
//	                               trangThaiVe, coPhongChoVip, thueApDung, kh);
//
//	                // Trường dẫn xuất để hiển thị
//	                String tenTau = rs.getString("tenTau");
//	                String chuyen = rs.getString("chuyen"); // maChuyenTau
//	                ve.setChuyen( (tenTau != null && !tenTau.isBlank()) ? tenTau : chuyen );
//	                ve.setGhe(rs.getString("viTriGhe"));
//	                ve.setTenGaDi(rs.getString("tenGaDi"));
//	                ve.setTenGaDen(rs.getString("tenGaDen"));
//	                ve.setTenKH(rs.getString("hoTenKhachHang"));
//	                return ve;
//	            }
//	        }
//	    } catch (SQLException e) {
//	        e.printStackTrace();
//	    }
//	    return null;
//	}

	
}
