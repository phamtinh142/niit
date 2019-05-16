package com.example.niit.Student.fragment.News;


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


import com.example.niit.R;
import com.example.niit.Share.SharePrefer;
import com.example.niit.Share.StringFinal;
import com.example.niit.Student.fragment.News.adapter.NewsAdapter;
import com.example.niit.Student.fragment.News.dialog.AddNewsDialog;
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
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class NewsFragment extends Fragment {
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @BindView(R.id.recyclerView_news)
    RecyclerView recyclerView_news;
    @BindView(R.id.img_avata_news)
    CircleImageView img_avata_news;

    NewsAdapter newsAdapter;
    List<News> newsList;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        ButterKnife.bind(this, view);
        init();

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
            img_avata_news.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.img_not_found));
        } else {
            Picasso.get().load(image)
                    .error(getActivity().getResources().getDrawable(R.drawable.img_not_found))
                    .into(img_avata_news);
        }


        newsList = new ArrayList<>();
        recyclerView_news.setHasFixedSize(true);
        recyclerView_news.setLayoutManager(
                new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        newsAdapter = new NewsAdapter(getContext(), newsList, databaseReference);
        recyclerView_news.setAdapter(newsAdapter);
    }

    private void getData() {
        databaseReference.child("News").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//                News news = dataSnapshot.getValue(News.class);
//                news.setIdNews(dataSnapshot.getKey());
//
//                if (newsList.size() == 0) {
//                    newsList.add(news);
//                } else {
//                    newsList.add(0, news);
//                }
//
//                newsAdapter.notifyDataSetChanged();
//
//                Log.d("ktIDnews", "onChildAdded: " + news.getIdNews());
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
