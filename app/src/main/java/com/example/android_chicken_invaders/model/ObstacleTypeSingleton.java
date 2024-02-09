package com.example.android_chicken_invaders.model;

import com.example.android_chicken_invaders.model.entities.ObstacleType;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;


public class ObstacleTypeSingleton {
    private static ObstacleTypeSingleton instance = null;
    private final Map<eObstacleTypes, List<ObstacleType>> obstacleTypes;
    private final Random random = new Random();


    private ObstacleTypeSingleton(Map<eObstacleTypes, List<ObstacleType>> obstacleTypes) {
        this.obstacleTypes = obstacleTypes;
    }

    public static void init(Map<eObstacleTypes, List<ObstacleType>> obstacleTypes) {
        if (instance == null) {
            instance = new ObstacleTypeSingleton(obstacleTypes);
        }
    }

    public static ObstacleTypeSingleton getInstance() {
        return instance;
    }

    public Map<eObstacleTypes, List<ObstacleType>> getObstacleTypes() {
        return obstacleTypes;
    }

    public ObstacleType getObstacleType(eObstacleTypes type, int index) {
        return Objects.requireNonNull(obstacleTypes.get(type)).get(index);
    }

    public ObstacleType getNone() {
        return Objects.requireNonNull(obstacleTypes.get(eObstacleTypes.NONE)).get(0);
    }

    public ObstacleType getRandomObstacleType(eObstacleTypes type) {
        List<ObstacleType> obstacleTypes = this.obstacleTypes.get(type);

        if (obstacleTypes == null || obstacleTypes.isEmpty()) {
            return this.getNone();
        }

        int randomIndex = random.nextInt(obstacleTypes.size());
        return obstacleTypes.get(randomIndex);
    }
}