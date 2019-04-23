package com.example.niit.Student.activity.CommentNews;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.niit.R;
import com.example.niit.Share.GetTimeSystem;
import com.example.niit.Share.SharePrefer;
import com.example.niit.Share.StringFinal;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NewsDetailActivity extends AppCompatActivity {
    @BindView(R.id.edt_comment_news_detail)
    EditText edtCommentNewsDetail;
    @BindView(R.id.recyclerView_comment_news)
    RecyclerView recyclerViewCommentNews;

    DatabaseReference databaseReference;

    CommentNewsAdapter commentNewsAdapter;

    List<SendComment> commentList;

    String idNews = "";
    String idUser = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        ButterKnife.bind(this);
        getDataBundle();
        init();

        getData();
    }

    private void getData() {
        databaseReference.child("News").child(idNews).child("comment").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                SendComment comment = dataSnapshot.getValue(SendComment.class);

                commentList.add(comment);
                commentNewsAdapter.notifyDataSetChanged();
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

        commentList = new ArrayList<>();
        recyclerViewCommentNews.setHasFixedSize(true);
        recyclerViewCommentNews.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        commentNewsAdapter = new CommentNewsAdapter(this, commentList);

        recyclerViewCommentNews.setAdapter(commentNewsAdapter);
    }

    private void getDataBundle() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        idNews = bundle.getString("idNews");
        idUser = bundle.getString("idUser");
    }

    @OnClick(R.id.btn_send_comment_news_detail)
    public void onClickSendComment() {
        String comment = edtCommentNewsDetail.getText().toString();
        String avatarUser = SharePrefer.getInstance().get(StringFinal.IMAGE, String.class);
        String username = SharePrefer.getInstance().get(StringFinal.USER_NAME, String.class);
        long createAtTime = GetTimeSystem.getMili();

        if (comment.equals("")) {
            edtCommentNewsDetail.requestFocus();
        } else {

            SendComment sendComment = new SendComment(avatarUser, username, comment, createAtTime);

            databaseReference.child("News").child(idNews).child("comment").push().setValue(sendComment, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                    if (databaseError == null) {
                        edtCommentNewsDetail.setText("");
                        recyclerViewCommentNews.scrollToPosition(commentNewsAdapter.getItemCount() - 1);
                    } else {
                        Toast.makeText(NewsDetailActivity.this, "Lỗi! vui lòng thử lại", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }
}
