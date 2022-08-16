package com.example.mymusicapplication.data_source;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import java.util.ArrayList;

public class MyMediaCursor {

    public static final String DEFAULT_SORT_ORDER = MediaStore.Audio.Media.ARTIST + ", " + MediaStore.Audio.Media.TITLE;
    public static final String DEFAULT_SELECTION = MediaStore.Audio.Media.IS_MUSIC + " != 0 and " +
            MediaStore.Audio.Media.DATA + " NOT LIKE '%/WhatsApp/%'";
    private static Context context;
    private static Cursor mediaCursorShuffleOn = null;
    private static Cursor mediaCursorShuffleOff = null;
    private static Cursor genresCursor = null;
    private static final String[] mediaProjection = new String[]{
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.DURATION,
            MediaStore.Audio.Media.ALBUM,
            MediaStore.Audio.Media.ALBUM_ID,
            MediaStore.Audio.Media.DATA
    };
    private final String[] genresProjection = {
            MediaStore.Audio.Genres.NAME,
            MediaStore.Audio.Genres._ID
    };

    private static final String[] playListProjection = new String[] {
            MediaStore.Audio.Playlists._ID, MediaStore.Audio.Playlists.NAME
    };

    private static MyMediaCursor INSTANCE;

    private MyMediaCursor(Context context) {
        this.context = context;
    }

    public static void initialize(Context context) {
        INSTANCE = new MyMediaCursor(context);
    }

    public static MyMediaCursor getInstance() {
        if (INSTANCE == null) {
            throw new IllegalStateException("MyMediaCursor must be initialized");
        }
        return INSTANCE;
    }

    private Cursor prepareMediaCursor(boolean isShuffleOn) {

        String selection = DEFAULT_SELECTION;
        String sortOrder = isShuffleOn?"RANDOM()":DEFAULT_SORT_ORDER;

        return context.getContentResolver().query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                mediaProjection,
                selection,
                null,
                sortOrder
        );

    }

    public Cursor getMediaCursorShuffleOn(){
        return prepareMediaCursor(true);
    }

    public Cursor getGenresCursor(Cursor mediaCursor){
        int musicId = Integer.parseInt(mediaCursor.getString(mediaCursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)));
        Uri uri = MediaStore.Audio.Genres.getContentUriForAudioId("external", musicId);
        genresCursor = context.getContentResolver().query(uri, genresProjection, null, null, null);
        return genresCursor;
    }

    public Cursor getMediaCursorShuffleOff(){
        return prepareMediaCursor(false);
    }

    public Cursor getPlaylistCursor(){
         Cursor cursor = context.getContentResolver()
                .query(MediaStore.Audio.Playlists.EXTERNAL_CONTENT_URI, playListProjection, null, null,
                        MediaStore.Audio.Playlists.NAME + " ASC");
         return cursor;
    }


    public void closeCursor(){
        mediaCursorShuffleOff.close();
        mediaCursorShuffleOn.close();
        genresCursor.close();
    }
}
