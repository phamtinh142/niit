package com.example.niit.Login.fragment.ManageLogin;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.niit.Login.fragment.ManageLogin.entities.LoginManager;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class ManageLoginPresenter implements ManageLoginContract.Presenter{
    private ManageLoginContract.View manageLoginView;
    private DatabaseReference databaseReference;

    public ManageLoginPresenter(ManageLoginContract.View manageLoginView, DatabaseReference databaseReference) {
        this.manageLoginView = manageLoginView;
        this.databaseReference = databaseReference;
    }

    @Override
    public void loginAccount(final String id, final String password) {
        if (id.isEmpty()) {
            manageLoginView.showToast("Vui lòng nhập ID");
        } else if (password.isEmpty()) {
            manageLoginView.showToast("Vui lòng nhập mật khẩu");
        } else {
            databaseReference.child("manager").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.child(id).exists()) {
                        LoginManager loginManager = dataSnapshot.child(id).getValue(LoginManager.class);
                        if (loginManager.getPassword().equals(password)) {
                            manageLoginView.showToast("Đăng nhập thành công");
                            manageLoginView.showLoginSuccess();
                        } else {
                            manageLoginView.showToast("Mật khẩu không đúng");
                        }

                    } else {
                        manageLoginView.showToast("Tài khoản không tồn tại");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }
}
