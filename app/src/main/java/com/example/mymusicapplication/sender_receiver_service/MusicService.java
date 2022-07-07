package com.example.mymusicapplication.sender_receiver_service;

import android.app.Service;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.os.PowerManager;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.MediaController;

import androidx.annotation.Nullable;

import com.example.mymusicapplication.R;
import com.example.mymusicapplication.model.Song;
import com.example.mymusicapplication.repository.Repository;
import com.example.mymusicapplication.utils.Constant;

import java.io.Serializable;
import java.util.ArrayList;

public class MusicService extends Service {

    private MyMediaPlayer myMediaPlayer = MyMediaPlayer.getInstance();
    private Song song;
    private int songIndex;
    private int songPosition;
    private boolean isPlaying;
    private ArrayList<Song> songs;
    Repository repository = Repository.getInstance();
    private String TAG = "MusicService";

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(intent!=null){

            String action = intent.getAction();
            songs = (ArrayList<Song>) intent.getSerializableExtra(Constant.ARG_PLAY_LIST);
            songIndex = repository.getPlayedSongIndex();
            songPosition = repository.getPlayedSongPosition();
            song = songs.get(songIndex);
            handleAction(action);
        }
        return START_STICKY;
    }

    private void handleAction(String action) {
        switch (action){
            case Constant.ACTION_PLAY:
                myMediaPlayer.prepareSong(song);
                break;
            case Constant.ACTION_PLAY_NEXT:
                songIndex = songIndex<(songs.size()-1)?songIndex+1:0;
                songPosition = 0;
                repository.savePlayedSongIndex(songIndex);
                repository.savePlayedSongPosition(songPosition);
                song = songs.get(songIndex);
                myMediaPlayer.prepareSong(song);
                break;
            case Constant.ACTION_PLAY_PREVIOUS:
                songIndex = songIndex==0?songs.size()-1:songIndex-1;
                songPosition = 0;
                repository.savePlayedSongIndex(songIndex);
                repository.savePlayedSongPosition(songPosition);
                song = songs.get(songIndex);
                myMediaPlayer.prepareSong(song);
                break;
            case Constant.ACTION_PAUSE:
                isPlaying = false;
                songPosition = myMediaPlayer.getCurrentPosition();
                repository.saveIsMusicIsPlaying(isPlaying);
                repository.savePlayedSongPosition(songPosition);
                myMediaPlayer.pause();
                break;
            case Constant.ACTION_RESUME:
                isPlaying = true;
                repository.saveIsMusicIsPlaying(isPlaying);
                myMediaPlayer.seekTo(songPosition);
                myMediaPlayer.start();
                break;

        }
    }

    @Override
    public void onDestroy() {
        Log.i(TAG, "onDestroy: ");
        myMediaPlayer.onServiceDestroy();
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
