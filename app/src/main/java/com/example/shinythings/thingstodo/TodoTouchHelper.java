package com.example.shinythings.thingstodo;

/**
 * Created by shiyoon on 6/25/16.
 * On drag, implements a callback
 */

public interface TodoTouchHelper {

    // Method to implement logic for processing item moves
    void onItemDrag(int fromPos, int toPos);
    void onItemDismiss(int position);

}