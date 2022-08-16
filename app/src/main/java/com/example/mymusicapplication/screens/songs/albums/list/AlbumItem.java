package com.example.mymusicapplication.screens.songs.albums.list;

public class AlbumItem {
    private long id;
    private String name;
    private String dummyArtistName;
    private String imagePath;
    private int songNo;

    public AlbumItem(long id, String name, String dummyArtistName, String imagePath, int songNo){
        this.id = id;
        this.name = name;
        this.dummyArtistName = dummyArtistName;
        this.imagePath = imagePath;
        this.songNo = songNo;
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

    public String getImagePath(){
        return this.imagePath = imagePath;
    }

    public void setImagePath(String imagePath){
        this.imagePath = imagePath;
    }

    public String getDummyArtistName() {
        return dummyArtistName;
    }

    public void setDummyArtistName(String dummyArtistName) {
        this.dummyArtistName = dummyArtistName;
    }

    public int getSongNo() {
        return songNo;
    }

    public void setSongNo(int songNo) {
        this.songNo = songNo;
    }
}
