package com.example.mymusicapplication.screens.activities;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.example.mymusicapplication.R;
import com.example.mymusicapplication.repository.MusicLoader;
import com.example.mymusicapplication.repository.SongListLoader;
import com.example.mymusicapplication.repository.StateRepository;
import com.example.mymusicapplication.music_player.MyMusicPlayer;
import com.example.mymusicapplication.utils.Constants;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class BaseActivity extends AppCompatActivity {

    private final String TAG = "BaseActivity";


    @RequiresApi(api = Build.VERSION_CODES.N)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkPermission();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void checkPermission() {
        if (needsToRequestPermissions()) {
                requestPermission();
        } else {
            setUp();
        }
    }

    private void requestPermission() {
        Log.i(TAG, "Requesting permission");
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                Constants.MY_PERMISSION_REQUEST_READ_MEDIA);
    }

    private boolean needsToRequestPermissions() {
        boolean supportsDynamicPermissions = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
        boolean result = supportsDynamicPermissions &&
                (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED);
        Log.i(TAG, "needsToRequestPermissions: "+result);
        return result;
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == Constants.MY_PERMISSION_REQUEST_READ_MEDIA){
            // If request is cancelled, the result arrays are empty
            if((grantResults.length>0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)){
               setUp();
            } else{
                    new MaterialAlertDialogBuilder(this)
                            .setTitle(R.string.app_name)
                            .setMessage(R.string.denied_permission_messsage)
                            .setPositiveButton(R.string.request_permission_button, (dialogInterface, i) -> finish())
                            .show();
                }
            }
            return;
        }

    private void setUp(){
        StateRepository.initialize(this);
        MusicLoader.initialize(this);
        SongListLoader.initialize(this);
        MyMusicPlayer.initialize(this);
        initNotificationChannel();
    }

    private void initNotificationChannel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(Constants.NOTIFICATION_CHANNEL_ID, Constants.NOTIFICATION_CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }


}