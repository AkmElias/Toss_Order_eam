package com.hellologic.toss_order_team.fragments;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.hellologic.toss_order_team.AdController;
import com.hellologic.toss_order_team.adapter.CreateTeamListViewAdapter;
import com.hellologic.toss_order_team.R;

import java.util.ArrayList;
import java.util.Collections;


/**
 * A simple {@link Fragment} subclass.
 */
public class TeamFragment extends Fragment {
    private AlertDialog nameInputDialog;
    private ArrayList<String> playerNames;
    private static String TAG = "my_tag";
    private Button makePopUpButton;
    CreateTeamListViewAdapter teamA;
    CreateTeamListViewAdapter teamB;
    AdController adController;

    public TeamFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_team, container, false);
        playerNames = new ArrayList<>();
        teamA = new CreateTeamListViewAdapter(new ArrayList<String>(), getContext(),Color.RED);
        teamB = new CreateTeamListViewAdapter(new ArrayList<String>(), getContext(),Color.WHITE);

        //playerName = (TextView)v.findViewById(R.id.player_name);
        buildDialog(inflater);

        makePopUpButton = v.findViewById(R.id.popUpButton);
        setPopUpButtonOnClickListener();
        adController = new AdController(getActivity(), getContext(),v,  AdController.BANNER_STANDERD);

        return v;
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
                            for(int i=0; i<playerNames.size(); i++)
                                Log.d(TAG,"player_"+i+": "+ playerNames.get(i));
                            makeTeam();
                        }else
                            toastIt("Please Provide Atleast Two Names!");

                        nameInputDialog.dismiss();
                    }
                });
            }
        });
        nameInputDialog.show();
    }

    private void toastIt(String msg) {
        Toast.makeText(getActivity().getBaseContext(), msg, Toast.LENGTH_SHORT).show();
    }

    private void makeTeam() {

        String extraPlayer = null;
        int playerCnt = playerNames.size();
        Collections.shuffle(playerNames);

        if(playerCnt%2!= 0) {
            extraPlayer = playerNames.get(playerCnt-1);
            playerNames.remove(playerCnt-1);
            playerCnt--;
            TextView extraPlayerTextView = getActivity().findViewById(R.id.extraPlayer);
            extraPlayerTextView.setText(extraPlayer);
        }
            teamA.setArrayList(new ArrayList<String>(playerNames.subList(0, playerCnt/2)));
        ListView listView = getActivity().findViewById(R.id.team_a_listview);

            listView.setAdapter(teamA);
       // playerName.setTextColor(Color.RED);

            teamB.setArrayList(new ArrayList<String>(playerNames.subList(playerCnt/2, playerCnt)));
            listView = getActivity().findViewById(R.id.team_b_listview);

            listView.setAdapter(teamB);
       // playerName.setTextColor(Color.BLUE);


    }

    private void setPopUpButtonOnClickListener() {
        makePopUpButton.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View view) {
                nameInputDialog.show();
                clearAll();
            }
        });
    }

    public void clearAll() {
        playerNames.clear();
        teamA.clearArrayList();
        teamB.clearArrayList();
        TextView extraPlayerTextView = getActivity().findViewById(R.id.extraPlayer);
        extraPlayerTextView.setText("");
    }

    @Override
    public void onResume() {
        super.onResume();
        adController.getBannerAd().resume();
    }

}
