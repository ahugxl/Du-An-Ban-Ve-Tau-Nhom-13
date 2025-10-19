package entity;

import java.util.Objects;

public class GheNgoi_mthanh {
	private String maGheNgoi;
	private int viTriGhe;
	private ToaTau toaTau;

	/**
	 * Constructor không tham số.
	 */
	public GheNgoi_mthanh() {
	}
	
	/**
	 * Constructor với mã ghế.
	 * @param maGheNgoi
	 */
	public GheNgoi_mthanh(String maGheNgoi) {
		this.maGheNgoi = maGheNgoi;
	}

	/**
	 * Constructor đầy đủ các thuộc tính.
	 * @param maGheNgoi
	 * @param viTriGhe
	 * @param toaTau
	 */
	public GheNgoi_mthanh(String maGheNgoi, int viTriGhe, ToaTau toaTau) {
		this.maGheNgoi = maGheNgoi;
		this.viTriGhe = viTriGhe;
		this.toaTau = toaTau;
	}

	// --- Getters and Setters ---
	
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

	// --- hashCode, equals, và toString ---

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
		// Ép kiểu về đúng tên lớp đã thống nhất
		GheNgoi_mthanh other = (GheNgoi_mthanh) obj;
		return Objects.equals(maGheNgoi, other.maGheNgoi);
	}

	@Override
	public String toString() {
		return "GheNgoi [maGheNgoi=" + maGheNgoi + ", viTriGhe=" + viTriGhe + ", toaTau=" + (toaTau != null ? toaTau.getMaToaTau() : "null") + "]";
	}
}