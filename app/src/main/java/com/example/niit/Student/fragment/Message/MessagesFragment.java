package com.example.niit.Student.fragment.Message;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.niit.R;
import com.example.niit.Student.activity.Contract.ContractActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;


public class MessagesFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_messages, container, false);
        ButterKnife.bind(this, view);


        return view;
    }

    @OnClick(R.id.floating_button)
    public void onClickAddMessage() {
        Intent intent = new Intent(getActivity(), ContractActivity.class);
        startActivity(intent);
    }

}
