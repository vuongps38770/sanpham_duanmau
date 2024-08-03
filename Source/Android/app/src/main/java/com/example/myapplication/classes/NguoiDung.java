package com.example.myapplication.classes;

import java.io.Serializable;

public class NguoiDung implements Serializable {
    private String taikhoan,matkhau, hoten;
    private Integer role;

    public NguoiDung() {

    }

    public NguoiDung(String taikhoan, String matkhau, String hoten, Integer role) {
        this.taikhoan = taikhoan;
        this.matkhau = matkhau;
        this.hoten = hoten;
        this.role = role;
    }

    public NguoiDung(String taikhoan, String hoten) {
        this.taikhoan = taikhoan;
        this.hoten = hoten;
    }

    public NguoiDung(String hoten, Integer role) {
        this.hoten = hoten;
        this.role = role;
    }

    public NguoiDung(String taikhoan, String hoten, Integer role) {
        this.taikhoan = taikhoan;
        this.hoten = hoten;
        this.role = role;
    }

    public String getTaikhoan() {
        return taikhoan;
    }

    public void setTaikhoan(String taikhoan) {
        this.taikhoan = taikhoan;
    }

    public String getMatkhau() {
        return matkhau;
    }

    public void setMatkhau(String matkhau) {
        this.matkhau = matkhau;
    }

    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }
}
