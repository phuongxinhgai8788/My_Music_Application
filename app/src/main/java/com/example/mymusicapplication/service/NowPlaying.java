package com.example.mymusicapplication.service;

public class NowPlaying {
    private long songId;
    private String songName;
    private String artist;
    private String album;
    private int duration;
    private int currentPlayedPosition;
    private boolean isShuffleOn;
    private boolean isRepeated;

    public NowPlaying(long songId, String songName, String artist, String album, int duration,
                      int currentPlayedPosition, boolean isShuffleOn, boolean isRepeated) {
        this.songId = songId;
        this.songName = songName;
        this.artist = artist;
        this.album = album;
        this.duration = duration;
        this.currentPlayedPosition = currentPlayedPosition;
        this.isShuffleOn = isShuffleOn;
        this.isRepeated = isRepeated;
    }

    public long getSongId() {
        return songId;
    }

    public void setSongId(long id) {
        this.songId = id;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String name) {
        this.songName = name;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getCurrentPlayedPosition() {
        return currentPlayedPosition;
    }

    public void setCurrentPlayedPosition(int currentPlayedPosition) {
        this.currentPlayedPosition = currentPlayedPosition;
    }

    public boolean isShuffleOn() {
        return isShuffleOn;
    }

    public void setShuffleOn(boolean shuffleOn) {
        isShuffleOn = shuffleOn;
    }

    public boolean isRepeated() {
        return isRepeated;
    }

    public void setRepeated(boolean repeated) {
        isRepeated = repeated;
    }

    @Override
    public String toString() {
        return "NowPlaying{" +
                "songId=" + songId +
                ", songName='" + songName + '\'' +
                ", artist='" + artist + '\'' +
                ", album='" + album + '\'' +
                ", duration=" + duration +
                ", currentPlayedPosition=" + currentPlayedPosition +
                ", isShuffleOn=" + isShuffleOn +
                ", isRepeated=" + isRepeated +
                '}';
    }
}
