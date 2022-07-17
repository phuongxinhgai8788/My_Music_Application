package com.example.mymusicapplication;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.mymusicapplication.model.Song;
import com.example.mymusicapplication.repository.Repository;
import com.example.mymusicapplication.sender_receiver_service.LocalBroadcastSender;
import com.example.mymusicapplication.sender_receiver_service.MyMediaPlayer;
import com.example.mymusicapplication.utils.Constant;

import java.time.Clock;
import java.util.ArrayList;
import java.util.List;


public class SongDetailFragment extends Fragment implements View.OnClickListener, SharedPreferences.OnSharedPreferenceChangeListener {


    private static final String ARG_INDEX = "param2";
    private static final String ARG_SONG_LIST = "param3";

    // TODO: Rename and change types of parameters
    private Song song;
    private int songIndex;
    private Context context;
    private String TAG = "SongDetailFragment";
    private static TextView songTitleTV;
    private static TextView singerTV;
    private TextView currentPositionTV;
    private TextView songLengthTV;
    private ImageView prevBtn;
    private ImageView nextBtn;
    private ImageView playOrPauseBtn;
    private SeekBar seekBar;
    private ArrayList<Song> songs = new ArrayList<>();
//    private static SongDetailFragment instance;
    LocalBroadcastSender localBroadcastSender;
    Repository repository;
    MyMediaPlayer myMediaPlayer= MyMediaPlayer.getInstance();


    public SongDetailFragment() {
        // Required empty public constructor
    }

    public static SongDetailFragment newInstance(ArrayList<Song> songs, int songIndex) {
//       if(instance == null) {
           SongDetailFragment instance = new SongDetailFragment();
           Bundle args = new Bundle();
           args.putInt(ARG_INDEX, songIndex);
           args.putSerializable(ARG_SONG_LIST, songs);
           instance.setArguments(args);
//       }
        return instance;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            songIndex = getArguments().getInt(ARG_INDEX);
            songs = (ArrayList<Song>) getArguments().getSerializable(ARG_SONG_LIST);
            song = songs.get(songIndex);
        }
        context = requireContext();
        repository = Repository.getInstance(context);
        localBroadcastSender = (LocalBroadcastSender) context;
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constant.ACTION_CHANGE_SONG_DETAIL);
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(requireContext());
        localBroadcastManager.registerReceiver(broadcastReceiver,filter);
        repository.registerListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_song_detail, container, false);
        songTitleTV = view.findViewById(R.id.tv_song_name);
        singerTV = view.findViewById(R.id.tv_song_author);
        currentPositionTV = view.findViewById(R.id.tv_current_time);
        songLengthTV = view.findViewById(R.id.tv_total_time);
        nextBtn = view.findViewById(R.id.iv_play_next);
        nextBtn.setOnClickListener(this);
        prevBtn = view.findViewById(R.id.iv_play_prev);
        prevBtn.setOnClickListener(this);
        seekBar = view.findViewById(R.id.timer);
        playOrPauseBtn = view.findViewById(R.id.iv_play);
        if(repository.getMusicIsPlaying()){
            playOrPauseBtn.setImageResource(R.drawable.ic_pause);
        }else{
            playOrPauseBtn.setImageResource(R.drawable.ic_play);
        }
        playOrPauseBtn.setOnClickListener(this);
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        songTitleTV.setText(song.getTitle());
        singerTV.setText(song.getAuthor());
        currentPositionTV.setText(repository.getPlayedSongPosition()+"");
        songLengthTV.setText(repository.getSongDuration()+"");
    }

    private final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent!=null) {
                if (Constant.ACTION_CHANGE_SONG_DETAIL.equals(intent.getAction())) {
                    song = (Song) intent.getSerializableExtra(Constant.ARG_ABOUT_TO_PLAY_SONG);
                    updateUI(song);
                }
            }
        }
    };
    private void updateUI(Song aboutToPlaySong){
        songTitleTV.setText(aboutToPlaySong.getTitle());
        singerTV.setText(aboutToPlaySong.getAuthor());
    }

    @Override
    public void onClick(View view) {
        int viewId = view.getId();

        switch (viewId){
            case R.id.iv_play_next:
                songIndex = songIndex<(songs.size()-1)?songIndex+1:0;
                song = songs.get(songIndex);
                localBroadcastSender.sendBroadcastPlayNext(songs);
                break;
            case R.id.iv_play_prev:
                songIndex = songIndex==0?songs.size()-1:songIndex-1;
                song = songs.get(songIndex);
                myMediaPlayer.prepareSong(song);
                localBroadcastSender.sendBroadcastPlayPrevious(songs);
                break;
            case R.id.iv_play:
                if(repository.getMusicIsPlaying()){
                    playOrPauseBtn.setImageResource(R.drawable.ic_play);
                    localBroadcastSender.sendBroadcastPause(songs);
                }else{
                    playOrPauseBtn.setImageResource(R.drawable.ic_pause);
                    localBroadcastSender.sendBroadcastResume(songs);
                }

            default:
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        repository.unregisterListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        if(Repository.PREF_SAVE_PLAYED_SONG_INDEX.equals(s)){
            localBroadcastSender.sendBroadcastChangeSongDetail(song);
        }
    }
}