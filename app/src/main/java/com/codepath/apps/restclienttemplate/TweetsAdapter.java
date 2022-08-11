package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.codepath.apps.restclienttemplate.models.Tweet;

import java.util.ArrayList;
import java.util.List;

public class TweetsAdapter  extends  RecyclerView.Adapter<TweetsAdapter.ViewHolder>{
    Context context;
    List<Tweet> tweets;

    //    pass in the context and list of tweets
    public TweetsAdapter(Context context, List<Tweet> tweets) {
        this.context = context;
        this.tweets = tweets;
    }

    //    for each row inflate the layout
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_tweet,parent,false);
        return new ViewHolder(view);
    }
    //    bind values based on position
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        Get the data at position
    Tweet tweet = tweets.get(position);
//        Bind the tweet with the view holder
        holder.bind(tweet);

    }

    @Override
    public int getItemCount() {  return tweets.size();}
//Clear all elements of the recycler
    public void clear(){
        tweets.clear();
        notifyDataSetChanged();
    }

//    add a list of items
    public void addAll(List<Tweet> tweetList){

        tweets.addAll(tweetList);
        notifyDataSetChanged();

    }




//    create viewHolder
    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView ivProfileImage;
        TextView tvScreenName;
        TextView tvBody;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);

        ivProfileImage = itemView.findViewById(R.id.ivProfileImage);
        tvScreenName = itemView.findViewById(R.id.tvScreenName);
        tvBody = itemView.findViewById(R.id.tvBody);
    }

    public void bind(Tweet tweet) {
        tvBody.setText(tweet.body);
        tvScreenName.setText(tweet.user.screenName);
        Glide.with(context).load(tweet.user.profileImageUrl).transform(new RoundedCorners(90)).into(ivProfileImage);

    }
}

}
