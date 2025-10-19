package entity;

import java.time.LocalDateTime;
import java.util.Objects;

public class ChangTau {
	private String maChangTau;
	private ChuyenTau chuyenTau;
	private GaTau gaDi;
	private GaTau gaDen;
	private LocalDateTime thoiGianDi;
	private LocalDateTime thoiGianDen;
	private int soKm;
	private int soThuTu;
	
	public ChangTau() {
	}

	public ChangTau(String maChangTau, ChuyenTau chuyenTau, GaTau gaDi, GaTau gaDen, LocalDateTime thoiGianDi,
			LocalDateTime thoiGianDen, int soKm, int soThuTu) {
		this.maChangTau = maChangTau;
		this.chuyenTau = chuyenTau;
		this.gaDi = gaDi;
		this.gaDen = gaDen;
		this.thoiGianDi = thoiGianDi;
		this.thoiGianDen = thoiGianDen;
		this.soKm = soKm;
		this.soThuTu = soThuTu;
	}

	public String getMaChangTau() {
		return maChangTau;
	}

	public ChuyenTau getChuyenTau() {
		return chuyenTau;
	}

	public GaTau getGaDi() {
		return gaDi;
	}

	public GaTau getGaDen() {
		return gaDen;
	}

	public LocalDateTime getThoiGianDi() {
		return thoiGianDi;
	}

	public LocalDateTime getThoiGianDen() {
		return thoiGianDen;
	}

	public int getSoKm() {
		return soKm;
	}

	public int getSoThuTu() {
		return soThuTu;
	}

	public void setMaChangTau(String maChangTau) {
		this.maChangTau = maChangTau;
	}

	public void setChuyenTau(ChuyenTau chuyenTau) {
		this.chuyenTau = chuyenTau;
	}

	public void setGaDi(GaTau gaDi) {
		this.gaDi = gaDi;
	}

	public void setGaDen(GaTau gaDen) {
		this.gaDen = gaDen;
	}

	public void setThoiGianDi(LocalDateTime thoiGianDi) {
		this.thoiGianDi = thoiGianDi;
	}

	public void setThoiGianDen(LocalDateTime thoiGianDen) {
		this.thoiGianDen = thoiGianDen;
	}

	public void setSoKm(int soKm) {
		this.soKm = soKm;
	}

	public void setSoThuTu(int soThuTu) {
		this.soThuTu = soThuTu;
	}

	@Override
	public int hashCode() {
		return Objects.hash(maChangTau);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ChangTau other = (ChangTau) obj;
		return Objects.equals(maChangTau, other.maChangTau);
	}

	@Override
	public String toString() {
		return "ChangTau [maChangTau=" + maChangTau + ", chuyenTau=" + chuyenTau + ", gaDi=" + gaDi + ", gaDen=" + gaDen
				+ ", thoiGianDi=" + thoiGianDi + ", thoiGianDen=" + thoiGianDen + ", soKm=" + soKm + ", soThuTu="
				+ soThuTu + "]";
	}
	
}
