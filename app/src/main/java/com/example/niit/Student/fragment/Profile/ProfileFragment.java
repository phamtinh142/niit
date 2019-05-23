package com.example.niit.Student.fragment.Profile;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.niit.Login.LoginActivity;
import com.example.niit.R;
import com.example.niit.Share.SharePrefer;
import com.example.niit.Share.StringFinal;
import com.example.niit.Student.activity.HocphiActivity;
import com.example.niit.Student.activity.Schelude.ScheludeActivity;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {
    @BindView(R.id.img_profile_avatar)
    CircleImageView img_profile_avatar;
    @BindView(R.id.txt_username_profile)
    TextView txt_username_profile;

    String userName;
    String avatar;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this, view);

        getData();

        return view;
    }

    private void getData() {
        userName = SharePrefer.getInstance().get(StringFinal.USERNAME, String.class);
        avatar = SharePrefer.getInstance().get(StringFinal.AVATAR, String.class);
        if (avatar.isEmpty()) {
            img_profile_avatar.setImageDrawable(getResources().getDrawable(R.drawable.avatar_default));
        } else {
            Picasso.get().load(avatar)
                    .error(getResources().getDrawable(R.drawable.img_not_found))
                    .into(img_profile_avatar);
        }
        txt_username_profile.setText(userName);
    }

    @OnClick(R.id.btn_logout_manager)
    public void onClickLogout() {
        SharePrefer.getInstance().clear();
        startActivity(new Intent(getActivity(), LoginActivity.class));
    }

    @OnClick(R.id.rtl_schelude)
    public void onClickSchelude() {
        startActivity(new Intent(getActivity(), ScheludeActivity.class));
    }

}
