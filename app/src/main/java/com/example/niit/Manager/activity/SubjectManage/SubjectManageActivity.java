package com.example.niit.Manager.activity.SubjectManage;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.example.niit.Manager.activity.SubjectManage.adapter.SubjectManageAdapter;
import com.example.niit.Manager.activity.SubjectManage.pojo.Subjects;
import com.example.niit.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SubjectManageActivity extends AppCompatActivity {
    @BindView(R.id.edt_id_subject)
    EditText edt_id_subject;
    @BindView(R.id.edt_name_subject)
    EditText edt_name_subject;
    @BindView(R.id.recyclerView_subjects)
    RecyclerView recyclerView_subjects;

    List<Subjects> subjectsList;
    SubjectManageAdapter subjectManageAdapter;

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_manage);
        ButterKnife.bind(this);
        init();

        getDataSubjects();
    }

    private void getDataSubjects() {

        databaseReference.child("subjects").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Subjects subjects = dataSnapshot.getValue(Subjects.class);

                subjectsList.add(subjects);
                subjectManageAdapter.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Subjects subjects = dataSnapshot.getValue(Subjects.class);

                subjectsList.add(subjects);
                subjectManageAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                Log.d("fjfjfjfjf", "onChildRemoved: " + dataSnapshot.toString());

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
        recyclerView_subjects.setHasFixedSize(true);
        recyclerView_subjects.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        subjectManageAdapter = new SubjectManageAdapter(this, subjectsList, databaseReference);
        recyclerView_subjects.setAdapter(subjectManageAdapter);

    }

    @OnClick(R.id.btn_create_subject)
    public void onClickCreateSubject() {
        String id = edt_id_subject.getText().toString().trim();
        String object = edt_name_subject.getText().toString().trim();

        if (id.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập mã môn học !", Toast.LENGTH_SHORT).show();
            edt_id_subject.requestFocus();
        } else if (object.isEmpty()){
            Toast.makeText(this, "Vui lòng nhập môn học !", Toast.LENGTH_SHORT).show();
            edt_name_subject.requestFocus();
        } else {
            Subjects subjects = new Subjects(id, object);

            databaseReference.child("subjects").child(id).setValue(subjects, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                    if (databaseError == null) {
                        Toast.makeText(SubjectManageActivity.this, "Tạo thành công !", Toast.LENGTH_SHORT).show();
                        edt_id_subject.setText("");
                        edt_name_subject.setText("");
                    } else {
                        Toast.makeText(SubjectManageActivity.this, "Tạo thất bại, thử lại !", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
