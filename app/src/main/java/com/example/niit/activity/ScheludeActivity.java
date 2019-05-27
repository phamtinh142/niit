package com.example.niit.activity;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.example.niit.R;
import com.example.niit.Share.SharePrefer;
import com.example.niit.Share.StringFinal;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ScheludeActivity extends AppCompatActivity {
    @BindView(R.id.edt_thu2_schelude_manage)
    TextView edt_thu2_schelude_manage;
    @BindView(R.id.edt_thu3_schelude_manage)
    TextView edt_thu3_schelude_manage;
    @BindView(R.id.edt_thu4_schelude_manage)
    TextView edt_thu4_schelude_manage;
    @BindView(R.id.edt_thu5_schelude_manage)
    TextView edt_thu5_schelude_manage;
    @BindView(R.id.edt_thu6_schelude_manage)
    TextView edt_thu6_schelude_manage;
    @BindView(R.id.edt_thu7_schelude_manage)
    TextView edt_thu7_schelude_manage;

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schelude);
        ButterKnife.bind(this);
        init();
        getData();
    }

    private void init() {
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    private void getData() {
        String classes = SharePrefer.getInstance().get(StringFinal.CLASSES, String.class);
        databaseReference.child("scheludes").child(classes).addValueEventListener(new ValueEventListener() {
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

    @OnClick(R.id.ibtn_back)
    public void onClickBack() {
        finish();
    }
}
