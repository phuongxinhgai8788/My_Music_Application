package com.example.mymusicapplication.screens.home.playlist;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mymusicapplication.R;
import com.example.mymusicapplication.base.BaseFragment;
import com.example.mymusicapplication.databinding.LayoutHomeFragmentBinding;
import com.example.mymusicapplication.utils.DummyData;

public class FragmentPlayList extends BaseFragment<LayoutHomeFragmentBinding, PlaylistViewModel>{

    private static FragmentPlayList instance;
    private RecyclerPlayList adapter;

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
        adapter = new RecyclerPlayList();
        adapter.updateItems(DummyData.getListPlaylist(),viewModel);
        binding.txtTitle.setText(R.string.playlist);
        binding.recyclerView.setAdapter(adapter);
    }

    public static synchronized FragmentPlayList getInstance(){
        if(instance == null){
            instance = new FragmentPlayList();
        }
        return instance;
    }
}
