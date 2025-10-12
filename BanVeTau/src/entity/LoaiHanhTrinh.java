package entity;

public enum LoaiHanhTrinh {
	Thuong,
	KhuHoiLuotDi,
	KhuHoiLuotVe;
	public String getDisplayName() {
        switch (this) {
            case Thuong:          
            	return "Thường";
            case KhuHoiLuotDi:           
            	return "Khứ hoi lượt đi";
            case KhuHoiLuotVe:        
            	return "Khứ hồi lượt về";
            default:              
            	return name();
        }
    }
}
