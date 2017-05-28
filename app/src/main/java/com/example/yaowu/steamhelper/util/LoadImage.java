package com.example.yaowu.steamhelper.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.yaowu.steamhelper.db.Game;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by yaowu on 2017/5/26.
 */

public class LoadImage {

    /**
     * download images and store in SharedPre
     * @param address  image address
     * @param sharedPreferences
     */

    public static void loadPic(String address, final SharedPreferences sharedPreferences, final Game game){

        HttpUtil.sendOkHttpRequest(address, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String image = response.body().string();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(game.getGameName(), image);
                editor.apply();
            }
        });

    }
}
