package com.example.shinythings.thingstodo;

import android.app.Dialog;
import android.database.DatabaseErrorHandler;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.OvershootInterpolator;

import com.android.colorpicker.ColorPickerDialog;
import com.android.colorpicker.ColorPickerSwatch;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;

import jp.wasabeef.recyclerview.animators.LandingAnimator;
import jp.wasabeef.recyclerview.animators.ScaleInAnimator;

public class TodoListActivity extends AppCompatActivity {


    // Default Color list
    public static final int[] ITEM_COLORS = {
            Color.parseColor("#73CAC4"),
            Color.parseColor("#E3E546"),
            Color.parseColor("#F2788F"),
            Color.parseColor("#F69DBB"),
            Color.parseColor("#FBAD4B")

    };

    private RecyclerView todoList;
    private LinearLayoutManager todoManager;
    private TodoItemAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Initialize the todo list recycle view
        // check if there are any saved items in local data store
        ArrayList<TodoItem> savedItems = getSavedItems();
        todoList = (RecyclerView) findViewById(R.id.todo_list_recycle);
        todoManager = new LinearLayoutManager(this);
        todoList.setLayoutManager(todoManager);
        adapter = new TodoItemAdapter(savedItems, getApplicationContext());
        todoList.setAdapter(adapter);
        // set animator
        todoList.setItemAnimator(new LandingAnimator());

        // Set a drag listener
        ItemTouchHelper.Callback dragCallback = new TodoTouchCallback(adapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(dragCallback);
        touchHelper.attachToRecyclerView(todoList);



        // On fab click, initialize a new item
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                addTodoItem();

            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_todo_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    // Check local storage for any saved item data
    private ArrayList<TodoItem> getSavedItems() {

        return TodoDAO.getAllTodos(getApplicationContext());
    }



    // Show dialog to add item
    private void addTodoItem() {


        // show the color select dialog
        ColorPickerDialog colorPickerDialog = new ColorPickerDialog();
        colorPickerDialog.initialize(
                R.string.color_picker_default_title,
                ITEM_COLORS, 0,
                5,
                ITEM_COLORS.length
        );


        colorPickerDialog.setOnColorSelectedListener(new ColorPickerSwatch.OnColorSelectedListener() {
            @Override
            public void onColorSelected(int color) {
                Log.d("colorpicker", Integer.toHexString(color).substring(2));


                String selectedColor = "#" + Integer.toHexString(color).substring(2);
                long id = TodoDAO.createTodo("", selectedColor, getApplicationContext());
                TodoItem newItem = new TodoItem("", selectedColor, id);
                adapter.addItem(newItem);
                todoList.scrollToPosition(0);

            }
        });


        colorPickerDialog.show(getFragmentManager(), "colorpicker");


    }


}
