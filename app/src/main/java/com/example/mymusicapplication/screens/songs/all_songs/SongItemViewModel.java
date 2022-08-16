package com.example.mymusicapplication.screens.songs.all_songs;

import android.app.Application;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.mymusicapplication.R;
import com.example.mymusicapplication.base.BaseViewModel;
import com.example.mymusicapplication.base.interfaces.IActionAdapterRecycler;

public class SongItemViewModel extends BaseViewModel implements IActionAdapterRecycler<SongItem> {

    public SongItemViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    public void onClickListener(SongItem data) {

    }

    @Override
    public void onLongClickListener(SongItem data) {

    }

    @Override
    public void onNavigate(SongItem data) {
        Log.d(TAG, "onNavigate: "+data.getTitle());
        Bundle bundle = new Bundle();
        onNavigate(R.id.fragmentNowPlaying,bundle);
    }
}
