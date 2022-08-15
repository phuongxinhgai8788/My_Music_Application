//package com.example.mymusicapplication.screens.songs;
//
//import android.content.Context;
//import android.database.Cursor;
//import android.provider.MediaStore;
//import android.util.Log;
//
//public class SongListViewModel {
//    private final String TAG = "SongListLoader";
//    private Cursor cursor = null;
//    private Context context;
//    private static SongListViewModel INSTANCE;
//
//    private SongListViewModel(Context context){
//        this.context = context;
//    }
//
//    public static void initialize(Context context){
//        INSTANCE = new SongListViewModel(context);
//    }
//
//    public static SongListViewModel getInstance(){
//        if(INSTANCE == null){
//            throw new IllegalStateException("SongListLoader must be initialized!");
//        }
//        return INSTANCE;
//    }
//
//    public Cursor getCursor(){
//
//        Log.i(TAG, "Querying media...");
//
//        String selection = NowPlayingViewModel.DEFAULT_SELECTION;
//        String[] projection = new String[]{
//                MediaStore.Audio.Media._ID,
//                MediaStore.Audio.Media.ARTIST,
//                MediaStore.Audio.Media.TITLE,
//                MediaStore.Audio.Media.DURATION,
//                MediaStore.Audio.Media.DATA,
//                MediaStore.Audio.Media.ALBUM_ID,
//                MediaStore.Audio.Media.ALBUM
//        };
//        Cursor localCur = context.getContentResolver().query(
//                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
//                projection,
//                selection,
//                null,
//                NowPlayingViewModel.DEFAULT_SORT_ORDER);
//
//        if (localCur == null) {
//            // Query failed...
//            Log.e(TAG, "Failed to retrieve music: cursor is null");
//        } else if (localCur.getCount() == 0) {
//            Log.e(TAG, "Failed to retrieve music: no music found");
//            localCur.close();
//            localCur = null;
//        } else {
//            Log.i(TAG, "Done querying media. SongListLoader is ready.");
//        }
//        cursor = localCur;
//        return cursor;
//    }
//
//    public Cursor getFilteredCursor(CharSequence constraint) {
//        Log.i(TAG, "Querying media for filter...");
//
//        //Some audio may be explicitly marked as not being music
//        String selection = (NowPlayingViewModel.DEFAULT_SELECTION + " and "
//                + "( " + MediaStore.Audio.Media.ARTIST + " LIKE ? or "
//                + MediaStore.Audio.Media.TITLE + " LIKE ? )");
//        String[] projection = new String[]{
//                MediaStore.Audio.Media._ID,
//                MediaStore.Audio.Media.ARTIST,
//                MediaStore.Audio.Media.TITLE,
//                MediaStore.Audio.Media.DURATION,
//                MediaStore.Audio.Media.ALBUM_ID,
//                MediaStore.Audio.Media.ALBUM
//        };
//        Cursor cur = context.getContentResolver().query(
//                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
//                projection,
//                selection, new String[]{"%$constraint%", "%$constraint%"},
//                NowPlayingViewModel.DEFAULT_SORT_ORDER);
//
//        if (cur == null) {
//            // Query failed...
//            Log.e(TAG, "Failed to retrieve music: cursor is null");
//        } else {
//            Log.i(TAG, "Done querying media. SongListLoader is ready.");
//        }
//        return cur;
//    }
//}
