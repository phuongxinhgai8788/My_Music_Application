package com.example.mymusicapplication.manager;

import android.app.Application;
import android.util.Log;

import com.example.mymusicapplication.repository.Repository;
import com.example.mymusicapplication.sender_receiver_service.MyMediaPlayer;

public class MyApplication extends Application {
    Repository repository;
    MyMediaPlayer myMediaPlayer;
    @Override
    public void onCreate() {
        super.onCreate();
        initRepository();
        initMyMediaPlayer();
        createNotificationChannel();
    }

    private void initMyMediaPlayer() {
        myMediaPlayer = MyMediaPlayer.getInstance(this);
    }

    private void initRepository(){
        repository = Repository.getInstance(this);
    }
    private void createNotificationChannel(){

    }
}
