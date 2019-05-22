package com.example.niit.Student.fragment.Message.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.niit.R;
import com.example.niit.Student.activity.MessageDetail.entites.CreateChatUID;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class MessageListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<CreateChatUID> createChatUIDList;
    private DatabaseReference databaseReference;

    public MessageListAdapter(Context context, List<CreateChatUID> createChatUIDList, DatabaseReference databaseReference) {
        this.context = context;
        this.createChatUIDList = createChatUIDList;
        this.databaseReference = databaseReference;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_item_message, viewGroup, false);
        return new MessageListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        MessageListViewHolder holder = (MessageListViewHolder) viewHolder;

        CreateChatUID createChatUID = createChatUIDList.get(i);

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
