package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.DAO.NguoiDungDAO;
import com.example.myapplication.classes.NguoiDung;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SigninFg#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SigninFg extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SigninFg() {
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
    public static SigninFg newInstance(String param1, String param2) {
        SigninFg fragment = new SigninFg();
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
        return inflater.inflate(R.layout.fragment_signin_fg, container, false);
    }
    private Button btn_login ;
    TextView tk;
    TextView mk;
    NguoiDungDAO nguoiDungDAO;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        nguoiDungDAO = new NguoiDungDAO(view.getContext());
        btn_login = view.findViewById(R.id.btn_login);
        tk = view.findViewById(R.id.edt_taikhoan);
        mk = view.findViewById(R.id.edt_matkhau);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NguoiDung nguoiDung = new NguoiDung(tk.getText().toString(),mk.getText().toString());
//                Toast.makeText(view.getContext(), tk.getText().toString()+mk.getText().toString(), Toast.LENGTH_SHORT).show();

                if (tk.getText().toString().isEmpty() || mk.getText().toString().isEmpty()) {
                    Toast.makeText(view.getContext(), "Vui lòng nhập tên đăng nhập và mật khẩu", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(nguoiDungDAO.validateUser(tk.getText().toString(),mk.getText().toString())){

                    int role = nguoiDungDAO.getRole(tk.getText().toString());
                    Intent i;
                    switch (role){
                        case 1:
                            Toast.makeText(view.getContext(),"Quản trị viên",Toast.LENGTH_SHORT).show();
                            i = new Intent(view.getContext(),Admin_activity.class);
                            i.putExtra("AD",nguoiDung);
                            startActivity(i);
                            getActivity().finish();
                            break;
                        case 2:
                            i = new Intent(view.getContext(),thuthu_activity.class);
                            startActivity(i);
                            break;
                        case 3:
                            i = new Intent(view.getContext(),Nguoidung_activity.class);
                            i.putExtra("account",nguoiDung.getTaikhoan());
                            startActivity(i);
                            startActivity(i);
                            break;
                        default:
                            Toast.makeText(view.getContext(),"Lỗi đăng nhập",Toast.LENGTH_SHORT).show();
                            break;
                    }
                }else {
                    Toast.makeText(view.getContext(),"sai thông tin",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}