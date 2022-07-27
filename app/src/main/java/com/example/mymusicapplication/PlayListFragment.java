package com.example.mymusicapplication;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymusicapplication.manager.MainActivity;
import com.example.mymusicapplication.manager.VisibleFragment;
import com.example.mymusicapplication.repository.Repository;
import com.example.mymusicapplication.sender_receiver_service_worker.LocalBroadcastSender;
import com.example.mymusicapplication.utils.OpenScreen;
import com.example.mymusicapplication.model.Song;

import java.util.ArrayList;


public class PlayListFragment extends VisibleFragment {

    private static final String ARG_PLAY_LIST = "param2";

    private ArrayList<Song> songs = new ArrayList<>();
    private RecyclerView playListRV;
    private Context context;
    private SongListAdapter adapter;
    private String TAG = "PlaylistFragment";
    Repository repository;
    LocalBroadcastSender localBroadcastSender;

    public PlayListFragment() {
        // Required empty public constructor
    }

    public static PlayListFragment newInstance(ArrayList<Song> songs) {
        PlayListFragment fragment = new PlayListFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PLAY_LIST, songs);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments()!=null){
            songs = (ArrayList<Song>) getArguments().getSerializable(ARG_PLAY_LIST);
        }
        Log.i(TAG, "onCreate");
        context = requireContext();
        repository = Repository.getInstance(context);
        localBroadcastSender = (LocalBroadcastSender)context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_play_list, container, false);
        playListRV = view.findViewById(R.id.list_recycler_view);

        TextView textView = new TextView(context);
        textView.setText(R.string.list_is_empty);
        textView.setTextSize(R.dimen.title_text_size);
        textView.setTypeface(textView.getTypeface(), Typeface.BOLD);
        if(songs.size()==0){
            playListRV.addView(textView);
        }else{
            playListRV.setLayoutManager(new LinearLayoutManager(context));
            adapter = new SongListAdapter(Song.itemCallback);
            adapter.submitList(songs);
            playListRV.setAdapter(adapter);
        }
        return view;
    }

    private class SongListAdapter extends ListAdapter<Song, SongListAdapter.SongItemHolder> {


        protected SongListAdapter(@NonNull DiffUtil.ItemCallback<Song> diffCallback) {
            super(diffCallback);
        }

        @NonNull
        @Override
        public SongListAdapter.SongItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            View itemView = layoutInflater.inflate(R.layout.song_item, parent, false);
            return new SongItemHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull SongListAdapter.SongItemHolder holder, int position) {
            Song song = getItem(position);
            holder.bind(song, position);
        }

        private class SongItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
            private int songPosition;
            private Song song;
            private TextView titleTV;
            private TextView artistTV;

            public SongItemHolder(@NonNull View itemView) {
                super(itemView);
                titleTV = itemView.findViewById(R.id.title_tv);
                artistTV = itemView.findViewById(R.id.artist_tv);
                itemView.setOnClickListener(this);

            }

            public void bind(Song song, int songPosition){
                titleTV.setText(song.getTitle());
                artistTV.setText(song.getAuthor());
                this.songPosition = songPosition;
                this.song = songs.get(songPosition);
            }

            @Override
            public void onClick(View view) {
                repository.savePlayedSongIndex(songPosition);
                boolean isPortrait = repository.getIsPortraitScreen();
                if(isPortrait) {
                    SongDetailFragment songDetailFragment = SongDetailFragment.newInstance(songs, songPosition);
                    OpenScreen.openScreen(songDetailFragment, true, isPortrait,(MainActivity)context);
                    localBroadcastSender.sendBroadcastPlay(songs);
                }else{
                    localBroadcastSender.sendBroadcastChangeSongDetail(song);
                }
                    localBroadcastSender.sendBroadcastPlay(songs);
            }

        }
    }
}