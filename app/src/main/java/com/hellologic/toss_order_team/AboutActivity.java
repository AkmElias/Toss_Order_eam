package com.hellologic.toss_order_team;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class AboutActivity extends AppCompatActivity {

    private AdController adController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Button contact = (Button) findViewById(R.id.contact_us);
        final View view = (View) ((ViewGroup) this
                .findViewById(android.R.id.content)).getChildAt(0);


        adController = new AdController(AboutActivity.this, this, view,  AdController.BANNER_STANDERD);


        contact.setText(Html.fromHtml("<a href=\"mailto:hellologics@gmail.com\">Contact Us</a>"));
        //contact.setTextColor(0xFFFFFF);
        contact.setMovementMethod(LinkMovementMethod.getInstance());
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Log.d(TAG, "item selected");
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        adController.getBannerAd().resume();
    }

}