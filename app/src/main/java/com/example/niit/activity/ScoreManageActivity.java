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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.niit.R;
import com.example.niit.adapter.SubjectAdapter;
import com.example.niit.entities.Subjects;
import com.example.niit.listener.ChooseSubjectListener;
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

public class ScoreManageActivity extends AppCompatActivity implements ChooseSubjectListener {
    @BindView(R.id.btn_choose_subject)
    CheckBox btn_choose_subject;
    @BindView(R.id.edt_practicing_score)
    EditText edt_practicing_score;
    @BindView(R.id.edt_theory_score)
    EditText edt_theory_score;
    @BindView(R.id.btn_create_subject)
    Button btn_create_subject;
    @BindView(R.id.layout_subject)
    LinearLayout layout_subject;
    @BindView(R.id.recyclerView_subject)
    RecyclerView recyclerView_subject;

    List<Subjects> subjectsList;
    SubjectAdapter subjectAdapter;

    String classesUser;
    String idUser;

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_manage);
        ButterKnife.bind(this);
        getDataBundle();
        init();

        getSubject();
    }

    private void getSubject() {
        databaseReference.child("subjects").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Subjects subjects = dataSnapshot.getValue(Subjects.class);

                Log.d("ktsub", "-------------------------------------");
                Log.d("ktsub", "onChildAdded: " + subjects.getId());
                Log.d("ktsub", "onChildAdded: " + subjects.getSubject());

                subjectsList.add(subjects);

                subjectAdapter.notifyDataSetChanged();
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
        subjectsList = new ArrayList<>();
        recyclerView_subject.setHasFixedSize(true);
        recyclerView_subject.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        subjectAdapter = new SubjectAdapter(this, subjectsList, this);
        recyclerView_subject.setAdapter(subjectAdapter);
    }

    private void getDataBundle() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            classesUser = bundle.getString("classes");
            idUser = bundle.getString("userID");
        }
    }

    @OnCheckedChanged(R.id.btn_choose_subject)
    public void onCheckedSubject(boolean checked) {
        if (checked) {
            layout_subject.setVisibility(View.VISIBLE);
        } else {
            layout_subject.setVisibility(View.GONE);
        }
    }

    @Override
    public void onSubject(String idSubject) {
        Toast.makeText(this, idSubject, Toast.LENGTH_SHORT).show();
    }
}
