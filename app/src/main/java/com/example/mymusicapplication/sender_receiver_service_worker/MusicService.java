package com.example.mymusicapplication.sender_receiver_service_worker;

import android.app.Service;
import android.content.ContentUris;
import android.content.Intent;
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
import androidx.work.Constraints;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import com.example.mymusicapplication.model.Song;
import com.example.mymusicapplication.repository.Repository;
import com.example.mymusicapplication.utils.Constant;

import java.util.ArrayList;

public class MusicService extends Service implements MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnErrorListener, MediaController.MediaPlayerControl, AudioManager.OnAudioFocusChangeListener{

    private MediaPlayer mediaPlayer;
    private Song song;
    private int songIndex;
    private int songPosition;
    private boolean isPlaying;
    private ArrayList<Song> songs;
    Repository repository = Repository.getInstance();
    private Binder localBinder = new LocalBinder();
    private WorkRequest workRequest;
    private String TAG = "MusicService";

    @Override
    public void onCreate() {
        super.onCreate();
        initMediaPlayer();
    }

    private void setUpWorkRequest() {
        Constraints constraints = new Constraints.Builder()
                .setRequiresBatteryNotLow(true)
                .build();
        workRequest = new OneTimeWorkRequest
                .Builder(MusicWorker.class)
                .setConstraints(constraints)
                .build();
        WorkManager.getInstance().enqueue(workRequest);
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
                prepareSong(song);
                break;
            case Constant.ACTION_PLAY_NEXT:
                songIndex = songIndex<(songs.size()-1)?songIndex+1:0;
                songPosition = 0;
                repository.savePlayedSongIndex(songIndex);
                repository.savePlayedSongPosition(songPosition);
                song = songs.get(songIndex);
                prepareSong(song);
                break;
            case Constant.ACTION_PLAY_PREVIOUS:
                songIndex = songIndex==0?songs.size()-1:songIndex-1;
                songPosition = 0;
                repository.savePlayedSongIndex(songIndex);
                repository.savePlayedSongPosition(songPosition);
                song = songs.get(songIndex);
                prepareSong(song);
                break;
            case Constant.ACTION_PAUSE:
                isPlaying = false;
                songPosition = getCurrentPosition();
                repository.saveIsMusicIsPlaying(isPlaying);
                repository.savePlayedSongPosition(songPosition);
                mediaPlayer.pause();
                break;
            case Constant.ACTION_RESUME:
                isPlaying = true;
                repository.saveIsMusicIsPlaying(isPlaying);
                mediaPlayer.seekTo(songPosition);
                mediaPlayer.start();
                break;

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return localBinder;
    }

    private void initMediaPlayer() {
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setWakeMode(getApplicationContext(), PowerManager.PARTIAL_WAKE_LOCK);
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
        repository.saveIsMusicIsPlaying(true);
        // Reset first because the user is playing subsequent songs
        mediaPlayer.reset();
        // Get id
        long playedSongId = song.getId();
        // set URI
        Uri trackUri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, playedSongId);
        try {
            mediaPlayer.setDataSource(getApplicationContext(), trackUri);
        } catch (Exception e) {
            Log.e(TAG, "Error setting data source", e);
        }
        mediaPlayer.prepareAsync();
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        repository.saveIsMusicIsPlaying(false);
        repository.savePlayedSongPosition(0);
        setUpWorkRequest();
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

    @Override
    public void onAudioFocusChange(int i) {

    }
    public MediaPlayer getMediaPlayer(){
        return mediaPlayer;
    }
    public class LocalBinder extends Binder {
        public MusicService getService(){
            return MusicService.this;
        }
    }
}
