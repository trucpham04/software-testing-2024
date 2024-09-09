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
public class PhieuMuon {
    private String maPhieuMuon;
    NhanVien nhanvien;
    DocGia docgia;
    private Date ngayMuon;
    private Date ngayTra;
    private int soLuong;
    private String trangThai;
    private ArrayList<CTPhieuMuon> list=new ArrayList<>();
    private SachCT sachmuon1;

    public SachCT getSachmuon1() {
		return sachmuon1;
	}

	public void setSachmuon1(SachCT sachmuon1) {
		this.sachmuon1 = sachmuon1;
	}

	public PhieuMuon(String maPhieuMuon, NhanVien nhanvien, DocGia docgia, Date ngayMuon, Date ngayTra, int soLuong) {
    }

    public PhieuMuon(String maPhieuMuon, NhanVien nhanvien, DocGia docgia, Date ngayMuon, Date ngayTra, int soLuong, String trangThai) {
        this.maPhieuMuon = maPhieuMuon;
        this.nhanvien = nhanvien;
        this.docgia = docgia;
        this.ngayMuon = ngayMuon;
        this.ngayTra = ngayTra;
        this.soLuong = soLuong;
        this.trangThai = trangThai;
    }

    public PhieuMuon() {
    }

    public DocGia getDocGia() {
        return docgia;
    }

    public void setDocGia(DocGia docgia) {
        this.docgia = docgia;
    }

    public NhanVien getMaNhanVien() {
        return nhanvien;
    }

    public void setMaNhanVien(NhanVien nhanvien) {
        this.nhanvien = nhanvien;
    }

    public String getMaPhieuMuon() {
        return maPhieuMuon;
    }

    public void setMaPhieuMuon(String maPhieuMuon) {
        this.maPhieuMuon = maPhieuMuon;
    }

    public Date getNgayMuon() {
        return ngayMuon;
    }

    public void setNgayMuon(Date ngayMuon) {
        this.ngayMuon = ngayMuon;
    }

    public Date getNgayTra() {
        return ngayTra;
    }

    public void setNgayTra(Date ngayTra) {
        this.ngayTra = ngayTra;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }
    public ArrayList<CTPhieuMuon> getListCTiet() {
        return list;
    }

    public void setListCTiet(ArrayList<CTPhieuMuon>list) {
        this.list = list;
    }
    
}


