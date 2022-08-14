package com.example.mymusicapplication.screens.playing_screens;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.provider.MediaStore;
import android.util.Log;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mymusicapplication.data_source.MyMediaCursor;
import com.example.mymusicapplication.music_player.MyMusicPlayer;
import com.example.mymusicapplication.repository.PlayingStatus;
import com.example.mymusicapplication.utils.Constants;

import java.util.UUID;

public class NowPlayingViewModel extends BaseObservable implements SharedPreferences.OnSharedPreferenceChangeListener {

    private static final String TAG = "NowPlayingViewModel";
    private NowPlaying nowPlaying;
    private MyMusicPlayer myMusicPlayer = MyMusicPlayer.getInstance();
    private MyMediaCursor myMediaCursor = MyMediaCursor.getInstance();
    private PlayingStatus playingStatus = PlayingStatus.getInstance();
    private Cursor cursor;

    private MutableLiveData<Long> songIdMutable = new MutableLiveData<>();
    private MutableLiveData<String> songNameMutable = new MutableLiveData<>();
    private MutableLiveData<String> artistMutable = new MutableLiveData<>();
    private MutableLiveData<String> albumMutable = new MutableLiveData<>();
    private MutableLiveData<Integer> durationMutable = new MutableLiveData<>();
    private MutableLiveData<Integer> currentPlayedPositionMutable = new MutableLiveData<>();
    private MutableLiveData<Boolean> isShuffleOnMutable = new MutableLiveData<>();
    private MutableLiveData<Boolean> isRepeatedMutable = new MutableLiveData<>();

    public NowPlayingViewModel(){
        initCursor();
        initData();
    }

    ////////////////////METHODS FOR CONTROLLING MUSIC\\\\\\\\\\\\\\\\\\\\\

    public void handleToggleShuffle() {
        nowPlaying.setShuffleOn(!nowPlaying.isShuffleOn());
        playingStatus.saveIsShuffleOn(!playingStatus.getIsShuffleOn());
        setIsShuffleOn(playingStatus.getIsShuffleOn());
        initCursor();
    }

    public void playPrev() {
        playingStatus.saveMusicIsPlaying(true);
        playingStatus.savePlayedSongPosition(0);
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

//    public boolean getMusicIsPlaying() {
//        return playingStatus.getMusicIsPlaying();
//    }
//
//    public int getPlayedSongPosition() {
//        return playingStatus.getPlayedSongPosition();
//    }

    public void resume() {
        playingStatus.saveMusicIsPlaying(true);
        myMusicPlayer.resume(playingStatus.getPlayedSongPosition());
    }

    public void prepareSong() {
        myMusicPlayer.prepareSong(playingStatus.getLastPlayedSongId());
        playingStatus.saveMusicIsPlaying(true);
    }

    public void pause() {
        myMusicPlayer.pause();
        playingStatus.saveMusicIsPlaying(false);
        playingStatus.savePlayedSongPosition(myMusicPlayer.getCurrentPosition());
    }

    public void toggleRepeat() {
        playingStatus.saveIsSongRepeated(!playingStatus.getIsSongRepeated());
        nowPlaying.setRepeated(playingStatus.getIsSongRepeated());
    }

    ////////////////////METHOD FOR NOTIFICATION\\\\\\\\\\\\\\\\\\\\\

    public NowPlaying getCurrentNowPlaying() {
        initData();
        return nowPlaying;
    }

    public boolean getMusicIsPlaying() {
        return playingStatus.getMusicIsPlaying();
    }

    public Boolean getIsSongRepeated() {
        return playingStatus.getIsSongRepeated();
    }

    ////////////////////METHOD LISTEN TO CHANGES\\\\\\\\\\\\\\\\\\\\\

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
    }

    ////////////////////METHODS FOR BINDING\\\\\\\\\\\\\\\\\\\\\
    public Long getSongId(){
        return songIdMutable.getValue();
    }

    public void setSongId(Long songId) {
        this.songIdMutable.setValue(songId);
    }

    public String getSongName(){
        return songNameMutable.getValue();
    }

    public void setSongName(String songName) {
        this.songNameMutable.setValue(songName);
    }

    public String getArtist(){
        return artistMutable.getValue();
    }

    public void setArtist(String artist) {
        this.artistMutable.setValue(artist);
    }

    public String getAlbum(){
        return albumMutable.getValue();
    }

    public void setAlbum(String album) {
        this.albumMutable.setValue(album);
    }

    public int getDuration(){
        return durationMutable.getValue();
    }

    public void setDuration(Integer duration) {
        this.durationMutable.setValue(duration);
    }

    public int getCurrentPlayedPosition(){
        return currentPlayedPositionMutable.getValue();
    }

    public void setCurrentPlayedPosition(Integer currentPlayedPosition) {
        this.currentPlayedPositionMutable.setValue(currentPlayedPosition);
    }

    public boolean getIsShuffleOn(){
        return isShuffleOnMutable.getValue();
    }
    public void setIsShuffleOn(Boolean isShuffleOn) {
        this.isShuffleOnMutable.setValue(isShuffleOn);
    }


    public boolean getIsRepeated(){
        return isRepeatedMutable.getValue();
    }
    public void setIsRepeated(Boolean isRepeated) {
        this.isRepeatedMutable.setValue(isRepeated);
    }

    public String getDurationString(){
        long durationLong = getDurationPrivate();
        int secondLeft = (int) ((durationLong/1000)%60);
        int minute = (int) ((durationLong/1000)/60);
        return minute+":"+secondLeft;
    }

    public String getCurrentPositionString(){
        long currentPosition = playingStatus.getPlayedSongPosition();
        int secondLeft = (int) ((currentPosition/1000)%60);
        int minute = (int) ((currentPosition/1000)/60);
        return minute+":"+secondLeft;
    }

    public void setCurrentPositionString(){
    }

    /////////////// PRIVATE HELPER METHODS \\\\\\\\\\\\\\\

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

    /////////////// METHODS FOR INITIALIZING DATA\\\\\\\\\\\\\\\

    public NowPlaying initData(){
        Long songId = playingStatus.getLastPlayedSongId();
        setSongId(songId);

        String lastPlayedSongName = getLastPlayedSongNamePrivate();
        setSongName(lastPlayedSongName);

        String lastPlayedSongArtist = getArtistPrivate();
        setArtist(lastPlayedSongArtist);

        String lastPlayedSongAlbum = getAlbumPrivate();
        setAlbum(lastPlayedSongAlbum);

        int duration = getDurationPrivate();
        setDuration(duration);

        int currentPlayedPosition = playingStatus.getPlayedSongPosition();
        setCurrentPlayedPosition(currentPlayedPosition);

        boolean isShuffleOn = playingStatus.getIsShuffleOn();
        setIsShuffleOn(isShuffleOn);

        boolean isRepeated = playingStatus.getIsSongRepeated();
        setIsRepeated(isRepeated);

        this.nowPlaying = new NowPlaying(songId, lastPlayedSongName, lastPlayedSongArtist, lastPlayedSongAlbum,
                duration, currentPlayedPosition, isShuffleOn, isRepeated);
        return nowPlaying;
    }

    private void initCursor(){
        this.cursor = playingStatus.getIsShuffleOn()?myMediaCursor.getMediaCursorShuffleOn(): myMediaCursor.getMediaCursorShuffleOff();
    }

    public int getPlayedSongPosition() {
        return playingStatus.getPlayedSongPosition();
    }

    public void turnOffMusic() {
        myMusicPlayer.stop();
        myMusicPlayer.release();
    }
}
