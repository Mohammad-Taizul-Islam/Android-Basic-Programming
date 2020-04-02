package com.example.dday.recyclerviewdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mlayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ArrayList<ExampleItem> arrayList=new ArrayList<>();
        arrayList.add(new ExampleItem(R.drawable.ic_android,"Line 1","Line 2"));
        arrayList.add(new ExampleItem(R.drawable.ic_audio,"Line 3","Line 4"));
        arrayList.add(new ExampleItem(R.drawable.ic_wb_sunny,"Line 5","Line 6"));


        arrayList.add(new ExampleItem(R.drawable.ic_android,"Line 7","Line 8"));
        arrayList.add(new ExampleItem(R.drawable.ic_audio,"Line 9","Line 10"));
        arrayList.add(new ExampleItem(R.drawable.ic_wb_sunny,"Line 11","Line 12"));


        arrayList.add(new ExampleItem(R.drawable.ic_android,"Line 13","Line 14"));
        arrayList.add(new ExampleItem(R.drawable.ic_audio,"Line 15","Line 16"));
        arrayList.add(new ExampleItem(R.drawable.ic_wb_sunny,"Line 17","Line 18"));


        mRecyclerView=findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mlayoutManager=new LinearLayoutManager(this);
        mAdapter=new ExampleAdapter(arrayList);
        mRecyclerView.setLayoutManager(mlayoutManager);
        mRecyclerView.setAdapter(mAdapter);


    }
}
