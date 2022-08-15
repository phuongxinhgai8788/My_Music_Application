package com.example.mymusicapplication.screens.songs.all_songs.now_playing;

import android.database.Cursor;
import android.provider.MediaStore;

import androidx.databinding.BaseObservable;
import androidx.lifecycle.MutableLiveData;

import com.example.mymusicapplication.data_source.MyMediaCursor;
import com.example.mymusicapplication.repository.PlayingStatus;

public class NowPlayingViewModel extends BaseObservable{

    private static final String TAG = "NowPlayingViewModel";
    private NowPlaying nowPlaying;
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


    public boolean getMusicIsPlaying() {
        return playingStatus.getMusicIsPlaying();
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

}
