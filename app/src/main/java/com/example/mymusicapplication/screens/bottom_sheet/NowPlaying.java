package com.example.mymusicapplication.screens.bottom_sheet;

public class NowPlaying {

    private long id;
    private String imagePath;
    private long duration;
    private boolean isPlaying;

    public NowPlaying(long id, String imagePath, long duration, boolean isPlaying) {
        this.id = id;
        this.imagePath = imagePath;
        this.duration = duration;
        this.isPlaying = isPlaying;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public void setPlaying(boolean playing) {
        isPlaying = playing;
    }
}
