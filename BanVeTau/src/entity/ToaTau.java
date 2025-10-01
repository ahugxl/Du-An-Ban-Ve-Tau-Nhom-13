package entity;


public class ToaTau {
    private String maToaTau;
    private String tenToaTau;

    public ToaTau(String maToaTau, String tenToaTau) {
        this.maToaTau = maToaTau;
        this.tenToaTau = tenToaTau;
    }
    
    // Thêm getter...
    public String getMaToaTau() { return maToaTau; }
    public String getTenToaTau() { return tenToaTau; }
}
