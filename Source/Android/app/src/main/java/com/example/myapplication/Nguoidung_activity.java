package com.example.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuCompat;

import com.example.myapplication.DAO.NguoiDungDAO;
import com.example.myapplication.DAO.PhieuMuonDAO;
import com.example.myapplication.DAO.SachDAO;
import com.example.myapplication.adapter.Nguoidung_cho_muon_adapter;
import com.example.myapplication.adapter.Nguoidung_da_muon_adapter;
import com.example.myapplication.adapter.Nguoidung_muon_sach_adapter;
import com.example.myapplication.classes.PhieuChoMuon;
import com.example.myapplication.classes.PhieuDaMuon;
import com.example.myapplication.classes.Sach;
import com.google.android.material.textfield.TextInputEditText;
import com.jakewharton.threetenabp.AndroidThreeTen;

import java.util.ArrayList;

public class Nguoidung_activity extends AppCompatActivity {
    Toolbar tb;

    ListView lv;
    int useCase;
    private String account;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        AndroidThreeTen.init(this);
        setContentView(R.layout.activity_nguoidung);
        lv = findViewById(R.id.nguoidung_lv);
        tb = findViewById(R.id.nguoidung_menu);
        Intent i = getIntent();
        account = i.getStringExtra("account");
        setSupportActionBar(tb);


