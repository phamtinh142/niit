package com.example.niit.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.niit.R;
import com.example.niit.Share.GetTimeSystem;
import com.example.niit.Share.SharePrefer;
import com.example.niit.Share.StringFinal;
import com.example.niit.adapter.ChatDetailAdapter;
import com.example.niit.entities.CreateChatUID;
import com.example.niit.entities.Chats;
import com.example.niit.entities.CreatedStudent;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import hani.momanii.supernova_emoji_library.Actions.EmojIconActions;
import hani.momanii.supernova_emoji_library.Helper.EmojiconEditText;

public class MessageDetailActivity extends AppCompatActivity {
    @BindView(R.id.edt_message)
    EmojiconEditText edt_message;
    @BindView(R.id.recyclerView_chat)
    RecyclerView recyclerView_chat;
    @BindView(R.id.btn_open_icon)
    ImageView btn_open_icon;
    @BindView(R.id.img_avatar_frient)
    CircleImageView img_avatar_frient;
    @BindView(R.id.txt_name_friend)
    TextView txt_name_friend;

    EmojIconActions emojIconActions;

    DatabaseReference databaseReference;

    List<Chats> chatsList;
    ChatDetailAdapter chatDetailAdapter;

    String userID;
    int typeAccount;

    String chatID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_detail);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        chatID = intent.getStringExtra("chatID");
        userID = SharePrefer.getInstance().get(StringFinal.ID, String.class);
        typeAccount = SharePrefer.getInstance().get(StringFinal.TYPE, Integer.class);
        init();

        getFriend();

        getDataMessage();

    }

    private void getFriend() {
        databaseReference.child("chats").child(chatID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot userSnapshot : dataSnapshot.child("memberList").getChildren()) {
                    String idUserChat = userSnapshot.child("id").getValue(String.class);
                    if (!userID.equals(idUserChat)) {
                        String classes = userSnapshot.child("classes").getValue(String.class);
                        int typeAccount = userSnapshot.child("typeAccount").getValue(Integer.class);
                        getDetailFrient(idUserChat, classes, typeAccount);
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void getDetailFrient(String idUserChat, String classes, int typeAccount) {

        if (typeAccount == 0) {
            databaseReference.child("manager").child(idUserChat).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.hasChild("avatar")) {
                        String avatar = dataSnapshot.child("avatar").getValue(String.class);
                        if (avatar.equals("")) {
                            img_avatar_frient.setImageDrawable(getResources().getDrawable(R.drawable.avatar_default));
                        } else {
                            Picasso.get().load(avatar)
                                    .error(getResources().getDrawable(R.drawable.avatar_default))
                                    .into(img_avatar_frient);
                        }
                    } else {
                        img_avatar_frient.setImageDrawable(getResources().getDrawable(R.drawable.avatar_default));
                    }

                    String userName = dataSnapshot.child("name").getValue(String.class);
                    txt_name_friend.setText(userName);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        } else if (typeAccount == 1) {
            databaseReference.child("student").child(classes).child(idUserChat).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.hasChild("avatar")) {
                        String avatar = dataSnapshot.child("avatar").getValue(String.class);
                        if (avatar.equals("")) {
                            img_avatar_frient.setImageDrawable(getResources().getDrawable(R.drawable.avatar_default));
                        } else {
                            Picasso.get().load(avatar)
                                    .error(getResources().getDrawable(R.drawable.avatar_default))
                                    .into(img_avatar_frient);
                        }
                    } else {
                        img_avatar_frient.setImageDrawable(getResources().getDrawable(R.drawable.avatar_default));
                    }

                    String userName = dataSnapshot.child("name").getValue(String.class);
                    txt_name_friend.setText(userName);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }

    private void getDataMessage() {
        databaseReference.child("chatMessages").child(chatID).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Chats chats = dataSnapshot.getValue(Chats.class);

                chatsList.add(0, chats);

                chatDetailAdapter.notifyDataSetChanged();

                recyclerView_chat.scrollToPosition(0);
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

        emojIconActions = new EmojIconActions(this, findViewById(R.id.layout_detail_message), edt_message, btn_open_icon);
        emojIconActions.ShowEmojIcon();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        linearLayoutManager.setReverseLayout(true);
        chatsList = new ArrayList<>();
        recyclerView_chat.setHasFixedSize(true);
        recyclerView_chat.setLayoutManager(linearLayoutManager);
        chatDetailAdapter = new ChatDetailAdapter(this, chatsList, databaseReference);
        recyclerView_chat.setAdapter(chatDetailAdapter);
    }

    @OnClick(R.id.btn_back)
    public void onClickBackMessageDetail() {
        finish();
    }

    @OnClick(R.id.btn_open_icon)
    public void onClickIcon() {

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
