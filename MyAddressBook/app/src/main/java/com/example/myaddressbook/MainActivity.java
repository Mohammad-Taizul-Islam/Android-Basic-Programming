package com.example.myaddressbook;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuInflater;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


  private List<Person> personList=new ArrayList<>();
  private CoordinatorLayout coordinatorLayout;
  private RecyclerView recyclerView;
  private TextView noPeopleView;
  private PersonsAdapter pAdapter;

    public static final int REQUEST_ADD_PEOPLE=1;
    public static final int REQUEST_EDIT_PEOPLE=2;



    private DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        noPeopleView=findViewById(R.id.empty_people_view);

        coordinatorLayout=findViewById(R.id.coordinateLayout);
        recyclerView=findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        db=new DBHelper(this);


        personList.addAll(db.getAllPersons());


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/

                Intent i=new Intent(MainActivity.this, EditAddPeopleActivity.class);
                startActivityForResult(i,REQUEST_ADD_PEOPLE);
            }
        });

        pAdapter=new PersonsAdapter(this,personList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new MyDividerItemDecoration(this, LinearLayoutManager.VERTICAL, 16));
        recyclerView.setAdapter(pAdapter);


        toggleEmptyPeople();


        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                        deletePeople(viewHolder.getAdapterPosition());
            }
        }).attachToRecyclerView(recyclerView);


        pAdapter.setOnItemClickListener(new PersonsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Person person) {
                Intent i=new Intent(MainActivity.this, EditAddPeopleActivity.class);
                i.putExtra(EditAddPeopleActivity.EXTRA_PERSON_ID,person.getId());
                i.putExtra(EditAddPeopleActivity.EXTRA_PERSON_NAME,person.getPerson_name());
                i.putExtra(EditAddPeopleActivity.EXTRA_PHONE_NUMBER,person.getPhone_number());
                i.putExtra(EditAddPeopleActivity.EXTRAEMAIL,person.getEmail());

                startActivityForResult(i,REQUEST_EDIT_PEOPLE);
                pAdapter.notifyItemChanged(pAdapter.getItemCount());
            }
        });



    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_ADD_PEOPLE && resultCode ==RESULT_OK)
        {
            String name=data.getStringExtra(EditAddPeopleActivity.EXTRA_PERSON_NAME);
            String phone_number=data.getStringExtra(EditAddPeopleActivity.EXTRA_PHONE_NUMBER);
            String email=data.getStringExtra(EditAddPeopleActivity.EXTRAEMAIL);

            db.insertPerson(name,phone_number,email);
            toggleEmptyPeople();
            //Person person=new Person(name,phone_number,email);

            Toast.makeText(this, "Person saved", Toast.LENGTH_SHORT).show();



        }else if( requestCode==REQUEST_EDIT_PEOPLE && requestCode ==RESULT_OK)
        {
            int id=data.getIntExtra(EditAddPeopleActivity.EXTRA_PERSON_ID,-1);
            if(id==-1)
            {

                Toast.makeText(this, "People can't be updated", Toast.LENGTH_SHORT).show();
            }else{

                String name=data.getStringExtra(EditAddPeopleActivity.EXTRA_PERSON_NAME);
                String phone_number=data.getStringExtra(EditAddPeopleActivity.EXTRA_PHONE_NUMBER);
                String email=data.getStringExtra(EditAddPeopleActivity.EXTRAEMAIL);

                Person person=new Person(name,phone_number,email);
                person.setId(id);
                db.updatePerson(person);
                toggleEmptyPeople();

            }
        }
    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_deleteAll) {
            //deleteAll persons
            return true;
        }

        return super.onOptionsItemSelected(item);
    }










    private void toggleEmptyPeople() {
        // you can check notesList.size() > 0

        if (db.getPersonsCount() > 0) {
            noPeopleView.setVisibility(View.GONE);
        } else {
            noPeopleView.setVisibility(View.VISIBLE);
        }
    }






    private void deletePeople(int position) {

        db.deletePerson(personList.get(position));
        personList.remove(position);

        pAdapter.notifyItemRemoved(position);
        toggleEmptyPeople();

    }
}
