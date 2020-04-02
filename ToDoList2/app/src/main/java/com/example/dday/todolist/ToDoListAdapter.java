package com.example.dday.todolist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import java.util.ArrayList;
import java.util.List;

public class ToDoListAdapter {

    private static final String TAG=ToDoListAdapter.class.getSimpleName();
    private static String DB_NAME="todoList";
    private static String TABLE_TODO="todo";
    private static int DB_VERTION=1;


    private static String  COL_ID="id";
    private static String COL_TASK="task";


    private static String TABLE_CREATION="CREATE TABLE " + TABLE_TODO + " ( " + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , "  +  COL_TASK + " TEXT NOT NULL )";

    private static ToDoListAdapter toDoListAdapterInstance;
     SQLiteDatabase sqLiteDatabase;
     Context context;

    private ToDoListAdapter(Context context)
    {
        this.context=context;
        sqLiteDatabase=new ToDoListHelper(context,DB_NAME,null,DB_VERTION).getWritableDatabase();
    }

    public static ToDoListAdapter getToDoAdapterInstance(Context context)
    {
        if(toDoListAdapterInstance==null)
        {
            toDoListAdapterInstance=new ToDoListAdapter(context);
        }

        return toDoListAdapterInstance;
    }



//insert delete modify and  showing/query methods


    public boolean insertData(String  toDoItems)
    {
        ContentValues contentValues=new ContentValues();
        contentValues.put(COL_TASK,toDoItems);
        return sqLiteDatabase.insert(TABLE_TODO,null,contentValues)>0;
    }

    public boolean deleteData(int taskId)
    {
        return sqLiteDatabase.delete(TABLE_TODO,COL_ID+ "=" +taskId,null)>0;
    }

    public boolean modifydata(int taskId,String toDoItems)
    {
        ContentValues values=new ContentValues();
        values.put(COL_TASK,toDoItems);
        return sqLiteDatabase.update(TABLE_TODO,values,taskId + "=" +COL_ID,null)>0;
    }

    public List<ToDo> getAllToDos()
    {
        List<ToDo> toDosList=new ArrayList<ToDo>();
        Cursor cursor=sqLiteDatabase.query(TABLE_TODO,new String[]{COL_ID,COL_TASK},null,null,null,null,null,null);

        if(cursor!=null &cursor.getCount()>0)
        {
            while(cursor.moveToNext())
            {
                ToDo toDo=new ToDo(cursor.getLong(0),cursor.getString(1));
                toDosList.add(toDo);
            }
        }
        cursor.close();
        return toDosList;
    }












    //helper class

    static class ToDoListHelper extends SQLiteOpenHelper{

        public ToDoListHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onConfigure(SQLiteDatabase db) {
            super.onConfigure(db);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                db.setForeignKeyConstraintsEnabled(true);
            }
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(TABLE_CREATION);

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}
