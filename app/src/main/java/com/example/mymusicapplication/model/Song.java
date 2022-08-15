package com.example.mymusicapplication.model;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.MediaStore;

import com.example.mymusicapplication.utils.Constants;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class Song implements Serializable {

    private long id;
    private String title;
    private String artist;
    private Long duration;
    private String album;
    private Long albumId;
    private int image;
    private List<String> genreList = new ArrayList<>();

    public Song(long id, String title, String artist, long duration, String album, Long albumId, List<String> genreList,int image){
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.duration = duration;
        this.album = album;
        this.albumId = albumId;
        this.genreList = genreList;
        this.image = image;
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

    public void setAlbum(String album) {
        this.album = album;
    }

    public Long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(Long albumId) {
        this.albumId = albumId;
    }

    public String getAlbumArtPath = Constants.ALBUM_ART_PATH + albumId;

    public String getDurationStr(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss", Locale.CHINA);
        return simpleDateFormat.format(new Date(duration));
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbumId(String albumId) {
        this.album = albumId;
    }

    public List<String> getGenreList() {
        return genreList;
    }

    public void setGenreList(List<String> genreList) {
        this.genreList = genreList;
    }

    public Uri getURI(){
        return ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, id);
    }

    public boolean isUnknownArtist(){
        return StringUtils.isBlank(artist) || artist.equalsIgnoreCase("<unknown>");
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    private String getGenreArrayString(){
        String result = "genres: ";
        int size = genreList.size();
        for(int i=0; i<size; i++){
            result+= genreList.get(i);
        }
        return result;
    }
    @Override
    public String toString() {
        return "Song{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", artist='" + artist + '\'' +
                ", duration=" + duration +
                ", album=" + album +
                ", genre='" + getGenreArrayString() + '\'' +
                ", getAlbumArtPath='" + getAlbumArtPath + '\'' +
                '}';
    }
}
