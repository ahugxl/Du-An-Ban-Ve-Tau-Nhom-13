package entity;

import java.time.LocalDateTime;

public class ChuyenTau {
    private String maChuyenTau;
    private String tenTau;
    private String maTau; // Dùng để liên kết đến ToaTau
    private LocalDateTime thoiGianDi;
    private LocalDateTime thoiGianDen;
    private int soLuongDatCho;
    private int soLuongChoTrong;

    public ChuyenTau(String maChuyenTau, String tenTau, String maTau, LocalDateTime thoiGianDi, LocalDateTime thoiGianDen, int soLuongDatCho, int soLuongChoTrong) {
        this.maChuyenTau = maChuyenTau;
        this.tenTau = tenTau;
        this.maTau = maTau;
        this.thoiGianDi = thoiGianDi;
        this.thoiGianDen = thoiGianDen;
        this.soLuongDatCho = soLuongDatCho;
        this.soLuongChoTrong = soLuongChoTrong;
    }

    // Thêm các hàm getter cho tất cả các thuộc tính...
    public String getMaChuyenTau() { return maChuyenTau; }
    public String getTenTau() { return tenTau; }
    public String getMaTau() { return maTau; }
    public LocalDateTime getThoiGianDi() { return thoiGianDi; }
    public LocalDateTime getThoiGianDen() { return thoiGianDen; }
    public int getSoLuongDatCho() { return soLuongDatCho; }
    public int getSoLuongChoTrong() { return soLuongChoTrong; }
}
