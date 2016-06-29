package com.example.shinythings.thingstodo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by shiyoon on 6/27/16.
 */
public class TodoDataHelper extends SQLiteOpenHelper {


    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Todo.db";

    // constants


    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TodoColumns.TABLE_NAME + " (" +
            TodoColumns._ID + " INTEGER PRIMARY KEY, " +
            TodoColumns.COLUMN_PAGE + " INTEGER, " +
            TodoColumns.COLUMN_ORDER + " INTEGER, " +
            TodoColumns.COLUMN_STATUS + " TEXT, "  +
            TodoColumns.COLUMN_BODY + " TEXT, " +
            TodoColumns.COLUMN_BG_COLOR + " TEXT"  + " )";


    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TodoColumns.TABLE_NAME;


    public TodoDataHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }



    public static abstract class TodoColumns implements BaseColumns {

        public static final String TABLE_NAME = "todos";

        public static final String COLUMN_PAGE = "page";
        public static final String COLUMN_ORDER = "todoOrder";
        public static final String COLUMN_STATUS = "status";

        public static final String COLUMN_BODY = "todoBody";
        public static final String COLUMN_BG_COLOR = "bgColor";

    }


}
