package com.example.mymusicapplication.repository;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.example.mymusicapplication.utils.Constants;

public class StateRepository {
    private static StateRepository INSTANCE;
    private String PREF_SAVE_MUSIC_IS_PLAYING = "preferenceSaveIsPlaying";
    private String TAG = "Repository";
    private Context context;

    private StateRepository(Context context) {
        this.context = context;
    }

    public static void initialize(Context context) {
        INSTANCE = new StateRepository(context);
    }

    public static StateRepository getInstance(){
        if(INSTANCE == null){
            throw new IllegalStateException("StateRepository must be initialized!");
        }
        return INSTANCE;
    }

    public int getPlayedSongPosition() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getInt(Constants.PREF_SAVE_PLAYED_SONG_POSITION, 0);
    }

    public void savePlayedSongPosition(int position) {
        Log.i(TAG, "savePlayedSongPosition");
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putInt(Constants.PREF_SAVE_PLAYED_SONG_POSITION, position)
                .apply();
    }

    public void saveMusicIsPlaying(boolean isPlaying){
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putBoolean(PREF_SAVE_MUSIC_IS_PLAYING, isPlaying)
                .apply();
    }

    public boolean getMusicIsPlaying(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getBoolean(PREF_SAVE_MUSIC_IS_PLAYING, false);
    }

    public boolean getIsShuffleOn(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getBoolean(Constants.PREF_SAVE_IS_SHUFFLE_ON, false);
    }

    public void saveIsShuffle(boolean isShuffle){
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putBoolean(Constants.PREF_SAVE_IS_SHUFFLE_ON, isShuffle)
                .apply();
    }

    public String getLastPlayedSongTitle() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(Constants.PREF_SAVE_LAST_PLAYED_SONG_TITLE, null);

    }

    public void saveLastPlayedSongTitle(String lastPlayedSongTitle){
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(Constants.PREF_SAVE_LAST_PLAYED_SONG_TITLE, lastPlayedSongTitle)
                .apply();
    }

    public String getLastPlayedSongArtist(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(Constants.PREF_SAVE_LAST_PLAYED_SONG_ARTIST, null);
    }

    public void saveLastPlayedSongArtist(String lastPlayedSongArtist){
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(Constants.PREF_SAVE_LAST_PLAYED_SONG_ARTIST, lastPlayedSongArtist)
                .apply();
    }
    public void registerListener(SharedPreferences.OnSharedPreferenceChangeListener listener) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferences.registerOnSharedPreferenceChangeListener(listener);
    }

    public void unregisterListener(SharedPreferences.OnSharedPreferenceChangeListener listener) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferences.unregisterOnSharedPreferenceChangeListener(listener);
    }

    public boolean getIsSongRepeated() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getBoolean(Constants.PREF_SAVE_IS_SONG_REPEATED, false);
    }

    public void saveIsSongRepeated(boolean isRepeated) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putBoolean(Constants.PREF_SAVE_IS_SONG_REPEATED, isRepeated)
                .apply();
    }
}
