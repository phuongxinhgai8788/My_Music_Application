package com.example.mymusicapplication.screens.songs.all_songs;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;

import com.example.mymusicapplication.R;
import com.example.mymusicapplication.base.BaseRecyclerViewAdapter;
import com.example.mymusicapplication.base.BaseViewHolder;
import com.example.mymusicapplication.databinding.LayoutItemAllsongBinding;
import com.example.mymusicapplication.utils.BitmapLoader;

public class RecyclerAllSongAdapter extends BaseRecyclerViewAdapter<SongItem, LayoutItemAllsongBinding> {
    @Override
    protected int getLayout() {
        return R.layout.layout_item_allsong;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder<LayoutItemAllsongBinding> holder, int position) {
        SongItem songItem = listItem.get(position);
        Bitmap songItemBitmap = BitmapLoader.getInstance().getBitmap(songItem.getUriString());
        holder.binding.setSongItem(listItem.get(position));
        holder.binding.imgAvatar.setImageBitmap(songItemBitmap);
    }
}
