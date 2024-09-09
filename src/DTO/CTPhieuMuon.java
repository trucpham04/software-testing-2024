/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

/**
 *
 * @author Admin
 */
public class CTPhieuMuon {
	private String phieuMuon;
	private SachCT sach;
	private int soLuong;
	private int SoLanMuon;
	private int TongSL;

	public int getSoLanMuon() {
		return SoLanMuon;
	}

	public void setSoLanMuon(int soLanMuon) {
		SoLanMuon = soLanMuon;
	}

	public int getTongSL() {
		return TongSL;
	}

	public void setTongSL(int tongSL) {
		TongSL = tongSL;
	}

	public CTPhieuMuon(String phieuMuon, SachCT sach, int soLuong) {
		this.phieuMuon = phieuMuon;
		this.sach = sach;
		this.soLuong = soLuong;
	}

	public CTPhieuMuon() {
	}

	public String getPhieuMuon() {
		return phieuMuon;
	}

	public void setPhieuMuon(String phieuMuon) {
		this.phieuMuon = phieuMuon;
	}

	public SachCT getSach() {
		return sach;
	}

	public void setSach(SachCT sach) {
		this.sach = sach;
	}

	public int getSoLuong() {
		return soLuong;
	}

	public void setSoLuong(int soLuong) {
		this.soLuong = soLuong;
	}

}
