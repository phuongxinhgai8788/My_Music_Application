package com.example.mymusicapplication.sender_receiver_service;

import android.content.ContentUris;
import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.PowerManager;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.MediaController;
import android.widget.SeekBar;

import com.example.mymusicapplication.model.Song;
import com.example.mymusicapplication.repository.Repository;

public class MyMediaPlayer implements MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnErrorListener, MediaController.MediaPlayerControl, AudioManager.OnAudioFocusChangeListener{

    private static MyMediaPlayer myMediaPlayer;
    private Context context;
    private MediaPlayer mediaPlayer;
    private Repository repository;

    private final String TAG = "MyMediaPlayer";

    private MyMediaPlayer(Context context){
        this.context = context;
        initMediaPlayer();
        repository = Repository.getInstance(context);
    }

    public static MyMediaPlayer getInstance(Context context){
        if(myMediaPlayer==null){
            myMediaPlayer = new MyMediaPlayer(context);
        }
        return myMediaPlayer;
    }
    public static MyMediaPlayer getInstance(){
        return myMediaPlayer;
    }
    private void initMediaPlayer() {
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setWakeMode(context, PowerManager.PARTIAL_WAKE_LOCK);
        mediaPlayer.setAudioAttributes(
                new AudioAttributes
                        .Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .build());
        mediaPlayer.setOnPreparedListener(this);
        mediaPlayer.setOnCompletionListener(this);
        mediaPlayer.setOnErrorListener(this);
    }

    public void prepareSong(Song song){
        // Reset first because the user is playing subsequent songs
        mediaPlayer.reset();
        // Get id
        long playedSongId = song.getId();
        // set URI
        Uri trackUri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, playedSongId);
        try {
            mediaPlayer.setDataSource(context, trackUri);
        } catch (Exception e) {
            Log.e(TAG, "Error setting data source", e);
        }
        mediaPlayer.prepareAsync();
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        repository.saveIsMusicIsPlaying(false);
        repository.savePlayedSongPosition(0);
    }

    @Override
    public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
        mediaPlayer.reset();
        return false;
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        mediaPlayer.start();
    }

    @Override
    public void start() {
        mediaPlayer.start();
    }

    @Override
    public void pause() {
        mediaPlayer.pause();
    }

    @Override
    public int getDuration() {
        return mediaPlayer.getDuration();
    }

    @Override
    public int getCurrentPosition() {
        return mediaPlayer.getCurrentPosition();
    }

    @Override
    public void seekTo(int i) {
        mediaPlayer.seekTo(i);
    }

    @Override
    public boolean isPlaying() {
        return mediaPlayer.isPlaying();
    }

    @Override
    public int getBufferPercentage() {
        return 0;
    }

    @Override
    public boolean canPause() {
        return true;
    }

    @Override
    public boolean canSeekBackward() {
        return true;
    }

    @Override
    public boolean canSeekForward() {
        return true;
    }

    @Override
    public int getAudioSessionId() {
        return 0;
    }

    public void onServiceDestroy(){
        mediaPlayer.reset();
        mediaPlayer.release();

    }

    public void stop() {
        mediaPlayer.stop();
    }

    public void release() {
        mediaPlayer.release();
    }

    @Override
    public void onAudioFocusChange(int i) {

    }
}
