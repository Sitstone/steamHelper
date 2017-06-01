package com.example.yaowu.steamhelper;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.example.yaowu.steamhelper.Adapter.GameAdapter;
import com.example.yaowu.steamhelper.db.Game;
import com.example.yaowu.steamhelper.db.UserInfo;
import com.example.yaowu.steamhelper.util.HttpUtil;
import com.example.yaowu.steamhelper.util.NavigationIniti;
import com.example.yaowu.steamhelper.util.Utility;


import org.litepal.crud.DataSupport;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by yaowu on 2017/5/24.
 */

public class GameListFragment extends Fragment {

    public static final String PERSONAL_KEY= "B3E3B6D8121A4CD4437F92C2BD098ECC";
    public static final String STEAM_ID = "76561198251057530";



    private ProgressDialog progressDialog;




    private ListView listView;

    private List<Game> gamelist;

    private GameAdapter gameAdapter;

    //选中的游戏
    private Game selectedGame;

    //My steam profiel
    private UserInfo mySteam;

    private View view;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.game_list,container,false);
        listView = (ListView) view.findViewById(R.id.game_listview);
        return view;
    }



    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedGame = gamelist.get(position);
                Intent intent = new Intent(getActivity(), AchievementsActivity.class);
                intent.putExtra("SelectedGame", selectedGame);
                startActivity(intent);
            }
        });

        queryMyInfo();
        queryGame();
    }




    /**
     * initialize the title of Game Activity(User info)
     */
    private void initiTitle(){

        ImageView avatar = (ImageView) view.findViewById(R.id.user_avatar);
        TextView personal_name = (TextView) view.findViewById(R.id.personal_name);
        TextView real_name = (TextView) view.findViewById(R.id.real_name);
        TextView time_created = (TextView) view.findViewById(R.id.time_created);
        TextView last_logoff = (TextView) view.findViewById(R.id.last_logoff);
        TextView location = (TextView) view.findViewById(R.id.location);
        Button profile_url = (Button) view.findViewById(R.id.profile_url);

        Glide.with(this).load(mySteam.getAvatarurl()).into(avatar);
        personal_name.setText(mySteam.getPersonaname());
        real_name.setText(mySteam.getRealname());
        time_created.setText("Created Time: " + mySteam.getTimecreated());
        last_logoff.setText("Last Log off Time: " + mySteam.getLastlogofftime());
        location.setText("Location: " + mySteam.getLoccityid() + " " + mySteam.getLocstatecode() + " " + mySteam.getLoccountrycode());

        profile_url.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ProfileWebView.class).putExtra("profile_url", mySteam.getProfileurl()));
            }
        });


    }
    /**
     * 查询游戏信息
     */
    private void queryGame(){

        gamelist = DataSupport.findAll(Game.class);

        if(gamelist.size() == 0){
            String address = "http://api.steampowered.com/IPlayerService/GetOwnedGames/v0001/?key=" + PERSONAL_KEY +
                    "&include_appinfo=1&steamid=" + STEAM_ID + "&format=json";
            queryGameFromServer(address);
        }

        gameAdapter = new GameAdapter(getContext(), R.layout.game_item, gamelist);
        listView.setAdapter(gameAdapter);
        listView.setSelection(0);
    }
    //private void gameDetail()

    private void queryGameFromServer(String address){

       // MainActivity.showProgressDialog(getContext(), progressDialog);

        HttpUtil.sendOkHttpRequest(address, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText = response.body().string();
                boolean result = false;

                result = Utility.handlePersonalGameListResponse(responseText);
                if(result){
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                          //  MainActivity.closeProgressDialog(progressDialog);
                            queryGame();
                        }
                    });
                }
              //  MainActivity.closeProgressDialog(progressDialog);
            }
        });
    }

    /**
     * query user steam info
     */
    private void queryMyInfo(){
        List<UserInfo> tmplist = DataSupport.where("steamid = ?", STEAM_ID).find(UserInfo.class);

        if(tmplist.size() == 0){
            String address = "http://api.steampowered.com/ISteamUser/GetPlayerSummaries/v0002/?key=" +
                    PERSONAL_KEY + "&steamids=" + STEAM_ID;
            queryMyInfoFromServer(address);
        }else{
            mySteam = tmplist.get(0);
            initiTitle();
        }
    }

    private void queryMyInfoFromServer(String address){
        HttpUtil.sendOkHttpRequest(address, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(getContext(), "Load User Info. failed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText = response.body().string();
                boolean result = Utility.handleUserInfo(responseText);
                if(result){
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            queryMyInfo();
                        }
                    });

                }
            }
        });

    }





}
