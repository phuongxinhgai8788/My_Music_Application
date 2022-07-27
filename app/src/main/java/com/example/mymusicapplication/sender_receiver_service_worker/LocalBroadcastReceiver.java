package com.example.mymusicapplication.sender_receiver_service_worker;

import android.app.Activity;
import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.core.app.NotificationManagerCompat;

import com.example.mymusicapplication.utils.Constant;

public class LocalBroadcastReceiver extends BroadcastReceiver {
    String TAG = "LocalBroadcastReceiver";
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent serviceIntent = new Intent(context, MusicService.class);
        if(intent != null){
            String action = intent.getAction();
            Log.i(TAG, action);
            switch (action){
                case Constant.ACTION_PLAY:
                    serviceIntent.setAction(Constant.ACTION_PLAY);
                    serviceIntent.putExtra(Constant.ARG_PLAY_LIST, intent.getSerializableExtra(Constant.ARG_PLAY_LIST));
                    context.startService(serviceIntent);
                    break;

                case Constant.ACTION_PLAY_NEXT:
                    serviceIntent.setAction(Constant.ACTION_PLAY_NEXT);
                    serviceIntent.putExtra(Constant.ARG_PLAY_LIST, intent.getSerializableExtra(Constant.ARG_PLAY_LIST));
                    context.startService(serviceIntent);
                    break;
                case Constant.ACTION_PLAY_PREVIOUS:
                    serviceIntent.setAction(Constant.ACTION_PLAY_PREVIOUS);
                    serviceIntent.putExtra(Constant.ARG_PLAY_LIST, intent.getSerializableExtra(Constant.ARG_PLAY_LIST));
                    context.startService(serviceIntent);
                    break;
                case Constant.ACTION_PAUSE:
                    serviceIntent.setAction(Constant.ACTION_PAUSE);
                    serviceIntent.putExtra(Constant.ARG_PLAY_LIST, intent.getSerializableExtra(Constant.ARG_PLAY_LIST));
                    context.startService(serviceIntent);
                    break;
                case Constant.ACTION_RESUME:
                    serviceIntent.setAction(Constant.ACTION_RESUME);
                    serviceIntent.putExtra(Constant.ARG_PLAY_LIST, intent.getSerializableExtra(Constant.ARG_PLAY_LIST));
                    context.startService(serviceIntent);
                    break;
                case MusicWorker.ACTION_SHOW_NOTIFICATION:
                    Log.i(TAG, "LocalBroadcastReceiver has received intent");
                    if(getResultCode() != Activity.RESULT_CANCELED) {

                        int requestCode = intent.getIntExtra(MusicWorker.REQUEST_CODE, 0);
                        Notification notification = intent.getParcelableExtra(MusicWorker.NOTIFICATION);
                        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
                        notificationManagerCompat.notify(requestCode, notification);
                    }
                    break;
            }

                   }
    }
}
