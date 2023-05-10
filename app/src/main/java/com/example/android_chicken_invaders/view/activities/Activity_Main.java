package com.example.android_chicken_invaders.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.example.android_chicken_invaders.R;
import com.example.android_chicken_invaders.model.GameConstants;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.switchmaterial.SwitchMaterial;

import im.delight.android.location.SimpleLocation;


public class Activity_Main extends AppCompatActivity {

    private MaterialButton main_BTN_play;
    private MaterialButton main_BTN_scoreBoard;
    private SwitchMaterial main_SW_sensor;
    private SwitchMaterial main_SW_fast;

    private AppCompatImageView main_IMG_back;

    private SimpleLocation location;
    double lat;
    double lng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        findViews();
        initButtonsListeners();

        getLocationFromSystem();
    }

    private void getLocationFromSystem() {
        this.location = new SimpleLocation(this);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 101);
        }
        setLatLng(location);
    }

    private void setLatLng(SimpleLocation location){
        location.beginUpdates();
        this.lat = location.getLatitude();
        this.lng = location.getLongitude();
    }

    private void findViews() {
        main_BTN_play = findViewById(R.id.main_BTN_play);
        main_BTN_scoreBoard = findViewById(R.id.main_BTN_scoreBoard);

        main_SW_sensor = findViewById(R.id.main_SW_sensor);
        main_SW_fast = findViewById(R.id.main_SW_fast);

        main_IMG_back = findViewById(R.id.main_IMG_back);

    }

    private void initButtonsListeners() {
        main_BTN_play.setOnClickListener(v -> gotoActivityGame());
        main_BTN_scoreBoard.setOnClickListener(v -> gotoActivityScoreBoard());
    }

    private void gotoActivityGame() {
        Intent intent = new Intent(this, Activity_Game.class);

        Bundle bundle = new Bundle();
        bundle.putBoolean(GameConstants.KEY_SENSOR, isSensorMode());
        bundle.putBoolean(GameConstants.KEY_FAST, isFastMode());
        bundle.putDouble(GameConstants.KEY_LAT, this.lat);
        bundle.putDouble(GameConstants.KEY_LNG, this.lng);
        intent.putExtra(GameConstants.KEY_BUNDLE, bundle);

        startActivity(intent);
    }

    private void gotoActivityScoreBoard() {
        Intent intent = new Intent(this,Activity_ScoreBoard.class);
        startActivity(intent);
    }

    private boolean isFastMode () {
        return main_SW_fast.isChecked();
    }

    private boolean isSensorMode () {
        return main_SW_sensor.isChecked();
    }

}