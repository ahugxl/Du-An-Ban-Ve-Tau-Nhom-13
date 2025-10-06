package entity;

import java.time.LocalDate;
import java.util.Objects;

public class KhuyenMai {
	private String maKhuyenMai;
	private String tenKhuyenMai;
	private String moTa;
	private LoaiKhuyenMai loaiKhuyenMai;
	private double giaTriKhuyenMai;
	private LocalDate ngayBatDau;
	private LocalDate NgayKetThuc;
	private String dieuKienApDung;
	private String trangThaiKhuyenMai;
	
	public KhuyenMai() {
	}

	public KhuyenMai(String maKhuyenMai, String tenKhuyenMai, String moTa, LoaiKhuyenMai loaiKhuyenMai,
			double giaTriKhuyenMai, LocalDate ngayBatDau, LocalDate ngayKetThuc, String dieuKienApDung,
			String trangThaiKhuyenMai) {
		this.maKhuyenMai = maKhuyenMai;
		this.tenKhuyenMai = tenKhuyenMai;
		this.moTa = moTa;
		this.loaiKhuyenMai = loaiKhuyenMai;
		this.giaTriKhuyenMai = giaTriKhuyenMai;
		this.ngayBatDau = ngayBatDau;
		NgayKetThuc = ngayKetThuc;
		this.dieuKienApDung = dieuKienApDung;
		this.trangThaiKhuyenMai = trangThaiKhuyenMai;
	}

	public String getMaKhuyenMai() {
		return maKhuyenMai;
	}

	public String getTenKhuyenMai() {
		return tenKhuyenMai;
	}

	public String getMoTa() {
		return moTa;
	}

	public LoaiKhuyenMai getLoaiKhuyenMai() {
		return loaiKhuyenMai;
	}

	public double getGiaTriKhuyenMai() {
		return giaTriKhuyenMai;
	}

	public LocalDate getNgayBatDau() {
		return ngayBatDau;
	}

	public LocalDate getNgayKetThuc() {
		return NgayKetThuc;
	}

	public String getDieuKienApDung() {
		return dieuKienApDung;
	}

	public String getTrangThaiKhuyenMai() {
		return trangThaiKhuyenMai;
	}

	public void setMaKhuyenMai(String maKhuyenMai) {
		this.maKhuyenMai = maKhuyenMai;
	}

	public void setTenKhuyenMai(String tenKhuyenMai) {
		this.tenKhuyenMai = tenKhuyenMai;
	}

	public void setMoTa(String moTa) {
		this.moTa = moTa;
	}

	public void setLoaiKhuyenMai(LoaiKhuyenMai loaiKhuyenMai) {
		this.loaiKhuyenMai = loaiKhuyenMai;
	}

	public void setGiaTriKhuyenMai(double giaTriKhuyenMai) {
		this.giaTriKhuyenMai = giaTriKhuyenMai;
	}

	public void setNgayBatDau(LocalDate ngayBatDau) {
		this.ngayBatDau = ngayBatDau;
	}

	public void setNgayKetThuc(LocalDate ngayKetThuc) {
		NgayKetThuc = ngayKetThuc;
	}

	public void setDieuKienApDung(String dieuKienApDung) {
		this.dieuKienApDung = dieuKienApDung;
	}

	public void setTrangThaiKhuyenMai(String trangThaiKhuyenMai) {
		this.trangThaiKhuyenMai = trangThaiKhuyenMai;
	}

	@Override
	public int hashCode() {
		return Objects.hash(maKhuyenMai);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		KhuyenMai other = (KhuyenMai) obj;
		return Objects.equals(maKhuyenMai, other.maKhuyenMai);
	}

	@Override
	public String toString() {
		return "KhuyenMai [maKhuyenMai=" + maKhuyenMai + ", tenKhuyenMai=" + tenKhuyenMai + ", moTa=" + moTa
				+ ", loaiKhuyenMai=" + loaiKhuyenMai + ", giaTriKhuyenMai=" + giaTriKhuyenMai + ", ngayBatDau="
				+ ngayBatDau + ", NgayKetThuc=" + NgayKetThuc + ", dieuKienApDung=" + dieuKienApDung
				+ ", trangThaiKhuyenMai=" + trangThaiKhuyenMai + "]";
	}
	
}
