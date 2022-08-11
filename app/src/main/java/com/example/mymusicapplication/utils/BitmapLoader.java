package com.example.mymusicapplication.utils;

import android.content.Context;
import android.graphics.Bitmap;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.mymusicapplication.model.Song;
import com.example.mymusicapplication.utils.Constants;

public class BitmapLoader {
    private Context context;

    public BitmapLoader(Context context){
        this.context = context;
    }

    public Bitmap getBitmap(String imagePath){
        try{
           return Glide.with(context)
                    .asBitmap()
                    .override(Constants.ALBUM_ART_SIZE)
                    .load(imagePath)
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .submit()
                    .get();
        }catch (Exception e){
            return null;
        }
    }

}