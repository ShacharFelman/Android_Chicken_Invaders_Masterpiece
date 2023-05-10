package com.example.android_chicken_invaders.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreference {

    private static final String SP_FILE_NAME = "SP_FILE_NAME";
    private SharedPreferences prefs;
    private static SharedPreference instance;

    private SharedPreference(Context context) {
        prefs = context.getApplicationContext().getSharedPreferences(SP_FILE_NAME, Context.MODE_PRIVATE);
    }

    public static void init(Context context) {
        if (instance == null) {
            instance = new SharedPreference(context);
        }
    }

    public static SharedPreference getInstance() {
        return instance;
    }

    public void putString(String key, String value) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public String getString(String key, String def) {
        return prefs.getString(key, def);
    }

}
