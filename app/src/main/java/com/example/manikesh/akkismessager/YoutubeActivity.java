package com.example.manikesh.akkismessager;

import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import java.util.ArrayList;
import java.util.List;

public class YoutubeActivity extends YouTubeBaseActivity {
// THE ABOVE EXTENSION for using the YOUTUBE API which is what we installed in our lib folder earliar.
    private static  final String TAG="MainActivity";

  YouTubePlayerView myoutubePlayaerView;
    Button btnPlay;
    YouTubePlayer.OnInitializedListener mOnitialzedListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube);

        Log.d(TAG, "onCreate: Starting.. ");
// used to show us a message on the view created.
        btnPlay = (Button) findViewById(R.id.playbtn);
        myoutubePlayaerView = (YouTubePlayerView) findViewById(R.id.view2);

// initialize the controls
        // the below methods are auto generated they hve on succees and on failure message accordingly
        mOnitialzedListener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
// when the video has successfully played it enters this message reads the below code and plays the loaded list of videos.
                Log.d(TAG, "onClick: done initializing");

                List<String> videoList = new ArrayList<>();
                videoList.add("RDC8PFV_rkW9o");
                videoList.add("9MJAg0VDgO0");

               youTubePlayer.loadVideos(videoList);
                // this is one way of adding a list of videos you can also use playlist instead.

               //youTubePlayer.loadPlaylist("https://youtu.be/9MJAg0VDgO0");
                // by the above way you can actualy play the entirre playlist when you have a playlist on your
                // youtube channel.

            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
// if in case the initialize of the key ven in the oter class failed to respond then this particular blck of code gets
                // executed and shows the above log on the screen
                Log.d(TAG, "onClick: failed to initializing");
            }
        };

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: initializing youtube player");

              myoutubePlayaerView.initialize(YouTubeConfig.getApiKey(),mOnitialzedListener);

               // Log.d(TAG, "onClick: initializing youtube player");







            }
        });


    }
}
