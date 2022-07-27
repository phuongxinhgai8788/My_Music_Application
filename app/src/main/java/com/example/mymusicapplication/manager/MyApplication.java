package com.example.mymusicapplication.manager;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.IntentFilter;
import android.os.Build;

import com.example.mymusicapplication.R;
import com.example.mymusicapplication.repository.Repository;
import com.example.mymusicapplication.sender_receiver_service_worker.LocalBroadcastReceiver;
import com.example.mymusicapplication.sender_receiver_service_worker.MusicWorker;
import com.example.mymusicapplication.sender_receiver_service_worker.MyMediaPlayer;

public class MyApplication extends Application {
    Repository repository;
    MyMediaPlayer myMediaPlayer;
    LocalBroadcastReceiver localBroadcastReceiver = new LocalBroadcastReceiver();
    public static final String NOTIFICATION_CHANNEL_ID = "notify_song_completion";

    @Override
    public void onCreate() {
        super.onCreate();
        initRepository();
        initMyMediaPlayer();
        createNotificationChannel();
        IntentFilter intentFilter = new IntentFilter(MusicWorker.ACTION_SHOW_NOTIFICATION);
        intentFilter.setPriority(-999);
        registerReceiver(localBroadcastReceiver, intentFilter, MusicWorker.PERMISSION_PRIVATE, null);
    }

    private void initMyMediaPlayer() {
        myMediaPlayer = MyMediaPlayer.getInstance(this);
    }

    private void initRepository(){
        repository = Repository.getInstance(this);
    }
    private void createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            String name = getString(R.string.notification_channel_name);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, name, importance);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        unregisterReceiver(localBroadcastReceiver);
    }
}
