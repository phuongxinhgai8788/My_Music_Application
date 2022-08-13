package com.example.mymusicapplication.screens.playlist_screens.my_playlist;

import java.util.ArrayList;
import java.util.List;

public class MyPlaylist {

    private String name;
    private List<String> songList = new ArrayList<>();

    public MyPlaylist(String name, List<String> songList) {
        this.name = name;
        this.songList = songList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getSongList() {
        return songList;
    }

    public void setSongList(List<String> songList) {
        this.songList = songList;
    }
}
