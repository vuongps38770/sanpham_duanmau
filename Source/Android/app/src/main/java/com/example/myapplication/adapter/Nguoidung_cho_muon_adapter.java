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
import android.widget.Toast;

import com.example.myapplication.DAO.PhieuMuonDAO;
import com.example.myapplication.DAO.SachDAO;
import com.example.myapplication.R;
import com.example.myapplication.classes.PhieuChoMuon;
import com.example.myapplication.classes.PhieuMuon;
import com.example.myapplication.classes.Sach;

import java.util.ArrayList;

public class Nguoidung_cho_muon_adapter extends BaseAdapter{
    private ArrayList<PhieuChoMuon> list;
    private Context context;
    private ArrayList<PhieuChoMuon> ogList;
    private SachDAO sachDAO;
    public Nguoidung_cho_muon_adapter(ArrayList<PhieuChoMuon> list, Context context) {
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
        View view = LayoutInflater.from(context).inflate(R.layout.item_admin_sach,parent,false);
        Animation animation = AnimationUtils.loadAnimation(this.context,R.anim.item_slide);
        view.startAnimation(animation);
        sachDAO = new SachDAO(context);
        PhieuChoMuon PhieuInPos = list.get(position);
        Button muon = view.findViewById(R.id.xoa);
        TextView tenSach = view.findViewById(R.id.tensach);
        TextView tacgia = view.findViewById(R.id.tacgia);
        TextView theloai = view.findViewById(R.id.theloai);
        TextView maPhieu = view.findViewById(R.id.gia);
        tacgia.setText("Tác giả: "+PhieuInPos.getTacgia());
        theloai.setText("Thể loại: "+PhieuInPos.getTheloai());
        maPhieu.setText("Mã phiếu: "+PhieuInPos.getMaphieu());
        tenSach.setText(PhieuInPos.getTensach());
        muon.setText("Đang chờ");

        return view;

    }
    public void search(String query){
        if(query.isEmpty()){
            list = new ArrayList<>(ogList);
            notifyDataSetChanged();
        }else{
            ArrayList<PhieuChoMuon> phieu = new ArrayList<>();
            for (PhieuChoMuon item : list){
                if (item.getTensach().toLowerCase().contains(query)) {
                    phieu.add(item);
                }
            }
            list=new ArrayList<>(phieu);
        }
        notifyDataSetChanged();
    }

}
