package com.example.mymusicapplication.base;

import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

public class BaseViewHolder<VB extends ViewDataBinding> extends RecyclerView.ViewHolder{
    public VB binding;
    public BaseViewHolder(VB binding) {
        super(binding.getRoot());
        this.binding = binding;
    }
}