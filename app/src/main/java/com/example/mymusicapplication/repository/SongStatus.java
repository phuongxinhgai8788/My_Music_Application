package com.example.mymusicapplication.repository;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;


public class SongStatus {

    private static SongStatus INSTANCE;
    private String TAG = "SongStatus";
    private Context context;

    public final String NO_OF_ALBUM_SUFFIX = "NumberOfAlbum";
    public final String PLAYED_TIMES_SUFFIX = "PlayedTimes";
    public final String ALBUM_NO_MIDDLE = "AlbumNo";
    public final String RECENTLY_PLAYED_TIME_SUFFIX = "MostRecentlyPlayed";


    private SongStatus(Context context) {
        this.context = context;
    }

    public static void initialize(Context context) {
        INSTANCE = new SongStatus(context);
    }

    public static SongStatus getInstance(){
        if(INSTANCE == null){
            throw new IllegalStateException("SongStatus must be initialized!");
        }
        return INSTANCE;
    }

    public int getAlbumNo(String songName){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getInt(songName+NO_OF_ALBUM_SUFFIX, 0);
    }

    public void saveAlbumNo(String songName){
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putInt(songName+NO_OF_ALBUM_SUFFIX, getAlbumNo(songName)+1)
                .apply();
    }

    public int getPlayedTimes(String songName){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getInt(songName+PLAYED_TIMES_SUFFIX, 0);

    }

    public void savePlayedTimes(String songName){
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putInt(songName+PLAYED_TIMES_SUFFIX, getPlayedTimes(songName)+1)
                .apply();
    }

    public void saveAlbumName(String songName, int indexOfAlbum, String albumName){
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(songName+ALBUM_NO_MIDDLE+indexOfAlbum, albumName)
                .apply();
    }

    public String getAlbumName(String songName, int indexOfAlbum){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(songName+ALBUM_NO_MIDDLE+indexOfAlbum, null);
    }

    public void saveRecentlyPlayedTime(String songName, String time){
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(songName+RECENTLY_PLAYED_TIME_SUFFIX, time)
                .apply();
    }

    public String getRecentlyPlayedTime(String songName){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(songName+RECENTLY_PLAYED_TIME_SUFFIX, null);
    }
}
