package com.example.mymusicapplication.screens.songs.albums.list;

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
import com.example.mymusicapplication.databinding.FragmentAlbumsBinding;
import com.example.mymusicapplication.screens.songs.albums.RecyclerAlbum;
import com.example.mymusicapplication.utils.DummyData;

import java.util.ArrayList;
import java.util.List;

public class FragmentAlbums extends BaseFragment<FragmentAlbumsBinding, AlbumViewModel> {
    private static FragmentAlbums instance;
    private RecyclerAlbum adapter;
    private AlbumListViewModel albumListViewModel = new AlbumListViewModel();
    private List<AlbumItem> albumItemList = new ArrayList<>();

    @Override
    protected Class<AlbumViewModel> getClassName() {
        return AlbumViewModel.class;
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_albums;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        new Handler().post(()->{
            albumItemList = albumListViewModel.getAlbumItemList();
            FragmentAlbums.this.getActivity().runOnUiThread(()->{
                adapter = new RecyclerAlbum();
                adapter.updateItems(albumItemList,viewModel);
                adapter.onItemActionMenu((id, title) ->  {
                    Log.d(TAG, "onViewCreated: id:="+id + "   title:= "+title);
                });

                binding.recyclerAlbum.setAdapter(adapter);
            });
        });

    }

    public static synchronized FragmentAlbums getInstance(){
        if(instance == null){
            instance = new FragmentAlbums();
        }
        return instance;
    }
}
