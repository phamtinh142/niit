package com.example.niit.Manager.activity.ScheludeManage;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.niit.R;
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

public class ScheludeManageActivity extends AppCompatActivity implements ClassAdapter.ClassesListener {

    @BindView(R.id.edt_thu2_schelude_manage)
    EditText edt_thu2_schelude_manage;
    @BindView(R.id.edt_thu3_schelude_manage)
    EditText edt_thu3_schelude_manage;
    @BindView(R.id.edt_thu4_schelude_manage)
    EditText edt_thu4_schelude_manage;
    @BindView(R.id.edt_thu5_schelude_manage)
    EditText edt_thu5_schelude_manage;
    @BindView(R.id.edt_thu6_schelude_manage)
    EditText edt_thu6_schelude_manage;
    @BindView(R.id.edt_thu7_schelude_manage)
    EditText edt_thu7_schelude_manage;
    @BindView(R.id.recyclerView_class)
    RecyclerView recyclerView_class;
    @BindView(R.id.btn_choose_classes)
    CheckBox btn_choose_classes;
    @BindView(R.id.layout_classes)
    LinearLayout layout_classes;

    List<String> stringList;
    ClassAdapter classAdapter;


    private String Classes = "";

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schelude_manage);
        ButterKnife.bind(this);
        init();

        getClasses();

//        getData();

    }

    private void getClasses() {
        databaseReference.child("classes").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String classes = dataSnapshot.getValue(String.class);

                Log.d("adadada", "onChildAdded: " + classes);

                stringList.add(classes);

                for (String st : stringList) {
                    if (st.equals("ALL"))
                        stringList.remove(st);
                }

                classAdapter.notifyDataSetChanged();
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

    @OnCheckedChanged(R.id.btn_choose_classes)
    public void onCheckedChooseClasses(boolean checked) {
        if (checked) {
            layout_classes.setVisibility(View.VISIBLE);
        } else {
            layout_classes.setVisibility(View.GONE);
        }
    }

    private void getData() {
        databaseReference.child("scheludes").child(Classes).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String thu2 = dataSnapshot.child("thu2").getValue().toString();
                edt_thu2_schelude_manage.setText(thu2);
                String thu3 = dataSnapshot.child("thu3").getValue().toString();
                edt_thu3_schelude_manage.setText(thu3);
                String thu4 = dataSnapshot.child("thu4").getValue().toString();
                edt_thu4_schelude_manage.setText(thu4);
                String thu5 = dataSnapshot.child("thu5").getValue().toString();
                edt_thu5_schelude_manage.setText(thu5);
                String thu6 = dataSnapshot.child("thu6").getValue().toString();
                edt_thu6_schelude_manage.setText(thu6);
                String thu7 = dataSnapshot.child("thu7").getValue().toString();
                edt_thu7_schelude_manage.setText(thu7);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @OnClick(R.id.btn_create_schelude_manage)
    public void onClickCreateSchelude() {
        String classes = btn_choose_classes.getText().toString().trim();
        String thu2 = edt_thu2_schelude_manage.getText().toString().trim();
        String thu3 = edt_thu3_schelude_manage.getText().toString().trim();
        String thu4 = edt_thu4_schelude_manage.getText().toString().trim();
        String thu5 = edt_thu5_schelude_manage.getText().toString().trim();
        String thu6 = edt_thu6_schelude_manage.getText().toString().trim();
        String thu7 = edt_thu7_schelude_manage.getText().toString().trim();

        if (classes.isEmpty()) {
            Toast.makeText(this, "Vui lòng chọn lớp", Toast.LENGTH_SHORT).show();
            layout_classes.setVisibility(View.VISIBLE);
            btn_choose_classes.setChecked(true);
        } else if (thu2.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ", Toast.LENGTH_SHORT).show();
            edt_thu2_schelude_manage.requestFocus();
        } else if (thu3.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ", Toast.LENGTH_SHORT).show();
            edt_thu3_schelude_manage.requestFocus();
        } else if (thu4.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ", Toast.LENGTH_SHORT).show();
            edt_thu4_schelude_manage.requestFocus();
        } else if (thu5.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ", Toast.LENGTH_SHORT).show();
            edt_thu5_schelude_manage.requestFocus();
        } else if (thu6.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ", Toast.LENGTH_SHORT).show();
            edt_thu6_schelude_manage.requestFocus();
        } else if (thu7.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ", Toast.LENGTH_SHORT).show();
            edt_thu7_schelude_manage.requestFocus();
        } else {
            CreatedScheludeManage createdScheludeManage = new CreatedScheludeManage(thu2, thu3, thu4, thu5, thu6, thu7);

            databaseReference.child("scheludes").child(Classes).setValue(createdScheludeManage, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                    if (databaseError == null) {
                        Toast.makeText(ScheludeManageActivity.this, "Tạo thành công", Toast.LENGTH_SHORT).show();
                        edt_thu2_schelude_manage.setText("");
                        edt_thu3_schelude_manage.setText("");
                        edt_thu4_schelude_manage.setText("");
                        edt_thu5_schelude_manage.setText("");
                        edt_thu6_schelude_manage.setText("");
                        edt_thu7_schelude_manage.setText("");
                    } else {
                        Toast.makeText(ScheludeManageActivity.this, "Tạo thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }

    @Override
    public void onClickItemClasses(String classes) {
        Classes = classes;
        btn_choose_classes.setText(classes);
        layout_classes.setVisibility(View.GONE);
        btn_choose_classes.setChecked(false);
    }
}
