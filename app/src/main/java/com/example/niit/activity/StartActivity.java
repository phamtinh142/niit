package com.example.niit.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.niit.entities.CreatedStudent;
import com.example.niit.R;
import com.example.niit.Share.SharePrefer;
import com.example.niit.Share.StringFinal;
import com.example.niit.entities.Manager;
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

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                String id = SharePrefer.getInstance().get(StringFinal.ID, String.class);

                Log.d("ktStart", "id: " + id);
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

        Log.d("ktStart", "type: " + type);

        if (type == 0) {
            databaseReference.child("manager").child(id).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    String address = dataSnapshot.child("address").getValue(String.class);
                    String avatar = dataSnapshot.child("avatar").getValue(String.class);
                    String email = dataSnapshot.child("email").getValue(String.class);
                    String id = dataSnapshot.child("id").getValue(String.class);
                    String name = dataSnapshot.child("name").getValue(String.class);
                    String phone = dataSnapshot.child("phone").getValue(String.class);
                    String sex = dataSnapshot.child("sex").getValue(String.class);
                    int type_account = dataSnapshot.child("type").getValue(Integer.class);

                    SharePrefer.getInstance().put(StringFinal.ADDRESS, address);
                    SharePrefer.getInstance().put(StringFinal.AVATAR, avatar);
                    SharePrefer.getInstance().put(StringFinal.EMAIL, email);
                    SharePrefer.getInstance().put(StringFinal.ID, id);
                    SharePrefer.getInstance().put(StringFinal.USERNAME, name);
                    SharePrefer.getInstance().put(StringFinal.PHONE, phone);
                    SharePrefer.getInstance().put(StringFinal.TYPE, type_account);
                    SharePrefer.getInstance().put(StringFinal.SEX, sex);

                    startActivity(new Intent(StartActivity.this, ManagerActivity.class));
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        } else if (type == 1){
            final String classes = SharePrefer.getInstance().get(StringFinal.CLASSES, String.class);

            databaseReference.child("student").child(classes).child(id).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

//                    Log.d("startApp", "address: " + dataSnapshot.child("address").getValue());
//                    Log.d("startApp", "age: " + dataSnapshot.child("age").getValue());
//                    Log.d("startApp", "avatar: " + dataSnapshot.child("avatar").getValue());
//                    Log.d("startApp", "bithday: " + dataSnapshot.child("bithday").getValue());
//                    Log.d("startApp", "classUser: " + dataSnapshot.child("classUser").getValue());
//                    Log.d("startApp", "email: " + dataSnapshot.child("email").getValue());
//                    Log.d("startApp", "id: " + dataSnapshot.child("id").getValue());
//                    Log.d("startApp", "name: " + dataSnapshot.child("name").getValue());
//                    Log.d("startApp", "phone: " + dataSnapshot.child("phone").getValue());
//                    Log.d("startApp", "sex: " + dataSnapshot.child("sex").getValue());
//                    Log.d("startApp", "type_account: " + dataSnapshot.child("type_account").getValue());

                    String address = dataSnapshot.child("address").getValue(String.class);
                    String age = dataSnapshot.child("age").getValue(String.class);
                    String avatar = dataSnapshot.child("avatar").getValue(String.class);
                    String bithday = dataSnapshot.child("bithday").getValue(String.class);
                    String classUser = dataSnapshot.child("classUser").getValue(String.class);
                    String email = dataSnapshot.child("email").getValue(String.class);
                    String id = dataSnapshot.child("id").getValue(String.class);
                    String name = dataSnapshot.child("name").getValue(String.class);
                    String phone = dataSnapshot.child("phone").getValue(String.class);
                    String sex = dataSnapshot.child("sex").getValue(String.class);
                    int type_account = dataSnapshot.child("type_account").getValue(Integer.class);


                    SharePrefer.getInstance().put(StringFinal.ID, id);
                    SharePrefer.getInstance().put(StringFinal.AGE, age);
                    SharePrefer.getInstance().put(StringFinal.CLASSES, classUser);
                    SharePrefer.getInstance().put(StringFinal.EMAIL, email);
                    SharePrefer.getInstance().put(StringFinal.ADDRESS, address);
                    SharePrefer.getInstance().put(StringFinal.AVATAR, avatar);
                    SharePrefer.getInstance().put(StringFinal.USERNAME, name);
                    SharePrefer.getInstance().put(StringFinal.PHONE, phone);
                    SharePrefer.getInstance().put(StringFinal.SEX, sex);
                    SharePrefer.getInstance().put(StringFinal.BIRTHDAY, bithday);
                    SharePrefer.getInstance().put(StringFinal.TYPE, type_account);

                    startActivity(new Intent(StartActivity.this, StudentActivity.class));
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }
}
