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

public class CreatePartyListRecyclerViewAdapter extends RecyclerView.Adapter<CreatePartyListRecyclerViewAdapter.ViewHolder> {
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
                        new AlertDialog.Builder(context)
                                .setTitle(pitem.time+ " " + pitem.name)
                                .setMessage("삭제하시겠습니까?")
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        // 확인시 처리 로직
                                        CreatePartyList.Delete_Party(pitem.jobid);
                                    }})
                                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        // 취소시 처리 로직
                                    }})
                                .show();
                    }
                }
            });
        }
    }

    CreatePartyListRecyclerViewAdapter(ArrayList<Party_Item> list)
    {
        mData = list;
    }

    @Override
    public CreatePartyListRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.fragment_party, parent, false);
        CreatePartyListRecyclerViewAdapter.ViewHolder vh = new CreatePartyListRecyclerViewAdapter.ViewHolder(view);

        return vh;
    }

    @Override
    public void onBindViewHolder(CreatePartyListRecyclerViewAdapter.ViewHolder holder, int position)
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
