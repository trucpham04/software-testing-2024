/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BUS;

import DATA.PhieuTraDAO;
import DTO.CTPhieuMuon;
import DTO.DocGia;
import DTO.LoaiSach;
import DTO.NhanVien;
import DTO.PhieuMuon;
import DTO.PhieuTra;
import DTO.SachCT;
import DTO.TacGia;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Admin
 */
public class PhieuTraBUS {
    private ArrayList<PhieuTra> list_phieutra;
    private ArrayList<LoaiSach> list_loaisach;
    private ArrayList<SachCT> list_sach;
    private ArrayList<NhanVien> list_nhanvien;
    private ArrayList<PhieuMuon> list_phieumuon;
    private ArrayList<DocGia> list_docgia;
    private ArrayList<CTPhieuMuon> list_ctpm;

    public PhieuTraBUS() {
        getDanhSachList();
    }

    public ArrayList<PhieuTra> getList_phieutra() {
        return list_phieutra;
    }

    public void setList_phieutra(ArrayList<PhieuTra> list_phieutra) {
        this.list_phieutra = list_phieutra;
    }

    public ArrayList<LoaiSach> getList_loaisach() {
        return list_loaisach;
    }

    public void setList_loaisach(ArrayList<LoaiSach> list_loaisach) {
        this.list_loaisach = list_loaisach;
    }

    public ArrayList<SachCT> getList_sach() {
        return list_sach;
    }

    public void setList_sach(ArrayList<SachCT> list_sach) {
        this.list_sach = list_sach;
    }

    public ArrayList<NhanVien> getList_nhanvien() {
        return list_nhanvien;
    }

    public void setList_nhanvien(ArrayList<NhanVien> list_nhanvien) {
        this.list_nhanvien = list_nhanvien;
    }

    public ArrayList<CTPhieuMuon> getList_ctpm() {
        return list_ctpm;
    }

    public void setList_ctpm(ArrayList<CTPhieuMuon> list_ctpm) {
        this.list_ctpm = list_ctpm;
    }

    public ArrayList<SachCT> get_ListSach() {
        return list_sach;
    }

    public ArrayList<DocGia> getList_docgia() {
        return list_docgia;
    }

    public void setList_docgia(ArrayList<DocGia> list_docgia) {
        this.list_docgia = list_docgia;
    }

    public void set_ListSach(ArrayList<SachCT> list_sach) {
        this.list_sach = list_sach;
    }

    public ArrayList<NhanVien> get_ListNhanVien() {
        return list_nhanvien;
    }

    public void set_ListNhanVien(ArrayList<NhanVien> list_phieutra) {
        this.list_nhanvien = list_phieutra;
    }

    public ArrayList<PhieuTra> get_ListPhieuTra() {
        return list_phieutra;
    }

    public void set_ListLoaiSach(ArrayList<LoaiSach> list_loaisach) {
        this.list_loaisach = list_loaisach;
    }

    public ArrayList<LoaiSach> get_ListLoaiSach() {
        return list_loaisach;
    }

    public void set_ListPhieuTra(ArrayList<PhieuTra> list_phieutra) {
        this.list_phieutra = list_phieutra;
    }

    public void setList_phieumuon(ArrayList<PhieuMuon> list_phieumuon) {
        this.list_phieumuon = list_phieumuon;
    }

    public ArrayList<PhieuMuon> getList_phieumuon() {
        return list_phieumuon;
    }

    public void XoaPhieuMuon1(String obj) {
        int vitri = TimViTriTrongMang_Arr1(obj);
        if (vitri == -1) {
            System.out.printf("\n Phiếu Mượn không tồn tại trong hệ thống ");
        } else {
            this.list_phieumuon.remove(vitri);
        }
    }

    public int TimViTriTrongMang_Arr1(String obj) {
        for (int i = 0; i < list_phieumuon.size(); i++) {
            if (this.list_phieumuon.get(i).getMaPhieuMuon().equals(obj) == true) {
                return i;
            }
        }
        return -1;
    }

    public void getDanhSachList() {
        PhieuTraDAO phieutraDAO = new PhieuTraDAO();
        this.list_loaisach = phieutraDAO.getListLoaiSach();
        this.list_nhanvien = phieutraDAO.getListNhanVien();
        this.list_phieutra = phieutraDAO.getListPhieuTra();
        this.list_sach = phieutraDAO.getListSACH();
        this.list_docgia = phieutraDAO.getListDocGia();
        this.list_phieumuon = phieutraDAO.getListPhieuMuon();
        this.list_ctpm = phieutraDAO.getListCTPhieuMuon();
        list_ctpm = new ArrayList<CTPhieuMuon>();
        list_phieumuon = new ArrayList<PhieuMuon>();
        list_ctpm = phieutraDAO.getListCTPhieuMuon();
        list_phieumuon = phieutraDAO.getListPhieuMuon();

    }

