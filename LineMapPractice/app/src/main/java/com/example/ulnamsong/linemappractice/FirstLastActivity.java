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
import android.os.StrictMode;
import android.util.Log;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by Ulnamsong on 2015-12-03.
 */
public class FirstLastActivity extends Activity {

    XmlPullParser parser = null;

    Typeface typeface;
    Typeface typefacebold;

    TextView tview1;
    TextView tview2;

    TextView up, up_first, up_last;
    TextView down, down_first, down_last;
    TextView tview;
    TextView ttview;
    TextView line;
    TextView tview2_1;
    TextView tview2_2;
    TextView tview2_3;
    TextView tview3_1;
    TextView tview3_2;
    TextView tview3_3;

    TextView c1;
    TextView c2;
    TextView c3;
    TextView c4;
    TextView c5;
    TextView c6;
    TextView c7;
    TextView c8;

    String upstn;
    String downstn;

    String[] upfirstTime = new String[13];
    String[] uplastTime = new String[13];
    String[] downfirstTime = new String[13];
    String[] downlastTime = new String[13];

    appNetwork appnet = null;
    String currentStation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firstlast);

        appnet = new appNetwork(this);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        tview1 = (TextView) findViewById(R.id.tv1);
        tview2 = (TextView) findViewById(R.id.tv2);
        tview = (TextView) findViewById(R.id.textView6);
        ttview = (TextView) findViewById(R.id.textView7);

        tview2_1 = (TextView) findViewById(R.id.textView8);
        tview2_2 = (TextView) findViewById(R.id.textView9);
        tview2_3 = (TextView) findViewById(R.id.textView10);
        tview3_1 = (TextView) findViewById(R.id.textView15);
        tview3_2 = (TextView) findViewById(R.id.textView16);
        tview3_3 = (TextView) findViewById(R.id.textView17);

        c1 = (TextView) findViewById(R.id.textView11);
        c2 = (TextView) findViewById(R.id.textView12);
        c3 = (TextView) findViewById(R.id.textView13);
        c4 = (TextView) findViewById(R.id.textView14);
        c5 = (TextView) findViewById(R.id.textView18);
        c6 = (TextView) findViewById(R.id.textView19);
        c7 = (TextView) findViewById(R.id.textView20);
        c8 = (TextView) findViewById(R.id.textView21);


        // First & Last Train Time Information View
        up = (TextView) findViewById(R.id.up);
        up_first = (TextView) findViewById(R.id.upfirst);
        up_last = (TextView) findViewById(R.id.uplast);
        down = (TextView) findViewById(R.id.down);
        down_first = (TextView) findViewById(R.id.downfirst);
        down_last = (TextView) findViewById(R.id.downlast);

        // Load Station Information from Main Activity
        Intent inte = getIntent();
        final String station_number = inte.getStringExtra("stationNumber");
        final String station_name = inte.getStringExtra("stationName");
        final String line_num = inte.getStringExtra("lineNumber");
        final int day = inte.getIntExtra("day", 1);
        final String upstnname = inte.getStringExtra("upstnname");
        final String downstnname = inte.getStringExtra("downstnname");
        final String origincode = inte.getStringExtra("originstncode");

        int count = 0;
        boolean flag = true;
        boolean flag1 = true;
        boolean flag2 = true;
        boolean flag3 = true;

        up.setText(upstnname.replace("()", ""));
        down.setText(downstnname.replace("()", ""));
        tview2_2.setText(upstnname.replace("()", ""));
        tview2_3.setText(downstnname.replace("()", ""));
        tview3_2.setText(upstnname.replace("()", ""));
        tview3_3.setText(downstnname.replace("()", ""));


        ConnectivityManager cManager;
        NetworkInfo mobile;
        NetworkInfo wifi;

        cManager=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        mobile = cManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        wifi = cManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        if(mobile.isConnected() || wifi.isConnected()){
        } else {
            AlertDialog.Builder alert = new AlertDialog.Builder(FirstLastActivity.this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
            alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    finish();
                }
            });
            alert.setMessage("네트워크 연결을 확인하세요.");
            alert.setTitle("네트워크 연결 확인");
            alert.show();
        }

        for(int j = 1; j < 4; ++j) {
            for (int i = 1; i < 3; ++i) {
                //String urlStr = "http://swopenAPI.seoul.go.kr/api/subway/sample/xml/realtimeStationArrival/1/5/숭실대입구(살피재)/";
                Log.i("Test", "log test222");
                //new XmlTask().execute();

                String urlStr = "http://openAPI.seoul.go.kr:8088/664f6257666779643531684547784e/xml/SearchFirstAndLastTrainbyIDService/1/5/";
                String station = 738 + "";
                URL url = null;
                try {
                    String tempsn = station;
                    tempsn = URLEncoder.encode(tempsn, "utf-8");

                    //주소에 추가해야하는 정보 : 호선/요일/상하행선/전철역코드
                    //0" + station_number
                    urlStr = urlStr + line_num + "/" + j + "/" + i + "/" + origincode;
                    Log.e("URL String : ", urlStr);
                    url = new URL(urlStr);

                    Log.e("count, i : ", count + ", " + i);

                    InputStream in = url.openStream();

                    XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                    parser = factory.newPullParser();

                    //test용
                    //parser = getResources().getXml(R.xml.subway);

                    parser.setInput(in, "utf-8");

                    Log.i("urlStr", urlStr);

                    int eventType = parser.getEventType();
                    boolean isItemTag = false;
                    String tagName = "";

                    while (eventType != XmlPullParser.END_DOCUMENT) {
                        if (eventType == XmlPullParser.START_TAG) {
                            tagName = parser.getName();

                        } else if (eventType == XmlPullParser.TEXT) {
                            if (tagName.equals("FIRST_TIME")) {
                                Log.e("blabla", "blablabla");
                                if(i == 1) {
                                    if(flag) {
                                        upfirstTime[i + j + count] = "첫차 : " + parser.getText();
                                        flag = false;
                                    }
                                    //Log.e("upfirstTime : ", upfirstTime[i + j + count] + ", " + i + ", " + j + ", " + count);
                                } else {
                                    Log.e("blabla22", "blabla22");
                                    if(flag1) {
                                        downfirstTime[i + j + count] = "첫차 : " + parser.getText();
                                        flag1 = false;
                                    }
                                    //Log.e("downfirstTime : ", downfirstTime[i + j + count] + ", " + i + ", " + j + ", " + count);
                                }
                            } else if (tagName.equals("LAST_TIME")) {
                                if(i == 1) {
                                    if(flag2) {
                                        uplastTime[i + j + count] = "막차 : " + parser.getText();
                                        flag2 = false;
                                    }
                                    //Log.e("uplastTime : ", uplastTime[i + j + count] + ", " + i + ", " + j + ", " + count);
                                } else {
                                    Log.e("blabla33", "blabla33");
                                    if(flag3) {
                                        downlastTime[i + j + count] = "막차 : " + parser.getText();
                                        flag3 = false;
                                    }
                                    //Log.e("downlastTime : ", downlastTime[i + j + count] + ", " + i + ", " + j + ", " + count);
                                }
                            }

                        } else if (eventType == XmlPullParser.END_TAG) {

                        }
                        Log.e("huhehe", "huhehe");
                        eventType = parser.next();
                    }

                    // 임시로 사용하지 않음
                    //for (int i = 0; i < 4; i++) {
                    //    if(hangStr.equals("정보 없음") || hangStr == null) {
        //
                    //    } else {
                    //        hangStr[i] += "행";
                    //    }
                    //}


                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                flag = true;
                flag1 = true;
                flag2 = true;
                flag3 = true;
            }
            flag = true;
            flag1 = true;
            flag2 = true;
            flag3 = true;
            count++;
        }
        //Log.e("upfirstTime[1+1+0] : ", upfirstTime[1 + 1 + 0]);
        //Log.e("upfirstTime[0] : ", upfirstTime[1 + 2 + 0]);
        //Log.e("upfirstTime[0] : ", upfirstTime[2 + 1 + 0]);
        //Log.e("upfirstTime[0] : ", upfirstTime[2 + 2 + 0]);
        //Log.e("upfirstTime[0] : ", upfirstTime[3 + 1 + 0]);
        //Log.e("upfirstTime[0] : ", upfirstTime[3 + 2 + 0]);
        // Set First/Last Train Information into Textview
        up_first.setText(upfirstTime[2]);
        up_last.setText(uplastTime[2]);
        down_first.setText(downfirstTime[3]);
        down_last.setText(downlastTime[3]);
        c1.setText(upfirstTime[4]);
        c3.setText(uplastTime[4]);
        c2.setText(downfirstTime[5]);
        c4.setText(downlastTime[5]);
        c5.setText(upfirstTime[6]);
        c7.setText(uplastTime[6]);
        c6.setText(downfirstTime[7]);
        c8.setText(downlastTime[7]);

        // 4, 4, 5, 5, 6, 6, 7, 7

        typeface = Typeface.createFromAsset(getAssets(), "NanumBarunGothic.mp3");
        typefacebold = Typeface.createFromAsset(getAssets(), "NanumBarunGothicBold.mp3");

        currentStation = station_name;
        tview.setText(line_num + "호선");
        tview2.setText(currentStation);

        setFontBold(tview1);
        setFontBold(ttview);
        setFontNormal(tview2);

        setFontBold(up);
        setFontBold(down);
        setFontBold(tview);
        setFontBold(tview2_1);
        setFontBold(tview2_2);
        setFontBold(tview2_3);
        setFontBold(tview3_1);
        setFontBold(tview3_2);
        setFontBold(tview3_3);

        setFontNormal(up_first);
        setFontNormal(up_last);
        setFontNormal(down_first);
        setFontNormal(down_last);

        setFontNormal(c1);
        setFontNormal(c2);
        setFontNormal(c3);
        setFontNormal(c4);
        setFontNormal(c5);
        setFontNormal(c6);
        setFontNormal(c7);
        setFontNormal(c8);

    }

    private void setFontNormal(TextView view) {
        view.setTypeface(typeface);
    }
    private void setFontBold(TextView view) {
        view.setTypeface(typefacebold, Typeface.BOLD);
    }
}
