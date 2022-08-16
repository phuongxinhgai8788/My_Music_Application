package com.example.mymusicapplication.screens.songs.genre;

import android.database.Cursor;
import android.provider.MediaStore;
import android.util.Log;

import com.example.mymusicapplication.data_source.MyMediaCursor;

import java.util.ArrayList;
import java.util.List;

public class GenreListViewModel {

    public List<Genre> getGenreList(){
        List<Genre> genreList = new ArrayList<>();
        List<Genre> cloneGenreList = new ArrayList<>();
        List<Genre> finalGenreList = new ArrayList<>();

        Cursor mediaCursor = MyMediaCursor.getInstance().getMediaCursorShuffleOff();
        if(mediaCursor.moveToFirst()) {
            do {
                Cursor genreCursor = MyMediaCursor.getInstance().getGenresCursor(mediaCursor);
                if (genreCursor.moveToFirst()) {
                    do {
                        String genreName = genreCursor.getString(genreCursor.getColumnIndexOrThrow(MediaStore.Audio.Genres.NAME));
                        Genre genre = new Genre(genreName, 1);
                        genreList.add(genre);
                    } while (genreCursor.moveToNext());
                }
            }
            while (mediaCursor.moveToNext());
        }

        for(Genre genre:genreList){
            cloneGenreList.add(genre);
        }
        while(!cloneGenreList.isEmpty()){
            Genre firstGenre = cloneGenreList.get(0);
            int count=0;
            for(Genre genre:genreList){
                if(firstGenre.getName().equals(genre.getName())){
                    cloneGenreList.remove(genre);
                    firstGenre.setSongNo(firstGenre.getSongNo()+count);
                }
                count=1;
            }
            finalGenreList.add(firstGenre);
        }
        return finalGenreList;
    }
}
