package com.example.shinythings.thingstodo;

import android.content.ClipData;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Dictionary;


/**
 * Created by shiyoon on 6/19/16.
 */
public class TodoItemAdapter extends RecyclerView.Adapter<TodoItemAdapter.ItemViewHolder>
                             implements TodoTouchHelper {




    // Array list of items
    private ArrayList<TodoItem> items;

    private static Context context;


    // Create adapter with any saved items
    public TodoItemAdapter(ArrayList<TodoItem> savedItems, Context ctx) {
        Log.d("Creating Adapter::", Integer.toString(savedItems.size()));


        items = savedItems;
        context = ctx;
    }


    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.todo_item, parent, false);

        // create the viewholder object, do any external init here
        ItemViewHolder vh = new ItemViewHolder(v);
        return vh;

    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder viewHolder, int pos) {



        final TodoItem item = items.get(pos);

        // Initialize viewholder
        viewHolder.setItemData(item);
        viewHolder.delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeItem(item);
            }
        });
        // edit button
        viewHolder.editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (viewHolder.contentText.isEnabled()) {
                    saveEdit(viewHolder);
                } else {
                    enableEdit(viewHolder);
                }
            }
        });


        // set focus listeners for change
        viewHolder.contentText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {

                if (!hasFocus) {
                    viewHolder.editBtn.setImageResource(R.drawable.ic_edit_white_48dp);
                    viewHolder.contentText.setEnabled(false);
                    updateAllTodos();
                }
            }
        });


        // Choose post it note color
        viewHolder.cardView.setCardBackgroundColor(Color.parseColor(item.getBgColor()));

        if (viewHolder.contentText.getText().toString().equals("") && pos == 0) {
            enableEdit(viewHolder);
        }



    }


    // Called when an item from the adapter is dragged
    @Override
    public void onItemDrag(int from, int to) {

        TodoItem fromItem = items.get(from);
        TodoItem toItem = items.get(to);
        fromItem.setOrder(to);
        toItem.setOrder(from);

        //Collections.swap(items, from, to);

        TodoDAO.updateTodo(fromItem, context);
        TodoDAO.updateTodo(toItem, context);

        TodoItem removed = items.remove(from);
        items.add(to, removed);

        updateAllTodos();
        notifyItemMoved(from, to);
    }

    @Override
    public void onItemDismiss(int pos) {
        items.remove(pos);
        updateAllTodos();
        notifyItemRemoved(pos);
    }


    public void addItem(TodoItem newItem) {

        items.add(0, newItem);
        notifyItemInserted(0);
    }

    public void removeItem(TodoItem newItem) {

        int pos = items.indexOf(newItem);

        if (pos >= 0) {
            items.remove(pos);

            TodoDAO.deleteTodo(newItem, context);
            notifyItemRemoved(pos);
        }


    }

    private void enableEdit(ItemViewHolder viewHolder) {
        viewHolder.editBtn.setImageResource(R.drawable.ic_save_white_48dp);
        viewHolder.contentText.setEnabled(true);
        viewHolder.contentText.requestFocus();
    }

    private void saveEdit(ItemViewHolder viewHolder) {
        viewHolder.editBtn.setImageResource(R.drawable.ic_edit_white_48dp);
        viewHolder.contentText.setEnabled(false);

        String todoTxt = viewHolder.contentText.getText().toString();

        // save data to data base
        int order = viewHolder.getAdapterPosition();
        TodoItem todo = items.get(order);
        todo.setTodo(todoTxt);
        todo.setOrder(order);
        TodoDAO.updateTodo(todo, context);

    }


    private void updateAllTodos() {
        TodoDAO.updateAllTodos(items, context);
    }




    // ViewHolder for items
    static public class ItemViewHolder extends RecyclerView.ViewHolder {

        // View fields in the viewholder
        EditText contentText;
        ImageButton delBtn;
        ImageButton editBtn;
        CardView cardView;
        LinearLayout mainFrame;


        public ItemViewHolder(View v) {
            super(v);
            contentText = (EditText) v.findViewById(R.id.content_text);
            delBtn = (ImageButton) v.findViewById(R.id.delBtn);
            editBtn = (ImageButton) v.findViewById(R.id.editBtn);
            cardView = (CardView) v.findViewById(R.id.card_view);
            mainFrame = (LinearLayout) v.findViewById(R.id.main_layout);


        }

        // Given an ITEM bind the data to current ViewHolder
        // TODO: add more logic to implement saved data
        public void setItemData(TodoItem item) {
            String todo = item.getTodo();

            contentText.setText(todo);
        }


    }








}
