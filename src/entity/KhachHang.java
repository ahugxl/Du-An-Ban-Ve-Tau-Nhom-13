package entity;

import java.time.LocalDate;
import java.util.Objects;

public class KhachHang {
	private String maKhachHang;
	private String hoTenKhachHang;
	private String soGiayTo;
	private LocalDate ngaySinh;
	private String soDienThoai;
	private boolean gioiTinh;
	
	public KhachHang() {
	}

	public KhachHang(String maKhachHang) {
		super();
		this.maKhachHang = maKhachHang;
	}

	public KhachHang(String maKhachHang, String hoTenKhachHang, String soGiayTo, LocalDate ngaySinh, String soDienThoai,
			boolean gioiTinh) {
		this.maKhachHang = maKhachHang;
		this.hoTenKhachHang = hoTenKhachHang;
		this.soGiayTo = soGiayTo;
		this.ngaySinh = ngaySinh;
		this.soDienThoai = soDienThoai;
		this.gioiTinh = gioiTinh;
	}

	public String getMaKhachHang() {
		return maKhachHang;
	}

	public String getHoTenKhachHang() {
		return hoTenKhachHang;
	}

	public String getSoGiayTo() {
		return soGiayTo;
	}

	public LocalDate getNgaySinh() {
		return ngaySinh;
	}

	public String getSoDienThoai() {
		return soDienThoai;
	}

	public boolean isGioiTinh() {
		return gioiTinh;
	}

	public void setMaKhachHang(String maKhachHang) {
		this.maKhachHang = maKhachHang;
	}

	public void setHoTenKhachHang(String hoTenKhachHang) {
		this.hoTenKhachHang = hoTenKhachHang;
	}

	public void setSoGiayTo(String soGiayTo) {
		this.soGiayTo = soGiayTo;
	}

	public void setNgaySinh(LocalDate ngaySinh) {
		this.ngaySinh = ngaySinh;
	}

	public void setSoDienThoai(String soDienThoai) {
		this.soDienThoai = soDienThoai;
	}

	public void setGioiTinh(boolean gioiTinh) {
		this.gioiTinh = gioiTinh;
	}

	@Override
	public int hashCode() {
		return Objects.hash(maKhachHang);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		KhachHang other = (KhachHang) obj;
		return Objects.equals(maKhachHang, other.maKhachHang);
	}

	@Override
	public String toString() {
		return "KhachHang [maKhachHang=" + maKhachHang + ", hoTenKhachHang=" + hoTenKhachHang + ", soGiayTo=" + soGiayTo
				+ ", ngaySinh=" + ngaySinh + ", soDienThoai=" + soDienThoai + ", gioiTinh=" + gioiTinh + "]";
	}
	
}
