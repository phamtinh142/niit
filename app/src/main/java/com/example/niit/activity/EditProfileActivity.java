package com.example.niit.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.niit.R;

public class EditProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
    }


//    @BindView(R.id.edt_id_create_student)
//    EditText edt_id_create_student;
//    @BindView(R.id.edt_name_create_student)
//    EditText edt_name_create_student;
//    @BindView(R.id.edt_email_create_student)
//    EditText edt_email_create_student;
//    @BindView(R.id.edt_password_create_student)
//    EditText edt_password_create_student;
//    @BindView(R.id.edt_confirm_create_student)
//    EditText edt_confirm_create_student;
//    @BindView(R.id.edt_age_create_student)
//    EditText edt_age_create_student;
//    @BindView(R.id.edt_class_create_student)
//    EditText edt_class_create_student;
//    @BindView(R.id.edt_sex_create_student)
//    EditText edt_sex_create_student;
//    @BindView(R.id.txt_date_create_student)
//    TextView txt_date_create_student;
//    @BindView(R.id.edt_phone_create_student)
//    EditText edt_phone_create_student;
//    @BindView(R.id.edt_address_create_student)
//    EditText edt_address_create_student;
//    @BindView(R.id.img_avata_create_student)
//    CircleImageView img_avata_create_student;
//    @BindView(R.id.layout_progress_bar)
//    LinearLayout layoutProgressBar;
//
//    private CreatedStudentPresenter createdStudentPresenter;
//
//    private String imageAvatar = "";
//
//    String id;
//    String name;
//    String email;
//    String password;
//    String confirmPassword;
//    String age;
//    String classUser;
//    String sex;
//    String birthDay;
//    String phone;
//    String address;
//
//    Bitmap bitmap;
//
//    private ArrayList<String> permissions = new ArrayList<>();
//    private final static int IMAGE_RESULT = 200;
//    private ArrayList<String> permissionsToRequest;
//    private final static int ALL_PERMISSIONS_RESULT = 107;
//    private ArrayList<String> permissionsRejected = new ArrayList<>();
//
//    DatabaseReference databaseReference;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_created_student);
//        ButterKnife.bind(this);
//        init();
//
//        askPermissions();
//    }
//
//    private void init() {
//        createdStudentPresenter = new CreatedStudentPresenter(this);
//        databaseReference = FirebaseDatabase.getInstance().getReference();
//    }
//
//    @OnClick(R.id.txt_date_create_student)
//    public void onClickSelectDate() {
//        final Calendar calendar = Calendar.getInstance();
//        int year = 2000;
//        int month = 1;
//        int day = 1;
//        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
//                new DatePickerDialog.OnDateSetListener() {
//                    @Override
//                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
//                        calendar.set(i, i1, i2);
//                        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat =
//                                new SimpleDateFormat("dd/MM/yyyy");
//                        txt_date_create_student.setText(simpleDateFormat.format(calendar.getTime()));
//                    }
//                }, year, month, day);
//        datePickerDialog.show();
//
//    }
//
//    @OnClick(R.id.ibtn_back_create_account)
//    public void onClickBackCreateAccount() {
//        finish();
//    }
//
//    @OnClick(R.id.btn_create_account)
//    public void onClickCreateAccount() {
//        id = edt_id_create_student.getText().toString().trim();
//        name = edt_name_create_student.getText().toString().trim();
//        email = edt_email_create_student.getText().toString().trim();
//        password = edt_password_create_student.getText().toString().trim();
//        confirmPassword = edt_confirm_create_student.getText().toString().trim();
//        age = edt_age_create_student.getText().toString().trim();
//        classUser = edt_class_create_student.getText().toString().trim();
//        sex = edt_sex_create_student.getText().toString().trim();
//        birthDay = txt_date_create_student.getText().toString().trim();
//        phone = edt_phone_create_student.getText().toString().trim();
//        address = edt_address_create_student.getText().toString().trim();
//
//        if (id.isEmpty()) {
//            Toast.makeText(this, "Vui lòng nhập ID", Toast.LENGTH_SHORT).show();
//            edt_id_create_student.requestFocus();
//        } else if (name.isEmpty()) {
//            Toast.makeText(this, "Vui lòng nhập tên", Toast.LENGTH_SHORT).show();
//            edt_name_create_student.requestFocus();
//        } else if (email.isEmpty()) {
//            Toast.makeText(this, "Vui lòng nhập email", Toast.LENGTH_SHORT).show();
//            edt_email_create_student.requestFocus();
//        } else if (password.isEmpty()) {
//            Toast.makeText(this, "Vui lòng nhập password", Toast.LENGTH_SHORT).show();
//            edt_password_create_student.requestFocus();
//        } else if (confirmPassword.isEmpty()) {
//            Toast.makeText(this, "Vui lòng nhập lại password", Toast.LENGTH_SHORT).show();
//            edt_confirm_create_student.requestFocus();
//        } else if (!password.equals(confirmPassword)) {
//            Toast.makeText(this, "Mật khẩu không khớp", Toast.LENGTH_SHORT).show();
//            edt_confirm_create_student.requestFocus();
//        } else if (age.isEmpty()) {
//            Toast.makeText(this, "Vui lòng nhập tuổi", Toast.LENGTH_SHORT).show();
//            edt_age_create_student.requestFocus();
//        } else if (classUser.isEmpty()) {
//            Toast.makeText(this, "Vui lòng nhập lớp", Toast.LENGTH_SHORT).show();
//            edt_class_create_student.requestFocus();
//        } else if (sex.isEmpty()) {
//            Toast.makeText(this, "Vui lòng nhập giới tính", Toast.LENGTH_SHORT).show();
//            edt_sex_create_student.requestFocus();
//        } else if (birthDay.isEmpty()) {
//            Toast.makeText(this, "Vui lòng nhập ngày sinh", Toast.LENGTH_SHORT).show();
//        } else if (phone.isEmpty()) {
//            Toast.makeText(this, "Vui lòng nhập số điện thoại", Toast.LENGTH_SHORT).show();
//            edt_phone_create_student.requestFocus();
//        } else if (address.isEmpty()) {
//            Toast.makeText(this, "Vui lòng nhập địa chỉ", Toast.LENGTH_SHORT).show();
//            edt_address_create_student.requestFocus();
//        } else {
//            layoutProgressBar.setVisibility(View.VISIBLE);
//
//            databaseReference.child("Account").child(classUser)
//                    .addValueEventListener(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                            if (dataSnapshot.child(id).exists()) {
//                                Toast.makeText(CreatedStudentActivity.this,
//                                        "Tài khoản đã tồn tại", Toast.LENGTH_SHORT).show();
//                                layoutProgressBar.setVisibility(View.GONE);
//                            } else {
//                                if (bitmap != null) {
//                                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//                                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
//                                    byte[] data = byteArrayOutputStream.toByteArray();
//                                    createdStudentPresenter.uploadAvatar(data);
//
//                                } else {
//                                    imageAvatar = "";
//                                    createdStudentPresenter.createdAccount(classUser, id, password);
//                                }
//                            }
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                        }
//                    });
//        }
//    }
//
//    @OnClick(R.id.layout_add_avata_account)
//    public void onClickChooseImage() {
//        startActivityForResult(getPickImageChooserIntent(), IMAGE_RESULT);
//    }
//
//    public Intent getPickImageChooserIntent() {
//
//        Uri outputFileUri = getCaptureImageOutputUri();
//
//        List<Intent> allIntents = new ArrayList<>();
//        PackageManager packageManager = getPackageManager();
//
//        Intent captureIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//        List<ResolveInfo> listCam = packageManager.queryIntentActivities(captureIntent, 0);
//        for (ResolveInfo res : listCam) {
//            Intent intent = new Intent(captureIntent);
//            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
//            intent.setPackage(res.activityInfo.packageName);
//            if (outputFileUri != null) {
//                intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
//            }
//            allIntents.add(intent);
//        }
//
//        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
//        galleryIntent.setType("image/*");
//        List<ResolveInfo> listGallery = packageManager.queryIntentActivities(galleryIntent, 0);
//        for (ResolveInfo res : listGallery) {
//            Intent intent = new Intent(galleryIntent);
//            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
//            intent.setPackage(res.activityInfo.packageName);
//            allIntents.add(intent);
//        }
//
//        Intent mainIntent = allIntents.get(allIntents.size() - 1);
//        for (Intent intent : allIntents) {
//            if (Objects.requireNonNull(intent.getComponent()).getClassName().equals("com.android.documentsui.DocumentsActivity")) {
//                mainIntent = intent;
//                break;
//            }
//        }
//        allIntents.remove(mainIntent);
//
//        Intent chooserIntent = Intent.createChooser(mainIntent, "Select source");
//        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, allIntents.toArray(
//                new Parcelable[allIntents.size()]));
//
//        return chooserIntent;
//    }
//
//    private void askPermissions() {
//        permissions.add(CAMERA);
//        permissions.add(WRITE_EXTERNAL_STORAGE);
//        permissions.add(READ_EXTERNAL_STORAGE);
//        permissionsToRequest = findUnAskedPermissions(permissions);
//
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//
//            if (permissionsToRequest.size() > 0)
//                requestPermissions(permissionsToRequest.toArray(
//                        new String[permissionsToRequest.size()]), ALL_PERMISSIONS_RESULT);
//        }
//    }
//
//    private ArrayList<String> findUnAskedPermissions(ArrayList<String> wanted) {
//        ArrayList<String> result = new ArrayList<>();
//        for (String perm : wanted) {
//            if (!hasPermission(perm)) {
//                result.add(perm);
//            }
//        }
//        return result;
//    }
//
//    private boolean hasPermission(String permission) {
//        if (canMakeSmores()) {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                return (checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED);
//            }
//        }
//        return true;
//    }
//
//    private boolean canMakeSmores() {
//        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
//    }
//
//    private Uri getCaptureImageOutputUri() {
//        Uri outputFileUri = null;
//        File getImage = getExternalFilesDir("");
//        if (getImage != null) {
//            outputFileUri = Uri.fromFile(new File(getImage.getPath(), "profile.png"));
//        }
//        return outputFileUri;
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
//                                           @NonNull int[] grantResults) {
//
//        if (requestCode == ALL_PERMISSIONS_RESULT) {
//            for (String perms : permissionsToRequest) {
//                if (!hasPermission(perms)) {
//                    permissionsRejected.add(perms);
//                }
//            }
//            if (permissionsRejected.size() > 0
//                    && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
//                    && shouldShowRequestPermissionRationale(permissionsRejected.get(0))) {
//
//                showMessageOKCancel(
//                        new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                requestPermissions(permissionsRejected.toArray(
//                                        new String[permissionsRejected.size()]),
//                                        ALL_PERMISSIONS_RESULT);
//                            }
//                        });
//            }
//        }
//
//    }
//
//    private void showMessageOKCancel(DialogInterface.OnClickListener okListener) {
//        new AlertDialog.Builder(this)
//                .setMessage("These permissions are mandatory for the application. Please allow access.")
//                .setPositiveButton("OK", okListener)
//                .setNegativeButton("Cancel", null)
//                .create()
//                .show();
//    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == Activity.RESULT_OK) {
//            if (requestCode == IMAGE_RESULT) {
//
//                String filePath = getImageFilePath(data);
//
//                if (filePath != null) {
//                    bitmap = BitmapFactory.decodeFile(filePath);
//                    img_avata_create_student.setImageBitmap(bitmap);
//                }
//            }
//        }
//    }
//
//    public String getImageFilePath(Intent data) {
//        return getImageFromFilePath(data);
//    }
//
//    private String getImageFromFilePath(Intent data) {
//        boolean isCamera = data == null || data.getData() == null;
//
//        if (isCamera) return getCaptureImageOutputUri().getPath();
//        else return getPathFromURI(data.getData());
//
//    }
//
//    private String getPathFromURI(Uri contentUri) {
//        String[] proj = {MediaStore.Audio.Media.DATA};
//        Cursor cursor = getContentResolver().query(contentUri, proj, null,
//                null, null);
//        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
//        cursor.moveToFirst();
//        return cursor.getString(column_index);
//    }
//
//    @Override
//    public void showCreateStudentSuccess() {
//        layoutProgressBar.setVisibility(View.GONE);
//        Toast.makeText(this, "Tạo Tài khoản thành công", Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    public void showCreateStudentFalse() {
//        layoutProgressBar.setVisibility(View.GONE);
//        Toast.makeText(this, "Tạo Tài khoản thất bại", Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    public void showCreatedAccountSuccess() {
//        CreatedStudent createdStudent = new CreatedStudent();
//        createdStudent.setId(id);
//        createdStudent.setAddress(address);
//        createdStudent.setAge(age);
//        createdStudent.setClassUser(classUser);
//        createdStudent.setEmail(email);
//        createdStudent.setAvatar(imageAvatar);
//        createdStudent.setName(name);
//        createdStudent.setPhone(phone);
//        createdStudent.setSex(sex);
//        createdStudent.setBithday(birthDay);
//        createdStudent.setType_account(1);
//
//        createdStudentPresenter.createdStudent(classUser, id, createdStudent);
//    }
//
//    @Override
//    public void showCreatedAccountFalse() {
//        layoutProgressBar.setVisibility(View.GONE);
//        Toast.makeText(this, "Lỗi", Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    public void showUploadImageSuccess(String image) {
//        this.imageAvatar = image;
//
//        createdStudentPresenter.createdAccount(classUser, id, password);
//
//    }
//
//    @Override
//    public void showUploadImageFalse() {
//        layoutProgressBar.setVisibility(View.GONE);
//        Toast.makeText(this, "Không thể upload ảnh", Toast.LENGTH_SHORT).show();
//    }
}
