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
import java.util.Collections;
import java.util.List;

public class PlayerOrderListViewAdapter extends BaseAdapter {
    List<String> items;
    private Context context;
    private static String TAG = "my_tag";
    private ArrayList<Integer> order;


    public PlayerOrderListViewAdapter(List<String> items, Context context) {
        this.items = items;
        this.context = context;
        this.order = new ArrayList<>();

        Log.d(TAG, "initialized with");
        for(int i=0; i<items.size(); i++)
            Log.d(TAG, this.items.get(i));

        for(int i=1; i<= items.size(); i++) {
            order.add(i);
        }
        Collections.shuffle(order);
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
            newView = inflate.inflate(R.layout.single_player_with_id, null);
        } else
        {
            newView = view;
        }

        TextView playerName = newView.findViewById(R.id.player_name);
        TextView playerId = newView.findViewById(R.id.player_id);
        Log.d(TAG, "setting "+i+": " + items.get(i));
        playerName.setText(items.get(i));
        playerId.setText(String.valueOf(order.get(i)));
        return newView;
    }
}
