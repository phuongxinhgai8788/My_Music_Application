package com.example.mymusicapplication.screens.setting;

import android.app.Application;

import androidx.annotation.NonNull;

import com.example.mymusicapplication.R;
import com.example.mymusicapplication.base.BaseViewModel;
import com.example.mymusicapplication.base.interfaces.IActionAdapterRecycler;
import com.example.mymusicapplication.model.Song;
import com.example.mymusicapplication.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class SettingViewModel extends BaseViewModel implements IActionAdapterRecycler<Setting> {

    private List<Setting> listItems = new ArrayList<>();
    private List<Setting>listItemsHeader = new ArrayList<>();

    public SettingViewModel(@NonNull Application application) {
        super(application);
        initListSetting();
        initListHeader();
    }

    private void initListHeader() {
        listItemsHeader.clear();
        listItemsHeader.add(new Setting(R.drawable.ic_paint,R.string.themes,Constants.THEMES));
        listItemsHeader.add(new Setting(R.drawable.ic_cut,R.string.ringtone_cutter, Constants.RINGTONE_CUTTER));
        listItemsHeader.add(new Setting(R.drawable.ic_stop_watch,R.string.sleep_time,Constants.SLEEP_TIME));
        listItemsHeader.add(new Setting(R.drawable.ic_sound_bar,R.string.equliser,Constants.EQULISER));
        listItemsHeader.add(new Setting(R.drawable.ic_car,R.string.drive_mode,Constants.DRIVE_MODE));
        listItemsHeader.add(new Setting(R.drawable.ic_folder,R.string.hidden_folder,Constants.HIDDEN_FOLDER));
        listItemsHeader.add(new Setting(R.drawable.ic_scan,R.string.scan_media,Constants.SCAN_MEDIA));
    }

    private void initListSetting() {
        listItems.clear();
        listItems.add(new Setting(R.drawable.ic_youtube,R.string.display, Constants.DISPLAY));
        listItems.add(new Setting(R.drawable.ic_volume,R.string.audio, Constants.AUDIO));
        listItems.add(new Setting(R.drawable.ic_headphone,R.string.head_set, Constants.HEAD_SET));
        listItems.add(new Setting(R.drawable.ic_padlock,R.string.lockscreen, Constants.LOCK_SCREEN));
        listItems.add(new Setting(R.drawable.ic_ellipse,R.string.advanced, Constants.ADVANCED));
        listItems.add(new Setting(R.drawable.ic_setting_s,R.string.other, Constants.OTHER));
    }

    @Override
    public void onClickListener(Setting data) {
        showToast(getString(data.getTitle()));
    }

    @Override
    public void onLongClickListener(Setting data) {

    }

    @Override
    public void onNavigate(Setting data) {

    }

    public List<Setting> getListItems() {
        return listItems;
    }

    public List<Setting> getListItemsHeader() {
        return listItemsHeader;
    }

}
