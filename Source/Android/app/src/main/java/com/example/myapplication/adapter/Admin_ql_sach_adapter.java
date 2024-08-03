package com.example.myapplication.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
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

import com.example.myapplication.Admin_activity;
import com.example.myapplication.DAO.LoaiSachDAO;
import com.example.myapplication.DAO.SachDAO;
import com.example.myapplication.R;
import com.example.myapplication.classes.LoaiSach;
import com.example.myapplication.classes.NguoiDung;
import com.example.myapplication.classes.Sach;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Timer;

public class Admin_ql_sach_adapter extends BaseAdapter {
    private ArrayList<Sach> list;
    private Context context;
    private ArrayList<Sach> ogList;
    private SachDAO sachDAO;

    public Admin_ql_sach_adapter(ArrayList<Sach> list, Context context) {
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
        Sach sachInPos = list.get(position);
        Button xoa = view.findViewById(R.id.xoa);
        TextView tenSach = view.findViewById(R.id.tensach);
        TextView tacgia = view.findViewById(R.id.tacgia);
        TextView theloai = view.findViewById(R.id.theloai);
        TextView gia = view.findViewById(R.id.gia);
        tacgia.setText("Tác giả: "+sachInPos.getTacgia());
        theloai.setText("Thể loại: "+sachDAO.getTheLoai(sachInPos.getMaloai()));
        gia.setText("Giá: "+sachInPos.getGiamuon());
        tenSach.setText(sachInPos.getTensach());
        xoa.setText("Sửa");
        xoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showXoaDialog(position);
            }
        });
        return view;
    }

    private void showXoaDialog(int posistion) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        Sach sach =list.get(posistion);
        View view = LayoutInflater.from(context).inflate(R.layout.addsach_alert, null);
        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.getWindow().setDimAmount(0.8f);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextInputEditText tenSach = view.findViewById(R.id.tensach);
        tenSach.setText(sach.getTensach());
        TextInputEditText tacgia = view.findViewById(R.id.tacgia);
        tacgia.setText(sach.getTacgia());
        TextInputEditText giamuon = view.findViewById(R.id.gia);
        giamuon.setText(String.valueOf(sach.getGiamuon()));
        Spinner spinner = view.findViewById(R.id.spn_loaisach);
        Button back = view.findViewById(R.id.back);
        Button add = view.findViewById(R.id.them);
        LoaiSachDAO loaiSachDAO = new LoaiSachDAO(context);
        ArrayList<LoaiSach> listLoaiSach = loaiSachDAO.getAllLoaiSach();
        ArrayList<String> listTenLoaiSach = new ArrayList<>();
        ArrayList<Integer> listMaLoaiSach = new ArrayList<>();
        if (listLoaiSach.isEmpty()) {
            spinner.setEnabled(false);
        } else {
            for (LoaiSach item : listLoaiSach) {
                listTenLoaiSach.add(item.getTenloai());
                listMaLoaiSach.add(item.getMaloai());
            }
        }
        ArrayAdapter adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, listTenLoaiSach);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setSelection(listMaLoaiSach.indexOf(list.get(posistion).getMaloai()));
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
                if (tenSach.getText().toString().equals("") || giamuon.getText().toString().equals("") || tacgia.getText().toString().equals("")) {
                    Toast.makeText(context, "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                } else if (!isValidInteger(giamuon.getText().toString())) {
                    Toast.makeText(context, "Vui lòng nhập đúng giá", Toast.LENGTH_SHORT).show();
                } else if (!spinner.isEnabled()) {
                    Toast.makeText(context, "Chưa có loại sách", Toast.LENGTH_SHORT).show();
                } else if (sachDAO.validateSach(tenSach.getText().toString(), tacgia.getText().toString()) && !tenSach.getText().toString().equals(list.get(posistion).getTensach())) {
                    Toast.makeText(context, "Tên trùng, vui lòng tạo loại sách khác!", Toast.LENGTH_SHORT).show();
                } else {
                    int maloai = listMaLoaiSach.get(spinner.getSelectedItemPosition());
                    String tensach = tenSach.getText().toString();
                    String tg = tacgia.getText().toString();
                    int gia = Integer.parseInt(giamuon.getText().toString());
                    if (sachDAO.updateSach(maloai,tensach,tg,gia,list.get(posistion).getMasach())) {
                        Toast.makeText(context, "Sửa thành công", Toast.LENGTH_SHORT).show();
                        Log.d("maloai", String.valueOf(maloai));
                        Log.d("tensach", tensach);
                        Log.d("tg", tg);
                        Log.d("gia", String.valueOf(gia));
                        list = sachDAO.getAllSach();
                        notifyDataSetChanged();
                        dialog.dismiss();
                    }else {
                        Toast.makeText(context, "Sửa thất bại", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                }

            }
        });
    }
        private boolean isValidInteger(String str) {
            if (str == null) {
                return false;
            }
            try {
                int value = Integer.parseInt(str);
                if (value < 0) {
                    return false;
                }
            } catch (NumberFormatException e) {
                return false;
            }
            return true;
        }

    public void Search(String searchInput){
        if(searchInput.isEmpty()) {
            list = new ArrayList<>(ogList);
            notifyDataSetChanged();
        }else {
            ArrayList<Sach> fillterledList = new ArrayList<>();
            for (Sach item : list){
                if(item.getTensach().toLowerCase().contains(searchInput.toLowerCase())){
                    fillterledList.add(item);
                }
            }
            list = new ArrayList<>(fillterledList);

        }
        notifyDataSetChanged();
    }
}
