package com.example.niit.Manager.activity.StudentManage.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.niit.Manager.activity.StudentManage.entites.Student;
import com.example.niit.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class StudentManageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<Student> studentList;

    public StudentManageAdapter(Context context, List<Student> studentList) {
        this.context = context;
        this.studentList = studentList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_list_student, viewGroup, false);
        return new StudentManageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        StudentManageViewHolder holder = (StudentManageViewHolder) viewHolder;

        Student student = studentList.get(i);

        holder.txt_stt_row_student.setText(String.valueOf(i++));
        holder.txt_name_row_student.setText(String.valueOf(student.getName()));

        if (student.getAvatar().equals("")) {
            holder.img_row_student.setImageDrawable(context.getResources().getDrawable(R.drawable.img_not_found));
        } else {
            Picasso.get().load(student.getAvatar())
                    .error(context.getResources().getDrawable(R.drawable.img_not_found))
                    .into(holder.img_row_student);
        }
    }

    @Override
    public int getItemCount() {
        if (studentList != null) return studentList.size();
        return 0;
    }

    class StudentManageViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txt_stt_row_student)
        TextView txt_stt_row_student;
        @BindView(R.id.img_row_student)
        CircleImageView img_row_student;
        @BindView(R.id.txt_name_row_student)
        TextView txt_name_row_student;

        public StudentManageViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
