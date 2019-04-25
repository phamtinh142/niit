package com.example.niit.Manager.activity.ScheludeManage;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.niit.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

public class ScheludeManageActivity extends AppCompatActivity {

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
    @BindView(R.id.rdo_cp13_schelude_manage)
    RadioButton rdoClass1;


    private String Class = "CP13";

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schelude_manage);
        ButterKnife.bind(this);
        init();

        getData();

    }

    private void init() {
        databaseReference = FirebaseDatabase.getInstance().getReference("schelude");
    }

    private void getData() {
        databaseReference.child(Class).addValueEventListener(new ValueEventListener() {
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
        String thu2 = edt_thu2_schelude_manage.getText().toString().trim();
        String thu3 = edt_thu3_schelude_manage.getText().toString().trim();
        String thu4 = edt_thu4_schelude_manage.getText().toString().trim();
        String thu5 = edt_thu5_schelude_manage.getText().toString().trim();
        String thu6 = edt_thu6_schelude_manage.getText().toString().trim();
        String thu7 = edt_thu7_schelude_manage.getText().toString().trim();

        if (thu2.isEmpty()) {
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

            databaseReference.child(Class).setValue(createdScheludeManage, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                    if (databaseError == null) {
                        Toast.makeText(ScheludeManageActivity.this, "Tạo thành công", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ScheludeManageActivity.this, "Tạo thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }

    @OnCheckedChanged({R.id.rdo_cp13_schelude_manage, R.id.rdo_cp14_schelude_manage, R.id.rdo_cp15_schelude_manage})
    public void onCheckedRdoClass(CompoundButton button, boolean checked) {
        if (checked) {
            switch (button.getId()) {
                case R.id.rdo_cp13_schelude_manage:
                    Class = "CP13";
                    getData();
                    break;
                case R.id.rdo_cp14_schelude_manage:
                    Class = "CP14";
                    getData();
                    break;
                case R.id.rdo_cp15_schelude_manage:
                    Class = "CP15";
                    getData();
                    break;
            }
        }
    }


}
