package com.example.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuCompat;

import com.example.myapplication.DAO.LoaiSachDAO;
import com.example.myapplication.DAO.NguoiDungDAO;
import com.example.myapplication.DAO.SachDAO;
import com.example.myapplication.adapter.Admin_ql_nd_adapter;
import com.example.myapplication.adapter.Admin_ql_loai_sach_adapter;
import com.example.myapplication.adapter.Admin_ql_sach_adapter;
import com.example.myapplication.classes.LoaiSach;
import com.example.myapplication.classes.NguoiDung;
import com.example.myapplication.classes.Sach;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class Admin_activity extends AppCompatActivity {
    Toolbar tb;

    ListView lv;

    int checkUsage;
    NguoiDung checkAD;
    Button add;
    TextView alertTV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin);
        Intent ad = getIntent();
        checkAD = (NguoiDung) ad.getSerializableExtra("AD");
        lv = findViewById(R.id.admin_lv);
        add = findViewById(R.id.add);
        tb = findViewById(R.id.admin_menu);
        setSupportActionBar(tb);
        getSupportActionBar().setTitle("Admin");
        alertTV =findViewById(R.id.alertTitle);
        defaultCase();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.admin_menu,menu);
        MenuItem search =menu.findItem(R.id.search);
        MenuCompat.setGroupDividerEnabled(menu,true);
        SearchView searchView = (SearchView) search.getActionView();
        searchView.setQueryHint("Nhập tên tài khoản");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                useSearchMethodByCase(checkUsage,query);

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                useSearchMethodByCase(checkUsage,newText);
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private void useSearchMethodByCase(int checkUsage,String query) {
        if(checkUsage ==1){
            if(checkAD!=null){
                Admin_ql_nd_adapter adminQlNdAdapter = new Admin_ql_nd_adapter(new NguoiDungDAO(Admin_activity.this).getUsersAndThuthu(),Admin_activity.this,checkAD);
                lv.setAdapter(adminQlNdAdapter);
                adminQlNdAdapter.Search(query);
            }else {
                Admin_ql_nd_adapter adminQlNdAdapter = new Admin_ql_nd_adapter(new NguoiDungDAO(Admin_activity.this).getUsersAndThuthu(),Admin_activity.this);
                lv.setAdapter(adminQlNdAdapter);
                adminQlNdAdapter.Search(query);
            }
        }if (checkUsage ==2){
            Admin_ql_sach_adapter adminQlSachAdapter = new Admin_ql_sach_adapter(new SachDAO(Admin_activity.this).getAllSach(),Admin_activity.this);
            lv.setAdapter(adminQlSachAdapter);
            adminQlSachAdapter.Search(query);
        }
        if (checkUsage ==3){
            Admin_ql_loai_sach_adapter adminQlLoaiSachAdapter = new Admin_ql_loai_sach_adapter(new LoaiSachDAO(Admin_activity.this).getAllLoaiSach(),Admin_activity.this);
            lv.setAdapter(adminQlLoaiSachAdapter);
            adminQlLoaiSachAdapter.Search(query);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.exit){
            caseExit();
        }
        if(id== R.id.signout){
            caseSignOut();
        }
        if(id==R.id.qly_sach){
            checkUsage = 2;

            qlySachCase();
        }
        if(id==R.id.qly_loaisach){
            checkUsage = 3;

            caseQlyLoaisach();

        }
        if(id==R.id.qly_nguoidung){
            defaultCase();
        }
        if(id==R.id.changePass){
            showChangePWAlert();
        }
        return super.onOptionsItemSelected(item);
    }

    private void qlySachCase() {
        add.setBackgroundResource(R.drawable.plus_btn_gr);
        SachDAO sachDAO = new SachDAO(this);
        ArrayList<Sach> saches =new ArrayList<>();
        saches = sachDAO.getAllSach();
        Admin_ql_sach_adapter adminQlSachAdapter;
        if(saches.isEmpty()){
            lv.setAdapter(null);
            alertTV.setText("Chưa có sách");
            alertTV.setVisibility(View.VISIBLE);
        }else {
            alertTV.setVisibility(View.GONE);
            adminQlSachAdapter = new Admin_ql_sach_adapter(saches,this);
            lv.setAdapter(adminQlSachAdapter);
        }
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddSachAlert(sachDAO);
            }
        });
    }

    private void defaultCase(){
        checkUsage = 1;
        Admin_ql_nd_adapter adminLvAdapter;
        NguoiDungDAO nguoiDungDAO;
        nguoiDungDAO =new NguoiDungDAO(this);
        ArrayList<NguoiDung> arrayList;
        arrayList=nguoiDungDAO.getUsersAndThuthu();
        if(!arrayList.isEmpty()){
            alertTV.setVisibility(View.GONE);
            add.setBackgroundResource(R.drawable.group_1__1_);
            if(checkAD==null){
                adminLvAdapter = new Admin_ql_nd_adapter(arrayList,this);
                Toast.makeText(this, "devmode", Toast.LENGTH_SHORT).show();
            }else{
                adminLvAdapter = new Admin_ql_nd_adapter(arrayList,this,checkAD);
            }
            lv.setAdapter(adminLvAdapter);
        }else {
            alertTV.setVisibility(View.VISIBLE);
        }

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlert(nguoiDungDAO);
            }
        });
    }
    private void caseExit(){
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
    private void caseSignOut(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Bạn muốn đăng xuất?");
        builder.setMessage("Bạn có chắc chắn muốn đăng xuất?");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent i = new Intent(Admin_activity.this, Login.class);
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
    private void caseQlyLoaisach(){
        LoaiSachDAO loaiSachDAO = new LoaiSachDAO(this);
        ArrayList<LoaiSach> loaiSaches =new ArrayList<>();
        loaiSaches = loaiSachDAO.getAllLoaiSach();
        Admin_ql_loai_sach_adapter adminQlLoaiSachAdapter;
        if(loaiSaches.isEmpty()){
            lv.setAdapter(null);
            alertTV.setText("Chưa có loại sách");
            alertTV.setVisibility(View.VISIBLE);
        }else {
            alertTV.setVisibility(View.GONE);
            adminQlLoaiSachAdapter = new Admin_ql_loai_sach_adapter(loaiSaches,this);
            lv.setAdapter(adminQlLoaiSachAdapter);
        }
        add.setBackgroundResource(R.drawable.plus_btn_gr);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddLoaiSachAlert(loaiSachDAO);
            }
        });
    }

    private void showAddLoaiSachAlert(LoaiSachDAO loaiSachDAO){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.addloaisach_alert,null);
        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.getWindow().setDimAmount(0.8f);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextInputEditText tenSach = view.findViewById(R.id.tensach);
        Button back = view.findViewById(R.id.back);
        Button add = view.findViewById(R.id.them);
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
                if(loaiSachDAO.validateTenLoaiSach(tenSach.getText().toString())){
                    Toast.makeText(Admin_activity.this, "Tên trùng, vui lòng tạo loại sách khác!", Toast.LENGTH_SHORT).show();
                }
                else if(loaiSachDAO.addLoaiSach(tenSach.getText().toString())){
                   ArrayList<LoaiSach> list = loaiSachDAO.getAllLoaiSach();
                   Admin_ql_loai_sach_adapter adapter = new Admin_ql_loai_sach_adapter(list,Admin_activity.this);
                   lv.setAdapter(adapter);
                   Toast.makeText(Admin_activity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                   alertTV.setVisibility(View.GONE);
                   dialog.dismiss();

                } else {
                    Toast.makeText(Admin_activity.this, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();

                }

            }
        });

    }
    private void showAddSachAlert(SachDAO sachDAO) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.addsach_alert,null);
        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.getWindow().setDimAmount(0.8f);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextInputEditText tenSach = view.findViewById(R.id.tensach);
        TextInputEditText tacgia = view.findViewById(R.id.tacgia);
        TextInputEditText giamuon = view.findViewById(R.id.gia);
        Spinner spinner = view.findViewById(R.id.spn_loaisach);
        Button back = view.findViewById(R.id.back);
        Button add = view.findViewById(R.id.them);
        LoaiSachDAO loaiSachDAO = new LoaiSachDAO(Admin_activity.this);
        ArrayList<LoaiSach> listLoaiSach = loaiSachDAO.getAllLoaiSach();
        ArrayList<String> listTenLoaiSach = new ArrayList<>();
        ArrayList<Integer> listMaLoaiSach = new ArrayList<>();
        if(listLoaiSach.isEmpty()){
            spinner.setEnabled(false);
        }else {
            for(LoaiSach item:listLoaiSach){
                listTenLoaiSach.add(item.getTenloai());
                listMaLoaiSach.add(item.getMaloai());
            }
        }
        ArrayAdapter adapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,listTenLoaiSach);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
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
                 if (tenSach.getText().toString().equals("")||giamuon.getText().toString().equals("")||tacgia.getText().toString().equals("")) {
                    Toast.makeText(Admin_activity.this, "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show();

                }
                 else if (!isValidInteger(giamuon.getText().toString())) {
                     Toast.makeText(Admin_activity.this, "Vui lòng nhập đúng giá", Toast.LENGTH_SHORT).show();

                 }
                 else if (!spinner.isEnabled()) {
                     Toast.makeText(Admin_activity.this, "Chưa có loại sách", Toast.LENGTH_SHORT).show();
                 }
                    else if(sachDAO.validateSach(tenSach.getText().toString(),tacgia.getText().toString())){
                    Toast.makeText(Admin_activity.this, "Tên trùng, vui lòng tạo loại sách khác!", Toast.LENGTH_SHORT).show();
                }  else if(sachDAO.addSach(new Sach(tenSach.getText().toString(),tacgia.getText().toString(),Integer.parseInt(giamuon.getText().toString()),listMaLoaiSach.get(spinner.getSelectedItemPosition())))){
                    ArrayList<Sach> list = sachDAO.getAllSach();
                    Admin_ql_sach_adapter adapter = new Admin_ql_sach_adapter(list,Admin_activity.this);
                    lv.setAdapter(adapter);
                    Toast.makeText(Admin_activity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                    alertTV.setVisibility(View.GONE);
                    dialog.dismiss();
                } else {
                    Toast.makeText(Admin_activity.this, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();

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
    private void showChangePWAlert(){
        NguoiDungDAO nguoiDungDAO = new NguoiDungDAO(Admin_activity.this);
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
                   Toast.makeText(Admin_activity.this, "Mật khẩu mới phải khác mật khẩu cũ", Toast.LENGTH_SHORT).show();
               } else if (!nguoiDungDAO.validateUser(tk,mk)) {
                   Toast.makeText(Admin_activity.this, "Tài khoản hoặc mật khẩu sai", Toast.LENGTH_SHORT).show();
               } else {
                   if (nguoiDungDAO.pwIsChanged(tk,mk_moi)){
                       Toast.makeText(Admin_activity.this, "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                       alertDialog.dismiss();
                   }else {
                       Toast.makeText(Admin_activity.this, "Đổi mật khẩu thất bại", Toast.LENGTH_SHORT).show();

                   }
               }
           }
       });

    }
    private void showAlert(NguoiDungDAO nguoiDungDAO){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.admin_add_nguoi_dung_alert,null);
        builder.setView(view);

        AlertDialog dialog = builder.create();
        dialog.getWindow().setDimAmount(0.8f);
        dialog.setCancelable(false);
        Spinner spn = view.findViewById(R.id.spn_role);
        TextInputEditText tk = view.findViewById(R.id.tk);
        TextInputEditText mk = view.findViewById(R.id.mk);
        TextInputEditText ten = view.findViewById(R.id.mk_moi);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ImageButton add = view.findViewById(R.id.them);
        Button back = view.findViewById(R.id.back);
        String[] item = new String[]{"Admin","Thủ thư","Người dùng"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn.setAdapter(adapter);
        spn.setSelection(2);
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
                boolean bl_tk=tk.getText().toString().equals("");
                boolean bl_mk=mk.getText().toString().equals("");
                boolean bl_ten=ten.getText().toString().equals("");
                if(bl_tk||bl_mk||bl_ten){
                    if(bl_tk){
                        tk.requestFocus();
                        tk.setHintTextColor(Color.parseColor("#ff0000"));
                        tk.setHint("Vui lòng nhập tài khoản");
                    } else if (bl_mk) {
                        mk.requestFocus();
                        mk.setHintTextColor(Color.parseColor("#ff0000"));
                        mk.setHint("Vui lòng nhập mật khẩu");
                    } else if (bl_ten) {
                        ten.requestFocus();
                        ten.setHintTextColor(Color.parseColor("#ff0000"));
                        ten.setHint("Vui lòng nhập tên tài khoản");
                    }else{
                    }
                } else{
                    NguoiDung nd= new NguoiDung(tk.getText().toString(),mk.getText().toString(),ten.getText().toString(),spn.getSelectedItemPosition()+1);
                    if(nguoiDungDAO.addNguoiDung(nd)){
                        Toast.makeText(Admin_activity.this, "Đã thêm thành công", Toast.LENGTH_SHORT).show();
                        if(checkAD!=null){

                            Admin_ql_nd_adapter adminQlNdAdapter = new Admin_ql_nd_adapter(new NguoiDungDAO(Admin_activity.this).getUsersAndThuthu(),Admin_activity.this,checkAD);
                            lv.setAdapter(adminQlNdAdapter);
                        }else {
                            Admin_ql_nd_adapter adminQlNdAdapter = new Admin_ql_nd_adapter(new NguoiDungDAO(Admin_activity.this).getUsersAndThuthu(),Admin_activity.this);
                            lv.setAdapter(adminQlNdAdapter);

                        }
                        dialog.dismiss();
                    }else {
                        if(nguoiDungDAO.CheckIsExistAccount(tk.getText().toString())){
                            Toast.makeText(Admin_activity.this, "Tên tài khoản đã tồn tại", Toast.LENGTH_SHORT).show();
                            tk.requestFocus();
                            tk.setTextColor(Color.parseColor("#ff0000"));
                        }
                        Toast.makeText(Admin_activity.this, "Thêm thất bại", Toast.LENGTH_SHORT).show();

                    }

                }



            }
        });
        setBackText(tk);
        setBackText(mk);
        setBackText(ten);
        tk.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tk.setTextColor(Color.parseColor("#FF000000"));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void setBackText(TextInputEditText backText){
        backText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backText.setHintTextColor(Color.parseColor("#FF000000"));
                backText.setHint("");
            }
        });

        backText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    backText.setHintTextColor(Color.parseColor("#FF000000"));
                    backText.setHint("");
                }
            }
        });


    }


}