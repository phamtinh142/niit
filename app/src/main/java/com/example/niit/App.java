package com.example.niit;

import android.app.Application;

import com.google.gson.Gson;

public class App extends Application {
    private static App mSelf;
    private Gson mGson;

    public static App self() {
        return mSelf;
    }

    public Gson getGson() {
        return mGson;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mSelf = this;
        mGson = new Gson();
    }
}
