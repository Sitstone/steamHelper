package com.example.yaowu.steamhelper.db;

import com.example.yaowu.steamhelper.util.Timeconvert;

import org.litepal.crud.DataSupport;

/**
 * Created by yaowu on 2017/5/25.
 */

public class Achievements extends DataSupport{

    private Game game;
    private int achieved;
    private String unlocktime;
    private String achievementName;
    private String description;

    public int getAchieved() {
        return achieved;
    }

    public void setAchieved(int achieved) {
        this.achieved = achieved;
    }

    public String getUnlocktime() {
        return unlocktime;
    }

    public void setUnlocktime(String unlocktime) {
        this.unlocktime = unlocktime == "0" ? "" :Timeconvert.converTime(unlocktime);
    }

    public String getAchievementName() {
        return achievementName;
    }

    public void setAchievementName(String achievementName) {
        this.achievementName = achievementName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
