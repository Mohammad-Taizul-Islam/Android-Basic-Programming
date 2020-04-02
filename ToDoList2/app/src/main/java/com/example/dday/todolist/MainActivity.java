package com.example.dday.todolist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG=MainActivity.class.getSimpleName();
    private EditText toDoItem,toDoPlace,toDoId,toDoNemItem;
    private TextView msgTextView;
    private Button addButton,deleteButton,updateButton;

    private ToDoListAdapter toDoListAdapter;
    private List<ToDo> toDos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toDoListAdapter=ToDoListAdapter.getToDoAdapterInstance(this);
        toDos=toDoListAdapter.getAllToDos();




        toDoId=findViewById(R.id.editTextToDoId);
        toDoItem=findViewById(R.id.editTextNewToDoString);
        toDoPlace=findViewById(R.id.editTextPlace);
        toDoNemItem=findViewById(R.id.editTextNewToDo);



        msgTextView=findViewById(R.id.textViewToDos);


        addButton=findViewById(R.id.buttonAddToDo);
        deleteButton=findViewById(R.id.buttonRemoveToDo);
        updateButton=findViewById(R.id.buttonModifyToDo);

        addButton.setOnClickListener(this);
        deleteButton.setOnClickListener(this);
        updateButton.setOnClickListener(this);
    }


    @Override
    protected void onResume() {
        super.onResume();
        setNewToDoList();
    }



    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.buttonAddToDo:
                addToDo();
                break;
            case R.id.buttonRemoveToDo:
                deleteToDo();
                break;
            case R.id.buttonModifyToDo:
                modifyToDo();
                break;
        }

    }


    private void addToDo()
    {
        toDoListAdapter.insertData(toDoItem.getText().toString());
        setNewToDoList();

    }

    private void modifyToDo()
    {
        toDoListAdapter.modifydata(Integer.parseInt(toDoId.getText().toString()),toDoNemItem.getText().toString());
    }

    private void deleteToDo()
    {
        toDoListAdapter.deleteData(Integer.parseInt(toDoId.getText().toString()));
        setNewToDoList();
    }


    private void setNewToDoList()
    {
        msgTextView.setText(getAllToDosListStrings());
    }

    private String getAllToDosListStrings()
    {
        toDos=toDoListAdapter.getAllToDos();
        if(toDos!=null & toDos.size()>0)
        {
            StringBuilder stringBuilder=new StringBuilder(" ");
            for(ToDo toDo:toDos)
            {
                stringBuilder.append(toDo.getId()+","+toDo.getTodo());
            }
            return stringBuilder.toString();
        }else
        {
            return "No todo list here";
        }
    }

}
