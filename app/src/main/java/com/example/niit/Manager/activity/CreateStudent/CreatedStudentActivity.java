package com.example.niit.Manager.activity.CreateStudent;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;

import android.widget.Toast;

import com.example.niit.Manager.activity.CreateStudent.entities.CreateAccountStudent;
import com.example.niit.Manager.activity.CreateStudent.entities.CreatedStudent;
import com.example.niit.R;

import com.example.niit.adapter.ClassAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

public class CreatedStudentActivity extends AppCompatActivity implements ClassAdapter.ClassesListener {
    @BindView(R.id.edt_classes_create_student)
    CheckBox edt_classes_create_student;
    @BindView(R.id.edt_id_create_student)
    EditText edt_id_create_student;
    @BindView(R.id.edt_name_create_student)
    EditText edt_name_create_student;
    @BindView(R.id.edt_password_create_student)
    EditText edt_password_create_student;
    @BindView(R.id.edt_confirm_create_student)
    EditText edt_confirm_create_student;
    @BindView(R.id.layout_progress_bar)
    LinearLayout layoutProgressBar;
    @BindView(R.id.recyclerView_class)
    RecyclerView recyclerView_class;
    @BindView(R.id.layout_classes)
    LinearLayout layout_classes;

    List<String> stringList;
    ClassAdapter classAdapter;

    String confirmPassword = "";
    String address = "";
    String age = "";
    String avatar = "";
    String birthDay = "";
    String classes = "";
    String email = "";
    String id = "";
    String name = "";
    String phone = "";
    String password = "";
    String sex = "";

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_created_student);
        ButterKnife.bind(this);
        init();

        getClasses();
    }

    private void getClasses() {
        databaseReference.child("classes").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String classes = dataSnapshot.getValue(String.class);
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

    private void init() {
        databaseReference = FirebaseDatabase.getInstance().getReference();

        stringList = new ArrayList<>();
        recyclerView_class.setHasFixedSize(true);
        recyclerView_class.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        classAdapter = new ClassAdapter(this, stringList, this);
        recyclerView_class.setAdapter(classAdapter);
    }

    @OnCheckedChanged(R.id.edt_classes_create_student)
    public void onCheckedClasses(boolean checked) {
        if (checked) {
            layout_classes.setVisibility(View.VISIBLE);
        } else {
            layout_classes.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.ibtn_back_create_account)
    public void onClickBackCreateAccount() {
        finish();
    }

    @OnClick(R.id.btn_create_account)
    public void onClickCreateAccount() {
        classes = edt_classes_create_student.getText().toString().trim();
        id = edt_id_create_student.getText().toString().trim();
        name = edt_name_create_student.getText().toString().trim();
        password = edt_password_create_student.getText().toString().trim();
        confirmPassword = edt_confirm_create_student.getText().toString().trim();


        if (classes.isEmpty()) {
            Toast.makeText(this, "Vui lòng chọn lớp", Toast.LENGTH_SHORT).show();
            edt_classes_create_student.setChecked(true);
            layout_classes.setVisibility(View.VISIBLE);
        } else if (id.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập ID", Toast.LENGTH_SHORT).show();
            edt_id_create_student.requestFocus();
        } else if (name.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập tên", Toast.LENGTH_SHORT).show();
            edt_name_create_student.requestFocus();
        } else if (password.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập password", Toast.LENGTH_SHORT).show();
            edt_password_create_student.requestFocus();
        } else if (confirmPassword.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập lại password", Toast.LENGTH_SHORT).show();
            edt_confirm_create_student.requestFocus();
        } else if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Mật khẩu không khớp", Toast.LENGTH_SHORT).show();
            edt_confirm_create_student.requestFocus();
        } else {
            CreateAccountStudent createAccountStudent = new CreateAccountStudent(password);
            databaseReference.child("account").child(classes).child(id)
                    .setValue(createAccountStudent, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                            if (databaseError == null) {
                                createProfile();
                            } else {
                                Toast.makeText(CreatedStudentActivity.this, "Lỗi, vui lòng thử lại !", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    private void createProfile() {
        CreatedStudent createdStudent = new CreatedStudent();
        createdStudent.setId(id);
        createdStudent.setAddress(address);
        createdStudent.setAge(age);
        createdStudent.setClassUser(classes);
        createdStudent.setEmail(email);
        createdStudent.setAvatar(avatar);
        createdStudent.setName(name);
        createdStudent.setPhone(phone);
        createdStudent.setSex(sex);
        createdStudent.setBithday(birthDay);
        createdStudent.setType_account(1);


        databaseReference.child("student").child(classes).child(id)
                .setValue(createdStudent, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                        if (databaseError == null) {
                            Toast.makeText(CreatedStudentActivity.this, "Tạo tài khoản thành công !", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(CreatedStudentActivity.this, "Tạo tài khoản thất bại !", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onClickItemClasses(String classes) {
        edt_classes_create_student.setText(classes);
        layout_classes.setVisibility(View.GONE);
        edt_classes_create_student.setChecked(false);


    }
}
