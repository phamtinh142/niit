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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.niit.R;
import com.example.niit.adapter.DiligenceAdapter;
import com.example.niit.adapter.SubjectAdapter;
import com.example.niit.entities.Diligence;
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
import butterknife.OnClick;

public class DiligenceManageActivity extends AppCompatActivity implements ChooseSubjectListener {
    @BindView(R.id.btn_choose_subject)
    CheckBox btn_choose_subject;
    @BindView(R.id.recyclerView_subject)
    RecyclerView recyclerView_subject;
    @BindView(R.id.layout_subject)
    LinearLayout layout_subject;
    @BindView(R.id.txt_total_sesion)
    TextView txt_total_sesion;
    @BindView(R.id.edt_number_holiday)
    EditText edt_number_holiday;
    @BindView(R.id.recyclerView_diligence)
    RecyclerView recyclerView_diligence;

    List<Subjects> subjectsList;
    SubjectAdapter subjectAdapter;

    List<Diligence> diligenceList;
    DiligenceAdapter diligenceAdapter;

    String idSubject = "";

    DatabaseReference databaseReference;

    String classUser;
    String idUser;
    int typeAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diligence_manage);
        ButterKnife.bind(this);
        getDataBundle();
        init();
        getSubject();
        getDiligence();
    }

    private void getDiligence() {
        databaseReference.child("diligence").child(classUser).child(idUser).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Diligence diligence = dataSnapshot.getValue(Diligence.class);

                diligenceList.add(diligence);

                diligenceAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Diligence diligence = dataSnapshot.getValue(Diligence.class);

                for (int i = 0; i < diligenceList.size(); i++) {
                    if (diligenceList.get(i).getIdSubject().equals(diligence.getIdSubject())) {
                        diligenceList.remove(i);
                        diligenceList.add(i, diligence);
                        diligenceAdapter.notifyItemChanged(i);
                    }
                }
                diligenceAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                Diligence diligence = dataSnapshot.getValue(Diligence.class);

                for (int i = 0; i < diligenceList.size(); i++) {
                    if (diligenceList.get(i).getIdSubject().equals(diligence.getIdSubject())) {
                        diligenceList.remove(i);
                        diligenceAdapter.notifyItemChanged(i);
                    }
                }
                diligenceAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void getDataBundle() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            classUser = bundle.getString("classUser", "");
            idUser = bundle.getString("idUser", "");
            typeAccount = bundle.getInt("typeAccount", 1);
        }
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

        diligenceList = new ArrayList<>();
        recyclerView_diligence.setHasFixedSize(true);
        recyclerView_diligence.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        diligenceAdapter = new DiligenceAdapter(this, diligenceList, databaseReference);
        recyclerView_diligence.setAdapter(diligenceAdapter);
    }

    @Override
    public void onSubject(Subjects subjects) {
        this.idSubject = subjects.getId();
        btn_choose_subject.setText(subjects.getSubject());
        txt_total_sesion.setText(subjects.getNumberSession());
        btn_choose_subject.setChecked(false);
        layout_subject.setVisibility(View.GONE);
    }

    @OnCheckedChanged(R.id.btn_choose_subject)
    public void onCheckedSubject(boolean checked) {
        if (checked) {
            layout_subject.setVisibility(View.VISIBLE);
        } else {
            layout_subject.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.ibtn_back)
    public void onClickBack() {
        finish();
    }

    @OnClick(R.id.btn_create)
    public void onClickCreate() {
        String numberHoliday = edt_number_holiday.getText().toString().trim();
        String numberTotalSession = txt_total_sesion.getText().toString().trim();
        if (numberHoliday.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập số buổi nghỉ", Toast.LENGTH_SHORT).show();
            edt_number_holiday.requestFocus();
        } else {
            Diligence diligence = new Diligence();
            diligence.setIdSubject(idSubject);
            diligence.setNumberHoliday(numberHoliday);
            diligence.setNumberSession(numberTotalSession);
            databaseReference.child("diligence").child(classUser).child(idUser).child(idSubject).setValue(diligence, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                    if (databaseError == null) {
                        Toast.makeText(DiligenceManageActivity.this, "Tạo thành công", Toast.LENGTH_SHORT).show();
                        edt_number_holiday.setText("");
                    } else {
                        Toast.makeText(DiligenceManageActivity.this, "Tạo thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }
}
