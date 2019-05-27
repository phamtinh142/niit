package com.example.niit.fragment;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.niit.Share.GetTimeSystem;
import com.example.niit.activity.DiligenceActivity;
import com.example.niit.activity.EditInfoStudentActivity;
import com.example.niit.activity.LoginActivity;
import com.example.niit.R;
import com.example.niit.Share.SharePrefer;
import com.example.niit.Share.StringFinal;
import com.example.niit.activity.ScheludeActivity;
import com.example.niit.activity.ScoreActivity;
import com.example.niit.activity.UpdatePasswordActivity;
import com.example.niit.entities.Diligence;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class ProfileFragment extends Fragment {
    @BindView(R.id.img_profile_avatar)
    CircleImageView img_profile_avatar;
    @BindView(R.id.txt_username_profile)
    TextView txt_username_profile;
    @BindView(R.id.layout_loading)
    LinearLayout layout_loading;

    String userName;
    String avatar;
    int typeAccount;
    String userID;

    Bitmap bitmap;

    String imageNews = "";

    StorageReference storageReference;
    DatabaseReference databaseReference;

    private ArrayList<String> permissions = new ArrayList<>();
    private final static int IMAGE_RESULT = 200;
    private ArrayList<String> permissionsToRequest;
    private final static int ALL_PERMISSIONS_RESULT = 107;
    private ArrayList<String> permissionsRejected = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this, view);

        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        getData();

        askPermissions();

        return view;
    }

    private void getData() {
        userName = SharePrefer.getInstance().get(StringFinal.USERNAME, String.class);
        avatar = SharePrefer.getInstance().get(StringFinal.AVATAR, String.class);
        typeAccount = SharePrefer.getInstance().get(StringFinal.TYPE, Integer.class);
        userID = SharePrefer.getInstance().get(StringFinal.ID, String.class);

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

    @OnClick(R.id.rtl_edit_profile)
    public void onClickEditProfile() {
        startActivity(new Intent(getActivity(), EditInfoStudentActivity.class));
    }

    @OnClick(R.id.img_profile_avatar)
    public void onClickUpdateProfile() {
        startActivityForResult(getPickImageChooserIntent(), IMAGE_RESULT);
    }

    @OnClick(R.id.rtl_score)
    public void onClickMyScore () {
        startActivity(new Intent(getActivity(), ScoreActivity.class));
    }

    @OnClick(R.id.rtl_diligence)
    public void onClickDiligence() {
        startActivity(new Intent(getActivity(), DiligenceActivity.class));
    }

    @OnClick(R.id.rtl_update_password)
    public void onClickUpdatePassword() {
        String UserID = SharePrefer.getInstance().get(StringFinal.ID, String.class);
        String classUser = SharePrefer.getInstance().get(StringFinal.CLASSES, String.class);
        int typeAccount = SharePrefer.getInstance().get(StringFinal.TYPE, Integer.class);

        Intent intent = new Intent(getActivity(), UpdatePasswordActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("idUser", UserID);
        bundle.putString("classUser", classUser);
        bundle.putInt("typeAccount", typeAccount);
        bundle.putString("option", "local");
        intent.putExtras(bundle);
        startActivity(intent);

    }

    public Intent getPickImageChooserIntent() {

        List<Intent> allIntents = new ArrayList<>();
        PackageManager packageManager = getActivity().getPackageManager();



        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        List<ResolveInfo> listGallery = packageManager.queryIntentActivities(galleryIntent, 0);
        for (ResolveInfo res : listGallery) {
            Intent intent = new Intent(galleryIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(res.activityInfo.packageName);
            allIntents.add(intent);
        }

        Intent mainIntent = allIntents.get(allIntents.size() - 1);
        for (Intent intent : allIntents) {
            if (Objects.requireNonNull(intent.getComponent()).getClassName().equals("com.android.documentsui.DocumentsActivity")) {
                mainIntent = intent;
                break;
            }
        }
        allIntents.remove(mainIntent);

        Intent chooserIntent = Intent.createChooser(mainIntent, "Select source");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, allIntents.toArray(
                new Parcelable[allIntents.size()]));

        return chooserIntent;
    }

    private void askPermissions() {
        permissions.add(CAMERA);
        permissions.add(WRITE_EXTERNAL_STORAGE);
        permissions.add(READ_EXTERNAL_STORAGE);
        permissionsToRequest = findUnAskedPermissions(permissions);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (permissionsToRequest.size() > 0)
                requestPermissions(permissionsToRequest.toArray(
                        new String[permissionsToRequest.size()]), ALL_PERMISSIONS_RESULT);
        }
    }

    private ArrayList<String> findUnAskedPermissions(ArrayList<String> wanted) {
        ArrayList<String> result = new ArrayList<>();
        for (String perm : wanted) {
            if (!hasPermission(perm)) {
                result.add(perm);
            }
        }
        return result;
    }

    private boolean hasPermission(String permission) {
        if (canMakeSmores()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return (getActivity().checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED);
            }
        }
        return true;
    }

    private boolean canMakeSmores() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == IMAGE_RESULT) {

                String filePath = getImageFilePath(data);

                if (filePath != null) {
                    layout_loading.setVisibility(View.VISIBLE);
                    bitmap = BitmapFactory.decodeFile(filePath);
                    img_profile_avatar.setImageBitmap(bitmap);
                    getImage();
                }
            }
        }
    }

    private void getImage() {
        final StorageReference storage = storageReference.child("news/" + "image" + GetTimeSystem.getMili() + ".jpg");

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] data = byteArrayOutputStream.toByteArray();

        final UploadTask uploadTask = storage.putBytes(data);

        uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) {
                return storage.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();
                    Log.d("ktUrl", "onComplete: " + downloadUri);
                    imageNews = Objects.requireNonNull(downloadUri).toString();
                    updateAvatar(imageNews);
                } else {
                    Toast.makeText(getContext(), "Tạo không thành công", Toast.LENGTH_SHORT).show();
                    layout_loading.setVisibility(View.GONE);
                }
            }
        });
    }

    private void updateAvatar(String linkImage) {
        if (typeAccount == 0) {
            databaseReference.child("manager").child(userID).child("avatar").setValue(linkImage, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                    if (databaseError == null) {
                        layout_loading.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), "Cập nhật Avatar thành công !", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), "Cập nhật Avatar thất bại !", Toast.LENGTH_SHORT).show();
                        layout_loading.setVisibility(View.GONE);
                    }
                }
            });
        } else if (typeAccount == 1) {
            String classes = SharePrefer.getInstance().get(StringFinal.CLASSES, String.class);
            databaseReference.child("student").child(classes).child(userID).child("avatar").setValue(linkImage, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                    if (databaseError == null) {
                        layout_loading.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), "Cập nhật Avatar thành công !", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), "Cập nhật Avatar thất bại !", Toast.LENGTH_SHORT).show();
                        layout_loading.setVisibility(View.GONE);
                    }
                }
            });
        }
    }

    public String getImageFilePath(Intent data) {
        return getImageFromFilePath(data);
    }

    private String getImageFromFilePath(Intent data) {
        boolean isCamera = data == null || data.getData() == null;

        return getPathFromURI(data.getData());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        if (requestCode == ALL_PERMISSIONS_RESULT) {
            for (String perms : permissionsToRequest) {
                if (!hasPermission(perms)) {
                    permissionsRejected.add(perms);
                }
            }
            if (permissionsRejected.size() > 0
                    && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                    && shouldShowRequestPermissionRationale(permissionsRejected.get(0))) {

                showMessageOKCancel(
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                requestPermissions(permissionsRejected.toArray(
                                        new String[permissionsRejected.size()]),
                                        ALL_PERMISSIONS_RESULT);
                            }
                        });
            }
        }

    }

    private void showMessageOKCancel(DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(getActivity())
                .setMessage("These permissions are mandatory for the application. Please allow access.")
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    private String getPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Audio.Media.DATA};
        Cursor cursor = getActivity().getContentResolver().query(contentUri, proj, null,
                null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

}
