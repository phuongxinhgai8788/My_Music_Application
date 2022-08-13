package com.example.mymusicapplication.screens.activities;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.mymusicapplication.R;

import com.example.mymusicapplication.databinding.MainActivityBinding;
import com.example.mymusicapplication.screens.playlist_screens.PlaylistFragment;
import com.example.mymusicapplication.screens.playlist_screens.my_playlist.AddMyPlaylistFragment;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MainActivity extends BaseActivity implements PlaylistFragment.openAddMyPlaylistFragment{

    private MainActivityBinding binding;
    private static final String TAG = "MainActivity";
    private Executor executor = Executors.newSingleThreadExecutor();


    @SuppressLint("Range")
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = MainActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if(currentFragment!=null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_container, new PlaylistFragment())
                    .commit();
        }else{
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new PlaylistFragment())
                    .addToBackStack(null)
                    .commit();
        }
    }

    @Override
    public void openAddMyPlaylistFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container, new AddMyPlaylistFragment())
                .commit();
    }

}