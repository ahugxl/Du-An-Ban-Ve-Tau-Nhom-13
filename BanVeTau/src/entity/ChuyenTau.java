package entity;

import java.time.LocalDateTime;
import java.util.Objects;

public class ChuyenTau {
	private String maChuyenTau;
	private Tau tau;
	private TuyenDuong tuyenDuong;
	private LocalDateTime ngayGioKhoiHanh;
	private LocalDateTime ngayGioDen;
	private double donGiaCoBan;
	
	public ChuyenTau() {
	}
	
	public ChuyenTau(String maChuyenTau) {
		super();
		this.maChuyenTau = maChuyenTau;
	}

	public ChuyenTau(String maChuyenTau, Tau tau, TuyenDuong tuyenDuong, LocalDateTime ngayGioKhoiHanh,
			LocalDateTime ngayGioDen, double donGiaCoBan) {
		this.maChuyenTau = maChuyenTau;
		this.tau = tau;
		this.tuyenDuong = tuyenDuong;
		this.ngayGioKhoiHanh = ngayGioKhoiHanh;
		this.ngayGioDen = ngayGioDen;
		this.donGiaCoBan = donGiaCoBan;
	}

	public String getMaChuyenTau() {
		return maChuyenTau;
	}

	public Tau getTau() {
		return tau;
	}

	public TuyenDuong getTuyenDuong() {
		return tuyenDuong;
	}

	public LocalDateTime getNgayGioKhoiHanh() {
		return ngayGioKhoiHanh;
	}

	public LocalDateTime getNgayGioDen() {
		return ngayGioDen;
	}

	public double getDonGiaCoBan() {
		return donGiaCoBan;
	}

	public void setMaChuyenTau(String maChuyenTau) {
		this.maChuyenTau = maChuyenTau;
	}

	public void setTau(Tau tau) {
		this.tau = tau;
	}

	public void setTuyenDuong(TuyenDuong tuyenDuong) {
		this.tuyenDuong = tuyenDuong;
	}

	public void setNgayGioKhoiHanh(LocalDateTime ngayGioKhoiHanh) {
		this.ngayGioKhoiHanh = ngayGioKhoiHanh;
	}

	public void setNgayGioDen(LocalDateTime ngayGioDen) {
		this.ngayGioDen = ngayGioDen;
	}

	public void setDonGiaCoBan(double donGiaCoBan) {
		this.donGiaCoBan = donGiaCoBan;
	}

	@Override
	public int hashCode() {
		return Objects.hash(maChuyenTau);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ChuyenTau other = (ChuyenTau) obj;
		return Objects.equals(maChuyenTau, other.maChuyenTau);
	}

	@Override
	public String toString() {
		return "ChuyenTau [maChuyenTau=" + maChuyenTau + ", tau=" + tau + ", tuyenDuong=" + tuyenDuong
				+ ", ngayGioKhoiHanh=" + ngayGioKhoiHanh + ", ngayGioDen=" + ngayGioDen + ", donGiaCoBan=" + donGiaCoBan
				+ "]";
	}
	
}
