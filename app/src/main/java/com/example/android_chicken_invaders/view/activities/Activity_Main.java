package com.example.android_chicken_invaders.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;
import com.example.android_chicken_invaders.R;
import com.example.android_chicken_invaders.model.constants.AppConstants;
import com.example.android_chicken_invaders.utils.MyScreenUtils;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.switchmaterial.SwitchMaterial;

import im.delight.android.location.SimpleLocation;


public class Activity_Main extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 123;

    private MaterialButton main_BTN_play;
    private MaterialButton main_BTN_scoreBoard;
    private MaterialButton main_BTN_gpsSettings;
    private MaterialButton main_BTN_gpsStartGame;
    private MaterialButton main_BTN_gpsClose;
    private SwitchMaterial main_SW_sensor;
    private SwitchMaterial main_SW_fast;
    private AppCompatImageView main_IMG_back;
    private AppCompatImageView main_IMG_rocket;

    private CardView main_LAY_gps;


    private SimpleLocation location;
    double lat;
    double lng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MyScreenUtils.hideSystemUI(this);

        findViews();
        initButtonsListeners();
        initVisualElements();

        getLocationFromSystem();
    }

    @Override
    protected void onResume() {
        super.onResume();
        hideGPSAlert();
    }


    private void findViews() {
        main_BTN_play = findViewById(R.id.main_BTN_play);
        main_BTN_scoreBoard = findViewById(R.id.main_BTN_scoreBoard);
        main_BTN_gpsSettings = findViewById(R.id.main_BTN_gpsSettings);
        main_BTN_gpsStartGame = findViewById(R.id.main_BTN_gpsStartGame);
        main_BTN_gpsClose = findViewById(R.id.main_BTN_gpsClose);

        main_SW_sensor = findViewById(R.id.main_SW_sensor);
        main_SW_fast = findViewById(R.id.main_SW_fast);

        main_IMG_back = findViewById(R.id.main_IMG_back);
        main_IMG_rocket = findViewById(R.id.main_IMG_rocket);

        main_LAY_gps = findViewById(R.id.main_LAY_gps);
    }

    private void initVisualElements() {
        Glide.with(Activity_Main.this)
                .load(R.drawable.img_bck_1)
                .into(main_IMG_back);

        Glide.with(Activity_Main.this)
                .load(R.drawable.gif_main_page_rocket2)
                .into(main_IMG_rocket);
    }

    private void initButtonsListeners() {
        main_BTN_play.setOnClickListener(v -> playGame());
        main_BTN_scoreBoard.setOnClickListener(v -> gotoActivityScoreBoard());
        main_BTN_gpsSettings.setOnClickListener(v -> gotoSettingsGPS());
        main_BTN_gpsStartGame.setOnClickListener(v ->gotoActivityGame());
        main_BTN_gpsClose.setOnClickListener(v -> hideGPSAlert());
    }

    private void getLocationFromSystem() {
        this.location = new SimpleLocation(this);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
        }
        setLatLng(location);
    }

    private void setLatLng(SimpleLocation location) {
        location.beginUpdates();
        this.lat = location.getLatitude();
        this.lng = location.getLongitude();
    }

    private void gotoActivityGame() {
        getLocationFromSystem();

        Intent intent = new Intent(this, Activity_Game.class);

        Bundle bundle = new Bundle();
        bundle.putBoolean(AppConstants.KEY_SENSOR, isSensorMode());
        bundle.putBoolean(AppConstants.KEY_FAST, isFastMode());
        bundle.putDouble(AppConstants.KEY_LAT, this.lat);
        bundle.putDouble(AppConstants.KEY_LNG, this.lng);
        intent.putExtra(AppConstants.KEY_BUNDLE, bundle);

        startActivity(intent);
    }

    private void playGame () {
        if(!checkIfGPSEnabled())
            gotoActivityGame();
        else
            showGPSAlert();
    }

    private void gotoActivityScoreBoard() {
        Intent intent = new Intent(this, Activity_ScoreBoard.class);
        startActivity(intent);
    }

    private boolean isFastMode() {
        return main_SW_fast.isChecked();
    }

    private boolean isSensorMode() {
        return main_SW_sensor.isChecked();
    }


    private boolean checkIfGPSEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        return !locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    private void showGPSAlert() {
        main_LAY_gps.setVisibility(View.VISIBLE);
        disableButtons();
    }

    private void hideGPSAlert() {
        main_LAY_gps.setVisibility(View.GONE);
        enableButtons();
    }

    private void gotoSettingsGPS() {
        Intent callGPSSettingIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivity(callGPSSettingIntent);
    }

//    private void requestNoGPS() {
//        gotoActivityGame();
//    }

    private void disableButtons() {
        main_BTN_play.setEnabled(false);
        main_BTN_scoreBoard.setEnabled(false);
        main_SW_sensor.setEnabled(false);
        main_SW_fast.setEnabled(false);
    }

    private void enableButtons() {
        main_BTN_play.setEnabled(true);
        main_BTN_scoreBoard.setEnabled(true);
        main_SW_sensor.setEnabled(true);
        main_SW_fast.setEnabled(true);
    }
}