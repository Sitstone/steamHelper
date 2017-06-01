package com.example.yaowu.steamhelper.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.yaowu.steamhelper.ProfileWebView;
import com.example.yaowu.steamhelper.R;
import com.example.yaowu.steamhelper.db.Friend;
import com.example.yaowu.steamhelper.db.UserInfo;

import java.util.List;

/**
 * Created by yaowu on 2017/5/30.
 */

public class FriendsAdapter extends ArrayAdapter<Friend> {
    private int resourceID;

    public FriendsAdapter(Context context, int textViewResourceId, List<Friend> objects) {
        super(context, textViewResourceId, objects);

        resourceID = textViewResourceId;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Friend friend = getItem(position);
        View view;
        if(convertView == null){
            view = LayoutInflater.from(getContext()).inflate(resourceID, parent, false);
        }else{
            view = convertView;
        }

        ImageView friend_avatar = (ImageView) view.findViewById(R.id.friend_avatar);
        TextView friend_pname = (TextView) view.findViewById(R.id.friend_pname);
        TextView friend_rname = (TextView) view.findViewById(R.id.friend_rname);
        TextView friend_time_created = (TextView) view.findViewById(R.id.friend_time_created);
        TextView friend_last_logoff = (TextView) view.findViewById(R.id.friend_last_logoff);
        TextView friend_location = (TextView) view.findViewById(R.id.friend_location);
        Button friend_profile_url = (Button) view.findViewById(R.id.friend_profile_url);

        final UserInfo friend_info = friend.getFriendinfo();

        if(friend_info != null){
            Glide.with(getContext()).load(friend_info.getAvatarurl()).into(friend_avatar);
            friend_pname.setText(friend_info.getPersonaname());
            friend_rname.setText(friend_info.getRealname());
            friend_time_created.setText("Created Time: " + friend_info.getTimecreated());
            friend_last_logoff.setText("Last Log off Time: " + friend_info.getLastlogofftime());
            friend_location.setText("Location: " + friend_info.getLoccityid() + " " + friend_info.getLocstatecode() + " " + friend_info.getLoccountrycode());

            friend_profile_url.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getContext().startActivity(new Intent(getContext(), ProfileWebView.class).putExtra("profile_url", friend_info.getProfileurl()));
                }
            });
        }



        return view;

    }
}
