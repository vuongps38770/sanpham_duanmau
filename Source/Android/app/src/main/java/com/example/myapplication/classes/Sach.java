package com.example.myapplication.classes;

public class Sach {
    private String  tensach, tacgia;
    private int masach,giamuon, maloai, luotmuon, maphieu;

    public Sach() {

    }

    public int getMaphieu() {
        return maphieu;
    }

    public void setMaphieu(int maphieu) {
        this.maphieu = maphieu;
    }

    public Sach(String tensach, String tacgia) {
        this.tensach = tensach;
        this.tacgia = tacgia;
    }

    public Sach(String tensach, String tacgia, int giamuon, int maloai) {
        this.tensach = tensach;
        this.tacgia = tacgia;
        this.giamuon = giamuon;
        this.maloai = maloai;
    }

    public int getLuotmuon() {
        return luotmuon;
    }

    public void setLuotmuon(int luotmuon) {
        this.luotmuon = luotmuon;
    }

    public Sach(String tensach, String tacgia, int masach, int giamuon, int maloai) {
        this.tensach = tensach;
        this.tacgia = tacgia;
        this.masach = masach;
        this.giamuon = giamuon;
        this.maloai = maloai;
    }
    public Sach(String tensach, String tacgia, int luotmuon) {
        this.tensach = tensach;
        this.tacgia = tacgia;
        this.luotmuon = luotmuon;
    }

    public int getMasach() {
        return masach;
    }

    public void setMasach(int masach) {
        this.masach = masach;
    }

    public String getTensach() {
        return tensach;
    }

    public void setTensach(String tensach) {
        this.tensach = tensach;
    }

    public Sach(int masach,String tensach, String tacgia, int maloai) {
        this.tensach = tensach;
        this.tacgia = tacgia;
        this.masach = masach;
        this.maloai = maloai;
    }

    public Sach(int maloai,String tensach, String tacgia) {
        this.tensach = tensach;
        this.tacgia = tacgia;
        this.maloai = maloai;
    }

    public String getTacgia() {
        return tacgia;
    }

    public void setTacgia(String tacgia) {
        this.tacgia = tacgia;
    }

    public int getMaloai() {
        return maloai;
    }

    public void setMaloai(int maloai) {
        this.maloai = maloai;
    }

    public int getGiamuon() {
        return giamuon;
    }

    public void setGiamuon(int giamuon) {
        this.giamuon = giamuon;
    }

}
