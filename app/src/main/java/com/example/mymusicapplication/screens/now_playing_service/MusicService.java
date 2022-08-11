package com.example.mymusicapplication.screens.now_playing_service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.example.mymusicapplication.R;
import com.example.mymusicapplication.model.Song;
import com.example.mymusicapplication.music_player.MyMusicPlayer;
import com.example.mymusicapplication.screens.now_playing_service.NowPlayingViewModel;
import com.example.mymusicapplication.repository.StateRepository;
import com.example.mymusicapplication.utils.Constants;
import com.example.mymusicapplication.utils.MusicNotificationBuilder;

public class MusicService extends Service implements SharedPreferences.OnSharedPreferenceChangeListener{

    private MyMusicPlayer myMusicPlayer = MyMusicPlayer.getInstance();
    private NowPlayingViewModel musicLoader = NowPlayingViewModel.getInstance();
    private StateRepository stateRepository = StateRepository.getInstance();
    private MusicNotificationBuilder notificationBuilder;
    private String TAG = "MusicService";

    @Override
    public void onCreate() {
        super.onCreate();
        startForeground(Constants.ONGOING_NOTIFICATION_ID, new NotificationCompat.Builder(
                this, Constants.NOTIFICATION_CHANNEL_ID)
        .setContentTitle("")
        .setContentText("")
        .build());
        stateRepository.registerListener(this);
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
            case Constants.ACTION_PLAY_PAUSE_RESUME:
                handlePlayPauseResume();
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
        musicLoader.toggleShuffle();
    }

    private void handlePlayPrevious() {
        myMusicPlayer.playPrev();
    }

    private void handlePlayNext() {
        myMusicPlayer.playNext();
    }

    private void handlePlayPauseResume() {
        boolean isMusicPlaying = StateRepository.getInstance().getMusicIsPlaying();
        if(isMusicPlaying){
            handlePause();
        }else if (StateRepository.getInstance().getPlayedSongPosition()==0){
            handlePlay();
        }else{
            handleResume();
        }
    }

    private void handleResume() {
        myMusicPlayer.resume();
    }

    private void handlePlay() {
        myMusicPlayer.prepareSong();
    }

    private void handlePause() {
        myMusicPlayer.pause();
    }

    private void handleClearNotification(){
        stopForeground(true);
    }

    private void handleToggleRepeat(){
       myMusicPlayer.toggleRepeat();
       pushNotification();
    }

    private void pushNotification() {

        Song song = musicLoader.getCurrentSong();
        Bitmap defaultBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.avatar_02_foreground);
        Boolean isPlaying = stateRepository.getMusicIsPlaying();
        Boolean isShuffleOn = stateRepository.getIsShuffleOn();
        Boolean isRepeated = stateRepository.getIsSongRepeated();
        notificationBuilder = new MusicNotificationBuilder(getApplicationContext(), Constants.NOTIFICATION_CHANNEL_ID);
        Notification notification = notificationBuilder.build(song, defaultBitmap, isPlaying, isShuffleOn, isRepeated, null);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(Constants.ONGOING_NOTIFICATION_ID, notification);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        if(s.equals(Constants.PREF_SAVE_PLAYED_SONG_POSITION)
        || s.equals(Constants.PREF_SAVE_LAST_PLAYED_SONG_TITLE)
        || s.equals(Constants.PREF_SAVE_IS_SONG_REPEATED)
        || s.equals(Constants.PREF_SAVE_IS_SHUFFLE_ON)){
            pushNotification();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        myMusicPlayer.stop();
        myMusicPlayer.release();
        myMusicPlayer = null;
        stateRepository.unregisterListener(this);
    }
}
