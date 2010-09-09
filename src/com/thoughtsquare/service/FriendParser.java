package com.thoughtsquare.service;

import com.thoughtsquare.domain.Friend;
import com.thoughtsquare.domain.LocationEvent;
import com.thoughtsquare.domain.User;
import com.thoughtsquare.utility.JSONArray;
import com.thoughtsquare.utility.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FriendParser {

    public List<Friend> parseFriends(String json) {

        ArrayList<Friend> friends = new ArrayList<Friend>();
        JSONArray array = new JSONArray(json);

        for(int i=0; i < array.length(); i++){
            JSONObject jsonFriend = array.getJSONObject(i).getJSONObject("user");

            Friend friend = new Friend(jsonFriend.getInt("id"), jsonFriend.getString("display_name"), jsonFriend.getString("email"), jsonFriend.getString("mobile_number"));
            friends.add(friend);
        }

        return friends;
    }



}
