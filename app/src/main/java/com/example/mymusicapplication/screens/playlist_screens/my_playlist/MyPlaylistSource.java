package com.example.mymusicapplication.screens.playlist_screens.my_playlist;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MyPlaylistSource {
    private static MyPlaylistSource INSTANCE;

    private static List<MyPlaylist> playlistList = new ArrayList<>();

    private MyPlaylistSource(){
        playlistList.add( new MyPlaylist("Sample playlist 01", Arrays.asList("Song 01", "Song 02")));
        playlistList.add(new MyPlaylist("Sample playlist 02", Arrays.asList("Song 03", "Song 04")));
    }

    public static MyPlaylistSource getInstance(){
        if(INSTANCE == null){
            INSTANCE = new MyPlaylistSource();
        }
        return INSTANCE;
    }
    public void addPlaylist(MyPlaylist playlist){
        playlistList.add(playlist);
    }

    public void removePlaylist(MyPlaylist playlist){
        playlistList.remove(playlist);
    }

    public List<MyPlaylist> getPlaylistList(){
        return playlistList;
    }
}
