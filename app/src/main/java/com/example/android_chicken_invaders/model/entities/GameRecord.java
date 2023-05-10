package com.example.android_chicken_invaders.model.entities;

import androidx.annotation.NonNull;

public class GameRecord {
    private String userId;
    private int score;
    private double lat;
    private double lng;
//    private LocalDateTime date;

    public GameRecord() {
    }

    public GameRecord(String userId, int score, double lat, double lng) {
        this.userId = userId;
        this.score = score;
        this.lat = lat;
        this.lng = lng;
//        this.date = LocalDateTime.;
    }

    public String getUserId() {
        return userId;
    }

    public GameRecord setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public int getScore() {
        return score;
    }

    public GameRecord setScore(int score) {
        this.score = score;
        return this;
    }

    public double getLat() {
        return lat;
    }

    public GameRecord setLat(double lat) {
        this.lat = lat;
        return this;
    }

    public double getLng() {
        return lng;
    }

    public GameRecord setLng(double lng) {
        this.lng = lng;
        return this;
    }

    @NonNull
    @Override
    public String toString() {
        return "GameRecord{" +
                "userId='" + userId + '\'' +
                ", score=" + score +
                '}';
    }
}