        if(account.isEmpty()){
            Toast.makeText(this, "Dev mode", Toast.LENGTH_SHORT).show();
            getSupportActionBar().setTitle("User: "+"devMode");
        }else {
            NguoiDungDAO nguoiDungDAO = new NguoiDungDAO(this);
            getSupportActionBar().setTitle("User: "+nguoiDungDAO.getTenByTaiKhoan(account));
        }
        defaultCase();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.khachhang_menu,menu);
        MenuItem search =menu.findItem(R.id.search);
        SearchView searchView = (SearchView) search.getActionView();
        MenuCompat.setGroupDividerEnabled(menu, true);
        searchView.setQueryHint("Nhập tên tài khoản");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                findByCase(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                findByCase(newText);
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.exit){
            thoatCase();
        }
        if(id== R.id.signout){
            dangXuatCase();
        }
        if(id == R.id.muonsach){
            defaultCase();
        }
        if(id ==R.id.chomuon){
            useCase = 2;
            chomuonCase();
        }
        if(id == R.id.damuon){
            useCase = 3;
            damuonCase();
        }
        if(id==R.id.changePass){
            showChangePWAlert();
        }
        return super.onOptionsItemSelected(item);
    }


    private void damuonCase() {
        PhieuMuonDAO phieuMuonDAO = new PhieuMuonDAO(this);
        ArrayList<PhieuDaMuon> list;
        if(account.isEmpty()){
             list = phieuMuonDAO.getAllPhieuByManguoidung_DaMuon("v");
        }else {
            list = phieuMuonDAO.getAllPhieuByManguoidung_DaMuon(account);

        }
        if(!list.isEmpty()){
            Nguoidung_da_muon_adapter adapter = new Nguoidung_da_muon_adapter(list, Nguoidung_activity.this);
            lv.setAdapter(adapter);
        }else{
            lv.setAdapter(null);
        }
    }

    private void chomuonCase() {
        PhieuMuonDAO phieumuonDAO = new PhieuMuonDAO(this);
        ArrayList<PhieuChoMuon> list;
        if(account.isEmpty()){
            list =phieumuonDAO.getAllPhieuByManguoidung_ChoMuon("v");
        }else {
            list =phieumuonDAO.getAllPhieuByManguoidung_ChoMuon(account);
        }
        if(!list.isEmpty()){
            Nguoidung_cho_muon_adapter adapter = new Nguoidung_cho_muon_adapter(list, Nguoidung_activity.this);
            lv.setAdapter(adapter);
        }else{
            lv.setAdapter(null);
        }
    }

    private void defaultCase() {
        useCase = 1;
        SachDAO sachDAO = new SachDAO(this);
        ArrayList<Sach> saches = sachDAO.getAllSach();
        if(!saches.isEmpty()){
            Nguoidung_muon_sach_adapter adapter;

            if(account.isEmpty()){
                adapter = new Nguoidung_muon_sach_adapter(saches,this,"v");

            }else {
                adapter = new Nguoidung_muon_sach_adapter(saches,this,account);

            }
            lv.setAdapter(adapter);
        }
    }

    private  void thoatCase(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Xác nhận thoát");
        builder.setMessage("Bạn có chắc chắn muốn thoát ứng dụng?");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finishAffinity();
            }
        });

        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void dangXuatCase(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Bạn muốn đăng xuất?");
        builder.setMessage("Bạn có chắc chắn muốn đăng xuất?");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent i = new Intent(Nguoidung_activity.this, Login.class);
                startActivity(i);
            }
        });

        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    private void showChangePWAlert(){
        NguoiDungDAO nguoiDungDAO = new NguoiDungDAO(Nguoidung_activity.this);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.admin_changpw_alert,null);
        builder.setView(view);

        TextInputEditText et_tk = view.findViewById(R.id.tk);
        TextInputEditText et_mk = view.findViewById(R.id.mk);
        TextInputEditText et_mk_moi = view.findViewById(R.id.mk_moi);



        Button add = view.findViewById(R.id.them);
        Button back = view.findViewById(R.id.back);



        AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.getWindow().setDimAmount(0.8f);
        alertDialog.setCancelable(false);
        alertDialog.show();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tk = et_tk.getText().toString();
                String mk = et_mk.getText().toString();
                String mk_moi = et_mk_moi.getText().toString();
                if (tk.equals("")) {
                    et_tk.requestFocus();
                } else if (mk.equals("")) {
                    et_mk.requestFocus();
                } else if (mk_moi.equals("")) {
                    et_mk_moi.requestFocus();
                } else if (mk_moi.equals(mk)) {
                    Toast.makeText(Nguoidung_activity.this, "Mật khẩu mới phải khác mật khẩu cũ", Toast.LENGTH_SHORT).show();
                } else if (!nguoiDungDAO.validateUser(tk,mk)) {
                    Toast.makeText(Nguoidung_activity.this, "Tài khoản hoặc mật khẩu sai", Toast.LENGTH_SHORT).show();
                } else {
                    if (nguoiDungDAO.pwIsChanged(tk,mk_moi)){
                        Toast.makeText(Nguoidung_activity.this, "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                        alertDialog.dismiss();
                    }else {
                        Toast.makeText(Nguoidung_activity.this, "Đổi mật khẩu thất bại", Toast.LENGTH_SHORT).show();

                    }
                }
            }
        });

    }
    public void findByCase(String query){
        if(useCase==2){
            ArrayList<PhieuChoMuon> list = new PhieuMuonDAO(Nguoidung_activity.this).getAllPhieuByManguoidung_ChoMuon(checkAccount());
            Nguoidung_cho_muon_adapter adapter = new Nguoidung_cho_muon_adapter(list,Nguoidung_activity.this);
            lv.setAdapter(adapter);
            adapter.search(query);
        }
        if(useCase ==1){
            ArrayList<Sach> list = new SachDAO(Nguoidung_activity.this).getAllSach();
            Nguoidung_muon_sach_adapter adapter = new Nguoidung_muon_sach_adapter(list,Nguoidung_activity.this,checkAccount());
            lv.setAdapter(adapter);
            adapter.search(query);
        }
        if(useCase==3){
            ArrayList<PhieuDaMuon> list = new PhieuMuonDAO(Nguoidung_activity.this).getAllPhieuByManguoidung_DaMuon(checkAccount());
            Nguoidung_da_muon_adapter adapter = new Nguoidung_da_muon_adapter(list,Nguoidung_activity.this);
            lv.setAdapter(adapter);
            adapter.search(query);
        }
    }
    private String checkAccount(){
        if(account.isEmpty()) return "v";
        else return account;
    }
}