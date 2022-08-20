package com.codepath.apps.restclienttemplate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;

import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

public class TimelineActivity extends AppCompatActivity {
    TwitterClient client;
    RecyclerView rvTweets;
    List<Tweet> tweets;
    TweetsAdapter adapter;
    SwipeRefreshLayout swipeContainer;
    EndlessRecyclerViewScrollListener scrollListener;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.create_tweet,menu);
        return super.onCreateOptionsMenu(menu);
    }

    public static final String  TAG ="TimelineActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
         client = TwitterApp.getRestClient(this);

         Toolbar toolbar = findViewById(R.id.toolBar);
         setSupportActionBar(toolbar);

         //Display toolbar's icons
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.ic_twitter);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        getSupportActionBar().setTitle(" ");


            swipeContainer = findViewById(R.id.swipeContainer);
        // Configure the refreshing colors
            swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
            swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
             @Override
             public void onRefresh() {
                 Log.i(TAG, "fetching new data");
                 populateHometimeline();

             }
         });


//         find the recyclerview
        rvTweets = findViewById(R.id.rvTweets);
//        init the list of tweets and adapter
        tweets = new ArrayList<>();
        adapter = new TweetsAdapter(this,tweets);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
//        recyclerview setup: layout manager and adapter
        rvTweets.setLayoutManager(layoutManager);
        rvTweets.setAdapter(adapter);
        scrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                        Log.i(TAG, "onLoadMore");
                        loadMoreData();
            }
        };
        // Adds the scroll listener to RecyclerView
        rvTweets.addOnScrollListener(scrollListener);

         populateHometimeline();


    }

    private void loadMoreData() {
        // Send an API request to retrieve appropriate paginated data
        client.getNextPageOfTweets(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.i(TAG,"onSuccess Load more data" +json.toString());

                //  --> Deserialize and construct new model objects from the API response
                JSONArray jsonArray = json.jsonArray;
                try {
                    List<Tweet> tweets = Tweet.fromJsonArray(jsonArray);
                    //  --> Append the new data objects to the existing set of items inside the array of items
                    //  --> Notify the adapter of the new items made with `notifyItemRangeInserted()`
                    adapter.addAll(tweets);
                } catch (JSONException e) {
                    e.printStackTrace();
                }



            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.e(TAG,"onFailure Load more data", throwable );

            }
        },tweets.get(tweets.size() -1).id);

    }

    private void populateHometimeline() {
        client.getHomeTimeline(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.i(TAG,"onSuccess!");
                JSONArray jsonArray = json.jsonArray;
                try {
                    adapter.clear();
                    adapter.addAll( Tweet.fromJsonArray(jsonArray));
                    // Now we call setRefreshing(false) to signal refresh has finished
                    swipeContainer.setRefreshing(false);

                } catch (JSONException e) {
                    Log.e(TAG,"json exception",e);

                }


            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.e(TAG,"onFailure!",throwable );
            }
        });
    }
}