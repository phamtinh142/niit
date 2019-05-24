package com.example.niit.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.niit.entities.LoginManager;
import com.example.niit.activity.ManagerActivity;
import com.example.niit.R;
import com.example.niit.Share.SharePrefer;
import com.example.niit.Share.StringFinal;
import com.example.niit.entities.Manager;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ManageLoginFragment extends Fragment {
    @BindView(R.id.edt_id_manage_login)
    EditText edt_id_login;
    @BindView(R.id.edt_password_manage_login)
    EditText edt_password_login;

    private DatabaseReference databaseReference;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_manage_login, container, false);
        ButterKnife.bind(this, view);
        init();

        return view;
    }

    private void init() {
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    @OnClick(R.id.btn_login_manage_account)
    public void onClickLoginManage() {
        final String id = edt_id_login.getText().toString().trim();
        final String password = edt_password_login.getText().toString().trim();

        if (id.isEmpty()) {
            showToast("Vui lòng nhập ID");
        } else if (password.isEmpty()) {
            showToast("Vui lòng nhập mật khẩu");
        } else {
            databaseReference.child("account").child("manager").addListenerForSingleValueEvent(
                    new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.child(id).exists()) {
                                LoginManager loginManager = dataSnapshot.child(id).getValue(LoginManager.class);
                                if (loginManager.getPassword().equals(password)) {
                                    loginSuccess(id);
                                } else {
                                    showToast("Mật khẩu không đúng");
                                }
                            } else {
                                showToast("Tài khoản không tồn tại");
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
        }

    }

    public void loginSuccess(final String id) {
        Toast.makeText(getActivity(), "Đăng nhập thành công", Toast.LENGTH_SHORT).show();

        databaseReference.child("manager").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Manager manager = dataSnapshot.child(id).getValue(Manager.class);

                SharePrefer.getInstance().put(StringFinal.ADDRESS, manager.getAddress());
                SharePrefer.getInstance().put(StringFinal.AVATAR, manager.getAvatar());
                SharePrefer.getInstance().put(StringFinal.EMAIL, manager.getEmail());
                SharePrefer.getInstance().put(StringFinal.ID, manager.getId());
                SharePrefer.getInstance().put(StringFinal.USERNAME, manager.getName());
                SharePrefer.getInstance().put(StringFinal.PHONE, manager.getPhone());
                SharePrefer.getInstance().put(StringFinal.TYPE, manager.getType());
                SharePrefer.getInstance().put(StringFinal.SEX, manager.getSex());

                Log.d("ktLoginManager", "ADDRESS: " + SharePrefer.getInstance().get(StringFinal.ADDRESS, String.class));
                Log.d("ktLoginManager", "AVATAR: " + SharePrefer.getInstance().get(StringFinal.AVATAR, String.class));
                Log.d("ktLoginManager", "EMAIL: " + SharePrefer.getInstance().get(StringFinal.EMAIL, String.class));
                Log.d("ktLoginManager", "ID: " + SharePrefer.getInstance().get(StringFinal.ID, String.class));
                Log.d("ktLoginManager", "USERNAME: " + SharePrefer.getInstance().get(StringFinal.USERNAME, String.class));
                Log.d("ktLoginManager", "PHONE: " + SharePrefer.getInstance().get(StringFinal.PHONE, String.class));
                Log.d("ktLoginManager", "TYPE: " + SharePrefer.getInstance().get(StringFinal.TYPE, Integer.class));
                Log.d("ktLoginManager", "SEX: " + SharePrefer.getInstance().get(StringFinal.SEX, String.class));


                Toast.makeText(getContext(), "Đăng nhập thành công", Toast.LENGTH_SHORT).show();

                startActivity(new Intent(getActivity(), ManagerActivity.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void showToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }
}
