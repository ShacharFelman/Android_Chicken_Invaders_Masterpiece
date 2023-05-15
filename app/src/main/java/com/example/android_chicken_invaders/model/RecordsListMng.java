package com.example.android_chicken_invaders.model;

import com.example.android_chicken_invaders.model.entities.GameRecord;
import com.example.android_chicken_invaders.utils.SharedPreference;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class RecordsListMng {
    private final String SP_RECORDS = "SP_RECORDS";
    private final int MAX_RECORDS = 10;
    private static RecordsListMng instance = null;
    private ArrayList<GameRecord> topRecords;

    public RecordsListMng() {
        this.topRecords = loadData();

        if (topRecords == null)
            this.topRecords = new ArrayList<>();

        sortRecords();
    }

    public static RecordsListMng getInstance() {
        if (instance == null)
            instance = new RecordsListMng();

        return instance;
    }

    public ArrayList<GameRecord> getTopRecords() {
        return topRecords;
    }

    public void addRecord(GameRecord newRecord){
        if(topRecords.size() < MAX_RECORDS) {
            topRecords.add(newRecord);
            saveData();
        }
        else {

            GameRecord lastRecord = topRecords.get(topRecords.size()-1);
            if (lastRecord.getScore() < newRecord.getScore()){
                topRecords.remove(lastRecord);
                topRecords.add(newRecord);
                saveData();
            }
        }
        sortRecords();
    }

    public ArrayList<GameRecord> loadData() {
        Type typeMyType = new TypeToken<ArrayList<GameRecord>>(){}.getType();
        String recordsJson = SharedPreference.getInstance().getString(SP_RECORDS, "");
        return new Gson().fromJson(recordsJson, typeMyType);
    }

    private void saveData() {
        sortRecords();
        String recordsJson = new Gson().toJson(topRecords);
        SharedPreference.getInstance().putString(SP_RECORDS, recordsJson);
    }

    private void sortRecords() {
        topRecords.sort((r1, r2) -> r2.getScore() - r1.getScore());
    }
}
