package com.example.niit.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.Toast;

import com.example.niit.R;
import com.example.niit.Share.GetTimeSystem;
import com.example.niit.Share.SharePrefer;
import com.example.niit.Share.StringFinal;
import com.example.niit.adapter.ChatDetailAdapter;
import com.example.niit.entities.CreateChatUID;
import com.example.niit.entities.Chats;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MessageDetailActivity extends AppCompatActivity {
    @BindView(R.id.edt_message)
    EditText edt_message;
    @BindView(R.id.recyclerView_chat)
    RecyclerView recyclerView_chat;

    DatabaseReference databaseReference;

    List<Chats> chatsList;
    ChatDetailAdapter chatDetailAdapter;


    String chatID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_detail);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        chatID = intent.getStringExtra("chatID");
        init();

        getDataMessage();

    }

    private void getDataMessage() {
        databaseReference.child("chatMessages").child(chatID).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Chats chats = dataSnapshot.getValue(Chats.class);

                chatsList.add(chats);

                chatDetailAdapter.notifyDataSetChanged();

                recyclerView_chat.scrollToPosition(chatsList.size() - 1);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void init() {
        databaseReference = FirebaseDatabase.getInstance().getReference();

        chatsList = new ArrayList<>();
        recyclerView_chat.setHasFixedSize(true);
        recyclerView_chat.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        chatDetailAdapter = new ChatDetailAdapter(this, chatsList, databaseReference);
        recyclerView_chat.setAdapter(chatDetailAdapter);
    }

    @OnClick(R.id.btn_send_message)
    public void onClickSendMessage() {
        String message = edt_message.getText().toString();
        String userID = SharePrefer.getInstance().get(StringFinal.ID, String.class);
        int typeAccount = SharePrefer.getInstance().get(StringFinal.TYPE, Integer.class);
        long createTime = GetTimeSystem.getMili();
        if (!message.isEmpty()) {
            Chats chats = new Chats();

            if (typeAccount == 0) {
                chats.setTypeAccount(typeAccount);
                chats.setCreateTime(createTime);
                chats.setMessage(message);
                chats.setSentBy(userID);
            } else if (typeAccount == 1) {
                String classes = SharePrefer.getInstance().get(StringFinal.CLASSES, String.class);
                chats.setTypeAccount(typeAccount);
                chats.setCreateTime(createTime);
                chats.setMessage(message);
                chats.setSentBy(userID);
                chats.setClasses(classes);
            }



            databaseReference.child("chatMessages").child(chatID).push().setValue(chats, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                    if (databaseError != null) {
                        Toast.makeText(MessageDetailActivity.this, "Lỗi, vui lòng thử lại !", Toast.LENGTH_SHORT).show();
                    } else {
                        edt_message.setText("");
                    }
                }
            });

            CreateChatUID.LastMessage lastMessage = new CreateChatUID.LastMessage();
            lastMessage.setCreateTime(createTime);
            lastMessage.setMessage(message);
            lastMessage.setSentBy(userID);

            databaseReference.child("chats").child(chatID).child("lastMessage").setValue(lastMessage);

        }
    }
}
