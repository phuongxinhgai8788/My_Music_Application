package com.example.mymusicapplication.repository;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.util.Log;

import com.example.mymusicapplication.model.Song;

public class MusicLoader {
    public static final String DEFAULT_SORT_ORDER = MediaStore.Audio.Media.ARTIST + ", " + MediaStore.Audio.Media.TITLE;
    private static final String TAG = "MusicViewModel";
    public static final String DEFAULT_SELECTION = MediaStore.Audio.Media.IS_MUSIC + " != 0 and " +
            MediaStore.Audio.Media.DATA + " NOT LIKE '%/WhatsApp/%'";
    private Context context;
    private Cursor cursor = null;
    private StateRepository stateRepository = StateRepository.getInstance();

    private static MusicLoader INSTANCE;

    private MusicLoader(Context context) {
        this.context = context;
            prepareCursor();
    }

    public static void initialize(Context context) {
        INSTANCE = new MusicLoader(context);
    }

    public static MusicLoader getInstance() {
        if (INSTANCE == null) {
            throw new IllegalStateException("Music Loader must be initialized");
        }
        return INSTANCE;
    }

    private void prepareCursor() {
            Log.i(TAG, "Cursor is prepared");
            //Check if shuffle mode is on
            boolean isShuffleOn = stateRepository.getIsShuffleOn();
            String sortOrder = isShuffleOn ? "RANDOM()" : DEFAULT_SORT_ORDER;
            Log.i(TAG, "Query media...");

            String selection = DEFAULT_SELECTION;
            String[] projection = new String[]{
                    MediaStore.Audio.Media._ID,
                    MediaStore.Audio.Media.ARTIST,
                    MediaStore.Audio.Media.TITLE,
                    MediaStore.Audio.Media.DURATION,
                    MediaStore.Audio.Media.ALBUM_ID
            };
            cursor = context.getContentResolver().query(
                    MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                    projection,
                    selection,
                    null,
                    sortOrder
            );
            if (cursor == null) {
                // Query failed...
                Log.e(TAG, "Failed to retrieve music: cursor is null");
            } else {
                moveCursorToTheRightPosition();
            }
        }

    private void moveCursorToTheRightPosition() {
        // Move the cursor to the position of the song which was playing the last time the application was running
        String lastSongTitle = stateRepository.getLastPlayedSongTitle();
        String lastSongArtist = stateRepository.getLastPlayedSongArtist();

        Log.i(TAG, "Last song title: "+lastSongTitle + " and artist: "+lastSongArtist);

        Log.i(TAG, "Cursor is moved to the right position");
        if (lastSongTitle != null && lastSongArtist != null) {

            while (cursor.moveToNext()) {
                @SuppressLint("Range") String title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
                @SuppressLint("Range") String artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));

                if (title.equals(lastSongTitle) && artist.equals(lastSongArtist)) {
                    return;
                }
            }
            Log.i(TAG, "Song not found");
        }
    }


    public Song getCurrentSong(){
            @SuppressLint("Range") String title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
            @SuppressLint("Range") String artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
            @SuppressLint("Range") long duration = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));
            @SuppressLint("Range") long id = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media._ID));
            @SuppressLint("Range") long albumId = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));
            return new Song(id, title, artist, duration, albumId);
        }


        public void toggleShuffle() {
        Log.i(TAG, "Shuffle is toggled");
        stateRepository.saveIsShuffle(!stateRepository.getIsShuffleOn());
        prepareCursor();
        }

//    public void jumpTo(Song song) {
//        stateRepository.saveLastPlayedSongTitle(song.getTitle());
//        stateRepository.saveLastPlayedSongArtist(song.getArtist());
//        cursor.close();
//        cursor = null;
//        isPrepared = false;
//    }

//    private void close() {
//        if(cursor != null){
//            if(cursor.getCount()>0){
//                // Save current song in a Shared Preference
//                @SuppressLint("Range") String title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
//                @SuppressLint("Range") String artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
//                stateRepository.saveLastPlayedSongTitle(title);
//                stateRepository.saveLastPlayedSongArtist(artist);
//
//            }
//            cursor = null;
//        }
//        isPrepared = false;
//    }

    public void moveToPrevSong() {
        if (!cursor.moveToPrevious()) {
            cursor.moveToLast();
        }
        updateStateRepository();

    }

    public void moveToNextSong(){
        if (!cursor.moveToNext())
        {
            cursor.moveToFirst();
        }
        updateStateRepository();
    }

    private void updateStateRepository(){
        @SuppressLint("Range") String title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
        @SuppressLint("Range") String artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
        stateRepository.saveLastPlayedSongTitle(title);
        stateRepository.saveLastPlayedSongArtist(artist);
    }
}
