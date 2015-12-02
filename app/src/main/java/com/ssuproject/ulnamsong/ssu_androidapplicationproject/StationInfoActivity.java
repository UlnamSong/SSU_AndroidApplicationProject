package com.ssuproject.ulnamsong.ssu_androidapplicationproject;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Ulnamsong on 2015-12-02.
 */
public class StationInfoActivity extends Activity {
    ImageView trainPic1;
    ImageView trainPic2;

    TextView stationName;
    TextView stationNum;
    Typeface typeface;
    Typeface typefacebold;
    TextView nextStation;
    TextView prevStation;
    TextView littleLineNum;
    TextView lArrow, rArrow;
    TextView refresh;
    TextView nowTrain1, nowTrain2, nextTrain1, nextTrain2;
    TextView nn11, nn12, nn21, nn22;

    int stationIndex;
    String station_Name;
    String message1;
    String message2;

    Button timetable;
    Button traininfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station_info);

        typeface = Typeface.createFromAsset(getAssets(), "NanumBarunGothic.mp3");
        typefacebold = Typeface.createFromAsset(getAssets(), "NanumBarunGothicBold.mp3");

        //setTheme(android.R.style.Theme_Translucent_NoTitleBar);

        stationName = (TextView) findViewById(R.id.textView);
        stationNum = (TextView) findViewById(R.id.textView4);
        nextStation = (TextView) findViewById(R.id.nextStn);
        prevStation = (TextView) findViewById(R.id.prevStn);
        littleLineNum = (TextView) findViewById(R.id.littleLine);

        //열차 그림
        trainPic1 = (ImageView) findViewById(R.id.train1);
        trainPic2 = (ImageView) findViewById(R.id.train2);

        lArrow = (TextView) findViewById(R.id.leftArrow);
        rArrow = (TextView) findViewById(R.id.rightArrow);
        refresh = (TextView) findViewById(R.id.refresh);
        nowTrain1 = (TextView) findViewById(R.id.nTrain1);
        nowTrain2 = (TextView) findViewById(R.id.nTrain2);
        nextTrain1 = (TextView) findViewById(R.id.nnTrain1);
        nextTrain2 = (TextView) findViewById(R.id.nnTrain2);
        nn11 = (TextView) findViewById(R.id.nn11);
        nn12 = (TextView) findViewById(R.id.nn12);
        nn21 = (TextView) findViewById(R.id.nn21);
        nn22 = (TextView) findViewById(R.id.nn22);

        timetable = (Button) findViewById(R.id.button);
        traininfo = (Button) findViewById(R.id.button2);


        setFontNormal(stationName);
        setFontNormal(stationNum);
        setFontNormal(nextStation);
        setFontNormal(prevStation);
        setFontNormal(littleLineNum);
        setFontNormal(lArrow);
        setFontNormal(rArrow);
        setFontNormal(refresh);
        setFontNormal(nn11);
        setFontNormal(nn12);
        setFontNormal(nn21);
        setFontNormal(nn22);
        setFontBold(nowTrain1);
        setFontBold(nowTrain2);
        setFontBold(nextTrain1);
        setFontBold(nextTrain2);

        timetable.setTypeface(typefacebold, Typeface.BOLD);
        traininfo.setTypeface(typefacebold, Typeface.BOLD);


        trainPic1.setVisibility(View.INVISIBLE);
        trainPic2.setVisibility(View.VISIBLE);

    }

    private void setFontNormal(TextView view) {
        view.setTypeface(typeface);
    }

    private void setFontBold(TextView view) {
        view.setTypeface(typefacebold, Typeface.BOLD);
    }
}
