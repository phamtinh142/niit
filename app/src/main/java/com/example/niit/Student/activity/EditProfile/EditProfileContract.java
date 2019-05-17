package com.example.niit.Student.activity.EditProfile;

import com.example.niit.Manager.activity.CreateStudent.entities.CreatedStudent;

public interface EditProfileContract {
    interface View {
        void showCreateStudentSuccess();
        void showCreateStudentFalse();

        void showCreatedAccountSuccess();
        void showCreatedAccountFalse();

        void showUploadImageSuccess(String image);
        void showUploadImageFalse();
    }

    interface Presenter {
        void createdAccount(String classUser, String id, String password);
        void createdStudent(String classUser, String id, CreatedStudent createdStudent);
        void uploadAvatar(byte[] data);
    }
}
