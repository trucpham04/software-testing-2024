/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BUS;

import DATA.DocGiaDAO;
import DTO.DocGia;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Admin
 */
public class DocGiaBUS {
    private ArrayList<DocGia> List_DocGia;
    private Integer solg_DGia;

    public ArrayList<DocGia> getList_DocGia() {
        return this.List_DocGia;
    }

    public void setList_DocGia(ArrayList<DocGia> List_DocGia) {
        this.List_DocGia = List_DocGia;
    }

    public Integer getSolg_DGia() {
        return this.solg_DGia;
    }

    public void setSolg_DGia(int solg_DGia) {
        this.solg_DGia = solg_DGia;
    }

    // Lấy dữ liệu từ data để đổ xuống mảng
    public void DanhSachlist() {
        DocGiaDAO obj = new DocGiaDAO();
        this.List_DocGia = new ArrayList();
        this.List_DocGia = obj.getDocgia_Data();
        this.solg_DGia = this.List_DocGia.size();

    }

    // Tạo ra một mã độc giả
    public String TaoMaDGia() {
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
    public String TaoMaDGia_DuyNhat() {
        int ktr = 1;
        String ma;
        while (true) {
            ma = TaoMaDGia();
            for (int i = 0; i < this.solg_DGia; i++) {
                if (this.List_DocGia.get(i).getMaDG().equals(ma))
                    ktr = 0;
            }
            if (ktr == 1)
                break;
        }
        return ma;
    }

    // Thêm tên độc giả trong mảng
    public void ThemDocGia_Arr(DocGia docgia) {
        this.List_DocGia.add(docgia);
        this.solg_DGia = this.List_DocGia.size();
    }

    public int TimViTriTrongMang_Arr(DocGia docgia) {
        int n = this.solg_DGia;
        for (int i = 0; i < n; i++) {
            if (this.List_DocGia.get(i).getMaDG().equals(docgia.getMaDG())) {
                return i;
            }
        }
        return -1;
    }

    // Xóa tác giả khỏi mảng
    public void XoaDocGia_Array(DocGia docgia) {
        int vitri = TimViTriTrongMang_Arr(docgia);
        if (vitri == -1) {
            System.out.printf("\n Độc giả không tồn tại trong hệ thống ");
        } else {
            this.List_DocGia.remove(vitri);
            this.solg_DGia = this.List_DocGia.size();
        }
    }

    public void EditDocGia_Array(DocGia docgia) {
        int vitri = TimViTriTrongMang_Arr(docgia);
        if (vitri == -1) {
            System.out.printf("\nTác giả không tồn tại trong hệ thống");
        } else {
            this.List_DocGia.set(vitri, docgia);
        }
    }

    // Tìm theo tên
    public ArrayList<DocGia> Tim_Theo_Ten(String ten) {
        // Tạo một ArrayList rỗng
        ArrayList<DocGia> List_Docgiatrung = new ArrayList<>();
        for (int i = 0; i < this.solg_DGia; i++) {
            if (this.List_DocGia.get(i).getHoTen().equals(ten) == true) {
                List_Docgiatrung.add(this.List_DocGia.get(i));
            }
        }
        return List_Docgiatrung;
    }

    // Tìm theo id
    public ArrayList<DocGia> Tim_Theo_ID(String id) {
        // Tạo một ArrayList rỗng
        ArrayList<DocGia> List_Docgiatrung = new ArrayList<>();
        for (int i = 0; i < this.solg_DGia; i++) {
            if (this.List_DocGia.get(i).getMaDG().equals(id) == true) {
                List_Docgiatrung.add(this.List_DocGia.get(i));
            }
        }
        return List_Docgiatrung;
    }

    // Tìm theo sdt
    public ArrayList<DocGia> Tim_Theo_SDT(String sdt) {
        // Tạo một ArrayList rỗng
        ArrayList<DocGia> List_Docgiatrung = new ArrayList<>();
        for (int i = 0; i < this.solg_DGia; i++) {
            if (this.List_DocGia.get(i).getSDT().equals(sdt) == true) {
                List_Docgiatrung.add(this.List_DocGia.get(i));
            }
        }
        return List_Docgiatrung;
    }

    // Tìm theo sdt
    public ArrayList<DocGia> Tim_Theo_Email(String email) {
        // Tạo một ArrayList rỗng
        ArrayList<DocGia> List_Docgiatrung = new ArrayList<>();
        for (int i = 0; i < this.solg_DGia; i++) {
            if (this.List_DocGia.get(i).getEmail().equals(email) == true) {
                List_Docgiatrung.add(this.List_DocGia.get(i));
            }
        }
        return List_Docgiatrung;
    }

    public void ThemNhieuDocGia_Arr(ArrayList<DocGia> docgia) {
        for (int i = 0; i < docgia.size(); i++) {
            this.List_DocGia.add(docgia.get(i));
        }
    }

}