    // Tạo ra một mã loại sách
    public String TaoMa4ChuSo() {
        int CODE_LENGTH = 4;
        String DIGITS = "0123456789";
        Random random = new Random();
        StringBuilder codeBuilder = new StringBuilder(CODE_LENGTH);
        for (int i = 0; i < CODE_LENGTH; i++) {
            codeBuilder.append(DIGITS.charAt(random.nextInt(DIGITS.length())));
        }
        return codeBuilder.toString();
    }

    public String TaoMa5ChuSo() {
        int CODE_LENGTH = 5;
        String DIGITS = "0123456789";
        Random random = new Random();
        StringBuilder codeBuilder = new StringBuilder(CODE_LENGTH);
        for (int i = 0; i < CODE_LENGTH; i++) {
            codeBuilder.append(DIGITS.charAt(random.nextInt(DIGITS.length())));
        }
        return codeBuilder.toString();
    }

    public String TaoMaSach_DuyNhat() {
        int ktr = 1;
        String ma;
        while (true) {
            ma = TaoMa4ChuSo();
            for (int i = 0; i < list_sach.size(); i++) {
                if (this.list_sach.get(i).get_MaSach().equals(ma) == true)
                    ktr = 0;
            }
            if (ktr == 1)
                break;
        }
        return ma;
    }

    public String TaoMaPhieuTra_DuyNhat() {
        int ktr = 1;
        String ma;
        while (true) {
            ma = TaoMa5ChuSo();
            for (int i = 0; i < list_phieutra.size(); i++) {
                if (this.list_phieutra.get(i).getPhieuTra().equals(ma) == true)
                    ktr = 0;
            }
            if (ktr == 1)
                break;
        }
        return ma;
    }

    public String TaoMaPhieuMuon_DuyNhat() {
        int ktr = 1;
        String ma;
        while (true) {
            ma = TaoMa5ChuSo();
            for (int i = 0; i < list_phieutra.size(); i++) {
                if (this.list_phieutra.get(i).getPhieuMuon().equals(ma) == true)
                    ktr = 0;
            }
            if (ktr == 1)
                break;
        }
        return ma;
    }

    // Thêm 1 phiếu mượn mới
    public void ThemPhieuTraMoi(PhieuTra obj) {
        this.list_phieutra.add(obj);
    }

    // Xóa Phiếu Nhập
    public int TimViTriTrongMang_Arr(PhieuTra obj) {
        for (int i = 0; i < list_phieutra.size(); i++) {
            if (this.list_phieutra.get(i).getPhieuTra().equals(obj.getPhieuTra()) == true) {
                return i;
            }
        }
        return -1;
    }

    public void XoaPhieuTra(PhieuTra obj) {
        int vitri = TimViTriTrongMang_Arr(obj);
        if (vitri == -1) {
            System.out.printf("\n Phiếu Mượn không tồn tại trong hệ thống ");
        } else {
            this.list_phieutra.remove(vitri);
        }
    }

    // Tìm kiếm theo mã phiếu nhập
    public ArrayList<PhieuTra> TimKiemTheoMaPhieuTra(String maPhieuTra) {
        ArrayList<PhieuTra> obj = new ArrayList<>();
        for (int i = 0; i < list_phieutra.size(); i++) {
            if (list_phieutra.get(i).getPhieuTra().equals(maPhieuTra) == true) {
                obj.add(list_phieutra.get(i));
            }
        }
        return obj;
    }

    // Tìm kiếm theo mã nhân viên đã tạo ra phiếu
    public ArrayList<PhieuTra> TimKiemTheoMaNhanVien(String maNhanVien) {
        ArrayList<PhieuTra> obj = new ArrayList<>();
        for (int i = 0; i < list_phieutra.size(); i++) {
            if (list_phieutra.get(i).getNhanVien().getHoTen().equals(maNhanVien) == true) {
                obj.add(list_phieutra.get(i));
            }
        }
        return obj;
    }

    public ArrayList<PhieuTra> TimKiemTheoMaDocGia(String maDocGia) {
        ArrayList<PhieuTra> obj = new ArrayList<>();
        for (int i = 0; i < list_phieutra.size(); i++) {
            if (list_phieutra.get(i).getDocGia().getHoTen().equals(maDocGia) == true) {
                obj.add(list_phieutra.get(i));
            }
        }
        return obj;
    }
}
