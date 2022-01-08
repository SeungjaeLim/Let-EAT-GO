package com.example.mealparty;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder>{
    private ArrayList<String> mData = null;

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView textView1;
        ViewHolder(View itemView){
            super(itemView);
            textView1 = itemView.findViewById(R.id.party_name);
        }
    }

    MyItemRecyclerViewAdapter(ArrayList<String> list)
    {
        mData = list;
    }

    @Override
    public MyItemRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.fragment_party, parent, false);
        MyItemRecyclerViewAdapter.ViewHolder vh = new MyItemRecyclerViewAdapter.ViewHolder(view);

        return vh;
    }

    @Override
    public void onBindViewHolder(MyItemRecyclerViewAdapter.ViewHolder holder, int position)
    {
        String text = mData.get(position);
        holder.textView1.setText(text);
    }

    @Override
    public int getItemCount()
    {
        return mData.size();
    }
}
