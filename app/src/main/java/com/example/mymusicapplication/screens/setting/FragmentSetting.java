package com.example.mymusicapplication.screens.setting;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mymusicapplication.R;
import com.example.mymusicapplication.base.BaseFragment;
import com.example.mymusicapplication.databinding.FragmentSettingBinding;

public class FragmentSetting extends BaseFragment<FragmentSettingBinding, SettingViewModel> {
    private RecyclerSetting _adapter;
    @Override
    protected Class<SettingViewModel> getClassName() {
        return SettingViewModel.class;
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_setting;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        displayEdittextHome(false);
        updateTitleHome(getString(R.string.setting));
        updateIconMenuHome(R.drawable.ic_menu);
        updateIconEndHome(null);

        _adapter = new RecyclerSetting();
        _adapter.updateItems(viewModel.getListItems(),viewModel);

        binding.recyclerSetting.setAdapter(_adapter);
    }

}
