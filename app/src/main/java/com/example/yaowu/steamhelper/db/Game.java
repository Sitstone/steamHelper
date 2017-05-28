package com.example.yaowu.steamhelper.db;

import org.litepal.crud.DataSupport;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yaowu on 2017/5/24.
 */

public class Game extends DataSupport implements Serializable{
    private static final long serialVersionUID=1L;
    private int appid;
    private String gameName;
    private double playTime;
    private String iconhash;
    private int acquiredAch;
    private List<Achievements> achievements = new ArrayList<>();

    public List<Achievements> getAchievements() {
        return achievements;
    }

    public void setAchievements(Achievements achievement){
        this.achievements.add(achievement);
    }

    public int getAppid() {
        return appid;
    }

    public void setAppid(int appid) {
        this.appid = appid;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public double getPlayTime() {
        return playTime;
    }

    public void setPlayTime(double playTime) {
        this.playTime = (int)((playTime/60)*100)/100;
    }

    public String getIconhash() {
        return iconhash;
    }

    public void setIconhash(String iconhash) {
        this.iconhash = iconhash;
    }

    public int getAcquiredAch() {
        for(Achievements achievement: achievements) if(achievement.getAchieved() == 1) acquiredAch++;
        return acquiredAch;
    }


}
