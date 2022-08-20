package com.example.essence;

import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.essence.Fragments.Hardware;
import com.example.essence.Fragments.Lockscreen;
import com.example.essence.Fragments.Qs;
import com.example.essence.Fragments.StatusBar;
import com.example.essence.Fragments.System;
import com.example.essence.Fragments.Themes;

public class ViewPageAdapter extends FragmentStateAdapter {
    //totalTabs: holds total number of fragments count
    //curTab: holds the current tab thats being/tobe displayed
    int totalTabs = 6, curTab;
    final static String TAG = "VPAdapter";
    public ViewPageAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                Log.d(TAG,"Hardware created");
                return new Hardware();
            case 1:
                Log.d(TAG,"Lockscreen created");
                return new Lockscreen();
            case 2:
                Log.d(TAG,"Qs created");
                return new Qs();
            case 3:
                Log.d(TAG, "StatusBar created");
                return new StatusBar();
            case 4:
                Log.d(TAG, "System created");
                return new System();
            case 5:
                Log.d(TAG,"Themes created");
                return new Themes();
            default:
                return null;
        }
    }
    // this counts total number of tabs
    public int getItemCount() {
        return totalTabs;
    }
}
