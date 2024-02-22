package com.example.android_chicken_invaders.view.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.android_chicken_invaders.R;
import com.example.android_chicken_invaders.model.RecordsListMng;
import com.example.android_chicken_invaders.model.entities.GameRecord;
import com.example.android_chicken_invaders.utils.MyScreenUtils;
import com.example.android_chicken_invaders.view.fragments.ListFragment;
import com.example.android_chicken_invaders.view.fragments.MapFragment;
import com.example.android_chicken_invaders.interfaces.Callback_List;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class Activity_ScoreBoard extends AppCompatActivity {
    private ListFragment listFragment;
    private MapFragment mapFragment;
    private MaterialButton score_BTN_mainPage;

    private final Callback_List callback_list = new Callback_List() {
        @Override
        public void setMapLocation(double lat, double lng, String name) {
            mapFragment.setMapLocation(lat, lng, name);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_board);

        MyScreenUtils.hideSystemUI(this);

        findViews();
        initFragments();
        initButtonsListeners();
    }

    private void findViews() {
        this.score_BTN_mainPage = findViewById(R.id.score_BTN_mainPage);
    }

    private void initButtonsListeners() {
        score_BTN_mainPage.setOnClickListener(v -> gotoActivityMain());
    }

    private void initFragments() {
        listFragment = new ListFragment();
        mapFragment = new MapFragment();
        listFragment.setCallback_list(callback_list);

        getSupportFragmentManager().beginTransaction().add(R.id.score_LAY_list,listFragment).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.score_LAY_map,mapFragment).commit();
    }

    private void gotoActivityMain() {
        finish();
    }
}