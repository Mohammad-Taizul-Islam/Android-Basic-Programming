package com.example.dday.notesroom;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;



public class AddEditNoteActivity extends AppCompatActivity {


    public static final String  ID_EXTRA="com.example.dday.notesroom_ID_EXTRA";
    public static final String  TITLE_EXTRA="com.example.dday.notesroom_TITLE_EXTRA";
    public static final String DESCRIPTION_EXTRA="com.example.dday.notesroom_DESCRIPTION_EXTRA";
    public static final String PRIORITY_EXTRA="com.example.dday.notesroom_PRIORITY_EXTRA";

    private EditText editTextTitle;
    private EditText editTextDescription;
    private NumberPicker numberPickerPriority;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);



        editTextTitle = findViewById(R.id.edit_text_title);
        editTextDescription = findViewById(R.id.edit_text_description);
        numberPickerPriority = findViewById(R.id.number_picker_priority);

        numberPickerPriority.setMinValue(1);
        numberPickerPriority.setMaxValue(10);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);


        Intent intent=getIntent();

        if(intent.hasExtra(ID_EXTRA))
        {
            setTitle("Edit note");
            editTextTitle.setText(intent.getStringExtra(TITLE_EXTRA));
            editTextDescription.setText(intent.getStringExtra(DESCRIPTION_EXTRA));
            numberPickerPriority.setValue(intent.getIntExtra(PRIORITY_EXTRA,1));
        }else {
            setTitle("Add Note");
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.add_note_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.save_note :
                saveNote();
                return true;

                default:
                 return super.onOptionsItemSelected(item);
        }

    }

    private void saveNote() {
        String title=editTextTitle.getText().toString();
        String description=editTextDescription.getText().toString();
        int priority=numberPickerPriority.getValue();

        if(title.trim().isEmpty() || description.trim().isEmpty())
        {
            Toast.makeText(this,"Please insert a title and description",Toast.LENGTH_SHORT).show();

            return;
        }

        Intent data=new Intent();
        data.putExtra(TITLE_EXTRA,title);
        data.putExtra(DESCRIPTION_EXTRA,description);
        data.putExtra(PRIORITY_EXTRA,priority);

        int id= getIntent().getIntExtra(ID_EXTRA,-1);
        if(id != -1)
        {
            data.putExtra(ID_EXTRA,id);
        }

        setResult(RESULT_OK,data);
        finish();
    }
}
