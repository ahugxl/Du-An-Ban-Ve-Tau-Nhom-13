package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import connectDB.ConnectDB;
import entity.ChuyenTau;
import entity.GaTau;
import entity.GheNgoi;
import entity.KhachHang;
import entity.LoaiHanhTrinh;
import entity.LoaiVe;
import entity.Thue;
import entity.Ve;

public class Ve_DAO {
	public ArrayList<Ve> getalltbVe(){
		ArrayList<Ve> dsVe = new ArrayList<Ve>();
		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();
			final String sql =
				    "SELECT v.maVe, v.tenVe, v.maChuyenTau, v.maGheNgoi, v.maGaDi, v.maGaDen, " +
				    "       v.ngayInVe, v.maLoaiHanhTrinh, v.maLoaiVe, v.trangThaiVe, v.coPhongChoVip, " +
				    "       v.maThueApDung, v.maKhachHang, " +
				    "       ct.maChuyenTau AS chuyen, t.tenTau AS tenTau, " +
				    "       gn.viTriGhe     AS viTriGhe, " +
				    "       gadi.tenGaTau   AS tenGaDi, " +
				    "       gaden.tenGaTau  AS tenGaDen, " +
				    "       kh.hoTenKhachHang AS hoTenKhachHang, " +
				    "       kh.soGiayTo           AS soGiayTo, " +
				    "       kh.soDienThoai    AS sdtKH " +
				    "FROM Ve v " +
				    "JOIN ChuyenTau ct ON v.maChuyenTau = ct.maChuyenTau " +
				    "LEFT JOIN Tau t    ON ct.maTau      = t.maTau " +   // có thể null
				    "JOIN GheNgoi gn   ON v.maGheNgoi   = gn.maGheNgoi " +
				    "JOIN GaTau  gadi  ON v.maGaDi      = gadi.maGaTau " +
				    "JOIN GaTau  gaden ON v.maGaDen     = gaden.maGaTau " +
				    "LEFT JOIN KhachHang kh ON v.maKhachHang = kh.maKhachHang";

			 Statement statement = con.createStatement();
			 ResultSet rs = statement.executeQuery(sql);
			 while(rs.next()) {
				// --- cột chính từ bảng Ve ---
	                String maVe        = rs.getString("maVe");
	                String tenVe       = rs.getString("tenVe");
	                ChuyenTau ct       = new ChuyenTau(rs.getString("maChuyenTau"));
	                GheNgoi gn         = new GheNgoi(rs.getString("maGheNgoi"));
	                GaTau gaDi         = new GaTau(rs.getString("maGaDi"));
	                GaTau gaDen        = new GaTau(rs.getString("maGaDen"));
	                LocalDateTime ngayInVe = rs.getTimestamp("ngayInVe").toLocalDateTime();

	                // Enum LoaiHanhTrinh
	                LoaiHanhTrinh loaiHanhTrinh;
	                String lht = rs.getString("maLoaiHanhTrinh");
	                if ("Thuong".equals(lht)) {
	                    loaiHanhTrinh = LoaiHanhTrinh.Thuong;
	                } else if ("KhuHoiLuotDi".equals(lht)) {
	                    loaiHanhTrinh = LoaiHanhTrinh.KhuHoiLuotDi;
	                } else {
	                    loaiHanhTrinh = LoaiHanhTrinh.KhuHoiLuotVe;
	                }

	                // Enum LoaiVe (sửa bug TreEm trước đây gán nhầm)
	                LoaiVe loaiVe;
	                String lve = rs.getString("maLoaiVe");
	                if ("ToanVe".equals(lve)) {
	                    loaiVe = LoaiVe.ToanVe;
	                } else if ("TreEm".equals(lve)) {
	                    loaiVe = LoaiVe.TreEm;
	                } else if ("SinhVien".equals(lve)) {
	                    loaiVe = LoaiVe.SinhVien;
	                } else if ("MeVNAH".equals(lve)) {
	                    loaiVe = LoaiVe.MeVNAH;
	                } else {
	                    loaiVe = LoaiVe.NguoiNuocNgoai;
	                }

	                String trangThaiVe     = rs.getString("trangThaiVe");
	                boolean coPhongChoVip  = rs.getBoolean("coPhongChoVip");
	                Thue thueApDung         = new Thue(rs.getString("maThueApDung"));
	                KhachHang kh            = new KhachHang(rs.getString("maKhachHang"));

	                // Tạo Ve bằng các đối tượng “mã”
	                Ve ve = new Ve(maVe, tenVe, ct, gn, gaDi, gaDen,
	                               ngayInVe, loaiHanhTrinh, loaiVe,
	                               trangThaiVe, coPhongChoVip, thueApDung, kh);

	                // --- Trường dẫn xuất cho hiển thị ---
	                String tenTau = rs.getString("tenTau");       // có thể null
	                String chuyen = rs.getString("chuyen");       // luôn có (maChuyenTau)
	                ve.setChuyen((tenTau != null && !tenTau.isBlank()) ? tenTau : chuyen);

	                ve.setGhe(rs.getString("viTriGhe"));          // vị trí ghế (nvarchar)

	                ve.setTenGaDi(rs.getString("tenGaDi"));
	                ve.setTenGaDen(rs.getString("tenGaDen"));
	                ve.setTenKH(rs.getString("hoTenKhachHang"));

	                dsVe.add(ve);
			 }
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dsVe;
	}
	public Ve timVeTheoMaVe(String maVe) {
		final String sql =
			    "SELECT v.maVe, v.tenVe, v.maChuyenTau, v.maGheNgoi, v.maGaDi, v.maGaDen, " +
			    "       v.ngayInVe, v.maLoaiHanhTrinh, v.maLoaiVe, v.trangThaiVe, v.coPhongChoVip, " +
			    "       v.maThueApDung, v.maKhachHang, " +
			    "       ct.maChuyenTau AS chuyen, t.tenTau AS tenTau, " +
			    "       gn.viTriGhe     AS viTriGhe, " +
			    "       gadi.tenGaTau   AS tenGaDi, " +
			    "       gaden.tenGaTau  AS tenGaDen, " +
			    "       kh.hoTenKhachHang AS hoTenKhachHang, " +
			    "       kh.soGiayTo           AS soGiayTo, " +
			    "       kh.soDienThoai    AS sdtKH " +
			    "FROM Ve v " +
			    "JOIN ChuyenTau ct ON v.maChuyenTau = ct.maChuyenTau " +
			    "LEFT JOIN Tau t    ON ct.maTau      = t.maTau " +   // có thể null
			    "JOIN GheNgoi gn   ON v.maGheNgoi   = gn.maGheNgoi " +
			    "JOIN GaTau  gadi  ON v.maGaDi      = gadi.maGaTau " +
			    "JOIN GaTau  gaden ON v.maGaDen     = gaden.maGaTau " +
			    "LEFT JOIN KhachHang kh ON v.maKhachHang = kh.maKhachHang"+
		        "WHERE v.maVe = ?";
	    try {
	        ConnectDB.getInstance();
	        try (Connection con = ConnectDB.getConnection();
	             java.sql.PreparedStatement ps = con.prepareStatement(sql)) {
	            ps.setString(1, maVe);
	            try (ResultSet rs = ps.executeQuery()) {
	                if (!rs.next()) return null;

	                // --- Lấy dữ liệu và tạo Ve ---
	                String _maVe   = rs.getString("maVe");
	                String tenVe   = rs.getString("tenVe");
	                ChuyenTau ct   = new ChuyenTau(rs.getString("maChuyenTau"));
	                GheNgoi gn     = new GheNgoi(rs.getString("maGheNgoi"));
	                GaTau gaDi     = new GaTau(rs.getString("maGaDi"));
	                GaTau gaDen    = new GaTau(rs.getString("maGaDen"));
	                java.time.LocalDateTime ngayInVe =
	                        rs.getTimestamp("ngayInVe").toLocalDateTime();

	                // LoaiHanhTrinh
	                String lht = rs.getString("maLoaiHanhTrinh");
	                entity.LoaiHanhTrinh loaiHanhTrinh;
	                if ("Thuong".equals(lht)) {
	                    loaiHanhTrinh = entity.LoaiHanhTrinh.Thuong;
	                } else if ("KhuHoiLuotDi".equals(lht)) {
	                    loaiHanhTrinh = entity.LoaiHanhTrinh.KhuHoiLuotDi;
	                } else {
	                    loaiHanhTrinh = entity.LoaiHanhTrinh.KhuHoiLuotVe;
	                }

	                // LoaiVe
	                String lve = rs.getString("maLoaiVe");
	                entity.LoaiVe loaiVe;
	                if      ("ToanVe".equals(lve))      loaiVe = entity.LoaiVe.ToanVe;
	                else if ("TreEm".equals(lve))       loaiVe = entity.LoaiVe.TreEm;
	                else if ("SinhVien".equals(lve))    loaiVe = entity.LoaiVe.SinhVien;
	                else if ("MeVNAH".equals(lve))      loaiVe = entity.LoaiVe.MeVNAH;
	                else                                 loaiVe = entity.LoaiVe.NguoiNuocNgoai;

	                String trangThaiVe    = rs.getString("trangThaiVe");
	                boolean coPhongChoVip = rs.getBoolean("coPhongChoVip");
	                Thue thueApDung       = new Thue(rs.getString("maThueApDung"));
	                KhachHang kh          = new KhachHang(rs.getString("maKhachHang"));

	                Ve ve = new Ve(_maVe, tenVe, ct, gn, gaDi, gaDen,
	                               ngayInVe, loaiHanhTrinh, loaiVe,
	                               trangThaiVe, coPhongChoVip, thueApDung, kh);

	                // Trường dẫn xuất để hiển thị
	                String tenTau = rs.getString("tenTau");
	                String chuyen = rs.getString("chuyen"); // maChuyenTau
	                ve.setChuyen( (tenTau != null && !tenTau.isBlank()) ? tenTau : chuyen );
	                ve.setGhe(rs.getString("viTriGhe"));
	                ve.setTenGaDi(rs.getString("tenGaDi"));
	                ve.setTenGaDen(rs.getString("tenGaDen"));
	                ve.setTenKH(rs.getString("hoTenKhachHang"));
	                return ve;
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return null;
	}

	
}
