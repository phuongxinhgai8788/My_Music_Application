package com.example.mymusicapplication.screens.songs.artists;

public class Artist {

    private String name;
    private int albumNo;
    private int songNo;

    public Artist(String name, int albumNo, int songNo) {
        this.name = name;
        this.albumNo = albumNo;
        this.songNo = songNo;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAlbumNo() {
        return albumNo;
    }

    public void setAlbumNo(int albumNo) {
        this.albumNo = albumNo;
    }

    public int getSongNo() {
        return songNo;
    }

    public void setSongNo(int songNo) {
        this.songNo = songNo;
    }

    @Override
    public String toString() {
        return "Artist{" +
                "name='" + name + '\'' +
                ", number of album ='" + albumNo + '\'' +
                ", number of song ='" + songNo + '\'' +
                '}';
    }
}
