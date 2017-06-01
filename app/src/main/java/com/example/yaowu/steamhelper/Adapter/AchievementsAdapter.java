package com.example.yaowu.steamhelper.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yaowu.steamhelper.R;
import com.example.yaowu.steamhelper.db.Achievements;

import java.util.List;

/**
 * Created by yaowu on 2017/5/25.
 */

public class AchievementsAdapter extends ArrayAdapter<Achievements> {
    private int resourceID;

    public AchievementsAdapter(Context context, int textViewResourceId, List<Achievements> objects) {
        super(context, textViewResourceId, objects);

        resourceID = textViewResourceId;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Achievements achievements = getItem(position);
        View view;
        if(convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceID, parent, false);
        }else{
            view = convertView;
        }
        TextView achievementName = (TextView)view.findViewById(R.id.achievement_name);
        TextView achievementUnlockTime = (TextView)view.findViewById(R.id.unlock_time);
        TextView achievementDescription = (TextView)view.findViewById(R.id.description_ach);

        achievementName.setText(achievements.getAchievementName());

        achievementUnlockTime.setText("Unlock Time: " + achievements.getUnlocktime());

       //set Description
        achievementDescription.setText("Decription: " + achievements.getDescription());

        return view;
    }
}
