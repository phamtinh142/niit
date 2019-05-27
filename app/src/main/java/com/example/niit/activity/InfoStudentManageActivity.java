package com.example.niit.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.niit.R;
import com.example.niit.entities.CreatedStudent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class InfoStudentManageActivity extends AppCompatActivity {
    @BindView(R.id.txt_name)
    TextView txt_name;
    @BindView(R.id.txt_email)
    TextView txt_email;
    @BindView(R.id.txt_age)
    TextView txt_age;
    @BindView(R.id.txt_sex)
    TextView txt_sex;
    @BindView(R.id.txt_birthday)
    TextView txt_birthday;
    @BindView(R.id.txt_phone)
    TextView txt_phone;
    @BindView(R.id.txt_address)
    TextView txt_address;

    DatabaseReference databaseReference;

    String userID;
    String classes;
    int typeAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_student_manage);
        getDataBundle();
        ButterKnife.bind(this);
        databaseReference = FirebaseDatabase.getInstance().getReference();

        getDataInfo();
    }

    private void getDataBundle() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            userID = bundle.getString("idUser", "");
            classes = bundle.getString("classUser", "");
            typeAccount = bundle.getInt("typeAccount", 0);
        }
    }

    private void getDataInfo() {
        databaseReference.child("student").child(classes).child(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                CreatedStudent createdStudent = dataSnapshot.getValue(CreatedStudent.class);

                txt_name.setText(createdStudent.getName());
                txt_email.setText(createdStudent.getEmail());
                txt_age.setText(createdStudent.getAge());
                txt_sex.setText(createdStudent.getSex());
                txt_birthday.setText(createdStudent.getBithday());
                txt_phone.setText(createdStudent.getPhone());
                txt_address.setText(createdStudent.getAddress());

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
