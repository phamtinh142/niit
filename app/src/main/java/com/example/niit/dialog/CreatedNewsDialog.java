package com.example.niit.dialog;

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
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.niit.R;
import com.example.niit.Share.GetTimeSystem;
import com.example.niit.Share.SharePrefer;
import com.example.niit.Share.StringFinal;
import com.example.niit.adapter.ClassAdapter;
import com.example.niit.entities.News;
import com.example.niit.listener.CreateNewsListener;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class CreatedNewsDialog extends DialogFragment implements ClassAdapter.ClassesListener {

    String news = "ALL";

    @BindView(R.id.img_add_news_manage)
    ImageView img_add_news_manage;
    @BindView(R.id.label_choose_image_manage)
    TextView label_choose_image_manage;
    @BindView(R.id.layout_image_news_manage)
    RelativeLayout layout_image_news_manage;
    @BindView(R.id.txt_content_add_news_manage)
    EditText txt_content_add_news_manage;
    @BindView(R.id.layout_progress_bar_add_news_manage)
    LinearLayout layout_progress_bar;
    @BindView(R.id.layout_classes)
    LinearLayout layout_classes;
    @BindView(R.id.recyclerView_class)
    RecyclerView recyclerView_class;
    @BindView(R.id.btn_choose_classes)
    CheckBox btn_choose_classes;
    @BindView(R.id.rdo_group_class)
    RadioGroup rdo_group_class;
    @BindView(R.id.rdo_class_news)
    RadioButton rdo_class_news;

    private String imageNews = "";

    Bitmap bitmap;

    private ArrayList<String> permissions = new ArrayList<>();
    private final static int IMAGE_RESULT = 200;
    private ArrayList<String> permissionsToRequest;
    private final static int ALL_PERMISSIONS_RESULT = 107;
    private ArrayList<String> permissionsRejected = new ArrayList<>();

    StorageReference storageReference;
    DatabaseReference databaseReference;

    List<String> stringList;
    ClassAdapter classAdapter;

    CreateNewsListener createNewsListener;

    String classes;

    int typeAccount;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_DeviceDefault_Light_NoActionBar);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_created_news, container, false);
        ButterKnife.bind(this, view);
        init();
        typeAccount = SharePrefer.getInstance().get(StringFinal.TYPE, Integer.class);

        optionLayout();

        getClasses();

        askPermissions();

        return view;
    }

    private void optionLayout() {
        if (typeAccount == 0) {
            btn_choose_classes.setVisibility(View.VISIBLE);
            rdo_group_class.setVisibility(View.GONE);
        } else if (typeAccount == 1) {
            rdo_group_class.setVisibility(View.VISIBLE);
            btn_choose_classes.setVisibility(View.GONE);
            classes = SharePrefer.getInstance().get(StringFinal.CLASSES, String.class);
            rdo_class_news.setText("Bảng tin lớp " + classes);
        }
    }

    private void getClasses() {
        databaseReference.child("classes").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String classes = dataSnapshot.getValue(String.class);

                stringList.add(classes);
                classAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void init() {
        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        try {
            createNewsListener = (CreateNewsListener) getActivity();
        } catch (Exception e) {
            createNewsListener = (CreateNewsListener) getTargetFragment();
        }

        stringList = new ArrayList<>();
        recyclerView_class.setHasFixedSize(true);
        recyclerView_class.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        classAdapter = new ClassAdapter(getActivity(), stringList, this);
        recyclerView_class.setAdapter(classAdapter);
    }

    @OnCheckedChanged({R.id.rdo_common_news, R.id.rdo_class_news})
    public void onCheckedCreateOption(CompoundButton button, boolean checked) {
        if (checked) {
            switch (button.getId()) {
                case R.id.rdo_common_news:
                    news = "ALL";
                    break;
                case R.id.rdo_class_news:
                    news = classes;
                    break;
            }
        }
    }

    @OnClick(R.id.btn_back_add_news_manage)
    public void onClickBackAddNews() {
        getDialog().dismiss();
    }

    @OnClick(R.id.btn_close_image_manage)
    public void onClickCloseChooseImage() {
        layout_image_news_manage.setVisibility(View.GONE);
        label_choose_image_manage.setText("Thêm Ảnh");
        imageNews = "";
    }

    @OnClick(R.id.layout_add_image_manage)
    public void onClickAddImage() {
        startActivityForResult(getPickImageChooserIntent(), IMAGE_RESULT);
    }

    @OnCheckedChanged(R.id.btn_choose_classes)
    public void onClickChooseClass(boolean checked) {
        if (checked) {
            layout_classes.setVisibility(View.VISIBLE);
        } else {
            layout_classes.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.btn_confirm)
    public void onClickConfirmAddNews() {
        getImage();
        layout_progress_bar.setVisibility(View.VISIBLE);
    }

    private void getImage() {
        if (bitmap != null) {
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
                        imageNews = downloadUri.toString();
                        uploadNews();
                        Toast.makeText(getContext(), "Tạo thành công", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "Tạo không thành công", Toast.LENGTH_SHORT).show();
                        layout_progress_bar.setVisibility(View.GONE);
                    }
                }
            });
        } else {
            imageNews = "";
            uploadNews();
        }
    }

    private void uploadNews() {
        News news = new News();
        news.setId(SharePrefer.getInstance().get(StringFinal.ID, String.class));
        news.setContent_news(txt_content_add_news_manage.getText().toString().trim());
        news.setCreate_time(GetTimeSystem.getTime());
        news.setImage_news(imageNews);
        news.setType_account(typeAccount);
        news.setClasses(this.news);
        if (typeAccount == 1) {
            news.setClassUser(classes);
        }

        databaseReference.child("news").child(this.news).push().setValue(news, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                if (databaseError == null) {
                    createNewsListener.onCreateNewsSuccess();
                    Toast.makeText(getActivity(), "Đăng thành công", Toast.LENGTH_SHORT).show();
                    getDialog().dismiss();
                    layout_progress_bar.setVisibility(View.GONE);
                } else {
                    Toast.makeText(getActivity(), "Đăng thất bại", Toast.LENGTH_SHORT).show();
                    layout_progress_bar.setVisibility(View.GONE);
                }
            }
        });
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
                    bitmap = BitmapFactory.decodeFile(filePath);
                    img_add_news_manage.setImageBitmap(bitmap);
                    layout_image_news_manage.setVisibility(View.VISIBLE);
                    label_choose_image_manage.setText("Thay Ảnh Khác");
                }
            }
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

    @Override
    public void onClickItemClasses(String classes) {
        if (classes.equals("Chung")) {
            news = "ALL";
        } else {
            news = classes;
        }
        btn_choose_classes.setText(classes);

        layout_classes.setVisibility(View.GONE);
        btn_choose_classes.setChecked(false);
    }
}
