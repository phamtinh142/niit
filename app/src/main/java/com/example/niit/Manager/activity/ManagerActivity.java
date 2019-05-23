package com.example.niit.Manager.activity;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.niit.Manager.fragment.ManageStudent.ManageStudentFragment;
import com.example.niit.Manager.fragment.MessageManage.MessageManageFragment;
import com.example.niit.Manager.fragment.NewsManage.NewsManagerFragment;
import com.example.niit.R;
import com.example.niit.Student.fragment.Message.MessagesFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ManagerActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    @BindView(R.id.bottom_navigation_manager_activity)
    BottomNavigationView navManager;

    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);
        ButterKnife.bind(this);

        loadFragment(new NewsManagerFragment());
        navManager.setOnNavigationItemSelectedListener(this);


    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container_manager_activity, fragment);
        fragmentTransaction.commit();
    }

    @SuppressLint("NewApi")
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            finishAffinity();
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment fragment;
        switch (menuItem.getItemId()) {
            case R.id.bottom_navigation_news:
                fragment = new NewsManagerFragment();
                loadFragment(fragment);
                break;
            case R.id.bottom_navigation_message:
                fragment = new MessagesFragment();
                loadFragment(fragment);
                break;
            case R.id.bottom_navigation_profile:
                fragment = new ManageStudentFragment();
                loadFragment(fragment);
                break;
        }

        return false;
    }
}
