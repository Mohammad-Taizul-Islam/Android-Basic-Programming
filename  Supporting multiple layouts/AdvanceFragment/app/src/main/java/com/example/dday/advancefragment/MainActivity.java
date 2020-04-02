package com.example.dday.advancefragment;

import android.os.PersistableBundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements CallBackInterface{


    //VARS
    private static final String TAG=MainActivity.class.getSimpleName();
    private static final String TAG_COMMON="orientation change";
    private FragmentManager manager;
    private FragmentTransaction transaction;
    CallBackInterface callBackInterface;
    String selectedContinent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        manager=getSupportFragmentManager();
        if(findViewById(R.id.layout_main_portrait)!=null)
        {
            if(savedInstanceState==null)
            {
                addContinentsFragment();
            }else{
                addContinentsDescriptionFragment(savedInstanceState.getString("selectedContinent",null));

            }

        }else if(findViewById(R.id.layout_main_landscape)!=null)
        {
             addContinentsFragment();
            if(savedInstanceState==null)
            {
                addContinentsDescriptionFragment(R.id.fragmentContainer2,savedInstanceState.getString("selectedContinent","ASIA"));
            }else{
                addContinentsDescriptionFragment(R.id.fragmentContainer2,savedInstanceState.getString("selectedContinent",null));

            }

        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putString("selectedContinent",selectedContinent);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        selectedContinent=savedInstanceState.getString("selectedContinent","ASIA");
    }

    private void addContinentsFragment() {
        transaction=manager.beginTransaction();
        ContinentsFragment continentsFragment=new ContinentsFragment();
        continentsFragment.setCallBackInterface(this);
        transaction.add(R.id.fragmentContainer,continentsFragment);
        transaction.commit();
    }

    private void addContinentsDescriptionFragment(String continents)
    {
        transaction=manager.beginTransaction();

        ContinentsDescriptionFragment descriptionFragment=new ContinentsDescriptionFragment();

        Bundle bundle=new Bundle();
        bundle.putString(callBackInterface.KEY_SELECTED_CONTINENT,continents);
        descriptionFragment.setArguments(bundle);

        transaction.replace(R.id.fragmentContainer,descriptionFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }



    private void addContinentsDescriptionFragment(int containerId,String continents)
    {
        transaction=manager.beginTransaction();

        ContinentsDescriptionFragment descriptionFragment=new ContinentsDescriptionFragment();

        Bundle bundle=new Bundle();
        bundle.putString(callBackInterface.KEY_SELECTED_CONTINENT,continents);
        descriptionFragment.setArguments(bundle);

        transaction.replace(containerId,descriptionFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void callBackMethod(String continents) {
        //Toast.makeText(this,"you have to trigger a fragment",Toast.LENGTH_SHORT).show();
        selectedContinent=continents;
        if(findViewById(R.id.layout_main_landscape)==null)
        {
            addContinentsDescriptionFragment(continents);
        }else
        {
            addContinentsDescriptionFragment(R.id.fragmentContainer2,continents);
        }

    }
}
