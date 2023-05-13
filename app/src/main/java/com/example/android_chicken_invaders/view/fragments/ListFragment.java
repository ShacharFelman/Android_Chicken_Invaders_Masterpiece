package com.example.android_chicken_invaders.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.android_chicken_invaders.R;
import com.example.android_chicken_invaders.model.RecordsListMng;
import com.example.android_chicken_invaders.model.entities.GameRecord;
import com.example.android_chicken_invaders.interfaces.Callback_List;

public class ListFragment extends Fragment {

    private ListView fragmentList_LV_scoreBoard;
    private Callback_List callback_list;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        findViews(view);
        initListViewScoreBoard();

        fragmentList_LV_scoreBoard.setOnItemClickListener((adapterView, view1, position, l) -> {
            double lat = RecordsListMng.getInstance().getTopRecords().get(position).getLat();
            double lng = RecordsListMng.getInstance().getTopRecords().get(position).getLng();
            String namePlayer = RecordsListMng.getInstance().getTopRecords().get(position).getUserId();
            callback_list.setMapLocation(lat,lng,namePlayer);
        });

        return view;
    }

    private void initListViewArray() {
        if(RecordsListMng.getInstance().getTopRecords() != null){
            ArrayAdapter<GameRecord> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_expandable_list_item_1, RecordsListMng.getInstance().getTopRecords());
            fragmentList_LV_scoreBoard.setAdapter(adapter);
        }
    }

    private void initListViewScoreBoard() {
        if (RecordsListMng.getInstance().getTopRecords() != null) {
            ScoreboardAdapter adapter = new ScoreboardAdapter(getActivity(), R.layout.list_item_scoreboard, RecordsListMng.getInstance().getTopRecords());
            fragmentList_LV_scoreBoard.setAdapter(adapter);
        }
    }

    private void findViews(View view){
        this.fragmentList_LV_scoreBoard = view.findViewById(R.id.fragmentList_LV_scoreBoard);
    }

    public void setCallback_list(Callback_List callback_list){
        this.callback_list = callback_list;
    }
}