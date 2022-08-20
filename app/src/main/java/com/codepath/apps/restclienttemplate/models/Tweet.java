package com.codepath.apps.restclienttemplate.models;

import com.codepath.apps.restclienttemplate.Entities;
import com.codepath.apps.restclienttemplate.TimeFormatter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;
@Parcel
public class Tweet {
    public String body;
    public Entities entities;
    public long id;
    public String createdAt;
    public User user;
    public int retweet;
    public int favorite;
    public boolean favorited;
    public boolean retweeted;

    public Tweet() {}

    public static Tweet fromJson(JSONObject jsonObject) throws JSONException {
        Tweet tweet = new Tweet();
        tweet.body = jsonObject.getString("text");
        tweet.createdAt  = jsonObject.getString("created_at");
        tweet.id = jsonObject.getLong("id");
        tweet.user = User.fromJson(jsonObject.getJSONObject("user"));
        tweet.retweet =  jsonObject.getInt("retweet_count");
        tweet.favorite = jsonObject.getInt("favorite_count");
        tweet.retweeted =  jsonObject.getBoolean("retweeted");
        tweet.favorited = jsonObject.getBoolean("favorited");
        tweet.entities = Entities.fromJson(jsonObject.getJSONObject("entities"));
        return tweet;
    }


    public static List<Tweet> fromJsonArray(JSONArray jsonArray) throws JSONException {
        List<Tweet> tweets = new ArrayList<>();
        for(int i=0 ; i < jsonArray.length(); i++){
            tweets.add(fromJson(jsonArray.getJSONObject(i)));
        }
        return tweets;

    }
    public static  String  getFormattedTimeStamp(String createdAt){
        return TimeFormatter.getTimeDifference(createdAt);
    }

    public static  String getFormattedTime (String createdAt){
        return TimeFormatter.getTimeStamp(createdAt);
    }

    public String getRetweet() {
        return String.valueOf(retweet);
    }

    public String getFavorite() {
        return String.valueOf(favorite);
    }
}
