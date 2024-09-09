package BUS;

import java.util.ArrayList;
import java.util.Random;

import DATA.SachDAO;
import DATA.TacgiaDAO;
import DTO.LoaiSach;
import DTO.NhaXuatBan;
import DTO.Sach;
import DTO.TacGia;

public class SachBUS {
	private ArrayList<Sach> List_Sach;
	private Integer solg_Sach;
	private ArrayList<NhaXuatBan> list_nhaxuatban;
	private ArrayList<LoaiSach> list_loaisach;
	private ArrayList<TacGia> list_tacgia;

	public SachBUS() {
		DanhSachlist();
	}

	public ArrayList<Sach> getList_Sach() {
		return this.List_Sach;
	}

	public void setList_Sach(ArrayList<Sach> List_Sach) {
		this.List_Sach = List_Sach;
	}

	public ArrayList<NhaXuatBan> get_ListNhaXuatBan() {
		return list_nhaxuatban;
	}

	public void set_ListNhaXuatBan(ArrayList<NhaXuatBan> list_nhaxuatban) {
		this.list_nhaxuatban = list_nhaxuatban;
	}

	public ArrayList<LoaiSach> get_ListLoaiSach() {
		return list_loaisach;
	}

	public void set_ListLoaiSach(ArrayList<LoaiSach> list_loaisach) {
		this.list_loaisach = list_loaisach;
	}

	public ArrayList<TacGia> get_ListTacGia() {
		return list_tacgia;
	}

	public void set_ListTacGia(ArrayList<TacGia> list_tacgia) {
		this.list_tacgia = list_tacgia;
	}

	public Integer getSolg_Sach() {
		return this.solg_Sach;
	}

	public void setSolg_Sach(int solg_Sach) {
		this.solg_Sach = solg_Sach;
	}

	// Lấy dữ liệu từ data để đổ xuống mảng
	public void DanhSachlist() {
		SachDAO obj = new SachDAO();

		this.List_Sach = obj.getSach_Data();
		this.list_tacgia = obj.getListTacGia();
		this.list_loaisach = obj.getListLoaiSach();
		this.list_nhaxuatban = obj.getListNhaXuatBan();
		this.solg_Sach = this.List_Sach.size();
	}

	// Tạo ra một mã độc giả
	public String TaoMaSach() {
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
	public String TaoMaSach_DuyNhat() {
		int ktr = 1;
		String ma;
		while (true) {
			ma = TaoMaSach();
			for (int i = 0; i < this.solg_Sach; i++) {
				if (this.List_Sach.get(i).getMaSach().equals(ma))
					ktr = 0;
			}
			if (ktr == 1)
				break;
		}
		return ma;
	}

	// Thêm tên độc giả trong mảng
	public void ThemSach_Arr(Sach sach) {
		this.List_Sach.add(sach);
		this.solg_Sach = this.List_Sach.size();
	}

	public int TimViTriTrongMang_Arr(Sach sach) {
		int n = this.solg_Sach;
		for (int i = 0; i < n; i++) {
			if (this.List_Sach.get(i).getMaSach().equals(sach.getMaSach())) {
				return i;
			}
		}
		return -1;
	}

	// Xóa tác giả khỏi mảng
	public void XoaSach_Array(Sach sach) {
		int vitri = TimViTriTrongMang_Arr(sach);
		if (vitri == -1) {
			System.out.printf("\n Độc giả không tồn tại trong hệ thống ");
		} else {
			this.List_Sach.remove(vitri);
			this.solg_Sach = this.List_Sach.size();
		}
	}

	public void EditSach_Array(Sach sach) {
		int vitri = TimViTriTrongMang_Arr(sach);
		if (vitri == -1) {
			System.out.printf("\nTác giả không tồn tại trong hệ thống");
		} else {
			this.List_Sach.set(vitri, sach);
		}
	}

	// Tìm theo tên
	public ArrayList<Sach> Tim_Theo_Ten(String id) {
		ArrayList<Sach> List_SachTrung = new ArrayList<>();
		id = id.isEmpty() ? id = "" : id;
		for (int i = 0; i < this.solg_Sach; i++) {
			if (this.List_Sach.get(i).getTenSach().contains(id)) {
				List_SachTrung.add(this.List_Sach.get(i));
			}
		}
		return List_SachTrung;
	}

	public ArrayList<Sach> Tim_Theo_ID_Sach(String id) {
		ArrayList<Sach> List_SachTrung = new ArrayList<>();
		id = id.isEmpty() ? id = "" : id;
		for (int i = 0; i < this.solg_Sach; i++) {
			if (this.List_Sach.get(i).getMaSach().contains(id)) {
				List_SachTrung.add(this.List_Sach.get(i));
			}
		}
		return List_SachTrung;
	}

	public ArrayList<Sach> Tim_Theo_Ten_TG(String id) {
		ArrayList<Sach> List_SachTrung = new ArrayList<>();
		id = id.isEmpty() ? id = "" : id;
		for (int i = 0; i < this.solg_Sach; i++) {
			if (this.List_Sach.get(i).getTacGia().getTenTG().contains(id)) {
				List_SachTrung.add(this.List_Sach.get(i));
			}
		}
		return List_SachTrung;
	}

	public ArrayList<Sach> Tim_Theo_Ten_NXB(String id) {
		ArrayList<Sach> List_SachTrung = new ArrayList<>();
		id = id.isEmpty() ? id = "" : id;
		for (int i = 0; i < this.solg_Sach; i++) {
			if (this.List_Sach.get(i).getMaNXB().getTenNXB().contains(id)) {
				List_SachTrung.add(this.List_Sach.get(i));
			}
		}
		return List_SachTrung;
	}

	public ArrayList<Sach> Tim_Theo_Ten_LS(String id) {
		ArrayList<Sach> List_SachTrung = new ArrayList<>();
		id = id.isEmpty() ? id = "" : id;
		for (int i = 0; i < this.solg_Sach; i++) {
			if (this.List_Sach.get(i).getMaLS().getTenLoai().contains(id)) {
				List_SachTrung.add(this.List_Sach.get(i));
			}
		}
		return List_SachTrung;
	}

	public ArrayList<Sach> Tim_Theo_MoTa(String id) {
		ArrayList<Sach> List_SachTrung = new ArrayList<>();
		id = id.isEmpty() ? id = "" : id;
		for (int i = 0; i < this.solg_Sach; i++) {
			if (this.List_Sach.get(i).getMoTa().contains(id)) {
				List_SachTrung.add(this.List_Sach.get(i));
			}
		}
		return List_SachTrung;
	}

	public ArrayList<Sach> Tim_Theo_Gia(int id) {
		ArrayList<Sach> List_SachTrung = new ArrayList<>();
		for (int i = 0; i < this.solg_Sach; i++) {
			if (this.List_Sach.get(i).getDonGia() == id) {
				List_SachTrung.add(this.List_Sach.get(i));
			}
		}
		return List_SachTrung;
	}

	public void ThemNhieuSach_Arr(ArrayList<Sach> s) {
		for (int i = 0; i < s.size(); i++) {
			this.List_Sach.add(s.get(i));
		}
	}

}
