package entity;

import java.util.Objects;

public class LoaiVe {
    private String maLoaiVe;
    private String tenLoaiVe;
    private double tiLeGiamGia;

    public LoaiVe() {
    }

    public LoaiVe(String maLoaiVe, String tenLoaiVe, double tiLeGiamGia) {
        this.maLoaiVe = maLoaiVe;
        this.tenLoaiVe = tenLoaiVe;
        this.tiLeGiamGia = tiLeGiamGia;
    }

    // --- Getters and Setters ---
    public String getMaLoaiVe() {
        return maLoaiVe;
    }

    public void setMaLoaiVe(String maLoaiVe) {
        this.maLoaiVe = maLoaiVe;
    }

    public String getTenLoaiVe() {
        return tenLoaiVe;
    }

    public void setTenLoaiVe(String tenLoaiVe) {
        this.tenLoaiVe = tenLoaiVe;
    }

    public double getTiLeGiamGia() {
        return tiLeGiamGia;
    }

    public void setTiLeGiamGia(double tiLeGiamGia) {
        this.tiLeGiamGia = tiLeGiamGia;
    }

    // --- hashCode, equals và toString ---
    @Override
    public int hashCode() {
        return Objects.hash(maLoaiVe);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        LoaiVe other = (LoaiVe) obj;
        return Objects.equals(maLoaiVe, other.maLoaiVe);
    }

    @Override
    public String toString() {
        return tenLoaiVe; // Giúp hiển thị tên trong ComboBox
    }
}