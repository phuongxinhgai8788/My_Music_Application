package com.example.mymusicapplication.sender_receiver_service_worker;

import com.example.mymusicapplication.model.Song;

import java.util.ArrayList;

public interface LocalBroadcastSender {

    void sendBroadcastPlay(ArrayList<Song> songs);
    void sendBroadcastChangeSongDetail(/*Song song*/Song song);

    void sendBroadcastPlayNext(ArrayList<Song> songs);

    void sendBroadcastPlayPrevious(ArrayList<Song> songs);

    void sendBroadcastPause(ArrayList<Song> songs);

    void sendBroadcastResume(ArrayList<Song> songs);
}
