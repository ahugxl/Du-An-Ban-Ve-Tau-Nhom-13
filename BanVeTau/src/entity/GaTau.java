package entity;

import java.util.Objects;

public class GaTau {
	private String maGaTau;
	private String tenGaTau;
	private String diaChiGa;
	private String soDienThoaiGa;
	
	public GaTau() {
	}
	
	public GaTau(String maGaTau, String tenGaTau) {
		super();
		this.maGaTau = maGaTau;
		this.tenGaTau = tenGaTau;
	}

	public GaTau(String maGaTau, String tenGaTau, String diaChiGa, String soDienThoaiGa) {
		this.maGaTau = maGaTau;
		this.tenGaTau = tenGaTau;
		this.diaChiGa = diaChiGa;
		this.soDienThoaiGa = soDienThoaiGa;
	}
	
	public GaTau(String maGaTau) {
		super();
		this.maGaTau = maGaTau;
	}

	public String getMaGaTau() {
		return maGaTau;
	}
	
	public String getTenGaTau() {
		return tenGaTau;
	}
	
	public String getDiaChiGa() {
		return diaChiGa;
	}
	
	public String getSoDienThoaiGa() {
		return soDienThoaiGa;
	}
	
	public void setMaGaTau(String maGaTau) {
		this.maGaTau = maGaTau;
	}
	
	public void setTenGaTau(String tenGaTau) {
		this.tenGaTau = tenGaTau;
	}
	
	public void setDiaChiGa(String diaChiGa) {
		this.diaChiGa = diaChiGa;
	}
	
	public void setSoDienThoaiGa(String soDienThoaiGa) {
		this.soDienThoaiGa = soDienThoaiGa;
	}

	@Override
	public int hashCode() {
		return Objects.hash(maGaTau);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GaTau other = (GaTau) obj;
		return Objects.equals(maGaTau, other.maGaTau);
	}

	@Override
	public String toString() {
		return "GaTau [maGaTau=" + maGaTau + ", tenGaTau=" + tenGaTau + ", diaChiGa=" + diaChiGa + ", soDienThoaiGa="
				+ soDienThoaiGa + "]";
	}
	
}
