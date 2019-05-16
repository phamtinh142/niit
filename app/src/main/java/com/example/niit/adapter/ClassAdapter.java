package com.example.niit.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.niit.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ClassAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<String> stringList;
    private ClassesListener classesListener;

    public ClassAdapter(Context context, List<String> stringList, ClassesListener classesListener) {
        this.context = context;
        this.stringList = stringList;
        this.classesListener = classesListener;
    }

    public interface ClassesListener {
        void onClickItemClasses(String classes);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.row_class, viewGroup, false);
        return new ClassViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ClassViewHolder holder = (ClassViewHolder) viewHolder;

        final String classes = stringList.get(i);

        Log.d("ktClasses", "adapter: " + stringList.get(i));

        if (classes.equals("ALL")) {
            holder.txt_class.setText("Chung");
        } else {
            holder.txt_class.setText(classes);
        }



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (classes.equals("ALL")) {
                    classesListener.onClickItemClasses("Chung");
                } else {
                    classesListener.onClickItemClasses(classes);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (stringList != null) return stringList.size();
        return 0;
    }

    class ClassViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txt_class)
        TextView txt_class;

        ClassViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
