package com.example.mymusicapplication.screens.songs;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.example.mymusicapplication.R;
import com.example.mymusicapplication.base.BaseFragment;
import com.example.mymusicapplication.databinding.FragmentSongBinding;
import com.example.mymusicapplication.screens.songs.all_songs.SongItemViewModel;
import com.google.android.material.tabs.TabLayoutMediator;


public class FragmentSong extends BaseFragment<FragmentSongBinding, SongItemViewModel> {
    private CustomPagerAdapter adapter;

    @Override
    protected Class<SongItemViewModel> getClassName() {
        return SongItemViewModel.class;
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_song;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupViewpagerAdapter();
        displayEdittextHome(false);
        updateTitleHome(getString(R.string.songs));
        updateIconMenuHome(R.drawable.ic_menu);
        new TabLayoutMediator(binding.tabLayout, binding.viewPager,
                (tab, position) -> tab.setText(adapter.listTitle.get(position))
        ).attach();
    }

    private void setupViewpagerAdapter() {
        adapter = new CustomPagerAdapter(getActivity());
        binding.viewPager.setAdapter(adapter);
        binding.tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(requireContext(), R.color.selected_color));
    }
}
