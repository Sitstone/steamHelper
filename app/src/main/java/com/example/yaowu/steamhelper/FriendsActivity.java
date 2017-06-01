package com.example.yaowu.steamhelper;

import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yaowu.steamhelper.Adapter.FriendsAdapter;
import com.example.yaowu.steamhelper.db.Friend;
import com.example.yaowu.steamhelper.db.UserInfo;
import com.example.yaowu.steamhelper.util.HttpUtil;
import com.example.yaowu.steamhelper.util.NavigationIniti;
import com.example.yaowu.steamhelper.util.Utility;

import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class FriendsActivity extends AppCompatActivity {

    private FriendsAdapter friendsAdapter;
    private List<Friend> friendList;

    private ListView friend_list_view;
    private NavigationView navigationView;
    private DrawerLayout friends_drawerLayout;
    private TextView titletext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friend_list);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //set navigation menu
        friends_drawerLayout = (DrawerLayout)findViewById(R.id.friend_drawer_layout);
        //set navigation button
        toolbar.setNavigationIcon(R.drawable.castlecrahsers);
        getSupportActionBar().setTitle("Friends");

        //load navi bar
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        NavigationIniti.initiNavView(navigationView, this, friends_drawerLayout);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                friends_drawerLayout.openDrawer(GravityCompat.START);
            }
        });
        //initi list view
        friend_list_view = (ListView) findViewById(R.id.friend_listview);

        queryFriends();
    }

    private void queryFriends(){
        //find friends
        friendList = DataSupport.findAll(Friend.class);



        if(friendList.size() == 0 || friendList.get(0).getFriendinfo() == null){
            String address = "http://api.steampowered.com/ISteamUser/GetFriendList/v0001/?key=" + GameListFragment.PERSONAL_KEY
                    + "&steamid=" + GameListFragment.STEAM_ID + "&relationship=friend";
            queryFriendFromServer(address);

        }else{

            Log.d("friends size", String.valueOf(friendList.size()));

            friendsAdapter = new FriendsAdapter(FriendsActivity.this, R.layout.friend_item, friendList);
            friend_list_view.setAdapter(friendsAdapter);
            friend_list_view.setSelection(0);
        }
    }

    private void queryFriendFromServer(String address){
        HttpUtil.sendOkHttpRequest(address, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(FriendsActivity.this,"Failed to load friends list",Toast.LENGTH_SHORT);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText = response.body().string();
                boolean result = Utility.handleFriendListResponse(responseText);



                if(result){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.d("query:", " friends summary");
                            queryFriendsSummaryFromServer();
                        }
                    });
                }
            }
        });
    }


    /**
     * query friends summaries from server
     */
    private void queryFriendsSummaryFromServer(){

        friendList = DataSupport.findAll(Friend.class);

        //get address
        StringBuilder friendsSummary = new StringBuilder( "http://api.steampowered.com/ISteamUser/GetPlayerSummaries/v0002/?key="
                + GameListFragment.PERSONAL_KEY
                + "&steamids=");

        for(int i = 0; i < friendList.size()-1; i++){
            friendsSummary.append(friendList.get(i).getFriendsteamid() + "||steamids=");
        }
        friendsSummary.append(friendList.get(friendList.size()-1).getFriendsteamid());
        friendsSummary.append("&relationship=friend");

        Log.d("friends summaries", friendsSummary.toString());
        HttpUtil.sendOkHttpRequest(friendsSummary.toString(), new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(FriendsActivity.this, "Failed to get friends summary", Toast.LENGTH_SHORT).show();

                    }
                });

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText = response.body().string();
                boolean result = Utility.handleUserInfo(responseText);
                if(result){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            queryFriends();//these two operations will be done by two threads, so it will perform twice;
                        }
                    });
                }

            }
        });

    }
}
