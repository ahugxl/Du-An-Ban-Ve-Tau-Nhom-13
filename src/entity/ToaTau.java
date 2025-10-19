package entity;

import java.util.Objects;

public class ToaTau {
	private String maToaTau;
	private String tenToaTau;
	private int thuTuToa;
	private LoaiToaTau loaiToaTau;
	private int soLuongGhe;
	private double heSoHangToa;
	private Tau tau;
	
	public ToaTau() {
	}
	
	public ToaTau(String maToaTau, String tenToaTau, int thuTuToa, LoaiToaTau loaiToaTau, int soLuongGhe,
			double heSoHangToa, Tau tau) {
		this.maToaTau = maToaTau;
		this.tenToaTau = tenToaTau;
		this.thuTuToa = thuTuToa;
		this.loaiToaTau = loaiToaTau;
		this.soLuongGhe = soLuongGhe;
		this.heSoHangToa = heSoHangToa;
		this.tau = tau;
	}

	public String getMaToaTau() {
		return maToaTau;
	}

	public String getTenToaTau() {
		return tenToaTau;
	}

	public int getThuTuToa() {
		return thuTuToa;
	}

	public LoaiToaTau getLoaiToaTau() {
		return loaiToaTau;
	}

	public int getSoLuongGhe() {
		return soLuongGhe;
	}

	public double getHeSoHangToa() {
		return heSoHangToa;
	}

	public Tau getTau() {
		return tau;
	}

	public void setMaToaTau(String maToaTau) {
		this.maToaTau = maToaTau;
	}

	public void setTenToaTau(String tenToaTau) {
		this.tenToaTau = tenToaTau;
	}

	public void setThuTuToa(int thuTuToa) {
		this.thuTuToa = thuTuToa;
	}

	public void setLoaiToaTau(LoaiToaTau loaiToaTau) {
		this.loaiToaTau = loaiToaTau;
	}

	public void setSoLuongGhe(int soLuongGhe) {
		this.soLuongGhe = soLuongGhe;
	}

	public void setHeSoHangToa(double heSoHangToa) {
		this.heSoHangToa = heSoHangToa;
	}

	public void setTau(Tau tau) {
		this.tau = tau;
	}

	@Override
	public int hashCode() {
		return Objects.hash(maToaTau);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ToaTau other = (ToaTau) obj;
		return Objects.equals(maToaTau, other.maToaTau);
	}

	@Override
	public String toString() {
		return "ToaTau [maToaTau=" + maToaTau + ", tenToaTau=" + tenToaTau + ", thuTuToa=" + thuTuToa + ", loaiToaTau="
				+ loaiToaTau + ", soLuongGhe=" + soLuongGhe + ", heSoHangToa=" + heSoHangToa + ", tau=" + tau + "]";
	}
	
}
