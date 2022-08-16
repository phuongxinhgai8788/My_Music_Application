package com.example.mymusicapplication.screens.songs.genre;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.View;

import com.example.mymusicapplication.R;
import com.example.mymusicapplication.base.BaseFragment;
import com.example.mymusicapplication.databinding.FragmentGenresBinding;
import com.example.mymusicapplication.utils.DummyData;

import java.util.List;

public class FragmentGenres extends BaseFragment<FragmentGenresBinding, GenreViewModel> {
    private static FragmentGenres instance;
    private RecyclerGenres adapter;
    private GenreListViewModel genreListViewModel = new GenreListViewModel();

    @Override
    protected Class getClassName() {
        return GenreViewModel.class;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new RecyclerGenres();
        new Handler().post(()->{
            List<Genre> genreList = genreListViewModel.getGenreList();
            FragmentGenres.this.getActivity().runOnUiThread(()->{
                adapter.updateItems(genreList, viewModel);
                binding.recyclerGenres.setAdapter(adapter);
            });
        });
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_genres;
    }

    public static synchronized FragmentGenres getInstance(){
        if(instance == null){
            instance = new FragmentGenres();
        }
        return instance;
    }
}
