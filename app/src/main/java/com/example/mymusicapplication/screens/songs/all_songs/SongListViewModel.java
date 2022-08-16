package com.example.mymusicapplication.screens.songs.all_songs;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.provider.MediaStore;

import com.example.mymusicapplication.data_source.MyMediaCursor;
import com.example.mymusicapplication.repository.PlayingStatus;
import com.example.mymusicapplication.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class SongListViewModel {

    private final String TAG = "SongListLoader";

    private PlayingStatus playingStatus = PlayingStatus.getInstance();
    private MyMediaCursor myMediaCursor = MyMediaCursor.getInstance();

    public SongListViewModel(){

    }

    public List<SongItem> getSongItemList(){
        List<SongItem> songItemList = new ArrayList<>();
        Cursor cursor = playingStatus.getIsShuffleOn()? myMediaCursor.getMediaCursorShuffleOn(): myMediaCursor.getMediaCursorShuffleOff();
        if(cursor.moveToFirst()){
            do{
                @SuppressLint("Range") String title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
                @SuppressLint("Range") String artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
                @SuppressLint("Range") long id = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media._ID));
                @SuppressLint("Range") String uri = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
                songItemList.add(new SongItem(id, title, artist, uri));

            }while(cursor.moveToNext());
        }
        return songItemList;
    }
}
