package com.example.niit.Login.fragment.StudentLogin;

public interface StudentLoginContract {
    interface View {
        void showLoginSuccess();
        void showLoginFalse();
    }

    interface Presenter {
        void loginAccount(String id, String password);

    }
}
