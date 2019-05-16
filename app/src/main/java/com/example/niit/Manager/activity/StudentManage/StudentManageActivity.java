package com.example.niit.Manager.activity.StudentManage;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import com.example.niit.Manager.activity.StudentManage.adapter.StudentManageAdapter;
import com.example.niit.Manager.activity.StudentManage.entites.Student;
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

public class StudentManageActivity extends AppCompatActivity implements ClassAdapter.ClassesListener {

    @BindView(R.id.recyclerView_student_manage)
    RecyclerView recyclerViewStudentList;
    @BindView(R.id.btn_choose_classes)
    CheckBox btn_choose_classes;
    @BindView(R.id.recyclerView_class)
    RecyclerView recyclerView_class;
    @BindView(R.id.layout_classes)
    LinearLayout layout_classes;

    List<String> stringList;
    ClassAdapter classAdapter;

    private List<Student> studentList;
    private StudentManageAdapter studentManageAdapter;

    private DatabaseReference databaseReference;

    String Classes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_manage);
        ButterKnife.bind(this);
        init();

        getClasses();

        getData();

    }

    private void getClasses() {
        databaseReference.child("classes").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String classes = dataSnapshot.getValue(String.class);

                if (!classes.equals("ALL")) {
                    stringList.add(classes);
                    classAdapter.notifyDataSetChanged();
                    Classes = classes;
                    btn_choose_classes.setText(classes);
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

        studentList = new ArrayList<>();
        recyclerViewStudentList.setHasFixedSize(true);
        recyclerViewStudentList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        studentManageAdapter = new StudentManageAdapter(this, studentList);
        recyclerViewStudentList.setAdapter(studentManageAdapter);

        stringList = new ArrayList<>();
        recyclerView_class.setHasFixedSize(true);
        recyclerView_class.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        classAdapter = new ClassAdapter(this, stringList, this);
        recyclerView_class.setAdapter(classAdapter);
    }

    @OnCheckedChanged(R.id.btn_choose_classes)
    public void onCheckedChooseClass(boolean checked) {
        if (checked) {
            layout_classes.setVisibility(View.VISIBLE);
        } else {
            layout_classes.setVisibility(View.GONE);
        }
    }

    private void getData() {
        databaseReference.child("Student").child(Classes).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.d("ktdd", "onDataChange: " + dataSnapshot.child("name").getValue().toString());

                Student student = new Student();

                student.setAddress(dataSnapshot.child("address").getValue().toString());
                student.setAge(dataSnapshot.child("age").getValue().toString());
                student.setAvatar(dataSnapshot.child("avatar").getValue().toString());
                student.setBithday(dataSnapshot.child("bithday").getValue().toString());
                student.setClassUser(dataSnapshot.child("classUser").getValue().toString());
                student.setEmail(dataSnapshot.child("email").getValue().toString());
                student.setPhone(dataSnapshot.child("phone").getValue().toString());
                student.setName(dataSnapshot.child("name").getValue().toString());
                student.setSex(dataSnapshot.child("sex").getValue().toString());

                studentList.add(student);

                studentManageAdapter.notifyDataSetChanged();
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

    @Override
    public void onClickItemClasses(String classes) {
        btn_choose_classes.setText(classes);
        layout_classes.setVisibility(View.GONE);
        btn_choose_classes.setChecked(false);
    }
}
