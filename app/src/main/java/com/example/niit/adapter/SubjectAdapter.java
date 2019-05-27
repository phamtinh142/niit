package com.example.niit.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.niit.R;
import com.example.niit.entities.Subjects;
import com.example.niit.listener.ChooseSubjectListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SubjectAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<Subjects> subjectsList;
    private ChooseSubjectListener chooseSubjectListener;

    public SubjectAdapter(Context context, List<Subjects> subjectsList, ChooseSubjectListener chooseSubjectListener) {
        this.context = context;
        this.subjectsList = subjectsList;
        this.chooseSubjectListener = chooseSubjectListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_class, viewGroup, false);
        return new SubjectViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        SubjectViewHolder holder = (SubjectViewHolder) viewHolder;

        final Subjects subjects = subjectsList.get(i);


        holder.txt_string.setText(subjects.getSubject());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseSubjectListener.onSubject(subjects);
            }
        });

    }

    @Override
    public int getItemCount() {
        if (subjectsList != null) return subjectsList.size();
        return 0;
    }

    class SubjectViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txt_string)
        TextView txt_string;

        SubjectViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
