package com.example.mealparty;

import android.view.Menu;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class PageAdapter extends FragmentStateAdapter {
    private ArrayList<Fragment> mFragments;
    public PageAdapter(FragmentActivity fa, ArrayList list){
        super(fa);
        this.mFragments = list;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position){
        return mFragments.get(position);
    }

    @Override
    public int getItemCount(){
        return mFragments.size();
    }
}
