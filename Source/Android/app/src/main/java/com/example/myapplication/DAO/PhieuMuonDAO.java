package com.example.myapplication.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.example.myapplication.classes.GetToday;
import com.example.myapplication.classes.NguoiDung;
import com.example.myapplication.classes.PhieuChoMuon;
import com.example.myapplication.classes.PhieuDaMuon;
import com.example.myapplication.classes.PhieuMuon;
import com.example.myapplication.classes.Sach;
import com.example.myapplication.database.DBHelper;

import java.util.ArrayList;

public class PhieuMuonDAO {
    DBHelper dbHelper;
    Context c;
    public PhieuMuonDAO(Context c){
        dbHelper = new DBHelper(c);
        this.c=c;
    }


    private ArrayList<PhieuMuon> getAllPhieuMuon(int trangthai){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from phieumuon",null);
        ArrayList<PhieuMuon> list = new ArrayList<>();
        if(cursor!=null){
            int mpI = cursor.getColumnIndex("ma_phieu");
            int nmI = cursor.getColumnIndex("ngay_muon");
            int ntI = cursor.getColumnIndex("ngay_tra");
            int tkI = cursor.getColumnIndex("tai_khoan");
            int ttI = cursor.getColumnIndex("trang_thai");
            if(cursor.moveToFirst()){
                if(mpI!=-1&&nmI!=-1&&ntI!=-1&&tkI!=-1&&ttI!=-1){
                    do{
                        int mp = cursor.getInt(mpI);
                        String nm = cursor.getString(nmI);
                        String nt = cursor.getString(ntI);
                        String tk = cursor.getString(tkI);
                        int tt = cursor.getInt(ttI);
                        if(tt==trangthai){
                            PhieuMuon phieuMuon = new PhieuMuon(mp,nm,nt,tk,tt);
                            list.add(phieuMuon);
                        }
                    }while (cursor.moveToNext());
                }
            }
        }
        db.close();
        cursor.close();
        return list;
    }
    public ArrayList<PhieuMuon> getAllPhieuChuaMuon(){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from phieumuon",null);
        ArrayList<PhieuMuon> list = new ArrayList<>();
        if(cursor!=null){
            int mpI = cursor.getColumnIndex("ma_phieu");
            int tkI = cursor.getColumnIndex("tai_khoan");
            int ttI = cursor.getColumnIndex("trang_thai");
            if(cursor.moveToFirst()){
                if(mpI!=-1&&tkI!=-1&&ttI!=-1){
                    do{
                        int mp = cursor.getInt(mpI);
                        String tk = cursor.getString(tkI);
                        int tt = cursor.getInt(ttI);
                        if(tt==1){
                            PhieuMuon phieuMuon = new PhieuMuon(mp,tt,tk);
                            list.add(phieuMuon);
                        }
                    }while (cursor.moveToNext());
                }
            }
        }
        db.close();
        cursor.close();
        return list;
    }
    public ArrayList<PhieuMuon> getAllThongTinTrangThaiPM() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "select * from phieumuon where trang_thai=2 or trang_thai=3";
        Cursor cursor = db.rawQuery(query, null);
        ArrayList<PhieuMuon> list = new ArrayList<>();

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int maphieuI = cursor.getColumnIndex("ma_phieu");
                int ngaymuonI = cursor.getColumnIndex("ngay_muon");
                int ngaytraI = cursor.getColumnIndex("ngay_tra");
                int trangthaiI = cursor.getColumnIndex("trang_thai");

                int maphieu = cursor.getInt(maphieuI);
                String ngaymuon = cursor.getString(ngaymuonI);
                String ngaytra = cursor.getString(ngaytraI);
                int trangthai = cursor.getInt(trangthaiI);

