package com.example.niit.Student.fragment.News.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.niit.R;
import com.example.niit.Share.FormatTime;
import com.example.niit.Student.activity.CommentNews.NewsDetailActivity;
import com.example.niit.Student.fragment.News.dialog.entities.News;
import com.google.firebase.database.DatabaseReference;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<News> newsList;
    private DatabaseReference databaseReference;
    public NewsAdapter(Context context, List<News> newsList, DatabaseReference databaseReference) {
        this.context = context;
        this.newsList = newsList;
        this.databaseReference = databaseReference;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_news, viewGroup, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        NewsViewHolder holder = (NewsViewHolder) viewHolder;

        final News news = newsList.get(i);

        holder.txtUsername.setText(news.getUserName());
        Picasso.get().load(news.getAvatarUsername())
                .error(context.getResources().getDrawable(R.drawable.img_not_found))
                .into(holder.imgAvatar);

        String date = FormatTime.formattedDate(news.getCreateTime());
        String time = FormatTime.formattedTimeMinute(news.getCreateTime());
        String createAtTime = "Đăng vào lúc " + time + " " + date;
        holder.txtDate.setText(createAtTime);

        if (!news.getImageNews().equals("")) {
            Picasso.get().load(news.getImageNews())
                    .error(context.getResources().getDrawable(R.drawable.img_not_found))
                    .into(holder.img_content_new);

            holder.img_content_new.setVisibility(View.VISIBLE);
        } else {
            holder.img_content_new.setVisibility(View.GONE);
        }
        holder.txtContent.setText(news.getContentNews());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, NewsDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("idNews", news.getIdNews());
                bundle.putString("idUser", news.getId());
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });

        holder.btnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Đã thích", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        if (newsList != null) return newsList.size();
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
