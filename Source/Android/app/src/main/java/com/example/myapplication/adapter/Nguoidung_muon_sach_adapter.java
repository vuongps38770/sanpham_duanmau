package com.example.myapplication.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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

import com.example.myapplication.DAO.PhieuMuonDAO;
import com.example.myapplication.DAO.SachDAO;
import com.example.myapplication.R;
import com.example.myapplication.classes.GetToday;
import com.example.myapplication.classes.NguoiDung;
import com.example.myapplication.classes.PhieuChoMuon;
import com.example.myapplication.classes.PhieuMuon;
import com.example.myapplication.classes.Sach;
import com.jakewharton.threetenabp.AndroidThreeTen;

import java.util.ArrayList;

public class Nguoidung_muon_sach_adapter extends BaseAdapter{
    private ArrayList<Sach> list;
    private Context context;
    private ArrayList<Sach> ogList;
    private SachDAO sachDAO;
    private String account;
    public Nguoidung_muon_sach_adapter(ArrayList<Sach> list, Context context, String account) {
        this.list = list;
        this.context = context;
        this.ogList = list;
        this.account = account;
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
        Sach sachInPos = list.get(position);
        Button muon = view.findViewById(R.id.xoa);
        TextView tenSach = view.findViewById(R.id.tensach);
        TextView tacgia = view.findViewById(R.id.tacgia);
        TextView theloai = view.findViewById(R.id.theloai);
        TextView gia = view.findViewById(R.id.gia);
        tacgia.setText("Tác giả: "+sachInPos.getTacgia());
        theloai.setText("Thể loại: "+sachDAO.getTheLoai(sachInPos.getMaloai()));
        gia.setText("Giá: "+sachInPos.getGiamuon()+"/ngày");
        tenSach.setText(sachInPos.getTensach());
        muon.setText("Mượn ngay");
        muon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showConfirm(position);
            }
        });
        return view;

    }


    private void showConfirm(int pos){
        PhieuMuonDAO phieuMuonDAO = new PhieuMuonDAO(context);
        if(phieuMuonDAO.nguoiDungYeuCauPM(list.get(pos).getMasach(),account)){
            Toast.makeText(context, "Đã thêm vào phiếu chờ", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Lỗi khi thêm sách vui lòng thử lại sau", Toast.LENGTH_SHORT).show();

        }
    }
    public void search(String querry){
        if(querry.isEmpty()){
            list=ogList;
            notifyDataSetChanged();
        }else {
            ArrayList<Sach> filterlledList = new ArrayList<>();
            for (Sach item:ogList){
                if(item.getTensach().toLowerCase().contains(querry.toLowerCase())){
                    filterlledList.add(item);
                }
            }
            list = new ArrayList<>(filterlledList);
            notifyDataSetChanged();

        }
    }

}
