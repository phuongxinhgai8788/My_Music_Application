package com.example.mymusicapplication.screens.activities;

import android.util.Log;
import android.view.View;

import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.example.mymusicapplication.R;

import com.example.mymusicapplication.base.BaseActivity;
import com.example.mymusicapplication.databinding.MainActivityBinding;

public class MainActivity extends BaseActivity<MainActivityBinding> {
    private NavHostFragment _navHostFragment;
    public static NavController mNavController;


    @Override
    protected int getLayout() {
        return R.layout.main_activity;
    }

    @Override
    protected void onInit() {
        createNavController();
    }

    private void createNavController() {
        _navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container_view);
        mNavController = _navHostFragment.getNavController();

        NavigationUI.setupWithNavController(binding.bottomNavigate, mNavController);
        binding.imgMenu.setOnClickListener(view -> {
            binding.drawableLayout.openDrawer(GravityCompat.START);
        });

    }

    public void updateIconStartHome(Integer src){
        if(src != null){
            binding.imgMenu.setBackgroundResource(src);
            binding.imgMenu.setVisibility(View.VISIBLE);
            return;
        }
        binding.imgMenu.setVisibility(View.INVISIBLE);
    }

    public void updateIconEndHome(Integer src){
        Log.d("SangTB", "updateIconEndHome: "+src);
        if(src != null){
            binding.imgSearch.setVisibility(View.VISIBLE);
            binding.imgSearch.setBackgroundResource(src);
            return;
        }
        binding.imgSearch.setVisibility(View.INVISIBLE);
    }

    public void updateTitleHome(String title){
        binding.txtTitle.setText(title);
    }

    public void displayEdittext(boolean display){
        binding.edtSearch.setVisibility(display? View.VISIBLE : View.INVISIBLE);
        if(display){
            binding.imgSearch.setVisibility(View.INVISIBLE);
            binding.txtTitle.setText("");
            return;
        }
        binding.imgSearch.setVisibility(View.VISIBLE);
    }
    }

