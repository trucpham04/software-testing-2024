/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BUS;

import DTO.TacGia;
import java.util.ArrayList;
import DATA.TacgiaDAO;

/**
 *
 * @author Admin
 */
import java.util.Random;

public class TacGiaBUS {
    private ArrayList<TacGia> List_Tacgia;
    private Integer solg_Tgia;

    public ArrayList<TacGia> getList_TacGia() {
        return this.List_Tacgia;
    }

    public void setList_TacGia(ArrayList<TacGia> List_TacGia) {
        this.List_Tacgia = List_TacGia;
    }

    public Integer getSolg_Tgia() {
        return this.solg_Tgia;
    }

    public void setSolg_Tgia(int solg_Tgia) {
        this.solg_Tgia = solg_Tgia;
    }

    public TacGiaBUS() {
        DanhSachlist();
    }

    // Lấy dữ liệu từ data để đổ xuống mảng
    public void DanhSachlist() {
        TacgiaDAO obj = new TacgiaDAO();
        this.List_Tacgia = new ArrayList();
        this.List_Tacgia = obj.getTacgia_Data();
        this.solg_Tgia = this.List_Tacgia.size();

    }

    // Tạo ra một mã tác giả
    public String TaoMaTGia() {
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
    public String TaoMaTGia_DuyNhat() {
        int ktr = 1;
        String ma;
        while (true) {
            ma = TaoMaTGia();
            for (int i = 0; i < this.solg_Tgia; i++) {
                if (this.List_Tacgia.get(i).getMaTG().equals(ma))
                    ktr = 0;
            }
            if (ktr == 1)
                break;
        }
        return ma;
    }

    // Thêm tên tác giả trong mảng
    public void ThemTacGia_Arr(TacGia tacgia) {
        this.List_Tacgia.add(tacgia);
    }

    public int TimViTriTrongMang_Arr(TacGia tacgia) {
        int n = this.solg_Tgia;
        for (int i = 0; i < n; i++) {
            if (this.List_Tacgia.get(i).getMaTG().equals(tacgia.getMaTG())) {
                return i;
            }
        }
        return -1;
    }

    // Xóa tác giả khỏi mảng
    public void XoaTGia_Array(TacGia tacgia) {
        int vitri = TimViTriTrongMang_Arr(tacgia);
        if (vitri == -1) {
            System.out.printf("\n Tác gỉa không tồn tại trong hệ thống ");
        } else {
            this.List_Tacgia.remove(vitri);
            this.solg_Tgia = this.List_Tacgia.size();
        }
    }

    // Thêm tác giả vào mảng
    public void EditTGia_Array(TacGia tacgia) {
        int vitri = TimViTriTrongMang_Arr(tacgia);
        if (vitri == -1) {
            System.out.printf("\nTác giả không tồn tại trong hệ thống");
        } else {
            this.List_Tacgia.set(vitri, tacgia);
        }
    }

    // Tìm theo tên
    public ArrayList<TacGia> Tim_Theo_Ten(String ten) {
        // Tạo một ArrayList rỗng
        ArrayList<TacGia> List_Tacgiatrung = new ArrayList<>();
        for (int i = 0; i < this.solg_Tgia; i++) {
            if (this.List_Tacgia.get(i).getTenTG().equals(ten) == true) {
                List_Tacgiatrung.add(this.List_Tacgia.get(i));
            }
        }
        return List_Tacgiatrung;
    }

    // Tìm theo id
    public ArrayList<TacGia> Tim_Theo_ID(String id) {
        // Tạo một ArrayList rỗng
        ArrayList<TacGia> List_Tacgiatrung = new ArrayList<>();
        for (int i = 0; i < this.solg_Tgia; i++) {
            if (this.List_Tacgia.get(i).getMaTG().equals(id) == true) {
                List_Tacgiatrung.add(this.List_Tacgia.get(i));
            }
        }
        return List_Tacgiatrung;
    }

    public void ThemNhieuTacGia_Arr(ArrayList<TacGia> tacgia) {
        for (int i = 0; i < tacgia.size(); i++) {
            this.List_Tacgia.add(tacgia.get(i));
        }
    }

}
