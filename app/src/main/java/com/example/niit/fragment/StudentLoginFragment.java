package com.example.niit.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.niit.entities.Login;
import com.example.niit.entities.CreatedStudent;
import com.example.niit.R;
import com.example.niit.Share.SharePrefer;
import com.example.niit.Share.StringFinal;
import com.example.niit.activity.StudentActivity;
import com.example.niit.adapter.ClassAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

public class StudentLoginFragment extends Fragment implements ClassAdapter.ClassesListener {

    @BindView(R.id.edt_id_student_login)
    EditText edt_id_login;
    @BindView(R.id.edt_password_student_login)
    EditText edt_password_login;
    @BindView(R.id.edt_class_student_login)
    CheckBox edt_classlogin;
    @BindView(R.id.recyclerView_class)
    RecyclerView recyclerViewClass;
    @BindView(R.id.layout_classes)
    LinearLayout layout_classes;

    private List<String> stringList;
    private ClassAdapter classAdapter;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_student_login, container, false);
        ButterKnife.bind(this, view);

        init();

        getClassSchool();

        return view;
    }

    private void getClassSchool() {
        databaseReference.child("classes").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String classes = dataSnapshot.getValue(String.class);

                Log.d("ktClasses", "onChildAdded: " + classes);

                if (!classes.equals("ALL")) {
                    stringList.add(classes);
                    classAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @OnCheckedChanged(R.id.edt_class_student_login)
    public void onCheckClasses(boolean checked) {
        if (checked) {
            layout_classes.setVisibility(View.VISIBLE);
        } else {
            layout_classes.setVisibility(View.GONE);
        }
    }

    private void init() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        stringList = new ArrayList<>();
        recyclerViewClass.setHasFixedSize(true);
        recyclerViewClass.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        classAdapter = new ClassAdapter(getActivity(), stringList, this);
        recyclerViewClass.setAdapter(classAdapter);
    }

    @OnClick(R.id.btn_login_student_account)
    public void onClickLoginAccount() {
        String id = edt_id_login.getText().toString().trim();
        String passWord  = edt_password_login.getText().toString().trim();
        String classes = edt_classlogin.getText().toString().trim();

        if (classes.isEmpty()) {
            Toast.makeText(getActivity(), "Vui lòng chọn lớp", Toast.LENGTH_SHORT).show();
        } else if (id.isEmpty()) {
            Toast.makeText(getActivity(), "Vui lòng nhập ID", Toast.LENGTH_SHORT).show();
        } else if (passWord.isEmpty()) {
            Toast.makeText(getActivity(), "Vui lòng nhập mật khẩu", Toast.LENGTH_SHORT).show();
            edt_password_login.requestFocus();
        } else {
            login(id, passWord, classes);
        }


    }

    private void login(final String id, final String password, final String classes) {
        databaseReference.child("account").child(classes).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(id).exists()) {

                    Login login = dataSnapshot.child(id).getValue(Login.class);

                    if (login.getPassword().equals(password)) {
                        getInforUser(id, classes);
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

    private void getInforUser(final String id, String classes) {

        databaseReference.child("student").child(classes).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                CreatedStudent createdStudent = dataSnapshot.child(id).getValue(CreatedStudent.class);

                SharePrefer.getInstance().put(StringFinal.ID, createdStudent.getId());
                SharePrefer.getInstance().put(StringFinal.AGE, createdStudent.getAge());
                SharePrefer.getInstance().put(StringFinal.CLASSES, createdStudent.getClassUser());
                SharePrefer.getInstance().put(StringFinal.EMAIL, createdStudent.getEmail());
                SharePrefer.getInstance().put(StringFinal.ADDRESS, createdStudent.getAddress());
                SharePrefer.getInstance().put(StringFinal.AVATAR, createdStudent.getAvatar());
                SharePrefer.getInstance().put(StringFinal.USERNAME, createdStudent.getName());
                SharePrefer.getInstance().put(StringFinal.PHONE, createdStudent.getPhone());
                SharePrefer.getInstance().put(StringFinal.SEX, createdStudent.getSex());
                SharePrefer.getInstance().put(StringFinal.BIRTHDAY, createdStudent.getBithday());
                SharePrefer.getInstance().put(StringFinal.TYPE, createdStudent.getType_account());

                Toast.makeText(getContext(), "Đăng nhập thành công !", Toast.LENGTH_SHORT).show();

                startActivity(new Intent(getActivity(), StudentActivity.class));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onClickItemClasses(String classes) {
        edt_classlogin.setText(classes);
        layout_classes.setVisibility(View.GONE);
        edt_classlogin.setChecked(false);
    }
}
