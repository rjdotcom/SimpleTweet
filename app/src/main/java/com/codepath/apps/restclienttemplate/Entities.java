package com.codepath.apps.restclienttemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

@Parcel
public class Entities {
    public String mediaUrl;

    public Entities() {}

    public static Entities fromJson(JSONObject jsonObject) throws JSONException{
        Entities entities = new Entities();

        if (!jsonObject.has("media")){
            entities.mediaUrl ="";
        }else if(jsonObject.has("media")){

            final JSONArray mediaArr =  jsonObject.getJSONArray("media");
            entities.mediaUrl=  mediaArr.getJSONObject(0).getString("media_url_https");

        }
        return entities;
    }
}
