package entity;

import java.util.Objects;

public class TuyenDuong {
	private String maTuyenDuong;
	private String tenTuyenDuong;
	private GaTau gaKhoiHanh;
	private GaTau gaKetThuc;
	private String thoiGianUocTinh;
	
	public TuyenDuong() {
	}
	
	public TuyenDuong(String maTuyenDuong, String tenTuyenDuong, GaTau gaKhoiHanh, GaTau gaKetThuc,
			String thoiGianUocTinh) {
		this.maTuyenDuong = maTuyenDuong;
		this.tenTuyenDuong = tenTuyenDuong;
		this.gaKhoiHanh = gaKhoiHanh;
		this.gaKetThuc = gaKetThuc;
		this.thoiGianUocTinh = thoiGianUocTinh;
	}

	public String getMaTuyenDuong() {
		return maTuyenDuong;
	}

	public String getTenTuyenDuong() {
		return tenTuyenDuong;
	}

	public GaTau getGaKhoiHanh() {
		return gaKhoiHanh;
	}

	public GaTau getGaKetThuc() {
		return gaKetThuc;
	}

	public String getThoiGianUocTinh() {
		return thoiGianUocTinh;
	}

	public void setMaTuyenDuong(String maTuyenDuong) {
		this.maTuyenDuong = maTuyenDuong;
	}

	public void setTenTuyenDuong(String tenTuyenDuong) {
		this.tenTuyenDuong = tenTuyenDuong;
	}

	public void setGaKhoiHanh(GaTau gaKhoiHanh) {
		this.gaKhoiHanh = gaKhoiHanh;
	}

	public void setGaKetThuc(GaTau gaKetThuc) {
		this.gaKetThuc = gaKetThuc;
	}

	public void setThoiGianUocTinh(String thoiGianUocTinh) {
		this.thoiGianUocTinh = thoiGianUocTinh;
	}

	@Override
	public int hashCode() {
		return Objects.hash(maTuyenDuong);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TuyenDuong other = (TuyenDuong) obj;
		return Objects.equals(maTuyenDuong, other.maTuyenDuong);
	}

	@Override
	public String toString() {
		return "TuyenDuong [maTuyenDuong=" + maTuyenDuong + ", tenTuyenDuong=" + tenTuyenDuong + ", gaKhoiHanh="
				+ gaKhoiHanh + ", gaKetThuc=" + gaKetThuc + ", thoiGianUocTinh=" + thoiGianUocTinh + "]";
	}
	
}
