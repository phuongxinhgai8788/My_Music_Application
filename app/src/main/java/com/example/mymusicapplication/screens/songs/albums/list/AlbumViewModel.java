package com.example.mymusicapplication.screens.songs.albums.list;

import android.app.Application;

import androidx.annotation.NonNull;

import com.example.mymusicapplication.base.BaseViewModel;
import com.example.mymusicapplication.base.interfaces.IActionAdapterRecycler;

public class AlbumViewModel extends BaseViewModel implements IActionAdapterRecycler<AlbumItem> {
    public AlbumViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    public void onClickListener(AlbumItem data) {

    }

    @Override
    public void onLongClickListener(AlbumItem data) {

    }

    @Override
    public void onNavigate(AlbumItem data) {

    }
}
