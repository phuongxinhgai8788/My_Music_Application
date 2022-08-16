package com.example.mymusicapplication.screens.home.recently_played;

import android.app.Application;

import com.example.mymusicapplication.base.BaseViewModel;
import com.example.mymusicapplication.base.interfaces.IActionAdapterRecycler;

public class RecentlyPlayedViewModel extends BaseViewModel implements IActionAdapterRecycler<RecentlyPlayed> {


    public RecentlyPlayedViewModel(Application application){
        super(application);
    }
    @Override
    public void onClickListener(RecentlyPlayed data) {

    }

    @Override
    public void onLongClickListener(RecentlyPlayed data) {

    }

    @Override
    public void onNavigate(RecentlyPlayed data) {

    }
}
