package com.example.niit.activity;

import android.content.Intent;
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

import com.example.niit.entities.CreatedStudent;
import com.example.niit.adapter.StudentManageAdapter;
import com.example.niit.R;
import com.example.niit.adapter.ClassAdapter;
import com.example.niit.listener.AccountListener;
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

public class StudentManageActivity extends AppCompatActivity implements ClassAdapter.ClassesListener, AccountListener {
    @BindView(R.id.recyclerView_student_manage)
    RecyclerView recyclerViewStudentList;
    @BindView(R.id.btn_choose_classes)
    CheckBox btn_choose_classes;
    @BindView(R.id.recyclerView_class)
    RecyclerView recyclerView_class;
    @BindView(R.id.layout_choose_classes)
    LinearLayout layout_choose_classes;

    List<String> stringList;
    ClassAdapter classAdapter;

    private List<CreatedStudent> createdStudentList;
    private StudentManageAdapter studentManageAdapter;

    private DatabaseReference databaseReference;

    String Classes = "CP15";

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
                    btn_choose_classes.setText(classes);
                    Classes = classes;
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

        createdStudentList = new ArrayList<>();
        recyclerViewStudentList.setHasFixedSize(true);
        recyclerViewStudentList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        studentManageAdapter = new StudentManageAdapter(this, createdStudentList, this);
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
            layout_choose_classes.setVisibility(View.VISIBLE);
        } else {
            layout_choose_classes.setVisibility(View.GONE);
        }
    }

    private void getData() {

        databaseReference.child("student").child(Classes).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                CreatedStudent createdStudent = dataSnapshot.getValue(CreatedStudent.class);

                Log.d("ktmamamam", "onChildAdded: " + createdStudent.getName());


                createdStudentList.add(createdStudent);
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
        createdStudentList.clear();
        studentManageAdapter.notifyDataSetChanged();

        btn_choose_classes.setText(classes);
        layout_choose_classes.setVisibility(View.GONE);
        btn_choose_classes.setChecked(false);

        Classes = classes;
        getData();
    }

    @Override
    public void onClickAccount(int position) {
        Intent intent = new Intent(this, InfoStudentActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("id", createdStudentList.get(position).getId());
        bundle.putString("class", createdStudentList.get(position).getClassUser());
        bundle.putInt("type_account", createdStudentList.get(position).getType_account());
        bundle.putString("name", createdStudentList.get(position).getName());
        bundle.putString("avatar", createdStudentList.get(position).getAvatar());
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
