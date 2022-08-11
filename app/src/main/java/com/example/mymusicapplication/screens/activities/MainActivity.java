package com.example.mymusicapplication.screens.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.example.mymusicapplication.model.Song;
import com.example.mymusicapplication.screens.songs.SongListViewModel;
import com.example.mymusicapplication.repository.StateRepository;
import com.example.mymusicapplication.service.MusicService;
import com.example.mymusicapplication.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity{


    private static final String TAG = "MainActivity";

    @SuppressLint("Range")
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Cursor cursor = SongListViewModel.getInstance().getCursor();

//        Test notification

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


//        Test list of songs
        int numberOfSong = cursor.getCount();
        cursor.moveToFirst();
        for(int i=1; i<=numberOfSong; i++){
            Song song = getCurrentSong(cursor);
            cursor.moveToNext();
            Log.i(TAG, song.toString());
        }

        // Test

        // Test album

//        Map<String, Integer> albumMap = new LinkedHashMap<>();
//        int songListSize = cursor.getCount();
//        for(int i=0; i<songListSize; i++){
//            cursor.moveToFirst();
//            int count = 0;
//            do{
//                albumMap.put(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM)), count);
//                count++;
//            }
//            while (cursor.moveToNext());
//        }
//        albumMap.forEach((album, count) -> {
//            Log.i(TAG, "Album: "+album+" has "+count+" songs");
//        });

        // Test genres
//        Map<String, List<Integer>> genreMap = new LinkedHashMap<>();
//        int songListSize02 = cursor.getCount();
//        for(int i=0; i<songListSize02; i++){
//            cursor.moveToFirst();
//            int count = 0;
//            do{
//                Song song = getCurrentSong(cursor);
//
//                List<String> genreList = song.getGenreList();
//                genreList.forEach((v)->{
//                    int count02 = 0;
//                    genreMap.put(v, song.getTitle());
//                });
//            }
//            while (cursor.moveToNext());
//        }
//        albumMap.forEach((album, count) -> {
//            Log.i(TAG, "Album: "+album+" has "+count+" songs");
//        });
    }

    private Song getCurrentSong(Cursor mediaCursor){
        @SuppressLint("Range") String title = mediaCursor.getString(mediaCursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
        @SuppressLint("Range") String artist = mediaCursor.getString(mediaCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
        @SuppressLint("Range") long duration = mediaCursor.getLong(mediaCursor.getColumnIndex(MediaStore.Audio.Media.DURATION));
        @SuppressLint("Range") long id = mediaCursor.getLong(mediaCursor.getColumnIndex(MediaStore.Audio.Media._ID));
        @SuppressLint("Range") long albumId = mediaCursor.getLong(mediaCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));
        @SuppressLint("Range") String album = mediaCursor.getString(mediaCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM));


        String[] genresProjection = {
                MediaStore.Audio.Genres.NAME,
                MediaStore.Audio.Genres._ID
        };
        int id_column_index = mediaCursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID);
        int musicId = mediaCursor.getInt(id_column_index);
        Uri uri = MediaStore.Audio.Genres.getContentUriForAudioId("external", musicId);
        Cursor genresCursor = this.getContentResolver().query(uri, genresProjection, null, null, null);
        int genre_colum_index = genresCursor.getColumnIndexOrThrow(MediaStore.Audio.Genres.NAME);

        List<String> genreArray = new ArrayList<>();
        if(genresCursor.moveToFirst()){
            do {
                genreArray.add(genresCursor.getString(genre_colum_index));

            } while (genresCursor.moveToNext());
        }
        return new Song(id, title, artist, duration, album, albumId, genreArray);
    }

}