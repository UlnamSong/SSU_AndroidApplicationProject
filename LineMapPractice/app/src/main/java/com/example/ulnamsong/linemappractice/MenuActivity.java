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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.InputStreamReader;

/**
 * Created by Ulnamsong on 2015-12-04.
 */
public class MenuActivity extends Activity {
    Typeface typeface;
    Typeface typefacebold;

    TextView subtitle;
    TextView title;

    ImageButton stationsearch;
    ImageButton shortestpath;
    ImageButton timetable;

    appNetwork appnet = null;

    private BackPressCloseSystem backPressCloseSystem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        appnet = new appNetwork(this);

        ConnectivityManager cManager;
        NetworkInfo mobile;
        NetworkInfo wifi;

        cManager=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        mobile = cManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        wifi = cManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        if(mobile.isConnected() || wifi.isConnected()){
        } else {
            AlertDialog.Builder alert = new AlertDialog.Builder(MenuActivity.this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
            alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            alert.setMessage("네트워크 연결을 확인하세요.");
            alert.setTitle("네트워크 연결 확인");
            alert.show();
        }
        backPressCloseSystem = new BackPressCloseSystem(this);

        typeface = Typeface.createFromAsset(getAssets(), "NanumBarunGothic.mp3");
        typefacebold = Typeface.createFromAsset(getAssets(), "NanumBarunGothicBold.mp3");
        stationsearch = (ImageButton) findViewById(R.id.imageButton5);
        shortestpath = (ImageButton) findViewById(R.id.imageButton3);
        timetable = (ImageButton) findViewById(R.id.imageButton4);

        timetable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, TimatableActivity.class);
                intent.putExtra("sn", "역 이름");
                startActivity(intent);
            }
        });

        shortestpath.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, StartSearch.class);
                startActivity(intent);
            }
        });

        stationsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });

        ImageButton favoriteBtn = (ImageButton)findViewById(R.id.favorite);
        favoriteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    FileInputStream r_fileIn = openFileInput("favorite_list.txt");
                    InputStreamReader r_InputRead = new InputStreamReader(r_fileIn);
                    char[] inputBuffer = new char[1000];
                    String s = "";
                    int charRead;
                    while ((charRead = r_InputRead.read(inputBuffer)) > 0) {
                        String readstring = String.copyValueOf(inputBuffer, 0, charRead);
                        s += readstring;
                    }

                    //테스트용 토스트
                    Log.e("Read", s);
                    //Toast.makeText(getBaseContext(), s, Toast.LENGTH_SHORT).show();
                    String[] tempArr;
                    //★로 나뉘어져있어서 ★로 split한다. writeRecentSearchingList 함수 참고
                    tempArr = s.split("★");
                    int tempSize = tempArr.length;


                    r_InputRead.close();

                    Intent intent = new Intent(MenuActivity.this, FavoriteActivity.class);
                    startActivity(intent);




                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        backPressCloseSystem.onBackPressed();
    }

    // Methods for Custom Font Setting.
    private void setFontNormal(TextView view) {
        view.setTypeface(typeface);
    }
    private void setFontBold(TextView view) {
        view.setTypeface(typefacebold, Typeface.BOLD);
    }
}
