package com.example.mymusicapplication.screens.setting;

public class Setting {
    private int icon;
    private int title;
    private int ItemId;

    public Setting(int icon, int title, int itemId) {
        this.icon = icon;
        this.title = title;
        ItemId = itemId;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public int getTitle() {
        return title;
    }

    public void setTitle(int title) {
        this.title = title;
    }

    public int getItemId() {
        return ItemId;
    }

    public void setItemId(int itemId) {
        ItemId = itemId;
    }
}
