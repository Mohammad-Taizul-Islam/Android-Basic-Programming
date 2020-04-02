package com.example.myaddressbook;

public class Person {
    public static final String TABLE_NAME="person";
    public static final String COL_ID="id";
    public static final String COL_NAME="person_name";
    public static final String COL_PHONE="phone_number";
    public static final String COL_EMAIL="email";


    private int id;
    private String person_name;
    private String phone_number;
    private String email;


    public static final String table_query= " CREATE TABLE " + TABLE_NAME + " ( " +
            COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ," +
            COL_NAME + " TEXT NOT NULL ," +
            COL_PHONE + " TEXT ," +
            COL_EMAIL + " TEXT " + " ) ; " ;

    public Person(){}

    public Person(int id, String person_name, String phone_number, String email) {
        this.id = id;
        this.person_name = person_name;
        this.phone_number = phone_number;
        this.email = email;
    }

    public Person(String person_name, String phone_number, String email) {
        this.person_name = person_name;
        this.phone_number = phone_number;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPerson_name() {
        return person_name;
    }

    public void setPerson_name(String person_name) {
        this.person_name = person_name;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
