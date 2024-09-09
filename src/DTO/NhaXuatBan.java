/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

/**
 *
 * @author Admin
 */
public class NhaXuatBan {
    private String maNXB;
    private String tenNXB;
    private int trangThai;

    public NhaXuatBan() {

    }

    public NhaXuatBan(String maNXB, String tenNXB, int trangThai) {
        this.maNXB = maNXB;
        this.tenNXB = tenNXB;
        this.trangThai = trangThai;
    }

    public String getMaNXB() {
        return maNXB;
    }

    public void setMaNXB(String maNXB) {
        this.maNXB = maNXB;
    }

    public String getTenNXB() {
        return tenNXB;
    }

    public void setTenNXB(String tenNXB) {
        this.tenNXB = tenNXB;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int tt) {
        this.trangThai = tt;
    }

}
