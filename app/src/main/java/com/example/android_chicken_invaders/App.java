package com.example.android_chicken_invaders;

import android.app.Application;

import com.example.android_chicken_invaders.utils.MySignal;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        MySignal.init(this);
    }
}
