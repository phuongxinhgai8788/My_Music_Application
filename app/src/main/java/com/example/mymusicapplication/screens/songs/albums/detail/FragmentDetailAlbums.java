package com.example.mymusicapplication.screens.songs.albums.detail;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mymusicapplication.R;
import com.example.mymusicapplication.base.BaseFragment;
import com.example.mymusicapplication.databinding.FragmentDetailsAlbumBinding;

import java.util.List;

public class FragmentDetailAlbums extends BaseFragment<FragmentDetailsAlbumBinding, SongItemViewModel> {
    private static FragmentDetailAlbums instance;
    private RecyclerAlbumDetail adapter;
    private SongListViewModel songListViewModel = new SongListViewModel();

    @Override
    protected Class<SongItemViewModel> getClassName() {
        return SongItemViewModel.class;
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_details_album;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new RecyclerAlbumDetail();
        new Handler().post(()->{
            List<SongItem> songItemList = songListViewModel.getSongItemList();
            FragmentDetailAlbums.this.getActivity().runOnUiThread(()->{
                adapter.updateItems(songItemList,viewModel);
                binding.recyclerDetailAlbum.setAdapter(adapter);
                displayEdittextHome(false);
                updateIconMenuHome(R.drawable.ic_back);
                updateTitleHome(getString(R.string.album_details));
            });
        });

    }

    public static synchronized FragmentDetailAlbums getInstance(){
        if(instance == null){
            instance = new FragmentDetailAlbums();
        }
        return instance;
    }
}
