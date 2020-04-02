package com.example.myaddressbook;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class EditAddPeopleActivity extends AppCompatActivity {

    public static final String EXTRA_PERSON_ID="id";
    public static final String  EXTRA_PERSON_NAME="person_name";
    public static final String EXTRA_PHONE_NUMBER="phone_number";
    public static final String EXTRAEMAIL="email";

    private EditText eName,ePhoneNo,eEmail;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_edit_add_people);


        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        eName=findViewById(R.id.etName);
        ePhoneNo=findViewById(R.id.etPhoneNumber);
        eEmail=findViewById(R.id.etEmail);

        Intent intent=getIntent();
        if(intent.hasExtra(EXTRA_PERSON_ID))
        {
            setTitle("Edit Persons");

            eName.setText(intent.getStringExtra(EXTRA_PERSON_NAME));
            ePhoneNo.setText(intent.getStringExtra(EXTRA_PHONE_NUMBER));
            eEmail.setText(intent.getStringExtra(EXTRAEMAIL));


        }else{
            setTitle("Add Person");
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.edit_save_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.save_button :
                savePeople();
                return true;
            default :
                return super.onOptionsItemSelected(item);
        }

    }

    private void savePeople() {

        String name=eName.getText().toString();
        String phone_number=ePhoneNo.getText().toString();
        String email=eEmail.getText().toString();


        if(name.trim().isEmpty() || phone_number.trim().isEmpty())
        {
            Toast.makeText(this, "Please enter correct name & phone number ", Toast.LENGTH_SHORT).show();
        }

        Intent data=new Intent();
        data.putExtra(EXTRA_PERSON_NAME,name);
        data.putExtra(EXTRA_PHONE_NUMBER,phone_number);
        data.putExtra(EXTRAEMAIL,email);


        int id=getIntent().getIntExtra(EXTRA_PERSON_ID,-1);

        if(id != -1)
        {
            data.putExtra(EXTRA_PERSON_ID,id);
        }

        setResult(RESULT_OK,data);
        finish();
    }
}
