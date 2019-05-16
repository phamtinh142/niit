package com.example.niit.Manager.fragment.NewsManage;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import com.example.niit.Manager.fragment.NewsManage.adapter.NewsManageAdapter;
import com.example.niit.Manager.fragment.NewsManage.dialog.CreatedNewsManageDialog;
import com.example.niit.R;
import com.example.niit.Share.SharePrefer;
import com.example.niit.Share.StringFinal;
import com.example.niit.adapter.ClassAdapter;
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

public class NewsManagerFragment extends Fragment implements ClassAdapter.ClassesListener {

    @BindView(R.id.recyclerView_news_manager)
    RecyclerView recyclerViewNewsList;
    @BindView(R.id.layout_classes)
    LinearLayout layout_classes;
    @BindView(R.id.recyclerView_class)
    RecyclerView recyclerView_class;
    @BindView(R.id.btn_choose_classes)
    CheckBox btn_choose_classes;
    @BindView(R.id.img_avata_news)
    CircleImageView img_avata_news;

    List<String> stringList;
    ClassAdapter classAdapter;

    private DatabaseReference databaseReference;

    NewsManageAdapter newsManageAdapter;

    List<News> newsList;

    String news = "ALL";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_manager, container, false);
        ButterKnife.bind(this, view);
        init();

        getClasses();

        getData();

        return view;
    }

    private void getClasses() {
        databaseReference.child("classes").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String classes = dataSnapshot.getValue(String.class);

                stringList.add(classes);
                classAdapter.notifyDataSetChanged();
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

        newsList = new ArrayList<>();
        recyclerViewNewsList.setHasFixedSize(true);
        recyclerViewNewsList.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        newsManageAdapter = new NewsManageAdapter(getActivity(), newsList);
        recyclerViewNewsList.setAdapter(newsManageAdapter);

        stringList = new ArrayList<>();
        recyclerView_class.setHasFixedSize(true);
        recyclerView_class.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        classAdapter = new ClassAdapter(getActivity(), stringList, this);
        recyclerView_class.setAdapter(classAdapter);

        String avatar = SharePrefer.getInstance().get(StringFinal.AVATAR, String.class);
        Picasso.get().load(avatar)
                .error(getResources().getDrawable(R.drawable.img_not_found))
                .into(img_avata_news);

    }

    @OnCheckedChanged(R.id.btn_choose_classes)
    public void onCheckChooseClasses(boolean checked) {
        if (checked) {
            layout_classes.setVisibility(View.VISIBLE);
        } else {
            layout_classes.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.txt_add_news)
    public void onClickAddNews() {
        CreatedNewsManageDialog createdNewsManageDialog = new CreatedNewsManageDialog();
        createdNewsManageDialog.show(getFragmentManager(), "addNewsManage");

    }


    private void getData() {
        databaseReference.child("news").child(news).addChildEventListener(new ChildEventListener() {
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
    public void onClickItemClasses(String classes) {
        newsList.clear();
        newsManageAdapter.notifyDataSetChanged();

        if (classes.equals("Chung")) {
            news = "ALL";
        } else {
            news = classes;
        }

        btn_choose_classes.setText(classes);
        layout_classes.setVisibility(View.GONE);
        btn_choose_classes.setChecked(false);

        getData();
    }
}
