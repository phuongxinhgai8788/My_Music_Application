package com.example.mymusicapplication.repository;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

public class MyCursor {

    public static final String DEFAULT_SORT_ORDER = MediaStore.Audio.Media.ARTIST + ", " + MediaStore.Audio.Media.TITLE;
    public static final String DEFAULT_SELECTION = MediaStore.Audio.Media.IS_MUSIC + " != 0 and " +
            MediaStore.Audio.Media.DATA + " NOT LIKE '%/WhatsApp/%'";
    private Context context;
    private Cursor mediaCursor = null;
    private Cursor genresCursor = null;
    private final String[] mediaProjection = new String[]{
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.DURATION,
            MediaStore.Audio.Media.ALBUM,
            MediaStore.Audio.Media.ALBUM_ID
    };
    private final String[] genresProjection = {
            MediaStore.Audio.Genres.NAME,
            MediaStore.Audio.Genres._ID
    };

    private static MyCursor INSTANCE;

    private MyCursor(Context context) {
        this.context = context;
        prepareCursor(StateRepository.getInstance().getIsShuffleOn());
    }

    public static void initialize(Context context) {
        INSTANCE = new MyCursor(context);
    }

    public static MyCursor getInstance() {
        if (INSTANCE == null) {
            throw new IllegalStateException("Music Loader must be initialized");
        }
        return INSTANCE;
    }

    private void prepareCursor(boolean isShuffleOn) {

        String selection = DEFAULT_SELECTION;
        String sortOrder = isShuffleOn?"RANDOM()":DEFAULT_SORT_ORDER;

        mediaCursor = context.getContentResolver().query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                mediaProjection,
                selection,
                null,
                sortOrder
        );

    }

    public Cursor getMediaCursor(boolean isShuffleOn){
        prepareCursor(isShuffleOn);
        return mediaCursor;
    }

    public Cursor getGenresCursor(int id_column_index, boolean isShuffleOn){
        prepareCursor(isShuffleOn);
        int musicId = mediaCursor.getInt(id_column_index);
        Uri uri = MediaStore.Audio.Genres.getContentUriForAudioId("external", musicId);
        genresCursor = context.getContentResolver().query(uri, genresProjection, null, null, null);
        return genresCursor;
    }



}
