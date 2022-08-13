package com.example.mymusicapplication.screens.artist;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Build;
import android.provider.MediaStore;

import androidx.annotation.RequiresApi;

import com.example.mymusicapplication.data_source.MyMediaCursor;

import java.util.ArrayList;
import java.util.List;

public class ArtistViewModel {

    private static final String TAG = "ArtistViewModel";
    private Cursor cursor = MyMediaCursor.getInstance().getMediaCursorShuffleOff();

    @RequiresApi(api = Build.VERSION_CODES.N)
    public List<Artist> getArtistList() {

        List<Artist> artistList = new ArrayList<>();
        List<Artist> cloneArtistList = new ArrayList<>();
        List<Artist> finalList = new ArrayList<>();
        if (cursor.moveToFirst()) {

            do {
                @SuppressLint("Range") String artistName = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
                Artist artist = new Artist(artistName, 1, 1);
                artistList.add(artist);

            } while (cursor.moveToNext());
        }

        artistList.forEach(artist -> {
            cloneArtistList.add(artist);
        });

        while(!cloneArtistList.isEmpty()){
            Artist firstArtist = cloneArtistList.get(0);
            int count=0;
            for(Artist artist:artistList){
                if(firstArtist.getName().equals(artist.getName())){
                        cloneArtistList.remove(artist);
                        firstArtist.setAlbumNo(firstArtist.getAlbumNo()+count);
                        firstArtist.setSongNo(firstArtist.getSongNo()+count);
                    }
                    count=1;
                }
                finalList.add(firstArtist);
            }

        return finalList;
    }

}
