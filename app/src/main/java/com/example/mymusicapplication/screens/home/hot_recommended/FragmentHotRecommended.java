package com.example.mymusicapplication.screens.home.hot_recommended;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mymusicapplication.R;
import com.example.mymusicapplication.base.BaseFragment;
import com.example.mymusicapplication.databinding.LayoutHomeFragmentBinding;
import com.example.mymusicapplication.screens.home.playlist.PlaylistViewModel;
import com.example.mymusicapplication.utils.DummyData;

public class FragmentHotRecommended extends BaseFragment<LayoutHomeFragmentBinding, HotRecommendedViewModel> {
    private RecyclerHotRecommended adapter;

    @Override
    protected Class getClassName() {
        return PlaylistViewModel.class;
    }

    @Override
    protected int getLayout() {
        return R.layout.layout_home_fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new RecyclerHotRecommended();
        adapter.updateItems(DummyData.getListHotRecommended(),viewModel);

        binding.txtTitle.setText(R.string.hot_recommended);
        binding.txtViewAll.setVisibility(View.GONE);
        binding.recyclerView.setAdapter(adapter);
    }
}
