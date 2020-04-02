package com.example.dday.datatransfertoactivities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {


    private TextView text,number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        text=findViewById(R.id.text_view_message);
        number=findViewById(R.id.text_view_number);

        Intent data=getIntent();

        text.setText(data.getStringExtra(MainActivity.TEXT_EXTRA));
        number.setText(" "+data.getIntExtra(MainActivity.INT_EXTRA,0));
    }


}
