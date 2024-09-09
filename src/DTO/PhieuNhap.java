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
public class PhieuNhap {
    private String maPNhap;
    private NhanVien nhanvien;
    private NhaXuatBan nhaxuatban;
    private Float tongTien;
    private Date ngayNhap;
    private Integer trangThai;
    private ArrayList<CTPhieuNhap> list = new ArrayList<>();
    private float tongThanhTien;
    private int tongSoLuong;

    public float getTongThanhTien() {
        return tongThanhTien;
    }

    public void setTongThanhTien(float tongThanhTien) {
        this.tongThanhTien = tongThanhTien;
    }

    public int getTongSoLuong() {
        return tongSoLuong;
    }

    public void setTongSoLuong(int tongSoLuong) {
        this.tongSoLuong = tongSoLuong;
    }

    public PhieuNhap() {
    }

    public void set_NhaXuatBan(NhaXuatBan nhaxuatban) {
        this.nhaxuatban = nhaxuatban;
    }

    public NhaXuatBan get_NhaXuatBan() {
        return nhaxuatban;
    }

    public void set_NhanVien(NhanVien nhanvien) {
        this.nhanvien = nhanvien;
    }

    public NhanVien get_NhanVien() {
        return nhanvien;
    }

    public void set_maPNhap(String maPNhap) {
        this.maPNhap = maPNhap;
    }

    public String get_maPNhap() {
        return maPNhap;
    }

    public Date getNgayNhap() {
        return ngayNhap;
    }

    public void setNgayNhap(Date ngayNhap) {
        this.ngayNhap = ngayNhap;
    }

    public Float getTongTien() {
        return tongTien;
    }

    public void setTongTien(float tongTien) {
        this.tongTien = tongTien;
    }

    public Integer getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    public ArrayList<CTPhieuNhap> getListCTiet() {
        return list;
    }

    public void setListCTiet(ArrayList<CTPhieuNhap> list) {
        this.list = list;
    }

}
