package com.example.mymusicapplication.utils;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.media.session.MediaSessionCompat;

import androidx.core.app.NotificationCompat;

import com.example.mymusicapplication.R;
import com.example.mymusicapplication.model.Song;
import com.example.mymusicapplication.screens.now_playing_service.MusicService;

public class MusicNotificationBuilder {

    private Context context;
    private String channelId;

    public MusicNotificationBuilder(Context context, String channelId){
        this.context = context;
        this.channelId = channelId;
    }

    public Notification build(Song song,
                              Bitmap albumArt,
                              boolean isPlaying,
                              boolean isShuffleOn,
                              boolean isRepeated,
                              MediaSessionCompat.Token mediaSession) {
//        int shuffleIcon = isShuffleOn? R.drawable.shuffle_on : R.drawable.shuffle_off;
        int playPauseIcon = isPlaying? R.drawable.ic_pause : R.drawable.ic_play;
        int repeatIcon = isRepeated? R.drawable.ic_repeat_on:R.drawable.ic_repeat_off;
        return new NotificationCompat.Builder(context, channelId) // Show controls on lock screen even when user hides sensitive content.
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC) // Add media control buttons that invoke intents in your media service
//                .addAction(shuffleIcon, null, getPendingIntent(context, Constants.ACTION_TOGGLE_SHUFFLE))
                .addAction(R.drawable.ic_stop, null, getPendingIntent(context, Constants.ACTION_CLEAR_NOTIFICATION))
                .addAction(R.drawable.ic_play_prev, null, getPendingIntent(context, Constants.ACTION_PLAY_PREVIOUS))
                .addAction(playPauseIcon, null, getPendingIntent(context, Constants.ACTION_PLAY_PAUSE_RESUME))
                .addAction(R.drawable.ic_play_next, null, getPendingIntent(context, Constants.ACTION_PLAY_NEXT))
                .addAction(repeatIcon, null, getPendingIntent(context, Constants.ACTION_TOGGLE_REPEAT))
//                .addAction(R.drawable.ic_stop, null, getPendingIntent(context, Constants.ACTION_CLEAR_NOTIFICATION))
                .setLargeIcon(albumArt)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setSmallIcon(R.mipmap.avatar_02_round)
                .setTicker(song.getTitle())
                .setWhen(System.currentTimeMillis())
                .setContentTitle(song.getTitle())
                .setContentText(song.getArtist())
                .setOngoing(true)
                .setCategory(Notification.CATEGORY_SERVICE)
                .setStyle(new androidx.media.app.NotificationCompat.MediaStyle()
                        .setMediaSession(mediaSession)
                        .setShowActionsInCompactView(1, 2, 3))
                .build();
    }

    private PendingIntent getPendingIntent(Context context, String action) {
        Intent intent = new Intent(context, MusicService.class);
        intent.setAction(action);
        return PendingIntent.getService(context, 0, intent, 0);
    }

}
