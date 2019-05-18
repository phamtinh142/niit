package com.example.niit.Manager.activity.StudentManage.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.niit.Manager.activity.CreateStudent.entities.CreatedStudent;
import com.example.niit.Manager.activity.InfoStudent.InfoStudentActivity;
import com.example.niit.Manager.activity.StudentManage.entites.Student;
import com.example.niit.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class StudentManageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<CreatedStudent> createdStudentList;

    public StudentManageAdapter(Context context, List<CreatedStudent> createdStudentList) {
        this.context = context;
        this.createdStudentList = createdStudentList;
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

        final CreatedStudent createdStudent = createdStudentList.get(i);

        holder.txt_stt_row_student.setText(String.valueOf(i++));
        holder.txt_name_row_student.setText(String.valueOf(createdStudent.getName()));

        if (createdStudent.getAvatar().isEmpty()) {
            holder.img_row_student.setImageDrawable(context.getResources().getDrawable(R.drawable.avatar_default));
        } else {
            Picasso.get().load(createdStudent.getAvatar())
                    .error(context.getResources().getDrawable(R.drawable.img_not_found))
                    .into(holder.img_row_student);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, InfoStudentActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("id", createdStudent.getId());
                bundle.putString("class", createdStudent.getClassUser());
                bundle.putInt("type_account", createdStudent.getType_account());
                bundle.putString("name", createdStudent.getName());
                bundle.putString("avatar", createdStudent.getAvatar());
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (createdStudentList != null) return createdStudentList.size();
        return 0;
    }

    class StudentManageViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txt_stt_row_student)
        TextView txt_stt_row_student;
        @BindView(R.id.img_row_student)
        CircleImageView img_row_student;
        @BindView(R.id.txt_name_row_student)
        TextView txt_name_row_student;

        StudentManageViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
