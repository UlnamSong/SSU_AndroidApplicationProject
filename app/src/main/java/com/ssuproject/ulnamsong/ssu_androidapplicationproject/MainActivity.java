package com.ssuproject.ulnamsong.ssu_androidapplicationproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;

/**
 * Created by Ulnamsong (Taein Kim) on 2015-11-27.
 */

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // After 2 Seconds, Activity Changed.
        Handler mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(MainActivity.this, MenuActivity.class);
                startActivity(i);
                finish();
            }
        }, 2000); // 2secs


    }


}
