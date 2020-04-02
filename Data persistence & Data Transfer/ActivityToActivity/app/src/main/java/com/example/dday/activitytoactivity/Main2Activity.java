package com.example.dday.activitytoactivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {

    private TextView numbers;
    private Button addButton,subButton;
    private int number1,number2,result;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        intent=getIntent();
        number1=intent.getIntExtra(MainActivity.INT_1_EXTRA,0);
        number2=intent.getIntExtra(MainActivity.INT_2_EXTRA,0);


        numbers=findViewById(R.id.textViewNumbers);
        numbers.setText(""+number1 +","+number2);

        addButton=findViewById(R.id.buttonAdd);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent=new Intent();
                result=number1+number2;
                intent.putExtra(MainActivity.RESULT_EXTRA,result);
                setResult(RESULT_OK,intent);
                finish();
            }
        });

        subButton=findViewById(R.id.buttonSub);
        subButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent=new Intent();
                result=number1-number2;
                intent.putExtra(MainActivity.RESULT_EXTRA,result);
                setResult(RESULT_OK,intent);
                finish();
            }
        });


    }
}
