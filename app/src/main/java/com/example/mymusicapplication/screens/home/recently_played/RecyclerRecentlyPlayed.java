package com.example.mymusicapplication.screens.home.recently_played;//package com.example.mymusicapplication.screens.adapter.home;

import androidx.annotation.NonNull;

import com.example.mymusicapplication.R;
import com.example.mymusicapplication.base.BaseRecyclerViewAdapter;
import com.example.mymusicapplication.base.BaseViewHolder;
import com.example.mymusicapplication.databinding.LayoutItemRecentlyBinding;
import com.example.mymusicapplication.model.Song;

public class RecyclerRecentlyPlayed extends BaseRecyclerViewAdapter<RecentlyPlayed, LayoutItemRecentlyBinding>{
    
    @Override
    protected int getLayout() {
        return R.layout.layout_item_recently;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder<LayoutItemRecentlyBinding> holder, int position) {
        holder.binding.setRecentlyPlayed(listItem.get(position));
    }
}