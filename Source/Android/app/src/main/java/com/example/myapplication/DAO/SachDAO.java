package com.example.myapplication.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.myapplication.classes.LoaiSach;
import com.example.myapplication.classes.PhieuChoMuon;
import com.example.myapplication.classes.PhieuMuon;
import com.example.myapplication.classes.Sach;
import com.example.myapplication.database.DBHelper;

import java.util.ArrayList;

public class SachDAO {
    DBHelper dbHelper;
    public SachDAO(Context c){
        dbHelper = new DBHelper(c);
    }
    public Boolean addSach(Sach sach){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values =new ContentValues();
        values.put("ten_sach", sach.getTensach());
        values.put("tac_gia", sach.getTacgia());
        values.put("gia_muon", sach.getGiamuon());
        values.put("ma_loai", sach.getMaloai());
        long result = db.insert("sach", null, values);
        return result>0;
    }
    public boolean validateSach(String tensach, String tacgia){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from sach where ten_sach=? and tac_gia=?", new String[]{tensach, tacgia});
        boolean isValid = cursor.getCount()>0;
        cursor.close();
        return isValid;
    }


    public boolean deleteSach(int masach) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long result =db.delete("sach","ma_sach=?",new String[]{String.valueOf(masach)});
        return result>=0;
    }

    public String getTheLoai(int maloai) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("loaisach", new String[]{"ten_loai"}, "ma_loai=?", new String[]{String.valueOf(maloai)}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            int columnindex = cursor.getColumnIndex("ten_loai");
            if (columnindex != -1) {
                String tac_gia = cursor.getString(columnindex);
                cursor.close();
                return tac_gia;
            } else {
                return null;
            }
        } else return null;

    }
    public ArrayList<Sach> getAllSach() {
        ArrayList<Sach> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("Select * from sach"  , null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int maI = cursor.getColumnIndex("ma_sach");
                int tenSachI = cursor.getColumnIndex("ten_sach");
                int tacGiaI = cursor.getColumnIndex("tac_gia");
                int theLoaiI = cursor.getColumnIndex("ma_loai");
                int giamuonI =cursor.getColumnIndex("gia_muon");
                do {
                    if (maI != -1 && tenSachI != -1&& tacGiaI != -1&& theLoaiI != -1&&giamuonI!=-1) {
                        int ma = cursor.getInt(maI);
                        int theLoai = cursor.getInt(theLoaiI);
                        String tenSach = cursor.getString(tenSachI);
                        String tacGia = cursor.getString(tacGiaI);
                        int giaMuon = cursor.getInt(giamuonI);
                        Sach sach = new Sach(tenSach,tacGia,ma,giaMuon,theLoai);
                        list.add(sach);
                    }
                } while (cursor.moveToNext());
            }
        }
        cursor.close();
        sqLiteDatabase.close();
        return list;
    }
    public boolean updateSach(int maLoai,String tensach,String tacgia,int giamuon,int masach){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("ma_loai",maLoai);
        contentValues.put("ten_sach",tensach);
        contentValues.put("tac_gia",tacgia);
        contentValues.put("gia_muon",giamuon);
        long result = db.update("sach",contentValues,"ma_sach =?",new String[]{String.valueOf(masach)});
        return result!=-1;
    }
    public ArrayList<Sach> getAllSachByLuotMuon() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "select s.ten_sach, s.tac_gia, COALESCE(COUNT(ct.ma_sach), 0) AS luot_muon " +
                "from sach s " +
                "left join ctpm ct ON s.ma_sach = ct.ma_sach " +
                "left join phieumuon pm ON ct.ma_phieu = pm.ma_phieu AND pm.trang_thai IN (2, 3) " +
                "group by s.ten_sach, s.tac_gia " +
                "order by luot_muon DESC";

        Cursor cursor = db.rawQuery(query, null);
        ArrayList<Sach> list = new ArrayList<>();

        if (cursor != null) {
            try {
                int luotMuonIndex = cursor.getColumnIndex("luot_muon");
                int tenSachIndex = cursor.getColumnIndex("ten_sach");
                int tacGiaIndex = cursor.getColumnIndex("tac_gia");

                if (luotMuonIndex != -1 && tenSachIndex != -1 && tacGiaIndex != -1) {
                    if (cursor.moveToFirst()) {
                        do {
                            String tenSach = cursor.getString(tenSachIndex);
                            String tacGia = cursor.getString(tacGiaIndex);
                            int luotMuon = cursor.getInt(luotMuonIndex);

                            Sach sach = new Sach(tenSach, tacGia, luotMuon);
                            list.add(sach);
                        } while (cursor.moveToNext());
                    }
                }
            } finally {
                cursor.close();
            }
        }

        return list;
    }
    public Sach getSachbyManguoidungAndMasach (String maNguoidung, int maSach){
         SQLiteDatabase db = dbHelper.getReadableDatabase();
         String query = "select sach.ten_sach, sach.tac_gia " +
                 "from sach " +
//                 "join loaisach on sach.loai_sach = loaisach.loai_sach " +
                 "join ctpm on ctpm.ma_sach = sach.ma_sach " +
                 "join phieumuon on phieumuon.ma_phieu = ctpm.ma_phieu " +
                 "where sach.ma_sach = ? and phieumuon.tai_khoan = ? ";
         Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(maSach), maNguoidung });
         int tenSachIndex = cursor.getColumnIndex("ten_sach");
         int tenTacgiaIndex = cursor.getColumnIndex("tac_gia");
         if(cursor.moveToFirst() && cursor!=null && tenTacgiaIndex!=-1 && tenSachIndex!=-1){
             String tenSach = cursor.getString(tenSachIndex);
             String tenTacGia = cursor.getString(tenTacgiaIndex);
             Sach sach = new Sach(tenSach, tenTacGia);
             return sach;
         }else{
             return null;
         }

    }
    public String getLoaiSachbyManguoidungAndMasach (String maNguoidung, int maSach){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "select loaisach.ten_loai " +
                "from loaisach " +
                "join sach on sach.ma_loai = loaisach.ma_loai " +
                "join ctpm on ctpm.ma_sach = sach.ma_sach " +
                "join phieumuon on phieumuon.ma_phieu = ctpm.ma_phieu " +
                "where sach.ma_sach = ? and phieumuon.tai_khoan = ? ";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(maSach), maNguoidung });
        int LoaiSachIndex = cursor.getColumnIndex("loai_sach");
        if(cursor.moveToFirst() && cursor!=null  && LoaiSachIndex!=-1){
            String loaisach = cursor.getString(LoaiSachIndex);
            return loaisach;
        }else{
            return null;
        }


    }
    public int getMaphieubyManguoidungAndMasach (String maNguoidung, int maSach){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "select phieumuon.ma_phieu " +
                "from phieumuon " +
                "join ctpm on ctpm.ma_phieu = phieumuon.ma_phieu " +
                "join sach on sach.ma_sach = ctpm.ma_sach " +
                "where sach.ma_sach = ? and phieumuon.tai_khoan = ? ";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(maSach), maNguoidung });
        int MaphieuIndex = cursor.getColumnIndex("ma_phieu");
        if(cursor.moveToFirst() && cursor!=null  && MaphieuIndex!=-1){
            int maphieu = cursor.getInt(MaphieuIndex);
            return maphieu;
        }else{
            return -1;
        }

    }



}

