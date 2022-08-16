package com.example.mymusicapplication.service;

import android.database.Cursor;
import android.provider.MediaStore;

import com.example.mymusicapplication.data_source.MyMediaCursor;
import com.example.mymusicapplication.music_player.MyMusicPlayer;
import com.example.mymusicapplication.repository.PlayingStatus;

public class NowPlayingViewModel {

    private MyMusicPlayer myMusicPlayer = MyMusicPlayer.getInstance();
    private MyMediaCursor myMediaCursor = MyMediaCursor.getInstance();
    private PlayingStatus playingStatus = PlayingStatus.getInstance();
    private Cursor cursor;

    private final String TAG = "NowPlayingViewModel";

    public NowPlayingViewModel(){
        initCursor();
        initData();
    }

    public void handleToggleShuffle() {
        playingStatus.saveIsShuffleOn(!playingStatus.getIsShuffleOn());
        initCursor();
    }

    public void playPrev() {
        long prevSongId = getPrevSongId();
        playingStatus.saveLastPlayedSongId(prevSongId);
        initData();
        myMusicPlayer.prepareSong(prevSongId);
    }

    public void playNext() {
        playingStatus.saveMusicIsPlaying(true);
        playingStatus.savePlayedSongPosition(0);
        long nextSongId = getNextSongId();
        playingStatus.saveLastPlayedSongId(nextSongId);
        initData();
        myMusicPlayer.prepareSong(nextSongId);
    }

    public void resume() {
        playingStatus.saveMusicIsPlaying(true);
        myMusicPlayer.resume(playingStatus.getPlayedSongPosition());
    }

    public void pause() {
        if(myMusicPlayer.getDuration()!=0) {
            myMusicPlayer.pause();
            playingStatus.saveMusicIsPlaying(false);
            playingStatus.savePlayedSongPosition(myMusicPlayer.getCurrentPosition());
        } else{
            myMusicPlayer.prepareSong(playingStatus.getLastPlayedSongId());
            playingStatus.saveMusicIsPlaying(true);
        }
    }

    public void toggleRepeat() {
        playingStatus.saveIsSongRepeated(!playingStatus.getIsSongRepeated());
    }

    public NowPlaying initData(){
        Long songId = playingStatus.getLastPlayedSongId();

        String lastPlayedSongName = getLastPlayedSongNamePrivate();

        String lastPlayedSongArtist = getArtistPrivate();

        String lastPlayedSongAlbum = getAlbumPrivate();

        int duration = getDurationPrivate();

        int currentPlayedPosition = playingStatus.getPlayedSongPosition();

        boolean isShuffleOn = playingStatus.getIsShuffleOn();

        boolean isRepeated = playingStatus.getIsSongRepeated();

        return new NowPlaying(songId, lastPlayedSongName, lastPlayedSongArtist, lastPlayedSongAlbum,
                duration, currentPlayedPosition, isShuffleOn, isRepeated);
    }

    private void initCursor(){
        this.cursor = playingStatus.getIsShuffleOn()?myMediaCursor.getMediaCursorShuffleOn(): myMediaCursor.getMediaCursorShuffleOff();
    }

    private String getLastPlayedSongNamePrivate() {
        long lastPlayedSongId = playingStatus.getLastPlayedSongId();
        if(cursor.moveToFirst()){
            do{
                long songId = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID));
                if(lastPlayedSongId == songId){
                    return cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE));
                }
            }while(cursor.moveToNext());
        }
        return null;
    }

    private int getDurationPrivate() {

        long lastPlayedSongId = playingStatus.getLastPlayedSongId();

        if(cursor.moveToFirst()){

            do{
                long songId = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID));
                if(lastPlayedSongId == songId){
                    return cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION));
                }
            }while(cursor.moveToNext());
        }
        return 0;
    }

    private String getAlbumPrivate() {

        long lastPlayedSongId = playingStatus.getLastPlayedSongId();

        if(cursor.moveToFirst()){
            do{
                long songId = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID));
                if(lastPlayedSongId == songId){
                    return cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM));
                }
            }while(cursor.moveToNext());
        }
        return null;
    }

    private String getArtistPrivate() {

        long lastPlayedSongId = playingStatus.getLastPlayedSongId();

        if (cursor.moveToFirst()) {

            do {
                long songId = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID));
                if (lastPlayedSongId == songId) {
                    return cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST));
                }
            } while (cursor.moveToNext());
        }
        return null;
    }

    private long getPrevSongId() {
        if(cursor.moveToFirst()){
            do{
                long songId = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID));
                if(playingStatus.getLastPlayedSongId()==songId){
                    if(cursor.moveToPrevious()){
                        return cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID));
                    }else{
                        cursor.moveToLast();
                        return cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID));
                    }
                }
            }while(cursor.moveToNext());
        }
        return 0;
    }

    private long getNextSongId() {
        if(cursor.moveToFirst()){
            do{
                long songId = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID));
                if(playingStatus.getLastPlayedSongId()==songId){
                    if(cursor.moveToNext()){
                        return cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID));
                    }else{
                        cursor.moveToFirst();
                        return cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID));
                    }
                }
            }while(cursor.moveToNext());
        }
        return 0;
    }

    public void turnOffMusic() {

        playingStatus.savePlayedSongPosition(myMusicPlayer.getCurrentPosition());
        playingStatus.saveMusicIsPlaying(false);
        playingStatus.saveLastPlayedSongId(playingStatus.getLastPlayedSongId());
        myMusicPlayer.stop();
        myMusicPlayer.release();

    }

    public boolean getMusicIsPlaying() {
        return playingStatus.getMusicIsPlaying();
    }

    public boolean getIsSongRepeated() {
        return playingStatus.getIsSongRepeated();
    }
}
