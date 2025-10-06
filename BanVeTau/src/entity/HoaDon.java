package entity;

import java.time.LocalDateTime;
import java.util.Objects;

public class HoaDon {
	private String maHoaDon;
	private KhachHang khachHang;
	private NhanVien nhanVienLapHoaDon;
	private LocalDateTime ngayLapHoaDon;
	private String trangThaiHoaDon;
	private String donViBanHang;
	private int soLuongVe;
	
	public HoaDon() {
	}

	public HoaDon(String maHoaDon, KhachHang khachHang, NhanVien nhanVienLapHoaDon, LocalDateTime ngayLapHoaDon,
			String trangThaiHoaDon, String donViBanHang, int soLuongVe) {
		this.maHoaDon = maHoaDon;
		this.khachHang = khachHang;
		this.nhanVienLapHoaDon = nhanVienLapHoaDon;
		this.ngayLapHoaDon = ngayLapHoaDon;
		this.trangThaiHoaDon = trangThaiHoaDon;
		this.donViBanHang = donViBanHang;
		this.soLuongVe = soLuongVe;
	}

	public String getMaHoaDon() {
		return maHoaDon;
	}

	public KhachHang getKhachHang() {
		return khachHang;
	}

	public NhanVien getNhanVienLapHoaDon() {
		return nhanVienLapHoaDon;
	}

	public LocalDateTime getNgayLapHoaDon() {
		return ngayLapHoaDon;
	}

	public String getTrangThaiHoaDon() {
		return trangThaiHoaDon;
	}

	public String getDonViBanHang() {
		return donViBanHang;
	}

	public int getSoLuongVe() {
		return soLuongVe;
	}

	public void setMaHoaDon(String maHoaDon) {
		this.maHoaDon = maHoaDon;
	}

	public void setKhachHang(KhachHang khachHang) {
		this.khachHang = khachHang;
	}

	public void setNhanVienLapHoaDon(NhanVien nhanVienLapHoaDon) {
		this.nhanVienLapHoaDon = nhanVienLapHoaDon;
	}

	public void setNgayLapHoaDon(LocalDateTime ngayLapHoaDon) {
		this.ngayLapHoaDon = ngayLapHoaDon;
	}

	public void setTrangThaiHoaDon(String trangThaiHoaDon) {
		this.trangThaiHoaDon = trangThaiHoaDon;
	}

	public void setDonViBanHang(String donViBanHang) {
		this.donViBanHang = donViBanHang;
	}

	public void setSoLuongVe(int soLuongVe) {
		this.soLuongVe = soLuongVe;
	}

	@Override
	public int hashCode() {
		return Objects.hash(maHoaDon);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HoaDon other = (HoaDon) obj;
		return Objects.equals(maHoaDon, other.maHoaDon);
	}

	@Override
	public String toString() {
		return "HoaDon [maHoaDon=" + maHoaDon + ", khachHang=" + khachHang + ", nhanVienLapHoaDon=" + nhanVienLapHoaDon
				+ ", ngayLapHoaDon=" + ngayLapHoaDon + ", trangThaiHoaDon=" + trangThaiHoaDon + ", donViBanHang="
				+ donViBanHang + ", soLuongVe=" + soLuongVe + "]";
	}
	
}
