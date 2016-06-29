package com.example.shinythings.thingstodo;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;


/**
 * Created by shiyoon on 6/25/16.
 */
public class TodoTouchCallback extends ItemTouchHelper.Callback {

    private final TodoTouchHelper adapter;

    public TodoTouchCallback(TodoTouchHelper adapter) {
        this.adapter = adapter;
    }


    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, ViewHolder vh) {
        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
        // let's disable swipes
        return makeMovementFlags(dragFlags, 0);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, ViewHolder viewHolder, ViewHolder target) {
        Log.d("TouchCallback", "onMove()");
        adapter.onItemDrag(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        return true;
    }

    @Override
    public void onSwiped(ViewHolder viewHolder, int direction) {
        adapter.onItemDismiss(viewHolder.getAdapterPosition());
    }

}
