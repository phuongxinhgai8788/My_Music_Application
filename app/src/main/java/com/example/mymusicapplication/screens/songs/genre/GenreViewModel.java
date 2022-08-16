package com.example.mymusicapplication.screens.songs.genre;

import android.app.Application;
import android.database.Cursor;
import android.provider.MediaStore;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.mymusicapplication.base.BaseViewModel;
import com.example.mymusicapplication.base.interfaces.IActionAdapterRecycler;
import com.example.mymusicapplication.data_source.MyMediaCursor;

import java.util.ArrayList;
import java.util.List;

public class GenreViewModel extends BaseViewModel implements IActionAdapterRecycler<Genre> {


    public GenreViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    public void onClickListener(Genre data) {

    }

    @Override
    public void onLongClickListener(Genre data) {

    }

    @Override
    public void onNavigate(Genre data) {

    }
}
