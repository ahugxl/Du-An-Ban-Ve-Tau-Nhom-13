CREATE DATABASE QuanLyBanVeTau3;
GO
USE QuanLyBanVeTau3;
GO

-- =========================
-- B?NG LO?I
-- =========================

CREATE TABLE LoaiTau (
    maLoaiTau VARCHAR(20) PRIMARY KEY,
    tenLoaiTau NVARCHAR(50)
);
CREATE TABLE LoaiToaTau (
    maLoaiToa VARCHAR(20) PRIMARY KEY,
    tenLoai NVARCHAR(50)
);

CREATE TABLE LoaiVe (
    maLoaiVe VARCHAR(20) PRIMARY KEY,
    tenLoai NVARCHAR(50)
);

CREATE TABLE LoaiHanhTrinh (
    maLoaiHanhTrinh VARCHAR(20) PRIMARY KEY,
    tenLoai NVARCHAR(50)
);

CREATE TABLE LoaiKhuyenMai (
    maLoaiKhuyenMai VARCHAR(20) PRIMARY KEY,
    tenLoai NVARCHAR(50)
);

CREATE TABLE ChucVu (
    maChucVu VARCHAR(20) PRIMARY KEY,
    tenChucVu NVARCHAR(50)
);

-- =========================
-- B?NG CHÍNH
-- =========================
CREATE TABLE Tau (
    maTau VARCHAR(20) PRIMARY KEY,
    tenTau NVARCHAR(100),
    maLoaiTau VARCHAR(20) FOREIGN KEY REFERENCES LoaiTau(maLoaiTau),
    soLanSuaChua INT
);

CREATE TABLE ToaTau (
    maToaTau VARCHAR(20) PRIMARY KEY,
    tenToaTau NVARCHAR(100),
    thuTuToa INT,
    maLoaiToa VARCHAR(20) FOREIGN KEY REFERENCES LoaiToaTau(maLoaiToa),
    soLuongGhe INT,
    heSoHangToa FLOAT,
    maTau VARCHAR(20) FOREIGN KEY REFERENCES Tau(maTau)
);

CREATE TABLE GheNgoi (
    maGheNgoi VARCHAR(20) PRIMARY KEY,
    viTriGhe NVARCHAR(50),
    maToaTau VARCHAR(20) FOREIGN KEY REFERENCES ToaTau(maToaTau)
);

CREATE TABLE GaTau (
    maGaTau VARCHAR(20) PRIMARY KEY,
    tenGaTau NVARCHAR(100),
    diaChiGa NVARCHAR(200),
    soDienThoaiGa VARCHAR(15)
);

CREATE TABLE TuyenDuong (
    maTuyenDuong VARCHAR(20) PRIMARY KEY,
    tenTuyenDuong NVARCHAR(50),
    gaKhoiHanh VARCHAR(20) FOREIGN KEY REFERENCES GaTau(maGaTau),
    gaKetThuc VARCHAR(20) FOREIGN KEY REFERENCES GaTau(maGaTau),
    thoiGianUocTinh VARCHAR(20)
);

CREATE TABLE ChuyenTau (
    maChuyenTau VARCHAR(20) PRIMARY KEY,
    maTau VARCHAR(20) FOREIGN KEY REFERENCES Tau(maTau),
    maTuyenDuong VARCHAR(20) FOREIGN KEY REFERENCES TuyenDuong(maTuyenDuong),
    ngayGioKhoiHanh DATETIME,
    ngayGioDen DATETIME,
    donGiaCoBan DECIMAL(18,2)
);

CREATE TABLE ChangTau (
    maChangTau VARCHAR(20) PRIMARY KEY,
    maChuyenTau VARCHAR(20) FOREIGN KEY REFERENCES ChuyenTau(maChuyenTau),
    maGaDi VARCHAR(20) FOREIGN KEY REFERENCES GaTau(maGaTau),
    maGaDen VARCHAR(20) FOREIGN KEY REFERENCES GaTau(maGaTau),
    thoiGianDi DATETIME,
    thoiGianDen DATETIME,
    soKm INT,
    soThuTu INT
);

CREATE TABLE TaiKhoan (
    tenTaiKhoan VARCHAR(50) PRIMARY KEY,
    matKhau VARCHAR(50),
    email NVARCHAR(100)
);

