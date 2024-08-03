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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
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
import com.example.myapplication.adapter.Thuthu_lv_ql_phieu_muon_adapter;
import com.example.myapplication.adapter.Thuthu_lv_ql_phieu_muon_luot_muon_adapter;
import com.example.myapplication.adapter.Thuthu_lv_ql_phieu_muon_trang_thai_adapter;
import com.example.myapplication.classes.NguoiDung;
import com.example.myapplication.classes.PhieuMuon;
import com.example.myapplication.classes.Sach;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class thuthu_activity extends AppCompatActivity {
    Toolbar tb;
    int useCase;
    ListView lv;
    Spinner spinner;
    LinearLayout spn_bg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_thuthu);
        lv = findViewById(R.id.thuthu_lv);
        tb = findViewById(R.id.nguoidung_menu);
        spinner = findViewById(R.id.spn);
        spn_bg = findViewById(R.id.bg_spn);
        setSupportActionBar(tb);
        getSupportActionBar().setTitle("Thủ thư");
        defaultCase();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.thuthu_menu, menu);
        MenuCompat.setGroupDividerEnabled(menu, true);

        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.exit) {
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
        if (id == R.id.signout) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Bạn muốn đăng xuất?");
            builder.setMessage("Bạn có chắc chắn muốn đăng xuất?");
            builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent i = new Intent(thuthu_activity.this, Login.class);
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
        if (id == R.id.changePass) {
            showChangePWAlert();
        }
        if (id == R.id.tkpm) {
            tkpm();
        }
        if(id == R.id.qlpm){
            defaultCase();
        }
        return super.onOptionsItemSelected(item);
    }
    private void tkpm(){
        spn_bg.setVisibility(View.VISIBLE);
        spinner.setVisibility(View.VISIBLE);
        String[] items = new String[]{"Phiếu mượn đã in","Danh sách mượn nhiều nhất"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        useCase =2;
                        setAdapterSpinnerDefault();
                        break;
                    case 1:
                        useCase =3;
                        setAdaptertop5();
                        break;
                    default:
                        useCase =2;
                        setAdapterSpinnerDefault();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }
    private void setAdaptertop5(){
        SachDAO sachDAO = new SachDAO(this);
        ArrayList<Sach> saches = sachDAO.getAllSachByLuotMuon();
        if(saches!=null){
            Thuthu_lv_ql_phieu_muon_luot_muon_adapter adapter = new Thuthu_lv_ql_phieu_muon_luot_muon_adapter(saches,this);
            lv.setAdapter(adapter);
        }else lv.setAdapter(null);
    }
    private void setAdapterSpinnerDefault(){
        Animation animation = AnimationUtils.loadAnimation(thuthu_activity.this,R.anim.item_slide);
        lv.setAnimation(animation);
        PhieuMuonDAO phieuMuonDAO = new PhieuMuonDAO(this);
        ArrayList<PhieuMuon> phieuMuons = phieuMuonDAO.getAllThongTinTrangThaiPM();
        if(phieuMuons!=null){
            Thuthu_lv_ql_phieu_muon_trang_thai_adapter thuthuLvQlPhieuMuonTrangThaiAdapter =new Thuthu_lv_ql_phieu_muon_trang_thai_adapter(phieuMuons,this);
            lv.setAdapter(thuthuLvQlPhieuMuonTrangThaiAdapter);
        }else lv.setAdapter(null);
    }
    private void defaultCase(){
        spn_bg.setVisibility(View.GONE);
        spinner.setVisibility(View.GONE);
        useCase = 1;
        ArrayList<PhieuMuon> phieuMuons;
        Thuthu_lv_ql_phieu_muon_adapter phieuMuonAdapter;
        PhieuMuonDAO phieuMuonDAO = new PhieuMuonDAO(this);
        phieuMuons = phieuMuonDAO.getAllPhieuChuaMuon();
        if (!phieuMuons.isEmpty()) {
            phieuMuonAdapter = new Thuthu_lv_ql_phieu_muon_adapter(phieuMuons, thuthu_activity.this);
            lv.setAdapter(phieuMuonAdapter);

        }else {
            Toast.makeText(this, "null", Toast.LENGTH_SHORT).show();
        }

    }
    private void showChangePWAlert() {
        NguoiDungDAO nguoiDungDAO = new NguoiDungDAO(thuthu_activity.this);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.admin_changpw_alert, null);
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
                NguoiDung nguoiDung = new NguoiDung(tk, mk);
                if (tk.equals("")) {
                    et_tk.requestFocus();
                } else if (mk.equals("")) {
                    et_mk.requestFocus();
                } else if (mk_moi.equals("")) {
                    et_mk_moi.requestFocus();
                } else if (mk_moi.equals(mk)) {
                    Toast.makeText(thuthu_activity.this, "Mật khẩu mới phải khác mật khẩu cũ", Toast.LENGTH_SHORT).show();
                } else if (!nguoiDungDAO.validateUser(tk,mk)) {
                    Toast.makeText(thuthu_activity.this, "Tài khoản hoặc mật khẩu sai", Toast.LENGTH_SHORT).show();
                } else {
                    if (nguoiDungDAO.pwIsChanged(tk,mk_moi)) {
                        Toast.makeText(thuthu_activity.this, "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                        alertDialog.dismiss();
                    } else {
                        Toast.makeText(thuthu_activity.this, "Đổi mật khẩu thất bại", Toast.LENGTH_SHORT).show();

                    }
                }
            }
        });
    }
}