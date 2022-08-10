package com.example.mymusicapplication.repository;

import android.content.Context;
import android.graphics.Bitmap;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.mymusicapplication.model.Song;
import com.example.mymusicapplication.utils.Constants;

public class AlbumArtLoader {
    private Context context;
    private static AlbumArtLoader INSTANCE;

    private AlbumArtLoader(Context context){
        this.context = context;
    }
    public static void initialize(Context context){
        INSTANCE = new AlbumArtLoader(context);
    }

    public static AlbumArtLoader getINSTANCE(){
        if(INSTANCE == null){
            throw new IllegalStateException("AlbumArtLoader must be initialized!");
        }
        return INSTANCE;
    }

    public Bitmap getAlbumArtLoader(Song song){
        try{
           return Glide.with(context)
                    .asBitmap()
                    .override(Constants.ALBUM_ART_SIZE)
                    .load(song.getAlbumArtPath)
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .submit()
                    .get();
        }catch (Exception e){
            return null;
        }

    }
}
