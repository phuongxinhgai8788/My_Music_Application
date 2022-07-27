package com.example.mymusicapplication.manager;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.Manifest;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.MediaStore;
import android.widget.FrameLayout;

import com.example.mymusicapplication.NoPermissionFragment;
import com.example.mymusicapplication.PlayListFragment;
import com.example.mymusicapplication.R;
import com.example.mymusicapplication.SongDetailFragment;
import com.example.mymusicapplication.repository.Repository;
import com.example.mymusicapplication.sender_receiver_service_worker.LocalBroadcastReceiver;
import com.example.mymusicapplication.sender_receiver_service_worker.LocalBroadcastSender;
import com.example.mymusicapplication.sender_receiver_service_worker.MusicService;
import com.example.mymusicapplication.utils.Constant;
import com.example.mymusicapplication.utils.OpenScreen;
import com.example.mymusicapplication.model.Song;
import com.example.mymusicapplication.utils.PermissionsUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity implements LocalBroadcastSender {

    private final int MY_PERMISSION_REQUEST_READ_MEDIA = 1;
    private final String TAG = "MainActivity";


    private FrameLayout container, listContainer, detailContainer;
    private PlayListFragment playListFragment;
    private SongDetailFragment songDetailFragment;
    private ArrayList<Song> songs = new ArrayList<>();
    private Repository repository = Repository.getInstance();
    private MusicService musicService;
    private MediaPlayer mediaPlayer;
    private Song closetOrCurrentPlayedSong;

    private boolean isPortraitLayout;
    private boolean isBound;
    private boolean isPlaying;
    private int songIndex;
    private int playedPosition;

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            MusicService.LocalBinder binder = (MusicService.LocalBinder) iBinder;
            musicService = binder.getService();
            mediaPlayer = musicService.getMediaPlayer();
            isBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            isBound = false;
            mediaPlayer = null;
        }
    };
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        container = findViewById(R.id.container);
        listContainer = findViewById(R.id.list_container);
        detailContainer = findViewById(R.id.detail_container);
        isPortraitLayout = container != null;
        repository.saveIsPortraitScreen(isPortraitLayout);
        isPlaying = repository.getMusicIsPlaying();
        playedPosition = repository.getPlayedSongPosition();
//        songIndex = 0;
//        repository.savePlayedSongIndex(0);
        songIndex = repository.getPlayedSongIndex();
        checkAndAskForPermission();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onStart() {
        super.onStart();
//        checkAndAskForPermission();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void checkAndAskForPermission() {
        if(!checkStorePermission(MY_PERMISSION_REQUEST_READ_MEDIA)){
            showRequestPermission(MY_PERMISSION_REQUEST_READ_MEDIA);
        }else{
            queryMusicAndOpenPlayListFragment();
            bindService();
        }
    }

    private void showRequestPermission(int requestCode) {
        String[] permissions = new String[1];
        if (requestCode == MY_PERMISSION_REQUEST_READ_MEDIA) {
            permissions[0] =
                    Manifest.permission.READ_EXTERNAL_STORAGE;

        }
        PermissionsUtil.requestPermissions(this, requestCode, permissions);
    }
    private boolean checkStorePermission(int my_permission_request_read_media) {
        if (my_permission_request_read_media == MY_PERMISSION_REQUEST_READ_MEDIA) {
            return PermissionsUtil.checkPermissions(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE);
        } else {
            return true;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSION_REQUEST_READ_MEDIA:

                if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    queryMusicAndOpenPlayListFragment();
//                    bindService();
                } else {
                    openNoPermissionFragment();
                }
                break;
            default:
                break;
        }
    }

    private void bindService(){
        Intent intent = new Intent(this, MusicService.class);
        bindService(intent, connection, Context.BIND_AUTO_CREATE);

    }

    private void openNoPermissionFragment(){
        OpenScreen.openScreen(new NoPermissionFragment(), false, isPortraitLayout, this);

    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void queryMusicAndOpenPlayListFragment() {
        queryMusic();
        openPlaylistFragment();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void queryMusic() {

            ContentResolver musicResolver = getContentResolver();
            Uri musicUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
            Cursor musicCursor = musicResolver.query(musicUri, null, null, null, null);

            if (musicCursor != null && musicCursor.moveToFirst()) {

                //get columns
                int titleColumn = musicCursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
                int idColumn = musicCursor.getColumnIndex(MediaStore.Audio.Media._ID);
                int artistColumn = musicCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);

                //add songs to list
                do {
                    long id = musicCursor.getLong(idColumn);
                    String title = musicCursor.getString(titleColumn);
                    String artist = musicCursor.getString(artistColumn);
                    songs.add(new Song(id, title, artist));
                } while (musicCursor.moveToNext());
            }
            Collections.sort(songs, Comparator.comparing(Song::getTitle));

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(connection);
    }

    private void openPlaylistFragment(){
            closetOrCurrentPlayedSong = songs.get(songIndex);
            playListFragment = PlayListFragment.newInstance(songs);
            songDetailFragment = SongDetailFragment.newInstance(songs, songIndex);
            if(isPortraitLayout){
                OpenScreen.openScreen(playListFragment, false, isPortraitLayout, this);
                OpenScreen.openScreen(songDetailFragment, true, isPortraitLayout, this);
            }else {
                OpenScreen.openScreen(playListFragment, false, isPortraitLayout, this);
                OpenScreen.openScreen(songDetailFragment, false, isPortraitLayout, this);
            }

        }

    @Override
    public void sendBroadcastPlay(ArrayList<Song> songs) {
        Intent intent = new Intent(this, LocalBroadcastReceiver.class);
        intent.setAction(Constant.ACTION_PLAY);
        intent.putExtra(Constant.ARG_PLAY_LIST, songs);
        sendBroadcast(intent);
    }

    @Override
    public void sendBroadcastChangeSongDetail(Song song) {
        Intent intent = new Intent(this, SongDetailFragment.class);
        intent.setAction(Constant.ACTION_CHANGE_SONG_DETAIL);
        intent.putExtra(Constant.ARG_ABOUT_TO_PLAY_SONG, song);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    @Override
    public void sendBroadcastPlayNext(ArrayList<Song> songs) {
        Intent intent = new Intent(this, LocalBroadcastReceiver.class);
        intent.setAction(Constant.ACTION_PLAY_NEXT);
        intent.putExtra(Constant.ARG_PLAY_LIST, songs);
        sendBroadcast(intent);
    }

    @Override
    public void sendBroadcastPlayPrevious(ArrayList<Song> songs) {
        Intent intent = new Intent(this, LocalBroadcastReceiver.class);
        intent.setAction(Constant.ACTION_PLAY_PREVIOUS);
        intent.putExtra(Constant.ARG_PLAY_LIST, songs);

        sendBroadcast(intent);
    }

    @Override
    public void sendBroadcastPause(ArrayList<Song> songs) {
        Intent intent = new Intent(this, LocalBroadcastReceiver.class);
        intent.setAction(Constant.ACTION_PAUSE);
        intent.putExtra(Constant.ARG_PLAY_LIST, songs);
        sendBroadcast(intent);
    }

    @Override
    public void sendBroadcastResume(ArrayList<Song> songs) {
        Intent intent = new Intent(this, LocalBroadcastReceiver.class);
        intent.setAction(Constant.ACTION_RESUME);
        intent.putExtra(Constant.ARG_PLAY_LIST, songs);
        sendBroadcast(intent);
    }

    public static Intent newIntent(Context context){
        return new Intent(context, MainActivity.class);
    }
}