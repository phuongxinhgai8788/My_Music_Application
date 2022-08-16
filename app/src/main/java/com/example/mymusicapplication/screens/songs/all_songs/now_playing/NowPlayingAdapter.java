package com.example.mymusicapplication.screens.songs.all_songs.now_playing;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymusicapplication.R;
import com.example.mymusicapplication.databinding.LayoutItemNowPlayingBinding;
import com.example.mymusicapplication.service.MusicService;
import com.example.mymusicapplication.utils.Constants;


public class NowPlayingAdapter extends RecyclerView.Adapter<NowPlayingHolder> {

    private NowPlayingViewModel nowPlayingViewModel;
    private Context context;

    public NowPlayingAdapter(NowPlayingViewModel nowPlayingViewModell, Context context){
        nowPlayingViewModel = nowPlayingViewModell;
        this.context = context;
    }

    @NonNull
    @Override
    public NowPlayingHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        LayoutItemNowPlayingBinding binding = LayoutItemNowPlayingBinding.inflate(inflater, parent, false);

        return new NowPlayingHolder(binding, context);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull NowPlayingHolder holder, int position) {
        holder.bind(nowPlayingViewModel);
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    }

 class NowPlayingHolder extends RecyclerView.ViewHolder{

    private static final String TAG = "NowPlayingHolder";
    private LayoutItemNowPlayingBinding binding;
    private Context context;

    public NowPlayingHolder(@NonNull LayoutItemNowPlayingBinding binding, Context context) {
        super(binding.getRoot());
        this.binding = binding;
        this.context = context;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void bind(com.example.mymusicapplication.screens.songs.all_songs.now_playing.NowPlayingViewModel nowPlayingViewModel){
        binding.setViewModel(nowPlayingViewModel);
        binding.playNextBtn.setOnClickListener(view -> {
            startForegroundService(Constants.ACTION_PLAY_NEXT);

        });
        binding.playPrevBtn.setOnClickListener(view -> {
            startForegroundService(Constants.ACTION_PLAY_PREVIOUS);
        });
        binding.playBtn.setOnClickListener(view -> {
            startForegroundService(Constants.ACTION_PAUSE_RESUME);
            if(nowPlayingViewModel.getMusicIsPlaying()){
                binding.playBtn.setImageResource(R.drawable.ic_pause);
            }else{
                binding.playBtn.setImageResource(R.drawable.ic_play);
            }
        });
        binding.executePendingBindings();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void startForegroundService(String action){
        Log.i(TAG, "startForeground");
        Intent intent = new Intent(context, MusicService.class);
        intent.setAction(action);
        context.startForegroundService(intent);
    }
}
