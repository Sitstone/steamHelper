package com.example.yaowu.steamhelper.util;

import android.text.TextUtils;
import android.util.JsonReader;
import android.util.Log;

import com.example.yaowu.steamhelper.db.Achievements;
import com.example.yaowu.steamhelper.db.Friend;
import com.example.yaowu.steamhelper.db.Game;
import com.example.yaowu.steamhelper.db.UserInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

/**
 * Created by yaowu on 2017/5/24.
 */


public class Utility {

    /**
     * 解析和处理服务器返回的数据
     */

    public static boolean handlePersonalGameListResponse(String response){

        if(!TextUtils.isEmpty(response)){
            try{
                JSONObject content = new JSONObject(response);

                JSONArray allGames = content.getJSONObject("response").getJSONArray("games");

                for(int i = 0; i < allGames.length(); i++){
                    JSONObject gameObject = allGames.getJSONObject(i);
                    Game game = new Game();
                    game.setAppid(gameObject.getInt("appid"));
                    game.setGameName(gameObject.getString("name"));
                    game.setPlayTime(gameObject.getDouble("playtime_forever"));
                    game.setIconhash(gameObject.getString("img_icon_url"));
                    game.save();
                }

                return true;

            }catch (JSONException e){
                e.printStackTrace();
            }
        }
        return false;
    }

    public static boolean handleFriendListResponse(String response){
        try {
            JSONObject content = new JSONObject(response);
            JSONArray allfriends = content.getJSONObject("friendslist").getJSONArray("friends");

            for(int i = 0; i < allfriends.length(); i++){
                JSONObject friendObject = allfriends.getJSONObject(i);
                Friend friend = new Friend();
                friend.setFriendsteamid(friendObject.getString("steamid"));
                friend.setFriendsince(friendObject.getString("friend_since"));
                friend.save();
            }
            return true;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return false;
    }


    /**
     * store achievements of each game
     * @param game
     * @param response
     * @return
     */

    public static boolean handleGameAchievements(Game game, String response){


        try {

            JSONObject content = new JSONObject(response);
            //this game doesn't have achievements
            if(!content.getJSONObject("playerstats").getBoolean("success")) return false;

            JSONArray all_achievements = content.getJSONObject("playerstats").getJSONArray("achievements");

            for(int i = 0; i < all_achievements.length(); i++){
                JSONObject achievementObject = all_achievements.getJSONObject(i);
                Achievements achievements = new Achievements();

                achievements.setAchieved(achievementObject.getInt("achieved"));
                achievements.setAchievementName(achievementObject.getString("name"));
                achievements.setDescription(achievementObject.getString("description"));
                achievements.setUnlocktime(achievementObject.getString("unlocktime"));
                game.setAchievements(achievements);
                achievements.save();
            }

            return true;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Store user info.
     * @param response
     * @return
     */
    public static boolean handleUserInfo(String response){
        try{
            JSONObject content = new JSONObject(response);
            JSONArray my_profile = content.getJSONObject("response").getJSONArray("players");

            for(int i = 0; i < my_profile.length(); i++){
                JSONObject userObeject = my_profile.getJSONObject(i);
                UserInfo userInfo = new UserInfo();
                userInfo.setPersonaname(userObeject.getString("personaname"));
                userInfo.setSteamid(userObeject.getString("steamid"));
                userInfo.setLastlogofftime(userObeject.getString("lastlogoff"));
                userInfo.setProfileurl(userObeject.getString("profileurl"));
                userInfo.setAvatarurl(userObeject.getString("avatarfull"));
                userInfo.setRealname(userObeject.optString("realname"));
                userInfo.setTimecreated(userObeject.getString("timecreated"));
                userInfo.setLoccountrycode(userObeject.optString("loccountrycode"));
                userInfo.setLocstatecode(userObeject.optString("locstatecode"));
                userInfo.setLoccityid(userObeject.optString("loccityid"));
                userInfo.save();
            }
            return true;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }
}
