package com.example.yaowu.steamhelper.db;

import com.example.yaowu.steamhelper.util.Timeconvert;

import org.litepal.crud.DataSupport;

/**
 * Created by yaowu on 2017/5/25.
 */

public class Friend extends DataSupport{
    private String friendname;
    private String friendsteamid;
    private String friendsince;



    public String getFriendname() {
        return friendname;
    }

    public void setFriendname(String friendname) {
        this.friendname = friendname;
    }

    public String getFriendsteamid() {
        return friendsteamid;
    }

    public void setFriendsteamid(String friendsteamid) {
        this.friendsteamid = friendsteamid;
    }

    public String getFriendsince() {
        return friendsince;
    }

    public void setFriendsince(String friendsince) {
        this.friendsince = Timeconvert.converTime(friendsince);
    }
}
