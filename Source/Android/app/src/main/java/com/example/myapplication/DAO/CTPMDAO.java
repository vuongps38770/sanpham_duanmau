package com.example.myapplication.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.myapplication.classes.CTPM;
import com.example.myapplication.classes.PhieuMuon;
import com.example.myapplication.database.DBHelper;

public class CTPMDAO {
    DBHelper dbHelper;
    public CTPMDAO(Context c){
        dbHelper = new DBHelper(c);
    }
    public Boolean addPCTPM(CTPM ct){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values =new ContentValues();
        values.put("ma_phieu",ct.getMaphieu());
        values.put("ma_sach",ct.getMasach());
        long result = db.insert("ctpm",null,values);
        return result>0;
    }
    public boolean validateCTPM(Integer maphieu, Integer masach){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from ctpm where ma_phieu=? and ma_sach=?", new String[]{maphieu.toString(), masach.toString()});
        boolean isValid = cursor.getCount()>0;
        cursor.close();
        return isValid;
    }


}
