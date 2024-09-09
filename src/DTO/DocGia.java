/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Admin
 */
public class DocGia {
    private String maDG;
    private String HoTen;
    private String SDT;
    private String Email;
    private int TrangThai;

    public DocGia() {
    }

    public DocGia(String maDG, String HoTen, String SDT, String Email, int TrangThai) {
        this.maDG = maDG;
        this.HoTen = HoTen;
        this.SDT = SDT;
        this.Email = Email;
        this.TrangThai = TrangThai;
    }

    public String getMaDG() {
        return maDG;
    }

    public void setMaDG(String maDG) {
        this.maDG = maDG;
    }

    public String getHoTen() {
        return HoTen;
    }

    public void setHoTen(String HoTen) {
        this.HoTen = HoTen;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public int getTrangThai() {
        return TrangThai;
    }

    public void setTrangThai(int TrangThai) {
        this.TrangThai = TrangThai;
    }

    // Kiểm tra xem 2 mã độc giả có giống nhau hay không
    public int Ktra_MaDGia(DocGia docgia) {
        if (this.maDG.equals(docgia.getMaDG()) == true)
            return 1;
        return 0;
    }

    // Kiểm tra 2 độc giả có tên với nhau không
    public int Ktra_TenDocGia(DocGia docgia) {
        if (this.HoTen.equals(docgia.getHoTen()) == true)
            return 1;
        return 0;
    }

    // Kiểm tra 2 email có trùng nhau hay không
    public int Ktra_EmailDGia(DocGia docgia) {
        if (this.Email.equals(docgia.getEmail()) == true)
            return 1;
        return 0;
    }

    public int Ktra_SDTDGia(DocGia docgia) {
        if (this.SDT.equals(docgia.getSDT()) == true)
            return 1;
        return 0;
    }

    public String toString() {
        return this.maDG + " " + this.HoTen + " " + this.SDT + " " + this.Email;
    }

}
