package com.example.mymusicapplication.base;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.mymusicapplication.app.MyApplication;
import com.example.mymusicapplication.model.DataAction;
import com.example.mymusicapplication.screens.activities.MainActivity;

public abstract class BaseFragment<VB extends ViewDataBinding,VM extends BaseViewModel> extends Fragment {
    protected VB binding;
    protected VM viewModel;
    protected final String TAG = this.getClass().getName();

    protected abstract Class<VM> getClassName();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,getLayout(),container,false);
        binding.setLifecycleOwner(getViewLifecycleOwner());

        viewModel = new ViewModelProvider(getViewModelStore(),MyApplication.factory).get(getClassName());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel.eventSender.observe(getViewLifecycleOwner(), dataAction -> {
            Log.d(TAG, "onChanged: "+dataAction.getEventSender());
            switch (dataAction.getEventSender()){
                case ON_CLOSE:
                    requireActivity().finish();
                    break;
                case ON_NAVIGATE:
                    MainActivity.mNavController.navigate(dataAction.getActionId(),dataAction.getBundle());
                    break;
                case SHOW_TOAST:
                    Toast.makeText(requireContext(),dataAction.getMessage(),Toast.LENGTH_LONG).show();
                    break;
                default:
                    throw new IllegalStateException("No implement method: " + dataAction.getEventSender().ordinal());
            }
        });
    }

    protected abstract int getLayout();

    protected void updateIconMenuHome(Integer src){
        MainActivity activity = getActivityMain();
        if(activity != null){
            activity.updateIconStartHome(src);
        }
    }

    protected void displayEdittextHome(boolean display) {
        MainActivity activity = getActivityMain();
        if (activity != null) {
            activity.displayEdittext(display);
        }
    }

    protected void updateIconEndHome(Integer src){
        MainActivity activity = getActivityMain();
        if(activity != null){
            activity.updateIconEndHome(src);
        }
    }

    protected void updateTitleHome(String title){
        MainActivity activity = getActivityMain();
        if(activity != null){
            activity.updateTitleHome(title);
        }
    }

    private MainActivity getActivityMain(){
        if(getActivity().getClass().equals(MainActivity.class)){
            return (MainActivity) getActivity();
        }
        return null;
    }}
