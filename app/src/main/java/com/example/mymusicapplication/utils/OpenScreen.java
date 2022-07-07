package com.example.mymusicapplication.utils;

import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.mymusicapplication.NoPermissionFragment;
import com.example.mymusicapplication.PlayListFragment;
import com.example.mymusicapplication.R;
import com.example.mymusicapplication.SongDetailFragment;

public class OpenScreen {

    public static void openScreen(Fragment fragment, boolean isAddedToBackstack, boolean isPortraitLayout, AppCompatActivity activity){
           Log.i("OpenScreen", "openScreen");
            String TAG = fragment.getClass().getSimpleName();
            Fragment sameTagFragment = activity.getSupportFragmentManager()
                    .findFragmentByTag(TAG);
            if (sameTagFragment != null) {
                activity.getSupportFragmentManager().beginTransaction()
                        .remove(sameTagFragment)
                        .commit();
            }
            FragmentTransaction fragmentTransaction = activity.getSupportFragmentManager()
                    .beginTransaction();

            if (isPortraitLayout) {
                fragmentTransaction.replace(R.id.container, fragment, TAG);
            } else {
                if (TAG.equals(PlayListFragment.class.getSimpleName())) {
                    fragmentTransaction.replace(R.id.list_container, fragment, TAG);
                } else if (TAG.equals(SongDetailFragment.class.getSimpleName())) {
                    fragmentTransaction.replace(R.id.detail_container, fragment, TAG);
                } else if(TAG.equals(NoPermissionFragment.class.getSimpleName())){
                    fragmentTransaction.replace(R.id.list_container, fragment, TAG);
                }
            }

            if (isAddedToBackstack) {
                fragmentTransaction.addToBackStack(TAG);
            }

            try {
                fragmentTransaction.commit();
            } catch (IllegalStateException exception) {
                exception.printStackTrace();
                fragmentTransaction.commitAllowingStateLoss();
            }
        }
    }
