package entity;

public enum LoaiKhuyenMai {
	PhanTram,
	SoTienCoDinh;
	public String getDisplayName() {
        switch (this) {
            case PhanTram:          
            	return "Phần trăm";
            case SoTienCoDinh:           
            	return "Số tiền cố định";
            default:              
            	return name();
        }
    }
}
