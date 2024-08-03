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

import com.example.myapplication.DAO.LoaiSachDAO;
import com.example.myapplication.R;
import com.example.myapplication.classes.LoaiSach;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class Admin_ql_loai_sach_adapter extends BaseAdapter {
    private ArrayList<LoaiSach> list;
    private Context context;
    private ArrayList<LoaiSach> ogList;
    private LoaiSachDAO loaiSachDAO;

    public Admin_ql_loai_sach_adapter(ArrayList<LoaiSach> list, Context context) {
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
        View view = LayoutInflater.from(context).inflate(R.layout.item_admin_loaisach,parent,false);
        Animation animation = AnimationUtils.loadAnimation(this.context,R.anim.item_slide);
        view.startAnimation(animation);
        loaiSachDAO = new LoaiSachDAO(context);
        LoaiSach loaiSachInThisItem = list.get(position);
        TextView tenloai = view.findViewById(R.id.theloai);
        TextView soluong = view.findViewById(R.id.soluong);
        Button button = view.findViewById(R.id.xoa);
        String sl4querry = String.valueOf(loaiSachInThisItem.getMaloai());
        int sl = loaiSachDAO.getSoLuong(sl4querry);
        soluong.setText("Số lượng: "+String.valueOf(sl));
        tenloai.setText(list.get(position).getTenloai());
        button.setText("Sửa");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSuaDialog(position);
            }
        });
        return view;
    }

    private void showSuaDialog(int posistion){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.addloaisach_alert,null);
        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.getWindow().setDimAmount(0.8f);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextInputEditText tenSach = view.findViewById(R.id.tensach);
        tenSach.setText(list.get(posistion).getTenloai());
        Button back = view.findViewById(R.id.back);
        Button add = view.findViewById(R.id.them);
        add.setText("Sửa");
        dialog.show();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tenSach.getText().toString().isEmpty()){
                    Toast.makeText(context, "Vui lòng nhập thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!tenSach.getText().toString().equals(list.get(posistion).getTenloai()) && loaiSachDAO.validateTenLoaiSach(tenSach.getText().toString())){
                    Toast.makeText(context, "Tên trùng với loại sách khác, vui lòng nhập tên khác!", Toast.LENGTH_SHORT).show();
                } else if (tenSach.getText().toString().equals(list.get(posistion).getTenloai())) {
                    Toast.makeText(context, "Nhập tên muốn sửa", Toast.LENGTH_SHORT).show();
                } else if(loaiSachDAO.updateLoaiSach(list.get(posistion).getMaloai(),tenSach.getText().toString())){
                    list = loaiSachDAO.getAllLoaiSach();
                    notifyDataSetChanged();
                    Toast.makeText(context, "Sửa thành công", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                } else {
                    Toast.makeText(context, "Sửa thất bại", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            }
        });
    }
    public void Search(String searchInput){
        if(searchInput.isEmpty()) {
            list = new ArrayList<>(ogList);
            notifyDataSetChanged();
        }else {
            ArrayList<LoaiSach> fillterledList = new ArrayList<>();
            for (LoaiSach item : list){
                if(item.getTenloai().toLowerCase().contains(searchInput.toLowerCase())){
                    fillterledList.add(item);
                }
            }
            list = new ArrayList<>(fillterledList);

        }
        notifyDataSetChanged();
    }


}
