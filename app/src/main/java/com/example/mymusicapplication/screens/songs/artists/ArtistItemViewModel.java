package com.example.mymusicapplication.screens.songs.artists;

import android.app.Application;

import androidx.annotation.NonNull;

import com.example.mymusicapplication.base.BaseViewModel;
import com.example.mymusicapplication.base.interfaces.IActionAdapterRecycler;

public class ArtistItemViewModel extends BaseViewModel implements IActionAdapterRecycler<Artist> {

    public ArtistItemViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    public void onClickListener(Artist data) {

    }

    @Override
    public void onLongClickListener(Artist data) {

    }

    @Override
    public void onNavigate(Artist data) {

    }
}
