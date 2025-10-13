package entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Ve {
	
	private String maVe;
	private String tenVe;
	private ChuyenTau chuyenTau;
	private GheNgoi gheNgoi;
	private GaTau gaDi;
	private GaTau gaDen;
	private LocalDateTime ngayInVe;
	private LoaiHanhTrinh loaiHanhTrinh;
	private LoaiVe loaiVe;
	private String trangThaiVe;
	private boolean coPhongChoVip;
	private Thue thueApDung;
	private KhachHang khachHang; 
	private static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
	public Ve() {
	}

	

	public Ve(String maVe, String tenVe, ChuyenTau chuyenTau, GheNgoi gheNgoi, GaTau gaDi, GaTau gaDen,
			LocalDateTime ngayInVe, LoaiHanhTrinh loaiHanhTrinh, LoaiVe loaiVe, String trangThaiVe,
			boolean coPhongChoVip, Thue thueApDung, KhachHang khachHang) {
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

    // clLoaiVe -> "loaiVeStr"
    public String getLoaiVeStr() {
        return (loaiVe != null) ? loaiVe.getDisplayName() : null;
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

	public GheNgoi getGheNgoi() {
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

	public void setGheNgoi(GheNgoi gheNgoi) {
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
