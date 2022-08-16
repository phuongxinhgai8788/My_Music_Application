package com.example.mymusicapplication.screens.songs.genre;

import androidx.annotation.NonNull;

import com.example.mymusicapplication.R;
import com.example.mymusicapplication.base.BaseRecyclerViewAdapter;
import com.example.mymusicapplication.base.BaseViewHolder;
import com.example.mymusicapplication.databinding.LayoutItemGenresBinding;
import com.example.mymusicapplication.model.Song;

public class RecyclerGenres extends BaseRecyclerViewAdapter<Genre, LayoutItemGenresBinding> {
    @Override
    protected int getLayout() {
        return R.layout.layout_item_genres;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder<LayoutItemGenresBinding> holder, int position) {
        holder.binding.setGenre(listItem.get(position));
    }
}
