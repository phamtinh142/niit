package com.example.niit.Login.fragment.ManageLogin;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.niit.Manager.activity.ManagerActivity;
import com.example.niit.R;
import com.example.niit.Share.SharePrefer;
import com.example.niit.Share.StringFinal;
import com.example.niit.model.Manager;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ManageLoginFragment extends Fragment implements ManageLoginContract.View {
    @BindView(R.id.edt_id_manage_login)
    EditText edt_id_login;
    @BindView(R.id.edt_password_manage_login)
    EditText edt_password_login;

    private DatabaseReference databaseReference;
    private ManageLoginPresenter manageLoginPresenter;

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
        manageLoginPresenter = new ManageLoginPresenter(this, databaseReference);
    }

    @OnClick(R.id.btn_login_manage_account)
    public void onClickLoginManage() {
        String id = edt_id_login.getText().toString().trim();
        String password = edt_password_login.getText().toString().trim();

        manageLoginPresenter.loginAccount(id, password);

    }

    @Override
    public void showLoginSuccess(final String id) {
        databaseReference.child("Manage").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Manager manager = dataSnapshot.child(id).getValue(Manager.class);

                SharePrefer.getInstance().put(StringFinal.ADDRESS, manager.getAddress());
                SharePrefer.getInstance().put(StringFinal.IMAGE, manager.getAvata());
                SharePrefer.getInstance().put(StringFinal.EMAIL, manager.getEmail());
                SharePrefer.getInstance().put(StringFinal.ID, manager.getId());
                SharePrefer.getInstance().put(StringFinal.USER_NAME, manager.getName());
                SharePrefer.getInstance().put(StringFinal.PHONE, manager.getPhone());
                SharePrefer.getInstance().put(StringFinal.TYPE, manager.getType_account());

                Toast.makeText(getContext(), "Đăng nhập thành công", Toast.LENGTH_SHORT).show();

                startActivity(new Intent(getActivity(), ManagerActivity.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }
}
