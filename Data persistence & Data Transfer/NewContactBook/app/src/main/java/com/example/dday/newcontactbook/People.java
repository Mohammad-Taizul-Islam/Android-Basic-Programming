package com.example.dday.newcontactbook;

public class People {

    public static final String CONTACTS_TABLE="contacts";
    public static final String COLUMN_ID="id";
    public static final String COLUMN_NAME="name";
    public static final String COLUMN_PHONE="phone";
    public static final String COLUMN_EMAIL="email";


    public static final String CREATE_TABLE=" CREATE TABLE " + CONTACTS_TABLE + " ( "
                                            + COLUMN_ID + " INTEGER  PRIMARY KEY AUTOINCREMENT , "
                                            + COLUMN_NAME + " TEXT , "
                                            + COLUMN_PHONE + " TEXT , "
                                            + COLUMN_EMAIL + " TEXT " + " ) ";



    private int id;
    private String name;
    private String phone;
    private String email;

    public People()
    {

    }

    public People(int id,String name, String phone, String email) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.id=id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }
}
