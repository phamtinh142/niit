package com.example.niit.utils;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.File;

public class DecodeImage {
//    public String getImageFilePath(Intent data) {
//        return getImageFromFilePath(data);
//    }

//    private String getImageFromFilePath(Intent data) {
//        boolean isCamera = data == null || data.getData() == null;
//
//        if (isCamera) return getCaptureImageOutputUri().getPath();
//        else return getPathFromURI(data.getData());
//
//    }

//    private Uri getCaptureImageOutputUri() {
//        Uri outputFileUri = null;
//        File getImage = getActivity().getExternalFilesDir("");
//        if (getImage != null) {
//            outputFileUri = Uri.fromFile(new File(getImage.getPath(), "profile.png"));
//        }
//        return outputFileUri;
//    }
//
//    private String getPathFromURI(Uri contentUri) {
//        String[] proj = {MediaStore.Audio.Media.DATA};
//        Cursor cursor = getActivity().getContentResolver().query(contentUri, proj, null,
//                null, null);
//        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
//        cursor.moveToFirst();
//        return cursor.getString(column_index);
//    }
}
