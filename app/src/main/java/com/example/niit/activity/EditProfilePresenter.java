package com.example.niit.activity;

public class EditProfilePresenter {

//    private CreatedStudentContract.View createdStudentView;
//    private DatabaseReference databaseReference;
//    private FirebaseStorage firebaseStorage;
//    private StorageReference storageReference;
//
//    CreatedStudentPresenter(CreatedStudentContract.View createdStudentView) {
//        this.createdStudentView = createdStudentView;
//        databaseReference = FirebaseDatabase.getInstance().getReference();
//        firebaseStorage = FirebaseStorage.getInstance();
//        storageReference = firebaseStorage.getReferenceFromUrl("gs://niit-c3bc4.appspot.com/Student/");
//    }
//
//    @Override
//    public void createdAccount(String classUser, String id, String password) {
//        CreateAccountStudent createAccountStudent = new CreateAccountStudent(password);
//        databaseReference.child("Account").child(classUser).child(id)
//                .setValue(createAccountStudent, new DatabaseReference.CompletionListener() {
//                    @Override
//                    public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
//                        if (databaseError == null) {
//                            createdStudentView.showCreatedAccountSuccess();
//                        } else {
//                            createdStudentView.showCreatedAccountFalse();
//                        }
//                    }
//                });
//    }
//
//    @Override
//    public void createdStudent(String classUser, String id, final CreatedStudent createdStudent) {
//        databaseReference.child("Student").child(classUser).child(id)
//                .setValue(createdStudent, new DatabaseReference.CompletionListener() {
//                    @Override
//                    public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
//                        if (databaseError == null) {
//                            createdStudentView.showCreateStudentSuccess();
//                        } else {
//                            createdStudentView.showCreateStudentFalse();
//                        }
//                    }
//                });
//    }
//
//    @Override
//    public void uploadAvatar(byte[] data) {
//        Calendar calendar = Calendar.getInstance();
//        storageReference.child("image" + calendar.getTimeInMillis() + "jpg");
//        UploadTask uploadTask = storageReference.putBytes(data);
//
//        uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
//            @Override
//            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) {
//                return storageReference.getDownloadUrl();
//            }
//        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
//            @Override
//            public void onComplete(@NonNull Task<Uri> task) {
//                if (task.isSuccessful()) {
//                    Uri downloadUri = task.getResult();
//                    Log.d("ktUrl", "onComplete: " + downloadUri);
//                    createdStudentView.showUploadImageSuccess(downloadUri.toString());
//                }
//            }
//        });
//    }

}
