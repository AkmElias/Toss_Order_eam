package com.hellologic.toss_order_team.fragments;


import android.content.SharedPreferences;
import android.content.res.Resources;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;

import com.hellologic.toss_order_team.AdController;
import com.hellologic.toss_order_team.models.Utils;
import com.hellologic.toss_order_team.R;
import com.hellologic.toss_order_team.models.DisplayNextView;
import com.hellologic.toss_order_team.models.Flip3dAnimation;
import com.hellologic.toss_order_team.models.ShakeDetector;


/**
 * A simple {@link Fragment} subclass.
 */
public class TossFragment extends Fragment {

    private ImageView image1;
    private ImageView image2;
    //shake detection..
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private ShakeDetector mShakeDetector;

    private boolean isFirstImage = true;
    private Handler handler;
    private Thread rotationThread;
    private static SharedPreferences sp;
    private boolean soundStatus;
    private AdController adController;

    public TossFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_toss, container, false);
//        SharedPreferences sharedPreferences = find


        image1 =  v.findViewById(R.id.ImageView01);
        image2 =  v.findViewById(R.id.ImageView02);
        image2.setVisibility(View.GONE);
        handler = new Handler();
        sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        adController = new AdController(getActivity(), getContext(),v,  AdController.BANNER_STANDERD);


        image1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if(rotationThread != null && rotationThread.isAlive())
                    return;
                rotateInThread();
            }
        });
        mSensorManager = (SensorManager) getActivity().getSystemService(getActivity().SENSOR_SERVICE);
        mAccelerometer = mSensorManager
                .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mShakeDetector = new ShakeDetector();

        mShakeDetector.setOnShakeListener(new ShakeDetector.OnShakeListener() {

            @Override
            public void onShake(int count) {
                rotateInThread();
            }
        });
        updateSoundChange();
        updateCoinOnCountryChange();

        return v;
    }

    private void rotateInThread() {
        if(soundStatus==true) {
        MediaPlayer mediaPlayer = MediaPlayer.create(getContext(), R.raw.coin_toss);

            mediaPlayer.start();
        }
        final Runnable run = new Runnable() {
            @Override
            public void run() {
                if (isFirstImage) {

                    applyRotation(0, 90);
                    isFirstImage = !isFirstImage;

                } else {
                    applyRotation(0, -90);
                    isFirstImage = !isFirstImage;
                }
            }
        };
        final int count = Utils.getRotationAns();//
        rotationThread = new Thread(new Runnable() {
            @Override
            public void run() {
                int pre=0;
                while(pre<5 || pre>10) {
                    pre =(int) Math.round(Math.random()*10);
                }
                for(int i=0; i<pre+count; i++) {
                    if(i == pre+count-2) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                if(soundStatus==true) {
                                MediaPlayer mediaPlayer = MediaPlayer.create(getContext(), R.raw.coin_drop);

                                    mediaPlayer.start();
                                }

                            }
                        });
                    }
                    handler.post(run);
                    try {
                        Thread.sleep(120);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        rotationThread.start();
    }

    private void applyRotation(float start, float end) {
// Find the center of image

        final float centerX = image1.getWidth() / 2.0f;
        final float centerY = image1.getHeight() / 2.0f;

// Create a new 3D rotation with the supplied parameter
// The animation listener is used to trigger the next animation
        final Flip3dAnimation rotation =
                new Flip3dAnimation(start, end, centerX, centerY);
        rotation.setDuration(2);
        rotation.setFillAfter(true);
        rotation.setInterpolator(new AccelerateInterpolator());
        rotation.setAnimationListener(new DisplayNextView(isFirstImage, image1, image2));

        if (isFirstImage)
        {
            image1.startAnimation(rotation);
        } else {
            image2.startAnimation(rotation);
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        updateCoinOnCountryChange();
        updateSoundChange();
        // Add the following line to register the Session Manager Listener onResume
        mSensorManager.registerListener(mShakeDetector, mAccelerometer,	SensorManager.SENSOR_DELAY_UI);
        adController.getBannerAd().resume();
    }

    @Override
    public void onPause() {
        // Add the following line to unregister the Sensor Manager onPause
        mSensorManager.unregisterListener(mShakeDetector);
        super.onPause();
    }


    public void updateCoinOnCountryChange() {
        String userCountry = sp.getString("key country","-1").toLowerCase();
        userCountry = Utils.removeSpaceFromString(userCountry);

//        Toast.makeText(getContext(), userCountry, Toast.LENGTH_SHORT).show();

        Utils.log("user_country: "+userCountry);

//R.drawable.he
        Resources res = getResources();
        int frontId = res.getIdentifier(userCountry+"_head", "drawable", getActivity().getPackageName());
       // Utils.log("res_id: "+frontId +"main_res_id: "+R.drawable.tail);
        if(frontId==0) {
            frontId = res.getIdentifier("bangladesh_head", "drawable", getActivity().getPackageName());

        }
        image1.setImageResource(frontId);

        int backId = res.getIdentifier(userCountry+"_tail", "drawable", getActivity().getPackageName());
        //Utils.log("res_id: "+frontId +"main_res_id: "+R.drawable.tail);
        if(backId==0) {
            backId = res.getIdentifier("bangladesh_tail", "drawable", getActivity().getPackageName());
        }
        image2.setImageResource(backId);

    }
    public  void updateSoundChange(){

        boolean sound = sp.getBoolean("key sound",false);
        if(sound==true) {
            soundStatus = true;

        }
        else if(sound==false)soundStatus = false;

        Utils.log("sound "+soundStatus);


    }
}
