package com.example.mymusicapplication.screens.album;

import com.example.mymusicapplication.model.Song;

import java.util.List;

public class Album {
    private Long id;
    private String name;
    private String imagePath;

    public Album(Long id, String name, String imagePath) {
        this.id = id;
        this.name = name;
        this.imagePath = imagePath;
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

    public void setImagePath(String imagPath) {
        this.imagePath = imagePath;
    }
}
