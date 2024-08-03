package com.example.myapplication.adapter;

import android.content.Context;
import android.content.DialogInterface;
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
import com.example.myapplication.R;
import com.example.myapplication.classes.NguoiDung;

import java.util.ArrayList;

public class Admin_ql_nd_adapter extends BaseAdapter{
    private ArrayList<NguoiDung> list;
    private Context context;
    private ArrayList<NguoiDung> ogList;
    private NguoiDungDAO nguoiDungDAO;
    private NguoiDung ADCheck;
    public Admin_ql_nd_adapter(ArrayList<NguoiDung> list, Context context, NguoiDung AdCheck) {
        this.list = list;
        this.context = context;
        this.ogList = list;
        this.ADCheck = AdCheck;
    }

    public Admin_ql_nd_adapter(ArrayList<NguoiDung> list, Context context){
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
        View view = LayoutInflater.from(context).inflate(R.layout.item_admin,parent,false);
        Animation animation = AnimationUtils.loadAnimation(this.context,R.anim.item_slide);
        view.startAnimation(animation);
        TextView role = view.findViewById(R.id.role);
        TextView name = view.findViewById(R.id.name);
        TextView tk = view.findViewById(R.id.tk);
        Button capQuyen=view.findViewById(R.id.capquyen);
        Button xoa=view.findViewById(R.id.xoa);
        role.setText(roleToString(list.get(position).getRole()));
        name.setText("Tên: "+list.get(position).getHoten());
        tk.setText("Tk: "+list.get(position).getTaikhoan());
        nguoiDungDAO = new NguoiDungDAO(context);
        if(ADCheck!=null){
            if(ADCheck.getTaikhoan().equals(list.get(position).getTaikhoan())){
                capQuyen.setBackgroundColor(Color.parseColor("#FF0000"));
                xoa.setBackgroundColor(Color.parseColor("#FF0000"));
                capQuyen.setEnabled(false);
                xoa.setEnabled(false);
            }
        }
        xoa.setVisibility(View.GONE);
//            xoa.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
//                    builder.setTitle("Xoá người dùng");
//                    builder.setMessage("Bạn có chắc chắn muốn xoá người dùng "+"\""+ list.get(position).getTaikhoan()+"\"");
//                    builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.dismiss();
//                        }
//                    });
//                    builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            NguoiDung nguoiDung = list.get(position);
//                            if(nguoiDungDAO.xoa(nguoiDung)){
//                                list.remove(position);
//                                notifyDataSetChanged();
//                                Toast.makeText(context, "Đã xoá thành công", Toast.LENGTH_SHORT).show();
//                            }else {
//                                Toast.makeText(context, "Lỗi! Xoá không thành công.", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    });
//                    builder.show();
//
//                }
//            });

            capQuyen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDialog(list.get(position),nguoiDungDAO);
                }
            });


        return view;


    }
    private String roleToString(int role){
        if(role==1){
            return "Admin";
        }if(role==2){
            return "Thủ thư";
        }
        if(role==3){
            return "Người dùng";
        }
        return "null";
    }
    public void Search(String searchInput){
        if(searchInput.isEmpty()) {
            list = new ArrayList<>(ogList);
            notifyDataSetChanged();
        }else {
            ArrayList<NguoiDung> fillterledList = new ArrayList<>();
            for (NguoiDung item : list){
                if(item.getTaikhoan().toLowerCase().contains(searchInput.toLowerCase())){
                    fillterledList.add(item);
                }
            }
            list = new ArrayList<>(fillterledList);

        }
        notifyDataSetChanged();
    }

    private void showDialog(NguoiDung nguoiDung, NguoiDungDAO nguoiDungDAO){
        //tạo alertdialog
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.admin_setrole_dialog,null);
        builder.setView(view);
        AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.getWindow().setDimAmount(0.8f);
        alertDialog.setCancelable(false);
        //ánh xạ
        TextView name = view.findViewById(R.id.name);
        TextView tk = view.findViewById(R.id.tk);
        Spinner spinner = view.findViewById(R.id.spn_role);
        Button back = view.findViewById(R.id.back);
        Button confirm = view.findViewById(R.id.confirm);
        //set tt
        name.setText("Người dùng: "+nguoiDung.getHoten());
        tk.setText("Tên tài khoản: "+nguoiDung.getTaikhoan());
        //thêm adapter cho spinner
        String[] item = new String[]{"Admin","Thủ thư","Người dùng"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_spinner_item, item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setSelection(nguoiDung.getRole()-1);
        alertDialog.show();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nguoiDung.setRole(stringToRole(spinner.getSelectedItem().toString()));
                nguoiDungDAO.setRole(nguoiDung);
                alertDialog.dismiss();
                notifyDataSetChanged();
            }
        });
    }
    private int stringToRole(String string){
        if(string.equals("Admin"))
            return 1;
        if(string.equals("Thủ thư"))
            return 2;
        if(string.equals("Người dùng"))
            return 3;
        return -1;
    }
}
