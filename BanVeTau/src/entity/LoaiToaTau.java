package entity;

public enum LoaiToaTau {
	NgoiCung,
	NgoiMem,
	GiuongNamBonCho,
	GiuongNamSauCho;
	public String getDisplayName() {
		switch (this) {
			case NgoiCung:          
				return "Ngồi cứng";
			case NgoiMem:           
				return "Ngồi mềm";
			case GiuongNamBonCho:        
				return "Giường nằm bốn chỗ";
			case GiuongNamSauCho:        
				return "Giường nằm sáu chỗ";
			default:              
				return name();
		}
	}
}
