package com.example.mymusicapplication.screens.songs.playlists;

import android.app.Application;

import androidx.annotation.NonNull;

import com.example.mymusicapplication.base.BaseViewModel;
import com.example.mymusicapplication.base.interfaces.IActionAdapterRecycler;
import com.example.mymusicapplication.screens.home.playlist.Playlist;

public class PlaylistViewModel extends BaseViewModel implements IActionAdapterRecycler<Playlist> {

    public PlaylistViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    public void onClickListener(Playlist data) {

    }

    @Override
    public void onLongClickListener(Playlist data) {

    }

    @Override
    public void onNavigate(Playlist data) {

    }
}
