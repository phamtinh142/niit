package com.example.niit.Student.fragment.Message.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.niit.R;
import com.example.niit.Share.SharePrefer;
import com.example.niit.Share.StringFinal;
import com.example.niit.Student.activity.MessageDetail.entites.CreateChatUID;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

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

        CreateChatUID createChatUID = createChatUIDList.get(i);

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

        holder.txt_last_message_sent.setText(createChatUID.getLastMessage());

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
        TextView txt_last_message_sent;

        MessageListViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
