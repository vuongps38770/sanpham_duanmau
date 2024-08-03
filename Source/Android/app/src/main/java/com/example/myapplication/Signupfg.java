package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.DAO.NguoiDungDAO;
import com.example.myapplication.classes.NguoiDung;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Signupfg#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Signupfg extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Signupfg() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginFg.
     */
    // TODO: Rename and change types and number of parameters
    public static Signupfg newInstance(String param1, String param2) {
        Signupfg fragment = new Signupfg();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_signup_fg, container, false);
    }
    NguoiDungDAO nguoiDungDAO;
    TextView et_tentk;
    TextView et_hoten;
    TextView et_mk;
    TextView et_confirm;
    Button btn_submit;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        et_tentk = view.findViewById(R.id.edt_taikhoan);
        et_hoten = view.findViewById(R.id.edt_hoten);
        et_mk = view.findViewById(R.id.edt_matkhau);
        et_confirm = view.findViewById(R.id.edt_xacnhanmatkhau);
        btn_submit = view.findViewById(R.id.btn_login);
        nguoiDungDAO = new NguoiDungDAO(view.getContext());

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(allFilled()){
                    if(et_mk.getText().toString().equals(et_confirm.getText().toString())){
                        NguoiDung nguoiDung = new NguoiDung(et_tentk.getText().toString(),et_mk.getText().toString(),et_hoten.getText().toString(),3);
                        if(nguoiDungDAO.addNguoiDung(nguoiDung)){
                            Toast.makeText(getContext(),"thêm thành công",Toast.LENGTH_SHORT).show();
                        } else if (nguoiDungDAO.CheckIsExistAccount(et_tentk.getText().toString())) {
                            Toast.makeText(getContext(),"Tên tài khoản đã tồn tại",Toast.LENGTH_SHORT).show();

                        } else {

                            Toast.makeText(getContext(),"thêm thất bại",Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(getContext(), "Xác mhận sai mật khẩu", Toast.LENGTH_SHORT).show();
                    }


                }else {
                    Toast.makeText(getContext(), "Vui lòng nhập đủ", Toast.LENGTH_SHORT).show();
                }
            }

            private boolean allFilled() {
                if(et_confirm.getText().toString().equals("")
                        ||et_hoten.getText().toString().equals("")
                        ||et_mk.getText().toString().equals("")
                        ||et_tentk.getText().toString().equals("")){
                    return false;
                }
                else{
                    return true;
                }
            }
        });

    }
}