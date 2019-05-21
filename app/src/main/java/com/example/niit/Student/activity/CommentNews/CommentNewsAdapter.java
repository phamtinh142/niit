package com.example.niit.Student.activity.CommentNews;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.niit.Manager.activity.CreateStudent.entities.CreatedStudent;
import com.example.niit.R;
import com.example.niit.Share.GetTimeSystem;
import com.example.niit.Share.TimestampUtils;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class CommentNewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<SendComment> commentList;

    CommentNewsAdapter(Context context, List<SendComment> commentList) {
        this.context = context;
        this.commentList = commentList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_comment_news, viewGroup, false);
        return new CommentNewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        final CommentNewsViewHolder holder = (CommentNewsViewHolder) viewHolder;

        final SendComment comment = commentList.get(i);

        if (comment.getType_account() == 0) {
            databaseReference.child("manager").child(comment.getId()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.hasChild("avatar")) {
                                String avatar = dataSnapshot.child("avatar").getValue().toString();

                                if (avatar.equals("")) {
                                    holder.imgAvata.setImageDrawable(context.getResources().getDrawable(R.drawable.avatar_default));
                                } else {
                                    Picasso.get().load(avatar)
                                            .error(context.getResources().getDrawable(R.drawable.avatar_default))
                                            .into(holder.imgAvata);
                                }

                            } else {
                                holder.imgAvata.setImageDrawable(context.getResources().getDrawable(R.drawable.avatar_default));
                            }

                            String username = dataSnapshot.child("name").getValue().toString();

                            holder.txtUsername.setText(username);

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

        } else {
            databaseReference.child("student").child(comment.getClasses()).child(comment.getId()).addValueEventListener(
                    new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.hasChild("avatar")) {
                                String avatar = dataSnapshot.child("avatar").getValue().toString();

                                if (avatar.equals("")) {
                                    holder.imgAvata.setImageDrawable(context.getResources().getDrawable(R.drawable.avatar_default));
                                } else {
                                    Picasso.get().load(avatar)
                                            .error(context.getResources().getDrawable(R.drawable.avatar_default))
                                            .into(holder.imgAvata);
                                }

                            } else {
                                holder.imgAvata.setImageDrawable(context.getResources().getDrawable(R.drawable.avatar_default));
                            }

                            String username = dataSnapshot.child("name").getValue().toString();

                            holder.txtUsername.setText(username);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
        }

        holder.txtContent.setText(comment.getContent());

        String time;
        long timeBegin = comment.getCreateAtTime();
        long timeNow = GetTimeSystem.getMili();
        long timeHeader = (timeNow -timeBegin) / 60000;
        if (timeHeader > 24 * 60) {
            time = (timeHeader / (24 * 60)) + " ngày trước";
        } else if (timeHeader >= 60){
            time = timeHeader / 60 + " giờ " + timeHeader % 60 + " phút trước";
        } else if (timeHeader > 0){
            time = timeHeader % 60 + " phút trước";
        } else {
            time = "Vừa xong";
        }
        holder.txtCreateAtTime.setText(time);

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        if (commentList != null) return commentList.size();
        return 0;
    }

    class CommentNewsViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_avata_user_comment_news)
        CircleImageView imgAvata;
        @BindView(R.id.txt_username_comment_news)
        TextView txtUsername;
        @BindView(R.id.txt_content_comment_news)
        TextView txtContent;
        @BindView(R.id.txt_create_at_time_comment_news)
        TextView txtCreateAtTime;
        CommentNewsViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
