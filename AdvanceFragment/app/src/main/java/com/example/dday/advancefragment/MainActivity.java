package com.example.dday.advancefragment;

import android.content.res.Configuration;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


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
                addContinentsFragment();
        }else if(findViewById(R.id.layout_main_landscape)!=null)
        {
                addContinentsFragment();
                addContinentsDescriptionFragment(R.id.fragmentContainer2,"AFRICA");
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
        selectedContinent=savedInstanceState.getString("selectedContinent","AFRICA");
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
            addContinentsDescriptionFragment(R.id.fragmentContainer2,selectedContinent);
        }

    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setContentView(R.layout.activity_main);
        if(newConfig.orientation==Configuration.ORIENTATION_LANDSCAPE)
        {
            addContinentsFragment();
            if(selectedContinent==null)
            {
                addContinentsDescriptionFragment(R.id.fragmentContainer2,"AFRICA");
            }else
            {
                addContinentsDescriptionFragment(R.id.fragmentContainer2,selectedContinent);
            }
        }else if(newConfig.orientation==Configuration.ORIENTATION_PORTRAIT)
        {
            if(selectedContinent==null)
            {
                addContinentsFragment();
            }else {
                addContinentsDescriptionFragment(selectedContinent);
            }
        }
    }
}
