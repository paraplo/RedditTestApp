package com.example.gplotnikov.reddittestapp;

import android.app.Application;

import com.example.gplotnikov.reddittestapp.data.backend.BackendFactory;
import com.example.gplotnikov.reddittestapp.domain.backend.Backend;

public class App extends Application {
    private static final String USER_NAME = "g3w_jMd58wNTPA";
    private static final String USER_PASSWORD = "";
    private static App sInstance;
    private Backend backend;

    public static App getInstance() {
        return sInstance;
    }

    public Backend getBackend() {
        return backend;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        backend = BackendFactory.newRetrofitBackend(USER_NAME, USER_PASSWORD);
    }
}