package com.example.niit.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.niit.R;
import com.example.niit.Share.SharePrefer;
import com.example.niit.Share.StringFinal;
import com.example.niit.entities.Chats;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class ChatDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<Chats> chatsList;
    private DatabaseReference databaseReference;
    private String userID;

    public ChatDetailAdapter(Context context, List<Chats> chatsList, DatabaseReference databaseReference) {
        this.context = context;
        this.chatsList = chatsList;
        this.databaseReference = databaseReference;
        userID = SharePrefer.getInstance().get(StringFinal.ID, String.class);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_frient_chats, viewGroup, false);
        return new ChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        final ChatViewHolder holder = (ChatViewHolder) viewHolder;

        final Chats chats = chatsList.get(i);

        if (userID.equals(chats.getSentBy())) {
            holder.layout_friend.setVisibility(View.GONE);
            holder.layout_user.setVisibility(View.VISIBLE);

            holder.txt_message_you.setText(chats.getMessage());

        } else {
            holder.layout_friend.setVisibility(View.VISIBLE);
            holder.layout_user.setVisibility(View.GONE);

            if (chats.getTypeAccount() == 0) {
                databaseReference.child("manager").child(chats.getSentBy()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChild("avatar")) {
                            String avatar = dataSnapshot.child("avatar").getValue().toString();

                            if (avatar.equals("")) {
                                holder.img_avata_message.setImageDrawable(context.getResources().getDrawable(R.drawable.avatar_default));
                            } else {
                                Picasso.get().load(avatar)
                                        .error(context.getResources().getDrawable(R.drawable.avatar_default))
                                        .into(holder.img_avata_message);
                            }

                        } else {
                            holder.img_avata_message.setImageDrawable(context.getResources().getDrawable(R.drawable.avatar_default));
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            } else if (chats.getTypeAccount() == 1) {
                databaseReference.child("student").child(chats.getClasses()).child(chats.getSentBy()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChild("avatar")) {
                            String avatar = dataSnapshot.child("avatar").getValue().toString();

                            if (avatar.equals("")) {
                                holder.img_avata_message.setImageDrawable(context.getResources().getDrawable(R.drawable.avatar_default));
                            } else {
                                Picasso.get().load(avatar)
                                        .error(context.getResources().getDrawable(R.drawable.avatar_default))
                                        .into(holder.img_avata_message);
                            }

                        } else {
                            holder.img_avata_message.setImageDrawable(context.getResources().getDrawable(R.drawable.avatar_default));
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            holder.txt_message_friend.setText(chats.getMessage());
        }

    }

    @Override
    public int getItemCount() {
        if (chatsList != null) return chatsList.size();
        return 0;
    }

    class ChatViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_avata_message)
        CircleImageView img_avata_message;
        @BindView(R.id.txt_message_friend)
        TextView txt_message_friend;
        @BindView(R.id.txt_message_you)
        TextView txt_message_you;
        @BindView(R.id.layout_friend)
        LinearLayout layout_friend;
        @BindView(R.id.layout_user)
        LinearLayout layout_user;

        ChatViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
