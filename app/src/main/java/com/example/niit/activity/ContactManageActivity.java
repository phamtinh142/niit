package com.example.niit.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.niit.entities.CreatedStudent;
import com.example.niit.adapter.StudentManageAdapter;
import com.example.niit.R;
import com.example.niit.Share.GetTimeSystem;
import com.example.niit.Share.SharePrefer;
import com.example.niit.Share.StringFinal;
import com.example.niit.entities.CreateChatUID;
import com.example.niit.adapter.ClassAdapter;
import com.example.niit.listener.AccountListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;

public class ContactManageActivity extends AppCompatActivity implements ClassAdapter.ClassesListener,
        AccountListener, TextWatcher {
    @BindView(R.id.ckb_choose_classes)
    CheckBox ckb_choose_classes;
    @BindView(R.id.layout_choose_classes)
    LinearLayout layout_choose_classes;
    @BindView(R.id.recyclerView_class)
    RecyclerView recyclerView_class;
    @BindView(R.id.recyclerView_account)
    RecyclerView recyclerView_account;
    @BindView(R.id.edt_search_name)
    EditText edt_search_name;

    List<String> classesList;
    ClassAdapter classAdapter;

    List<CreatedStudent> accountList;
    List<CreatedStudent> searchAccountList;
    StudentManageAdapter studentManageAdapter;

    String classes;
    String userID;

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_manage);
        ButterKnife.bind(this);
        init();

        userID = SharePrefer.getInstance().get(StringFinal.ID, String.class);
        classes = ckb_choose_classes.getText().toString().trim();

        getClasses();

        edt_search_name.addTextChangedListener(this);
    }

    private void getManager() {
        databaseReference.child("manager").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                CreatedStudent createdStudent = dataSnapshot.getValue(CreatedStudent.class);

                if (!createdStudent.getId().equals(userID)) {
                    accountList.add(createdStudent);
                    studentManageAdapter.notifyDataSetChanged();
                }
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

    private void getStudent() {
        databaseReference.child("student").child(classes).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                CreatedStudent createdStudent = dataSnapshot.getValue(CreatedStudent.class);

                accountList.add(createdStudent);
                studentManageAdapter.notifyDataSetChanged();

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

    private void getClasses() {
        databaseReference.child("classes").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String classes = dataSnapshot.getValue(String.class);

                Log.d("adadada", "onChildAdded: " + classes);

                if (!classes.equals("ALL")) {
                    classesList.add(classes);
                    classAdapter.notifyDataSetChanged();
                }
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
        classesList = new ArrayList<>();
        classesList.add(0, "Manager");
        recyclerView_class.setHasFixedSize(true);
        recyclerView_class.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        classAdapter = new ClassAdapter(this, classesList, this);
        recyclerView_class.setAdapter(classAdapter);

        searchAccountList = new ArrayList<>();
        accountList = new ArrayList<>();
        recyclerView_account.setHasFixedSize(true);
        recyclerView_account.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        studentManageAdapter = new StudentManageAdapter(this, accountList, this);
        recyclerView_account.setAdapter(studentManageAdapter);

    }

    @OnCheckedChanged(R.id.ckb_choose_classes)
    public void onCheckedChooseClasses(boolean checked) {
        if (checked) {
            layout_choose_classes.setVisibility(View.VISIBLE);
        } else {
            layout_choose_classes.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClickItemClasses(String classes) {
        ckb_choose_classes.setText(classes);
        ckb_choose_classes.setChecked(false);
        layout_choose_classes.setVisibility(View.GONE);
        this.classes = classes;

        searchAccountList.clear();
        accountList.clear();
        if (this.classes.equals("Manager")) {
            getManager();
        } else {
            getStudent();
        }
    }

    @Override
    public void onClickAccount(int position) {
        int typeAccount = SharePrefer.getInstance().get(StringFinal.TYPE, Integer.class);

        final long time = GetTimeSystem.getMili();
        final String friendID = accountList.get(position).getId();

        CreateChatUID.memberUser userMember = null;
        if (typeAccount == 0) {
            userMember = new CreateChatUID.memberUser(userID, typeAccount);
        } else if (typeAccount == 1) {
            String classes = SharePrefer.getInstance().get(StringFinal.CLASSES, String.class);
            userMember = new CreateChatUID.memberUser(userID, typeAccount, classes);
        }

        CreateChatUID.memberUser friendMember = null;
        if (accountList.get(position).getType_account() == 0) {
            friendMember = new CreateChatUID.memberUser(
                    accountList.get(position).getId(),
                    accountList.get(position).getType_account());
        } else if (accountList.get(position).getType_account() == 1) {
            friendMember = new CreateChatUID.memberUser(
                    accountList.get(position).getId(),
                    accountList.get(position).getType_account(),
                    accountList.get(position).getClassUser());
        }


        List<CreateChatUID.memberUser> memberList = new ArrayList<>();
        memberList.add(userMember);
        memberList.add(friendMember);

        CreateChatUID.LastMessage lastMessage = new CreateChatUID.LastMessage();
        lastMessage.setMessage("");
        lastMessage.setCreateTime(0);
        lastMessage.setSentBy("");


        CreateChatUID createChatUID = new CreateChatUID();
        createChatUID.setMemberList(memberList);
        createChatUID.setLastMessage(lastMessage);

        databaseReference.child("chats").child(String.valueOf(time)).setValue(createChatUID);
        databaseReference.child("userChats").child(userID).push().setValue(String.valueOf(time));
        databaseReference.child("userChats").child(friendID).push().setValue(String.valueOf(time));

        Intent intent = new Intent(this, MessageDetailActivity.class);
        intent.putExtra("chatID", String.valueOf(time));
        startActivity(intent);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getManager();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        s.toString().toLowerCase();
        searchAccountList.clear();


        for (CreatedStudent createdStudent : accountList) {
            if (createdStudent.getName().toLowerCase().contains(s.toString())) {
                searchAccountList.add(createdStudent);
            }
        }

        studentManageAdapter = new StudentManageAdapter(ContactManageActivity.this, searchAccountList, this);

        recyclerView_account.setAdapter(studentManageAdapter);
        if (s.toString().equals("")){
            studentManageAdapter = new StudentManageAdapter(ContactManageActivity.this, accountList, this);
            recyclerView_account.setAdapter(studentManageAdapter);
        }
    }
}
