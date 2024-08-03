package com.example.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.myapplication.DAO.PhieuMuonDAO;
import com.example.myapplication.R;
import com.example.myapplication.classes.PhieuMuon;
import com.example.myapplication.classes.Sach;
import com.jakewharton.threetenabp.AndroidThreeTen;

import java.util.ArrayList;

public class Thuthu_lv_ql_phieu_muon_luot_muon_adapter extends BaseAdapter{
    private ArrayList<Sach> list;
    private Context context;
    private ArrayList<Sach> ogList;
    public Thuthu_lv_ql_phieu_muon_luot_muon_adapter(ArrayList<Sach> list, Context context) {
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
        View view = LayoutInflater.from(context).inflate(R.layout.item_thuthu_sach,parent,false);
        Animation animation = AnimationUtils.loadAnimation(this.context,R.anim.item_slide);
        Sach sachInPos = list.get(position);
        view.startAnimation(animation);
        TextView tensach = view.findViewById(R.id.tensach);
        TextView tacgia = view.findViewById(R.id.tacgia);
        TextView luotmuon = view.findViewById(R.id.luotmuon);
        tensach.setText(sachInPos.getTensach());
        tacgia.setText("Tác giả: "+sachInPos.getTacgia());
        luotmuon.setText("Lượt mượn: "+String.valueOf(sachInPos.getLuotmuon()));
        return view;
    }


}
