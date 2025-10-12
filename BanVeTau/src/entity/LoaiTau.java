package entity;

public enum LoaiTau {
	SE,
	SNT,
	SPT;
	public String getDisplayName() {
		switch (this) {
			case SE:          
				return "Tàu SE";
			case SNT:           
				return "Tàu SNT";
			case SPT:        
				return "Tàu SPT";
			default:              
				return name();
		}
	}
}
