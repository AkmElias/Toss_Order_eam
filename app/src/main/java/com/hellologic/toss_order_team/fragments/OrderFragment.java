package com.hellologic.toss_order_team.fragments;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.hellologic.toss_order_team.AdController;
import com.hellologic.toss_order_team.adapter.PlayerOrderListViewAdapter;
import com.hellologic.toss_order_team.R;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class OrderFragment extends Fragment {

    private AlertDialog nameInputDialog;
    private ArrayList<String> playerNames;
    private static String TAG = "my_tag";
    private ListView listView;
    Button makePopUpButton;
    private AdController adController;
    //private TextView playerName

    public OrderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_order, container, false);

        makePopUpButton = v.findViewById(R.id.makePopUpButton);
        playerNames = new ArrayList<>();
        adController = new AdController(getActivity(), getContext(),v,  AdController.BANNER_STANDERD);


        buildDialog(inflater);
        setPopUpButtonOnClickListener();

        return v;
    }

    private void setPopUpButtonOnClickListener() {
        makePopUpButton.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View view) {
                playerNames.clear();
                nameInputDialog.show();
            }
        });
    }


    private void buildDialog(LayoutInflater inflater) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflat = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.customlayout, null);
        dialogBuilder.setView(dialogView);


        dialogBuilder.setTitle("make team");
        dialogBuilder.setMessage("enter players name");
        dialogBuilder.setPositiveButton("ADD", null);
        dialogBuilder.setNegativeButton("DONE", null);


        nameInputDialog = dialogBuilder.create();

        nameInputDialog.setOnShowListener(new DialogInterface.OnShowListener() {

            @Override
            public void onShow(DialogInterface dialogInterface) {

                Button addButton = ((AlertDialog) nameInputDialog).getButton(AlertDialog.BUTTON_POSITIVE);
                addButton.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        final EditText name = nameInputDialog.findViewById(R.id.edit1);

                        if(name.getText().toString().trim().length()>0) {
                            String playerName = name.getText().toString();
                            playerNames.add(playerName);
                            name.setText("");
                        } else
                            toastIt("Please Give A Name");
                    }
                });

                Button doneButton = ((AlertDialog) nameInputDialog).getButton(AlertDialog.BUTTON_NEGATIVE);
                doneButton.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        final EditText name = nameInputDialog.findViewById(R.id.edit1);
                        if(name.getText().toString().trim().length()>0) {
                            String playerName = name.getText().toString();
                            playerNames.add(playerName);
                            name.setText("");
                        }
//                        playerNames.add("a");
//                        playerNames.add("b");
//                        playerNames.add("c");
//                        playerNames.add("d");
//                        playerNames.add("a");
//                        playerNames.add("b");
//                        playerNames.add("c");
//                        playerNames.add("d");
//                        playerNames.add("a");
//                        playerNames.add("b");
//                        playerNames.add("c");
//                        playerNames.add("d");
                        if(playerNames.size()>1) {
                            for(int i=0; i<playerNames.size(); i++) {
                                Log.d(TAG,"player_"+i+": "+ playerNames.get(i));
                            }
                            orderPlayers();
                        }else
                            Toast.makeText(getContext(), "Please Provide Atleast Two Names!", Toast.LENGTH_SHORT).show();

                        nameInputDialog.dismiss();
                       // playerNames.clear();
                    }
                });
            }
        });
        nameInputDialog.show();
    }

    private void toastIt(String msg) {
        Toast.makeText(getActivity().getBaseContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResume() {
        super.onResume();
        adController.getBannerAd().resume();
    }

    private void orderPlayers() {

        listView = getActivity().findViewById(R.id.playerOrderListView);
        listView.setAdapter(new PlayerOrderListViewAdapter(playerNames, getContext()));

    }


    public void onPlayerOrder(View v) {
        nameInputDialog.show();
    }

}
