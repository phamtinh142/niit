package com.example.niit.Manager.activity.CreateStudent;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.niit.Manager.activity.CreateStudent.entities.CreateAccountStudent;
import com.example.niit.Manager.activity.CreateStudent.entities.CreatedStudent;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Calendar;

public class CreatedStudentPresenter implements CreatedStudentContract.Presenter {
    CreatedStudentContract.View createdStudentView;
    DatabaseReference databaseReference;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;

    CreatedStudentPresenter(CreatedStudentContract.View createdStudentView) {
        this.createdStudentView = createdStudentView;
        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReferenceFromUrl("gs://niit-c3bc4.appspot.com/Student/");
    }

    @Override
    public void createdAccount(String classUser, String id, String password) {
        CreateAccountStudent createAccountStudent = new CreateAccountStudent(password);
        databaseReference.child("Account").child(classUser).child(id)
                .setValue(createAccountStudent, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                        if (databaseError == null) {
                            createdStudentView.showCreatedAccountSuccess();
                        } else {
                            createdStudentView.showCreatedAccountFalse();
                        }
                    }
                });
    }

    @Override
    public void createdStudent(String classUser, String id, final CreatedStudent createdStudent) {
        databaseReference.child("Student").child(classUser).child(id)
                .setValue(createdStudent, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                        if (databaseError == null) {
                            createdStudentView.showCreateStudentSuccess();
                        } else {
                            createdStudentView.showCreateStudentFalse();
                        }
                    }
                });
    }

    @Override
    public void uploadAvatar(byte[] data) {
        Calendar calendar = Calendar.getInstance();
        storageReference.child("image" + calendar.getTimeInMillis() + "jpg");
        UploadTask uploadTask = storageReference.putBytes(data);

        uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) {
                return storageReference.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();
                    Log.d("ktUrl", "onComplete: " + downloadUri);
                    createdStudentView.showUploadImageSuccess(downloadUri.toString());
                }
            }
        });
    }
}
