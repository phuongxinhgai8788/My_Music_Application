package com.example.mymusicapplication.screens.activities;

import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.example.mymusicapplication.R;

import com.example.mymusicapplication.base.BaseActivity;
import com.example.mymusicapplication.databinding.MainActivityBinding;

public class MainActivity extends BaseActivity<MainActivityBinding> {

    private NavHostFragment navHostFragment;
    public static NavController navController;

    @Override
    protected int getLayout() {
        return R.layout.main_activity;
    }

    @Override
    protected void onInit() {
        createNavController();
    }

    private void createNavController() {
        navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container_view);
        navController = navHostFragment.getNavController();

        NavigationUI.setupWithNavController(binding.bottomNavigate, navController);
        binding.imgMenu.setOnClickListener(view -> {
            binding.drawableLayout.openDrawer(GravityCompat.START);
        });
    }
    }

