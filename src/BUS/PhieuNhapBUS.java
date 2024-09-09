/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BUS;

import DATA.PhieuNhapDAO;
import DTO.CTPhieuNhap;
import DTO.LoaiSach;
import DTO.NhaXuatBan;
import DTO.NhanVien;
import DTO.PhieuNhap;
import DTO.SachCT;
import DTO.TacGia;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Admin
 */
public class PhieuNhapBUS {
    private ArrayList<PhieuNhap> list_phieunhap;
    private ArrayList<SachCT> list_sach;
    private ArrayList<NhaXuatBan> list_nhaxuatban;
    private ArrayList<LoaiSach> list_loaisach;
    private ArrayList<TacGia> list_tacgia;
    private ArrayList<NhanVien> list_nhanvien;
    private ArrayList<CTPhieuNhap> list_ctpn;

    public ArrayList<CTPhieuNhap> getList_ctpn() {
        return list_ctpn;
    }

    public void setList_ctpn(ArrayList<CTPhieuNhap> list_ctpn) {
        this.list_ctpn = list_ctpn;
    }

    public PhieuNhapBUS() {
        getDanhSachList();
    }

    public ArrayList<NhanVien> get_ListNhanVien() {
        return list_nhanvien;
    }

    public void set_ListNhanVien(ArrayList<NhanVien> list_phieunhap) {
        this.list_nhanvien = list_phieunhap;
    }

    public ArrayList<PhieuNhap> get_ListPhieuNhap() {
        return list_phieunhap;
    }

    public void set_ListPhieuNhap(ArrayList<PhieuNhap> list_phieunhap) {
        this.list_phieunhap = list_phieunhap;
    }

    public ArrayList<SachCT> get_ListSach() {
        return list_sach;
    }

    public void set_ListSach(ArrayList<SachCT> list_sach) {
        this.list_sach = list_sach;
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

    public void getDanhSachList() {
        PhieuNhapDAO phieunhapDAO = new PhieuNhapDAO();
        this.list_nhanvien = phieunhapDAO.getListNhanVien();
        this.list_phieunhap = phieunhapDAO.getListPhieuNhap();
        this.list_loaisach = phieunhapDAO.getListLoaiSach();
        this.list_nhaxuatban = phieunhapDAO.getListNhaXuatBan();
        this.list_sach = phieunhapDAO.getListSACH();
        this.list_tacgia = phieunhapDAO.getListTacGia();
        list_phieunhap = new ArrayList<PhieuNhap>();
        list_phieunhap = phieunhapDAO.getListPhieuNhap();
        list_ctpn = new ArrayList<CTPhieuNhap>();
        list_ctpn = phieunhapDAO.getListCTPhieuNhap1();
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

    public String TaoMaPhieuNhap_DuyNhat() {
        int ktr = 1;
        String ma;
        while (true) {
            ma = TaoMa5ChuSo();
            for (int i = 0; i < list_phieunhap.size(); i++) {
                if (this.list_phieunhap.get(i).get_maPNhap().equals(ma) == true)
                    ktr = 0;
            }
            if (ktr == 1)
                break;
        }
        return ma;
    }

    // Thêm 1 phiếu nhập mới
    public void ThemPhieuNhapMoi(PhieuNhap obj) {
        this.list_phieunhap.add(obj);
    }

    // Thêm 1 sách mới
    public void ThemSachMoi(SachCT obj) {
        this.list_sach.add(obj);
    }

    // Xóa Phiếu Nhập
    public int TimViTriTrongMang_Arr(PhieuNhap obj) {
        for (int i = 0; i < list_phieunhap.size(); i++) {
            if (this.list_phieunhap.get(i).get_maPNhap().equals(obj.get_maPNhap()) == true) {
                return i;
            }
        }
        return -1;
    }

    public void XoaPhieuNhap(PhieuNhap obj) {
        int vitri = TimViTriTrongMang_Arr(obj);
        if (vitri == -1) {
            System.out.printf("\n Phiếu Nhập không tồn tại trong hệ thống ");
        } else {
            this.list_phieunhap.remove(vitri);
        }
    }

    // Tìm kiếm theo mã phiếu nhập
    public ArrayList<PhieuNhap> TimKiemTheoMaPhieuNhap(String maPNhap) {
        ArrayList<PhieuNhap> obj = new ArrayList<>();
        for (int i = 0; i < list_phieunhap.size(); i++) {
            if (list_phieunhap.get(i).get_maPNhap().equals(maPNhap) == true) {
                obj.add(list_phieunhap.get(i));
            }
        }
        return obj;
    }

    // Tìm kiếm theo tên nhân viên đã tạo ra phiếu
    public ArrayList<PhieuNhap> TimKiemTheoTenNhanVien(String tenNhanVien) {
        ArrayList<PhieuNhap> obj = new ArrayList<>();
        for (int i = 0; i < list_phieunhap.size(); i++) {
            if (list_phieunhap.get(i).get_NhanVien().getHoTen().equals(tenNhanVien) == true) {
                obj.add(list_phieunhap.get(i));
            }
        }
        return obj;
    }

    // Tìm kiếm theo mã nhân viên đã tạo ra phiếu
    public ArrayList<PhieuNhap> TimKiemTheoMaNhanVien(String maNhanVien) {
        ArrayList<PhieuNhap> obj = new ArrayList<>();
        for (int i = 0; i < list_phieunhap.size(); i++) {
            if (list_phieunhap.get(i).get_NhanVien().getMaNV().equals(maNhanVien) == true) {
                obj.add(list_phieunhap.get(i));
            }
        }
        return obj;
    }

    // Tìm kiếm theo tên nhà xuất bản đã tạo ra phiếu
    public ArrayList<PhieuNhap> TimKiemTheoTenNhaXuatBan(String tenNhaXuatBan) {
        ArrayList<PhieuNhap> obj = new ArrayList<>();
        for (int i = 0; i < list_phieunhap.size(); i++) {
            if (list_phieunhap.get(i).get_NhaXuatBan().getTenNXB().equals(tenNhaXuatBan) == true) {
                obj.add(list_phieunhap.get(i));
            }
        }
        return obj;
    }

    // Tìm kiếm theo mã nhà xuất bản
    public ArrayList<PhieuNhap> TimKiemTheoMaNhaXuatBan(String maNhaXuatBan) {
        ArrayList<PhieuNhap> obj = new ArrayList<>();
        for (int i = 0; i < list_phieunhap.size(); i++) {
            if (list_phieunhap.get(i).get_NhaXuatBan().getMaNXB().equals(maNhaXuatBan) == true) {
                obj.add(list_phieunhap.get(i));
            }
        }
        return obj;
    }

    // Thay đỏi

}
