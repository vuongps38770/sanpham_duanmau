package com.example.myapplication.classes;

public class PhieuChoMuon {
     private String tensach, tacgia, theloai;
     private int maphieu;

    public PhieuChoMuon() {

    }

    public PhieuChoMuon(String tensach, String tacgia, String theloai, int maphieu) {
        this.tensach = tensach;
        this.tacgia = tacgia;
        this.theloai = theloai;
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

    public int getMaphieu() {
        return maphieu;
    }

    public void setMaphieu(int maphieu) {
        this.maphieu = maphieu;
    }
}
