package com.example.mymusicapplication.screens.home.hot_recommended;

import android.app.Application;

import com.example.mymusicapplication.base.BaseViewModel;
import com.example.mymusicapplication.base.interfaces.IActionAdapterRecycler;

public class HotRecommendedViewModel extends BaseViewModel implements IActionAdapterRecycler<HotRecommended> {


    public HotRecommendedViewModel(Application application){
        super(application);
    }
    @Override
    public void onClickListener(HotRecommended data) {

    }

    @Override
    public void onLongClickListener(HotRecommended data) {

    }

    @Override
    public void onNavigate(HotRecommended data) {

    }
}
