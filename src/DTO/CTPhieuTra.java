/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

/**
 *
 * @author Admin
 */
public class CTPhieuTra {
    private String phieuTra;
    private SachCT sach;
    private int soLuong;

    public CTPhieuTra() {
    }

    public CTPhieuTra(String phieuTra, SachCT sach, int soLuong) {
        this.phieuTra = phieuTra;
        this.sach = sach;
        this.soLuong = soLuong;
    }

    public String getPhieuTra() {
        return phieuTra;
    }

    public SachCT getSach() {
        return sach;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setPhieuTra(String phieuTra) {
        this.phieuTra = phieuTra;
    }

    public void setSach(SachCT sach) {
        this.sach = sach;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

}