                if (trangthai == 2 && ngaymuonI != -1) {
                    PhieuMuon phieuMuon = new PhieuMuon(maphieu, ngaymuon, null, trangthai);
                    list.add(phieuMuon);
                } else if (trangthai == 3 && ngaytraI != -1) {
                    PhieuMuon phieuMuon = new PhieuMuon(maphieu, null, ngaytra, trangthai);
                    list.add(phieuMuon);
                }
            } while (cursor.moveToNext());

            cursor.close();
        }

        if (list.isEmpty()) {
            return null;
        } else {
            return list;
        }
    }
    public ArrayList<PhieuChoMuon> getAllPhieuByManguoidung_ChoMuon(String maNguoidung){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "select s.ten_sach, s.tac_gia, ls.ten_loai, pm.ma_phieu from sach s " +
                "join loaisach ls on ls.ma_loai = s.ma_loai " +
                "join ctpm ct on ct.ma_sach = s.ma_sach " +
                "join phieumuon pm on pm.ma_phieu = ct.ma_phieu " +
                "where pm.tai_khoan =? and pm.trang_thai=?";
        Cursor cursor = db.rawQuery(query, new String[]{maNguoidung,"1"});
        ArrayList<PhieuChoMuon> list = new ArrayList<>();
        if(cursor!=null){
            int TenSachIndex = cursor.getColumnIndex("ten_sach");
            int TacGiaIndex = cursor.getColumnIndex("tac_gia");
            int TenLoaiIndex = cursor.getColumnIndex("ten_loai");
            int MaPhieuIndex = cursor.getColumnIndex("ma_phieu");
            if(cursor.moveToFirst() ){
                if( TenSachIndex!=-1 && TacGiaIndex!=-1 && TenLoaiIndex!=-1 && MaPhieuIndex!=-1){
                    do {
                        String tenSach = cursor.getString(TenSachIndex);
                        String tacGia = cursor.getString(TacGiaIndex);
                        String tenLoai = cursor.getString(TenLoaiIndex);
                        int maPhieu = cursor.getInt(MaPhieuIndex);
                        PhieuChoMuon phieu = new PhieuChoMuon(tenSach, tacGia, tenLoai, maPhieu);
                        list.add(phieu);
                    }while (cursor.moveToNext());
                }
            }
        }
        cursor.close();
        db.close();
        return list;
    }
    public ArrayList<PhieuDaMuon> getAllPhieuByManguoidung_DaMuon(String maNguoidung){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "select s.ten_sach, s.tac_gia, ls.ten_loai, pm.ngay_muon,pm.ma_phieu from sach s " +
                "join loaisach ls on ls.ma_loai = s.ma_loai " +
                "join ctpm ct on ct.ma_sach = s.ma_sach " +
                "join phieumuon pm on pm.ma_phieu = ct.ma_phieu " +
                "where pm.tai_khoan =? and pm.trang_thai=?";
        Cursor cursor = db.rawQuery(query, new String[]{maNguoidung,"2"});
        ArrayList<PhieuDaMuon> list = new ArrayList<>();
        if(cursor!=null){
            int TenSachIndex = cursor.getColumnIndex("ten_sach");
            int TacGiaIndex = cursor.getColumnIndex("tac_gia");
            int TenLoaiIndex = cursor.getColumnIndex("ten_loai");
            int NgayMuonIndex = cursor.getColumnIndex("ngay_muon");
            int MaPhieuIndex = cursor.getColumnIndex("ma_phieu");
            if(cursor.moveToFirst() ){
                if( TenSachIndex!=-1 && TacGiaIndex!=-1 && TenLoaiIndex!=-1 && NgayMuonIndex!=-1 && MaPhieuIndex!=-1){
                    do {
                        String tenSach = cursor.getString(TenSachIndex);
                        String tacGia = cursor.getString(TacGiaIndex);
                        String tenLoai = cursor.getString(TenLoaiIndex);
                        String NgayMuon = cursor.getString(NgayMuonIndex);
                        int maPhieu = cursor.getInt(MaPhieuIndex);
                        PhieuDaMuon phieu = new PhieuDaMuon(tenSach, tacGia, tenLoai,NgayMuon, maPhieu);
                        list.add(phieu);
                    }while (cursor.moveToNext());
                }
            }
        }
        cursor.close();
        db.close();
        return list;
    }

    public Sach getSachBYMaPM_tg_ts(int maPhieu){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT sach.ten_sach, sach.tac_gia "+
                "from phieumuon "+
                "join ctpm on ctpm.ma_phieu = phieumuon.ma_phieu "+
                "JOIN sach ON ctpm.ma_sach = sach.ma_sach "+
                "WHERE phieumuon.ma_phieu = ?";
        Cursor cursor = db.rawQuery(query,new String[]{String.valueOf(maPhieu)});
        int tsI = cursor.getColumnIndex("ten_sach");
        int tgI = cursor.getColumnIndex("tac_gia");
        if(cursor!=null&& tsI!=-1&&tgI!=-1&&cursor.moveToFirst()){
            Sach sach = new Sach(cursor.getString(tsI), cursor.getString(tgI));
            return sach;
        }else return null;
    }

    public String getTenLoaiSachByMaPhieu(int maPhieu){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "select loaisach.ten_loai "+
                "from ctpm "+
                "join sach on ctpm.ma_sach = sach.ma_sach "+
                "join loaisach on loaisach.ma_loai = sach.ma_loai "+
                "WHERE ctpm.ma_phieu = ?";
        Cursor cursor = db.rawQuery(query,new String[]{String.valueOf(maPhieu)});
        int tlI = cursor.getColumnIndex("ten_loai");
        if(cursor!=null&& tlI!=-1&&cursor.moveToFirst()){
            String tl = cursor.getString(tlI);
            return tl;
        }else return null;
    }

    public NguoiDung getKhachHangByMaPhieu(int maPhieu){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "select nguoidung.tai_khoan, nguoidung.ho_ten "+
                "from phieumuon "+
                "join nguoidung on nguoidung.tai_khoan = phieumuon.tai_khoan "+
                "WHERE phieumuon.ma_phieu = ?";
        Cursor cursor = db.rawQuery(query,new String[]{String.valueOf(maPhieu)});
        int tkI = cursor.getColumnIndex("tai_khoan");
        int htI = cursor.getColumnIndex("ho_ten");
        if(cursor!=null&& tkI!=-1&&htI!=-1&&cursor.moveToFirst()){
            String tk = cursor.getString(tkI);
            String ht = cursor.getString(htI);
            return new NguoiDung(tk,ht);
        }else return null;
    }
    public Boolean taoPhieuMuon(int maPM,int trangThai,String ngayMuon){
        if(updateTrangthaiPM(maPM,trangThai)&&updateNgayMuon(maPM,ngayMuon)){
            return true;
        }else return false;
    }
    private boolean updateTrangthaiPM(int maPM,int trangThai){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("trang_thai",trangThai);
        long result = db.update("phieumuon",contentValues,"ma_phieu=?",new String[]{String.valueOf(maPM)});
        return result!=-1;
    }
    private boolean updateNgayMuon(int maPM,String ngayMuon){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("ngay_muon",ngayMuon);
        long result = db.update("phieumuon",contentValues,"ma_phieu=?",new String[]{String.valueOf(maPM)});
        return result!=-1;
    }
    private boolean updateNgayTra(int maPM,String ngayTra){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("ngay_tra",ngayTra);
        long result = db.update("phieumuon",contentValues,"ma_phieu=?",new String[]{String.valueOf(maPM)});
        return result!=-1;
    }

    public boolean nguoiDungYeuCauPM(int maSach,String taikhoan) {
        boolean suc33d = false;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues phieuMuonValues = new ContentValues();
            phieuMuonValues.put("tai_khoan", taikhoan);
            phieuMuonValues.put("trang_thai", 1);
            long maPhieu = db.insert("phieumuon", null, phieuMuonValues);
            if (maPhieu != -1) {
                ContentValues ctpmValues = new ContentValues();
                ctpmValues.put("ma_phieu", maPhieu);
                ctpmValues.put("ma_sach", maSach);
                long ctpmResult = db.insert("ctpm", null, ctpmValues);
                if (ctpmResult != -1) {
                    db.setTransactionSuccessful();
                    suc33d = true;
                    Toast.makeText(c, "Đã thêm phiếu chờ", Toast.LENGTH_SHORT).show();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
        return suc33d;
    }

    public Boolean traSach(int maPhieu, String ngayTra){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("trang_thai",3);
        values.put("ngay_tra",ngayTra);
        long result = db.update("phieumuon",values,"ma_phieu=?",new String[]{String.valueOf(maPhieu)});
        return result!=-1;
    }


}
