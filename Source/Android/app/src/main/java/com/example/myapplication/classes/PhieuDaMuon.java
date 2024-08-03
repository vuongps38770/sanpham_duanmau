package com.example.myapplication.classes;

public class PhieuDaMuon {
     private String tensach, tacgia, theloai, ngaymuon;
     private int maphieu;

    public PhieuDaMuon() {

    }

    public PhieuDaMuon(String tensach, String tacgia, String theloai,String ngaymuon, int maphieu) {
        this.tensach = tensach;
        this.tacgia = tacgia;
        this.theloai = theloai;
        this.ngaymuon = ngaymuon;
        this.maphieu = maphieu;
    }

    public String getTensach() {
        return tensach;
    }

    public void setTensach(String tensach) {
        this.tensach = tensach;
    }

    public String getTacgia() {
        return tacgia;
    }

    public void setTacgia(String tacgia) {
        this.tacgia = tacgia;
    }

    public String getTheloai() {
        return theloai;
    }

    public void setTheloai(String theloai) {
        this.theloai = theloai;
    }

    public String getNgaymuon() {
        return ngaymuon;
    }

    public void setNgaymuon(String ngaymuon) {
        this.ngaymuon = ngaymuon;
    }

    public int getMaphieu() {
        return maphieu;
    }

    public void setMaphieu(int maphieu) {
        this.maphieu = maphieu;
    }
}
