package com.example.android_chicken_invaders.model.entities;

import com.example.android_chicken_invaders.model.eObstacleTypes;

public class ObstacleType {
    private eObstacleTypes type;
    private String name;
    private String drawableRef;
    private String impactDrawableRef;
    private int scoreValue;
    private int livesValue;

    public ObstacleType(eObstacleTypes type, String name, String drawableRef, String impactDrawableRef, int scoreValue, int livesValue) {
        this.type = type;
        this.name = name;
        this.drawableRef = drawableRef;
        this.impactDrawableRef = impactDrawableRef;
        this.scoreValue = scoreValue;
        this.livesValue = livesValue;
    }

    public ObstacleType() {
    }

    public eObstacleTypes getType() {
        return type;
    }

    public ObstacleType setType(eObstacleTypes type) {
        this.type = type;
        return this;
    }

    public String getName() {
        return name;
    }

    public ObstacleType setName(String name) {
        this.name = name;
        return this;
    }

    public String getDrawableRef() {
        return drawableRef;
    }

    public void setDrawableRef(String drawableRef) {
        this.drawableRef = drawableRef;
//        return this;
    }


    public String getImpactDrawableRef() {
        return impactDrawableRef;
    }

    public ObstacleType setImpactDrawableRef(String impactDrawableRef) {
        this.impactDrawableRef = impactDrawableRef;
        return this;
    }

    public int getScoreValue() {
        return scoreValue;
    }

    public ObstacleType setScoreValue(int scoreValue) {
        this.scoreValue = Math.abs(scoreValue);
        return this;
    }

    public int getLivesValue() {
        return livesValue;
    }

    public ObstacleType setLivesValue(int livesValue) {
        this.livesValue = Math.abs(livesValue);
        return this;
    }

    @Override
    public String toString() {
        return "ObstacleType{" +
                "type=" + type +
                ", name='" + name + '\'' +
                ", image='" + drawableRef + '\'' +
                ", scoreValue=" + scoreValue +
                ", livesValue=" + livesValue +
                '}';
    }
}