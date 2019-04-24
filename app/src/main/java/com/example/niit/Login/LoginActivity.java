package com.example.niit.Login;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.CompoundButton;

import com.example.niit.Login.fragment.ManageLogin.ManageLoginFragment;
import com.example.niit.Login.fragment.StudentLogin.StudentLoginFragment;
import com.example.niit.R;

import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;


public class LoginActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        setFramgnet(new StudentLoginFragment());

    }


    @OnCheckedChanged({R.id.rdo_student_login, R.id.rdo_manage_login})
    public void onCheckedRadioButtonLogin(CompoundButton button, boolean checked) {
        if (checked) {
            switch (button.getId()) {
                case R.id.rdo_student_login:
                    setFramgnet(new StudentLoginFragment());
                    break;
                case R.id.rdo_manage_login:
                    setFramgnet(new ManageLoginFragment());
                    break;
            }
        }
    }

    private void setFramgnet(Fragment framgnet) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container_login, framgnet);
        fragmentTransaction.commit();
    }


    @Override
    public void onBackPressed() {
        finishAffinity();
    }
}
