package com.example.dday.advancefragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ContinentsFragment extends Fragment{

    //VARS

    private static final String TAG=ContinentsFragment.class.getSimpleName();
    private View rootView;
    private ListView listView;
    private String continents[];
    private ArrayAdapter<String> arrayAdapter;
    private Context context;
    CallBackInterface callBackInterface;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(rootView==null){
            rootView=inflater.inflate(R.layout.fragment_continent,container,false);
            initUi();
            return rootView;
        }
        return rootView;
    }

    @Override
    public void onResume() {
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(getString(R.string.app_name)+"->select Continet");
        super.onResume();
    }

    public void setCallBackInterface(CallBackInterface callBackInterface)
    {
        this.callBackInterface=callBackInterface;
    }

    private void initUi() {
        context=getContext();
        continents=getResources().getStringArray(R.array.continents);
        listView=(ListView) rootView.findViewById(R.id.listContainer);
        arrayAdapter=new ArrayAdapter<String>(context,android.R.layout.simple_list_item_1,continents);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(callBackInterface!=null)
                {
                    callBackInterface.callBackMethod(continents[position]);
                }
            }
        });

    }


    //for orientation changes........


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(savedInstanceState!=null)
        {
            callBackInterface=(MainActivity)getActivity();
        }
    }
}
