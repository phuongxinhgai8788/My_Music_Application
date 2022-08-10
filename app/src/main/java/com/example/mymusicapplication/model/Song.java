package com.example.mymusicapplication.model;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.MediaStore;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.example.mymusicapplication.utils.Constants;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class Song implements Serializable {

    private long id;
    private String title;
    private String artist;
    private Long duration;
    private Long albumId;

    public Song(long id, String title, String artist, long duration, long albumId){
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.duration = duration;
        this.albumId = albumId;
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

    public Long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(Long albumId) {
        this.albumId = albumId;
    }

    public Uri getURI(){
        return ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, id);
    }

    public boolean isUnknownArtist(){
        return StringUtils.isBlank(artist) || artist.toLowerCase().equals("<unknown>");
    }

    @Override
    public String toString() {
        return "Song{" +
                "title='" + title + '\'' +
                ", artist='" + artist + '\'' +
                ", duration=" +getDurationStr()+
                '}';
    }
}
