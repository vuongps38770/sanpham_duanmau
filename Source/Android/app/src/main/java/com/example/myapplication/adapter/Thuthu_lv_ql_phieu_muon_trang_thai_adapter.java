package com.example.myapplication.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;

import com.example.myapplication.DAO.PhieuMuonDAO;
import com.example.myapplication.R;
import com.example.myapplication.classes.GetToday;
import com.example.myapplication.classes.NguoiDung;
import com.example.myapplication.classes.PhieuMuon;
import com.example.myapplication.classes.Sach;
import com.jakewharton.threetenabp.AndroidThreeTen;

import java.util.ArrayList;

public class Thuthu_lv_ql_phieu_muon_trang_thai_adapter extends BaseAdapter{
    private ArrayList<PhieuMuon> list;
    private Context context;
    private ArrayList<PhieuMuon> ogList;
    PhieuMuonDAO phieuMuonDAO;

    public Thuthu_lv_ql_phieu_muon_trang_thai_adapter(ArrayList<PhieuMuon> list, Context context) {
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
        View view = LayoutInflater.from(context).inflate(R.layout.item_trang_thai_phieu,parent,false);
        Animation animation = AnimationUtils.loadAnimation(this.context,R.anim.item_slide);
        PhieuMuon pMInPos = list.get(position);
        view.startAnimation(animation);
        phieuMuonDAO = new PhieuMuonDAO(context);
        TextView maPhieu = view.findViewById(R.id.maphieu);
        TextView ngay = view.findViewById(R.id.ngay);
        AppCompatButton daTra = view.findViewById(R.id.datra);
        AppCompatButton chuatra = view.findViewById(R.id.chuatra);
        maPhieu.setText("Mã: "+String.valueOf(pMInPos.getMaphieu()));
        if(pMInPos.getTrangthai()==2){
            ngay.setText("Ngày mượn: "+pMInPos.getNgaymuon());
            chuatra.setBackgroundDrawable(ContextCompat.getDrawable(view.getContext(),R.drawable.button_bg_chua_tra));
        }
        if(pMInPos.getTrangthai()==3){
            ngay.setText("Ngày trả: "+pMInPos.getNgaytra());
            daTra.setBackgroundDrawable(ContextCompat.getDrawable(context,R.drawable.button_bg));
        }
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show toast with the maphieu or other relevant information
                Toast.makeText(context, "Mã phiếu: " + pMInPos.getMaphieu(), Toast.LENGTH_SHORT).show();
            }
        });


        return view;
    }


}
