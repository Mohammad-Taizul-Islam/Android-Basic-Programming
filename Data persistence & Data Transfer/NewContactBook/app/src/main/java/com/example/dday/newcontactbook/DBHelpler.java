package com.example.dday.newcontactbook;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DBHelpler extends SQLiteOpenHelper {

    public static final String TAG=DBHelpler.class.getSimpleName();

    public static final String DATABASE_NAME="contacts.DB";
    public static final int DATABASE_VERSION=1;
    private  People people;


    public DBHelpler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(People.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + People.CONTACTS_TABLE);

        onCreate(db);
    }

    public long insertContacts(String name,String phone,String email)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();

        values.put(People.COLUMN_NAME,name);
        values.put(People.COLUMN_PHONE,phone);
        values.put(People.COLUMN_EMAIL,email);

        long id=db.insert(People.CONTACTS_TABLE,null,values);

        Log.d(TAG, "insertContacts: on");

        db.close();

        return id;
    }

    public People getPeople(long id)
    {
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.query(People.CONTACTS_TABLE,
                new String[]{People.COLUMN_NAME,People.COLUMN_PHONE,People.COLUMN_EMAIL},
                People.COLUMN_ID + "=?",new String[]{String.valueOf(id)},null,null,null,null);

        if(cursor != null) {
            cursor.moveToFirst();

            people = new People(
                    cursor.getInt(cursor.getColumnIndex(People.COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndex(People.COLUMN_NAME)),
                    cursor.getString(cursor.getColumnIndex(People.COLUMN_PHONE)),
                    cursor.getString(cursor.getColumnIndex(People.COLUMN_EMAIL))
            );


            cursor.close();
        }

            return people;

    }

    public List<People> getAllPeople()
    {
        List<People> peoples=new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + People.CONTACTS_TABLE + " ORDER BY " +
                People.COLUMN_ID + " DESC";

        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery(selectQuery,null);

        if(cursor.moveToFirst())
        {
            do {

                People people=new People();
                people.setId(cursor.getInt(cursor.getColumnIndex(People.COLUMN_ID)));
                people.setName(cursor.getString(cursor.getColumnIndex(People.COLUMN_NAME)));
                people.setEmail(cursor.getString(cursor.getColumnIndex(People.COLUMN_EMAIL)));
                people.setPhone(cursor.getString(cursor.getColumnIndex(People.COLUMN_PHONE)));

                peoples.add(people);
            }while (cursor.moveToNext());
        }

        cursor.close();


        return peoples;
    }

    public int getPeopleCounts()
    {
        String countQuery="SELECT *FROM " +People.CONTACTS_TABLE;

        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery(countQuery,null);


        int count=cursor.getCount();

        cursor.close();

        return count;
    }


    public int updatePeoples(People people)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(People.COLUMN_NAME,people.getName());
        values.put(People.COLUMN_PHONE,people.getPhone());
        values.put(People.COLUMN_EMAIL,people.getEmail());


        return  db.update(People.CONTACTS_TABLE,values,People.COLUMN_ID + " = ? " , new String[]{String.valueOf(people.getId())});

    }


    public void deletePeoples(People people)
    {
       SQLiteDatabase db=this.getWritableDatabase();
       db.delete(People.CONTACTS_TABLE , People.COLUMN_ID + " = ? ", new String[]{String.valueOf(people.getId())});

       db.close();
    }
}
