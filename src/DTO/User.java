package DTO;

public class User {
	private String manv;
	private String username;
	private String password;
	private String macv;
	private int trangthai;

	public User(String manv, String username, String password, String macv, int trangthai) {
		super();
		this.manv = manv;
		this.username = username;
		this.password = password;
		this.macv = macv;
		this.trangthai = trangthai;
	}

	public String getManv() {
		return manv;
	}

	public void setManv(String manv) {
		this.manv = manv;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMacv() {
		return macv;
	}

	public void setMacv(String macv) {
		this.macv = macv;
	}

	public int getTrangthai() {
		return trangthai;
	}

	public void setTrangthai(int trangthai) {
		this.trangthai = trangthai;
	}

}