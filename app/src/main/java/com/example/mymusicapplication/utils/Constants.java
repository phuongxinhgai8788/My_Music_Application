package com.example.mymusicapplication.utils;


public class Constants {

    public static final String ACTION_CHANGE_SONG_DETAIL = "ACTION_CHANGE_SONG_DETAIL";
    public static final String ACTION_PAUSE_RESUME = "ACTION_PLAY";
    public static final String ACTION_PLAY_PREVIOUS = "ACTION_PLAY_PREVIOUS";
    public static final String ACTION_PLAY_NEXT = "ACTION_PLAY_NEXT";


    public static final int MY_PERMISSION_REQUEST_READ_MEDIA = 2;
    public static final String PREF_SAVE_LAST_PLAYED_SONG_TITLE = "PREF_SAVE_LAST_PLAYED_SONG_TITLE";
    public static final String ALBUM_ART_PATH = "content://media/external/audio/albumart/";
    public static final int ALBUM_ART_SIZE = 164;
    public static final String ACTION_CLEAR_NOTIFICATION = "ACTION_CLEAR_NOTIFICATION";

    public static final String PREF_SAVE_PLAYED_SONG_POSITION = "PREF_SAVE_PLAYED_SONG_POSITION";
    public static final String PREF_SAVE_IS_SHUFFLE_ON = "PREF_SAVE_IS_SHUFFLE_ON";

    public static final int ONGOING_NOTIFICATION_ID = 1;
    public static final String NOTIFICATION_CHANNEL_ID = "NOTIFICATION_CHANNEL_ID";
    public static final String NOTIFICATION_CHANNEL_NAME = "NOTIFICATION_CHANNEL_NAME";
    public static final String ACTION_TOGGLE_REPEAT = "ACTON_TOGGLE_REPEAT";
    public static final String ACTION_TOGGLE_SHUFFLE = "ACTION_TOGGLE_SHUFFLE";
    public static final String PREF_SAVE_IS_SONG_REPEATED = "PREF_SAVE_IS_SONG_REPEATED";
    public static final String PREF_SAVE_LAST_PLAYED_SONG_ID = "PREF_SAVE_LAST_PLAYED_SONG_ID";
    public static final String PREF_SAVE_MUSIC_IS_PLAYING = "PREF_SAVE_MUSIC_IS_PLAYING";

    public static final String KEY_SONG = "key_song";

    //constant setting
    public static final int DISPLAY = 001;
    public static final int AUDIO = 002;
    public static final int HEAD_SET = 003;
    public static final int LOCK_SCREEN = 004;
    public static final int ADVANCED = 005;
    public static final int OTHER = 006;

    //constant header
    public static final int THEMES = 0001;
    public static final int RINGTONE_CUTTER = 0002;
    public static final int SLEEP_TIME = 0003;
    public static final int EQULISER = 0004;
    public static final int DRIVE_MODE = 0005;
    public static final int HIDDEN_FOLDER = 0006;
    public static final int SCAN_MEDIA = 0007;

}
