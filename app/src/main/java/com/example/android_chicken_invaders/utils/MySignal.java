package com.example.android_chicken_invaders.utils;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.widget.Toast;

public class MySignal {


    private Context context;
    private Vibrator vibrator;


    private static MySignal mySignal;

    private MySignal(Context context) {
        this.context = context;
        vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
    }

    public static void init(Context context) {
        if (mySignal == null) {
            mySignal = new MySignal(context.getApplicationContext());
        }
    }

    public static MySignal getInstance() {
        return mySignal;
    }

    public void vibrate(long vibrateTime) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(vibrateTime, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            //deprecated in API 26
            vibrator.vibrate(vibrateTime);
        }
    }

    public void sound(int sound) {
        MediaPlayer mediaPlayer = MediaPlayer.create(context, sound);
        mediaPlayer.start();
    }

    public void toast(String message) {
        new Handler(Looper.getMainLooper()).post(() -> {
            try {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            } catch (IllegalStateException ex) {
                Log.d("exceptions", "run: Toast Error - " + ex.getLocalizedMessage());
            }
        });
    }

}
