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
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.niit.R;
import com.example.niit.adapter.ScoreAdapter;
import com.example.niit.adapter.SubjectAdapter;
import com.example.niit.entities.Score;
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
    @BindView(R.id.recyclerView_score)
    RecyclerView recyclerView_score;

    List<Subjects> subjectsList;
    SubjectAdapter subjectAdapter;

    List<Score> scoreList;
    ScoreAdapter scoreAdapter;

    String classesUser;
    String idUser;

    String idSubject = "";

    String testAgain = "1";

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_manage);
        ButterKnife.bind(this);
        getDataBundle();
        init();

        getSubject();

        getScore();
    }

    private void getScore() {
        databaseReference.child("score").child(classesUser).child(idUser).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Score score = dataSnapshot.getValue(Score.class);

                Log.d("KTscore", "-------------------------------------");
                Log.d("KTscore", "getIdSubject: " + score.getIdSubject());
                Log.d("KTscore", "getPracticingScore: " + score.getPracticingScore());
                Log.d("KTscore", "getTheoryScore: " + score.getTheoryScore());

                scoreList.add(score);

                scoreAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Score score = dataSnapshot.getValue(Score.class);

                for (int i = 0; i < scoreList.size(); i++) {
                    if (scoreList.get(i).getIdSubject().equals(score.getIdSubject())) {
                        scoreList.remove(i);
                        scoreList.add(i, score);
                        scoreAdapter.notifyItemChanged(i);
                    }
                }
                scoreAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                Score score = dataSnapshot.getValue(Score.class);

                for (int i = 0; i < scoreList.size(); i++) {
                    if (scoreList.get(i).getIdSubject().equals(score.getIdSubject())) {
                        scoreList.remove(i);
                        scoreAdapter.notifyItemChanged(i);
                    }
                }
                scoreAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
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

        scoreList = new ArrayList<>();
        recyclerView_score.setHasFixedSize(true);
        recyclerView_score.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        scoreAdapter = new ScoreAdapter(this, scoreList, databaseReference);
        recyclerView_score.setAdapter(scoreAdapter);
    }

    private void getDataBundle() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            classesUser = bundle.getString("classes");
            idUser = bundle.getString("userID");
        }
    }

    @OnClick(R.id.ibtn_back)
    public void onClickBack() {
        finish();
    }

    @OnCheckedChanged({R.id.ckb_again_1, R.id.ckb_again_2, R.id.ckb_again_3})
    public void onCheckTestAgain(CompoundButton button, boolean checked) {
        if (checked) {
            switch (button.getId()) {
                case R.id.ckb_again_1:
                    testAgain = "1";
                    break;
                case R.id.ckb_again_2:
                    testAgain = "2";
                    break;
                case R.id.ckb_again_3:
                    testAgain = "3";
                    break;
            }
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
    public void onSubject(Subjects subjects) {
        this.idSubject = subjects.getId();
        btn_choose_subject.setText(subjects.getSubject());
        btn_choose_subject.setChecked(false);
        layout_subject.setVisibility(View.GONE);
    }

    @OnClick(R.id.btn_create_subject)
    public void onClickCreateSubject() {
        final String practicing = edt_practicing_score.getText().toString().trim();
        final String theory = edt_theory_score.getText().toString().trim();

        if (idSubject.isEmpty()) {
            Toast.makeText(this, "Vui lòng chọn môn học !", Toast.LENGTH_SHORT).show();
            layout_subject.setVisibility(View.VISIBLE);
        } else {
            Score score = new Score();
            score.setIdSubject(idSubject);
            if (practicing.isEmpty()) {
                score.setPracticingScore("Trống");
            } else {
                score.setPracticingScore(practicing);
            }

            if (theory.isEmpty()) {
                score.setTheoryScore("Trống");
            } else {
                score.setTheoryScore(theory);
            }

            score.setTestAgain(testAgain);

            databaseReference.child("score").child(classesUser).child(idUser).child(idSubject + testAgain).setValue(score, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                    if (databaseError == null) {
                        Toast.makeText(ScoreManageActivity.this, "Tạo thành công !", Toast.LENGTH_SHORT).show();
                        edt_practicing_score.setText("");
                        edt_theory_score.setText("");
                    } else {
                        Toast.makeText(ScoreManageActivity.this, "Tạo thất bại !", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
