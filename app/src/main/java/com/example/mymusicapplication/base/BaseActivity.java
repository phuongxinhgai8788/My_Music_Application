package com.example.mymusicapplication.base;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.example.mymusicapplication.R;
import com.example.mymusicapplication.data_source.MyMediaCursor;
import com.example.mymusicapplication.music_player.MyMusicPlayer;
import com.example.mymusicapplication.repository.PlayingStatus;
import com.example.mymusicapplication.repository.SongStatus;
import com.example.mymusicapplication.utils.Constants;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public abstract class BaseActivity<VB extends ViewDataBinding> extends AppCompatActivity {
    protected VB binding;

    protected final String TAG = this.getClass().getName();

    protected abstract int getLayout();

    @RequiresApi(api = Build.VERSION_CODES.N)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,getLayout());
        binding.setLifecycleOwner(this);
        checkPermission();
        onInit();
    }

    abstract protected void onInit();

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void checkPermission() {
        if (needsToRequestPermissions()) {
                requestPermission();
        } else {

        }
    }

    private void requestPermission() {

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

}