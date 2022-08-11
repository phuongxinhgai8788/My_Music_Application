package com.example.mymusicapplication.screens.now_playing;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import com.example.mymusicapplication.model.Song;
import com.example.mymusicapplication.repository.MyCursor;
import com.example.mymusicapplication.repository.StateRepository;

import java.util.ArrayList;
import java.util.List;

public class NowPlayingViewModel {

    private MyCursor cursorLoader = MyCursor.getInstance();
    private StateRepository stateRepository = StateRepository.getInstance();

    private static NowPlayingViewModel INSTANCE;

    private NowPlayingViewModel() {

    }

    public static void initialize(Context context) {
        INSTANCE = new NowPlayingViewModel();
    }

    public static NowPlayingViewModel getInstance() {
        if (INSTANCE == null) {
            throw new IllegalStateException("Music Loader must be initialized");
        }
        return INSTANCE;
    }

    private void moveCursorToTheRightPosition() {
        // Move the cursor to the position of the song which was playing the last time the application was running
        String lastSongTitle = stateRepository.getLastPlayedSongTitle();
        String lastSongArtist = stateRepository.getLastPlayedSongArtist();

        if (lastSongTitle != null && lastSongArtist != null) {

            while (cursorLoader.getMediaCursor(StateRepository.getInstance().getIsShuffleOn()).moveToNext()) {
                @SuppressLint("Range") String title = cursorLoader.getMediaCursor(StateRepository.getInstance().getIsShuffleOn()).getString(cursorLoader.getMediaCursor(StateRepository.getInstance().getIsShuffleOn()).getColumnIndex(MediaStore.Audio.Media.TITLE));
                @SuppressLint("Range") String artist = cursorLoader.getMediaCursor(StateRepository.getInstance().getIsShuffleOn()).getString(cursorLoader.getMediaCursor(StateRepository.getInstance().getIsShuffleOn()).getColumnIndex(MediaStore.Audio.Media.ARTIST));

                if (title.equals(lastSongTitle) && artist.equals(lastSongArtist)) {
                    return;
                }
            }
        }
    }


    public Song getCurrentSong(){

        moveCursorToTheRightPosition();

            @SuppressLint("Range") String title = cursorLoader.getMediaCursor(StateRepository.getInstance().getIsShuffleOn()).getString(cursorLoader.getMediaCursor(StateRepository.getInstance().getIsShuffleOn()).getColumnIndex(MediaStore.Audio.Media.TITLE));
            @SuppressLint("Range") String artist = cursorLoader.getMediaCursor(StateRepository.getInstance().getIsShuffleOn()).getString(cursorLoader.getMediaCursor(StateRepository.getInstance().getIsShuffleOn()).getColumnIndex(MediaStore.Audio.Media.ARTIST));
            @SuppressLint("Range") long duration = cursorLoader.getMediaCursor(StateRepository.getInstance().getIsShuffleOn()).getLong(cursorLoader.getMediaCursor(StateRepository.getInstance().getIsShuffleOn()).getColumnIndex(MediaStore.Audio.Media.DURATION));
            @SuppressLint("Range") long id = cursorLoader.getMediaCursor(StateRepository.getInstance().getIsShuffleOn()).getLong(cursorLoader.getMediaCursor(StateRepository.getInstance().getIsShuffleOn()).getColumnIndex(MediaStore.Audio.Media._ID));
            @SuppressLint("Range") String album = cursorLoader.getMediaCursor(StateRepository.getInstance().getIsShuffleOn()).getString(cursorLoader.getMediaCursor(StateRepository.getInstance().getIsShuffleOn()).getColumnIndex(MediaStore.Audio.Media.ALBUM));
        @SuppressLint("Range") long albumId = cursorLoader.getMediaCursor(StateRepository.getInstance().getIsShuffleOn()).getLong(cursorLoader.getMediaCursor(StateRepository.getInstance().getIsShuffleOn()).getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));


        int id_column_index = cursorLoader.getMediaCursor(StateRepository.getInstance().getIsShuffleOn()).getColumnIndexOrThrow(MediaStore.Audio.Media._ID);
            int musicId = cursorLoader.getMediaCursor(StateRepository.getInstance().getIsShuffleOn()).getInt(id_column_index);

            Cursor genresCursor = cursorLoader.getGenresCursor(musicId, StateRepository.getInstance().getIsShuffleOn());

            int genre_colum_index = genresCursor.getColumnIndexOrThrow(MediaStore.Audio.Genres.NAME);

            List<String> genreArray = new ArrayList<>();

            if(genresCursor.moveToFirst()){
                do {
                    genreArray.add(genresCursor.getString(genre_colum_index));

                } while (genresCursor.moveToNext());
            }
            return new Song(id, title, artist, duration, album, albumId, genreArray);
        }


        public void toggleShuffle() {
        stateRepository.saveIsShuffle(!stateRepository.getIsShuffleOn());
        }

    public void moveToPrevSong() {
        if (!cursorLoader.getMediaCursor(StateRepository.getInstance().getIsShuffleOn()).moveToPrevious()) {
            cursorLoader.getMediaCursor(StateRepository.getInstance().getIsShuffleOn()).moveToLast();
        }
        updateStateRepository();

    }

    public void moveToNextSong(){
        if (!cursorLoader.getMediaCursor(StateRepository.getInstance().getIsShuffleOn()).moveToNext())
        {
            cursorLoader.getMediaCursor(StateRepository.getInstance().getIsShuffleOn()).moveToFirst();
        }
        updateStateRepository();
    }

    private void updateStateRepository(){
        @SuppressLint("Range") String title = cursorLoader.getMediaCursor(StateRepository.getInstance().getIsShuffleOn()).getString(cursorLoader.getMediaCursor(StateRepository.getInstance().getIsShuffleOn()).getColumnIndex(MediaStore.Audio.Media.TITLE));
        @SuppressLint("Range") String artist = cursorLoader.getMediaCursor(StateRepository.getInstance().getIsShuffleOn()).getString(cursorLoader.getMediaCursor(StateRepository.getInstance().getIsShuffleOn()).getColumnIndex(MediaStore.Audio.Media.ARTIST));
        stateRepository.saveLastPlayedSongTitle(title);
        stateRepository.saveLastPlayedSongArtist(artist);
    }
}
