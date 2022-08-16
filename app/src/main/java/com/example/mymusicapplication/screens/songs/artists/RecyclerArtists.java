package com.example.mymusicapplication.screens.songs.artists;

import androidx.annotation.NonNull;

import com.example.mymusicapplication.R;
import com.example.mymusicapplication.base.BaseRecyclerViewAdapter;
import com.example.mymusicapplication.base.BaseViewHolder;
import com.example.mymusicapplication.base.interfaces.IMenuItemAction;
import com.example.mymusicapplication.databinding.LayoutItemArtistsBinding;
import com.example.mymusicapplication.model.Song;
import com.example.mymusicapplication.utils.Extension;

public class RecyclerArtists extends BaseRecyclerViewAdapter<Artist, LayoutItemArtistsBinding> {
    private IMenuItemAction menuItemAction;
    @Override
    protected int getLayout() {
        return R.layout.layout_item_artists;
    }

    public void onItemActionMenu(IMenuItemAction menuItemAction){
        this.menuItemAction = menuItemAction;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder<LayoutItemArtistsBinding> holder, int position) {
        holder.binding.setArtist(listItem.get(position));
        holder.binding.imgAvatar.setOnClickListener(view -> {
            action.onNavigate(listItem.get(position));
        });

        holder.binding.imgMenuItem.setOnClickListener(view -> {
            Extension.showPopup(view, R.menu.menu_albums,menuItemAction);
        });
    }
}
