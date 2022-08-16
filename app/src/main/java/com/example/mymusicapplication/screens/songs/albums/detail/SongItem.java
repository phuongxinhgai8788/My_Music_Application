package com.example.mymusicapplication.screens.songs.albums.detail;

public class SongItem {
    private long id;
    private String name;
    private long duration;

    public SongItem(long id, String name, long duration) {
        this.id = id;
        this.name = name;
        this.duration = duration;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public String getDurationString(){
        int minute = (int) ((duration/1000)/60);
        int second = (int) ((duration/1000)%60);
        return minute+" : "+second;
    }
}
