package entity;

import java.util.Objects;

public class GheNgoi_mthanh {
	private String maGheNgoi;
	private int viTriGhe;
	private ToaTau toaTau;
	public GheNgoi_mthanh() {
	}
	
	public GheNgoi_mthanh(String maGheNgoi) {
		super();
		this.maGheNgoi = maGheNgoi;
	}

	

	public GheNgoi_mthanh(String maGheNgoi, int viTriGhe, ToaTau toaTau) {
		super();
		this.maGheNgoi = maGheNgoi;
		this.viTriGhe = viTriGhe;
		this.toaTau = toaTau;
	}

	public String getMaGheNgoi() {
		return maGheNgoi;
	}

	public int getViTriGhe() {
		return viTriGhe;
	}

	public ToaTau getToaTau() {
		return toaTau;
	}

	public void setMaGheNgoi(String maGheNgoi) {
		this.maGheNgoi = maGheNgoi;
	}

	public void setViTriGhe(int viTriGhe) {
		this.viTriGhe = viTriGhe;
	}

	public void setToaTau(ToaTau toaTau) {
		this.toaTau = toaTau;
	}

	@Override
	public int hashCode() {
		return Objects.hash(maGheNgoi);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GheNgoi_mthanh other = (GheNgoi_mthanh) obj;
		return Objects.equals(maGheNgoi, other.maGheNgoi);
	}

	@Override
	public String toString() {
		return "GheNgoi [maGheNgoi=" + maGheNgoi + ", viTriGhe=" + viTriGhe + ", toaTau=" + toaTau + "]";
	}

}
