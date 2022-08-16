package com.example.mymusicapplication.utils;

import android.content.Context;
import android.graphics.Bitmap;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

public class BitmapLoader {
    private static BitmapLoader INSTANCE;
    private Context context;
    private BitmapLoader bitmapLoader;

    private BitmapLoader(Context context) {
        this.context = context;
    }

    public static void initialize(Context context) {
        INSTANCE = new BitmapLoader(context);
    }

    public static BitmapLoader getInstance(){
        if(INSTANCE == null){
            throw new IllegalStateException("BitmapLoader must be initialized!");
        }
        return INSTANCE;
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
