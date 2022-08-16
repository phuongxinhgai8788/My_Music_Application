package com.example.mymusicapplication.base;

import androidx.recyclerview.widget.DiffUtil;

import java.util.List;

public class BaseDiffCallBack<T> extends DiffUtil.Callback{
    private List<T>oldList;
    private List<T>newList;

    public BaseDiffCallBack(List<T> oldList, List<T>newList){
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).hashCode() == newList.get(newItemPosition).hashCode();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).equals(newList.get(newItemPosition));
    }
}
