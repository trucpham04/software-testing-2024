/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BUS;

import DATA.PhieuMuonDAO;
import DATA.UserDAO;
import DTO.CTPhieuMuon;
import DTO.DocGia;
import DTO.LoaiSach;
import DTO.NhanVien;
import DTO.PhieuMuon;
import DTO.SachCT;
import DTO.TacGia;
import DTO.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Admin
 */
public class PhieuMuonBUS {
    private ArrayList<PhieuMuon> list_phieumuon;
    private ArrayList<LoaiSach> list_loaisach;
    private ArrayList<SachCT> list_sach;
    private ArrayList<NhanVien> list_nhanvien;
    private ArrayList<DocGia> list_docgia;
    private ArrayList<TacGia> list_tacgia;
    private ArrayList<CTPhieuMuon> list_ctphieumuon;

    public ArrayList<CTPhieuMuon> getList_ctphieumuon() {
        return list_ctphieumuon;
    }

    public void setList_ctphieumuon(ArrayList<CTPhieuMuon> list_ctphieumuon) {
        this.list_ctphieumuon = list_ctphieumuon;
    }

    public PhieuMuonBUS() {
        getDanhSachList();
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

    public void set_ListNhanVien(ArrayList<NhanVien> list_phieumuon) {
        this.list_nhanvien = list_phieumuon;
    }

    public ArrayList<PhieuMuon> get_ListPhieuMuon() {
        return list_phieumuon;
    }

    public void set_ListLoaiSach(ArrayList<LoaiSach> list_loaisach) {
        this.list_loaisach = list_loaisach;
    }

    public ArrayList<LoaiSach> get_ListLoaiSach() {
        return list_loaisach;
    }

    public void set_ListPhieuMuon(ArrayList<PhieuMuon> list_phieumuon) {
        this.list_phieumuon = list_phieumuon;
    }

    public ArrayList<TacGia> get_ListTacGia() {
        return list_tacgia;
    }

    public void set_ListTacGia(ArrayList<TacGia> list_tacgia) {
        this.list_tacgia = list_tacgia;
    }

    public void getDanhSachList() {
        PhieuMuonDAO phieumuonDAO = new PhieuMuonDAO();
        this.list_loaisach = phieumuonDAO.getListLoaiSach();
        this.list_nhanvien = phieumuonDAO.getListNhanVien();
        this.list_phieumuon = phieumuonDAO.getListPhieuMuon();
        this.list_sach = phieumuonDAO.getListSACH();
        this.list_docgia = phieumuonDAO.getListDocGia();
        this.list_tacgia = phieumuonDAO.getListTacGia();
        list_phieumuon = new ArrayList<PhieuMuon>();
        list_phieumuon = phieumuonDAO.getListPhieuMuon();
        list_ctphieumuon = new ArrayList<CTPhieuMuon>();
        list_ctphieumuon = phieumuonDAO.getListCTPhieuMuon1();
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

    public String TaoMaPhieuMuon_DuyNhat() {
        int ktr = 1;
        String ma;
        while (true) {
            ma = TaoMa5ChuSo();
            for (int i = 0; i < list_phieumuon.size(); i++) {
                if (this.list_phieumuon.get(i).getMaPhieuMuon().equals(ma) == true)
                    ktr = 0;
            }
            if (ktr == 1)
                break;
        }
        return ma;
    }

    // Thêm 1 phiếu mượn mới
    public void ThemPhieuMuonMoi(PhieuMuon obj) {
        this.list_phieumuon.add(obj);
    }

    // Xóa Phiếu Nhập
    public int TimViTriTrongMang_Arr(PhieuMuon obj) {
        for (int i = 0; i < list_phieumuon.size(); i++) {
            if (this.list_phieumuon.get(i).getMaPhieuMuon().equals(obj.getMaPhieuMuon()) == true) {
                return i;
            }
        }
        return -1;
    }

    public void XoaPhieuMuon(PhieuMuon obj) {
        int vitri = TimViTriTrongMang_Arr(obj);
        if (vitri == -1) {
            System.out.printf("\n Phiếu Mượn không tồn tại trong hệ thống ");
        } else {
            this.list_phieumuon.remove(vitri);
        }
    }

    // Tìm kiếm theo mã phiếu nhập
    public ArrayList<PhieuMuon> TimKiemTheoMaPhieuMuon(String maPhieuMuon) {
        ArrayList<PhieuMuon> obj = new ArrayList<>();
        for (int i = 0; i < list_phieumuon.size(); i++) {
            if (list_phieumuon.get(i).getMaPhieuMuon().equals(maPhieuMuon) == true) {
                obj.add(list_phieumuon.get(i));
            }
        }
        return obj;
    }

    public void delete2(String userID) {
        for (PhieuMuon hd : list_phieumuon) {
            if (hd.getMaPhieuMuon().equals(userID)) {
                list_phieumuon.remove(hd);
                PhieuMuonDAO pmdao = new PhieuMuonDAO();
                try {
                    pmdao.delete2(userID);
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                return;
            }
        }
    }

    // Tìm kiếm theo mã nhân viên đã tạo ra phiếu
    public ArrayList<PhieuMuon> TimKiemTheoMaNhanVien(String maNhanVien) {
        ArrayList<PhieuMuon> obj = new ArrayList<>();
        for (int i = 0; i < list_phieumuon.size(); i++) {
            if (list_phieumuon.get(i).getMaNhanVien().getHoTen().equals(maNhanVien) == true) {
                obj.add(list_phieumuon.get(i));
            }
        }
        return obj;
    }

    public ArrayList<PhieuMuon> TimKiemTheoMaDocGia(String maDocGia) {
        ArrayList<PhieuMuon> obj = new ArrayList<>();
        for (int i = 0; i < list_phieumuon.size(); i++) {
            if (list_phieumuon.get(i).getDocGia().getHoTen().equals(maDocGia) == true) {
                obj.add(list_phieumuon.get(i));
            }
        }
        return obj;
    }
}
