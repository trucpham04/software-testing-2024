/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Admin
 */
public class PhieuTra {
    private String phieuTra;
    private PhieuMuon phieuMuon;
    private DocGia docGia;
    private NhanVien nhanVien;
    private Date ngayTra;
    private int soLuong;
    private String tinhTrang;
    private String trangThai;
    private ArrayList<CTPhieuTra> list = new ArrayList<>();

    public PhieuTra() {
    }

    public PhieuTra(String phieuTra, PhieuMuon phieuMuon, DocGia docGia, NhanVien nhanVien, Date ngayTra, int soLuong,
            String tinhTrang, String trangThai) {
        this.phieuTra = phieuTra;
        this.phieuMuon = phieuMuon;
        this.docGia = docGia;
        this.nhanVien = nhanVien;
        this.ngayTra = ngayTra;
        this.soLuong = soLuong;
        this.tinhTrang = tinhTrang;
        this.trangThai = trangThai;
    }

    public String getPhieuTra() {
        return phieuTra;
    }

    public PhieuMuon getPhieuMuon() {
        return phieuMuon;
    }

    public DocGia getDocGia() {
        return docGia;
    }

    public NhanVien getNhanVien() {
        return nhanVien;
    }

    public Date getNgayTra() {
        return ngayTra;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public String getTinhTrang() {
        return tinhTrang;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setPhieuTra(String phieuTra) {
        this.phieuTra = phieuTra;
    }

    public void setPhieuMuon(PhieuMuon phieuMuon) {
        this.phieuMuon = phieuMuon;
    }

    public void setDocGia(DocGia docGia) {
        this.docGia = docGia;
    }

    public void setNhanVien(NhanVien nhanVien) {
        this.nhanVien = nhanVien;
    }

    public void setNgayTra(Date ngayTra) {
        this.ngayTra = ngayTra;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public void setTinhTrang(String tinhTrang) {
        this.tinhTrang = tinhTrang;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public ArrayList<CTPhieuTra> getListCTiet() {
        return list;
    }

    public void setListCTiet(ArrayList<CTPhieuTra> list) {
        this.list = list;
    }

}
