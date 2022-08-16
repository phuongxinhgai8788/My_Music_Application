package com.example.mymusicapplication.screens.home.playlist;

import androidx.databinding.BaseObservable;

public class Playlist {
    String name;
    int songNo;

    public Playlist(){

    }

    public Playlist(String name, int songNo){
        this.name = name;
        this.songNo = songNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSongNo() {
        return songNo;
    }

    public void setSongNo(int songNo) {
        this.songNo = songNo;
    }

}
