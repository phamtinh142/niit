package com.example.niit.Login.fragment.StudentLogin;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.niit.Login.LoginActivity;
import com.example.niit.Login.entities.Login;
import com.example.niit.R;
import com.example.niit.Share.SharePrefer;
import com.example.niit.Share.StringFinal;
import com.example.niit.Student.InfoUser.User;
import com.example.niit.Student.activity.MainStudent.MainActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StudentLoginFragment extends Fragment {

    @BindView(R.id.edt_id_student_login)
    EditText edt_id_login;
    @BindView(R.id.edt_password_student_login)
    EditText edt_password_login;
    @BindView(R.id.edt_class_student_login)
    EditText edt_classlogin;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_student_login, container, false);
        ButterKnife.bind(this, view);
        init();

        return view;
    }

    private void init() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    @OnClick(R.id.btn_login_student_account)
    public void onClickLoginAccount() {
        String id = edt_id_login.getText().toString().trim();
        String passWord  = edt_password_login.getText().toString().trim();
        String classStudent = edt_classlogin.getText().toString().trim();

        if (id.isEmpty()) {
            Toast.makeText(getActivity(), "Vui lòng nhập ID", Toast.LENGTH_SHORT).show();
            edt_id_login.requestFocus();
        } else if (passWord.isEmpty()) {
            Toast.makeText(getActivity(), "Vui lòng nhập mật khẩu", Toast.LENGTH_SHORT).show();
            edt_password_login.requestFocus();
        } else {
            login(id, passWord);
        }


    }

    private void login(final String id, final String password) {
        databaseReference.child("Account").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(id).exists()) {

                    Login login = dataSnapshot.child(id).getValue(Login.class);

                    if (login.getPassword().equals(password)) {
                        getInforUser(id);
                    } else {
                        Toast.makeText(getActivity(), "Mật khẩu không đúng", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "Tài khoản không tồn tại", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void getInforUser(final String id) {

        databaseReference.child("Student").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(id).exists()) {
                    SharePrefer.getInstance().put(StringFinal.ID, id);

                    User user = dataSnapshot.child(id).getValue(User.class);

                    SharePrefer.getInstance().put(StringFinal.ADDRESS, user.getAddress());
                    SharePrefer.getInstance().put(StringFinal.USER_NAME, user.getName());
                    SharePrefer.getInstance().put(StringFinal.AGE, user.getAge());
                    SharePrefer.getInstance().put(StringFinal.EMAIL, user.getEmail());
                    SharePrefer.getInstance().put(StringFinal.IMAGE, user.getImage());
                    SharePrefer.getInstance().put(StringFinal.PHONE, user.getPhone());
                    SharePrefer.getInstance().put(StringFinal.SEX, user.getSex());

                    Toast.makeText(getActivity(), "Đăng nhập thành công", Toast.LENGTH_SHORT).show();

                    startActivity(new Intent(getActivity(), MainActivity.class));

                } else {
                    Toast.makeText(getActivity(),
                            "Tài khoản chưa có dữu liệu, vui lòng liên lạc với trung tâm để cập nhập",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}
