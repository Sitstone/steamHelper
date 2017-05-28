package com.example.yaowu.steamhelper.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.yaowu.steamhelper.GameListFragment;
import com.example.yaowu.steamhelper.R;
import com.example.yaowu.steamhelper.db.Achievements;
import com.example.yaowu.steamhelper.db.Game;
import com.example.yaowu.steamhelper.util.LoadImage;

import java.util.List;

/**
 * Created by yaowu on 2017/5/26.
 */

public class GameAdapter extends ArrayAdapter {
    private int resourceID;
   // private SharedPreferences sharedPreferences;

    public GameAdapter(Context context, int textViewResourceId, List<Game> objects) {
        super(context, textViewResourceId, objects);
      //  sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        resourceID = textViewResourceId;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Game game = (Game) getItem(position);
        View view;
        if(convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceID, parent, false);
        }else{
            view = convertView;
        }
        ImageView game_icon_view = (ImageView)view.findViewById(R.id.game_icon);
        TextView achievementName = (TextView)view.findViewById(R.id.game_name);
        TextView achievementUnlockTime = (TextView)view.findViewById(R.id.play_time);


        String address = "http://media.steampowered.com/steamcommunity/public/images/apps/" + game.getAppid() +
                         "/" + game.getIconhash() + ".jpg";

        Glide.with(getContext()).load(address).into(game_icon_view);

        achievementName.setText(game.getGameName() + "(AppID: " + game.getAppid() + ")");

        achievementUnlockTime.setText("Play Time: " + game.getPlayTime());

        return view;
    }


}

