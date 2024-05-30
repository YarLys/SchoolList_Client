package com.example.schoollistclient;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.example.schoollistclient.adapters.MarkAdapter;

public interface RecyclerViewClickListener {
    public void recyclerViewClickListened(View view, int position);
}
