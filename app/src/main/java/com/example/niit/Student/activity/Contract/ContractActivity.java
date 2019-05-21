package com.example.niit.Student.activity.Contract;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;

import com.example.niit.Manager.activity.CreateStudent.entities.CreatedStudent;
import com.example.niit.Manager.activity.StudentManage.adapter.StudentManageAdapter;
import com.example.niit.Manager.activity.StudentManage.entites.Student;
import com.example.niit.R;
import com.example.niit.Share.SharePrefer;
import com.example.niit.Share.StringFinal;
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

public class ContractActivity extends AppCompatActivity implements AccountListener, TextWatcher {
    @BindView(R.id.edt_search_name)
    EditText edt_search_name;
    @BindView(R.id.rdo_classes)
    RadioButton rdo_classes;
    @BindView(R.id.rdo_manager)
    RadioButton rdo_manager;
    @BindView(R.id.recyclerView_contract)
    RecyclerView recyclerView_contract;

    List<CreatedStudent> studentList;
    List<CreatedStudent> searchList;
    StudentManageAdapter studentManageAdapter;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contract);
        ButterKnife.bind(this);
        init();

        getStudentList();

        edt_search_name.addTextChangedListener(this);


    }

    private void init() {
        databaseReference = FirebaseDatabase.getInstance().getReference();

        searchList = new ArrayList<>();
        studentList = new ArrayList<>();
        recyclerView_contract.setHasFixedSize(true);
        recyclerView_contract.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        studentManageAdapter = new StudentManageAdapter(this, studentList, this);
        recyclerView_contract.setAdapter(studentManageAdapter);
    }

    @OnCheckedChanged({R.id.rdo_classes, R.id.rdo_manager})
    public void onCheckedContract(CompoundButton button, boolean checked) {
        if (checked) {
            studentList.clear();
            studentManageAdapter.notifyDataSetChanged();

            switch (button.getId()) {
                case R.id.rdo_classes:
                    getStudentList();
                    break;
                case R.id.rdo_manager:
                    getManagerList();
                    break;
            }
        }
    }

    private void getManagerList() {
        databaseReference.child("manager").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                CreatedStudent createdStudent = dataSnapshot.getValue(CreatedStudent.class);
                studentList.add(createdStudent);
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

    private void getStudentList() {
        String classes = SharePrefer.getInstance().get(StringFinal.CLASSES, String.class);

        databaseReference.child("student").child(classes).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                CreatedStudent createdStudent = dataSnapshot.getValue(CreatedStudent.class);

                studentList.add(createdStudent);
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
    public void onClickAccount(int position) {

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        s.toString().toLowerCase();
        searchList.clear();


        for (CreatedStudent createdStudent : studentList) {
            if (createdStudent.getName().toLowerCase().contains(s.toString())) {
                searchList.add(createdStudent);
            }
        }

        studentManageAdapter = new StudentManageAdapter(ContractActivity.this, searchList, this);

        recyclerView_contract.setAdapter(studentManageAdapter);
        if (s.toString().equals("")){
            studentManageAdapter = new StudentManageAdapter(ContractActivity.this, studentList, this);
            recyclerView_contract.setAdapter(studentManageAdapter);
        }
    }
}
