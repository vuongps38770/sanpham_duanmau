package com.example.myapplication.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    protected static final String DATABASE_NAME= "toocloselib";
    protected static final String TB_NGUOI_DUNG= "nguoidung";
    protected static final String TB_PHIEU_MUON= "phieumuon";
    protected static final String TB_CTPM= "ctpm";
    protected static final String TB_SACH= "sach";
    protected static final String TB_LOAI_SACH= "loaisach";
    private static String sqlNguoidung = "create table " +TB_NGUOI_DUNG+
            "(" +
            "tai_khoan text primary key," +
            "mat_khau text," +
            "ho_ten text,"+
            "role integer"+
            ")";
    private static String sqlPhieuMuon = "create table " +TB_PHIEU_MUON+
            "(" +
            "ma_phieu integer primary key autoincrement," +
            "ngay_muon text," +
            "ngay_tra text,"+
            "tai_khoan text,"+
            "trang_thai integer,"+
            "foreign key (tai_khoan) references " + TB_NGUOI_DUNG + "(tai_khoan)"+
            ")";
    private static String sqlLoaisach = "create table " +TB_LOAI_SACH+
            "(" +
            "ma_loai integer primary key autoincrement," +
            "ten_loai text"+
            ")";
    private static String sqlSach = "create table " +TB_SACH+
            "(" +
            "ma_sach integer primary key autoincrement," +
            "ten_sach text," +
            "tac_gia text,"+
            "gia_muon integer,"+
            "ma_loai integer,"+
            "foreign key (ma_loai) references " + TB_LOAI_SACH +" ON DELETE CASCADE"+
            ")";
    private static String sqlCTPM = "create table " +TB_CTPM+
            "(" +
            "ma_phieu integer,"+
            "ma_sach integer," +
            "primary key (ma_phieu,ma_sach),"+
            "foreign key (ma_phieu) references "+TB_PHIEU_MUON+"(ma_phieu),"+
            "foreign key (ma_sach) references "+TB_SACH+"(ma_sach)"+
            ")";




    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 6);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("PRAGMA foreign_keys = ON;");
        db.execSQL(sqlNguoidung);
        db.execSQL(sqlLoaisach);
        db.execSQL(sqlPhieuMuon);
        db.execSQL(sqlSach);
        db.execSQL(sqlCTPM);
        ContentValues admin1Values = new ContentValues();
        admin1Values.put("tai_khoan", "admin1");
        admin1Values.put("mat_khau", "vuongdz");
        admin1Values.put("ho_ten", "Administrator1");
        admin1Values.put("role", 1);
        db.insert(TB_NGUOI_DUNG, null, admin1Values);

        ContentValues admin2Values = new ContentValues();
        admin2Values.put("tai_khoan", "admin2");
        admin2Values.put("mat_khau", "sihehe");
        admin2Values.put("ho_ten", "Administrator2");
        admin2Values.put("role", 1);
        db.insert(TB_NGUOI_DUNG, null, admin2Values);
        insertSampleData(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion!= newVersion){
            db.execSQL("PRAGMA foreign_keys = OFF;");
            db.execSQL("drop table if exists "+TB_NGUOI_DUNG);
            db.execSQL("drop table if exists "+TB_PHIEU_MUON);
            db.execSQL("drop table if exists "+TB_CTPM);
            db.execSQL("drop table if exists "+TB_SACH);
            db.execSQL("drop table if exists "+TB_LOAI_SACH);
            db.execSQL("PRAGMA foreign_keys = ON;");
            onCreate(db);
        }

    }
    private void insertSampleData(SQLiteDatabase db) {
        // Insert sample users


        ContentValues user1 = new ContentValues();
        user1.put("tai_khoan", "user1");
        user1.put("mat_khau", "ff");
        user1.put("ho_ten", "User One");
        user1.put("role", 3);
        db.insert(TB_NGUOI_DUNG, null, user1);

        ContentValues user2 = new ContentValues();
        user2.put("tai_khoan", "v");
        user2.put("mat_khau", "v");
        user2.put("ho_ten", "v");
        user2.put("role", 3);
        db.insert(TB_NGUOI_DUNG, null, user2);

        // Insert sample loan records
        ContentValues pm1 = new ContentValues();
        pm1.put("ngay_muon", "2/2/2");
        pm1.put("ngay_tra", "2/2/2");
        pm1.put("tai_khoan", "user1");
        pm1.put("trang_thai", 1);
        db.insert(TB_PHIEU_MUON, null, pm1);

        ContentValues pm2 = new ContentValues();
        pm2.put("ngay_muon", "2/3/2");
        pm2.put("ngay_tra", "2/2/2");
        pm2.put("tai_khoan", "user2");
        pm2.put("trang_thai", 1);
        db.insert(TB_PHIEU_MUON, null, pm2);

        ContentValues pm3 = new ContentValues();
        pm3.put("ngay_muon", "2/2/2");
        pm3.put("ngay_tra", "2/4/2");
        pm3.put("tai_khoan", "user1");
        pm3.put("trang_thai", 1);
        db.insert(TB_PHIEU_MUON, null, pm3);

        ContentValues pm4 = new ContentValues();
        pm4.put("ngay_muon", "2/2/2");
        pm4.put("ngay_tra", "2/4/2");
        pm4.put("tai_khoan", "user2");
        pm4.put("trang_thai", 1);
        db.insert(TB_PHIEU_MUON, null, pm4);

        // Insert sample categories
        ContentValues category1 = new ContentValues();
        category1.put("ten_loai", "Category One");
        long categoryId1 = db.insert(TB_LOAI_SACH, null, category1);

        ContentValues category2 = new ContentValues();
        category2.put("ten_loai", "Category Two");
        long categoryId2 = db.insert(TB_LOAI_SACH, null, category2);

        // Insert sample books
        ContentValues book1 = new ContentValues();
        book1.put("ten_sach", "Book One");
        book1.put("tac_gia", "Author One");
        book1.put("gia_muon", 100);
        book1.put("ma_loai", categoryId1);
        long bookId1 = db.insert(TB_SACH, null, book1);

        ContentValues book2 = new ContentValues();
        book2.put("ten_sach", "Book Two");
        book2.put("tac_gia", "Author Two");
        book2.put("gia_muon", 200);
        book2.put("ma_loai", categoryId2);
        long bookId2 = db.insert(TB_SACH, null, book2);

        ContentValues book3 = new ContentValues();
        book3.put("ten_sach", "Book Three");
        book3.put("tac_gia", "Author Three");
        book3.put("gia_muon", 300);
        book3.put("ma_loai", categoryId1);
        long bookId3 = db.insert(TB_SACH, null, book3);

        // Insert sample loan details
        ContentValues ct1 = new ContentValues();
        ct1.put("ma_phieu", 1); // Assume ma_phieu is 1 for the first loan record
        ct1.put("ma_sach", bookId1);
        db.insert(TB_CTPM, null, ct1);

        ContentValues ct2 = new ContentValues();
        ct2.put("ma_phieu", 2); // Assume ma_phieu is 2 for the second loan record
        ct2.put("ma_sach", bookId2);
        db.insert(TB_CTPM, null, ct2);

        ContentValues ct3 = new ContentValues();
        ct3.put("ma_phieu", 3); // Assume ma_phieu is 3 for the third loan record
        ct3.put("ma_sach", bookId3);
        db.insert(TB_CTPM, null, ct3);
    }
}
