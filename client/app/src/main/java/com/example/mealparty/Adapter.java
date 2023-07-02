package com.example.mealparty;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class Adapter extends PagerAdapter {
    private List<ListData> models;
    private LayoutInflater layoutInflater;
    private Context context;

    public Adapter(List<ListData> models, Context context){
        this.models = models;
        this.context = context;
    }

    @Override
    public int getCount(){
        return models.size();
    }
    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position){
        layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.menuitem,container,false);

        TextView title, desc;

        title = view.findViewById(R.id.title);
        desc = view.findViewById(R.id.desc);

        //title.setText();
        desc.setText(models.get(position).getKama_lunch().toString());

        container.addView(view,0);
        return view;

    }
    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object){
        container.removeView((View)object);
    }
}
