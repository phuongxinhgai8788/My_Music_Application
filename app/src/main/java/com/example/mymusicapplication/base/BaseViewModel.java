package com.example.mymusicapplication.base;

import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.mymusicapplication.model.DataAction;
import com.example.mymusicapplication.utils.SingleLiveEvent;

public abstract class BaseViewModel extends AndroidViewModel {

    private DataAction dataAction = new DataAction();

    protected final String TAG = this.getClass().getName();
    protected final SingleLiveEvent<DataAction> eventSender = new SingleLiveEvent<>();

    public BaseViewModel(@NonNull Application application) {
        super(application);
    }

    protected void onNavigate(int actionId, Bundle argument){
        dataAction.setActionId(actionId);
        dataAction.setBundle(argument);
        dataAction.setEventSender(DataAction.EventSender.ON_NAVIGATE);
        eventSender.postValue(dataAction);
    }

    protected void showToast(String message) {
        dataAction.setMessage(message);
        dataAction.setEventSender(DataAction.EventSender.SHOW_TOAST);
        eventSender.postValue(dataAction);
    }

    protected void onClose(){
        dataAction.setEventSender(DataAction.EventSender.ON_CLOSE);
        eventSender.postValue(dataAction);
    }

    protected String getString(int message){
        return getApplication().getString(message);
    }
}
