package entity;

public enum ChucVu {
	NhanVienBanVe,
	NhanVienQuanLy;
	public String getDisplayName() {
        switch (this) {
            case NhanVienBanVe:          
            	return "Nhân viên bán vé";
            case NhanVienQuanLy:           
            	return "Nhân viên quản lý";
            default:              
            	return name();
        }
    }
}
