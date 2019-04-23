package com.example.niit.Student.activity.CommentNews;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.niit.R;
import com.example.niit.Share.GetTimeSystem;
import com.example.niit.Share.TimestampUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class CommentNewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<SendComment> commentList;

    public CommentNewsAdapter(Context context, List<SendComment> commentList) {
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
        CommentNewsViewHolder holder = (CommentNewsViewHolder) viewHolder;

        SendComment comment = commentList.get(i);

        holder.txtUsername.setText(comment.getUsername());
        holder.txtContent.setText(comment.getContent());

        String time;
        long timeBegin = comment.getCreateAtTime();
        long timeNow = GetTimeSystem.getMili();
        long timeHeader = (timeNow -timeBegin)/60000;
        if (timeHeader > 180){
            time = timeHeader/60 + " giờ trước";
        } else if (timeHeader >= 60){
            time = timeHeader/60 + " giờ " + timeHeader%60 + " phút trước";
        }else if (timeHeader >= 0){
            time = timeHeader%60 + " phút trước";
        } else {
            time = "Vừa xong";
        }
        holder.txtCreateAtTime.setText(time);

        Picasso.get().load(comment.getAvatar())
                .error(context.getResources().getDrawable(R.drawable.img_not_found))
                .into(holder.imgAvata);

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
        public CommentNewsViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
