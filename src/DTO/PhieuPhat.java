/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

import java.util.Date;

/**
 *
 * @author Admin
 */
public class PhieuPhat {
    private String maPhieuPhat;
    private PhieuTra phieuTra;
    private DocGia docGia;
    private Date ngayPhat;
    private Date ngayTra;
    private float tienPhat;
    private String lyDo;
    private String trangThai;

    public PhieuPhat() {
    }

    public PhieuPhat(String maPhieuPhat, PhieuTra phieuTra, DocGia docGia, Date ngayPhat, Date ngayTra, float tienPhat,
            String lyDo, String trangThai) {
        this.maPhieuPhat = maPhieuPhat;
        this.phieuTra = phieuTra;
        this.docGia = docGia;
        this.ngayPhat = ngayPhat;
        this.ngayTra = ngayTra;
        this.tienPhat = tienPhat;
        this.lyDo = lyDo;
        this.trangThai = trangThai;
    }

    public String getMaPhieuPhat() {
        return maPhieuPhat;
    }

    public PhieuTra getPhieuTra() {
        return phieuTra;
    }

    public DocGia getDocGia() {
        return docGia;
    }

    public Date getNgayPhat() {
        return ngayPhat;
    }

    public Date getNgayTra() {
        return ngayTra;
    }

    public float getTienPhat() {
        return tienPhat;
    }

    public String getLyDo() {
        return lyDo;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setMaPhieuPhat(String maPhieuPhat) {
        this.maPhieuPhat = maPhieuPhat;
    }

    public void setPhieuTra(PhieuTra phieuTra) {
        this.phieuTra = phieuTra;
    }

    public void setDocGia(DocGia docGia) {
        this.docGia = docGia;
    }

    public void setNgayPhat(Date ngayPhat) {
        this.ngayPhat = ngayPhat;
    }

    public void setNgayTra(Date ngayTra) {
        this.ngayTra = ngayTra;
    }

    public void setTienPhat(float tienPhat) {
        this.tienPhat = tienPhat;
    }

    public void setLyDo(String lyDo) {
        this.lyDo = lyDo;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

}
