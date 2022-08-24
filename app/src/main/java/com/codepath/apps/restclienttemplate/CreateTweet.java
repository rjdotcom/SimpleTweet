package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;

import org.json.JSONException;
import org.parceler.Parcels;

import okhttp3.Headers;
// ...

public class CreateTweet extends DialogFragment {
    public static String TAG = "ComposeActivity";

    public static final int MAX_TWEET_LENGTH = 140;
    EditText etCompose;
    Context context;
    Button btnTweet;
    TwitterClient client;
    private EditText mEditText;

    public CreateTweet() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }

    public static CreateTweet newInstance(String title) {
        CreateTweet frag = new CreateTweet();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.createtweet, container);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        client = TwitterApp.getRestClient(context);
        btnTweet = view.findViewById(R.id.btnTweet);
        // Get field from view
        mEditText = (EditText) view.findViewById(R.id.etCompose);
        // Fetch arguments from bundle and set title
        String title = getArguments().getString("title", "Enter Name");
        getDialog().setTitle(title);
        // Show soft keyboard automatically and request focus to field
        mEditText.requestFocus();
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        btnTweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tweetContent = etCompose.getText().toString();
                if (tweetContent.isEmpty()){
                    Toast.makeText(context, "Sorry your tweet cannot be empty", Toast.LENGTH_LONG).show();
                    return;
                }
                if(tweetContent.length()> MAX_TWEET_LENGTH){
                    Toast.makeText(context, "Sorry your tweet is too long", Toast.LENGTH_LONG).show();

                    return;

                }
                Toast.makeText(context,tweetContent, Toast.LENGTH_LONG).show();


//        Make an API call to Twitter to Publish the tweet
                client.publishTweet(tweetContent, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Headers headers, JSON json) {
                        Log.i(TAG, "onSuccess to publish");
                        try {
                            Tweet tweet = Tweet.fromJson(json.jsonObject);
                            Log.i(TAG, "published tweet" + tweet);
                            Intent intent = new Intent();
                            intent.putExtra("tweet", Parcels.wrap( tweet));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        dismiss();

                    }

                    @Override
                    public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                        Log.e(TAG, "onFailure to publish", throwable);
                    }
                });


            }

        });
    }
}

