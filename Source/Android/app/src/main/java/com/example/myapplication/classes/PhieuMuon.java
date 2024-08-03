package com.example.myapplication.classes;

public class PhieuMuon {
    private Integer maphieu , trangthai;
    private String ngaymuon, ngaytra, taikhoan;

    public PhieuMuon() {

    }

    public PhieuMuon(Integer maphieu, Integer trangthai, String taikhoan) {
        this.maphieu = maphieu;
        this.trangthai = trangthai;
        this.taikhoan = taikhoan;
    }
    public PhieuMuon(Integer maphieu, String ngaymuon,String ngaytra,Integer trangthai) {
        this.maphieu = maphieu;
        this.trangthai = trangthai;
        this.ngaymuon = ngaymuon;
        this.ngaytra = ngaytra;
    }

    public Integer getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(Integer trangthai) {
        this.trangthai = trangthai;
    }

    public PhieuMuon(Integer maphieu,  String ngaymuon, String ngaytra,String taikhoan,Integer trangthai) {
        this.maphieu = maphieu;
        this.trangthai = trangthai;
        this.ngaymuon = ngaymuon;
        this.ngaytra = ngaytra;
        this.taikhoan = taikhoan;
    }



    public Integer getMaphieu() {
        return maphieu;
    }

    public void setMaphieu(Integer maphieu) {
        this.maphieu = maphieu;
    }

    public String getNgaymuon() {
        return ngaymuon;
    }

    public void setNgaymuon(String ngaymuon) {
        this.ngaymuon = ngaymuon;
    }

    public String getNgaytra() {
        return ngaytra;
    }

    public void setNgaytra(String ngaytra) {
        this.ngaytra = ngaytra;
    }

    public String getTaikhoan() {
        return taikhoan;
    }

    public void setTaikhoan(String taikhoan) {
        this.taikhoan = taikhoan;
    }
}
