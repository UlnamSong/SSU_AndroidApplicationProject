package com.example.ulnamsong.linemappractice;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

/**
 * Logo Activity class.
 * Author Ulnamsong ( Taein Kim )
 */
public class LogoActivity extends Activity {

    TextView textview;
    TextView textview2;

    Typeface typeface;
    Typeface typefacebold;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);

        textview = (TextView) findViewById(R.id.textView2);
        textview2 = (TextView) findViewById(R.id.textView3);

        //To use Custom Font, we must define typefaces.
        typeface = Typeface.createFromAsset(getAssets(), "NanumBarunGothic.mp3");
        typefacebold = Typeface.createFromAsset(getAssets(), "NanumBarunGothicBold.mp3");

        setFontNormal(textview);
        setFontBold(textview2);

        // After 2 Seconds, Activity Changed.
        Handler mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ConnectivityManager cManager;
                NetworkInfo mobile;
                NetworkInfo wifi;

                cManager=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
                mobile = cManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
                wifi = cManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

                if(mobile.isConnected() || wifi.isConnected()){
                    Intent i = new Intent(LogoActivity.this, MenuActivity.class);
                    startActivity(i);
                    finish();
                } else {
                    AlertDialog.Builder alert = new AlertDialog.Builder(LogoActivity.this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
                    alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            finish();
                            android.os.Process.killProcess(android.os.Process.myPid());
                            System.exit(0);
                        }
                    });
                    alert.setMessage("네트워크 연결을 확인하세요.");
                    alert.setTitle("네트워크 연결 확인");
                    alert.show();
                }


            }
        }, 2000); // 2secs

    }

    //Method for Set Custom Font
    private void setFontNormal(TextView view) {
        view.setTypeface(typeface);
    }
    private void setFontBold(TextView view) {
        view.setTypeface(typefacebold, Typeface.BOLD);
    }
}
