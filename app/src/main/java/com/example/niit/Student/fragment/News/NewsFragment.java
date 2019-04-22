package com.example.niit.Student.fragment.News;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.niit.R;
import com.example.niit.Share.SharePrefer;
import com.example.niit.Share.StringFinal;
import com.example.niit.Student.fragment.News.dialog.AddNewsDialog;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class NewsFragment extends Fragment {
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @BindView(R.id.img_avata_news)
    CircleImageView img_avata_news;

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
        databaseReference = firebaseDatabase.getReference("News");

        String image = SharePrefer.getInstance().get(StringFinal.IMAGE, String.class);

        Log.d("ktimage", "init: " + image);

        if (image.equals("")) {
            img_avata_news.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.img_not_found));
        } else {
            Picasso.get().load(image)
                    .error(getActivity().getResources().getDrawable(R.drawable.img_not_found))
                    .into(img_avata_news);
        }
    }

    private void getData() {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("ktDataNews", "onDataChange: " + dataSnapshot.toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
