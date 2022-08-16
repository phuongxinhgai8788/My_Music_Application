package com.example.mymusicapplication.screens.home.hot_recommended;

import androidx.databinding.BaseObservable;

public class HotRecommended extends BaseObservable {
    private String name;
    private String artist;
    private int image;

    public HotRecommended(){

    }

    public HotRecommended(String name, String artist, int image){
        this.name = name;
        this.artist = artist;
        this.image = image;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
