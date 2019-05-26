package com.example.niit.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.devs.readmoreoption.ReadMoreOption;
import com.example.niit.R;
import com.example.niit.Share.FormatTime;
import com.example.niit.Share.SharePrefer;
import com.example.niit.Share.StringFinal;
import com.example.niit.listener.ItemNewsListener;
import com.example.niit.entities.News;
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

public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<News> newsList;
    private ItemNewsListener itemNewsListener;
    private String idUser;
    private ReadMoreOption readMoreOption;

    public NewsAdapter(Context context, List<News> newsList, ItemNewsListener itemNewsListener) {
        this.context = context;
        this.newsList = newsList;
        this.itemNewsListener = itemNewsListener;
        idUser = SharePrefer.getInstance().get(StringFinal.ID, String.class);
        readMoreOption = new ReadMoreOption.Builder(context)
                .textLength(300)
                .moreLabel("Xem thêm")
                .lessLabel("Thu gọn")
                .moreLabelColor(context.getResources().getColor(R.color.color_text_default))
                .lessLabelColor(context.getResources().getColor(R.color.color_text_default))
                .labelUnderLine(true)
                .build();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.row_news, viewGroup, false);
        return new NewsManageViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

        final NewsManageViewHolder holder = (NewsManageViewHolder) viewHolder;


        holder.position = i;

        final News news = newsList.get(i);

        readMoreOption.addReadMoreTo(holder.txtContent, news.getContent_news());

        if (news.getType_account() == 0) {
            databaseReference.child("manager").child(news.getId()).addValueEventListener(
                    new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.hasChild("avatar")) {
                        String avatar = dataSnapshot.child("avatar").getValue().toString();

                        if (avatar.equals("")) {
                            holder.imgAvatar.setImageDrawable(context.getResources().getDrawable(R.drawable.avatar_default));
                        } else {
                            Picasso.get().load(avatar)
                                    .error(context.getResources().getDrawable(R.drawable.avatar_default))
                                    .into(holder.imgAvatar);
                        }
                    } else {
                        holder.imgAvatar.setImageDrawable(context.getResources().getDrawable(R.drawable.avatar_default));
                    }

                    String username = dataSnapshot.child("name").getValue().toString();

                    holder.txtUsername.setText(username);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        } else {
            databaseReference.child("student").child(news.getClassUser()).child(news.getId()).addValueEventListener(
                    new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.hasChild("avatar")) {
                        String avatar = dataSnapshot.child("avatar").getValue().toString();

                        if (avatar.equals("")) {
                            holder.imgAvatar.setImageDrawable(context.getResources().getDrawable(R.drawable.avatar_default));
                        } else {
                            Picasso.get().load(avatar)
                                    .error(context.getResources().getDrawable(R.drawable.avatar_default))
                                    .into(holder.imgAvatar);
                        }
                    } else {
                        holder.imgAvatar.setImageDrawable(context.getResources().getDrawable(R.drawable.avatar_default));
                    }

                    String username = dataSnapshot.child("name").getValue().toString();

                    holder.txtUsername.setText(username);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

        String date = FormatTime.formattedDate(news.getCreate_time());
        String time = FormatTime.formattedTimeMinute(news.getCreate_time());
        String createAtTime =  date + " lúc " + time;
        holder.txtDate.setText(createAtTime);

        if (!news.getImage_news().equals("")) {
            Picasso.get().load(news.getImage_news())
                    .error(context.getResources().getDrawable(R.drawable.img_not_found))
                    .into(holder.img_content_new);

            holder.img_content_new.setVisibility(View.VISIBLE);
        } else {
            holder.img_content_new.setVisibility(View.GONE);
        }


        holder.layout_content_news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemNewsListener.onClickDetailNews(holder.position);
            }
        });

        holder.btnComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemNewsListener.onClickDetailNews(holder.position);
            }
        });

        holder.btnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.btnLike.isChecked()) {
                    String userID = SharePrefer.getInstance().get(StringFinal.ID, String.class);
                    databaseReference.child("news").child(news.getClasses())
                            .child(news.getIdNews()).child("like")
                            .child(idUser).setValue(userID, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                            if (databaseError != null) {
                                holder.btnLike.setChecked(false);
                            }
                        }
                    });
                } else {
                    databaseReference.child("news").child(news.getClasses())
                            .child(news.getIdNews()).child("like")
                            .child(idUser).removeValue(new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                            if (databaseError != null) {
                                holder.btnLike.setChecked(true);
                            }
                        }
                    });
                }
            }
        });

        holder.btnLike.setChecked(false);


        if (news.getCountComment() > 0) {
            holder.layout_view_comment.setVisibility(View.VISIBLE);
            holder.txt_count_comment.setText(String.valueOf(news.getCountComment()));
        } else {
            holder.layout_view_comment.setVisibility(View.INVISIBLE);
        }

        if (news.getCountLike() > 0) {
            holder.layout_view_like.setVisibility(View.VISIBLE);

            if (news.getLikeList().contains(idUser)) {
                holder.btnLike.setChecked(true);

                holder.txt_you_like.setVisibility(View.VISIBLE);
                holder.txt_other_like.setVisibility(View.VISIBLE);
                holder.txt_count_like.setText(" " + (news.getCountLike() - 1));
            } else {
                holder.btnLike.setChecked(false);

                holder.txt_you_like.setVisibility(View.GONE);
                holder.txt_other_like.setVisibility(View.GONE);
                holder.txt_count_like.setText(" " + news.getCountLike());
            }

        } else {
            holder.layout_view_like.setVisibility(View.INVISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        if (newsList != null) return newsList.size();
        return 0;
    }

    class NewsManageViewHolder extends RecyclerView.ViewHolder {
        int position;
        @BindView(R.id.layout_content_news)
        LinearLayout layout_content_news;
        @BindView(R.id.img_avatar_row_user_news)
        CircleImageView imgAvatar;
        @BindView(R.id.txt_username_row_news)
        TextView txtUsername;
        @BindView(R.id.txt_time_row_news)
        TextView txtDate;
        @BindView(R.id.txt_content_row_news)
        TextView txtContent;
        @BindView(R.id.btn_like_row_news)
        CheckBox btnLike;
        @BindView(R.id.btn_comment_news)
        CheckBox btnComment;
        @BindView(R.id.img_content_new)
        ImageView img_content_new;
        @BindView(R.id.layout_view_like)
        LinearLayout layout_view_like;
        @BindView(R.id.txt_count_like)
        TextView txt_count_like;
        @BindView(R.id.layout_view_comment)
        LinearLayout layout_view_comment;
        @BindView(R.id.txt_count_comment)
        TextView txt_count_comment;
        @BindView(R.id.txt_you_like)
        TextView txt_you_like;
        @BindView(R.id.txt_other_like)
        TextView txt_other_like;

        NewsManageViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
