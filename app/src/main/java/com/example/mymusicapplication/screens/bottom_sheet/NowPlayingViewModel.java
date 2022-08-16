package com.example.mymusicapplication.screens.bottom_sheet;

import android.annotation.SuppressLint;
import android.app.Application;
import android.database.Cursor;
import android.provider.MediaStore;

import com.example.mymusicapplication.base.BaseViewModel;
import com.example.mymusicapplication.data_source.MyMediaCursor;
import com.example.mymusicapplication.music_player.MyMusicPlayer;
import com.example.mymusicapplication.repository.PlayingStatus;
import com.example.mymusicapplication.utils.Constants;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 *  Change NowPlaying, handle event, save value to repository
 */
public class NowPlayingViewModel extends BaseViewModel {

    private PlayingStatus playingStatus = PlayingStatus.getInstance();
    private MyMusicPlayer myMusicPlayer = MyMusicPlayer.getInstance();
    private MyMediaCursor myMediaCursor = MyMediaCursor.getInstance();
    private Executor executor = Executors.newSingleThreadExecutor();

    public NowPlaying nowPlaying;

    public NowPlayingViewModel(Application application){
        super(application);
        initNowPlaying();
    }

    public void onNextBtnClick() {
        // Create new NowPlaying
        executor.execute(()->{
            playingStatus.saveMusicIsPlaying(true);
            long currentId = playingStatus.getLastPlayedSongId();
            Cursor cursor = playingStatus.getIsShuffleOn()? myMediaCursor.getMediaCursorShuffleOn() : myMediaCursor.getMediaCursorShuffleOff();
            if(cursor.moveToFirst()){
                do{
                    @SuppressLint("Range") long id = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media._ID));
                    if(id==currentId){

                        if(cursor.moveToNext()){

                        }else {
                            cursor.moveToFirst();
                        }
                        @SuppressLint("Range") long nextId = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media._ID));
                        @SuppressLint("Range") long albumId = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));
                        @SuppressLint("Range") long duration = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));
                        boolean isPlaying = playingStatus.getMusicIsPlaying();
                        nowPlaying =  new NowPlaying(nextId, Constants.ALBUM_ART_PATH + albumId, duration, isPlaying);

                        playingStatus.saveLastPlayedSongId(nextId);
                        playingStatus.savePlayedSongPosition(0);
                        return;
                    }
                }while(cursor.moveToNext());
            }
        });

    }

    public void onPrevBtnClick() {
        executor.execute(()->{
            playingStatus.saveMusicIsPlaying(true);
            long currentId = playingStatus.getLastPlayedSongId();
            Cursor cursor = playingStatus.getIsShuffleOn()? myMediaCursor.getMediaCursorShuffleOn() : myMediaCursor.getMediaCursorShuffleOff();
            if(cursor.moveToFirst()){
                do{
                    @SuppressLint("Range") long id = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media._ID));
                    if(id==currentId){

                        if(cursor.moveToNext()){

                        }else {
                            cursor.moveToFirst();
                        }
                        @SuppressLint("Range") long nextId = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media._ID));
                        playingStatus.saveLastPlayedSongId(nextId);
                        playingStatus.savePlayedSongPosition(0);
                        @SuppressLint("Range") long albumId = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));
                        @SuppressLint("Range") long duration = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));
                        boolean isPlaying = playingStatus.getMusicIsPlaying();
                        nowPlaying =  new NowPlaying(id, Constants.ALBUM_ART_PATH + albumId, duration, isPlaying);

                        return;
                    }
                }while(cursor.moveToNext());
            }
        });
    }

    public void onPauseResumeClick() {
        executor.execute(()->{
            if(playingStatus.getMusicIsPlaying()){
                playingStatus.saveMusicIsPlaying(false);
                int currentPlayedPosition = myMusicPlayer.getCurrentPosition();
                playingStatus.savePlayedSongPosition(currentPlayedPosition);
                myMusicPlayer.pause();
            }else{
                playingStatus.saveMusicIsPlaying(true);
                int lastPlayedSongPosition = playingStatus.getPlayedSongPosition();
                myMusicPlayer.resume(lastPlayedSongPosition);
            }
        });
    }

    private NowPlaying initNowPlaying() {

        Cursor cursor = playingStatus.getIsShuffleOn() ? myMediaCursor.getMediaCursorShuffleOn()
                : myMediaCursor.getMediaCursorShuffleOff();

        long lastPlayedMusicId = playingStatus.getLastPlayedSongId();

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") long id = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media._ID));
                @SuppressLint("Range") long albumId = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));
                @SuppressLint("Range") long duration = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));
                boolean isPlaying = playingStatus.getMusicIsPlaying();
                if (id == lastPlayedMusicId) {
                    return new NowPlaying(id, Constants.ALBUM_ART_PATH + albumId, duration, isPlaying);
                }
            } while (cursor.moveToNext());
        }
        return null;
    }

    public long getId() {
        return nowPlaying.getId();
    }

    public String getImagePath() {
        return nowPlaying.getImagePath();
    }

    public long getDuration() {
        return nowPlaying.getDuration();
    }

    public boolean isPlaying() {
        return nowPlaying.isPlaying();
    }

}
