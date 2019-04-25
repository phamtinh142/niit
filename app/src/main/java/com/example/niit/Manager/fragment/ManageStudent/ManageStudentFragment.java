package com.example.niit.Manager.fragment.ManageStudent;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.niit.Login.LoginActivity;
import com.example.niit.Manager.activity.CreateStudent.CreatedStudentActivity;
import com.example.niit.Manager.activity.ScheludeManage.ScheludeManageActivity;
import com.example.niit.Manager.activity.StudentManage.StudentManageActivity;
import com.example.niit.R;
import com.example.niit.Share.SharePrefer;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class ManageStudentFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_manage_student, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @OnClick(R.id.btn_logout_manager)
    public void onClickLogout() {
        SharePrefer.getInstance().clear();
        startActivity(new Intent(getActivity(), LoginActivity.class));
    }

    @OnClick(R.id.rtl_student_manage)
    public void onCLickStudentManage() {
        startActivity(new Intent(getActivity(), StudentManageActivity.class));
    }

    @OnClick(R.id.rtl_create_student_account)
    public void onClickCreateStudent() {
        startActivity(new Intent(getActivity(), CreatedStudentActivity.class));
    }

    @OnClick(R.id.rtl_schelude_manage)
    public void onClickScheludeManage() {
        startActivity(new Intent(getActivity(), ScheludeManageActivity.class));
    }

}
