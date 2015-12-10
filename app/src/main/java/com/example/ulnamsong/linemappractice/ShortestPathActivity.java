package com.example.ulnamsong.linemappractice;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import java.util.ArrayList;

/**
 * Created by Ulnamsong on 2015-12-06.
 */
public class ShortestPathActivity extends Activity {

    String startStationName;
    String finishStationName;

    String pathname;
    String pathnumber;

    String minStatnName;
    String minStatnNumber;

    private appNetwork appnet = null;

    String shtTime;
    String shtCount;
    String minTime;
    String minCount;

    TextView start;
    TextView finish;

    TextView a1;
    TextView a2;
    TextView a3;
    TextView a4;

    TextView result1;
    TextView result2;

    ArrayList<String> pathStns = new ArrayList<String>();
    ArrayList<String> pathStnNums = new ArrayList<String>();
    String[] pathNums = new String[630];
    String[] pathNames = new String[630];
    String[] pathNums2 = new String[630];
    String[] pathNames2 = new String[630];

    XmlPullParser parser = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shortestpath_result);

        Intent loadData = getIntent();
        startStationName = loadData.getStringExtra("startstn");
        finishStationName = loadData.getStringExtra("finishstn");

        start = (TextView) findViewById(R.id.textView24);
        finish = (TextView) findViewById(R.id.textView25);

        start.setText("출발역 : " + startStationName);
        finish.setText("도착역 : " + finishStationName);

        a1 = (TextView) findViewById(R.id.textView29);
        a2 = (TextView) findViewById(R.id.textView30);
        a3 = (TextView) findViewById(R.id.textView31);
        a4 = (TextView) findViewById(R.id.textView32);

        result1 = (TextView) findViewById(R.id.textView33);
        result2 = (TextView) findViewById(R.id.textView34);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        appnet = new appNetwork(this);

        ConnectivityManager cManager;
        NetworkInfo mobile;
        NetworkInfo wifi;

        cManager=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        mobile = cManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        wifi = cManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        if(mobile.isConnected() || wifi.isConnected()){
        } else {
            AlertDialog.Builder alert = new AlertDialog.Builder(ShortestPathActivity.this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
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

        String urlStr = "http://swopenapi.seoul.go.kr/api/subway/50756a574e67796438374377456b5a/xml/shortestRoute/0/5/";
        URL url = null;

        try {

            String tempStr = startStationName + "/" + finishStationName;
            String query1 = URLEncoder.encode(startStationName, "utf-8");
            String query2 = URLEncoder.encode(finishStationName, "utf-8");
            urlStr = urlStr + query1 + "/" + query2;
            url = new URL(urlStr);
            Log.e("00", "00");
            InputStream in = url.openStream();
            Log.e("01", "01");
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            parser = factory.newPullParser();
            parser.setInput(in, "utf-8");
            Log.e("urlStr", urlStr);

            int eventType = parser.getEventType();
            boolean isItemTag = false;
            String tagName = "";

            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG) {
                    tagName = parser.getName();

                } else if (eventType == XmlPullParser.TEXT) {
                    if (tagName.equals("shtStatnId")) {
                        pathnumber = parser.getText();
                    } else if(tagName.equals("shtStatnNm")) {
                        pathname = parser.getText();
                    } else if(tagName.equals("minStatnId")) {
                        minStatnNumber = parser.getText();
                    } else if(tagName.equals("minStatnNm")) {
                        minStatnName = parser.getText();
                    } else if(tagName.equals("shtStatnCnt")) {
                        shtCount = parser.getText();
                    } else if(tagName.equals("shtTravelTm")) {
                        shtTime = parser.getText();
                    } else if(tagName.equals("minStatnCnt")) {
                        minCount = parser.getText();
                    } else if(tagName.equals("minTravelTm")) {
                        minTime = parser.getText();
                    }

                } else if (eventType == XmlPullParser.END_TAG) {

                }
                eventType = parser.next();
            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        int time1 = Integer.parseInt(shtTime);
        int time2 = Integer.parseInt(minTime);

        if(time1 > 60) {
            int hour = time1 / 60;
            int min = time1 % 60;
            String time1_real = hour + "시간 " + min;
            shtTime = time1_real;
        }

        if(time2 > 60) {
            int hour = time2 / 60;
            int min = time2 % 60;
            String time2_real = hour + "시간 " + min;
            minTime = time2_real;
        }

        a1.setText("경유 역 개수 : " + shtCount + "회");
        a2.setText("소요 시간 : " + shtTime + "분");
        a3.setText("경유 역 개수 : " + minCount + "회");
        a4.setText("소요 시간 : " + minTime + "분");

        //Route Calculation
        for(int i = 0; i <= Integer.parseInt(shtCount); ++i) {
            pathNums[i] = pathnumber.split(",")[i];
            pathNames[i] = pathname.split(" ,")[i];

            Log.e("array : ", pathNums[i] + ", " + pathNames[i]);
        }
        int rright = Integer.parseInt(pathNums[0]);
        int ttt = (rright / 1000000) % 1000;
        String sstn;
        if(ttt == 75) {
            sstn = "분당선";
        } else if(ttt == 65) {
            sstn = "공항철도";
        } else if(ttt == 77) {
            sstn = "신분당선";
        } else if(ttt == 67) {
            sstn = "경춘선";
        } else if(ttt == 61 || ttt == 63) {
            sstn = "경의중앙선";
        } else if(ttt == 69){
            sstn = "인천1호선";
        } else {
            sstn = ttt + "호선";
        }
        result1.append(pathNames[0] + "역에서 " + sstn +" 탑승\n");

        // Transfer Path Calculation
        // Author Ulnamsong(Taein Kim)
        for(int i = 0; i < Integer.parseInt(shtCount) - 1; ++i) {
            pathNums[i+1].replace("                         ", "");
            int left = Integer.parseInt(pathNums[i]);
            int right = Integer.parseInt(pathNums[i+1]);
            int t_left = left / 1000000;
            int t_right = right / 1000000;
            String stn = "";
            if(t_left != t_right) {
                int tt = t_right % 1000;
                if(tt == 75) {
                    stn = "분당선";
                } else if(tt == 65) {
                    stn = "공항철도";
                } else if(tt == 77) {
                    stn = "신분당선";
                } else if(tt == 67) {
                    stn = "경춘선";
                } else if(tt == 61 || tt == 63) {
                    stn = "경의중앙선";
                } else if(tt == 69){
                    stn = "인천1호선";
                } else {
                    stn = tt + "호선";
                }
                Log.e("diff", "transfer to line " + stn + " at the " + pathNames[i+1] + ".");
                result1.append(pathNames[i+1]+"" + "역에서 " + stn + "으로 환승\n");
            }
        }


        //Route Calculation
        for(int i = 0; i < Integer.parseInt(minCount); ++i) {
            pathNums2[i] = minStatnNumber.split(",")[i];
            pathNames2[i] = minStatnName.split(" ,")[i];

            Log.e("array : ", pathNums2[i] + ", " + pathNames2[i]);
        }

        int rrright = Integer.parseInt(pathNums2[0]);
        int tttt = (rrright / 1000000) % 1000;
        String ssstn;
        if(tttt == 75) {
            ssstn = "분당선";
        } else if(tttt == 65) {
            ssstn = "공항철도";
        } else if(tttt == 77) {
            ssstn = "신분당선";
        } else if(tttt == 67) {
            ssstn = "경춘선";
        } else if(tttt == 61 || tttt == 63) {
            ssstn = "경의중앙선";
        } else if(tttt == 69){
            ssstn = "인천1호선";
        } else {
            ssstn = tttt + "호선";
        }
        result2.append(pathNames2[0] + "역에서 " + ssstn +" 탑승\n");


        // Transfer Path Calculation
        // Author Ulnamsong(Taein Kim)
        for(int i = 0; i < Integer.parseInt(minCount) - 1; ++i) {
            pathNums2[i+1].replace("                         ", "");
            int left = Integer.parseInt(pathNums2[i]);
            int right = Integer.parseInt(pathNums2[i+1]);
            int t_left = left / 1000000;
            int t_right = right / 1000000;
            String stn = "";
            if(t_left != t_right) {
                int ttttt = t_right % 1000;
                if(ttttt == 75) {
                    stn = "분당선";
                } else if(ttttt == 65) {
                    stn = "공항철도";
                } else if(ttttt == 77) {
                    stn = "신분당선";
                } else if(ttttt == 67) {
                    stn = "경춘선";
                } else if(ttttt == 61 || ttttt == 63) {
                    stn = "경의중앙선";
                } else if(ttttt == 69){
                    stn = "인천1호선";
                } else {
                    stn = ttttt + "호선";
                }
                Log.e("diff", "transfer to line " + stn + " at the " + pathNames2[i+1] + ".");
                result2.append(pathNames2[i+1]+"" + "역에서 " + stn + "으로 환승\n");
            }
        }


    }
}
