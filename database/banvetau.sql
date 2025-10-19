use master
CREATE DATABASE QuanLyBanVeTau;
GO
USE QuanLyBanVeTau;
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
-- B?NG CH NH
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
    email NVARCHAR(100),
	trangThaiXoa BIT DEFAULT 0
);

CREATE TABLE NhanVien (
    maNhanVien VARCHAR(20) PRIMARY KEY,
    tenNhanVien NVARCHAR(100),
    ngaySinh DATE,
    gioiTinh BIT,
    soDienThoai VARCHAR(15),
    trangThaiLamViec NVARCHAR(50),
    maChucVu VARCHAR(20) FOREIGN KEY REFERENCES ChucVu(maChucVu),
    tenTaiKhoan VARCHAR(50) FOREIGN KEY REFERENCES TaiKhoan(tenTaiKhoan),
	trangThaiXoa BIT DEFAULT 0
);

CREATE TABLE KhachHang (
    maKhachHang VARCHAR(20) PRIMARY KEY,
    hoTenKhachHang NVARCHAR(100),
    soGiayTo VARCHAR(20),
    ngaySinh DATE,
    soDienThoai VARCHAR(15),
    gioiTinh BIT,
	trangThaiXoa BIT DEFAULT 0
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
-- B?NG LI N K?T NHI?U - NHI?U
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

-- Bảng ChucVu
INSERT INTO ChucVu (maChucVu, tenChucVu) VALUES
('CV01', N'Nhân viên'),
('CV02', N'Quản lý');

-- Bảng TaiKhoan
INSERT INTO TaiKhoan (tenTaiKhoan, matKhau, email, trangThaiXoa) VALUES
('nv001_Nam', '123456', 'nam@example.com', 0),
('nv002_Hoa', '123456', 'hoa@example.com', 0),
('nv003_An', '123456', 'an@example.com', 0),
('nv004_Trang', '123456', 'trang@example.com', 0),
('nv005_Linh', '123456', 'linh@example.com', 0),
('nv006_Quang', '123456', 'quang@example.com', 0),
('nv007_Tuan', '123456', 'tuan@example.com', 0),
('nv008_Hung', '123456', 'hung@example.com', 0),
('nv009_Thao', '123456', 'thao@example.com', 0),
('nv010_Thanh', '123456', 'thanh@example.com', 0);

-- Bảng NhanVien
INSERT INTO NhanVien (maNhanVien, tenNhanVien, ngaySinh, gioiTinh, soDienThoai, trangThaiLamViec, maChucVu, tenTaiKhoan, trangThaiXoa) VALUES
('nv001', N'Nguyen Van Nam', '1985-05-15', 1, '0901234567', N'Đang làm việc', 'CV01', 'nv001_Nam', 0),
('nv002', N'Tran Thi Hoa', '1990-07-21', 0, '0902345678', N'Đang làm việc', 'CV01', 'nv002_Hoa', 0),
('nv003', N'Le Van An', '1988-11-10', 1, '0903456789', N'Đang làm việc', 'CV01', 'nv003_An', 0),
('nv004', N'Pham Thi Trang', '1992-03-05', 0, '0904567890', N'Đang làm việc', 'CV01', 'nv004_Trang', 0),
('nv005', N'Dang Thi Linh', '1987-12-25', 0, '0905678901', N'Đang làm việc', 'CV01', 'nv005_Linh', 0),
('nv006', N'Hoang Van Quang', '1984-09-17', 1, '0906789012', N'Đang làm việc', 'CV02', 'nv006_Quang', 0),
('nv007', N'Bui Van Tuan', '1986-01-30', 1, '0907890123', N'Đang làm việc', 'CV02', 'nv007_Tuan', 0),
('nv008', N'Duong Van Hung', '1991-08-12', 1, '0908901234', N'Đang làm việc', 'CV01', 'nv008_Hung', 0),
('nv009', N'Vu Thi Thao', '1993-04-18', 0, '0909012345', N'Đang làm việc', 'CV01', 'nv009_Thao', 0),
('nv010', N'Le Van Thanh', '1989-06-22', 1, '0910123456', N'Đang làm việc', 'CV02', 'nv010_Thanh', 0);

-- Bảng KhachHang (10 dòng mẫu)
INSERT INTO KhachHang (maKhachHang, hoTenKhachHang, soGiayTo, ngaySinh, soDienThoai, gioiTinh, trangThaiXoa) VALUES
('kh001', N'Tran Van A', '079202022132', '1990-01-01', '0912345678', 1, 0),
('kh002', N'Le Thi B', '079202022111', '1992-02-02', '0923456789', 0, 0),
('kh003', N'Pham Van C', '079202022654', '1988-03-03', '0934567890', 1, 0),
('kh004', N'Hoang Thi D', '079202022789', '1991-04-04', '0945678901', 0, 0),
('kh005', N'Nguyen Van E', '079202022987', '1987-05-05', '0956789012', 1, 0),
('kh006', N'Dang Thi F', '079202022555', '1993-06-06', '0967890123', 0, 0),
('kh007', N'Vu Van G', '079202022122', '1989-07-07', '0978901234', 1, 0),
('kh008', N'Bui Thi H', '079202022012', '1994-08-08', '0989012345', 0, 0),
('kh009', N'Duong Van I', '079202022101', '1990-09-09', '0990123456', 1, 0),
('kh010', N'Le Thi K', '079202022112', '1992-10-10', '0901234567', 0, 0);

-- =========================
-- INSERT DỮ LIỆU MẪU
-- =========================

-- Bảng LoaiTau
INSERT INTO LoaiTau (maLoaiTau, tenLoaiTau) VALUES
('LT001', N'Tàu khách'),
('LT002', N'Tàu cao tốc'),
('LT003', N'Tàu chở hàng');

-- Bảng LoaiToaTau
INSERT INTO LoaiToaTau (maLoaiToa, tenLoai) VALUES
('LTA001', N'Toa ghế ngồi'),
('LTA002', N'Toa giường nằm'),
('LTA003', N'Toa VIP');

-- Bảng LoaiVe
INSERT INTO LoaiVe (maLoaiVe, tenLoai) VALUES
('LV001', N'Vé người lớn'),
('LV002', N'Vé trẻ em'),
('LV003', N'Vé người già');

-- Bảng LoaiHanhTrinh
INSERT INTO LoaiHanhTrinh (maLoaiHanhTrinh, tenLoai) VALUES
('LHT001', N'Hành trình ngắn'),
('LHT002', N'Hành trình dài'),
('LHT003', N'Hành trình quốc tế');

-- Bảng LoaiKhuyenMai
INSERT INTO LoaiKhuyenMai (maLoaiKhuyenMai, tenLoai) VALUES
('LKM001', N'Giảm giá phần trăm'),
('LKM002', N'Giảm giá cố định'),
('LKM003', N'Khuyến mại mua kèm');

-- Bảng Tau
INSERT INTO Tau (maTau, tenTau, maLoaiTau, soLanSuaChua) VALUES
('T001', N'Tàu Sài Gòn Express', 'LT002', 2),
('T002', N'Tàu Hà Nội Express', 'LT002', 1),
('T003', N'Tàu Huế Express', 'LT001', 3),
('T004', N'Tàu Đà Nẵng Express', 'LT001', 2),
('T005', N'Tàu Nha Trang Express', 'LT002', 0);

-- Bảng ToaTau
INSERT INTO ToaTau (maToaTau, tenToaTau, thuTuToa, maLoaiToa, soLuongGhe, heSoHangToa, maTau) VALUES
('TOA001', N'Toa 1', 1, 'LTA001', 50, 1.0, 'T001'),
('TOA002', N'Toa 2', 2, 'LTA002', 40, 1.5, 'T001'),
('TOA003', N'Toa 3', 3, 'LTA003', 20, 2.0, 'T001'),
('TOA004', N'Toa 1', 1, 'LTA001', 50, 1.0, 'T002'),
('TOA005', N'Toa 2', 2, 'LTA002', 40, 1.5, 'T002'),
('TOA006', N'Toa 1', 1, 'LTA001', 50, 1.0, 'T003'),
('TOA007', N'Toa 2', 2, 'LTA002', 40, 1.5, 'T003'),
('TOA008', N'Toa 1', 1, 'LTA001', 50, 1.0, 'T004'),
('TOA009', N'Toa 1', 1, 'LTA001', 50, 1.0, 'T005');

-- Bảng GheNgoi
INSERT INTO GheNgoi (maGheNgoi, viTriGhe, maToaTau) VALUES
('GHE001', N'A1', 'TOA001'),
('GHE002', N'A2', 'TOA001'),
('GHE003', N'A3', 'TOA001'),
('GHE004', N'B1', 'TOA001'),
('GHE005', N'B2', 'TOA001'),
('GHE006', N'A1', 'TOA002'),
('GHE007', N'A2', 'TOA002'),
('GHE008', N'B1', 'TOA002'),
('GHE009', N'A1', 'TOA003'),
('GHE010', N'A2', 'TOA003'),
('GHE011', N'A1', 'TOA004'),
('GHE012', N'A2', 'TOA004'),
('GHE013', N'A1', 'TOA005'),
('GHE014', N'A2', 'TOA005'),
('GHE015', N'A1', 'TOA006'),
('GHE016', N'A2', 'TOA006'),
('GHE017', N'A1', 'TOA007'),
('GHE018', N'A2', 'TOA007'),
('GHE019', N'A1', 'TOA008'),
('GHE020', N'A1', 'TOA009');

-- Bảng GaTau
INSERT INTO GaTau (maGaTau, tenGaTau, diaChiGa, soDienThoaiGa) VALUES
('GA001', N'Ga Sài Gòn', N'123 Nguyễn Huệ, TP.HCM', '0283456789'),
('GA002', N'Ga Hà Nội', N'456 Lê Duẩn, Hà Nội', '0243456789'),
('GA003', N'Ga Huế', N'789 Trần Hưng Đạo, Huế', '0343456789'),
('GA004', N'Ga Đà Nẵng', N'321 Hải Phòng, Đà Nẵng', '0363456789'),
('GA005', N'Ga Nha Trang', N'654 Trần Phú, Nha Trang', '0583456789');

-- Bảng TuyenDuong
INSERT INTO TuyenDuong (maTuyenDuong, tenTuyenDuong, gaKhoiHanh, gaKetThuc, thoiGianUocTinh) VALUES
('TD001', N'Sài Gòn - Hà Nội', 'GA001', 'GA002', '30 giờ'),
('TD002', N'Sài Gòn - Huế', 'GA001', 'GA003', '20 giờ'),
('TD003', N'Sài Gòn - Đà Nẵng', 'GA001', 'GA004', '18 giờ'),
('TD004', N'Hà Nội - Huế', 'GA002', 'GA003', '15 giờ'),
('TD005', N'Huế - Nha Trang', 'GA003', 'GA005', '12 giờ');

-- Bảng ChuyenTau
INSERT INTO ChuyenTau (maChuyenTau, maTau, maTuyenDuong, ngayGioKhoiHanh, ngayGioDen, donGiaCoBan) VALUES
('CT001', 'T001', 'TD001', '2024-01-15 08:00:00', '2024-01-16 14:00:00', 500000),
('CT002', 'T002', 'TD002', '2024-01-15 10:00:00', '2024-01-15 22:00:00', 400000),
('CT003', 'T003', 'TD003', '2024-01-15 12:00:00', '2024-01-16 06:00:00', 350000),
('CT004', 'T004', 'TD004', '2024-01-15 14:00:00', '2024-01-15 23:00:00', 300000),
('CT005', 'T005', 'TD005', '2024-01-15 16:00:00', '2024-01-16 04:00:00', 250000);

-- Bảng ChangTau
INSERT INTO ChangTau (maChangTau, maChuyenTau, maGaDi, maGaDen, thoiGianDi, thoiGianDen, soKm, soThuTu) VALUES
('CG001', 'CT001', 'GA001', 'GA002', '2024-01-15 08:00:00', '2024-01-16 14:00:00', 1700, 1),
('CG002', 'CT002', 'GA001', 'GA003', '2024-01-15 10:00:00', '2024-01-15 22:00:00', 1200, 1),
('CG003', 'CT003', 'GA001', 'GA004', '2024-01-15 12:00:00', '2024-01-16 06:00:00', 1100, 1),
('CG004', 'CT004', 'GA002', 'GA003', '2024-01-15 14:00:00', '2024-01-15 23:00:00', 900, 1),
('CG005', 'CT005', 'GA003', 'GA005', '2024-01-15 16:00:00', '2024-01-16 04:00:00', 700, 1);

-- Bảng Thue
INSERT INTO Thue (maSoThue, tenThue, mucThue, trangThai, ngayBatDau) VALUES
('T001', N'Thuế VAT 10%', 0.10, N'Hoạt động', '2024-01-01'),
('T002', N'Thuế VAT 5%', 0.05, N'Hoạt động', '2024-01-01'),
('T003', N'Thuế môn bài', 0.02, N'Hoạt động', '2024-01-01');

-- Bảng Ve
INSERT INTO Ve (maVe, tenVe, maChuyenTau, maGheNgoi, maGaDi, maGaDen, ngayInVe, maLoaiHanhTrinh, maLoaiVe, trangThaiVe, coPhongChoVip, maThueApDung) VALUES
('V001', N'Vé CT001-GHE001', 'CT001', 'GHE001', 'GA001', 'GA002', '2024-01-14 10:00:00', 'LHT002', 'LV001', N'Còn hiệu lực', 0, 'T001'),
('V002', N'Vé CT001-GHE002', 'CT001', 'GHE002', 'GA001', 'GA002', '2024-01-14 10:00:00', 'LHT002', 'LV001', N'Còn hiệu lực', 0, 'T001'),
('V003', N'Vé CT001-GHE003', 'CT001', 'GHE003', 'GA001', 'GA002', '2024-01-14 10:00:00', 'LHT002', 'LV002', N'Còn hiệu lực', 0, 'T001'),
('V004', N'Vé CT002-GHE006', 'CT002', 'GHE006', 'GA001', 'GA003', '2024-01-14 11:00:00', 'LHT002', 'LV001', N'Còn hiệu lực', 1, 'T001'),
('V005', N'Vé CT002-GHE007', 'CT002', 'GHE007', 'GA001', 'GA003', '2024-01-14 11:00:00', 'LHT002', 'LV001', N'Còn hiệu lực', 1, 'T001'),
('V006', N'Vé CT003-GHE009', 'CT003', 'GHE009', 'GA001', 'GA004', '2024-01-14 12:00:00', 'LHT002', 'LV001', N'Còn hiệu lực', 0, 'T002'),
('V007', N'Vé CT003-GHE010', 'CT003', 'GHE010', 'GA001', 'GA004', '2024-01-14 12:00:00', 'LHT002', 'LV003', N'Còn hiệu lực', 0, 'T002'),
('V008', N'Vé CT004-GHE011', 'CT004', 'GHE011', 'GA002', 'GA003', '2024-01-14 13:00:00', 'LHT001', 'LV001', N'Còn hiệu lực', 0, 'T001'),
('V009', N'Vé CT005-GHE013', 'CT005', 'GHE013', 'GA003', 'GA005', '2024-01-14 14:00:00', 'LHT001', 'LV001', N'Còn hiệu lực', 0, 'T001'),
('V010', N'Vé CT005-GHE014', 'CT005', 'GHE014', 'GA003', 'GA005', '2024-01-14 14:00:00', 'LHT001', 'LV002', N'Còn hiệu lực', 0, 'T001');

-- Bảng HoaDon
INSERT INTO HoaDon (maHoaDon, maKhachHang, maNhanVienLapHoaDon, ngayLapHoaDon, trangThaiHoaDon, donViBanHang) VALUES
('HD001', 'kh001', 'nv001', '2024-01-14 10:30:00', N'Đã thanh toán', N'Ga Sài Gòn'),
('HD002', 'kh002', 'nv002', '2024-01-14 11:00:00', N'Đã thanh toán', N'Ga Sài Gòn'),
('HD003', 'kh003', 'nv003', '2024-01-14 11:30:00', N'Đã thanh toán', N'Ga Sài Gòn'),
('HD004', 'kh004', 'nv001', '2024-01-14 12:00:00', N'Đã thanh toán', N'Ga Sài Gòn'),
('HD005', 'kh005', 'nv002', '2024-01-14 12:30:00', N'Đã thanh toán', N'Ga Sài Gòn'),
('HD006', 'kh006', 'nv003', '2024-01-14 13:00:00', N'Đã thanh toán', N'Ga Sài Gòn'),
('HD007', 'kh007', 'nv001', '2024-01-14 13:30:00', N'Đã thanh toán', N'Ga Sài Gòn'),
('HD008', 'kh008', 'nv002', '2024-01-14 14:00:00', N'Đã thanh toán', N'Ga Sài Gòn'),
('HD009', 'kh009', 'nv003', '2024-01-14 14:30:00', N'Đã thanh toán', N'Ga Sài Gòn'),
('HD010', 'kh010', 'nv001', '2024-01-14 15:00:00', N'Đã thanh toán', N'Ga Sài Gòn');

-- Bảng ChiTietHoaDon
INSERT INTO ChiTietHoaDon (maHoaDon, maVe, tenDichVu, donViTinh) VALUES
('HD001', 'V001', N'Vé tàu', N'Cái'),
('HD001', 'V002', N'Vé tàu', N'Cái'),
('HD002', 'V003', N'Vé tàu', N'Cái'),
('HD003', 'V004', N'Vé tàu', N'Cái'),
('HD004', 'V005', N'Vé tàu', N'Cái'),
('HD005', 'V006', N'Vé tàu', N'Cái'),
('HD006', 'V007', N'Vé tàu', N'Cái'),
('HD007', 'V008', N'Vé tàu', N'Cái'),
('HD008', 'V009', N'Vé tàu', N'Cái'),
('HD009', 'V010', N'Vé tàu', N'Cái'),
('HD010', 'V001', N'Vé tàu', N'Cái');

-- Bảng KhuyenMai
INSERT INTO KhuyenMai (maKhuyenMai, tenKhuyenMai, moTa, maLoaiKhuyenMai, giaTriKhuyenMai, ngayBatDau, ngayKetThuc, dieuKienApDung, trangThai) VALUES
('KM001', N'Giảm 10% cho vé tàu cao tốc', N'Khuyến mại đặc biệt cho tàu cao tốc', 'LKM001', 0.10, '2024-01-01', '2024-01-31', N'Áp dụng cho tàu cao tốc', N'Hoạt động'),
('KM002', N'Giảm 50.000đ cho vé tàu thường', N'Khuyến mại cho vé tàu thường', 'LKM002', 50000, '2024-01-01', '2024-01-31', N'Áp dụng cho tàu thường', N'Hoạt động'),
('KM003', N'Mua 2 vé tặng 1 vé', N'Khuyến mại mua kèm', 'LKM003', 0, '2024-01-01', '2024-01-31', N'Mua 2 vé tặng 1 vé', N'Hoạt động'),
('KM004', N'Giảm 15% cho khách hàng thân thiết', N'Khuyến mại cho khách hàng thân thiết', 'LKM001', 0.15, '2024-01-01', '2024-02-28', N'Áp dụng cho tất cả vé', N'Hoạt động'),
('KM005', N'Giảm 100.000đ cho vé VIP', N'Khuyến mại cho vé VIP', 'LKM002', 100000, '2024-01-01', '2024-01-31', N'Áp dụng cho vé VIP', N'Hoạt động');

-- Bảng ChiTietKhuyenMai
INSERT INTO ChiTietKhuyenMai (maVe, maKhuyenMai, ngayApDung) VALUES
('V001', 'KM001', '2024-01-14 10:30:00'),
('V002', 'KM001', '2024-01-14 10:30:00'),
('V003', 'KM002', '2024-01-14 11:00:00'),
('V004', 'KM004', '2024-01-14 11:30:00'),
('V005', 'KM004', '2024-01-14 11:30:00'),
('V006', 'KM001', '2024-01-14 12:00:00'),
('V007', 'KM002', '2024-01-14 12:00:00'),
('V008', 'KM004', '2024-01-14 13:00:00'),
('V009', 'KM001', '2024-01-14 14:00:00'),
('V010', 'KM002', '2024-01-14 14:00:00');
