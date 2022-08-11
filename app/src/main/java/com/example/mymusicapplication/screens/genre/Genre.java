package com.example.mymusicapplication.screens.genre;

import com.example.mymusicapplication.model.Song;

import java.util.List;

public class Genre {

    private Long id;
    private String name;
    private List<Song> songList;

    public Genre(Long id, String name, List<Song> songList) {
        this.id = id;
        this.name = name;
        this.songList = songList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Song> getSongList() {
        return songList;
    }

    public void setSongList(List<Song> songList) {
        this.songList = songList;
    }
}