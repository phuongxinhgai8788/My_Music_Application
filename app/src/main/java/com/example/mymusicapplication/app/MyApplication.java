package com.example.mymusicapplication.app;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import androidx.lifecycle.ViewModelProvider;

import com.example.mymusicapplication.data_source.MyMediaCursor;
import com.example.mymusicapplication.music_player.MyMusicPlayer;
import com.example.mymusicapplication.repository.PlayingStatus;
import com.example.mymusicapplication.repository.SongStatus;
import com.example.mymusicapplication.utils.BitmapLoader;
import com.example.mymusicapplication.utils.Constants;


public class MyApplication extends Application {
    public static ViewModelProvider.Factory factory;

    @Override
    public void onCreate() {
        super.onCreate();
        factory = (ViewModelProvider.Factory) new ViewModelProvider.AndroidViewModelFactory(this);
        setUp();
    }

    //setup nen bo vao splash activity or customApplication
    private void setUp(){
        PlayingStatus.initialize(this);
        MyMediaCursor.initialize(this);
        SongStatus.initialize(this);
        MyMusicPlayer.initialize(this);
        BitmapLoader.initialize(this);
        initNotificationChannel();
    }

    //setup nen bo vao splash activity or customApplication
    private void initNotificationChannel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(Constants.NOTIFICATION_CHANNEL_ID, Constants.NOTIFICATION_CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
