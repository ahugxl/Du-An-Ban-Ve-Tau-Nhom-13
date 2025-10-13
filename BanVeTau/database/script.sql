USE [master]
GO
/****** Object:  Database [QuanLyBanVeTau3]    Script Date: 10/13/2025 12:06:19 AM ******/
CREATE DATABASE [QuanLyBanVeTau3]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'QuanLyBanVeTau3', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL16.MSSQLSERVER\MSSQL\DATA\QuanLyBanVeTau3.mdf' , SIZE = 8192KB , MAXSIZE = UNLIMITED, FILEGROWTH = 65536KB )
 LOG ON 
( NAME = N'QuanLyBanVeTau3_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL16.MSSQLSERVER\MSSQL\DATA\QuanLyBanVeTau3_log.ldf' , SIZE = 8192KB , MAXSIZE = 2048GB , FILEGROWTH = 65536KB )
 WITH CATALOG_COLLATION = DATABASE_DEFAULT, LEDGER = OFF
GO
ALTER DATABASE [QuanLyBanVeTau3] SET COMPATIBILITY_LEVEL = 160
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [QuanLyBanVeTau3].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [QuanLyBanVeTau3] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [QuanLyBanVeTau3] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [QuanLyBanVeTau3] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [QuanLyBanVeTau3] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [QuanLyBanVeTau3] SET ARITHABORT OFF 
GO
ALTER DATABASE [QuanLyBanVeTau3] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [QuanLyBanVeTau3] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [QuanLyBanVeTau3] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [QuanLyBanVeTau3] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [QuanLyBanVeTau3] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [QuanLyBanVeTau3] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [QuanLyBanVeTau3] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [QuanLyBanVeTau3] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [QuanLyBanVeTau3] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [QuanLyBanVeTau3] SET  ENABLE_BROKER 
GO
ALTER DATABASE [QuanLyBanVeTau3] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [QuanLyBanVeTau3] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [QuanLyBanVeTau3] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [QuanLyBanVeTau3] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [QuanLyBanVeTau3] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [QuanLyBanVeTau3] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [QuanLyBanVeTau3] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [QuanLyBanVeTau3] SET RECOVERY FULL 
GO
ALTER DATABASE [QuanLyBanVeTau3] SET  MULTI_USER 
GO
ALTER DATABASE [QuanLyBanVeTau3] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [QuanLyBanVeTau3] SET DB_CHAINING OFF 
GO
ALTER DATABASE [QuanLyBanVeTau3] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [QuanLyBanVeTau3] SET TARGET_RECOVERY_TIME = 60 SECONDS 
GO
ALTER DATABASE [QuanLyBanVeTau3] SET DELAYED_DURABILITY = DISABLED 
GO
ALTER DATABASE [QuanLyBanVeTau3] SET ACCELERATED_DATABASE_RECOVERY = OFF  
GO
EXEC sys.sp_db_vardecimal_storage_format N'QuanLyBanVeTau3', N'ON'
GO
ALTER DATABASE [QuanLyBanVeTau3] SET QUERY_STORE = ON
GO
ALTER DATABASE [QuanLyBanVeTau3] SET QUERY_STORE (OPERATION_MODE = READ_WRITE, CLEANUP_POLICY = (STALE_QUERY_THRESHOLD_DAYS = 30), DATA_FLUSH_INTERVAL_SECONDS = 900, INTERVAL_LENGTH_MINUTES = 60, MAX_STORAGE_SIZE_MB = 1000, QUERY_CAPTURE_MODE = AUTO, SIZE_BASED_CLEANUP_MODE = AUTO, MAX_PLANS_PER_QUERY = 200, WAIT_STATS_CAPTURE_MODE = ON)
GO
USE [QuanLyBanVeTau3]
GO
/****** Object:  Table [dbo].[ChangTau]    Script Date: 10/13/2025 12:06:19 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ChangTau](
	[maChangTau] [varchar](20) NOT NULL,
	[maChuyenTau] [varchar](20) NULL,
	[maGaDi] [varchar](20) NULL,
	[maGaDen] [varchar](20) NULL,
	[thoiGianDi] [datetime] NULL,
	[thoiGianDen] [datetime] NULL,
	[soKm] [int] NULL,
	[soThuTu] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[maChangTau] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[ChiTietHoaDon]    Script Date: 10/13/2025 12:06:19 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ChiTietHoaDon](
	[maHoaDon] [varchar](20) NOT NULL,
	[maVe] [varchar](20) NOT NULL,
	[tenDichVu] [nvarchar](100) NULL,
	[donViTinh] [nvarchar](50) NULL,
PRIMARY KEY CLUSTERED 
(
	[maHoaDon] ASC,
	[maVe] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[ChiTietKhuyenMai]    Script Date: 10/13/2025 12:06:19 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ChiTietKhuyenMai](
	[maVe] [varchar](20) NOT NULL,
	[maKhuyenMai] [varchar](20) NOT NULL,
	[ngayApDung] [datetime] NULL,
PRIMARY KEY CLUSTERED 
(
	[maVe] ASC,
	[maKhuyenMai] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[ChucVu]    Script Date: 10/13/2025 12:06:19 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ChucVu](
	[maChucVu] [varchar](20) NOT NULL,
	[tenChucVu] [nvarchar](50) NULL,
PRIMARY KEY CLUSTERED 
(
	[maChucVu] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[ChuyenTau]    Script Date: 10/13/2025 12:06:19 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ChuyenTau](
	[maChuyenTau] [varchar](20) NOT NULL,
	[maTau] [varchar](20) NULL,
	[maTuyenDuong] [varchar](20) NULL,
	[ngayGioKhoiHanh] [datetime] NULL,
	[ngayGioDen] [datetime] NULL,
	[donGiaCoBan] [decimal](18, 2) NULL,
PRIMARY KEY CLUSTERED 
(
	[maChuyenTau] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[GaTau]    Script Date: 10/13/2025 12:06:19 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[GaTau](
	[maGaTau] [varchar](20) NOT NULL,
	[tenGaTau] [nvarchar](100) NULL,
	[diaChiGa] [nvarchar](200) NULL,
	[soDienThoaiGa] [varchar](15) NULL,
PRIMARY KEY CLUSTERED 
(
	[maGaTau] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[GheNgoi]    Script Date: 10/13/2025 12:06:19 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[GheNgoi](
	[maGheNgoi] [varchar](20) NOT NULL,
	[viTriGhe] [nvarchar](50) NULL,
	[maToaTau] [varchar](20) NULL,
PRIMARY KEY CLUSTERED 
(
	[maGheNgoi] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[HoaDon]    Script Date: 10/13/2025 12:06:19 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[HoaDon](
	[maHoaDon] [varchar](20) NOT NULL,
	[maKhachHang] [varchar](20) NULL,
	[maNhanVienLapHoaDon] [varchar](20) NULL,
	[ngayLapHoaDon] [datetime] NULL,
	[trangThaiHoaDon] [nvarchar](20) NULL,
	[donViBanHang] [nvarchar](50) NULL,
PRIMARY KEY CLUSTERED 
(
	[maHoaDon] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[KhachHang]    Script Date: 10/13/2025 12:06:19 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[KhachHang](
	[maKhachHang] [varchar](20) NOT NULL,
	[hoTenKhachHang] [nvarchar](100) NULL,
	[soGiayTo] [varchar](20) NULL,
	[ngaySinh] [date] NULL,
	[soDienThoai] [varchar](15) NULL,
	[gioiTinh] [bit] NULL,
PRIMARY KEY CLUSTERED 
(
	[maKhachHang] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[KhuyenMai]    Script Date: 10/13/2025 12:06:19 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[KhuyenMai](
	[maKhuyenMai] [varchar](20) NOT NULL,
	[tenKhuyenMai] [nvarchar](100) NULL,
	[moTa] [nvarchar](200) NULL,
	[maLoaiKhuyenMai] [varchar](20) NULL,
	[giaTriKhuyenMai] [decimal](18, 2) NULL,
	[ngayBatDau] [date] NULL,
	[ngayKetThuc] [date] NULL,
	[dieuKienApDung] [nvarchar](200) NULL,
	[trangThai] [nvarchar](50) NULL,
PRIMARY KEY CLUSTERED 
(
	[maKhuyenMai] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[LoaiHanhTrinh]    Script Date: 10/13/2025 12:06:19 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[LoaiHanhTrinh](
	[maLoaiHanhTrinh] [varchar](20) NOT NULL,
	[tenLoai] [nvarchar](50) NULL,
PRIMARY KEY CLUSTERED 
(
	[maLoaiHanhTrinh] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[LoaiKhuyenMai]    Script Date: 10/13/2025 12:06:19 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[LoaiKhuyenMai](
	[maLoaiKhuyenMai] [varchar](20) NOT NULL,
	[tenLoai] [nvarchar](50) NULL,
PRIMARY KEY CLUSTERED 
(
	[maLoaiKhuyenMai] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[LoaiTau]    Script Date: 10/13/2025 12:06:19 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[LoaiTau](
	[maLoaiTau] [varchar](20) NOT NULL,
	[tenLoaiTau] [nvarchar](50) NULL,
PRIMARY KEY CLUSTERED 
(
	[maLoaiTau] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[LoaiToaTau]    Script Date: 10/13/2025 12:06:19 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[LoaiToaTau](
	[maLoaiToa] [varchar](20) NOT NULL,
	[tenLoai] [nvarchar](50) NULL,
PRIMARY KEY CLUSTERED 
(
	[maLoaiToa] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[LoaiVe]    Script Date: 10/13/2025 12:06:19 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[LoaiVe](
	[maLoaiVe] [varchar](20) NOT NULL,
	[tenLoai] [nvarchar](50) NULL,
PRIMARY KEY CLUSTERED 
(
	[maLoaiVe] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[NhanVien]    Script Date: 10/13/2025 12:06:19 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[NhanVien](
	[maNhanVien] [varchar](20) NOT NULL,
	[tenNhanVien] [nvarchar](100) NULL,
	[ngaySinh] [date] NULL,
	[gioiTinh] [bit] NULL,
	[soDienThoai] [varchar](15) NULL,
	[trangThaiLamViec] [nvarchar](50) NULL,
	[maChucVu] [varchar](20) NULL,
	[tenTaiKhoan] [varchar](50) NULL,
	[trangThaiXoa] [bit] NULL,
PRIMARY KEY CLUSTERED 
(
	[maNhanVien] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[TaiKhoan]    Script Date: 10/13/2025 12:06:19 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[TaiKhoan](
	[tenTaiKhoan] [varchar](50) NOT NULL,
	[matKhau] [varchar](50) NULL,
	[email] [nvarchar](100) NULL,
PRIMARY KEY CLUSTERED 
(
	[tenTaiKhoan] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Tau]    Script Date: 10/13/2025 12:06:19 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Tau](
	[maTau] [varchar](20) NOT NULL,
	[tenTau] [nvarchar](100) NULL,
	[maLoaiTau] [varchar](20) NULL,
	[soLanSuaChua] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[maTau] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Thue]    Script Date: 10/13/2025 12:06:19 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Thue](
	[maSoThue] [varchar](20) NOT NULL,
	[tenThue] [nvarchar](50) NULL,
	[mucThue] [decimal](18, 2) NULL,
	[trangThai] [nvarchar](20) NULL,
	[ngayBatDau] [date] NULL,
PRIMARY KEY CLUSTERED 
(
	[maSoThue] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[ToaTau]    Script Date: 10/13/2025 12:06:19 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ToaTau](
	[maToaTau] [varchar](20) NOT NULL,
	[tenToaTau] [nvarchar](100) NULL,
	[thuTuToa] [int] NULL,
	[maLoaiToa] [varchar](20) NULL,
	[soLuongGhe] [int] NULL,
	[heSoHangToa] [float] NULL,
	[maTau] [varchar](20) NULL,
PRIMARY KEY CLUSTERED 
(
	[maToaTau] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[TuyenDuong]    Script Date: 10/13/2025 12:06:19 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[TuyenDuong](
	[maTuyenDuong] [varchar](20) NOT NULL,
	[tenTuyenDuong] [nvarchar](50) NULL,
	[gaKhoiHanh] [varchar](20) NULL,
	[gaKetThuc] [varchar](20) NULL,
	[thoiGianUocTinh] [varchar](20) NULL,
PRIMARY KEY CLUSTERED 
(
	[maTuyenDuong] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Ve]    Script Date: 10/13/2025 12:06:19 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Ve](
	[maVe] [varchar](20) NOT NULL,
	[tenVe] [nvarchar](100) NULL,
	[maChuyenTau] [varchar](20) NULL,
	[maGheNgoi] [varchar](20) NULL,
	[maGaDi] [varchar](20) NULL,
	[maGaDen] [varchar](20) NULL,
	[ngayInVe] [datetime] NULL,
	[maLoaiHanhTrinh] [varchar](20) NULL,
	[maLoaiVe] [varchar](20) NULL,
	[trangThaiVe] [nvarchar](50) NULL,
	[coPhongChoVip] [bit] NULL,
	[maThueApDung] [varchar](20) NULL,
	[maKhachHang] [varchar](20) NULL,
PRIMARY KEY CLUSTERED 
(
	[maVe] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[ChangTau]  WITH CHECK ADD FOREIGN KEY([maChuyenTau])
REFERENCES [dbo].[ChuyenTau] ([maChuyenTau])
GO
ALTER TABLE [dbo].[ChangTau]  WITH CHECK ADD FOREIGN KEY([maGaDen])
REFERENCES [dbo].[GaTau] ([maGaTau])
GO
ALTER TABLE [dbo].[ChangTau]  WITH CHECK ADD FOREIGN KEY([maGaDi])
REFERENCES [dbo].[GaTau] ([maGaTau])
GO
ALTER TABLE [dbo].[ChiTietHoaDon]  WITH CHECK ADD FOREIGN KEY([maHoaDon])
REFERENCES [dbo].[HoaDon] ([maHoaDon])
GO
ALTER TABLE [dbo].[ChiTietHoaDon]  WITH CHECK ADD FOREIGN KEY([maVe])
REFERENCES [dbo].[Ve] ([maVe])
GO
ALTER TABLE [dbo].[ChiTietKhuyenMai]  WITH CHECK ADD FOREIGN KEY([maKhuyenMai])
REFERENCES [dbo].[KhuyenMai] ([maKhuyenMai])
GO
ALTER TABLE [dbo].[ChiTietKhuyenMai]  WITH CHECK ADD FOREIGN KEY([maVe])
REFERENCES [dbo].[Ve] ([maVe])
GO
ALTER TABLE [dbo].[ChuyenTau]  WITH CHECK ADD FOREIGN KEY([maTau])
REFERENCES [dbo].[Tau] ([maTau])
GO
ALTER TABLE [dbo].[ChuyenTau]  WITH CHECK ADD FOREIGN KEY([maTuyenDuong])
REFERENCES [dbo].[TuyenDuong] ([maTuyenDuong])
GO
ALTER TABLE [dbo].[GheNgoi]  WITH CHECK ADD FOREIGN KEY([maToaTau])
REFERENCES [dbo].[ToaTau] ([maToaTau])
GO
ALTER TABLE [dbo].[HoaDon]  WITH CHECK ADD FOREIGN KEY([maKhachHang])
REFERENCES [dbo].[KhachHang] ([maKhachHang])
GO
ALTER TABLE [dbo].[HoaDon]  WITH CHECK ADD FOREIGN KEY([maNhanVienLapHoaDon])
REFERENCES [dbo].[NhanVien] ([maNhanVien])
GO
ALTER TABLE [dbo].[KhuyenMai]  WITH CHECK ADD FOREIGN KEY([maLoaiKhuyenMai])
REFERENCES [dbo].[LoaiKhuyenMai] ([maLoaiKhuyenMai])
GO
ALTER TABLE [dbo].[NhanVien]  WITH CHECK ADD FOREIGN KEY([maChucVu])
REFERENCES [dbo].[ChucVu] ([maChucVu])
GO
ALTER TABLE [dbo].[NhanVien]  WITH CHECK ADD FOREIGN KEY([tenTaiKhoan])
REFERENCES [dbo].[TaiKhoan] ([tenTaiKhoan])
GO
ALTER TABLE [dbo].[Tau]  WITH CHECK ADD FOREIGN KEY([maLoaiTau])
REFERENCES [dbo].[LoaiTau] ([maLoaiTau])
GO
ALTER TABLE [dbo].[ToaTau]  WITH CHECK ADD FOREIGN KEY([maLoaiToa])
REFERENCES [dbo].[LoaiToaTau] ([maLoaiToa])
GO
ALTER TABLE [dbo].[ToaTau]  WITH CHECK ADD FOREIGN KEY([maTau])
REFERENCES [dbo].[Tau] ([maTau])
GO
ALTER TABLE [dbo].[TuyenDuong]  WITH CHECK ADD FOREIGN KEY([gaKetThuc])
REFERENCES [dbo].[GaTau] ([maGaTau])
GO
ALTER TABLE [dbo].[TuyenDuong]  WITH CHECK ADD FOREIGN KEY([gaKhoiHanh])
REFERENCES [dbo].[GaTau] ([maGaTau])
GO
ALTER TABLE [dbo].[Ve]  WITH CHECK ADD FOREIGN KEY([maChuyenTau])
REFERENCES [dbo].[ChuyenTau] ([maChuyenTau])
GO
ALTER TABLE [dbo].[Ve]  WITH CHECK ADD FOREIGN KEY([maGaDen])
REFERENCES [dbo].[GaTau] ([maGaTau])
GO
ALTER TABLE [dbo].[Ve]  WITH CHECK ADD FOREIGN KEY([maGaDi])
REFERENCES [dbo].[GaTau] ([maGaTau])
GO
ALTER TABLE [dbo].[Ve]  WITH CHECK ADD FOREIGN KEY([maGheNgoi])
REFERENCES [dbo].[GheNgoi] ([maGheNgoi])
GO
ALTER TABLE [dbo].[Ve]  WITH CHECK ADD FOREIGN KEY([maLoaiHanhTrinh])
REFERENCES [dbo].[LoaiHanhTrinh] ([maLoaiHanhTrinh])
GO
ALTER TABLE [dbo].[Ve]  WITH CHECK ADD FOREIGN KEY([maLoaiVe])
REFERENCES [dbo].[LoaiVe] ([maLoaiVe])
GO
ALTER TABLE [dbo].[Ve]  WITH CHECK ADD FOREIGN KEY([maThueApDung])
REFERENCES [dbo].[Thue] ([maSoThue])
GO
USE [master]
GO
ALTER DATABASE [QuanLyBanVeTau3] SET  READ_WRITE 
GO









-- Sử dụng cơ sở dữ liệu QuanLyBanVeTau3
USE [QuanLyBanVeTau3]
GO


-- =================================================================
-- 1. Bảng dữ liệu cơ bản (Mã tương ứng với ENUMs)
-- =================================================================

-- Chức Vụ
INSERT INTO [dbo].[ChucVu] ([maChucVu], [tenChucVu]) VALUES
('NhanVienBanVe', N'Nhân Viên Bán Vé'),
('NhanVienQuanLy', N'Nhân Viên Quản Lý');
GO

-- Loại Hành Trình
INSERT INTO [dbo].LoaiHanhTrinh ([maLoaiHanhTrinh], [tenLoai]) VALUES
('Thuong', N'Thường'),
('KhuHoiLuotDi', N'Khứ Hồi Lượt Đi'),
('KhuHoiLuotVe', N'Khứ Hồi Lượt Về');
GO

-- Loại Khuyến Mãi
INSERT INTO [dbo].[LoaiKhuyenMai] ([maLoaiKhuyenMai], [tenLoai]) VALUES
('PhanTram', N'Phần Trăm'),
('SoTienCoDinh', N'Số Tiền Cố Định');
GO

-- Loại Tàu
INSERT INTO [dbo].[LoaiTau] ([maLoaiTau], [tenLoaiTau]) VALUES
('SE', N'Tàu Thống Nhất (SE)'),
('SNT', N'Tàu Sài Gòn - Nha Trang (SNT)'),
('SPT', N'Tàu Sài Gòn - Phan Thiết (SPT)');
GO

-- Loại Toa Tàu
INSERT INTO [dbo].[LoaiToaTau] ([maLoaiToa], [tenLoai]) VALUES
('NgoiCung', N'Ngồi Cứng'),
('NgoiMem', N'Ngồi Mềm'),
('GiuongNamBonCho', N'Giường Nằm Bốn Chỗ'),
('GiuongNamSauCho', N'Giường Nằm Sáu Chỗ');
GO

-- Loại Vé
INSERT INTO [dbo].[LoaiVe] ([maLoaiVe], [tenLoai]) VALUES
('ToanVe', N'Toàn Vé'),
('TreEm', N'Trẻ Em'),
('SinhVien', N'Sinh Viên'),
('MeVNAH', N'Mẹ Việt Nam Anh Hùng'),
('NguoiNuocNgoai', N'Người Nước Ngoài');
GO


-- =================================================================
-- 2. Bảng dữ liệu nghiệp vụ
-- =================================================================

-- Thuế
INSERT INTO [dbo].[Thue] ([maSoThue], [tenThue], [mucThue], [trangThai], [ngayBatDau]) VALUES
('VAT8', N'Thuế GTGT 8%', 0.08, N'Đang áp dụng', '2024-01-01');
GO

-- Ga Tàu
INSERT INTO [dbo].[GaTau] ([maGaTau], [tenGaTau], [diaChiGa], [soDienThoaiGa]) VALUES
('SGO', N'Ga Sài Gòn', N'1 Nguyễn Thông, Phường 9, Quận 3, TP.HCM', '02838436528'),
('DNG', N'Ga Đà Nẵng', N'791 Hải Phòng, Tam Thuận, Thanh Khê, Đà Nẵng', '02363823810'),
('HNO', N'Ga Hà Nội', N'120 Lê Duẩn, Văn Miếu, Đống Đa, Hà Nội', '02438221724'),
('NTR', N'Ga Nha Trang', N'17 Thái Nguyên, Phước Tân, Nha Trang, Khánh Hòa', '02583822113');
GO

-- Tuyến Đường
INSERT INTO [dbo].[TuyenDuong] ([maTuyenDuong], [tenTuyenDuong], [gaKhoiHanh], [gaKetThuc], [thoiGianUocTinh]) VALUES
('TD-SGHN', N'Sài Gòn - Hà Nội', 'SGO', 'HNO', '33 giờ'),
('TD-SGNT', N'Sài Gòn - Nha Trang', 'SGO', 'NTR', '7 giờ');
GO

-- Tàu
INSERT INTO [dbo].[Tau] ([maTau], [tenTau], [maLoaiTau], [soLanSuaChua]) VALUES
('SE1', N'Tàu Thống Nhất SE1', 'SE', 5),
('SNT2', N'Tàu SNT2', 'SNT', 2);
GO

-- Toa Tàu (CẬP NHẬT maLoaiToa)
-- Toa của tàu SE1
INSERT INTO [dbo].[ToaTau] ([maToaTau], [tenToaTau], [thuTuToa], [maLoaiToa], [soLuongGhe], [heSoHangToa], [maTau]) VALUES
('SE1-TOA01', N'Toa 1 SE1', 1, 'GiuongNamBonCho', 28, 1.5, 'SE1'),
('SE1-TOA02', N'Toa 2 SE1', 2, 'NgoiMem', 64, 1.2, 'SE1');
-- Toa của tàu SNT2
INSERT INTO [dbo].[ToaTau] ([maToaTau], [tenToaTau], [thuTuToa], [maLoaiToa], [soLuongGhe], [heSoHangToa], [maTau]) VALUES
('SNT2-TOA01', N'Toa 1 SNT2', 1, 'NgoiMem', 64, 1.2, 'SNT2');
GO

-- Ghế Ngồi
INSERT INTO [dbo].[GheNgoi] ([maGheNgoi], [viTriGhe], [maToaTau]) VALUES
('SE1T1G1', N'Buồng 1 - Giường 1', 'SE1-TOA01'),
('SE1T1G2', N'Buồng 1 - Giường 2', 'SE1-TOA01'),
('SNT2T1G15', N'Ghế 15', 'SNT2-TOA01'),
('SNT2T1G16', N'Ghế 16', 'SNT2-TOA01');
GO

-- Chuyến Tàu
INSERT INTO [dbo].[ChuyenTau] ([maChuyenTau], [maTau], [maTuyenDuong], [ngayGioKhoiHanh], [ngayGioDen], [donGiaCoBan]) VALUES
('CT-SE1-20251020', 'SE1', 'TD-SGHN', '2025-10-20 19:30:00', '2025-10-22 04:30:00', 900000.00),
('CT-SNT2-20251025', 'SNT2', 'TD-SGNT', '2025-10-25 20:00:00', '2025-10-26 03:00:00', 450000.00);
GO

-- Chặng Tàu
INSERT INTO [dbo].[ChangTau] ([maChangTau], [maChuyenTau], [maGaDi], [maGaDen], [thoiGianDi], [thoiGianDen], [soKm], [soThuTu]) VALUES
('CTG-SE1-SGO-NTR', 'CT-SE1-20251020', 'SGO', 'NTR', '2025-10-20 19:30:00', '2025-10-21 02:30:00', 411, 1),
('CTG-SE1-NTR-DNG', 'CT-SE1-20251020', 'NTR', 'DNG', '2025-10-21 02:45:00', '2025-10-21 12:00:00', 524, 2),
('CTG-SE1-DNG-HNO', 'CT-SE1-20251020', 'DNG', 'HNO', '2025-10-21 12:30:00', '2025-10-22 04:30:00', 791, 3),
('CTG-SNT2-SGO-NTR', 'CT-SNT2-20251025', 'SGO', 'NTR', '2025-10-25 20:00:00', '2025-10-26 03:00:00', 411, 1);
GO

-- Tài Khoản & Nhân Viên (CẬP NHẬT maChucVu)
INSERT INTO [dbo].[TaiKhoan] ([tenTaiKhoan], [matKhau], [email]) VALUES
('ql_vana', '123456', 'vana@email.com'),
('nv_vanb', '123456', 'vanb@email.com');
GO

INSERT INTO [dbo].[NhanVien] ([maNhanVien], [tenNhanVien], [ngaySinh], [gioiTinh], [soDienThoai], [trangThaiLamViec], [maChucVu], [tenTaiKhoan], [trangThaiXoa]) VALUES
('NV01', N'Nguyễn Văn A', '1990-05-15', 1, '0905111222', N'Đang làm việc', 'NhanVienQuanLy', 'ql_vana', 0),
('NV02', N'Trần Thị B', '1995-08-20', 0, '0913333444', N'Đang làm việc', 'NhanVienBanVe', 'nv_vanb', 0);
GO

-- Khách Hàng
INSERT INTO [dbo].[KhachHang] ([maKhachHang], [hoTenKhachHang], [soGiayTo], [ngaySinh], [soDienThoai], [gioiTinh]) VALUES
('KH001', N'Lê Văn C', '079200001111', '2000-01-25', '0987654321', 1),
('KH002', N'Phạm Thị D', '079201002222', '2001-02-10', '0987123456', 0);
GO

-- Khuyến Mãi (CẬP NHẬT maLoaiKhuyenMai)
INSERT INTO [dbo].[KhuyenMai] ([maKhuyenMai], [tenKhuyenMai], [moTa], [maLoaiKhuyenMai], [giaTriKhuyenMai], [ngayBatDau], [ngayKetThuc], [dieuKienApDung], [trangThai]) VALUES
('KM30/4', N'Chào mừng 30/4', N'Giảm 10% cho tất cả các vé', 'PhanTram', 0.10, '2025-04-25', '2025-05-02', N'Áp dụng cho mọi hóa đơn', N'Hết hạn'),
('KMT10', N'Chào tháng 10', N'Giảm 50,000 VNĐ cho vé trên 1,000,000 VNĐ', 'SoTienCoDinh', 50000.00, '2025-10-01', '2025-10-31', N'Hóa đơn có giá trị từ 1,000,000 VNĐ', N'Còn hạn');
GO

-- Vé (CẬP NHẬT maLoaiHanhTrinh và maLoaiVe)
INSERT INTO [dbo].[Ve] ([maVe], [tenVe], [maChuyenTau], [maGheNgoi], [maGaDi], [maGaDen], [ngayInVe], [maLoaiHanhTrinh], [maLoaiVe], [trangThaiVe], [coPhongChoVip], [maThueApDung], [maKhachHang]) VALUES
('VE00001', N'Vé Sài Gòn - Hà Nội', 'CT-SE1-20251020', 'SE1T1G1', 'SGO', 'HNO', '2025-10-13 09:00:00', 'Thuong', 'ToanVe', N'Đã thanh toán', 1, 'VAT8', 'KH001'),
('VE00002', N'Vé Sài Gòn - Nha Trang', 'CT-SNT2-20251025', 'SNT2T1G15', 'SGO', 'NTR', '2025-10-13 09:05:00', 'Thuong', 'SinhVien', N'Chưa thanh toán', 0, 'VAT8', 'KH002');
GO

-- Hóa Đơn
INSERT INTO [dbo].[HoaDon] ([maHoaDon], [maKhachHang], [maNhanVienLapHoaDon], [ngayLapHoaDon], [trangThaiHoaDon], [donViBanHang]) VALUES
('HD00001', 'KH001', 'NV02', '2025-10-13 09:00:00', N'Đã hoàn thành', N'Ga Sài Gòn');
GO

-- Chi Tiết Hóa Đơn
INSERT INTO [dbo].[ChiTietHoaDon] ([maHoaDon], [maVe], [tenDichVu], [donViTinh]) VALUES
('HD00001', 'VE00001', N'Dịch vụ ăn uống', N'Phần');
GO

-- Chi Tiết Khuyến Mãi
INSERT INTO [dbo].[ChiTietKhuyenMai] ([maVe], [maKhuyenMai], [ngayApDung]) VALUES
('VE00001', 'KMT10', '2025-10-13 09:00:00');
GO