CREATE TABLE NhanVien (
    maNhanVien VARCHAR(20) PRIMARY KEY,
    tenNhanVien NVARCHAR(100),
    ngaySinh DATE,
    gioiTinh BIT,
    soDienThoai VARCHAR(15),
    trangThaiLamViec NVARCHAR(50),
    maChucVu VARCHAR(20) FOREIGN KEY REFERENCES ChucVu(maChucVu),
    tenTaiKhoan VARCHAR(50) FOREIGN KEY REFERENCES TaiKhoan(tenTaiKhoan)
);

CREATE TABLE KhachHang (
    maKhachHang VARCHAR(20) PRIMARY KEY,
    hoTenKhachHang NVARCHAR(100),
    soGiayTo VARCHAR(20),
    ngaySinh DATE,
    soDienThoai VARCHAR(15),
    gioiTinh BIT
);

CREATE TABLE HoaDon (
    maHoaDon VARCHAR(20) PRIMARY KEY,
    maKhachHang VARCHAR(20) FOREIGN KEY REFERENCES KhachHang(maKhachHang),
    maNhanVienLapHoaDon VARCHAR(20) FOREIGN KEY REFERENCES NhanVien(maNhanVien),
    ngayLapHoaDon DATETIME,
    trangThaiHoaDon NVARCHAR(20),
    donViBanHang NVARCHAR(50)
);

CREATE TABLE Thue (
    maSoThue VARCHAR(20) PRIMARY KEY,
    tenThue NVARCHAR(50),
    mucThue DECIMAL(18,2),
    trangThai NVARCHAR(20),
    ngayBatDau DATE
);

CREATE TABLE Ve (
    maVe VARCHAR(20) PRIMARY KEY,
    tenVe NVARCHAR(100),
    maChuyenTau VARCHAR(20) FOREIGN KEY REFERENCES ChuyenTau(maChuyenTau),
    maGheNgoi VARCHAR(20) FOREIGN KEY REFERENCES GheNgoi(maGheNgoi),
    maGaDi VARCHAR(20) FOREIGN KEY REFERENCES GaTau(maGaTau),
    maGaDen VARCHAR(20) FOREIGN KEY REFERENCES GaTau(maGaTau),
    ngayInVe DATETIME,
    maLoaiHanhTrinh VARCHAR(20) FOREIGN KEY REFERENCES LoaiHanhTrinh(maLoaiHanhTrinh),
    maLoaiVe VARCHAR(20) FOREIGN KEY REFERENCES LoaiVe(maLoaiVe),
    trangThaiVe NVARCHAR(50),
    coPhongChoVip BIT,
    maThueApDung VARCHAR(20) FOREIGN KEY REFERENCES Thue(maSoThue),
);

-- =========================
-- B?NG LIÊN K?T NHI?U - NHI?U
-- =========================
CREATE TABLE ChiTietHoaDon (
    maHoaDon VARCHAR(20),
    maVe VARCHAR(20),
    PRIMARY KEY (maHoaDon, maVe),
    FOREIGN KEY (maHoaDon) REFERENCES HoaDon(maHoaDon),
    FOREIGN KEY (maVe) REFERENCES Ve(maVe),
	tenDichVu NVARCHAR(100),
	donViTinh NVARCHAR(50)
);

CREATE TABLE KhuyenMai (
    maKhuyenMai VARCHAR(20) PRIMARY KEY,
    tenKhuyenMai NVARCHAR(100),
    moTa NVARCHAR(200),
    maLoaiKhuyenMai VARCHAR(20) FOREIGN KEY REFERENCES LoaiKhuyenMai(maLoaiKhuyenMai),
    giaTriKhuyenMai DECIMAL(18,2),
    ngayBatDau DATE,
    ngayKetThuc DATE,
    dieuKienApDung NVARCHAR(200),
    trangThai NVARCHAR(50)
);

CREATE TABLE ChiTietKhuyenMai (
    maVe VARCHAR(20),
    maKhuyenMai VARCHAR(20),
    PRIMARY KEY (maVe, maKhuyenMai),
    FOREIGN KEY (maVe) REFERENCES Ve(maVe),
    FOREIGN KEY (maKhuyenMai) REFERENCES KhuyenMai(maKhuyenMai),
	ngayApDung DATETIME
);
