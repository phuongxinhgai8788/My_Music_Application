package com.example.mymusicapplication.repository;

import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.example.mymusicapplication.model.Song;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Repository {
    private static final String PREF_SAVE_SONG_DURATION = "preferenceSaveSongDuration";
    private static String PREF_SAVE_IS_PORTRAIT_SCREEN = "preferenceSaveIsPortraitScreen";
    private static Repository instance;
    public static final String PREF_SAVE_PLAYED_SONG_INDEX = "preferenceSavePlayedSongIndex";
    private String PREF_SAVE_PLAYED_SONG_POSITION = "preferenceSavePlayedSongPosition";
    private String PREF_SAVE_MUSIC_IS_PLAYING = "preferenceSaveIsPlaying";
    private String TAG = "Repository";
    private Context context;

    private Repository(Context context) {
        this.context = context;
    }

    public static Repository getInstance(Context context) {
        if (instance == null) {
            instance = new Repository(context);
        }
        return instance;
    }

    public static Repository getInstance(){
        return instance;
    }

    public int getPlayedSongPosition() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getInt(PREF_SAVE_PLAYED_SONG_POSITION, 0);
    }

    public void savePlayedSongPosition(int position) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putInt(PREF_SAVE_PLAYED_SONG_POSITION, position)
                .apply();
    }

    public int getPlayedSongIndex() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getInt(PREF_SAVE_PLAYED_SONG_INDEX, 0);
    }

    public void savePlayedSongIndex(int index) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putInt(PREF_SAVE_PLAYED_SONG_INDEX, index)
                .apply();
    }
    public boolean getIsPortraitScreen(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getBoolean(PREF_SAVE_IS_PORTRAIT_SCREEN, true);
    }

    public void saveIsPortraitScreen(boolean isPortrait){
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putBoolean(PREF_SAVE_IS_PORTRAIT_SCREEN, isPortrait)
                .apply();
    }

    public void saveIsMusicIsPlaying(boolean isPlaying){
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putBoolean(PREF_SAVE_MUSIC_IS_PLAYING, isPlaying)
                .apply();
    }

    public boolean getMusicIsPlaying(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getBoolean(PREF_SAVE_MUSIC_IS_PLAYING, true);
    }

    public int getSongDuration(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getInt(PREF_SAVE_SONG_DURATION, 0);
    }

    public void saveSongDuration(int songDuration){
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putInt(PREF_SAVE_SONG_DURATION, songDuration)
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

}
