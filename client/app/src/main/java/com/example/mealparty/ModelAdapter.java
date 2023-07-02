package com.example.mealparty;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ModelAdapter extends FragmentStateAdapter {
    private ArrayList<Fragment> mFragments;
    //private List<ListData> models;
    //private LayoutInflater layoutInflater;
    //private Context context;
    private int place;

    public ModelAdapter(FragmentActivity fa, int place, ArrayList list){
        super(fa);
        this.mFragments = list;
        this.place = place;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position){
        return mFragments.get(position);
    }

    @Override
    public int getItemCount(){
        // 카이마루면 2개, 나머지면 3개 생성
        return mFragments.size();
    }
}
