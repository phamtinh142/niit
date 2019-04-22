package com.example.niit.Student.fragment.Profile;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.niit.Login.LoginActivity;
import com.example.niit.R;
import com.example.niit.Share.SharePrefer;
import com.example.niit.Student.activity.HocphiActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {
    private View view;
    Button btnhocphi;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);

        btnhocphi = view.findViewById(R.id.btnhocphi);

        btnhocphi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharePrefer.getInstance().clear();
                startActivity(new Intent(getActivity(), LoginActivity.class));
            }
        });

        return view;
    }

}
