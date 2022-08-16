package com.example.mymusicapplication.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.example.mymusicapplication.R;
import com.example.mymusicapplication.repository.PlayingStatus;
import com.example.mymusicapplication.utils.Constants;
import com.example.mymusicapplication.utils.MusicNotificationBuilder;

public class MusicService extends Service implements SharedPreferences.OnSharedPreferenceChangeListener{

    private NowPlayingViewModel nowPlayingViewModel = new NowPlayingViewModel();
    private MusicNotificationBuilder notificationBuilder;
    private String TAG = "MusicService";
    private PlayingStatus playingStatus = PlayingStatus.getInstance();

    @Override
    public void onCreate() {
        super.onCreate();
        startForeground(Constants.ONGOING_NOTIFICATION_ID, new NotificationCompat.Builder(
                this, Constants.NOTIFICATION_CHANNEL_ID)
        .setContentTitle("")
        .setContentText("")
        .build());
        playingStatus.registerListener(this);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(intent == null || intent.getAction()==null) {
            return Service.START_STICKY;
        } else {
            handleIntentAction(intent);
        }
        return super.onStartCommand(intent, flags, startId);
    }

    private void handleIntentAction(Intent intent) {
        String action = intent.getAction();
        switch (action){
            case Constants.ACTION_PAUSE_RESUME:
                handlePauseResume();
                break;
            case Constants.ACTION_PLAY_NEXT:
                handlePlayNext();
                break;
            case Constants.ACTION_PLAY_PREVIOUS:
                handlePlayPrevious();
                break;
            case Constants.ACTION_TOGGLE_SHUFFLE:
                handleToggleShuffle();
                break;
            case Constants.ACTION_CLEAR_NOTIFICATION:
                handleClearNotification();
                break;
            case Constants.ACTION_TOGGLE_REPEAT:
                handleToggleRepeat();
                break;

        }
    }

    private void handleToggleShuffle() {
        nowPlayingViewModel.handleToggleShuffle();
        Log.i(TAG, "handleToggleShuffle");
    }

    private void handlePlayPrevious() {
        nowPlayingViewModel.playPrev();
        Log.i(TAG, "handlePlayPrevious");
    }

    private void handlePlayNext() {
        nowPlayingViewModel.playNext();
        Log.i(TAG, "handlePlayNext");
    }

    private void handlePauseResume() {
        boolean isMusicPlaying = nowPlayingViewModel.getMusicIsPlaying();
        if(isMusicPlaying){
            handlePause();
        }else{
            handleResume();
        }
    }

    private void handleResume() {
        nowPlayingViewModel.resume();
        Log.i(TAG, "handleResume");
    }

    private void handlePause() {
        nowPlayingViewModel.pause();
        Log.i(TAG, "handlePause");
    }

    private void handleClearNotification(){
        stopSelf();
    }

    private void handleToggleRepeat(){
        nowPlayingViewModel.toggleRepeat();
    }

    private void pushNotification() {

        NowPlaying nowPlaying = nowPlayingViewModel.initData();
        Bitmap defaultBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.avatar_02_foreground);
        Boolean isPlaying = nowPlayingViewModel.getMusicIsPlaying();
        Boolean isRepeated = nowPlayingViewModel.getIsSongRepeated();
        notificationBuilder = new MusicNotificationBuilder(getApplicationContext(), Constants.NOTIFICATION_CHANNEL_ID);
        Notification notification = notificationBuilder.build(nowPlaying, defaultBitmap, isPlaying, isRepeated, null);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(Constants.ONGOING_NOTIFICATION_ID, notification);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        if(s.equals(Constants.PREF_SAVE_PLAYED_SONG_POSITION)
                || s.equals(Constants.PREF_SAVE_LAST_PLAYED_SONG_ID)
                || s.equals(Constants.PREF_SAVE_MUSIC_IS_PLAYING)
                || s.equals(Constants.PREF_SAVE_IS_SONG_REPEATED)
                || s.equals(Constants.PREF_SAVE_IS_SHUFFLE_ON)){
            pushNotification();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy");
        playingStatus.unregisterListener(this);
        nowPlayingViewModel.turnOffMusic();
    }
}
