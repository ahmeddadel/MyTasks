package com.dolla.mytasks.pojo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DatabaseAdapter {

    DatabaseHelper helper;

    // Constructor of the outer class
    public DatabaseAdapter(Context context) {
        helper = new DatabaseHelper(context);
    }

    // Insert data method
    public void insertEntry(ArrayList<TaskModel> entry) {

        // access by Write
        SQLiteDatabase database = helper.getWritableDatabase();
        // Make sure th table is cleared
        database.execSQL("delete from " + DatabaseHelper.TABLE_NAME);

        for (int i = 0; i < entry.size(); i++) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(DatabaseHelper.COLUMN_TITLE, entry.get(i).getTitle());
            contentValues.put(DatabaseHelper.COLUMN_BODY, entry.get(i).getBody());
            database.insert(DatabaseHelper.TABLE_NAME, null, contentValues);
        }
        database.close();
    }

    // retrieve data method
    public ArrayList<TaskModel> retrieveEntry() {

        ArrayList<TaskModel> list = new ArrayList<>();

        // access by Read
        SQLiteDatabase database = helper.getReadableDatabase();

        // columns name of the database in array to pass it to the cursor
        String[] columns = {DatabaseHelper.COLUMN_TITLE, DatabaseHelper.COLUMN_BODY};
        Cursor cursor = database.query(DatabaseHelper.TABLE_NAME, columns, null, null, null, null, null);

        // loop till the end of the table (row by row)
        TaskModel entry;

        int indexTitle = cursor.getColumnIndex(DatabaseHelper.COLUMN_TITLE);
        int indexBody = cursor.getColumnIndex(DatabaseHelper.COLUMN_BODY);

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            entry = new TaskModel(cursor.getString(indexTitle), cursor.getString(indexBody));
            list.add(entry);
        }
        cursor.close();
        database.close();
        return list;
    }


    //Start of Inner class
    static class DatabaseHelper extends SQLiteOpenHelper {

        //SQLite Attributes and Query
        public static final int DATABASE_VERSION = 1;
        public static final String DATABASE_NAME = "myDatabase";
        public static final String TABLE_NAME = "myTable";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_BODY = "body";
        public static final String CREAT_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_TITLE + " TEXT, "
                + COLUMN_BODY + " TEXT );";


        public DatabaseHelper(@Nullable Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL(CREAT_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

            /*  Not needed right now

            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(sqLiteDatabase);

             */
        }
    }
    //End of Inner class

}