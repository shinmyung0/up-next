package com.example.shinythings.thingstodo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.shinythings.thingstodo.TodoDataHelper.TodoColumns;
import java.util.ArrayList;

/**
 * Created by shiyoon on 6/28/16.
 */
public class TodoDAO {

    private static TodoDataHelper dbHelper;

    private TodoDAO() {
    }

    public static ArrayList<TodoItem> getAllTodos(Context context) {
        if (dbHelper == null) {
            dbHelper = new TodoDataHelper(context);
        }

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor data = db.rawQuery(
                "select * from " + TodoColumns.TABLE_NAME +
                " order by " + TodoColumns.COLUMN_ORDER, null);

        ArrayList<TodoItem> todos = new ArrayList<>();

        if (data.moveToFirst()) {
            long id;
            int page, order;
            String status, body, bgColor;
            TodoItem curr;


            // TODO: implement pagination
            while(!data.isAfterLast()) {
                // id, page, order, status, body, bgcolor
                id = data.getLong(0);
                //page = data.getInt(1);
                order = data.getInt(2);
                status = data.getString(3);
                body = data.getString(4);
                bgColor = data.getString(5);
                curr = new TodoItem(body, bgColor, status, order, id);
                todos.add(curr);
                data.moveToNext();
            }

        }
        return todos;
    }


    public static long createTodo(String body, String color, Context context) {
        if (dbHelper == null) {
            dbHelper = new TodoDataHelper(context);
        }

        Log.d("TodoDAO", "Creating todo");

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues todoData = new ContentValues();

        todoData.put(TodoColumns.COLUMN_PAGE, 0);
        todoData.put(TodoColumns.COLUMN_STATUS, TodoItem.STATUS_TODO);
        todoData.put(TodoColumns.COLUMN_ORDER, 0);
        todoData.put(TodoColumns.COLUMN_BODY, body);
        todoData.put(TodoColumns.COLUMN_BG_COLOR, color);

        return db.insert(TodoColumns.TABLE_NAME, null, todoData);
    }


    public static void updateTodo(TodoItem todo, Context context) {

        if (dbHelper == null) {
            dbHelper = new TodoDataHelper(context);
        }

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues todoData = new ContentValues();

        todoData.put(TodoColumns.COLUMN_PAGE, 0);
        todoData.put(TodoColumns.COLUMN_STATUS, todo.getStatus());
        todoData.put(TodoColumns.COLUMN_ORDER, todo.getOrder());
        todoData.put(TodoColumns.COLUMN_BODY, todo.getTodo());
        todoData.put(TodoColumns.COLUMN_BG_COLOR, todo.getBgColor());


        db.update(TodoColumns.TABLE_NAME, todoData, "_id=" + Long.toString(todo.getId()), null);


    }




    public static void deleteTodo(TodoItem todo, Context context) {
        if (dbHelper == null) {
            dbHelper = new TodoDataHelper(context);
        }

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        db.delete(TodoColumns.TABLE_NAME, "_id=" + Long.toString(todo.getId()), null);

    }


    public static void updateAllTodos(ArrayList<TodoItem> allTodos, Context context) {
        if (dbHelper == null) {
            dbHelper = new TodoDataHelper(context);
        }

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues todoData;
        TodoItem curr;
        for (int i=0; i < allTodos.size(); i++) {
            todoData = new ContentValues();
            curr = allTodos.get(i);

            // TODO: implement paging
            todoData.put(TodoColumns.COLUMN_PAGE, 0);
            todoData.put(TodoColumns.COLUMN_ORDER, curr.getOrder());
            todoData.put(TodoColumns.COLUMN_STATUS, curr.getStatus());
            todoData.put(TodoColumns.COLUMN_BODY, curr.getTodo());
            todoData.put(TodoColumns.COLUMN_BG_COLOR, curr.getBgColor());

            db.update(TodoColumns.TABLE_NAME, todoData, "_id=" + Long.toString(curr.getId()), null);


        }

    }


    public static void saveAllTodos(ArrayList<TodoItem> todos, Context context) {
        if (dbHelper == null) {
            dbHelper = new TodoDataHelper(context);
        }

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues todoData;
        TodoItem curr;
        for (int i=0; i < todos.size(); i++) {
            todoData = new ContentValues();
            curr = todos.get(i);

            // TODO: implement paging
            todoData.put(TodoColumns.COLUMN_PAGE, 0);
            todoData.put(TodoColumns.COLUMN_ORDER, curr.getOrder());
            todoData.put(TodoColumns.COLUMN_STATUS, curr.getStatus());
            todoData.put(TodoColumns.COLUMN_BODY, curr.getTodo());
            todoData.put(TodoColumns.COLUMN_BG_COLOR, curr.getBgColor());

            db.insert(TodoColumns.TABLE_NAME, null, todoData);


        }

    }

    public static void deleteAllTodos(Context context) {
        if (dbHelper == null) {
            dbHelper = new TodoDataHelper(context);
        }

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("delete from " + TodoColumns.TABLE_NAME);
    }


}
