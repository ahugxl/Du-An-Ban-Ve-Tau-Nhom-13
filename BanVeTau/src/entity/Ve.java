package entity;

import java.time.LocalDateTime;
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
	private boolean coPhongChopVip;
	private Thue thueApDung;
	
	public Ve() {
	}

	public Ve(String maVe, String tenVe, ChuyenTau chuyenTau, GheNgoi gheNgoi, GaTau gaDi, GaTau gaDen,
			LocalDateTime ngayInVe, LoaiHanhTrinh loaiHanhTrinh, LoaiVe loaiVe, String trangThaiVe,
			boolean coPhongChopVip, Thue thueApDung) {
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
		this.coPhongChopVip = coPhongChopVip;
		this.thueApDung = thueApDung;
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

	public boolean isCoPhongChopVip() {
		return coPhongChopVip;
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

	public void setCoPhongChopVip(boolean coPhongChopVip) {
		this.coPhongChopVip = coPhongChopVip;
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
				+ ", loaiVe=" + loaiVe + ", trangThaiVe=" + trangThaiVe + ", coPhongChopVip=" + coPhongChopVip
				+ ", thueApDung=" + thueApDung + "]";
	}

}
