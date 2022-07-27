package com.example.mymusicapplication.sender_receiver_service_worker;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationManagerCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.mymusicapplication.R;
import com.example.mymusicapplication.manager.MainActivity;
import com.example.mymusicapplication.manager.MyApplication;
import com.example.mymusicapplication.repository.Repository;

public class MusicWorker extends Worker {

    public static final String REQUEST_CODE = "REQUEST_CODE";
    public static final String NOTIFICATION = "NOTIFICATION";
    private Context context;
    private Repository repository = Repository.getInstance();
    private final String TAG = "MusicWorker";
    public static final String ACTION_SHOW_NOTIFICATION = "com.example.mymusicapplication.SHOW_NOTIFICATION";
    public static final String PERMISSION_PRIVATE = "com.example.mymusicapplication.PRIVATE";

    public MusicWorker(@androidx.annotation.NonNull Context context, @androidx.annotation.NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        this.context = context;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @androidx.annotation.NonNull
    @Override
    public Result doWork() {
        Log.i(TAG, "Work request triggered");
            Intent intent = MainActivity.newIntent(context);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
            Resources resources = context.getResources();
            Notification notification = new Notification.Builder(context, MyApplication.NOTIFICATION_CHANNEL_ID)
                    .setTicker(resources.getString(R.string.app_name))
                    .setSmallIcon(R.mipmap.avatar)
                    .setContentTitle(resources.getString(R.string.notification_title))
                    .setContentText(resources.getString(R.string.notification_content))
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)
                    .build();
            showBackgroundNotification(0, notification);

        return Result.success();
    }

    private void showBackgroundNotification(int requestCode, Notification notification) {
        Log.i(TAG, "Intent is sent!");
        Intent intentShowNotification = new Intent(ACTION_SHOW_NOTIFICATION);
        intentShowNotification.setAction(ACTION_SHOW_NOTIFICATION);
        intentShowNotification.putExtra(REQUEST_CODE, requestCode);
        intentShowNotification.putExtra(NOTIFICATION, notification);
        context.sendOrderedBroadcast(intentShowNotification, PERMISSION_PRIVATE);
    }
}
