package entity;

import java.time.LocalDate;
import java.util.Objects;

public class Thue {
	private String maSoThue;
	private String tenThue;
	private double mucThue;
	private String trangThai;
	private LocalDate ngayBatDau;
	
	public Thue() {
	}
	
	public Thue(String maSoThue, String tenThue, double mucThue, String trangThai, LocalDate ngayBatDau) {
		this.maSoThue = maSoThue;
		this.tenThue = tenThue;
		this.mucThue = mucThue;
		this.trangThai = trangThai;
		this.ngayBatDau = ngayBatDau;
	}

	public String getMaSoThue() {
		return maSoThue;
	}

	public String getTenThue() {
		return tenThue;
	}

	public double getMucThue() {
		return mucThue;
	}

	public String getTrangThai() {
		return trangThai;
	}

	public LocalDate getNgayBatDau() {
		return ngayBatDau;
	}

	public void setMaSoThue(String maSoThue) {
		this.maSoThue = maSoThue;
	}

	public void setTenThue(String tenThue) {
		this.tenThue = tenThue;
	}

	public void setMucThue(double mucThue) {
		this.mucThue = mucThue;
	}

	public void setTrangThai(String trangThai) {
		this.trangThai = trangThai;
	}

	public void setNgayBatDau(LocalDate ngayBatDau) {
		this.ngayBatDau = ngayBatDau;
	}

	@Override
	public int hashCode() {
		return Objects.hash(maSoThue);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Thue other = (Thue) obj;
		return Objects.equals(maSoThue, other.maSoThue);
	}

	@Override
	public String toString() {
		return "Thue [maSoThue=" + maSoThue + ", tenThue=" + tenThue + ", mucThue=" + mucThue + ", trangThai="
				+ trangThai + ", ngayBatDau=" + ngayBatDau + "]";
	}
	
}
