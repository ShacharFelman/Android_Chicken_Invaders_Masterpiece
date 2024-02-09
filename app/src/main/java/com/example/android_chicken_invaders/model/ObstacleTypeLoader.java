package com.example.android_chicken_invaders.model;

import com.example.android_chicken_invaders.model.constants.AppConstants;
import com.example.android_chicken_invaders.model.entities.ObstacleType;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;

public class ObstacleTypeLoader {

    private static String FILE_NAME = AppConstants.OBSTACLE_TYPES_FILE;

    public static void setFileName(String fileName) {
        FILE_NAME = fileName;
    }

    public static Map<eObstacleTypes, List<ObstacleType>> loadObstacleTypes(Context context) {
        try {
            String json = loadJSONFromAsset(context, FILE_NAME);
            Type wrapperType = new TypeToken<ObstacleTypesWrapper>() {
            }.getType();
            ObstacleTypesWrapper wrapper = new Gson().fromJson(json, wrapperType);

            Map<eObstacleTypes, List<ObstacleType>> finalObstacleTypesMap = new HashMap<>();
            for (Map.Entry<String, List<ObstacleType>> entry : wrapper.obstacleTypes.entrySet()) {
                eObstacleTypes type = eObstacleTypes.valueOf(entry.getKey());
                finalObstacleTypesMap.put(type, entry.getValue());

            }

            List<ObstacleType> none = new ArrayList<>();
            none.add(new ObstacleType().setType(eObstacleTypes.NONE));
            finalObstacleTypesMap.put(eObstacleTypes.NONE, none);

            return finalObstacleTypesMap;
        }
        catch (Exception e) {
            return getDefaults();
        }
    }

    private static String loadJSONFromAsset(Context context, String fileName) {
        String json = null;
        try {
            InputStream is = context.getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return json;
    }

    private static Map<eObstacleTypes, List<ObstacleType>> getDefaults() {
        Map<eObstacleTypes, List<ObstacleType>> obstacleTypes = new HashMap<>();
        List<ObstacleType> obstacles = new ArrayList<>();
        List<ObstacleType> rewards = new ArrayList<>();

        List<ObstacleType> none = new ArrayList<>();
        none.add(new ObstacleType().setType(eObstacleTypes.NONE));

        obstacles.add(new ObstacleType(
                eObstacleTypes.OBSTACLE,
                "egg",
                "ic_egg",
                "gif_egg3",
                0,
                1
        ));

        rewards.add(new ObstacleType(
                eObstacleTypes.REWARD,
                "gift",
                "gif_gift3",
                "gif_firework1",
                50,
                0
        ));
        rewards.add(new ObstacleType(
                eObstacleTypes.REWARD,
                "lives",
                "ic_heart",
                "gif_firework3",
                0,
                1
        ));

        obstacleTypes.put(eObstacleTypes.NONE, none);
        obstacleTypes.put(eObstacleTypes.OBSTACLE, obstacles);
        obstacleTypes.put(eObstacleTypes.REWARD, rewards);

        return obstacleTypes;
    }



    private static class ObstacleTypesWrapper {
        Map<String, List<ObstacleType>> obstacleTypes;
    }
}