package com.example.mymusicapplication.screens.home.recently_played;

import androidx.databinding.BaseObservable;

public class RecentlyPlayed extends BaseObservable {
    private String name;
    private String artist;

    public RecentlyPlayed(){

    }

    public RecentlyPlayed(String name, String artist){
        this.name = name;
        this.artist = artist;
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

}
