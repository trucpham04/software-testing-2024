/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

/**
 *
 * @author Admin
 */
public class SachCT {
    private String maSach;
    private String tenSach;
    private String moTa;
    private String namXB;
    private TacGia tacGia;
    private NhaXuatBan nhaXuatBan;
    private LoaiSach loaiSach;
    private Integer donGia;
    private Integer soLuong;
    private Integer trangThai;

    public SachCT() {
    }

    public void set_LoaiSach(LoaiSach loaiSach) {
        this.loaiSach = loaiSach;
    }

    public LoaiSach get_LoaiSach() {
        return loaiSach;
    }

    public void set_nhaXuatBan(NhaXuatBan nhaXuatBan) {
        this.nhaXuatBan = nhaXuatBan;
    }

    /**
     *
     * @return
     */
    public NhaXuatBan get_nhaXuatBan() {
        return nhaXuatBan;
    }

    public void set_tacGia(TacGia tacGia) {
        this.tacGia = tacGia;
    }

    public TacGia get_tacGia() {
        return tacGia;

    }

    public void set_TrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    /**
     *
     * @return
     */
    public Integer get_TrangThai() {
        return trangThai;
    }

    public void set_SoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public Integer get_SoLuong() {
        return soLuong;
    }

    public void set_DonGia(int dongia) {
        this.donGia = dongia;
    }

    public Integer get_DonGia() {
        return donGia;
    }

    public void set_NamXB(String namXB) {
        this.namXB = namXB;
    }

    public String get_NamXB() {
        return namXB;
    }

    public void set_MoTa(String moTa) {
        this.moTa = moTa;
    }

    public String get_MoTa() {
        return moTa;
    }

    public void set_TenSach(String tenSach) {
        this.tenSach = tenSach;
    }

    public String get_TenSach() {
        return tenSach;
    }

    public void set_MaSach(String maSach) {
        this.maSach = maSach;
    }

    public String get_MaSach() {
        return maSach;
    }

}
