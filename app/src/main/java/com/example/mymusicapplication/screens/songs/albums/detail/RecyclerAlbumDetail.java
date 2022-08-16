package com.example.mymusicapplication.screens.songs.albums.detail;

import androidx.annotation.NonNull;

import com.example.mymusicapplication.R;
import com.example.mymusicapplication.base.BaseRecyclerViewAdapter;
import com.example.mymusicapplication.base.BaseViewHolder;
import com.example.mymusicapplication.databinding.LayoutItemAlbumDetailBinding;

public class RecyclerAlbumDetail extends BaseRecyclerViewAdapter<SongItem, LayoutItemAlbumDetailBinding> {
    @Override
    protected int getLayout() {
        return R.layout.layout_item_album_detail;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder<LayoutItemAlbumDetailBinding> holder, int position) {
        holder.binding.setSongItem(listItem.get(position));
    }
}
