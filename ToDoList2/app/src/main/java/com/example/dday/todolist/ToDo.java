package com.example.dday.todolist;

public class ToDo {

    private long id;
    private String todo;

    public ToDo()
    {
        super();
    }

    public ToDo(long id,String todo) {
        this.id = id;
        this.todo=todo;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTodo() {
        return todo;
    }

    public void setTodo(String todo) {
        this.todo = todo;
    }
}
