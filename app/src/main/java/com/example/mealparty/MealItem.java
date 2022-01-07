package com.example.mealparty;

import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

public class MealItem extends Fragment {
    //ListData menudata;
    String[] menudata;
    public static MealItem newInstance(String[] menudata){
        MealItem menuItem = new MealItem();
        menuItem.menudata = menudata;
        Bundle bundle = new Bundle();
        /*for(String[] tmp : menudata){
            bundle.putStringArray("menudata", tmp);
        }*/
        bundle.putStringArray("menudata", menudata);
        menuItem.setArguments(bundle);
        return menuItem;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        if(getArguments() != null){
            String[] menudata =  getArguments().getStringArray("menudata");
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.menuitem, container, false);
        TextView desc = view.findViewById(R.id.desc);
        String menu = String.join("\n",menudata);
        desc.setText(menu);
        return view;
    }
}
