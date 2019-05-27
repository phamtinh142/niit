package com.example.niit.activity;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.Toast;

import com.example.niit.adapter.SubjectManageAdapter;
import com.example.niit.entities.Subjects;
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
    @BindView(R.id.edt_number_sesion)
    EditText edt_number_sesion;
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
                subjects.setKeySubject(dataSnapshot.getKey());

                subjectsList.add(0, subjects);
                subjectManageAdapter.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Subjects subjects = dataSnapshot.getValue(Subjects.class);
                subjects.setKeySubject(dataSnapshot.getKey());

                for (int i = 0; i < subjectsList.size(); i++) {
                    if (subjectsList.get(i).getKeySubject().equals(subjects.getKeySubject())) {
                        subjectsList.remove(i);
                        subjectsList.add(i, subjects);
                        subjectManageAdapter.notifyItemChanged(i);
                    }
                }
                subjectManageAdapter.notifyDataSetChanged();

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                Subjects subjects = dataSnapshot.getValue(Subjects.class);
                subjects.setKeySubject(dataSnapshot.getKey());

                for (int i = 0; i < subjectsList.size(); i++) {
                    if (subjectsList.get(i).getKeySubject().equals(subjects.getKeySubject())) {
                        subjectsList.remove(i);
                        subjectManageAdapter.notifyItemChanged(i);
                    }
                }
                subjectManageAdapter.notifyDataSetChanged();
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

    @OnClick(R.id.ibtn_back)
    public void onClickBack() {
        finish();
    }

    @OnClick(R.id.btn_create_subject)
    public void onClickCreateSubject() {
        final String id = edt_id_subject.getText().toString().trim();
        final String object = edt_name_subject.getText().toString().trim();
        final String numberSession = edt_number_sesion.getText().toString().trim();

        if (id.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập mã môn học !", Toast.LENGTH_LONG).show();
            edt_id_subject.requestFocus();
        } else if (object.isEmpty()){
            Toast.makeText(this, "Vui lòng nhập môn học !", Toast.LENGTH_LONG).show();
            edt_name_subject.requestFocus();
        } else if (numberSession.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập số buổi học !", Toast.LENGTH_SHORT).show();
        } else {
            final Subjects subjects = new Subjects(id, object, numberSession);

            databaseReference.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    if (dataSnapshot.child("subjects").exists()) {
                        if (dataSnapshot.child("subjects").child(id).exists()) {
                            Toast.makeText(SubjectManageActivity.this, "ID môn học đã tồn tại, vui lòng chọn id khác", Toast.LENGTH_LONG).show();
                        } else {
                            databaseReference.child("subjects").child(id).setValue(subjects, new DatabaseReference.CompletionListener() {
                                @Override
                                public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                                    if (databaseError == null) {
                                        Toast.makeText(SubjectManageActivity.this, "Tạo thành công !", Toast.LENGTH_LONG).show();
                                        edt_id_subject.setText("");
                                        edt_name_subject.setText("");
                                        edt_number_sesion.setText("");
                                    } else {
                                        Toast.makeText(SubjectManageActivity.this, "Tạo thất bại, thử lại !", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        }
                    } else {
                        databaseReference.child("subjects").child(id).setValue(subjects, new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                                if (databaseError == null) {
                                    Toast.makeText(SubjectManageActivity.this, "Tạo thành công !", Toast.LENGTH_LONG).show();
                                    edt_id_subject.setText("");
                                    edt_name_subject.setText("");
                                    edt_number_sesion.setText("");
                                } else {
                                    Toast.makeText(SubjectManageActivity.this, "Tạo thất bại, thử lại !", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    }
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
    }
}
