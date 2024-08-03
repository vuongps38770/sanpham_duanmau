package com.example.myapplication.classes;

public class ChoMuon {
    private String taiKhoan;
    private int masach;

    public ChoMuon(String taiKhoan, int masach) {
        this.taiKhoan = taiKhoan;
        this.masach = masach;
    }

    public ChoMuon() {
    }

    public String getTaiKhoan() {
        return taiKhoan;
    }

    public void setTaiKhoan(String taiKhoan) {
        this.taiKhoan = taiKhoan;
    }

    public int getMasach() {
        return masach;
    }

    public void setMasach(int masach) {
        this.masach = masach;
    }
}
