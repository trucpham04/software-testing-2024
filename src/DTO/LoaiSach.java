/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

/**
 *
 * @author Admin
 */
public class LoaiSach {
    private String maLoai;
    private String tenLoai;
    private Integer trangThai;

    public LoaiSach() {

    }

    public LoaiSach(String maLoai, String tenLoai, int trangThai) {
        this.maLoai = maLoai;
        this.tenLoai = tenLoai;
        this.trangThai = trangThai;
    }

    public String getMaLoai() {
        return maLoai;
    }

    public void setMaLoai(String maLoai) {
        this.maLoai = maLoai;
    }

    public String getTenLoai() {
        return tenLoai;
    }

    public void setTenLoai(String tenLoai) {
        this.tenLoai = tenLoai;
    }

    public Integer getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    // Kiểm tra xem 2 mã tác giả có giông nhau hay không
    public int Ktra_MaLoaiSach(LoaiSach loaisach) {
        if (this.maLoai.equals(loaisach.getMaLoai()) == true)
            return 1;
        return 0;
    }

    // Kiểm tra 2 tác giả có trùng tên với nhau không
    public int Ktra_TenLoaiSach(LoaiSach loaisach) {
        if (this.tenLoai.equals(loaisach.getTenLoai()) == true)
            return 1;
        return 0;
    }

    public String toString() {
        return this.maLoai + " " + this.tenLoai;
    }

}
