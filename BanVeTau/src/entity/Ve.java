package entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Ve {
	// ===== Trường dẫn xuất chỉ dùng để hiển thị (TableView) =====
	private String chuyen;   // tên chuyến / mã chuyến để hiển thị
	private String ghe;      // vị trí ghế
	private String tenGaDi;  // tên ga đi
	private String tenGaDen; // tên ga đến
	private String tenKhachHang; // tên ga đến
	
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
	private KhachHang khachHang; // Thêm thuộc tính KhachHang
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
	public void setChuyen(String chuyen) { this.chuyen = chuyen; }
	public void setGhe(String ghe) { this.ghe = ghe; }
	public void setTenGaDi(String tenGaDi) { this.tenGaDi = tenGaDi; }
	public void setTenGaDen(String tenGaDen) { this.tenGaDen = tenGaDen; }
	public void setTenKH(String tenKH) { this.tenKhachHang = tenKH; }

	// ===== Getter dẫn xuất cho TableView =====
	/** Tên chuyến tàu */
	public String getChuyen() {
	    if (chuyen == null) {
	        return "";
	    } else {
	        String ten = chuyen;
	        if (ten == null) {
	            return "";
	        } else {
	            return ten;
	        }
	    }
	}

	/** Tên ghế */
	public String getGhe() {
	    if (ghe == null) {
	        return "";
	    } else {
	        String ten =  ghe;
	        if (ten == null) {
	            return "";
	        } else {
	            return ten;
	        }
	    }
	}

	/** Tên ga đi */
	public String getTenGaDi() {
	    if (tenGaDi == null) {
	        return "";
	    } else {
	        String ten = tenGaDi;
	        if (ten == null) {
	            return "";
	        } else {
	            return ten;
	        }
	    }
	}

	/** Tên ga đến */
	public String getTenGaDen() {
	    if (tenGaDen == null) {
	        return "";
	    } else {
	        String ten = tenGaDen;
	        if (ten == null) {
	            return "";
	        } else {
	            return ten;
	        }
	    }
	}

	/** Ngày in vé định dạng dd/MM/yyyy HH:mm */
	public String getNgayInVeStr() {
	    if (ngayInVe == null) {
	        return "";
	    } else {
	        return ngayInVe.format(DTF);
	    }
	}

	/** Loại hành trình hiển thị (nếu là enum, dùng toString hoặc getDisplayName nếu có) */
	public String getLoaiHanhTrinhStr() {
	    if (loaiHanhTrinh == null) {
	        return "";
	    } else {
	        return loaiHanhTrinh.getDisplayName();
	    }
	}

	/** Loại vé hiển thị (enum LoaiVe có getDisplayName()) */
	public String getLoaiVeStr() {
	    if (loaiVe == null) {
	        return "";
	    } else {
	        return loaiVe.getDisplayName();
	    }
	}

	/** Trạng thái vé hiển thị (tránh null) */
	public String getTrangThaiVeStr() {
	    if (trangThaiVe == null) {
	        return "";
	    } else {
	        return trangThaiVe;
	    }
	}

	/** Có phòng chờ VIP? → "Có"/"Không" */
	public String getCoPhongChoVipStr() {
	    if (coPhongChoVip) {
	        return "Có";
	    } else {
	        return "Không";
	    }
	}

	/** Tên khách hàng (nếu có) */
	public String getTenKhachHang() {
	    if (tenKhachHang == null) {
	        return "";
	    } else {
	        String ten = tenKhachHang;
	        if (ten == null) {
	            return "";
	        } else {
	            return ten;
	        }
	    }
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
