package entity;

import java.util.Objects;

public class Tau {
	private String maTau;
	private String tenTau;
	private LoaiTau loaiTau;
	private int soLanSuaChua;
	
	public Tau() {
	}

	public Tau(String maTau, String tenTau, LoaiTau loaiTau, int soLanSuaChua) {
		this.maTau = maTau;
		this.tenTau = tenTau;
		this.loaiTau = loaiTau;
		this.soLanSuaChua = soLanSuaChua;
	}

	public String getMaTau() {
		return maTau;
	}

	public String getTenTau() {
		return tenTau;
	}

	public LoaiTau getLoaiTau() {
		return loaiTau;
	}

	public int getSoLanSuaChua() {
		return soLanSuaChua;
	}

	public void setMaTau(String maTau) {
		this.maTau = maTau;
	}

	public void setTenTau(String tenTau) {
		this.tenTau = tenTau;
	}

	public void setLoaiTau(LoaiTau loaiTau) {
		this.loaiTau = loaiTau;
	}

	public void setSoLanSuaChua(int soLanSuaChua) {
		this.soLanSuaChua = soLanSuaChua;
	}

	@Override
	public int hashCode() {
		return Objects.hash(maTau);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tau other = (Tau) obj;
		return Objects.equals(maTau, other.maTau);
	}

	@Override
	public String toString() {
		return "Tau [maTau=" + maTau + ", tenTau=" + tenTau + ", loaiTau=" + loaiTau + ", soLanSuaChua=" + soLanSuaChua
				+ "]";
	}
	
}
