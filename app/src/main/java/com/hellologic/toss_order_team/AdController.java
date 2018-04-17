package com.hellologic.toss_order_team;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;

public class AdController {
    private static final String TAG = "my_tag";
    private Activity activity;
    Context context;
    public static final String BANNER_STANDERD = "bannerStandered";
    public static final String BANNER_STANDERD_AND_REWARDED = "bannerStanderedAndRewarded";
    public static final String REWARDED_VIDEO = "rewardedVideo";

    private RewardedVideoAd rewardedVideoAd;
    private AdView bannerAd;

    public AdController(Activity activity, Context context,View v,  String intention) {
        this.activity = activity;
        this.context = context;
        if(intention.equals(BANNER_STANDERD)) {
            setBannerAd(v);
        }
        if(intention.equals(BANNER_STANDERD)) {
            setBannerAd(v);
        } else if(intention.equals(REWARDED_VIDEO)) {
            setRewardedVideoAd();
        } else if(intention.equals(BANNER_STANDERD_AND_REWARDED)) {
            setBannerAd(v);
            setRewardedVideoAd();
        }
    }

    private void setRewardedVideoAd() {
        rewardedVideoAd = MobileAds.getRewardedVideoAdInstance(context);
        rewardedVideoAd.setRewardedVideoAdListener(new RewardedVideoAdListener() {
            @Override
            public void onRewarded(RewardItem reward) {
               // Toast.makeText(context, "onRewarded! currency: " + reward.getType() + "  amount: "+ reward.getAmount(), Toast.LENGTH_SHORT).show();
                // Reward the user.
            }

            @Override
            public void onRewardedVideoAdLeftApplication() {
                Toast.makeText(context, "onRewardedVideoAdLeftApplication",
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRewardedVideoAdClosed() {
//                Toast.makeText(MainActivity.this, "onRewardedVideoAdClosed", Toast.LENGTH_SHORT).show();
                checkShowLoadRewardedVideo();
                Log.d(TAG, "rewarded video closed");
            }

            @Override
            public void onRewardedVideoAdFailedToLoad(int errorCode) {
                Log.d(TAG, "rewarded video failed to load");
                checkShowLoadRewardedVideo();
//                Toast.makeText(MainActivity.this, "onRewardedVideoAdFailedToLoad", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRewardedVideoAdLoaded() {
//                Toast.makeText(MainActivity.this, "onRewardedVideoAdLoaded", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRewardedVideoAdOpened() {
//                Toast.makeText(MainActivity.this, "onRewardedVideoAdOpened", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRewardedVideoStarted() {
//                Toast.makeText(MainActivity.this, "onRewardedVideoStarted", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRewardedVideoCompleted() {
//                REWARDED_VIDEO_COMPLETED++;
                Log.d(TAG, "rewarded video completed");
                loadRewardedVideoAd();
//                Toast.makeText(MainActivity.this, "onRewardedVideoCompleted", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public RewardedVideoAd getRewardedVideoAd() {
        return rewardedVideoAd;
    }

    public AdView getBannerAd() {
        return bannerAd;
    }

    private void loadRewardedVideoAd() {
        if(!rewardedVideoAd.isLoaded()) {
            rewardedVideoAd.loadAd("ca-app-pub-3940256099942544/5224354917",
                    new AdRequest.Builder().build());
        }
    }
    public boolean checkShowLoadRewardedVideo() {
        if(!rewardedVideoAd.isLoaded()) {
            loadRewardedVideoAd();
            return false;
        }
        rewardedVideoAd.show();
        return true;
    }
    public boolean checkShowLoadRewardedVideo(Boolean showAd) {
        if(!rewardedVideoAd.isLoaded()) {
            loadRewardedVideoAd();
            return false;
        }
        if(showAd) {
            Log.d("temp", "rewarded video showed for showAd");
            rewardedVideoAd.show();
        }
        return true;
    }

    private void setBannerAd(View v) {
        bannerAd = v.findViewById(R.id.bannerAdView);
        AdRequest adRequest = new AdRequest.Builder().build();
        bannerAd.loadAd(adRequest);

        bannerAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                bannerAd.setVisibility(View.VISIBLE);
                Log.d(TAG, "banner ad loaded");
                // Code to be executed when an ad finishes loading.
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                bannerAd.setVisibility(View.GONE);
                Log.d(TAG, "banner ad loaded, error: "+ errorCode);
            }

            @Override
            public void onAdOpened() {
                AdRequest adRequest = new AdRequest.Builder().build();
                bannerAd.loadAd(adRequest);

            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when when the user is about to return
                // to the app after tapping on an ad.
            }
        });
    }

}
