package com.example.niit.Student.fragment.News;


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
import android.widget.CompoundButton;
import android.widget.RadioButton;


import com.example.niit.Manager.fragment.NewsManage.adapter.NewsManageAdapter;
import com.example.niit.R;
import com.example.niit.Share.SharePrefer;
import com.example.niit.Share.StringFinal;
import com.example.niit.Student.activity.CommentNews.CommentNewsActivity;
import com.example.niit.Student.fragment.News.dialog.AddNewsDialog;
import com.example.niit.listener.ItemNewsListener;
import com.example.niit.model.News;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class NewsFragment extends Fragment implements ItemNewsListener {
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @BindView(R.id.rdo_class_news)
    RadioButton rdo_class_news;

    @BindView(R.id.recyclerView_news)
    RecyclerView recyclerView_news;
    @BindView(R.id.img_avata_news)
    CircleImageView img_avata_news;

    NewsManageAdapter newsManageAdapter;
    List<News> newsList;

    String optionNews = "ALL";
    String classes;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        ButterKnife.bind(this, view);
        init();

        classes = SharePrefer.getInstance().get(StringFinal.CLASSES, String.class);
        rdo_class_news.setText("Bảng tin " + classes);

        getData();

        return view;
    }

    @OnClick(R.id.txt_add_news)
    public void onClickAddNews() {
        AddNewsDialog addNewsDialog = new AddNewsDialog();
        addNewsDialog.show(getFragmentManager(), "addnews");
    }

    private void init() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        String image = SharePrefer.getInstance().get(StringFinal.AVATAR, String.class);

        Log.d("ktimage", "init: " + image);

        if (image.equals("")) {
            img_avata_news.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.avatar_default));
        } else {
            Picasso.get().load(image)
                    .error(getActivity().getResources().getDrawable(R.drawable.avatar_default))
                    .into(img_avata_news);
        }


        newsList = new ArrayList<>();
        recyclerView_news.setHasFixedSize(true);
        recyclerView_news.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        newsManageAdapter = new NewsManageAdapter(getContext(), newsList, this);
        recyclerView_news.setAdapter(newsManageAdapter);
    }

    @OnCheckedChanged({R.id.rdo_common_news, R.id.rdo_class_news})
    public void onCheckedNews(CompoundButton button, boolean checked) {
        if (checked) {
            switch (button.getId()) {
                case R.id.rdo_common_news:
                    optionNews = "ALL";
                    break;
                case R.id.rdo_class_news:
                    optionNews = classes;
                    break;
            }
            newsList.clear();
            newsManageAdapter.notifyDataSetChanged();
            getData();
        }
    }

    private void getData() {
        databaseReference.child("news").child(optionNews).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                News news = dataSnapshot.getValue(News.class);
                news.setIdNews(dataSnapshot.getKey());
                if (newsList.size() == 0) {
                    newsList.add(news);
                } else {
                    newsList.add(0, news);
                }

                newsManageAdapter.notifyDataSetChanged();
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

    @Override
    public void onClickDetailNews(int position) {
        Intent intent = new Intent(getActivity(), CommentNewsActivity.class);
        Bundle bundle = new Bundle();

        bundle.putString("classes", newsList.get(position).getClasses());
        bundle.putString("idNews", newsList.get(position).getIdNews());
        bundle.putString("idUser", newsList.get(position).getId());
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
