package com.example.mealparty;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder>{
    private ArrayList<Party_Item> mData = null;

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView textName;
        TextView textMember;
        TextView textTime;
        ViewHolder(View itemView){
            super(itemView);
            textName = itemView.findViewById(R.id.party_name);
            textMember = itemView.findViewById(R.id.party_member);
            textTime = itemView.findViewById(R.id.party_time);
        }
    }

    MyItemRecyclerViewAdapter(ArrayList<Party_Item> list)
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
        String textm = mData.get(position).member;
        holder.textMember.setText(textm);

        String textt = mData.get(position).time;
        holder.textTime.setText(textt);

        String textn = mData.get(position).name;
        holder.textName.setText(textn);
    }

    @Override
    public int getItemCount()
    {
        return mData.size();
    }
}
