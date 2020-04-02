package com.example.dday.notesroom;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final int ADD_NOTE_REQUEST=1;
    public static final int EDIT_NOTE_REQUEST=2;

    private NoteViewModel viewModel;
    private RecyclerView recyclerView;
    private FloatingActionButton addFloatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        recyclerView=findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final NoteAdapter noteAdapter=new NoteAdapter();
        recyclerView.setAdapter(noteAdapter);

        viewModel= ViewModelProviders.of(this).get(NoteViewModel.class);
        viewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(@Nullable List<Note> notes) {

               noteAdapter.setNotes(notes);

            }
        });



        addFloatingActionButton=findViewById(R.id.button_add_note);
        addFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,AddEditNoteActivity.class);
                startActivityForResult(intent,ADD_NOTE_REQUEST);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                    viewModel.delete(noteAdapter.getNoteAt(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "Note deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);


        noteAdapter.setOnItemClickListener(new NoteAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Note note) {
                Intent intent=new Intent(MainActivity.this , AddEditNoteActivity.class);

                intent.putExtra(AddEditNoteActivity.ID_EXTRA,note.getId());
                intent.putExtra(AddEditNoteActivity.TITLE_EXTRA,note.getTitle());
                intent.putExtra(AddEditNoteActivity.DESCRIPTION_EXTRA,note.getDescription());
                intent.putExtra(AddEditNoteActivity.PRIORITY_EXTRA,note.getPriority());
                startActivityForResult(intent,EDIT_NOTE_REQUEST);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_NOTE_REQUEST && resultCode == RESULT_OK) {
            String title = data.getStringExtra(AddEditNoteActivity.TITLE_EXTRA);
            String description = data.getStringExtra(AddEditNoteActivity.DESCRIPTION_EXTRA);
            int priority = data.getIntExtra(AddEditNoteActivity.PRIORITY_EXTRA, 1);

            Note note = new Note(title, description, priority);
            viewModel.insert(note);

            Toast.makeText(this, "Note saved", Toast.LENGTH_SHORT).show();
        }else if (requestCode == EDIT_NOTE_REQUEST && resultCode == RESULT_OK){

           int id=getIntent().getIntExtra(AddEditNoteActivity.ID_EXTRA,-1);

           if(id == -1)
           {
               Toast.makeText(this, "Note cann't be updated", Toast.LENGTH_SHORT).show();
               return;
           }

            String title = data.getStringExtra(AddEditNoteActivity.TITLE_EXTRA);
            String description = data.getStringExtra(AddEditNoteActivity.DESCRIPTION_EXTRA);
            int priority = data.getIntExtra(AddEditNoteActivity.PRIORITY_EXTRA, 1);

            Note note = new Note(title, description, priority);
            note.setId(id);
            viewModel.update(note);

            Toast.makeText(this, "Note updated", Toast.LENGTH_SHORT).show();

        }
        else
        {
            Toast.makeText(this, "Note not saved", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.delete_all_note :

                viewModel.deleteAllNotes();

                Toast.makeText(MainActivity.this,"All notes deleted",Toast.LENGTH_SHORT).show();
                return true;
                default:
                return super.onOptionsItemSelected(item);
        }

    }
}
