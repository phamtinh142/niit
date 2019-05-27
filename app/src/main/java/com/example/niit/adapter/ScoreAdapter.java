package com.example.niit.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.niit.R;
import com.example.niit.entities.Score;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ScoreAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<Score> scoreList;
    private DatabaseReference databaseReference;

    public ScoreAdapter(Context context, List<Score> scoreList, DatabaseReference databaseReference) {
        this.context = context;
        this.scoreList = scoreList;
        this.databaseReference = databaseReference;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_score, viewGroup, false);
        return new ScoreViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        final ScoreViewholder holder = (ScoreViewholder) viewHolder;

        Score score = scoreList.get(i);

        databaseReference.child("subjects").child(score.getIdSubject()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String subjectName = dataSnapshot.child("subject").getValue(String.class);

                holder.txt_subject_name.setText(subjectName);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        holder.txt_praticing_score.setText(score.getPracticingScore());
        holder.txt_theory_score.setText(score.getTheoryScore());
    }

    @Override
    public int getItemCount() {
        if (scoreList != null) return scoreList.size();
        return 0;
    }

    class ScoreViewholder extends RecyclerView.ViewHolder {
        @BindView(R.id.txt_subject_name)
        TextView txt_subject_name;
        @BindView(R.id.txt_praticing_score)
        TextView txt_praticing_score;
        @BindView(R.id.txt_theory_score)
        TextView txt_theory_score;

        public ScoreViewholder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
