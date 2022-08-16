package com.example.mymusicapplication.screens.songs.all_songs;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mymusicapplication.R;
import com.example.mymusicapplication.base.BaseFragment;
import com.example.mymusicapplication.databinding.FragmentAllSongBinding;
import com.example.mymusicapplication.utils.DummyData;

public class FragmentAllSong extends BaseFragment<FragmentAllSongBinding, SongItemViewModel> {
    private static FragmentAllSong instance;
    private RecyclerAllSongAdapter adapter;
    private SongListViewModel songListViewModel = new SongListViewModel();

    @Override
    protected Class getClassName() {
        return SongItemViewModel.class;
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_all_song;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new RecyclerAllSongAdapter();
        adapter.updateItems(songListViewModel.getSongItemList(),viewModel);

        binding.recyclerAllSong.setAdapter(adapter);
    }

    public static synchronized FragmentAllSong getInstance(){
        if(instance == null){
            instance = new FragmentAllSong();
        }
        return instance;
    }
}
