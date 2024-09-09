/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

import javax.management.loading.PrivateClassLoader;

/**
 *
 * @author Admin
 */
public class Sach {
	private String maSach;
	private String tenSach;
	private String moTa;
	private String namXB;
	private TacGia tacGia;
	private NhaXuatBan maNXB;
	private int donGia;
	private int soLuong;
	private int trangThai;
	private LoaiSach maLS;

	public Sach() {
	}

	public Sach(String maSach, String tenSach, String moTa, String namXB, TacGia tacGia, NhaXuatBan maNXB, int donGia,
			int soLuong, int trangThai, LoaiSach maLS) {
		super();
		this.maSach = maSach;
		this.tenSach = tenSach;
		this.moTa = moTa;
		this.namXB = namXB;
		this.tacGia = tacGia;
		this.maNXB = maNXB;
		this.donGia = donGia;
		this.soLuong = soLuong;
		this.trangThai = trangThai;
		this.maLS = maLS;
	}

	public String getMaSach() {
		return maSach;
	}

	public void setMaSach(String maSach) {
		this.maSach = maSach;
	}

	public String getTenSach() {
		return tenSach;
	}

	public void setTenSach(String tenSach) {
		this.tenSach = tenSach;
	}

	public String getMoTa() {
		return moTa;
	}

	public void setMoTa(String moTa) {
		this.moTa = moTa;
	}

	public String getNamXB() {
		return namXB;
	}

	public void setNamXB(String namXB) {
		this.namXB = namXB;
	}

	public TacGia getTacGia() {
		return tacGia;
	}

	public void setTacGia(TacGia tacGia) {
		this.tacGia = tacGia;
	}

	public NhaXuatBan getMaNXB() {
		return maNXB;
	}

	public void setMaNXB(NhaXuatBan maNXB) {
		this.maNXB = maNXB;
	}

	public int getDonGia() {
		return donGia;
	}

	public void setDonGia(int donGia) {
		this.donGia = donGia;
	}

	public int getSoLuong() {
		return soLuong;
	}

	public void setSoLuong(int soLuong) {
		this.soLuong = soLuong;
	}

	public int getTrangThai() {
		return trangThai;
	}

	public void setTrangThai(int trangThai) {
		this.trangThai = trangThai;
	}

	public LoaiSach getMaLS() {
		return maLS;
	}

	public void setMaLS(LoaiSach maLS) {
		this.maLS = maLS;
	}

}
