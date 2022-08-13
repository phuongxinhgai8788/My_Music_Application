package com.example.mymusicapplication.screens.playlist_screens.my_playlist;


import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

public class MyPlaylistViewModel extends BaseObservable {

    private MyPlaylistSource playlistSource = MyPlaylistSource.getInstance();
    private MyPlaylist myPlaylist;

    public MyPlaylistViewModel(MyPlaylist myPlaylist){
        this.myPlaylist = myPlaylist;
    }


    public void addPlaylistToSource(MyPlaylist playlist){
        playlistSource.addPlaylist(playlist);
    }

    public void removePlaylistFromSource(MyPlaylist playlist){
        playlistSource.removePlaylist(playlist);
    }

    @Bindable
    public String getMyPlaylistName(){
        return myPlaylist.getName();
    }

}
