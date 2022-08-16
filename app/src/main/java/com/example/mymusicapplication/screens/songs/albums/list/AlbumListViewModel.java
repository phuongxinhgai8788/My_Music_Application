package com.example.mymusicapplication.screens.songs.albums.list;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Build;
import android.provider.MediaStore;

import androidx.annotation.RequiresApi;

import com.example.mymusicapplication.data_source.MyMediaCursor;
import com.example.mymusicapplication.repository.PlayingStatus;
import com.example.mymusicapplication.screens.songs.artists.Artist;
import com.example.mymusicapplication.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class AlbumListViewModel {

    @RequiresApi(api = Build.VERSION_CODES.N)
    public List<AlbumItem> getAlbumItemList(){
        List<AlbumItem> albumItemList = new ArrayList<>();
        List<AlbumItem> cloneAlbumItemList = new ArrayList<>();
        List<AlbumItem> finalAlbumItemList = new ArrayList<>();

        MyMediaCursor myMediaCursor = MyMediaCursor.getInstance();
        PlayingStatus playingStatus = PlayingStatus.getInstance();
        Cursor cursor = playingStatus.getIsShuffleOn()?myMediaCursor.getMediaCursorShuffleOn():myMediaCursor.getMediaCursorShuffleOff();
        if(cursor.moveToFirst()){
            do{
                @SuppressLint("Range") String albumName = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM));
                @SuppressLint("Range") long albumId = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));
                String albumPath = Constants.ALBUM_ART_PATH+albumId;
                albumItemList.add(new AlbumItem(albumId, albumName,"PhuongLTM8", albumPath, 1));
            }while (cursor.moveToNext());
        }

        albumItemList.forEach(artist -> {
            cloneAlbumItemList.add(artist);
        });

        while(!cloneAlbumItemList.isEmpty()){
            AlbumItem firstArtist = cloneAlbumItemList.get(0);
            int count=0;
            for(AlbumItem artist:albumItemList){
                if(firstArtist.getId()==artist.getId()){
                    cloneAlbumItemList.remove(artist);
                    firstArtist.setSongNo(firstArtist.getSongNo()+count);
                }
                count=1;
            }
            finalAlbumItemList.add(firstArtist);
        }
        return finalAlbumItemList;
    }
}
