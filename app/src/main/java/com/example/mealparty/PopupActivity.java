package com.example.mealparty;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.animation.ArgbEvaluator;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Adapter;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PopupActivity extends AppCompatActivity {

    ViewPager2 viewPager;
    ArrayList<ListData> models;
    Integer[] colors = null;
    ArgbEvaluator argbEvaluator = new ArgbEvaluator();
    int place;
    int DEFAULT_VAL = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup);

        Intent intent = getIntent();

        ///나중에 datepicker로 수

        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String getTime = sdf.format(date);

        ////

        models = (ArrayList<ListData>) intent.getSerializableExtra("menu");
        place = intent.getIntExtra("식당",DEFAULT_VAL);

        ArrayList<Fragment> fragments = new ArrayList<>();
        //ArrayList<String[]> menudata = new ArrayList<>();

        ListData MenuList = null;
        if(models.isEmpty()==true){
            System.out.println("EMPTY");
        }else{
            System.out.print("NONO\n\n\n\n");
        }

        for(ListData tmpList : models){
            String datatime = tmpList.getDate();
            System.out.println(datatime+"\n\n\n");
            if(datatime != null) {
                if (datatime.equals(getTime)) {
                    MenuList = tmpList;
                    break;
                }
            }
        }

        switch(place){
            case 0:
                fragments.clear();
                fragments.add(MealItem.newInstance(MenuList.getKama_lunch()));
                fragments.add(MealItem.newInstance(MenuList.getKama_dinner()));

                break;
            case 1:
                fragments.clear();
                fragments.add(MealItem.newInstance(MenuList.getWest_breakfast()));
                fragments.add(MealItem.newInstance(MenuList.getWest_lunch()));
                fragments.add(MealItem.newInstance(MenuList.getWest_dinner()));
                break;
            case 2:
                fragments.clear();
                fragments.add(MealItem.newInstance(MenuList.getEast_breakfast()));
                fragments.add(MealItem.newInstance(MenuList.getEast_lunch()));
                fragments.add(MealItem.newInstance(MenuList.getEast_dinner()));
                break;
        }


        //models
        //Toast.makeText(getApplicationContext(),Integer.toString(place),Toast.LENGTH_SHORT).show();

        ModelAdapter modelAdapter = new ModelAdapter(this, place, fragments);

        viewPager = (ViewPager2) findViewById(R.id.popup_viewpager);
        viewPager.setAdapter(modelAdapter);



    }
}