package com.example.mymusicapplication.model;

import android.os.Bundle;

public class DataAction {
    private Bundle bundle;
    private int actionId;
    private EventSender eventSender;
    private String message;

    public DataAction(Bundle bundle, int action, EventSender eventSender, String message) {
        this.bundle = bundle;
        this.actionId = action;
        this.eventSender = eventSender;
        this.message = message;
    }

    public DataAction(){
    }

    public Bundle getBundle() {
        return bundle;
    }

    public void setBundle(Bundle bundle) {
        this.bundle = bundle;
    }

    public int getActionId() {
        return actionId;
    }

    public void setActionId(int actionId) {
        this.actionId = actionId;
    }

    public EventSender getEventSender() {
        return eventSender;
    }

    public void setEventSender(EventSender eventSender) {
        this.eventSender = eventSender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public enum EventSender {
        ON_NAVIGATE,
        SHOW_TOAST,
        ON_CLOSE
    }
}


