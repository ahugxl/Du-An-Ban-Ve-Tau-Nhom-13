package entity;

public class NhanVien {
    private String tenNV;
    private String taiKhoan;
    private String matKhau;
    private boolean quanLy;

    public NhanVien(String tenNV, String taiKhoan, String matKhau, boolean quanLy) {
        super();
        this.tenNV = tenNV;
        this.taiKhoan = taiKhoan;
        this.matKhau = matKhau;
        this.quanLy = quanLy;
    }

    // THÊM CÁC PHƯƠNG THỨC GETTER VÀO ĐÂY
    public String getTenNV() {
        return tenNV;
    }

    public String getTaiKhoan() {
        return taiKhoan;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public boolean isQuanLy() {
        return quanLy;
    }
}