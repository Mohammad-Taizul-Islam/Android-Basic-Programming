package com.example.dday.newcontactbook;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String TAG=MainActivity.class.getSimpleName();
    private FloatingActionButton floatingActionButton;
    private PeopleAdapter adapter;
    private DBHelpler helpler;
    private Intent intent;
    private RecyclerView recyclerView;
    private List<People> peopleList;
    public static final int REQUEST_ADD_PEOPLE=1;
    private People people;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        floatingActionButton=findViewById(R.id.add_contact);
        recyclerView=findViewById(R.id.recycleView);
        peopleList=new ArrayList<>();
        helpler=new DBHelpler(this);
        peopleList.addAll(helpler.getAllPeople());

        adapter=new PeopleAdapter(this,peopleList);
        RecyclerView.LayoutManager manager=new LinearLayoutManager(getApplicationContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(manager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        toggleEmptyContacts();

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,Edit_Add_Contact.class);
                startActivityForResult(intent,REQUEST_ADD_PEOPLE);
            }
        });


        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT |ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                //helpler.deleteC(noteAdapter.getNoteAt(viewHolder.getAdapterPosition()));
                helpler.deletePeoples(peopleList.get(viewHolder.getAdapterPosition()));
                peopleList.remove(viewHolder.getAdapterPosition());
                adapter.notifyItemRemoved(viewHolder.getAdapterPosition());
                toggleEmptyContacts();
                Toast.makeText(MainActivity.this, "Deleted a contacts", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_ADD_PEOPLE && resultCode ==RESULT_OK)
        {
            String name=data.getStringExtra(Edit_Add_Contact.EXTRA_NAME);
            String phone=data.getStringExtra(Edit_Add_Contact.EXTRA_PHONE);
            String email=data.getStringExtra(Edit_Add_Contact.EXTRA_EMAIL);
           long id = helpler.insertContacts(name,phone,email);
            people=helpler.getPeople(id);
            if(people != null)
            {
                peopleList.add(0,people);
                adapter.notifyDataSetChanged();
                toggleEmptyContacts();
            }
            Toast.makeText(this, "Contact saved", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "Contacts is not saved", Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * Toggling list and empty list contacts view
     */
    private void toggleEmptyContacts() {
        // you can check notesList.size() > 0

        if (helpler.getPeopleCounts()<0) {
            recyclerView.setVisibility(View.GONE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
        }
    }
}
