package com.example.mymusicapplication.utils;


public class Constants {

    public static final String ACTION_CHANGE_SONG_DETAIL = "ACTION_CHANGE_SONG_DETAIL";
    public static final String ACTION_PLAY_PAUSE_RESUME = "ACTION_PLAY";
    public static final String ACTION_PLAY_PREVIOUS = "ACTION_PLAY_PREVIOUS";
    public static final String ACTION_PLAY_NEXT = "ACTION_PLAY_NEXT";


    public static final int MY_PERMISSION_REQUEST_READ_MEDIA = 2;
    public static final String PREF_SAVE_LAST_PLAYED_SONG_TITLE = "preferenceSaveLastPlayedSongTitle";
    public static final String PREF_SAVE_LAST_PLAYED_SONG_ARTIST = "preferenceSaveLastPlayedSongArtist";
    public static final String ALBUM_ART_PATH = "content://media/external/audio/albumart/";
    public static final int ALBUM_ART_SIZE = 164;
    public static final String ACTION_CLEAR_NOTIFICATION = "ACTION_CLEAR_NOTIFICATION";

    public static final String PREF_SAVE_PLAYED_SONG_POSITION = "preferenceSavePlayedSongPosition";
    public static final String PREF_SAVE_IS_SHUFFLE_ON = "preferenceSaveIsShuffleOn1";

    public static final int ONGOING_NOTIFICATION_ID = 1;
    public static final String NOTIFICATION_CHANNEL_ID = "Notification_channel_id";
    public static final String NOTIFICATION_CHANNEL_NAME = "Notification_channel_name";
    public static final String ACTION_TOGGLE_REPEAT = "ACTON_TOGGLE_REPEAT";
    public static final String ACTION_TOGGLE_SHUFFLE = "ACTION_TOGGLE_SHUFFLE";
    public static final String PREF_SAVE_IS_SONG_REPEATED = "PREF_SAVE_IS_SONG_REPEATED";
}
