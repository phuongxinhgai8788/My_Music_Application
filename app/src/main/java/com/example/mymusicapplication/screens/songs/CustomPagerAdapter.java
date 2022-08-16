package com.example.mymusicapplication.screens.songs;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.mymusicapplication.R;
import com.example.mymusicapplication.screens.home.playlist.FragmentPlayList;
import com.example.mymusicapplication.screens.songs.albums.list.FragmentAlbums;
import com.example.mymusicapplication.screens.songs.all_songs.FragmentAllSong;
import com.example.mymusicapplication.screens.songs.artists.FragmentArtists;
import com.example.mymusicapplication.screens.songs.genre.FragmentGenres;


import java.util.ArrayList;
import java.util.List;

public class CustomPagerAdapter extends FragmentStateAdapter {
    private List<Fragment>listFragment = new ArrayList<>();
    public List<Integer> listTitle = new ArrayList();

    public CustomPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
        listFragment.add(FragmentAllSong.getInstance());
        listFragment.add(FragmentPlayList.getInstance());
        listFragment.add(FragmentAlbums.getInstance());
        listFragment.add(FragmentArtists.getInstance());
        listFragment.add(FragmentGenres.getInstance());

        listTitle.add(R.string.all_song);
        listTitle.add(R.string.Playlists);
        listTitle.add(R.string.albums);
        listTitle.add(R.string.artists);
        listTitle.add(R.string.genres);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return listFragment.get(position);
    }

    @Override
    public int getItemCount() {
        return listFragment.size();
    }
}
