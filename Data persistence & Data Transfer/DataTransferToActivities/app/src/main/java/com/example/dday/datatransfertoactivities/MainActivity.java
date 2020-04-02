package com.example.dday.datatransfertoactivities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {


    public static final String TAG=MainActivity.class.getSimpleName();
    public static final String TEXT_EXTRA="com.example.dday.datatransfertoactivities_TEXT_EXTRA";
    public static final String INT_EXTRA="com.example.dday.datatransfertoactivities_INT_EXTRA";
    private EditText text,number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text=findViewById(R.id.text_view_message);
        number=findViewById(R.id.text_view_number);
    }

    public void openActivity(View view) {
        Intent intent=new Intent(MainActivity.this,SecondActivity.class);
        intent.putExtra(TEXT_EXTRA,text.getText().toString());
        intent.putExtra(INT_EXTRA,Integer.parseInt(number.getText().toString()));
        startActivity(intent);


    }
}
