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
import com.example.myapplication.classes.PhieuChoMuon;
import com.example.myapplication.classes.PhieuDaMuon;
import com.jakewharton.threetenabp.AndroidThreeTen;

import java.util.ArrayList;

public class Nguoidung_da_muon_adapter extends BaseAdapter{
    private ArrayList<PhieuDaMuon> list;
    private Context context;
    private ArrayList<PhieuDaMuon> ogList;
    private PhieuMuonDAO phieuDAO;
    public Nguoidung_da_muon_adapter(ArrayList<PhieuDaMuon> list, Context context) {
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
        View view = LayoutInflater.from(context).inflate(R.layout.item_nguoidung_trasach,parent,false);
        Animation animation = AnimationUtils.loadAnimation(this.context,R.anim.item_slide);
        view.startAnimation(animation);
        phieuDAO = new PhieuMuonDAO(context);
        PhieuDaMuon PhieuInPos = list.get(position);
        Button muon = view.findViewById(R.id.xoa);
        TextView tenSach = view.findViewById(R.id.tensach);
        TextView ngayMuon = view.findViewById(R.id.ngaymuon);
        TextView tacgia = view.findViewById(R.id.tacgia);
        tacgia.setText("Tác giả: "+PhieuInPos.getTacgia());
//        tacgia.setText("Tác giả: "+PhieuInPos.getTacgia());
//        theloai.setText("Thể loại: "+PhieuInPos.getTheloai());

        ngayMuon.setText("Ngày mượn: "+PhieuInPos.getNgaymuon());
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                traPhieuAlert(position);
            }
        });
        tenSach.setText(PhieuInPos.getTensach());
        muon.setText("Trả sách");

        muon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(phieuDAO.traSach(list.get(position).getMaphieu(),new GetToday().getToday())){
                    Toast.makeText(context, "Trả sách thành công", Toast.LENGTH_SHORT).show();
                    list.remove(position);
                    notifyDataSetChanged();
                }else{
                    Toast.makeText(context, "Trả sách không thành công", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;

    }
    private void traPhieuAlert(int posistion){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_nguoidung_tt_phieudamuon,null);
        builder.setView(view);


        TextView tvMa = view.findViewById(R.id.tvMaphieu);
        TextView tvTen = view.findViewById(R.id.tvTensach);
        TextView tvTacgia = view.findViewById(R.id.tvTacgia);
        TextView tvTheloai = view.findViewById(R.id.tvTheloai);
        TextView tvNgaymuon = view.findViewById(R.id.tvNgaymuon);
        TextView tvNgayDamuon = view.findViewById(R.id.tvNgayDamuon);


        PhieuDaMuon phieu = list.get(posistion);
        tvMa.setText("MP: P"+String.valueOf(phieu.getMaphieu()));
        tvTen.setText(phieu.getTensach());
        tvTacgia.setText("Tác giả: "+phieu.getTacgia());
        tvTheloai.setText("Thể loại: "+phieu.getTheloai());
        tvNgaymuon.setText("Ngày mượn: "+phieu.getNgaymuon());
        GetToday today = new GetToday();
        tvNgayDamuon.setText("Số ngày đã mượn: "+String.valueOf(today.calculateDaysBetween(phieu.getNgaymuon(),today.getToday()))+" ngày");

        AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setDimAmount(0.8f);
        dialog.show();
    }
    public void search(String querry){
        if(querry.isEmpty()){
            list=new ArrayList<>(ogList);
        }else {
            ArrayList<PhieuDaMuon> fillteredList= new ArrayList<>();
            for (PhieuDaMuon item:ogList){
                if(item.getTensach().toLowerCase().contains(querry.toLowerCase())){
                    fillteredList.add(item);
                }
            }
            list=new ArrayList<>(fillteredList);
            notifyDataSetChanged();
        }
    }
}
