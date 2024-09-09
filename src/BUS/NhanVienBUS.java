package BUS;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.regex.Pattern;

import DATA.NhanVienDAO;
import DTO.NhanVien;
import DTO.User;

public class NhanVienBUS {
	private ArrayList<NhanVien> dsnv;

	public NhanVienBUS(int i1) {
		listNV();
	}

	public NhanVienBUS() {

	}

	public NhanVien get(String MaNV) {
		for (NhanVien nv : dsnv) {
			if (nv.getMaNV().equals(MaNV)) {
				return nv;
			}
		}
		return null;
	}

	public void listNV() {
		NhanVienDAO nvDAO = new NhanVienDAO();
		dsnv = new ArrayList<>();
		dsnv = nvDAO.list();
	}

	public void addNV(NhanVien sp) {
		NhanVienDAO nvDAO = new NhanVienDAO();
		nvDAO.add(sp);
		dsnv.add(sp);
	}

	public void add_all(ArrayList<NhanVien> nv) {
		for (int i = 0; i < nv.size(); i++) {
			dsnv.add(nv.get(i));
		}
	}

	@SuppressWarnings("unlikely-arg-type")
	public void deleteNV(NhanVien MaNV) {
		for (NhanVien nv : dsnv) {
			if (nv.getMaNV().equals(MaNV)) {
				dsnv.remove(nv);
				NhanVienDAO nvDAO = new NhanVienDAO();
				nvDAO.delete(MaNV);
				return;
			}
		}
	}

	public void delete2(String MaNV) {
		for (NhanVien nv : dsnv) {
			if (nv.getMaNV().equals(MaNV)) {
				dsnv.remove(nv);
				NhanVienDAO nvDAO = new NhanVienDAO();
				nvDAO.delete2(MaNV);
				return;
			}
		}
	}

	public void setNV(NhanVien s) {
		for (int i = 0; i < dsnv.size(); i++) {
			if (dsnv.get(i).getMaNV().equals(s.getMaNV())) {
				dsnv.set(i, s);
				NhanVienDAO nvDAO = new NhanVienDAO();
				nvDAO.set(s);
				return;
			}
		}
	}

	public boolean check(String manv) {
		for (NhanVien nv : dsnv) {
			if (nv.getMaNV().equals(manv)) {
				return true;
			}
		}
		return false;
	}

	public ArrayList<NhanVien> search_manv(String manv) {
		ArrayList<NhanVien> search = new ArrayList<>();

		manv = manv.isEmpty() ? manv = "" : manv;
		// hoten = hoten.isEmpty() ? hoten = "" : hoten;
		// email=email.isEmpty() ? email ="": email;
		// sdt=sdt.isEmpty()? sdt ="": sdt;
		for (NhanVien nv : dsnv) {
			if (nv.getMaNV().contains(manv))

			{
				search.add(nv);
			}
		}
		return search;
	}

	public ArrayList<NhanVien> search_hoten(String hoten) {
		ArrayList<NhanVien> search = new ArrayList<>();

		// manv = manv.isEmpty() ? manv = "" : manv;
		hoten = hoten.isEmpty() ? hoten = "" : hoten;
		// email=email.isEmpty() ? email ="": email;
		// sdt=sdt.isEmpty()? sdt ="": sdt;
		for (NhanVien nv : dsnv) {
			if (nv.getHoTen().contains(hoten))

			{
				search.add(nv);
			}
		}
		return search;
	}

	public ArrayList<NhanVien> search_sdt(String sdt) {
		ArrayList<NhanVien> search = new ArrayList<>();

		sdt = sdt.isEmpty() ? sdt = "" : sdt;
		for (NhanVien nv : dsnv) {
			if (nv.getSDT().contains(sdt))

			{
				search.add(nv);
			}
		}
		return search;
	}

	public ArrayList<NhanVien> search_email(String email) {
		ArrayList<NhanVien> search = new ArrayList<>();
		email = email.isEmpty() ? email = "" : email;
		// sdt=sdt.isEmpty()? sdt ="": sdt;
		for (NhanVien nv : dsnv) {
			if (nv.getEmail().contains(email))

			{
				search.add(nv);
			}
		}
		return search;
	}

	public String removeAccent(String s) {
		String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
		Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
		temp = pattern.matcher(temp).replaceAll("");
		return temp.replaceAll("đ", "d");
	}

	public ArrayList<NhanVien> getList() {
		return dsnv;
	}

	// Tạo ra một mã độc giả
	public String TaoMaNV() {
		int CODE_LENGTH = 4;
		String DIGITS = "0123456789";
		Random random = new Random();
		StringBuilder codeBuilder = new StringBuilder(CODE_LENGTH);
		for (int i = 0; i < CODE_LENGTH; i++) {
			codeBuilder.append(DIGITS.charAt(random.nextInt(DIGITS.length())));
		}
		return codeBuilder.toString();
	}

	// Tạo mã độc giả mới không trùng với bất kì mã nào trong hệ thống
	public String TaoMaNV_DuyNhat() {
		int ktr = 1;
		String ma;
		while (true) {
			ma = TaoMaNV();
			for (int i = 0; i < dsnv.size(); i++) {
				if (this.dsnv.get(i).getMaNV().equals(ma))
					ktr = 0;
			}
			if (ktr == 1)
				break;
		}
		return ma;
	}

}
