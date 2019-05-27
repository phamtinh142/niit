package com.example.niit.activity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.niit.R;
import com.example.niit.Share.SharePrefer;
import com.example.niit.Share.StringFinal;
import com.example.niit.entities.CreatedStudent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class EditInfoStudentActivity extends AppCompatActivity {
    @BindView(R.id.edt_name_update_student)
    EditText edt_name_update_student;
    @BindView(R.id.edt_email_update_student)
    EditText edt_email_update_student;
    @BindView(R.id.edt_age_update_student)
    EditText edt_age_update_student;
    @BindView(R.id.edt_sex_update_student)
    EditText edt_sex_update_student;
    @BindView(R.id.txt_date_update_student)
    TextView txt_date_update_student;
    @BindView(R.id.edt_phone_update_student)
    EditText edt_phone_update_student;
    @BindView(R.id.edt_address_update_student)
    EditText edt_address_update_student;

    DatabaseReference databaseReference;

    String userID;
    String classes;

    String userName;
    String email;
    String age;
    String sex;
    String bithday;
    String phone;
    String address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_info_student);
        ButterKnife.bind(this);
        userID = SharePrefer.getInstance().get(StringFinal.ID, String.class);
        classes = SharePrefer.getInstance().get(StringFinal.CLASSES, String.class);
        init();
        getData();


    }

    private void init() {
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    private void getData() {
        databaseReference.child("student").child(classes).child(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                CreatedStudent createdStudent = dataSnapshot.getValue(CreatedStudent.class);

                edt_name_update_student.setText(createdStudent.getName());
                edt_email_update_student.setText(createdStudent.getEmail());
                edt_age_update_student.setText(createdStudent.getAge());
                edt_sex_update_student.setText(createdStudent.getSex());
                txt_date_update_student.setText(createdStudent.getBithday());
                edt_phone_update_student.setText(createdStudent.getPhone());
                edt_address_update_student.setText(createdStudent.getAddress());

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

    @OnClick(R.id.txt_date_update_student)
    public void onClickChooseDate() {
        final Calendar calendar = Calendar.getInstance();
        int year = 2000;
        int month = 1;
        int day = 1;
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        calendar.set(i, i1, i2);
                        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat =
                                new SimpleDateFormat("dd/MM/yyyy");
                        txt_date_update_student.setText(simpleDateFormat.format(calendar.getTime()));
                    }
                }, year, month, day);
        datePickerDialog.show();
    }

    @OnClick(R.id.btn_update_account)
    public void onClickUpdateAccount() {
        userName = edt_name_update_student.getText().toString().trim();
        email = edt_email_update_student.getText().toString().trim();
        age = edt_age_update_student.getText().toString().trim();
        sex = edt_sex_update_student.getText().toString().trim();
        bithday = txt_date_update_student.getText().toString().trim();
        phone = edt_phone_update_student.getText().toString().trim();
        address = edt_address_update_student.getText().toString().trim();

        databaseReference.child("student").child(classes).child(userID).child("address").setValue(address);
        databaseReference.child("student").child(classes).child(userID).child("age").setValue(age);
        databaseReference.child("student").child(classes).child(userID).child("bithday").setValue(bithday);
        databaseReference.child("student").child(classes).child(userID).child("email").setValue(email);
        databaseReference.child("student").child(classes).child(userID).child("sex").setValue(sex);
        databaseReference.child("student").child(classes).child(userID).child("name").setValue(userName);
        databaseReference.child("student").child(classes).child(userID).child("phone").setValue(phone);
    }
}
