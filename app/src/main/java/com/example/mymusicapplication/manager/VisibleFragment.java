package com.example.mymusicapplication.manager;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.mymusicapplication.sender_receiver_service_worker.MusicWorker;

public class VisibleFragment extends Fragment {

    private Context context;
    private final String TAG = "VisibleFragment";

    private BroadcastReceiver onShowNotification = new BroadcastReceiver(){

        @Override
        public void onReceive(Context context, Intent intent) {

            setResultCode(Activity.RESULT_CANCELED);
            Log.i(TAG, "Visible Fragment has received intent");
        }
    };

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onStart() {
        super.onStart();
        IntentFilter filter = new IntentFilter(MusicWorker.ACTION_SHOW_NOTIFICATION);
        filter.setPriority(0);
        requireActivity().registerReceiver(
                onShowNotification,
                filter,
                MusicWorker.PERMISSION_PRIVATE,
                null
        );
    }

    @Override
    public void onStop() {
        super.onStop();
        requireActivity().unregisterReceiver(onShowNotification);
    }
}
