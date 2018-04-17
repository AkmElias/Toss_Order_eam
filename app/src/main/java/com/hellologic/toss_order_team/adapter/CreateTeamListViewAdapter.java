package com.hellologic.toss_order_team.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hellologic.toss_order_team.R;

import java.util.ArrayList;

public class CreateTeamListViewAdapter extends BaseAdapter {
    ArrayList<String> items;
    private Context context;
    private static String TAG = "my_tag";
    private  int color;

    public CreateTeamListViewAdapter(ArrayList<String> items, Context context,int color) {
        this.items = items;
        this.context = context;
        this.color = color;
//        Log.d(TAG, "initialized with");
//        for(int i=0; i<items.size(); i++)
//            Log.d(TAG, this.items.get(i));
    }

    @Override
    public int getCount() {
        return items.size();

    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View newView;
        LayoutInflater inflate =(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(view == null) {
            newView = new View(context);
            newView = inflate.inflate(R.layout.single_player, null);
        } else
        {
            newView = view;
        }

        TextView playerName = newView.findViewById(R.id.player_name);
        playerName.setTextColor(color);
        Log.d(TAG, "setting "+i+": " + items.get(i));
        playerName.setText(items.get(i));
        return newView;
    }

    public void clearArrayList() {
        items.clear();
        notifyDataSetChanged();
    }

    public void setArrayList(ArrayList<String> items) {
        this.items = items;
    }
}
