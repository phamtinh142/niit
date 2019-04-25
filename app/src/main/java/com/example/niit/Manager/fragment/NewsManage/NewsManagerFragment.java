package com.example.niit.Manager.fragment.NewsManage;


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
import android.widget.Toast;

import com.example.niit.Manager.fragment.NewsManage.adapter.NewsManageAdapter;
import com.example.niit.Manager.fragment.NewsManage.dialog.CreatedNewsManageDialog;
import com.example.niit.R;
import com.example.niit.model.News;
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
import butterknife.OnClick;

public class NewsManagerFragment extends Fragment {

    @BindView(R.id.recyclerView_news_manager)
    RecyclerView recyclerViewNewsList;

    private DatabaseReference databaseReference;

    NewsManageAdapter newsManageAdapter;

    List<News> newsList;

    String Class = "CP13";
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_manager, container, false);
        ButterKnife.bind(this, view);
        init();

        getData();

        return view;
    }

    private void init() {
        databaseReference = FirebaseDatabase.getInstance().getReference("News");
        newsList = new ArrayList<>();
        recyclerViewNewsList.setHasFixedSize(true);
        recyclerViewNewsList.setLayoutManager(
                new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        newsManageAdapter = new NewsManageAdapter(getActivity(), newsList);

        recyclerViewNewsList.setAdapter(newsManageAdapter);

    }

    @OnCheckedChanged({R.id.rdo_cp13_news_manage,
            R.id.rdo_cp14_news_manage, R.id.rdo_cp15_news_manage})
    public void onCheckedRdoClass(CompoundButton button, boolean checked) {
        if (checked) {
            newsList.clear();
            newsManageAdapter.notifyDataSetChanged();
            switch (button.getId()) {
                case R.id.rdo_cp13_news_manage:
                    Class = "CP13";
                    break;
                case R.id.rdo_cp14_news_manage:
                    Class = "CP14";
                    break;
                case R.id.rdo_cp15_news_manage:
                    Class = "CP15";
                    break;
            }
            getData();
        }
    }

    @OnClick(R.id.btn_create_news_manage)
    public void onClickCreateNewsManage() {
        CreatedNewsManageDialog createdNewsManageDialog = new CreatedNewsManageDialog();
        createdNewsManageDialog.show(getFragmentManager(), "addNewsManage");
    }

    private void getData() {
        databaseReference.child(Class).addChildEventListener(new ChildEventListener() {
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
}
