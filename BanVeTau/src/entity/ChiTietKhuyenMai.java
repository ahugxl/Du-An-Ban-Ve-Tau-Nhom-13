package entity;

import java.time.LocalDateTime;
import java.util.Objects;

public class ChiTietKhuyenMai {
	private KhuyenMai khuyenMai;
	private Ve ve;
	private double soTienDuocGiam;
	private LocalDateTime ngayApDung;
	
	public ChiTietKhuyenMai() {
	}
	
	public ChiTietKhuyenMai(KhuyenMai khuyenMai, Ve ve, double soTienDuocGiam, LocalDateTime ngayApDung) {
		this.khuyenMai = khuyenMai;
		this.ve = ve;
		this.soTienDuocGiam = soTienDuocGiam;
		this.ngayApDung = ngayApDung;
	}

	public KhuyenMai getKhuyenMai() {
		return khuyenMai;
	}

	public Ve getVe() {
		return ve;
	}

	public double getSoTienDuocGiam() {
		return soTienDuocGiam;
	}

	public LocalDateTime getNgayApDung() {
		return ngayApDung;
	}

	public void setKhuyenMai(KhuyenMai khuyenMai) {
		this.khuyenMai = khuyenMai;
	}

	public void setVe(Ve ve) {
		this.ve = ve;
	}

	public void setSoTienDuocGiam(double soTienDuocGiam) {
		this.soTienDuocGiam = soTienDuocGiam;
	}

	public void setNgayApDung(LocalDateTime ngayApDung) {
		this.ngayApDung = ngayApDung;
	}

	@Override
	public int hashCode() {
		return Objects.hash(khuyenMai, ve);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ChiTietKhuyenMai other = (ChiTietKhuyenMai) obj;
		return Objects.equals(khuyenMai, other.khuyenMai) && Objects.equals(ve, other.ve);
	}

	@Override
	public String toString() {
		return "ChiTietKhuyenMai [khuyenMai=" + khuyenMai + ", ve=" + ve + ", soTienDuocGiam=" + soTienDuocGiam
				+ ", ngayApDung=" + ngayApDung + "]";
	}
	
}

