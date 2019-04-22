package com.example.niit.Login;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.niit.R;
import com.example.niit.Share.SharePrefer;
import com.example.niit.Share.StringFinal;
import com.example.niit.Student.InfoUser.User;
import com.example.niit.Student.activity.MainStudent.MainActivity;
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

        String id = SharePrefer.getInstance().get(StringFinal.ID, String.class);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (id.equals("")) {
                    startActivity(new Intent(StartActivity.this, LoginActivity.class));
                } else {
                    SharePrefer.getInstance().clear();
                    getInforUser(id);
                }
            }
        }, 2000);

    }

    private void getInforUser(String id) {
        databaseReference.child("Student").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(id).exists()) {
                    SharePrefer.getInstance().put(StringFinal.ID, id);

                    User user = dataSnapshot.child(id).getValue(User.class);

                    SharePrefer.getInstance().put(StringFinal.ADDRESS, user.getAddress());
                    SharePrefer.getInstance().put(StringFinal.USER_NAME, user.getName());
                    SharePrefer.getInstance().put(StringFinal.AGE, user.getAge());
                    SharePrefer.getInstance().put(StringFinal.EMAIL, user.getEmail());
                    SharePrefer.getInstance().put(StringFinal.IMAGE, user.getImage());
                    SharePrefer.getInstance().put(StringFinal.PHONE, user.getPhone());
                    SharePrefer.getInstance().put(StringFinal.SEX, user.getSex());

                    startActivity(new Intent(StartActivity.this, MainActivity.class));

                } else {
                    startActivity(new Intent(StartActivity.this, LoginActivity.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
