package com.example.mymusicapplication.screens.slide;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mymusicapplication.R;
import com.example.mymusicapplication.base.BaseFragment;
import com.example.mymusicapplication.databinding.FragmentSlideBinding;
import com.example.mymusicapplication.screens.setting.RecyclerSetting;
import com.example.mymusicapplication.screens.setting.SettingViewModel;

public class FragmentSlide extends BaseFragment<FragmentSlideBinding, SettingViewModel> {
    private RecyclerSetting adapter;
    @Override
    protected Class<SettingViewModel> getClassName() {
        return SettingViewModel.class;
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_slide;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new RecyclerSetting();
        adapter.updateItems(viewModel.getListItemsHeader(),viewModel);

        binding.recyclerHeader.setAdapter(adapter);
    }


}
