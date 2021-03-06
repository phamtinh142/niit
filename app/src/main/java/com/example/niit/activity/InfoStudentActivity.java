package com.example.niit.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.niit.R;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class InfoStudentActivity extends AppCompatActivity {
    @BindView(R.id.img_profile_avatar)
    CircleImageView img_profile_avatar;
    @BindView(R.id.txt_username_profile)
    TextView txt_username_profile;

    String id;
    String classes;
    int typeAccount;
    String userName;
    String avatar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_student);
        getDataBundle();
        ButterKnife.bind(this);
        setDataView();

    }

    private void setDataView() {
        if (avatar.isEmpty()) {
            img_profile_avatar.setImageDrawable(getResources().getDrawable(R.drawable.avatar_default));
        } else {
            Picasso.get().load(avatar)
                    .error(getResources().getDrawable(R.drawable.img_not_found))
                    .into(img_profile_avatar);
        }

        txt_username_profile.setText(userName);
    }

    private void getDataBundle() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            id = bundle.getString("id", "");
            classes = bundle.getString("class", "");
            typeAccount = bundle.getInt("type_account", 0);
            userName = bundle.getString("name", "");
            avatar = bundle.getString("avatar", "");
        }
    }

    @OnClick(R.id.rtl_test_score)
    public void onClickScoreStudent() {
        Intent intent = new Intent(this, ScoreManageActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("classes", classes);
        bundle.putString("userID", id);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @OnClick(R.id.rtl_change_pasword)
    public void onCLickChangePassword() {
        Intent intent = new Intent(this, UpdatePasswordActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("idUser", id);
        bundle.putString("classUser", classes);
        bundle.putInt("typeAccount", typeAccount);
        bundle.putString("option", "admin");
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @OnClick(R.id.rtl_diligence_student)
    public void onClickDiligence() {
        Intent intent = new Intent(this, DiligenceManageActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("idUser", id);
        bundle.putString("classUser", classes);
        bundle.putInt("typeAccount", typeAccount);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @OnClick(R.id.rtl_info_student)
    public void onClickInfoStudent() {
        Intent intent = new Intent(this, InfoStudentManageActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("idUser", id);
        bundle.putString("classUser", classes);
        bundle.putInt("typeAccount", typeAccount);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
