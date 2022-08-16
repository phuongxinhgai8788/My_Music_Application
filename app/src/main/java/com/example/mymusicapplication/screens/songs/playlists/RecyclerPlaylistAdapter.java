package com.example.mymusicapplication.screens.songs.playlists;

import androidx.annotation.NonNull;

import com.example.mymusicapplication.R;
import com.example.mymusicapplication.base.BaseRecyclerViewAdapter;
import com.example.mymusicapplication.base.BaseViewHolder;
import com.example.mymusicapplication.databinding.LayoutItemPlaylistBottomBinding;
import com.example.mymusicapplication.model.Song;
import com.example.mymusicapplication.screens.home.playlist.Playlist;

public class RecyclerPlaylistAdapter extends BaseRecyclerViewAdapter<Playlist, LayoutItemPlaylistBottomBinding> {
    @Override
    protected int getLayout() {
        return R.layout.layout_item_playlist_bottom;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder<LayoutItemPlaylistBottomBinding> holder, int position) {
        holder.binding.setPlaylist(listItem.get(position));
    }
}
