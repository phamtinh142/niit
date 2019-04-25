package com.example.niit.Manager.activity.StudentManage;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.example.niit.Manager.activity.StudentManage.adapter.StudentManageAdapter;
import com.example.niit.Manager.activity.StudentManage.entites.Student;
import com.example.niit.R;
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

public class StudentManageActivity extends AppCompatActivity {

    @BindView(R.id.recyclerView_student_manage)
    RecyclerView recyclerViewStudentList;

    private List<Student> studentList;
    private StudentManageAdapter studentManageAdapter;

    private DatabaseReference databaseReference;

    String Class = "CP13";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_manage);
        ButterKnife.bind(this);
        init();
        getData();

    }

    private void init() {
        databaseReference = FirebaseDatabase.getInstance().getReference();
        studentList = new ArrayList<>();
        recyclerViewStudentList.setHasFixedSize(true);
        recyclerViewStudentList.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        studentManageAdapter = new StudentManageAdapter(this, studentList);
        recyclerViewStudentList.setAdapter(studentManageAdapter);
    }

    @OnCheckedChanged({R.id.rdo_cp13_student_manage, R.id.rdo_cp14_student_manage, R.id.rdo_cp15_student_manage})
    public void onCheckedRdoClass(CompoundButton button, boolean checked) {
        if (checked) {
            studentList.clear();
            studentManageAdapter.notifyDataSetChanged();
            switch (button.getId()) {
                case R.id.rdo_cp13_student_manage:
                    Class = "CP13";
                    break;
                case R.id.rdo_cp14_student_manage:
                    Class = "CP14";
                    break;
                case R.id.rdo_cp15_student_manage:
                    Class = "CP15";
                    break;
            }
            getData();
        }
    }

    private void getData() {
        databaseReference.child("Student").child(Class).addChildEventListener(new ChildEventListener() {
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
}
