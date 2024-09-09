/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BUS;

import DATA.NhaXuatBanDAO;
import DTO.NhaXuatBan;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Admin
 */
public class NhaXuatBanBUS {
    private ArrayList<NhaXuatBan> List_NhaXuatBan;
    private Integer solg_NhaXuatBan;

    public ArrayList<NhaXuatBan> getList_NhaXuatBan() {
        return this.List_NhaXuatBan;
    }

    public void setList_NhaXuatBan(ArrayList<NhaXuatBan> List_NhaXuatBan) {
        this.List_NhaXuatBan = List_NhaXuatBan;
    }

    public Integer getSolg_NhaXuatBan() {
        return this.solg_NhaXuatBan;
    }

    public void setSolg_NhaXuatBan(int solg_NhaXuatBan) {
        this.solg_NhaXuatBan = solg_NhaXuatBan;
    }

    public void DanhSachlist() {
        NhaXuatBanDAO obj = new NhaXuatBanDAO();
        this.List_NhaXuatBan = new ArrayList();
        this.List_NhaXuatBan = obj.getNhaXuatBan_Data();
        this.solg_NhaXuatBan = this.List_NhaXuatBan.size();

    }

    // Tạo ra một mã loại sách
    public String TaoMaNhaXuatBan() {
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
    public String TaoMaNhaXuatBan_DuyNhat() {
        int ktr = 1;
        String ma;
        while (true) {
            ma = TaoMaNhaXuatBan();
            for (int i = 0; i < this.solg_NhaXuatBan; i++) {
                if (this.List_NhaXuatBan.get(i).getMaNXB().equals(ma) == true)
                    ktr = 0;
            }
            if (ktr == 1)
                break;
        }
        return ma;
    }

    // Thêm tên tác giả trong mảng
    public void ThemNhaXuatBan_Arr(NhaXuatBan nhaxuatban) {
        this.List_NhaXuatBan.add(nhaxuatban);
        this.solg_NhaXuatBan = this.List_NhaXuatBan.size();
    }

    public int TimViTriTrongMang_Arr(NhaXuatBan nhaxuatban) {
        int n = this.solg_NhaXuatBan;
        for (int i = 0; i < n; i++) {
            if (this.List_NhaXuatBan.get(i).getMaNXB().equals(nhaxuatban.getMaNXB()) == true) {
                return i;
            }
        }
        return -1;
    }

    // Xóa loại sách khỏi mảng
    public void XoaNhaXuatBan_Array(NhaXuatBan nhaxuatban) {
        int vitri = TimViTriTrongMang_Arr(nhaxuatban);
        if (vitri == -1) {
            System.out.printf("\n Nhà xuất bản không tồn tại trong hệ thống ");
        } else {
            this.List_NhaXuatBan.remove(vitri);
            this.solg_NhaXuatBan = this.List_NhaXuatBan.size();
        }
    }

    // Sửa loại sách mảng
    public void EditNhaXuatBan_Array(NhaXuatBan nhaxuatban) {
        int vitri = TimViTriTrongMang_Arr(nhaxuatban);
        if (vitri == -1) {
            System.out.printf("\nNhà xuất bản không tồn tại trong hệ thống");
        } else {
            this.List_NhaXuatBan.set(vitri, nhaxuatban);
        }
    }

    // Tìm theo tên
    public ArrayList<NhaXuatBan> Tim_Theo_Ten(String ten) {
        // Tạo một ArrayList rỗng
        ArrayList<NhaXuatBan> List_NhaXuatBantrung = new ArrayList<>();
        for (int i = 0; i < this.solg_NhaXuatBan; i++) {
            if (this.List_NhaXuatBan.get(i).getTenNXB().equals(ten) == true) {
                List_NhaXuatBantrung.add(this.List_NhaXuatBan.get(i));
            }
        }
        return List_NhaXuatBantrung;
    }

    // Tìm theo id
    public ArrayList<NhaXuatBan> Tim_Theo_ID(String id) {
        // Tạo một ArrayList rỗng
        ArrayList<NhaXuatBan> List_NhaXuatBantrung = new ArrayList<>();
        for (int i = 0; i < this.solg_NhaXuatBan; i++) {
            if (this.List_NhaXuatBan.get(i).getMaNXB().equals(id) == true) {
                List_NhaXuatBantrung.add(this.List_NhaXuatBan.get(i));
            }
        }
        return List_NhaXuatBantrung;
    }

    public void ThemNhieuNhaXuatBan_Arr(ArrayList<NhaXuatBan> nhaxuatban) {
        for (int i = 0; i < nhaxuatban.size(); i++) {
            this.List_NhaXuatBan.add(nhaxuatban.get(i));
        }
    }

}
