package com.example.niit.fragment;


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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.niit.adapter.NewsAdapter;
import com.example.niit.dialog.CreatedNewsDialog;
import com.example.niit.R;
import com.example.niit.Share.SharePrefer;
import com.example.niit.Share.StringFinal;
import com.example.niit.activity.CommentNewsActivity;
import com.example.niit.adapter.ClassAdapter;
import com.example.niit.listener.CreateNewsListener;
import com.example.niit.listener.ItemNewsListener;
import com.example.niit.entities.News;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class NewsFragment extends Fragment implements ClassAdapter.ClassesListener,
        ItemNewsListener,
        CreateNewsListener {

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
    @BindView(R.id.rdo_group_class)
    RadioGroup rdo_group_class;
    @BindView(R.id.rdo_class_news)
    RadioButton rdo_class_news;

    List<String> stringList;
    ClassAdapter classAdapter;

    private DatabaseReference databaseReference;

    NewsAdapter newsAdapter;

    List<News> newsList;

    String news = "ALL";

    String classes;

    int typeAccount;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        ButterKnife.bind(this, view);
        init();

        typeAccount = SharePrefer.getInstance().get(StringFinal.TYPE, Integer.class);

        optionNews();

        getClasses();

        return view;
    }

    private void optionNews() {
        if (typeAccount == 0) {
            btn_choose_classes.setVisibility(View.VISIBLE);
            rdo_group_class.setVisibility(View.GONE);
        } else if (typeAccount == 1) {
            btn_choose_classes.setVisibility(View.GONE);
            rdo_group_class.setVisibility(View.VISIBLE);
            classes = SharePrefer.getInstance().get(StringFinal.CLASSES, String.class);
            rdo_class_news.setText("Bảng tin  " + classes);
        }
    }

    @OnCheckedChanged({R.id.rdo_common_news, R.id.rdo_class_news})
    public void onCheckedOptionNews(CompoundButton button, boolean checked) {
        if (checked) {
            newsList.clear();
            newsAdapter.notifyDataSetChanged();
            switch (button.getId()) {
                case R.id.rdo_common_news:
                    news = "ALL";
                    break;
                case R.id.rdo_class_news:
                    news = classes;
                    break;
            }
            getData();
        }
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
        newsAdapter = new NewsAdapter(getActivity(), newsList, this);
        recyclerViewNewsList.setAdapter(newsAdapter);

        stringList = new ArrayList<>();
        recyclerView_class.setHasFixedSize(true);
        recyclerView_class.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        classAdapter = new ClassAdapter(getActivity(), stringList, this);
        recyclerView_class.setAdapter(classAdapter);

        String avatar = SharePrefer.getInstance().get(StringFinal.AVATAR, String.class);

        if (!avatar.equals("")) {
            Picasso.get().load(avatar)
                    .error(getResources().getDrawable(R.drawable.avatar_default))
                    .into(img_avata_news);
        } else {
            img_avata_news.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.avatar_default));
        }

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
        CreatedNewsDialog createdNewsDialog = new CreatedNewsDialog();
        createdNewsDialog.setTargetFragment(this, 0);
        createdNewsDialog.show(Objects.requireNonNull(getActivity())
                .getSupportFragmentManager(), "quantityDialog");

    }


    private void getData() {
        databaseReference.child("news").child(news).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                News news = dataSnapshot.getValue(News.class);
                news.setIdNews(dataSnapshot.getKey());

                List<String> likeList = new ArrayList<>();

                for (DataSnapshot postSnapshot: dataSnapshot.child("like").getChildren()) {
                    String userLike = postSnapshot.getValue(String.class);
                    likeList.add(userLike);
                }

                if (likeList.size() > 0) {
                    Log.d("ktlistlike", "onBindViewHolder: " + likeList.size());
                    news.setLikeList(likeList);
                }

                if (newsList.size() == 0) {
                    newsList.add(news);
                } else {
                    newsList.add(0, news);
                }

                newsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.d("ktyuyuy", "onChildChanged: ");
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
        newsAdapter.notifyDataSetChanged();

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

    @Override
    public void onResume() {
        super.onResume();

        newsList.clear();
        newsAdapter.notifyDataSetChanged();
        getData();
    }

    @Override
    public void onCreateNewsSuccess() {
        newsList.clear();
        newsAdapter.notifyDataSetChanged();

        getData();
    }
}
