package entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Ve {
	
	private String maVe;
	private String tenVe;
	private ChuyenTau chuyenTau;
	private GheNgoi_mthanh gheNgoi;
	private GaTau gaDi;
	private GaTau gaDen;
	private LocalDateTime ngayInVe;
	private LoaiHanhTrinh loaiHanhTrinh;
	private LoaiVe loaiVe;
	private String trangThaiVe;
	private boolean coPhongChoVip;
	private Thue thueApDung;
	private KhachHang khachHang; 
	private List<ChiTietKhuyenMai> danhSachChiTietKhuyenMai;
	private static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
	public Ve() {
		this.danhSachChiTietKhuyenMai = new ArrayList<>();
	}

	

	
	 public Ve(String maVe, String tenVe, ChuyenTau chuyenTau, GheNgoi_mthanh gheNgoi, GaTau gaDi, GaTau gaDen,
			LocalDateTime ngayInVe, LoaiHanhTrinh loaiHanhTrinh, LoaiVe loaiVe, String trangThaiVe,
			boolean coPhongChoVip, Thue thueApDung, KhachHang khachHang,
			List<ChiTietKhuyenMai> danhSachChiTietKhuyenMai) {
		super();
		this.maVe = maVe;
		this.tenVe = tenVe;
		this.chuyenTau = chuyenTau;
		this.gheNgoi = gheNgoi;
		this.gaDi = gaDi;
		this.gaDen = gaDen;
		this.ngayInVe = ngayInVe;
		this.loaiHanhTrinh = loaiHanhTrinh;
		this.loaiVe = loaiVe;
		this.trangThaiVe = trangThaiVe;
		this.coPhongChoVip = coPhongChoVip;
		this.thueApDung = thueApDung;
		this.khachHang = khachHang;
		this.danhSachChiTietKhuyenMai = danhSachChiTietKhuyenMai;
	}




	 /* ===== Getter phục vụ TableView (PropertyValueFactory keys) ===== */
    // clTenChuyen -> "chuyen"
    public String getChuyen() {
        // bạn có thể đổi hiển thị theo ý muốn (mã chuyến / tên tuyến / ...).
        return (chuyenTau != null) ? chuyenTau.getMaChuyenTau() : null;
    }

    // clTenGhe -> "ghe"
    public String getGhe() {
    	return (gheNgoi != null) ? "Ghế " + gheNgoi.getViTriGhe() : null;
    }

    // clGaDi -> "tenGaDi"
    public String getTenGaDi() {
        return (gaDi != null) ? gaDi.getTenGaTau() : null;
    }

    // clGaDen -> "tenGaDen"
    public String getTenGaDen() {
        return (gaDen != null) ? gaDen.getTenGaTau() : null;
    }

    // clNgayIn -> "ngayInVeStr"
    public String getNgayInVeStr() {
        if (ngayInVe == null) return null;
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return ngayInVe.format(fmt);
    }

    // clLoaiHT -> "loaiHanhTrinhStr"
    public String getLoaiHanhTrinhStr() {
        return (loaiHanhTrinh != null) ? loaiHanhTrinh.getDisplayName() : null;
    }

    // clTinhTrang -> "trangThaiVeStr"
    public String getTrangThaiVeStr() {
        return trangThaiVe; // nếu muốn hiển thị khác, bạn map lại ở đây
    }

    // clPhongCho -> "coPhongChoVipStr"
    public String getCoPhongChoVipStr() {
        return coPhongChoVip ? "Có" : "Không";
    }

    // clTenKH -> "tenKhachHang"
    public String getTenKhachHang() {
        return (khachHang != null) ? khachHang.getHoTenKhachHang() : null;
    }
    ///=========================================//
	public KhachHang getKhachHang() {
		return khachHang;
	}
	public void setKhachHang(KhachHang khachHang) {
		this.khachHang = khachHang;
	}
	public String getMaVe() {
		return maVe;
	}

	public String getTenVe() {
		return tenVe;
	}

	public ChuyenTau getChuyenTau() {
		return chuyenTau;
	}

	public GheNgoi_mthanh getGheNgoi() {
		return gheNgoi;
	}

	public GaTau getGaDi() {
		return gaDi;
	}

	public GaTau getGaDen() {
		return gaDen;
	}

	public LocalDateTime getNgayInVe() {
		return ngayInVe;
	}

	public LoaiHanhTrinh getLoaiHanhTrinh() {
		return loaiHanhTrinh;
	}

	public LoaiVe getLoaiVe() {
		return loaiVe;
	}

	public String getTrangThaiVe() {
		return trangThaiVe;
	}

	public boolean isCoPhongChoVip() {
		return coPhongChoVip;
	}

	public Thue getThueApDung() {
		return thueApDung;
	}

	public void setMaVe(String maVe) {
		this.maVe = maVe;
	}

	public void setTenVe(String tenVe) {
		this.tenVe = tenVe;
	}

	public void setChuyenTau(ChuyenTau chuyenTau) {
		this.chuyenTau = chuyenTau;
	}

	public void setGheNgoi(GheNgoi_mthanh gheNgoi) {
		this.gheNgoi = gheNgoi;
	}

	public void setGaDi(GaTau gaDi) {
		this.gaDi = gaDi;
	}

	public void setGaDen(GaTau gaDen) {
		this.gaDen = gaDen;
	}

	public void setNgayInVe(LocalDateTime ngayInVe) {
		this.ngayInVe = ngayInVe;
	}

	public void setLoaiHanhTrinh(LoaiHanhTrinh loaiHanhTrinh) {
		this.loaiHanhTrinh = loaiHanhTrinh;
	}

	public void setLoaiVe(LoaiVe loaiVe) {
		this.loaiVe = loaiVe;
	}

	public void setTrangThaiVe(String trangThaiVe) {
		this.trangThaiVe = trangThaiVe;
	}

	public void setCoPhongChoVip(boolean coPhongChoVip) {
		this.coPhongChoVip = coPhongChoVip;
	}

	public void setThueApDung(Thue thueApDung) {
		this.thueApDung = thueApDung;
	}
	
	public List<ChiTietKhuyenMai> getDanhSachChiTietKhuyenMai() {
		return danhSachChiTietKhuyenMai;
	}




	public void setDanhSachChiTietKhuyenMai(List<ChiTietKhuyenMai> danhSachChiTietKhuyenMai) {
		this.danhSachChiTietKhuyenMai = danhSachChiTietKhuyenMai;
	}

	public double getTienPhongChoVip(boolean coPhongChoVip) {
		if (coPhongChoVip)
			return 20000;
		return 0;
	}


	public double getGiaVeThucTe() {
        int tongKm = 0;
        boolean batDauTinh = false;
        
        ArrayList<ChangTau> dsChang = chuyenTau.getDanhSachChang();

        if (dsChang == null || dsChang.isEmpty()) {
            System.out.println("Lỗi: ChuyenTau không có danh sách chặng để tính toán.");
            return 0; 
        }

        dsChang.sort((c1, c2) -> Integer.compare(c1.getSoThuTu(), c2.getSoThuTu()));

        for (ChangTau chang : dsChang) {
            if (!batDauTinh && chang.getGaDi().getMaGaTau().equals(this.gaDi.getMaGaTau())) {
                batDauTinh = true;
            }
            if (batDauTinh) {
                tongKm += chang.getSoKm();
            }
            if (batDauTinh && chang.getGaDen().getMaGaTau().equals(this.gaDen.getMaGaTau())) {
                break;
            }
        }

        System.out.println("Tổng km tính được: " + tongKm); // KIỂM TRA GIÁ TRỊ NÀY
        
        double donGiaCoBan = chuyenTau.getDonGiaCoBan();
        System.out.println("Đơn giá cơ sở: " + donGiaCoBan); // KIỂM TRA GIÁ TRỊ NÀY
        
        double heSoHangGhe = gheNgoi.getToaTau().getHeSoHangToa();
        ToaTau  tt = gheNgoi.getToaTau();
        System.out.println("Hệ số hạng ghế: " + heSoHangGhe);
        System.out.println("tt: " + tt.toString());
        return (tongKm * donGiaCoBan * heSoHangGhe) + getTienPhongChoVip(coPhongChoVip);
    }

    /**
     * 2. Tính số tiền được Giảm giá.
     * @param ngayDatVe Ngày thực hiện việc đặt vé.
     * @return Số tiền được giảm.
     */
	// Bên trong lớp Ve.java

	/**
	 * Tính tổng số tiền được giảm giá cho vé.
	 * PHIÊN BẢN CUỐI CÙNG: Dùng LoaiVe là class, tách biệt giảm giá đối tượng và khuyến mãi.
	 *
	 * @param ngayDatVe Ngày thực hiện việc đặt vé.
	 * @return Tổng số tiền được giảm.
	 */
	public double getGiamGia(LocalDate ngayDatVe) {
	    double donGia = getGiaVeThucTe();
	    double giaSauKhiGiamPhanTram = donGia;
	    double giamGiaCoDinh = 0.0;

	    // --- 1. ÁP DỤNG GIẢM GIÁ THEO ĐỐI TƯỢNG (Từ class LoaiVe) ---
	    LoaiVe loaiVe = getLoaiVe();
	    
	    // Lấy thẳng tỷ lệ giảm giá từ đối tượng LoaiVe, không cần switch-case
	    double tiLeGiamGiaDoiTuong = loaiVe.getTiLeGiamGia();
	    
	    // Áp dụng giảm giá đối tượng vào giá vé
	    giaSauKhiGiamPhanTram = giaSauKhiGiamPhanTram * (1 - tiLeGiamGiaDoiTuong);

	    // --- 2. ÁP DỤNG CÁC CHƯƠG TRÌNH KHUYẾN MÃI (Bao gồm cả mua vé sớm) ---
	 // Giả định enum LoaiKhuyenMai của bạn có các hằng số là PhanTram và SoTienCoDinh

	    if (this.danhSachChiTietKhuyenMai != null && !this.danhSachChiTietKhuyenMai.isEmpty()) {
	        // 1. Lặp qua danh sách các "ChiTietKhuyenMai"
	        for (ChiTietKhuyenMai chiTiet : this.danhSachChiTietKhuyenMai) {
	            
	            // 2. Từ mỗi "chiTiet", lấy ra đối tượng "KhuyenMai" tương ứng
	            KhuyenMai km = chiTiet.getKhuyenMai();
	            
	            // 3. Kiểm tra loại khuyến mãi và tính toán
	            if (km.getLoaiKhuyenMai() == LoaiKhuyenMai.PhanTram) {
	                // Áp dụng giảm giá % tiếp nối
	                giaSauKhiGiamPhanTram = giaSauKhiGiamPhanTram * (1 - km.getGiaTriKhuyenMai());
	            } 
	            else if (km.getLoaiKhuyenMai() == LoaiKhuyenMai.SoTienCoDinh) {
	                // Cộng dồn các khoản giảm giá cố định
	                giamGiaCoDinh += km.getGiaTriKhuyenMai();
	            }
	        }
	    }

	    // --- 3. TÍNH TOÁN TỔNG SỐ TIỀN GIẢM ---
	    double tongGiamGiaPhanTram = donGia - giaSauKhiGiamPhanTram;
	    return tongGiamGiaPhanTram + giamGiaCoDinh;
	}

    /**
     * 3. Tính Số tiền thuế.
     * @param ngayDatVe Ngày thực hiện việc đặt vé.
     * @return Số tiền thuế VAT 10%.
     */
    public double getSoTienThue(LocalDate ngayDatVe) {
        double giaSauGiam = getGiaVeThucTe() - getGiamGia(ngayDatVe);
        return giaSauGiam * thueApDung.getMucThue();
    }

    /**
     * 4. Tính Giá vé cuối cùng sau thuế.
     * @param ngayDatVe Ngày thực hiện việc đặt vé.
     * @return Tổng số tiền phải thanh toán.
     */
    public double getGiaVeSauThue(LocalDate ngayDatVe) {
        double giaSauGiam = getGiaVeThucTe() - getGiamGia(ngayDatVe);
        return giaSauGiam + getSoTienThue(ngayDatVe);
    }

    // --- Phương thức phụ để xác định tỷ lệ giảm giá ---
 // Bên trong lớp Ve.java

    /**
     * Phương thức phụ để xác định tỷ lệ giảm giá (R).
     * ĐÃ CẬP NHẬT THEO ENUM LOAIVE MỚI.
     */

	@Override
	public int hashCode() {
		return Objects.hash(maVe);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ve other = (Ve) obj;
		return Objects.equals(maVe, other.maVe);
	}

	@Override
	public String toString() {
		return "Ve [maVe=" + maVe + ", tenVe=" + tenVe + ", chuyenTau=" + chuyenTau + ", gheNgoi=" + gheNgoi + ", gaDi="
				+ gaDi + ", gaDen=" + gaDen + ", ngayInVe=" + ngayInVe + ", loaiHanhTrinh=" + loaiHanhTrinh
				+ ", loaiVe=" + loaiVe + ", trangThaiVe=" + trangThaiVe + ", coPhongChoVip=" + coPhongChoVip
				+ ", thueApDung=" + thueApDung + "]";
	}

}
