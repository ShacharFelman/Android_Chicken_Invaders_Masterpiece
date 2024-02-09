package com.example.android_chicken_invaders;

import android.app.Application;

import com.example.android_chicken_invaders.model.ObstacleTypeLoader;
import com.example.android_chicken_invaders.model.ObstacleTypeSingleton;
import com.example.android_chicken_invaders.utils.MySignal;
import com.example.android_chicken_invaders.utils.SharedPreference;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        MySignal.init(this);
        SharedPreference.init(this);

        ObstacleTypeSingleton.init(ObstacleTypeLoader.loadObstacleTypes(this));
    }
}
