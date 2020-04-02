package com.example.dday.dbdemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBAdapter {

    //vars

    private static final String TAG=DBAdapter.class.getSimpleName();


    //Database fields

    private static final String dataBaseName="myDB";   //conventional name
    private static final String tableName="mainTable";
    private static final int dataBaseVersion=2;


    //Table fields

    private static final String keyRowId="_id";
    private static final String keyStudentName="name";
    private static final String getKeyStudentNumber="number";
    private static final String keysColumn[]={keyRowId,keyStudentName,getKeyStudentNumber};


    //Table fields numbers

    private static final int col_RowId=1;
    private static final int col_StudentName=2;
    private static final int col_StudentNumber=3;


    //Table  creation String

    private static final String table_Creation="create table " + tableName + "(" + keyRowId + " integer primary key autoincrement," +
                                                keyStudentName + "text not null, "+
                                                getKeyStudentNumber +"text not null " +  ");";





    //more vars

    private Context context;
    private static SQLiteDatabase db;
    private  DBAdapter myDB;
    private MyDBHelper myDBHelper;
    private ContentValues values;
    private Cursor cursor;



    //Constructor of BDAdapter class


    public  DBAdapter(Context context)
    {
        this.context=context;
        myDBHelper=new MyDBHelper(context);
    }


    //open database


    public DBAdapter openDB()
    {
        db=myDBHelper.getWritableDatabase();
       return this;
    }



    //close database


    public void closeDB() {
        db.close();
    }












    ///database operations........///






    //insertion of a row in table

    public long insertionRow(String studentName,int studentNumber)
    {
        values=new ContentValues();
        values.put(keyStudentName,studentName);
        values.put(getKeyStudentNumber,studentNumber);
        return db.insert(tableName,null,values);
    }





    //delete of a row

    public boolean deleteRow(long rowId)
    {
        String where=keyRowId+"="+rowId;
        return db.delete(tableName,where,null)>0;
    }






    //deleting all rows

    public void deleteAllRows()
    {
        cursor=getAllRows();
        long rowId=cursor.getColumnIndexOrThrow(keyRowId);
        if(cursor.moveToFirst())
        {
            do{
                deleteRow(cursor.getLong((int)rowId));
            }while (cursor.moveToNext());
        }
        cursor.close();
    }







    //getting  all data in database



    public Cursor getAllRows()
    {
        String where=null;
        cursor=db.query(tableName,keysColumn,null,null,null,null,null,null);
        if(cursor!=null)
        {
            cursor.moveToFirst();
        }
        return cursor;
    }





    //getting specific row


    public Cursor getRow(int rowId)
    {
        String where=keyRowId+"="+rowId;
        cursor=db.query(tableName,keysColumn,null,null,null,null,null,null);
        if(cursor!=null)
        {
            cursor.moveToFirst();
        }
        return cursor;

    }







    //updates rows


    public boolean updateRows(int rowId,String studentName,String studentNumber)
    {
        String where= keyRowId + "=" +rowId;
        values=new ContentValues();
        values.put(keyStudentName,studentName);
        values.put(getKeyStudentNumber,studentNumber);

        return db.update(tableName,values,where,null)>0;
    }










    //database open helper class



    private static class MyDBHelper extends SQLiteOpenHelper{


        public MyDBHelper(Context context) {
            super(context, dataBaseName, null, dataBaseVersion);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL(table_Creation);
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            sqLiteDatabase.execSQL("drop table if exists " +tableName);
            onCreate(sqLiteDatabase);
        }
    }


}
