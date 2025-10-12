package entity;

public enum LoaiVe {
	ToanVe,
	TreEm,
	SinhVien,
	MeVNAH,
	NguoiNuocNgoai;
	public String getDisplayName() {
        switch (this) {
            case ToanVe:          
            	return "Toàn vé";
            case TreEm:           
            	return "Trẻ em";
            case SinhVien:        
            	return "Sinh viên";
            case MeVNAH:          
            	return "Mẹ VNAH";
            case NguoiNuocNgoai:  
            	return "Người nước ngoài";
            default:              
            	return name();
        }
    }
}
