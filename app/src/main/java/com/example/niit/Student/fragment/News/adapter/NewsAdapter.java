package com.example.niit.Student.fragment.News.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.niit.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class NewsViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_avatar_row_user_news)
        CircleImageView imgAvatar;
        @BindView(R.id.txt_username_row_news)
        TextView txtUsername;
        @BindView(R.id.txt_time_row_news)
        TextView txtDate;
        @BindView(R.id.txt_content_row_news)
        TextView txtContent;
        @BindView(R.id.txt_like_row_news)
        TextView txtLike;
        @BindView(R.id.img_icon_like_row_news)
        ImageView icLike;
        @BindView(R.id.btn_like_row_news)
        LinearLayout btnLike;
        @BindView(R.id.btn_comment_news)
        LinearLayout btnComment;
        @BindView(R.id.img_content_new)
        ImageView img_content_new;

        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
