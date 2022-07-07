package com.example.mymusicapplication.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;

import androidx.core.app.ActivityCompat;

public class PermissionsUtil {
    private static String TAG ="PermissionsUtil";
    public static boolean checkPermissions(Context context, String... permissions) {
        for (String permission : permissions) {
        if (!checkPermission(context, permission)) {
            Log.i(TAG, "checkPermissions");
            return false;
        }
    }
        return true;
}

    public static boolean checkPermission(Context context, String permission) {
        Log.i(TAG, "checkPermission");
        return ActivityCompat.checkSelfPermission(context, permission)
                == PackageManager.PERMISSION_GRANTED;
    }

    public static void requestPermissions(Activity activity, int requestCode,
                                          String... permissions) {
        Log.i(TAG, "requestPermissions");
        ActivityCompat.requestPermissions(activity, permissions, requestCode);
    }
}
