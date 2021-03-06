package com.example.niit.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.niit.R;
import com.example.niit.Share.SharePrefer;
import com.example.niit.Share.StringFinal;
import com.example.niit.activity.MessageDetailActivity;
import com.example.niit.entities.CreateChatUID;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import hani.momanii.supernova_emoji_library.Helper.EmojiconTextView;

public class MessageListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<CreateChatUID> createChatUIDList;
    private DatabaseReference databaseReference;
    private String usernID;

    public MessageListAdapter(Context context, List<CreateChatUID> createChatUIDList, DatabaseReference databaseReference) {
        this.context = context;
        this.createChatUIDList = createChatUIDList;
        this.databaseReference = databaseReference;
        usernID = SharePrefer.getInstance().get(StringFinal.ID, String.class);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_item_message, viewGroup, false);
        return new MessageListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        final MessageListViewHolder holder = (MessageListViewHolder) viewHolder;

        final CreateChatUID createChatUID = createChatUIDList.get(i);

        List<CreateChatUID.memberUser> memberUserList = createChatUID.getMemberList();

        for (CreateChatUID.memberUser memberUser : memberUserList) {

            if (!memberUser.getId().equals(usernID)) {
                if (memberUser.getTypeAccount() == 0) {
                    databaseReference.child("manager").child(memberUser.getId()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.hasChild("avatar")) {
                                String avatar = dataSnapshot.child("avatar").getValue().toString();

                                if (avatar.equals("")) {
                                    holder.img_avatar_message.setImageDrawable(context.getResources().getDrawable(R.drawable.avatar_default));
                                } else {
                                    Picasso.get().load(avatar)
                                            .error(context.getResources().getDrawable(R.drawable.avatar_default))
                                            .into(holder.img_avatar_message);
                                }

                            } else {
                                holder.img_avatar_message.setImageDrawable(context.getResources().getDrawable(R.drawable.avatar_default));
                            }

                            String username = dataSnapshot.child("name").getValue().toString();

                            holder.txt_username_message.setText(username);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                } else if (memberUser.getTypeAccount() == 1) {
                    databaseReference.child("student").child(memberUser.getClasses()).child(memberUser.getId()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.hasChild("avatar")) {
                                String avatar = dataSnapshot.child("avatar").getValue().toString();

                                if (avatar.equals("")) {
                                    holder.img_avatar_message.setImageDrawable(context.getResources().getDrawable(R.drawable.avatar_default));
                                } else {
                                    Picasso.get().load(avatar)
                                            .error(context.getResources().getDrawable(R.drawable.avatar_default))
                                            .into(holder.img_avatar_message);
                                }

                            } else {
                                holder.img_avatar_message.setImageDrawable(context.getResources().getDrawable(R.drawable.avatar_default));
                            }

                            String username = dataSnapshot.child("name").getValue().toString();

                            holder.txt_username_message.setText(username);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }

        }

        if (createChatUID.getLastMessage() != null) {
            CreateChatUID.LastMessage lastMessage = createChatUID.getLastMessage();

            holder.txt_last_message_sent.setMaxLines(1);
            holder.txt_last_message_sent.setEllipsize(TextUtils.TruncateAt.END);
            if (lastMessage.getSentBy().equals(usernID)) {
                holder.txt_last_message_sent.setText("Bạn: " + lastMessage.getMessage());
            } else {
                holder.txt_last_message_sent.setText(lastMessage.getMessage());
            }

            if (lastMessage.getCreateTime() > 0) {
                long createtime = lastMessage.getCreateTime();
                Date date = new Date(createtime);
                DateFormat formatter = new SimpleDateFormat("HH:mm");
                formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
                String dateFormatted = formatter.format(date);

                holder.txt_time_create.setText(dateFormatted);
            }

        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MessageDetailActivity.class);
                intent.putExtra("chatID", createChatUID.getChatID());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        if (createChatUIDList != null) return createChatUIDList.size();
        return 0;
    }

    class MessageListViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_avatar_message)
        CircleImageView img_avatar_message;
        @BindView(R.id.txt_username_message)
        TextView txt_username_message;
        @BindView(R.id.txt_last_message_sent)
        EmojiconTextView txt_last_message_sent;
        @BindView(R.id.txt_time_create)
        TextView txt_time_create;

        MessageListViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
