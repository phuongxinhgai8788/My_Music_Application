package com.example.mymusicapplication.screens.album;

import com.example.mymusicapplication.model.Song;

import java.util.List;

public class Album {
    private Long id;
    private String name;
    private String imagePath;
    private int songNumber;

    public Album(Long id, String name, String imagePath, int songNumber) {
        this.id = id;
        this.name = name;
        this.imagePath = imagePath;
        this.songNumber = songNumber;
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

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public int getSongNumber() {
        return songNumber;
    }

    public void setSongNumber(int songNumber) {
        this.songNumber = songNumber;
    }

    @Override
    public String toString() {
        return "Album{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", imagePath='" + imagePath + '\'' +
                ", songNumber=" + songNumber +
                '}';
    }
}
