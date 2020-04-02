package com.example.dday.activitytoactivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static final String TAG=MainActivity.class.getSimpleName();
    public static final String INT_1_EXTRA="com.example.dday.activitytoactivity_INT_1_EXTRA";
    public static final String INT_2_EXTRA="com.example.dday.activitytoactivity_INT_2_EXTRA";
    public static final String RESULT_EXTRA="com.example.dday.activitytoactivity_RESULT_EXTRA";
    public static final int REQUEST_FOR_RESULT=1;
    private TextView result;
    private EditText number1,number2;
    private int num1,num2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        result=findViewById(R.id.textViewResult);
        number1=findViewById(R.id.editTextNumber1);
        number2=findViewById(R.id.editTextNumber2);

    }


    public void goToMain2Activity(View view) {

        num1=Integer.parseInt(number1.getText().toString());
        num2=Integer.parseInt(number2.getText().toString());
        Intent data=new Intent(MainActivity.this,Main2Activity.class);
        data.putExtra(INT_1_EXTRA,num1);
        data.putExtra(INT_2_EXTRA,num2);
        startActivityForResult(data,REQUEST_FOR_RESULT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_FOR_RESULT)
        {
            if(resultCode ==RESULT_OK)
            {
                result.setText("" +data.getIntExtra(RESULT_EXTRA,0));
            }else {
                Toast.makeText(this, "Something doesn't go right", Toast.LENGTH_SHORT).show();
            }
        }

    }
}
