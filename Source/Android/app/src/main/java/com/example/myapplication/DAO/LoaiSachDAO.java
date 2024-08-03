package com.example.myapplication.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.myapplication.classes.LoaiSach;
import com.example.myapplication.classes.NguoiDung;
import com.example.myapplication.classes.Sach;
import com.example.myapplication.database.DBHelper;

import java.util.ArrayList;

public class LoaiSachDAO {
    private String TB_LOAI_SACH = "loaisach";
    DBHelper dbHelper;
    public LoaiSachDAO(Context c){
        dbHelper = new DBHelper(c);
    }
    public Boolean addLoaiSach(String tenloai){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values =new ContentValues();
        values.put("ten_loai",tenloai);
        long result = db.insert(TB_LOAI_SACH,null,values);
        return result!=-1;
    }
    public boolean validateTenLoaiSach(String tenloai){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from loaisach where ten_loai=?", new String[]{tenloai});
        boolean isValid = cursor.getCount()>0;
        cursor.close();
        return isValid;
    }

    public boolean updateLoaiSach(int maLoai, String tenLoai) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("ten_loai", tenLoai);
        long result = db.update(TB_LOAI_SACH, values, "ma_loai=?", new String[] { String.valueOf(maLoai) });
        db.close();
        return result!=-1;
    }

    public boolean deleteLoaiSach(int maLoai) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int result = db.delete(TB_LOAI_SACH, "ma_loai=?", new String[] { String.valueOf(maLoai) });
        boolean bool = result!=1;
        if(bool){
            try {
                db.delete("sach", "ma_loai = ?", new String[]{String.valueOf(maLoai)});
            }catch (Exception e){
            }

        }
        db.close();
        return result!=-1;
    }

    public ArrayList<LoaiSach> getAllLoaiSach() {
        ArrayList<LoaiSach> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("Select * from " + TB_LOAI_SACH, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int maI = cursor.getColumnIndex("ma_loai");
                int tenloaiI = cursor.getColumnIndex("ten_loai");
                do {
                    if (maI != -1 && tenloaiI != -1) {
                        int ma = cursor.getInt(maI);
                        String tenloai = cursor.getString(tenloaiI);
                        LoaiSach loaiSach = new LoaiSach(ma, tenloai);
                        list.add(loaiSach);
                    }
                } while (cursor.moveToNext());
            }
        }
        cursor.close();
        sqLiteDatabase.close();
        return list;
    }
    public int getSoLuong(String maLoai) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT COUNT(ma_loai) AS soluong FROM sach WHERE ma_loai=?", new String[]{maLoai});
        int soluong = 0;

        try {
            if (cursor != null && cursor.moveToFirst()) {
                int col = cursor.getColumnIndex("soluong");
                if (col != -1) {
                    soluong = cursor.getInt(col);
                }
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return soluong;
    }

}
