package com.example.mymusicapplication.screens.home;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.example.mymusicapplication.R;
import com.example.mymusicapplication.app.MyApplication;
import com.example.mymusicapplication.base.BaseFragment;
import com.example.mymusicapplication.databinding.FragmentHomeBinding;
import com.example.mymusicapplication.screens.home.playlist.PlaylistViewModel;

public class FragmentHome extends BaseFragment<FragmentHomeBinding, PlaylistViewModel> {
    protected PlaylistViewModel viewModel;

    @Override
    protected Class<PlaylistViewModel> getClassName() {
        return PlaylistViewModel.class;
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_home;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        displayEdittextHome(true);
        updateIconMenuHome(R.drawable.ic_menu);
        viewModel = new ViewModelProvider(this, MyApplication.factory).get(PlaylistViewModel.class);
    }
}
