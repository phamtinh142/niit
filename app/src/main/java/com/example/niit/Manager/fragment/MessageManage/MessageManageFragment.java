package com.example.niit.Manager.fragment.MessageManage;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.niit.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MessageManageFragment extends Fragment {


    public MessageManageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_message_manage, container, false);
    }

}