package com.example.mymusicapplication.screens.songs.albums.detail;

import android.app.Application;

import androidx.annotation.NonNull;

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

    }
}
