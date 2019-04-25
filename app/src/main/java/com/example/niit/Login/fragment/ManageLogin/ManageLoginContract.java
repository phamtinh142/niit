package com.example.niit.Login.fragment.ManageLogin;

public interface ManageLoginContract {
    interface View {
        void showLoginSuccess(String id);
        void showToast(String message);
    }

    interface Presenter {
        void loginAccount(String id, String password);

    }
}
