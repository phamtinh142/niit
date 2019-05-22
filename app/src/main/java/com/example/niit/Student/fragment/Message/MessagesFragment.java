package com.example.niit.Student.fragment.Message;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.niit.R;
import com.example.niit.Share.SharePrefer;
import com.example.niit.Share.StringFinal;
import com.example.niit.Student.activity.Contract.ContractActivity;
import com.example.niit.Student.activity.MessageDetail.entites.CreateChatUID;
import com.example.niit.Student.fragment.Message.adapter.MessageListAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MessagesFragment extends Fragment {
    @BindView(R.id.recyclerView_message)
    RecyclerView recyclerView_message;

    DatabaseReference databaseReference;
    List<CreateChatUID> createChatUIDList;
    MessageListAdapter messageListAdapter;

    String userID;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_messages, container, false);
        ButterKnife.bind(this, view);
        userID = SharePrefer.getInstance().get(StringFinal.ID, String.class);
        init();

        getListChatID();


        return view;
    }

    private void getListChatID() {

        databaseReference.child("userChats").child(userID).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String chatID = dataSnapshot.getValue(String.class);
                Log.d("khkhkh", "onChildAdded: " + chatID);
                getMessageList(chatID);
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

    private void getMessageList(String chatID) {
        databaseReference.child("chats").child(chatID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                CreateChatUID createChatUID = new CreateChatUID();

                String lastMessage = dataSnapshot.child("lastMessage").getValue(String.class);

                List<CreateChatUID.memberUser> memberUserList = new ArrayList<>();

                for(DataSnapshot userSnapshot : dataSnapshot.child("memberList").getChildren()) {
                    String userID = userSnapshot.child("id").getValue(String.class);
                    String classes = userSnapshot.child("classes").getValue(String.class);
                    int typeAccount = userSnapshot.child("typeAccount").getValue(Integer.class);

                    CreateChatUID.memberUser memberUser = new CreateChatUID.memberUser(userID, typeAccount, classes);
                    memberUserList.add(memberUser);
                }

                createChatUID.setLastMessage(lastMessage);

                createChatUID.setMemberList(memberUserList);

                createChatUIDList.add(createChatUID);

                messageListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void init() {
        databaseReference = FirebaseDatabase.getInstance().getReference();

        createChatUIDList = new ArrayList<>();
        recyclerView_message.setHasFixedSize(true);
        recyclerView_message.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        messageListAdapter = new MessageListAdapter(getActivity(), createChatUIDList, databaseReference);
        recyclerView_message.setAdapter(messageListAdapter);

    }

    @OnClick(R.id.floating_button)
    public void onClickAddMessage() {
        Intent intent = new Intent(getActivity(), ContractActivity.class);
        startActivity(intent);
    }

}
