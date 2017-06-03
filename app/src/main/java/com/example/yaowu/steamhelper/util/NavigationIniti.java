package com.example.yaowu.steamhelper.util;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;

import com.example.yaowu.steamhelper.FriendsActivity;
import com.example.yaowu.steamhelper.MainActivity;
import com.example.yaowu.steamhelper.PriceActivity;
import com.example.yaowu.steamhelper.R;
import com.example.yaowu.steamhelper.db.Achievements;
import com.example.yaowu.steamhelper.db.Friend;
import com.example.yaowu.steamhelper.db.Game;
import com.example.yaowu.steamhelper.db.UserInfo;

import org.litepal.crud.DataSupport;

/**
 * Created by yaowu on 2017/5/30.
 */

public class NavigationIniti {

    public static void initiNavView(NavigationView navigationView, final Activity activity, final DrawerLayout drawerLayout){
        //register nav bar and events

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemid = item.getItemId();
                switch (itemid){
                    case (R.id.nav_friendslist):
                        item.setChecked(true);
                        Intent friendAc_intent = new Intent(activity, FriendsActivity.class);
                        activity.startActivity(friendAc_intent);
                        drawerLayout.closeDrawers();
                        break;
                    case (R.id.nav_gamelist):
                        item.setChecked(true);
                        Intent gameAc_intent = new Intent(activity, MainActivity.class);
                        activity.startActivity(gameAc_intent);
                        drawerLayout.closeDrawers();
                        break;
                    case (R.id.nav_deleteall):
                        item.setChecked(true);
                        DataSupport.deleteAll(Game.class);
                        DataSupport.deleteAll(Friend.class);
                        DataSupport.deleteAll(Achievements.class);
                        DataSupport.deleteAll(UserInfo.class);
                        break;
                    case (R.id.nav_price):
                        item.setChecked(true);
                        Intent price_intent = new Intent(activity, PriceActivity.class);
                        activity.startActivity(price_intent);
                        drawerLayout.closeDrawers();

                    default:

                }
                return true;
            }
        });
    }
}