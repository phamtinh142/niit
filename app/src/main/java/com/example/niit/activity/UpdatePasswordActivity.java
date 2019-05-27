package com.example.niit.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.niit.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UpdatePasswordActivity extends AppCompatActivity {
    @BindView(R.id.edt_old_password)
    EditText edt_old_password;
    @BindView(R.id.edt_new_password)
    EditText edt_new_password;
    @BindView(R.id.edt_confirm_new_password)
    EditText edt_confirm_new_password;

    DatabaseReference databaseReference;

    String classUser;
    String idUser;
    int typeAccount;
    String option;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);
        getDataBundle();
        ButterKnife.bind(this);

        if (option.equals("local")) {
            edt_old_password.setVisibility(View.VISIBLE);
        } else if (option.equals("admin")){
            edt_old_password.setVisibility(View.GONE);
        }

        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    private void getDataBundle() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            classUser = bundle.getString("classUser", "");
            idUser = bundle.getString("idUser", "");
            typeAccount = bundle.getInt("typeAccount", 0);
            option = bundle.getString("option", "");
        }
    }

    @OnClick(R.id.ibtn_back)
    public void onClickBack() {
        finish();
    }

    @OnClick(R.id.btn_update_password)
    public void onClickChangePassword() {
        final String oldPassword = edt_old_password.getText().toString().trim();
        final String newPassword = edt_new_password.getText().toString().trim();
        String confirmNewPassword = edt_confirm_new_password.getText().toString().trim();

        if (option.equals("local")) {
            if (oldPassword.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập mật khẩu cũ !", Toast.LENGTH_SHORT).show();
                edt_old_password.requestFocus();
            } else if (newPassword.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập mật khẩu mới !", Toast.LENGTH_SHORT).show();
                edt_new_password.requestFocus();
            } else if (confirmNewPassword.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập lại mật khẩu !", Toast.LENGTH_SHORT).show();
                edt_confirm_new_password.requestFocus();
            } else if (!newPassword.equals(confirmNewPassword)){
                Toast.makeText(this, "Mật khẩu không trùng khớp !", Toast.LENGTH_SHORT).show();
                edt_confirm_new_password.requestFocus();
            } else {
                if (typeAccount == 0) {
                    databaseReference.child("account").child("manager").child(idUser).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String password = dataSnapshot.child("password").getValue(String.class);
                            if (oldPassword.equals(password)) {
                                changePassword(newPassword);
                            } else {
                                Toast.makeText(UpdatePasswordActivity.this, "Mật khẩu cũ không đúng !", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                } else if (typeAccount == 1) {
                    databaseReference.child("account").child(classUser).child(idUser).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String password = dataSnapshot.child("password").getValue(String.class);
                            if (oldPassword.equals(password)) {
                                changePassword(newPassword);
                            } else {
                                Toast.makeText(UpdatePasswordActivity.this, "Mật khẩu cũ không đúng !", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }
        } else if (option.equals("admin")) {
            if (newPassword.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập mật khẩu mới !", Toast.LENGTH_SHORT).show();
                edt_new_password.requestFocus();
            } else if (confirmNewPassword.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập lại mật khẩu !", Toast.LENGTH_SHORT).show();
                edt_confirm_new_password.requestFocus();
            } else if (!newPassword.equals(confirmNewPassword)){
                Toast.makeText(this, "Mật khẩu không trùng khớp !", Toast.LENGTH_SHORT).show();
                edt_confirm_new_password.requestFocus();
            } else {
                changePassword(newPassword);
            }
        }



    }

    private void changePassword(String newPassword) {
        if (typeAccount == 0) {
            databaseReference.child("account").child("manager").child(idUser).child("password").setValue(newPassword, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                    if (databaseError == null) {
                        Toast.makeText(UpdatePasswordActivity.this, "Đổi mật khẩu thành công !", Toast.LENGTH_SHORT).show();
                        edt_old_password.setText("");
                        edt_new_password.setText("");
                        edt_confirm_new_password.setText("");
                    } else {
                        Toast.makeText(UpdatePasswordActivity.this, "Đổi mật khẩu thất bại !", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else if (typeAccount == 1){
            databaseReference.child("account").child(classUser).child(idUser).child("password").setValue(newPassword, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                    if (databaseError == null) {
                        Toast.makeText(UpdatePasswordActivity.this, "Đổi mật khẩu thành công !", Toast.LENGTH_SHORT).show();
                        edt_old_password.setText("");
                        edt_new_password.setText("");
                        edt_confirm_new_password.setText("");
                    } else {
                        Toast.makeText(UpdatePasswordActivity.this, "Đổi mật khẩu thất bại !", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
