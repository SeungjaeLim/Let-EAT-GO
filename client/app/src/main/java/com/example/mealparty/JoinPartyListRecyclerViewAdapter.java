package com.example.mealparty;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class JoinPartyListRecyclerViewAdapter extends RecyclerView.Adapter<JoinPartyListRecyclerViewAdapter.ViewHolder> {
    private ArrayList<Party_Item> mData = null;
    Context context;

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView textName;
        TextView textMember;
        TextView textTime;
        ViewHolder(View itemView){
            super(itemView);
            textName = itemView.findViewById(R.id.party_name);
            textMember = itemView.findViewById(R.id.party_member);
            textTime = itemView.findViewById(R.id.party_time);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION)
                    {
                        Party_Item pitem = mData.get(pos);
                        System.out.println("Clicked Jobid : " + pitem.jobid);
                        if(pitem.joined == 1)
                        {
                            new AlertDialog.Builder(context)
                                    .setTitle("Resign " + pitem.name+"?")
                                    .setMessage(pitem.hostname +" 참여 중")
                                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int whichButton) {
                                            // 확인시 처리 로직
                                            JoinPartyList.Resign_Party(pitem.jobid);
                                        }})
                                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int whichButton) {
                                            // 취소시 처리 로직
                                        }})
                                    .show();
                        }
                        else if(pitem.joined == 2)
                        {
                            new AlertDialog.Builder(context)
                                    .setTitle("Resign " + pitem.name+"?")
                                    .setMessage(pitem.hostname +", "+ pitem.name1+ " 참여 중")
                                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int whichButton) {
                                            // 확인시 처리 로직
                                            JoinPartyList.Resign_Party(pitem.jobid);
                                        }})
                                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int whichButton) {
                                            // 취소시 처리 로직
                                        }})
                                    .show();
                        }
                        else if(pitem.joined == 3)
                        {
                            new AlertDialog.Builder(context)
                                    .setTitle("Resign " + pitem.name+"?")
                                    .setMessage(pitem.hostname +", "+ pitem.name1+", " + pitem.name2+ " 참여 중")
                                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int whichButton) {
                                            // 확인시 처리 로직
                                            JoinPartyList.Resign_Party(pitem.jobid);
                                        }})
                                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int whichButton) {
                                            // 취소시 처리 로직
                                        }})
                                    .show();
                        }
                        else if(pitem.joined == 4)
                        {
                            new AlertDialog.Builder(context)
                                    .setTitle("Resign " + pitem.name+"?")
                                    .setMessage(pitem.hostname +", "+ pitem.name1+", " + pitem.name2 +", "+ pitem.name3 + " 참여 중")
                                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int whichButton) {
                                            // 확인시 처리 로직
                                            JoinPartyList.Resign_Party(pitem.jobid);
                                        }})
                                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int whichButton) {
                                            // 취소시 처리 로직
                                        }})
                                    .show();
                        }
                    }
                }
            });
        }
    }

    JoinPartyListRecyclerViewAdapter(ArrayList<Party_Item> list)
    {
        mData = list;
    }

    @Override
    public JoinPartyListRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.fragment_party, parent, false);
        JoinPartyListRecyclerViewAdapter.ViewHolder vh = new JoinPartyListRecyclerViewAdapter.ViewHolder(view);

        return vh;
    }

    @Override
    public void onBindViewHolder(JoinPartyListRecyclerViewAdapter.ViewHolder holder, int position)
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

