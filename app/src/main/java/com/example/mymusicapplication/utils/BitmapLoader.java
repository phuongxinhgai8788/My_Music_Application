package com.example.mymusicapplication.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.net.Uri;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

public class BitmapLoader {
    private static BitmapLoader INSTANCE;
    private Context context;

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

    public Bitmap getBitmap(String uriString){

        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        byte[] rawArt;
        Bitmap art = null;
        BitmapFactory.Options bfo=new BitmapFactory.Options();

        mmr.setDataSource(context, Uri.parse(uriString));
        rawArt = mmr.getEmbeddedPicture();

        if (null != rawArt){
            art = BitmapFactory.decodeByteArray(rawArt, 0, rawArt.length, bfo);
        }
        return art;
    }

}
