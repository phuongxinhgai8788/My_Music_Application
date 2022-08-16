package com.example.mymusicapplication.utils;

import com.example.mymusicapplication.R;
import com.example.mymusicapplication.model.Song;
import com.example.mymusicapplication.screens.home.hot_recommended.HotRecommended;
import com.example.mymusicapplication.screens.home.playlist.Playlist;
import com.example.mymusicapplication.screens.home.recently_played.RecentlyPlayed;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DummyData {
    static final List<Song> list = new ArrayList<>();
    static final List<Playlist> playlistList = new ArrayList<>();
    static final List<HotRecommended> hotRecommendedList = new ArrayList<>();
    static final List<RecentlyPlayed> recentlyPlayedList = new ArrayList<>();

    public static List<Song> getListSong() {
        list.clear();
        for (int i = 0; i <= 20; i++) {
            list.add(new Song(i,
                    "Sound of Sky",
                    "Dilon Bruce",
                    new Random().nextLong(),
                    "album",
                    new Random().nextLong(),
                    null,
                    R.drawable.img_bg));
            }
        return list;
    }

    public static List<Playlist> getListPlaylist(){
        playlistList.clear();
        playlistList.add(new Playlist("Playlist 01", 1));
        playlistList.add(new Playlist("Playlist 02", 2));
        playlistList.add(new Playlist("Playlist 03", 3));
        playlistList.add(new Playlist("Playlist 04", 4));
        playlistList.add(new Playlist("Playlist 05", 5));
        playlistList.add(new Playlist("Playlist 06", 6));
        return playlistList;
    }

    public static List<HotRecommended> getListHotRecommended(){
        hotRecommendedList.clear();
        hotRecommendedList.add(new HotRecommended("Hot Recommended 01", "Artist 01", R.mipmap.ic_tam_foreground));
        hotRecommendedList.add(new HotRecommended("Hot Recommended 02", "Artist 02", R.mipmap.ic_tam_foreground));
        hotRecommendedList.add(new HotRecommended("Hot Recommended 03", "Artist 03", R.mipmap.ic_tam_foreground));
        hotRecommendedList.add(new HotRecommended("Hot Recommended 04", "Artist 04", R.mipmap.ic_tam_foreground));
        hotRecommendedList.add(new HotRecommended("Hot Recommended 05", "Artist 05", R.mipmap.ic_tam_foreground));
        hotRecommendedList.add(new HotRecommended("Hot Recommended 06", "Artist 06", R.mipmap.ic_tam_foreground));
        return hotRecommendedList;
    }

    public static List<RecentlyPlayed> getListRecentlyPlayed() {
        recentlyPlayedList.clear();
        recentlyPlayedList.add(new RecentlyPlayed("RecentlyPlayed 01", "Artist 01"));
        recentlyPlayedList.add(new RecentlyPlayed("RecentlyPlayed 02", "Artist 02"));
        recentlyPlayedList.add(new RecentlyPlayed("RecentlyPlayed 03", "Artist 03"));
        recentlyPlayedList.add(new RecentlyPlayed("RecentlyPlayed 04", "Artist 04"));
        recentlyPlayedList.add(new RecentlyPlayed("RecentlyPlayed 05", "Artist 05"));
        recentlyPlayedList.add(new RecentlyPlayed("RecentlyPlayed 06", "Artist 06"));
        return recentlyPlayedList;
    }
}
