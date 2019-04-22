package com.example.niit.Student.fragment.Message;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.niit.R;
import com.example.niit.Student.activity.MessageDetailActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class MessagesFragment extends Fragment {
    View view;
    LinearLayout layoutdetails;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_messages, container, false);

        layoutdetails = view.findViewById(R.id.layout_detail);

        layoutdetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MessageDetailActivity.class));
            }
        });

        return view;
    }

}
