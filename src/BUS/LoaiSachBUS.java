/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BUS;

import DATA.LoaiSachDAO;
import DTO.LoaiSach;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Admin
 */
public class LoaiSachBUS {
    private ArrayList<LoaiSach> List_LoaiSach;
    private Integer solg_LoaiSach;

    public ArrayList<LoaiSach> getList_LoaiSach() {
        return this.List_LoaiSach;
    }

    public void setList_LoaiSach(ArrayList<LoaiSach> List_LoaiSach) {
        this.List_LoaiSach = List_LoaiSach;
    }

    public Integer getSolg_LoaiSach() {
        return this.solg_LoaiSach;
    }

    public void setSolg_LoaiSach(int solg_LoaiSach) {
        this.solg_LoaiSach = solg_LoaiSach;
    }

    public void DanhSachlist() {
        LoaiSachDAO obj = new LoaiSachDAO();
        this.List_LoaiSach = new ArrayList();
        this.List_LoaiSach = obj.getLoaiSach_Data();
        this.solg_LoaiSach = this.List_LoaiSach.size();

    }

    // Tạo ra một mã loại sách
    public String TaoMaLoaiSach() {
        int CODE_LENGTH = 4;
        String DIGITS = "0123456789";
        Random random = new Random();
        StringBuilder codeBuilder = new StringBuilder(CODE_LENGTH);
        for (int i = 0; i < CODE_LENGTH; i++) {
            codeBuilder.append(DIGITS.charAt(random.nextInt(DIGITS.length())));
        }
        return codeBuilder.toString();
    }

    // Tạo mã tác giả mới không trung với bất kì mã nào trong hệ thống
    public String TaoMaLoaiSach_DuyNhat() {
        int ktr = 1;
        String ma;
        while (true) {
            ma = TaoMaLoaiSach();
            for (int i = 0; i < this.solg_LoaiSach; i++) {
                if (this.List_LoaiSach.get(i).getMaLoai().equals(ma) == true)
                    ktr = 0;
            }
            if (ktr == 1)
                break;
        }
        return ma;
    }

    // Thêm tên tác giả trong mảng
    public void ThemLoaiSach_Arr(LoaiSach loaisach) {
        this.List_LoaiSach.add(loaisach);
        this.solg_LoaiSach = this.List_LoaiSach.size();
    }

    public int TimViTriTrongMang_Arr(LoaiSach loaisach) {
        int n = this.solg_LoaiSach;
        for (int i = 0; i < n; i++) {
            if (this.List_LoaiSach.get(i).getMaLoai().equals(loaisach.getMaLoai()) == true) {
                return i;
            }
        }
        return -1;
    }

    // Xóa loại sách khỏi mảng
    public void XoaLoaiSach_Array(LoaiSach loaisach) {
        int vitri = TimViTriTrongMang_Arr(loaisach);
        if (vitri == -1) {
            System.out.printf("\n Loại sách không tồn tại trong hệ thống ");
        } else {
            this.List_LoaiSach.remove(vitri);
            this.solg_LoaiSach = this.List_LoaiSach.size();
        }
    }

    // Sửa loại sách mảng
    public void EditLoaiSach_Array(LoaiSach loaisach) {
        int vitri = TimViTriTrongMang_Arr(loaisach);
        if (vitri == -1) {
            System.out.printf("\nLoại sách không tồn tại trong hệ thống");
        } else {
            this.List_LoaiSach.set(vitri, loaisach);
        }
    }

    // Tìm theo tên
    public ArrayList<LoaiSach> Tim_Theo_Ten(String ten) {
        // Tạo một ArrayList rỗng
        ArrayList<LoaiSach> List_LoaiSachtrung = new ArrayList<>();
        for (int i = 0; i < this.solg_LoaiSach; i++) {
            if (this.List_LoaiSach.get(i).getTenLoai().equals(ten) == true) {
                List_LoaiSachtrung.add(this.List_LoaiSach.get(i));
            }
        }
        return List_LoaiSachtrung;
    }

    // Tìm theo id
    public ArrayList<LoaiSach> Tim_Theo_ID(String id) {
        // Tạo một ArrayList rỗng
        ArrayList<LoaiSach> List_LoaiSachtrung = new ArrayList<>();
        for (int i = 0; i < this.solg_LoaiSach; i++) {
            if (this.List_LoaiSach.get(i).getMaLoai().equals(id) == true) {
                List_LoaiSachtrung.add(this.List_LoaiSach.get(i));
            }
        }
        return List_LoaiSachtrung;
    }

    public void ThemNhieuLoaiSach_Arr(ArrayList<LoaiSach> loaisach) {
        for (int i = 0; i < loaisach.size(); i++) {
            this.List_LoaiSach.add(loaisach.get(i));
        }
    }

}
