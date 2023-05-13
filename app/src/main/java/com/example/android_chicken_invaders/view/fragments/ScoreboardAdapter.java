package com.example.android_chicken_invaders.view.fragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.android_chicken_invaders.R;
import com.example.android_chicken_invaders.model.entities.GameRecord;

import java.util.ArrayList;

public class ScoreboardAdapter extends ArrayAdapter<GameRecord> {
    private ArrayList<GameRecord> records;

    public ScoreboardAdapter(Context context, int resource, ArrayList<GameRecord> objects) {
        super(context, resource, objects);
        this.records = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_scoreboard, parent, false);
        }

        TextView tv_rank = convertView.findViewById(R.id.tv_rank);
        TextView tv_score = convertView.findViewById(R.id.tv_score);
        TextView tv_name = convertView.findViewById(R.id.tv_name);

        GameRecord record = records.get(position);

        tv_rank.setText(String.valueOf(position + 1));
        tv_score.setText(String.valueOf(record.getScore()));
        tv_name.setText(record.getUserId());

        return convertView;
    }
}
