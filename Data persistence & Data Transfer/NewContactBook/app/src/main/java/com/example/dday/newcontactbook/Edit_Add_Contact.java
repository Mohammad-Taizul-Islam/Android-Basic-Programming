package com.example.dday.newcontactbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class Edit_Add_Contact extends AppCompatActivity {


    public static final String EXTRA_NAME="com.example.dday.newcontactbook_EXTRA_NAME";
    public static final String EXTRA_PHONE="com.example.dday.newcontactbook_EXTRA_PHONE";
    public static final String EXTRA_EMAIL="com.example.dday.newcontactbook_EXTRA_EMAIL";


    public static final String TAG=Edit_Add_Contact.class.getSimpleName();
    private EditText editTextName,editTextEmail,editTextPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit__add__contact);


        editTextName=findViewById(R.id.editTextName);
        editTextEmail=findViewById(R.id.editTextEmail);
        editTextPhone=findViewById(R.id.editTextPhone);


        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        setTitle("Add Contact");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.add_menu,menu);
        return  true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.save_contact :
                saveContacts();
                return true;
                default:
                    return super.onOptionsItemSelected(item);
        }

    }

    private void saveContacts() {
        String name=editTextName.getText().toString();
        String phone=editTextPhone.getText().toString();
        String email=editTextEmail.getText().toString();


        if(name.trim().isEmpty() || phone.trim().isEmpty() || email.trim().isEmpty())
        {
            Toast.makeText(this, "Please insert a Name and Phone Number & email", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data=new Intent();
        data.putExtra(EXTRA_NAME,name);
        data.putExtra(EXTRA_PHONE,phone);
        data.putExtra(EXTRA_EMAIL,email);

        setResult(RESULT_OK,data);
        finish();


    }
}
