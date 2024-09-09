/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

/**
 *
 * @author Admin
 */
public class CTPhieuNhap {
    private String maphieunhap;
    private int soluong;
    private int thanhtien;
    private SachCT sach;
    private float TongTien;
    private int TongSL;

    public float getTongTien() {
        return TongTien;
    }

    public void setTongTien(float tongTien) {
        TongTien = tongTien;
    }

    public int getTongSL() {
        return TongSL;
    }

    public void setTongSL(int tongSL) {
        TongSL = tongSL;
    }

    public CTPhieuNhap() {

    }

    public void set_Sach(SachCT sach) {
        this.sach = sach;
    }

    public SachCT get_Sach() {
        return sach;
    }

    public void set_thanhtien(Integer thanhtien) {
        this.thanhtien = thanhtien;
    }

    public Integer get_thanhtien() {
        return thanhtien;
    }

    public void set_soluong(Integer soluong) {
        this.soluong = soluong;
    }

    public Integer get_soluong() {
        return soluong;
    }

    public void set_MaPhieuNhap(String maphieuNhap) {
        this.maphieunhap = maphieuNhap;
    }

    public String get_MaPhieuNhap() {
        return maphieunhap;
    }

}
