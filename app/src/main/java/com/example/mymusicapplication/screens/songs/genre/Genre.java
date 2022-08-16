package com.example.mymusicapplication.screens.songs.genre;

public class Genre {

    private String name;
    private int songNo;

    public Genre(String name, int songNo) {
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

    @Override
    public String toString() {
        return "Genre{" +
                "name='" + name + '\'' +
                ", songNo=" + songNo +
                '}';
    }
}
