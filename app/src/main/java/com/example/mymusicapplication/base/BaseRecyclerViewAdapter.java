package com.example.mymusicapplication.base;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymusicapplication.base.interfaces.IActionAdapterRecycler;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseRecyclerViewAdapter<T , VB extends ViewDataBinding> extends RecyclerView.Adapter<BaseViewHolder<VB>>{
    protected List<T> listItem = new ArrayList();
    protected IActionAdapterRecycler<T> action;

    public void updateItems(List<T> list,IActionAdapterRecycler<T> action) {
        BaseDiffCallBack taskDiffCallBack = new BaseDiffCallBack<T>(list,listItem);
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(taskDiffCallBack);
        diffResult.dispatchUpdatesTo(this);
        this.listItem = list;
        this.action = action;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return listItem.size();
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BaseViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), getLayout(), parent, false));
    }

    protected abstract int getLayout();

}
