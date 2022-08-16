package com.example.mymusicapplication.base.interfaces;

public interface IActionAdapterRecycler <T>{
    void onClickListener(T data);

    void onLongClickListener(T data);

    void onNavigate(T data);
}
