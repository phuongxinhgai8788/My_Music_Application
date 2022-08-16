package com.example.mymusicapplication.screens.bottom_sheet;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mymusicapplication.R;
import com.example.mymusicapplication.base.BaseFragment;
import com.example.mymusicapplication.databinding.FragmentNotificationSongBinding;

public class FragmentNotificationSong extends BaseFragment<FragmentNotificationSongBinding, NowPlayingViewModel> {
    private static FragmentNotificationSong instance;

    @Override
    protected Class<NowPlayingViewModel> getClassName() {
        return NowPlayingViewModel.class;
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_notification_song;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    public static synchronized FragmentNotificationSong getInstance(){
        if(instance == null){
            instance = new FragmentNotificationSong();
        }
        return instance;
    }
}
