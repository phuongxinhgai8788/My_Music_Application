package com.example.mymusicapplication.screens.songs.all_songs;

public class SongItem {

    private long id;
    private String title;
    private String artist;
    private String imagePath;

    public SongItem(long id, String title, String artist, String imagePath) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.imagePath = imagePath;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
