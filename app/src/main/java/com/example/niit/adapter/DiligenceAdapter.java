package com.example.niit.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.niit.R;
import com.example.niit.entities.Diligence;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DiligenceAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<Diligence> diligenceList;
    private DatabaseReference databaseReference;

    public DiligenceAdapter(Context context, List<Diligence> diligenceList, DatabaseReference databaseReference) {
        this.context = context;
        this.diligenceList = diligenceList;
        this.databaseReference = databaseReference;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_diligence, viewGroup, false);
        return new DiligenceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        final DiligenceViewHolder holder = (DiligenceViewHolder) viewHolder;

        Diligence diligence = diligenceList.get(i);

        databaseReference.child("subjects").child(diligence.getIdSubject()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String subjectName = dataSnapshot.child("subject").getValue(String.class);

                holder.txt_subject_name.setText(subjectName);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        holder.txt_total_sesion.setText(diligence.getNumberSession());
        holder.txt_number_holiday.setText(diligence.getNumberHoliday());

        int numberHoliday = Integer.parseInt(diligence.getNumberHoliday());
        int numberTotalSession = Integer.parseInt(diligence.getNumberSession());
        float percent =  ((float) numberHoliday / (float) numberTotalSession) * 100;

        holder.txt_percent.setText(String.valueOf((int) percent));
    }

    @Override
    public int getItemCount() {
        if (diligenceList != null) return diligenceList.size();
        return 0;
    }

    class DiligenceViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txt_subject_name)
        TextView txt_subject_name;
        @BindView(R.id.txt_total_sesion)
        TextView txt_total_sesion;
        @BindView(R.id.txt_number_holiday)
        TextView txt_number_holiday;
        @BindView(R.id.txt_percent)
        TextView txt_percent;

        public DiligenceViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
