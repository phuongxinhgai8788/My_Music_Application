package com.example.mymusicapplication.screens.songs.artists;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.example.mymusicapplication.R;
import com.example.mymusicapplication.base.BaseFragment;
import com.example.mymusicapplication.databinding.FragmentAllSongBinding;

import java.util.ArrayList;
import java.util.List;

public class FragmentArtists extends BaseFragment<FragmentAllSongBinding, ArtistItemViewModel> {
    private static FragmentArtists instance;
    private RecyclerArtists adapter;

    private ArtistListViewModel artistListViewModel = new ArtistListViewModel();
    private List<Artist> artistList = new ArrayList<>();
    @Override
    protected Class<ArtistItemViewModel> getClassName() {
        return ArtistItemViewModel.class;
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_all_song;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        new Handler().post(()->{
            artistList = artistListViewModel.getArtistList();
            FragmentArtists.this.getActivity().runOnUiThread(()->{
                adapter = new RecyclerArtists();

                adapter.updateItems(artistList,viewModel);
                adapter.onItemActionMenu((id,title)->{
                    Log.d(TAG, "onViewCreated: id:= "+id +"  title:= "+title);
                });

                binding.recyclerAllSong.setAdapter(adapter);
            });
        });
    }

    public static synchronized FragmentArtists getInstance(){
        if(instance == null){
            instance = new FragmentArtists();
        }
        return instance;
    }
}
