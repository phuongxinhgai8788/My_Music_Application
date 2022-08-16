package com.example.mymusicapplication.screens.songs.playlists;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mymusicapplication.R;
import com.example.mymusicapplication.base.BaseFragment;
import com.example.mymusicapplication.databinding.FragmentPlaylistBinding;
import com.example.mymusicapplication.utils.DummyData;

public class FragmentPlayList extends BaseFragment<FragmentPlaylistBinding, PlaylistViewModel> {
    private static FragmentPlayList instance;
    private RecyclerPlaylistAdapter adapter;

    @Override
    protected Class<PlaylistViewModel> getClassName() {
        return PlaylistViewModel.class;
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_playlist;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new RecyclerPlaylistAdapter();
        adapter.updateItems(DummyData.getListPlaylist(),viewModel);

        binding.recyclerPlayList.setAdapter(adapter);
    }

    public static synchronized FragmentPlayList getInstance(){
        if(instance == null){
            instance = new FragmentPlayList();
        }
        return instance;
    }
}
