/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

/**
 *
 * @author Admin
 */
public class TacGia {
    private String maTG;
    private String tenTG;
    private String gioiTinh;
    private String namSinh;
    private Integer trangThai;

    public TacGia() {
    }

    public TacGia(String maTG, String tenTG, String gioiTinh, String namSinh, int trangThai) {
        this.maTG = maTG;
        this.tenTG = tenTG;
        this.gioiTinh = gioiTinh;
        this.namSinh = namSinh;
        this.trangThai = trangThai;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getMaTG() {
        return maTG;
    }

    public void setMaTG(String maTG) {
        this.maTG = maTG;
    }

    public String getNamSinh() {
        return namSinh;
    }

    public void setNamSinh(String namSinh) {
        this.namSinh = namSinh;
    }

    public String getTenTG() {
        return tenTG;
    }

    public void setTenTG(String tenTG) {
        this.tenTG = tenTG;
    }

    public Integer getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    // Kiểm tra xem 2 mã tác giả có giông nhau hay không
    public int Ktra_MaTGia(TacGia tgia) {
        if (this.maTG.equals(tgia.getMaTG()) == true)
            return 1;
        return 0;
    }

    // Kiểm tra 2 tác giả có trùng tên với nhau không
    public int Ktra_TenTGia(TacGia tgia) {
        if (this.tenTG.equals(tgia.getTenTG()) == true)
            return 1;
        return 0;
    }

    public String toString() {
        return this.maTG + " " + this.tenTG + " " + this.gioiTinh + " " + this.namSinh + " " + this.trangThai;
    }

}
