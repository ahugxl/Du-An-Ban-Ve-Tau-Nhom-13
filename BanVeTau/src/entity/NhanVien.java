package entity;

import java.time.LocalDate;

public class NhanVien {
	private String maNhanVien;
    private String tenNV;
    private LocalDate ngaySinh;
    private boolean gioiTinh;
    private String sdt;
    private boolean trangThaiLamViec;
    private ChucVu cv; 
    private TaiKhoan taiKhoan;
    private boolean trangThaiXoa;
	public NhanVien(String maNhanVien, String tenNV, LocalDate ngaySinh, boolean gioiTinh, String sdt,
			boolean trangThaiLamViec, ChucVu cv, TaiKhoan taiKhoan, boolean trangThaiXoa) {
		super();
		this.maNhanVien = maNhanVien;
		this.tenNV = tenNV;
		this.ngaySinh = ngaySinh;
		this.gioiTinh = gioiTinh;
		this.sdt = sdt;
		this.trangThaiLamViec = trangThaiLamViec;
		this.cv = cv;
		this.taiKhoan = taiKhoan;
		this.trangThaiXoa = trangThaiXoa;
	}
	
	public NhanVien() {
		super();
	}

	public String getMaNhanVien() {
		return maNhanVien;
	}
	public void setMaNhanVien(String maNhanVien) {
		this.maNhanVien = maNhanVien;
	}
	public String getTenNV() {
		return tenNV;
	}
	public void setTenNV(String tenNV) {
		this.tenNV = tenNV;
	}
	public LocalDate getNgaySinh() {
		return ngaySinh;
	}
	public void setNgaySinh(LocalDate ngaySinh) {
		this.ngaySinh = ngaySinh;
	}
	public boolean isGioiTinh() {
		return gioiTinh;
	}
	public void setGioiTinh(boolean gioiTinh) {
		this.gioiTinh = gioiTinh;
	}
	public String getSdt() {
		return sdt;
	}
	public void setSdt(String sdt) {
		this.sdt = sdt;
	}
	public boolean isTrangThaiLamViec() {
		return trangThaiLamViec;
	}
	public void setTrangThaiLamViec(boolean trangThaiLamViec) {
		this.trangThaiLamViec = trangThaiLamViec;
	}
	public ChucVu getCv() {
		return cv;
	}
	public void setCv(ChucVu cv) {
		this.cv = cv;
	}
	public TaiKhoan getTaiKhoan() {
		return taiKhoan;
	}
	public void setTaiKhoan(TaiKhoan taiKhoan) {
		this.taiKhoan = taiKhoan;
	}
	public boolean isTrangThaiXoa() {
		return trangThaiXoa;
	}
	public void setTrangThaiXoa(boolean trangThaiXoa) {
		this.trangThaiXoa = trangThaiXoa;
	}
    
}