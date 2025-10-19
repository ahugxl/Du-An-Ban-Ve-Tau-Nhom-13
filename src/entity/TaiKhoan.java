package entity;

import java.util.Objects;

public class TaiKhoan {
	private String tenTaiKhoan;
	private String matKhau;
	private String email;
	
	public TaiKhoan() {
	}
	
	public TaiKhoan(String tenTaiKhoan) {
		super();
		this.tenTaiKhoan = tenTaiKhoan;
	}

	public TaiKhoan(String tenTaiKhoan, String matKhau, String email) {
		this.tenTaiKhoan = tenTaiKhoan;
		this.matKhau = matKhau;
		this.email = email;
	}

	public String getTenTaiKhoan() {
		return tenTaiKhoan;
	}

	public String getMatKhau() {
		return matKhau;
	}

	public String getEmail() {
		return email;
	}

	public void setTenTaiKhoan(String tenTaiKhoan) {
		this.tenTaiKhoan = tenTaiKhoan;
	}

	public void setMatKhau(String matKhau) {
		this.matKhau = matKhau;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public int hashCode() {
		return Objects.hash(tenTaiKhoan);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TaiKhoan other = (TaiKhoan) obj;
		return Objects.equals(tenTaiKhoan, other.tenTaiKhoan);
	}

	@Override
	public String toString() {
		return "TaiKhoan [tenTaiKhoan=" + tenTaiKhoan + ", matKhau=" + matKhau + ", email=" + email + "]";
	}
	
}
