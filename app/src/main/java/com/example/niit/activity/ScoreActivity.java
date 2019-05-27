package com.example.niit.activity;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.niit.R;
import com.example.niit.Share.SharePrefer;
import com.example.niit.Share.StringFinal;
import com.example.niit.adapter.ScoreAdapter;
import com.example.niit.entities.Score;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ScoreActivity extends AppCompatActivity {
    @BindView(R.id.recyclerView_score)
    RecyclerView recyclerView_score;

    DatabaseReference databaseReference;

    List<Score> scoreList;
    ScoreAdapter scoreAdapter;

    String classUser;
    String idUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        ButterKnife.bind(this);
        classUser = SharePrefer.getInstance().get(StringFinal.CLASSES, String.class);
        idUser = SharePrefer.getInstance().get(StringFinal.ID,String.class);
        init();
        getScore();
    }

    private void getScore() {
        databaseReference.child("score").child(classUser).child(idUser).addChildEventListener(new ChildEventListener() {
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

    private void init() {
        databaseReference = FirebaseDatabase.getInstance().getReference();

        scoreList = new ArrayList<>();
        recyclerView_score.setHasFixedSize(true);
        recyclerView_score.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));;
        scoreAdapter = new ScoreAdapter(this, scoreList, databaseReference);
        recyclerView_score.setAdapter(scoreAdapter);
    }
}
