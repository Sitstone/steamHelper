package com.example.yaowu.steamhelper.db;

import com.example.yaowu.steamhelper.util.Timeconvert;

import org.litepal.crud.DataSupport;

/**用户信息
 * Created by yaowu on 2017/5/25.
 */

public class UserInfo extends DataSupport{
    private String steamid;
    private String personaname;
    private String realname;
    private String loccountrycode;
    private String locstatecode;
    private String loccityid;
    private String profileurl;
    private String timecreated;
    private String lastlogofftime;
    private String avatarurl;


    public String getSteamid() {
        return steamid;
    }

    public void setSteamid(String steamid) {
        this.steamid = steamid;
    }

    public String getPersonaname() {
        return personaname;
    }

    public void setPersonaname(String personaname) {
        this.personaname = personaname;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getLoccountrycode() {
        return loccountrycode;
    }

    public void setLoccountrycode(String loccountrycode) {
        this.loccountrycode = loccountrycode;
    }

    public String getLocstatecode() {
        return locstatecode;
    }

    public void setLocstatecode(String locstatecode) {
        this.locstatecode = locstatecode;
    }

    public String getLoccityid() {
        return loccityid;
    }

    public void setLoccityid(String loccityid) {
        this.loccityid = loccityid;
    }

    public String getProfileurl() {
        return profileurl;
    }

    public void setProfileurl(String profileurl) {
        this.profileurl = profileurl;
    }

    public String getTimecreated() {
        return timecreated;
    }

    public void setTimecreated(String timecreated) {
        this.timecreated = Timeconvert.converTime(timecreated);
    }

    public String getLastlogofftime() {
        return lastlogofftime;
    }

    public void setLastlogofftime(String lastlogofftime) {
        this.lastlogofftime = Timeconvert.converTime(lastlogofftime);
    }

    public String getAvatarurl() {
        return avatarurl;
    }

    public void setAvatarurl(String avatarurl) {
        this.avatarurl = avatarurl;
    }
}
