package com.example.niit.activity;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.niit.R;
import com.example.niit.Share.SharePrefer;
import com.example.niit.Share.StringFinal;
import com.example.niit.adapter.DiligenceAdapter;
import com.example.niit.entities.Diligence;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DiligenceActivity extends AppCompatActivity {
    @BindView(R.id.recyclerView_diligence)
    RecyclerView recyclerView_diligence;

    List<Diligence> diligenceList;
    DiligenceAdapter diligenceAdapter;

    DatabaseReference databaseReference;

    String classes;
    String idUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diligence);
        ButterKnife.bind(this);
        classes = SharePrefer.getInstance().get(StringFinal.CLASSES, String.class);
        idUser = SharePrefer.getInstance().get(StringFinal.ID, String.class);
        init();

        getDiligence();
    }

    @OnClick(R.id.ibtn_back)
    public void onClickBack() {
        finish();
    }

    private void getDiligence() {
        databaseReference.child("diligence").child(classes).child(idUser).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Diligence diligence = dataSnapshot.getValue(Diligence.class);

                diligenceList.add(diligence);

                diligenceAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Diligence diligence = dataSnapshot.getValue(Diligence.class);

                for (int i = 0; i < diligenceList.size(); i++) {
                    if (diligenceList.get(i).getIdSubject().equals(diligence.getIdSubject())) {
                        diligenceList.remove(i);
                        diligenceList.add(i, diligence);
                        diligenceAdapter.notifyItemChanged(i);
                    }
                }
                diligenceAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                Diligence diligence = dataSnapshot.getValue(Diligence.class);

                for (int i = 0; i < diligenceList.size(); i++) {
                    if (diligenceList.get(i).getIdSubject().equals(diligence.getIdSubject())) {
                        diligenceList.remove(i);
                        diligenceAdapter.notifyItemChanged(i);
                    }
                }
                diligenceAdapter.notifyDataSetChanged();
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

        diligenceList = new ArrayList<>();
        recyclerView_diligence.setHasFixedSize(true);
        recyclerView_diligence.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        diligenceAdapter = new DiligenceAdapter(this, diligenceList, databaseReference);
        recyclerView_diligence.setAdapter(diligenceAdapter);
    }
}
