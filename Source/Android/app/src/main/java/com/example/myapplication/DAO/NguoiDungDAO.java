package com.example.myapplication.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;
import android.widget.Toast;

import com.example.myapplication.classes.NguoiDung;
import com.example.myapplication.database.DBHelper;

import java.util.ArrayList;

public class NguoiDungDAO {
    private DBHelper dbHelper;
    public NguoiDungDAO(Context c){
        dbHelper = new DBHelper(c);
    }

    public Boolean addNguoiDung(NguoiDung nd) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("tai_khoan", nd.getTaikhoan());
        values.put("mat_khau", nd.getMatkhau());
        values.put("ho_ten", nd.getHoten());
        values.put("role", nd.getRole());
        long result = -1;
        result = db.insert("nguoidung", null, values);
        return result > 0;
    }


    public boolean validateUser(String taikhoan, String matkhau) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = null;
        boolean isValid = false;
        cursor = db.rawQuery("SELECT * FROM nguoidung WHERE tai_khoan=? AND mat_khau=?", new String[]{taikhoan, matkhau});
        isValid = cursor.getCount() > 0;

        return isValid;
    }


    public int getRole(String taikhoan) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("nguoidung", new String[]{"role"}, "tai_khoan=?", new String[]{taikhoan}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            int columnindex = cursor.getColumnIndex("role");
            if (columnindex != -1) {
                int role = cursor.getInt(columnindex);
                cursor.close();
                return role;
            } else {
                return -1;
            }
        } else return -1;
    }


    public ArrayList<NguoiDung> getUsersAndThuthu() {
        ArrayList<NguoiDung> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("Select * from nguoidung", null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                int hoten = cursor.getColumnIndex("ho_ten");
                int role = cursor.getColumnIndex("role");
                int tk = cursor.getColumnIndex("tai_khoan");
                if (hoten != -1 && role != -1) {
                    NguoiDung nguoiDung = new NguoiDung(cursor.getString(tk), cursor.getString(hoten), cursor.getInt(role));
                    list.add(nguoiDung);
                }
            }
            cursor.close();
        }
        return list;
    }


    public boolean xoa(NguoiDung nguoiDung) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        long result = sqLiteDatabase.delete("nguoidung", "tai_khoan = ?", new String[]{nguoiDung.getTaikhoan()});
        return result != 0;
    }


    public boolean setRole(NguoiDung nguoiDung) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("role", nguoiDung.getRole());
        long result = sqLiteDatabase.update("nguoidung", contentValues, "tai_khoan=?", new String[]{nguoiDung.getTaikhoan()});
        return result != 0;
    }


    public Boolean CheckIsExistAccount(String nguoiDung) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from nguoidung where tai_khoan=?", new String[]{nguoiDung});
        boolean isValid = cursor.getCount() > 0;
        cursor.close();
        return isValid;
    }

    public Boolean pwIsChanged(String taikhoan, String matkhau) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String query = "UPDATE nguoidung SET mat_khau = ? WHERE tai_khoan = ?";
        try {
            SQLiteStatement statement = db.compileStatement(query);
            statement.bindString(1, matkhau);
            statement.bindString(2, taikhoan);
            statement.execute();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getTenByTaiKhoan(String account){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String ten = "null";
        Cursor cursor = db.rawQuery("Select ho_ten from nguoidung where tai_khoan=?",new String[]{account});
        if(cursor.moveToFirst()){
            int indx = cursor.getColumnIndex("ho_ten");
            ten = cursor.getString(indx);
        }

        return ten;
    }
}
