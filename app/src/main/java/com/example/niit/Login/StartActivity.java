package com.example.niit.Login;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.niit.Manager.activity.ManagerActivity;
import com.example.niit.Manager.activity.StudentManage.entites.Student;
import com.example.niit.R;
import com.example.niit.Share.SharePrefer;
import com.example.niit.Share.StringFinal;
import com.example.niit.Student.activity.MainStudent.MainActivity;
import com.example.niit.model.Manager;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class StartActivity extends AppCompatActivity {

    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        final String id = SharePrefer.getInstance().get(StringFinal.ID, String.class);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (id.equals("")) {
                    startActivity(new Intent(StartActivity.this, LoginActivity.class));
                } else {
                    getInforUser(id);
                }
            }
        }, 2000);

    }

    private void getInforUser(final String id) {
        int type = SharePrefer.getInstance().get(StringFinal.TYPE, Integer.class);

        if (type == 0) {
            databaseReference.child("manager").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Manager manager = dataSnapshot.child(id).getValue(Manager.class);
                    SharePrefer.getInstance().put(StringFinal.ADDRESS, manager.getAddress());
                    SharePrefer.getInstance().put(StringFinal.AVATAR, manager.getAvatar());
                    SharePrefer.getInstance().put(StringFinal.EMAIL, manager.getEmail());
                    SharePrefer.getInstance().put(StringFinal.ID, manager.getId());
                    SharePrefer.getInstance().put(StringFinal.USERNAME, manager.getName());
                    SharePrefer.getInstance().put(StringFinal.PHONE, manager.getPhone());
                    SharePrefer.getInstance().put(StringFinal.TYPE, manager.getType());
                    SharePrefer.getInstance().put(StringFinal.SEX, manager.getSex());

                    startActivity(new Intent(StartActivity.this, ManagerActivity.class));
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        } else {
            databaseReference.child("Student").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Student student = dataSnapshot.child(id).getValue(Student.class);
                    SharePrefer.getInstance().put(StringFinal.TYPE, student.getType_account());
                    SharePrefer.getInstance().put(StringFinal.ADDRESS, student.getAddress());
                    SharePrefer.getInstance().put(StringFinal.USERNAME, student.getName());
                    SharePrefer.getInstance().put(StringFinal.AGE, student.getAge());
                    SharePrefer.getInstance().put(StringFinal.EMAIL, student.getEmail());
                    SharePrefer.getInstance().put(StringFinal.AVATAR, student.getAvatar());
                    SharePrefer.getInstance().put(StringFinal.PHONE, student.getPhone());
                    SharePrefer.getInstance().put(StringFinal.SEX, student.getSex());
                    SharePrefer.getInstance().put(StringFinal.ID, student.getId());
                    SharePrefer.getInstance().put(StringFinal.TYPE, student.getType_account());

                    startActivity(new Intent(StartActivity.this, MainActivity.class));
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }
}
