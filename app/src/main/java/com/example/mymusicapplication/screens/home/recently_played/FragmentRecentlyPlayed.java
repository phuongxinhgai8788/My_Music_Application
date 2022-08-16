package com.example.mymusicapplication.screens.home.recently_played;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.mymusicapplication.R;
import com.example.mymusicapplication.base.BaseFragment;
import com.example.mymusicapplication.databinding.LayoutHomeFragmentBinding;
import com.example.mymusicapplication.screens.home.playlist.PlaylistViewModel;
import com.example.mymusicapplication.utils.DummyData;

public class FragmentRecentlyPlayed extends BaseFragment<LayoutHomeFragmentBinding, RecentlyPlayedViewModel> {
    private RecyclerRecentlyPlayed adapter;

    @Override
    protected Class<RecentlyPlayedViewModel> getClassName() {
        return RecentlyPlayedViewModel.class;
    }

    @Override
    protected int getLayout() {
        return R.layout.layout_home_fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new RecyclerRecentlyPlayed();
        adapter.updateItems(DummyData.getListRecentlyPlayed(),viewModel);

        binding.txtTitle.setText(R.string.playlist);
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false);
        binding.recyclerView.setLayoutManager(layoutManager);
        binding.recyclerView.setAdapter(adapter);
    }
}
