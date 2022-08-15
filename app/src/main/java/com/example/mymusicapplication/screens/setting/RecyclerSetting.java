package com.example.mymusicapplication.screens.setting;

import androidx.annotation.NonNull;

import com.example.mymusicapplication.R;
import com.example.mymusicapplication.base.BaseRecyclerViewAdapter;
import com.example.mymusicapplication.base.BaseViewHolder;
import com.example.mymusicapplication.databinding.LayoutItemSettingBinding;

public class RecyclerSetting extends BaseRecyclerViewAdapter<Setting, LayoutItemSettingBinding> {
    @Override
    protected int getLayout() {
        return R.layout.layout_item_setting;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder<LayoutItemSettingBinding> holder, int position) {
        Setting setting = listItem.get(position);
        holder.binding.setSetting(setting);

        holder.binding.getRoot().setOnClickListener(view -> {
            action.onClickListener(setting);
        });
    }
}
