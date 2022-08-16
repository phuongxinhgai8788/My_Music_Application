package com.example.mymusicapplication.utils;

import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import com.example.mymusicapplication.base.interfaces.IMenuItemAction;

public class Extension {
    @BindingAdapter("app:setUrlImage")
    public static void setUrlImage(ImageView imageView, int src) {
        imageView.setBackgroundResource(src);
    }

    public static void showPopup(View view, int menu, IMenuItemAction iMenuItemAction) {
        view.setOnClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(v.getContext(), view);
            popupMenu.inflate(menu);
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    if (v instanceof TextView) {
                        ((TextView) v).setText(menuItem.getTitle());
                    } else if (v instanceof EditText) {
                        ((EditText) v).setText(menuItem.getTitle());
                    }
                    iMenuItemAction.onClickMenuItem(menuItem.getItemId(), menuItem.getTitle().toString());
                    return true;
                }
            });
            popupMenu.show();
        });
    }
}


