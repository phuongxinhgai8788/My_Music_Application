package com.example.mymusicapplication.utils;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.media.session.MediaSessionCompat;

import androidx.core.app.NotificationCompat;

import com.example.mymusicapplication.R;
import com.example.mymusicapplication.screens.playing_screens.NowPlaying;
import com.example.mymusicapplication.service.MusicService;

public class MusicNotificationBuilder {

    private Context context;
    private String channelId;

    public MusicNotificationBuilder(Context context, String channelId){
        this.context = context;
        this.channelId = channelId;
    }

    public Notification build(NowPlaying nowPlaying,
                              Bitmap thumbnailBitmap,
                              boolean isPlaying,
                              boolean isRepeated,
                              MediaSessionCompat.Token mediaSession) {
        int playPauseIcon = isPlaying? R.drawable.ic_pause : R.drawable.ic_play;
        int repeatIcon = isRepeated? R.drawable.ic_repeat_on:R.drawable.ic_repeat_off;
        return new NotificationCompat.Builder(context, channelId) // Show controls on lock screen even when user hides sensitive content.
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC) // Add media control buttons that invoke intents in your media service
                .addAction(R.drawable.ic_stop, null, getPendingIntent(context, Constants.ACTION_CLEAR_NOTIFICATION))
                .addAction(R.drawable.ic_play_prev, null, getPendingIntent(context, Constants.ACTION_PLAY_PREVIOUS))
                .addAction(playPauseIcon, null, getPendingIntent(context, Constants.ACTION_PLAY_PAUSE_RESUME))
                .addAction(R.drawable.ic_play_next, null, getPendingIntent(context, Constants.ACTION_PLAY_NEXT))
                .addAction(repeatIcon, null, getPendingIntent(context, Constants.ACTION_TOGGLE_REPEAT))
                .setLargeIcon(thumbnailBitmap)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setSmallIcon(R.mipmap.avatar_02_round)
                .setTicker(nowPlaying.getSongName())
                .setWhen(System.currentTimeMillis())
                .setContentTitle(nowPlaying.getSongName())
                .setContentText(nowPlaying.getArtist())
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
