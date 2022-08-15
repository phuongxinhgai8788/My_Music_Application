package com.example.mymusicapplication.screens.songs.all_songs.now_playing;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mymusicapplication.R;
import com.example.mymusicapplication.data_source.MyMediaCursor;
import com.example.mymusicapplication.databinding.FragmentNowPlayingBinding;
import com.example.mymusicapplication.repository.PlayingStatus;
import com.example.mymusicapplication.utils.Constants;

public class FragmentNowPlaying extends Fragment implements SharedPreferences.OnSharedPreferenceChangeListener {

    private FragmentNowPlayingBinding binding;
    private PlayingStatus playingStatus = PlayingStatus.getInstance();
    private String TAG = "NowPlayingFragment";
    
    public FragmentNowPlaying() {
        // Required empty public constructor
    }

    public static FragmentNowPlaying newInstance(String param1, String param2) {
        FragmentNowPlaying fragment = new FragmentNowPlaying();
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
        binding.nowPlayingContainer.setAdapter(new NowPlayingAdapter(new NowPlayingViewModel(), getContext()));
        binding.nowPlayingContainer.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onStart() {
        super.onStart();
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
            updateUI();
        }
    }

    private void updateUI() {
        NowPlayingAdapter nowPlayingAdapter = new NowPlayingAdapter(new NowPlayingViewModel(), getContext());
        binding.nowPlayingContainer.setAdapter(nowPlayingAdapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        playingStatus.unregisterListener(this);
    }

}