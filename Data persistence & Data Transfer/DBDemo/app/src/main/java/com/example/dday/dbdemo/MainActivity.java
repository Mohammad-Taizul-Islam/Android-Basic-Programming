package com.example.dday.dbdemo;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //vars

    private static final String TAG=MainActivity.class.getSimpleName();
    private Button addButton,clearButton,showButton;
    TextView msgTextView;
    private String message;


    //db vars

    private DBAdapter  dbAdapter;
    private Cursor cursor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //initialing vars


        addButton=findViewById(R.id.addButton);
        clearButton=findViewById(R.id.clearButton);
        showButton=findViewById(R.id.showButton);
        msgTextView=findViewById(R.id.msgTextView);


        //method invoked


        addButton.setOnClickListener(this);
        clearButton.setOnClickListener(this);
        showButton.setOnClickListener(this);


        //initialing methods

        openDB();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        closeDB();
    }

    private void closeDB() {
        dbAdapter.closeDB();
    }

    private void openDB() {

        dbAdapter=new DBAdapter(this);
        dbAdapter.openDB();
    }


    @Override
    public void onClick(View view) {
        int id=view.getId();
        switch (id)
        {
            case R.id.addButton:
                addData();
                break;
            case R.id.clearButton:
                clearTextField();
                break;

            case R.id.showButton:
                showData();
                break;
                default:
                    showData();
                    break;
        }
    }





    private void displayText(String message)
    {
        msgTextView.setText(message);
    }



    private void addData() {
        displayText("Add record to database");
        dbAdapter.insertionRow("Arif",999);
        dbAdapter.insertionRow("Sharif",333);
        dbAdapter.insertionRow("Array",111);
        dbAdapter.insertionRow("Bali",888);
        dbAdapter.insertionRow("Lia",777);
    }

    private void clearTextField() {
        displayText("clear the text filed");
        dbAdapter.deleteAllRows();
    }

    private void showData()
    {
        displayText("Display records from database");
        cursor=dbAdapter.getAllRows();
        displayRecordSet(cursor);

    }



    //displaying record sets

    private void displayRecordSet(Cursor cursor) {
        message="";
        if(cursor.moveToFirst())
        {
            //processing data

            int id=cursor.getInt(0);
            String name=cursor.getString(1);
            int  studentNumber=cursor.getInt(2);


            //append data

            message +="ID:"+id+"name:"+name+"##:"+studentNumber+"\n";
        }

        displayText(message);
    }


}
