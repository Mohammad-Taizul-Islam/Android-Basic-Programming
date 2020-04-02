package com.example.dday.notesroom;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

public class NoteViewModel extends AndroidViewModel {

    private NoteReposity reposity;
    private LiveData<List<Note>> allNotes;

    public NoteViewModel(@NonNull Application application) {
        super(application);
        reposity=new NoteReposity(application);
        allNotes=reposity.getAllNotes();
    }

    public void insert(Note note)
    {
        reposity.insert(note);
    }
    public void update(Note note)
    {
        reposity.update(note);
    }
    public void delete(Note note)
    {
        reposity.delete(note);
    }

    public void deleteAllNotes()
    {
        reposity.deleteAllNotes();
    }

    public LiveData<List<Note>> getAllNotes()
    {
        return  allNotes;
    }

}
