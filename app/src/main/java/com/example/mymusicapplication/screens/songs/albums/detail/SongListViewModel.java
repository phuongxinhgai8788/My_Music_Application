package com.example.mymusicapplication.screens.songs.albums.detail;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.provider.MediaStore;
import android.util.Log;

import com.example.mymusicapplication.data_source.MyMediaCursor;
import com.example.mymusicapplication.repository.PlayingStatus;

import java.util.ArrayList;
import java.util.List;

public class SongListViewModel {

    public List<SongItem> getSongItemList(){
        PlayingStatus playingStatus = PlayingStatus.getInstance();
        MyMediaCursor myMediaCursor = MyMediaCursor.getInstance();
        List<SongItem> songItemList = new ArrayList<>();
        Cursor cursor = playingStatus.getIsShuffleOn()?myMediaCursor.getMediaCursorShuffleOn():
                myMediaCursor.getMediaCursorShuffleOff();
        if(cursor.moveToFirst()){
            do{
                @SuppressLint("Range") String title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
                @SuppressLint("Range") long id = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media._ID));
                @SuppressLint("Range") long duration = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));
                songItemList.add(new SongItem(id, title, duration));
            }while(cursor.moveToNext());
        }
        Log.i("SongListViewModel", "There are "+songItemList.size()+" songs in the device");
        return songItemList;
    }
}
