package com.example.yaowu.steamhelper;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;

import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.yaowu.steamhelper.Adapter.AchievementsAdapter;
import com.example.yaowu.steamhelper.db.Achievements;
import com.example.yaowu.steamhelper.db.Game;
import com.example.yaowu.steamhelper.util.HttpUtil;
import com.example.yaowu.steamhelper.util.Utility;

import java.io.IOException;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static org.litepal.LitePalApplication.getContext;

/**
 * A simple {@link Fragment} subclass.
 */
public class AchievementsActivity extends AppCompatActivity{

    private TextView gameNameAch;
    private TextView achievementProgress;
    private ImageView gameIconAch;

    private ProgressDialog ach_progressDialog;



    private TextView titleText;

    private ListView achlistView;
    private List<Achievements> achievementsList;
    private AchievementsAdapter achievementsAdapters;

    private Game selectedGame;




    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.achievement_list);

        achlistView = (ListView) findViewById(R.id.achievement_listview);
        //receive selceted game
        selectedGame = (Game) getIntent().getSerializableExtra("SelectedGame");
        queryAchievement(selectedGame);

    }

    private void initTitle(){


        gameNameAch = (TextView) findViewById(R.id.game_name_ach);
        achievementProgress = (TextView) findViewById(R.id.achievements_progress);
        gameIconAch = (ImageView) findViewById(R.id.game_icon_ach);
        titleText = (TextView) findViewById(R.id.ach_title_text);

        titleText.setText("Achievements");
        gameNameAch.setText(selectedGame.getGameName());
        achievementProgress.setText(achievementProgress.getText() + String.valueOf(selectedGame.getAcquiredAch()) + "/" + achievementsList.size());
        String address = "http://media.steampowered.com/steamcommunity/public/images/apps/" + selectedGame.getAppid() +
                "/" + selectedGame.getIconhash() + ".jpg";
        Glide.with(this).load(address).into(gameIconAch);

    }



    /**
     * query achievements for each game
     * @param game
     */
    private void queryAchievement(Game game){

        achievementsList = game.getAchievements();

        if(achievementsList.size() == 0){
            String address = "http://api.steampowered.com/ISteamUserStats/GetPlayerAchievements/v0001/?appid=" + game.getAppid() +
                    "&key=" + GameListFragment.PERSONAL_KEY + "&steamid=" + GameListFragment.STEAM_ID + "&l=en";
            queryAchievementsFromServer(game, address);
        }else{


            achievementsList = selectedGame.getAchievements();
            achievementsAdapters = new AchievementsAdapter(AchievementsActivity.this, R.layout.achievement_item,achievementsList);
            achlistView.setAdapter(achievementsAdapters);
            achlistView.setSelection(0);
            initTitle();
        }



    }
    private void queryAchievementsFromServer(final Game game, String address){
       // MainActivity.showProgressDialog(this,ach_progressDialog);

        HttpUtil.sendOkHttpRequest(address, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(getContext(),"Sorry, this game doesn't have any achievement", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText = response.body().string();
                boolean result = false;

                result = Utility.handleGameAchievements(game, responseText);


                if(result){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                        queryAchievement(game);
                        }
                    });
                }

            }
        });
    }

}
