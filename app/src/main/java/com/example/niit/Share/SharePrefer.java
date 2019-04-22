package com.example.niit.Share;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.niit.App;

public class SharePrefer {
    private static final String PREFER_NAME = "info_user";
    private static SharePrefer mInstance;
    private SharedPreferences mSharedPreferences;


    private SharePrefer() {
        mSharedPreferences = App.self().getSharedPreferences(PREFER_NAME, Context.MODE_PRIVATE);

    }

    public static SharePrefer getInstance() {
        if (mInstance == null) {
            mInstance = new SharePrefer();
        }

        return mInstance;
    }

    @SuppressWarnings("unchecked")
    public <T> T get(String key, Class<T> anonymousClass) {
        if (anonymousClass == String.class) {
            return (T) mSharedPreferences.getString(key, "");
        } else if (anonymousClass == Boolean.class) {
            return (T) Boolean.valueOf(mSharedPreferences.getBoolean(key, false));
        } else if (anonymousClass == Float.class) {
            return (T) Float.valueOf(mSharedPreferences.getFloat(key, 0));
        } else if (anonymousClass == Integer.class) {
            return (T) Integer.valueOf(mSharedPreferences.getInt(key, 0));
        } else if (anonymousClass == Long.class) {
            return (T) Long.valueOf(mSharedPreferences.getLong(key, 0));
        } else {
            return (T) App.self().getGson().fromJson(mSharedPreferences.getString(key, ""), anonymousClass);
        }
    }

    public <T> void put(String key, T data) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();

        if (data instanceof String) {
            editor.putString(key, (String) data);
        } else if (data instanceof Boolean) {
            editor.putBoolean(key, (Boolean) data);
        } else if (data instanceof Float) {
            editor.putFloat(key, (Float) data);
        } else if (data instanceof Integer) {
            editor.putInt(key, (Integer) data);
        } else if (data instanceof Long) {
            editor.putLong(key, (Long) data);
        } else {
            editor.putString(key, App.self().getGson().toJson(data));
        }
        editor.apply();
    }

    public void clear () {
        mSharedPreferences.edit().clear().apply();
    }
}
