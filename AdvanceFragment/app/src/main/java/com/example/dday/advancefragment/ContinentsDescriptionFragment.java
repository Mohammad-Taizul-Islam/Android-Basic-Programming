package com.example.dday.advancefragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ContinentsDescriptionFragment extends Fragment{

    private View rootView;
    private TextView textViewContinentDescription;
    private String continentName;
    private String continentDescription;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(savedInstanceState!=null)
        {
            continentName=savedInstanceState.getString("selectedContinent",continentName);
            continentDescription=getString(getStringId(continentName));
        }else{

            Bundle bundle=getArguments();
            continentName=bundle.getString(CallBackInterface.KEY_SELECTED_CONTINENT,"AFRICA");
            continentDescription=getString(getStringId(continentName));

        }

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("selectedContinent",continentName);
    }

    private int getStringId(String continentName) {
      if(continentName.equals("ASIA"))
      {
          return R.string.ASIA;
      }else if(continentName.equals("AFRICA"))
      {
          return R.string.AFRICA;
      }else if(continentName.equals("EUROPE"))
      {
          return R.string.EUROPE;
      }else if(continentName.equals("NORTH_AMERICA"))
      {
          return R.string.NORTH_AMERICA;
      }else if(continentName.equals("SOUTH_AMERICA"))
      {
          return R.string.SOUTH_AMERICA;
      }else if(continentName.equals("AUSTRALIA"))
      {
          return R.string.AUSTRALIA;
      }else{
          return R.string.ANTARCTICA;
      }
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView= inflater.inflate(R.layout.fragment_continent_description,container,false);
        initUi();
        return rootView;
    }

    private void initUi() {

        textViewContinentDescription=rootView.findViewById(R.id.textViewContinentDescription);

    }

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(continentName);
        textViewContinentDescription.setText(continentDescription);
    }


}
