package com.codepath.apps.restclienttemplate;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.codepath.apps.restclienttemplate.models.Tweet;

import org.parceler.Parcel;
import org.parceler.Parcels;

public class DetailActivity extends AppCompatActivity {
    ImageView ivProfile;
    TextView screenName1;
    TextView tvUserName1;
    TextView tvBody1;
    TextView tvdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ivProfile = findViewById(R.id.ivProfile);
        screenName1 = findViewById(R.id.screenName1);
        tvUserName1 =findViewById(R.id.tvUserName1);
        tvBody1 = findViewById(R.id.tvBody1);
        tvdate = findViewById(R.id.tvdate);

        Tweet tweet = Parcels.unwrap(getIntent().getParcelableExtra("Tweet"));
        screenName1.setText(tweet.user.name);
        tvUserName1.setText(tweet.user.screenName);
        tvBody1.setText(tweet.body);
        tvdate.setText(tweet.getFormattedTime(tweet.createdAt));
        Glide.with(this).load(tweet.user.profileImageUrl).transform(new RoundedCorners(90)).into(ivProfile);



    }
}