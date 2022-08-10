package com.example.mymusicapplication.screens.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.example.mymusicapplication.repository.SongListLoader;
import com.example.mymusicapplication.repository.StateRepository;
import com.example.mymusicapplication.service.MusicService;
import com.example.mymusicapplication.utils.Constants;

public class MainActivity extends BaseActivity{


    private static final String TAG = "MainActivity";

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Cursor cursor = SongListLoader.getInstance().getCursor();

        Log.i(TAG, "Number of song in the device: "+cursor.getCount());
        Log.i(TAG, "Cursor moves to first position: "+cursor.moveToFirst());

        @SuppressLint("Range") String title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
        @SuppressLint("Range") String artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));

        Log.i(TAG, "Current song title: "+title);
        Log.i(TAG, "Current artist: "+artist);

        StateRepository.getInstance().saveLastPlayedSongTitle(title);
        StateRepository.getInstance().saveLastPlayedSongArtist(artist);
        StateRepository.getInstance().savePlayedSongPosition(0);

        Intent intent = new Intent(this, MusicService.class);
        intent.setAction(Constants.ACTION_PLAY_PREVIOUS);
        startForegroundService(intent);

    }
}