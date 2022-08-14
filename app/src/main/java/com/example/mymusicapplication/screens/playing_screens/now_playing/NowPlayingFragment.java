package com.example.mymusicapplication.screens.playing_screens.now_playing;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mymusicapplication.R;
import com.example.mymusicapplication.data_source.MyMediaCursor;
import com.example.mymusicapplication.databinding.FragmentNowPlayingBinding;
import com.example.mymusicapplication.repository.PlayingStatus;
import com.example.mymusicapplication.screens.playing_screens.NowPlayingViewModel;
import com.example.mymusicapplication.service.MusicService;
import com.example.mymusicapplication.utils.Constants;

public class NowPlayingFragment extends Fragment implements SharedPreferences.OnSharedPreferenceChangeListener {

    private FragmentNowPlayingBinding binding;
    private NowPlayingViewModel nowPlayingViewModel;
    private PlayingStatus playingStatus = PlayingStatus.getInstance();
    private Callback callback;
    private String TAG = "NowPlayingFragment";

    public interface Callback{
        void doSomeThing();
    }
    public NowPlayingFragment() {
        // Required empty public constructor
    }

    public static NowPlayingFragment newInstance(String param1, String param2) {
        NowPlayingFragment fragment = new NowPlayingFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
        playingStatus.registerListener(this);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        callback = (Callback) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         binding = DataBindingUtil.inflate(inflater, R.layout.fragment_now_playing,
                container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        nowPlayingViewModel = new NowPlayingViewModel();
        binding.setLifecycleOwner(getViewLifecycleOwner());
        binding.setViewModel(nowPlayingViewModel);
        binding.executePendingBindings();
//        initData();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onStart() {
        super.onStart();

        binding.playNextBtn.setOnClickListener(view -> {
            startForegroundService(Constants.ACTION_PLAY_NEXT);
        });
        binding.playPrevBtn.setOnClickListener(view -> {
            startForegroundService(Constants.ACTION_PLAY_PREVIOUS);
        });
        binding.playBtn.setOnClickListener(view -> {
                startForegroundService(Constants.ACTION_PLAY_PAUSE_RESUME);
        });
        if(nowPlayingViewModel.getMusicIsPlaying()){
            binding.playBtn.setImageResource(R.drawable.ic_pause);
        }else{
            binding.playBtn.setImageResource(R.drawable.ic_play);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void startForegroundService(String action){
        Log.i(TAG, "startForeground");
        Intent intent = new Intent(getContext(), MusicService.class);
        intent.setAction(action);
        getContext().startForegroundService(intent);
    }
    private void initData() {
        Cursor cursor = MyMediaCursor.getInstance().getMediaCursorShuffleOff();
        if(cursor.moveToFirst()){
            long id = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID));
            PlayingStatus.getInstance().saveLastPlayedSongId(id);
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        if (s.equals(Constants.PREF_SAVE_PLAYED_SONG_POSITION)
                || s.equals(Constants.PREF_SAVE_LAST_PLAYED_SONG_ID)
                || s.equals(Constants.PREF_SAVE_MUSIC_IS_PLAYING)
                || s.equals(Constants.PREF_SAVE_IS_SONG_REPEATED)
                || s.equals(Constants.PREF_SAVE_IS_SHUFFLE_ON)) {
            Log.i(TAG, "I can hear your changes");
            callback.doSomeThing();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        playingStatus.unregisterListener(this);
    }
}