package com.example.myapplication.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.example.myapplication.DAO.NguoiDungDAO;
import com.example.myapplication.DAO.PhieuMuonDAO;
import com.example.myapplication.R;
import com.example.myapplication.classes.GetToday;
import com.example.myapplication.classes.LoaiSach;
import com.example.myapplication.classes.NguoiDung;
import com.example.myapplication.classes.PhieuMuon;
import com.example.myapplication.classes.Sach;
import com.jakewharton.threetenabp.AndroidThreeTen;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Thuthu_lv_ql_phieu_muon_adapter extends BaseAdapter{
    private ArrayList<PhieuMuon> list;
    private Context context;
    private ArrayList<PhieuMuon> ogList;
    PhieuMuonDAO phieuMuonDAO;

    public Thuthu_lv_ql_phieu_muon_adapter(ArrayList<PhieuMuon> list, Context context) {
        this.list = list;
        this.context = context;
        this.ogList = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AndroidThreeTen.init(context);
        View view = LayoutInflater.from(context).inflate(R.layout.item_thuthu,parent,false);
        Animation animation = AnimationUtils.loadAnimation(this.context,R.anim.item_slide);
        PhieuMuon pMInPos = list.get(position);
        view.startAnimation(animation);
        phieuMuonDAO = new PhieuMuonDAO(context);
        TextView tensach = view.findViewById(R.id.tensach);
        TextView tacGia = view.findViewById(R.id.tacgia);
        TextView theLoai = view.findViewById(R.id.theloai);
        TextView nguoiMuon = view.findViewById(R.id.nguoimuon);
        Button them = view.findViewById(R.id.btn_taoPhieu);

        Sach sach = phieuMuonDAO.getSachBYMaPM_tg_ts(pMInPos.getMaphieu());
        String loaiSach = phieuMuonDAO.getTenLoaiSachByMaPhieu(pMInPos.getMaphieu());
        NguoiDung nguoiDung = phieuMuonDAO.getKhachHangByMaPhieu(pMInPos.getMaphieu());
        if(nguoiDung!=null){
            nguoiMuon.setText("Người dùng: "+nguoiDung.getHoten());
        }
        if(sach!=null){
            tensach.setText(sach.getTensach());
            tacGia.setText("Tác giả: "+sach.getTacgia());

        }
        if(loaiSach!=null){
            theLoai.setText("Thể loại: "+loaiSach);

        }
        them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(position);
            }
        });


        return view;
    }
    private void showDialog(int posistion){
        //tạo alertdialog
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.thuthu_taophieu_alert,null);
        builder.setView(view);
        AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.getWindow().setDimAmount(0.8f);
        alertDialog.setCancelable(false);
        //ánh xạ
        TextView tensach = view.findViewById(R.id.tensach);
        TextView tacgia = view.findViewById(R.id.tacgia);
        TextView theloai = view.findViewById(R.id.theloai);
        TextView ngaymuon = view.findViewById(R.id.ngayxb);
        PhieuMuon phieuMuon = list.get(posistion);
        TextView nd = view.findViewById(R.id.noidung);
        Button in = view.findViewById(R.id.taopm);
        Button back = view.findViewById(R.id.back);
        Sach sach = phieuMuonDAO.getSachBYMaPM_tg_ts(phieuMuon.getMaphieu());
        NguoiDung nguoiDung = phieuMuonDAO.getKhachHangByMaPhieu(phieuMuon.getMaphieu());
        String loaiSach =phieuMuonDAO.getTenLoaiSachByMaPhieu(phieuMuon.getMaphieu());
        if(sach!=null&&nguoiDung!=null&& loaiSach!=null){
            tensach.setText(sach.getTensach());
            tacgia.setText("Tác giả: "+sach.getTacgia());
            theloai.setText("Thể loại: "+loaiSach);

            ngaymuon.setText("Ngày mượn: "+new GetToday().getToday());
            nd.setText("Tên khách hàng: "+ nguoiDung.getHoten()+"\n"+
                    "Tài khoản: "+nguoiDung.getTaikhoan()+"\n"+
                    "Ngày mượn: "+new GetToday().getToday());
        }

        alertDialog.show();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(phieuMuonDAO.taoPhieuMuon(list.get(posistion).getMaphieu(),2,new GetToday().getToday())){
                    Toast.makeText(context, "Đã duyệt thành công", Toast.LENGTH_SHORT).show();
                    list = phieuMuonDAO.getAllPhieuChuaMuon();
                    notifyDataSetChanged();
                    alertDialog.dismiss();
                }else {
                    Toast.makeText(context, "Lỗi khi duyệt", Toast.LENGTH_SHORT).show();
                    alertDialog.dismiss();
                }
            }
        });

    }


}
