package com.example.myaddressbook;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME="address_book.db";
    public static final int DAABAE_VERSION=1;

    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DAABAE_VERSION);
    }

    @Override

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Person.table_query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" DROP TABLE IF EXISTS " + Person.TABLE_NAME);
        onCreate(db);
    }






    //inserting person


    public long insertPerson(String name,String phone_number,String email) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(Person.COL_NAME, name);
        values.put(Person.COL_PHONE, phone_number);
        values.put(Person.COL_EMAIL, email);

        // insert row
        long id = db.insert(Person.TABLE_NAME, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }




    // Get Person by ID


    public Person getPerson(long id) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Person.TABLE_NAME,
                new String[]{Person.COL_ID,Person.COL_NAME,Person.COL_PHONE,Person.COL_EMAIL},
                Person.COL_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();



        // prepare person object
       Person person = new Person(
                cursor.getInt(cursor.getColumnIndex(Person.COL_ID)),
                cursor.getString(cursor.getColumnIndex(Person.COL_NAME)),
               cursor.getString(cursor.getColumnIndex(Person.COL_PHONE)),
               cursor.getString(cursor.getColumnIndex(Person.COL_EMAIL))
               );

        // close the db connection
        cursor.close();

        return person;
    }





    //Get All people


    public List<Person> getAllPersons() {
        List<Person> persons = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + Person.TABLE_NAME + " ORDER BY " +
                Person.COL_NAME + " ASC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Person person = new Person();
                person.setId(cursor.getInt(cursor.getColumnIndex(Person.COL_ID)));
                person.setPerson_name(cursor.getString(cursor.getColumnIndex(Person.COL_NAME)));
                person.setPhone_number(cursor.getString(cursor.getColumnIndex(Person.COL_PHONE)));
                person.setEmail(cursor.getString(cursor.getColumnIndex(Person.COL_EMAIL)));
                persons.add(person);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return notes list
        return persons;
    }





    // get numbers of people

    public int getPersonsCount() {
        String countQuery = "SELECT  * FROM " + Person.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();


        // return count
        return count;
    }




    public int updatePerson(Person person) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Person.COL_NAME, person.getPerson_name());
        values.put(Person.COL_PHONE, person.getPhone_number());
        values.put(Person.COL_EMAIL, person.getEmail());

        // updating row
        return db.update(Person.TABLE_NAME, values, Person.COL_ID + " = ?",
                new String[]{String.valueOf(person.getId())});
    }

    public void deletePerson(Person person) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Person.TABLE_NAME, Person.COL_ID + " = ?",
                new String[]{String.valueOf(person.getId())});
        db.close();
    }

}
