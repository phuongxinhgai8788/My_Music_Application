package com.example.mymusicapplication.music_player;

import android.content.ContentUris;
import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.PowerManager;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.MediaController;

import com.example.mymusicapplication.model.Song;
import com.example.mymusicapplication.screens.now_playing.NowPlayingViewModel;
import com.example.mymusicapplication.repository.StateRepository;

public class MyMusicPlayer implements MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnErrorListener, MediaController.MediaPlayerControl, AudioManager.OnAudioFocusChangeListener{

        private static MyMusicPlayer INSTANCE;
        private Context context;
        private MediaPlayer mediaPlayer;
        private StateRepository stateRepository = StateRepository.getInstance();
        private NowPlayingViewModel musicLoader = NowPlayingViewModel.getInstance();

        private final String TAG = "MyMusicPlayer";

        private MyMusicPlayer(Context context){
            this.context = context;
            initMusicPlayer();
        }

        public static MyMusicPlayer initialize(Context context){
            if(INSTANCE==null){
                INSTANCE = new MyMusicPlayer(context);
            }
            return INSTANCE;
        }
        public static MyMusicPlayer getInstance(){
            return INSTANCE;
        }

        private void initMusicPlayer() {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setWakeMode(context, PowerManager.PARTIAL_WAKE_LOCK);
            mediaPlayer.setAudioAttributes(
                    new AudioAttributes
                            .Builder()
                            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                            .build());
            mediaPlayer.setOnPreparedListener(this);
            mediaPlayer.setOnCompletionListener(this);
            mediaPlayer.setOnErrorListener(this);
        }

        public void prepareSong(){
            stateRepository.saveMusicIsPlaying(true);
            // Reset first because the user is playing subsequent songs
            mediaPlayer.reset();
            //Get currentSong
            Song currentSong = musicLoader.getCurrentSong();
            // Get id
            long playedSongId = currentSong.getId();
            // set URI
            Uri trackUri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, playedSongId);
            try {
                mediaPlayer.setDataSource(context, trackUri);
            } catch (Exception e) {
                Log.e(TAG, "Error setting data source", e);
            }
            mediaPlayer.prepareAsync();
        }

        @Override
        public void onAudioFocusChange(int i) {

        }

        @Override
        public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
            mediaPlayer.reset();
            stateRepository.savePlayedSongPosition(0);
            return false;
        }

        @Override
        public void onPrepared(MediaPlayer mediaPlayer) {
            mediaPlayer.start();
        }

        @Override
        public void start() {
            mediaPlayer.start();
        }

        @Override
        public void pause() {
            mediaPlayer.pause();
            stateRepository.saveMusicIsPlaying(false);
            stateRepository.savePlayedSongPosition(getCurrentPosition());
        }

        @Override
        public int getDuration() {
            return mediaPlayer.getDuration();
        }

        @Override
        public int getCurrentPosition() {
            return mediaPlayer.getCurrentPosition();
        }

        @Override
        public void seekTo(int i) {
            mediaPlayer.seekTo(i);
        }

        @Override
        public boolean isPlaying() {
            return mediaPlayer.isPlaying();
        }

        @Override
        public int getBufferPercentage() {
            return 0;
        }

        @Override
        public boolean canPause() {
            return true;
        }

        @Override
        public boolean canSeekBackward() {
            return true;
        }

        @Override
        public boolean canSeekForward() {
            return true;
        }

        @Override
        public int getAudioSessionId() {
            return 0;
        }

        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            Log.i(TAG, "onCompletion");
            stateRepository.savePlayedSongPosition(0);
            playNext();
        }

        public void resume() {
            mediaPlayer.seekTo(stateRepository.getPlayedSongPosition());
            mediaPlayer.start();
            stateRepository.saveMusicIsPlaying(!stateRepository.getMusicIsPlaying());
        }

        public void playPrev() {
            musicLoader.moveToPrevSong();
            prepareSong();
        }

        public void playNext() {
            musicLoader.moveToNextSong();
            prepareSong();
        }

    public void toggleRepeat() {
            Log.i(TAG, "toggleRepeat");
            stateRepository.saveIsSongRepeated(!stateRepository.getIsSongRepeated());
    }

    public void stop() {
            mediaPlayer.stop();
    }

    public void release() {
            mediaPlayer.release();
    }
}

