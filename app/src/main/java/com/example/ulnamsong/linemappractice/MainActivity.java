package com.example.ulnamsong.linemappractice;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/*
 *  MainActivity Class ( Station Information )
 *  Author : Taein Kim ( Ulnamsong )
 */
public class MainActivity extends Activity {

    appNetwork appnet = null;
    XmlPullParser parser = null;
    //지하철 관련 정보 변수들
    TextView station1, station2;
    TextView[] textTime = new TextView[4];
    TextView[] trainNum = new TextView[4];
    TextView[] hang = new TextView[4];
    TextView[] orientation = new TextView[2];

    String[] time = new String[21];
    String[] time1 = new String[3];
    String[] time2 = new String[3];
    String[] num = new String[21];
    String[] hangStr = new String[21];
    String[] hangStr1 = new String[3];
    String[] hangStr2 = new String[3];
    String[] ori = new String[21];
    String[] ori2 = new String[3];
    String[] ori22 = new String[3];
    String[] subwayId = new String[21];

    String nextStationName;
    String prevStationName;
    String[] updn = new String[21];
    String[] updn1 = new String[3];
    String[] updn2 = new String[3];

    ImageView trainPic1;
    ImageView trainPic2;
    EditText inputStnName;

    Typeface typeface;
    Typeface typefacebold;

    TextView stationName, stationNum;
    TextView nextStation, prevStation;
    TextView littleLineNum;
    TextView lArrow, rArrow, refresh;
    TextView nowTrain1, nowTrain2, nextTrain1, nextTrain2;

    TextView trainN1, trainN2;

    Button refreshBtn;
    Button kakaoshareBtn;
    Button favoriteBtn;

    String stationIndex;

    String curStnName;
    String nextStnName;
    String prevStnName;
    String LineName;
    String LineInfo;

    // Train Arrival Information at current station.
    String leftDirNowInfo;
    String leftDirNextInfo;
    String rightDirNowInfo;
    String rightDirNextInfo;

    Button timetable;
    Button traininfo;

    EditText editName;

    static RelativeLayout.LayoutParams mparam;
    static RelativeLayout.LayoutParams mparam2;
    static RelativeLayout.LayoutParams mparam3;
    static RelativeLayout.LayoutParams mparam4;

    boolean isLastTrain = false;
    ImageView linebox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        appnet = new appNetwork(this);

        trainN1 = (TextView) findViewById(R.id.trainnum1);
        trainN2 = (TextView) findViewById(R.id.trainnum2);

        Intent intent = getIntent();
        final String sn = intent.getStringExtra("sn");
        final int lineindex = intent.getIntExtra("lineindex", 0);
        final String linenumber = intent.getStringExtra("linenumber");
        final String stationnumber = intent.getStringExtra("stationnumber");
        final String origin = intent.getStringExtra("originstncode");

        LineName = linenumber + "";

        //Station Index Test
        stationIndex = stationnumber;

        typeface = Typeface.createFromAsset(getAssets(), "NanumBarunGothic.mp3");
        typefacebold = Typeface.createFromAsset(getAssets(), "NanumBarunGothicBold.mp3");

        editName = (EditText) findViewById(R.id.editText);

        inputStnName = (EditText) findViewById(R.id.editText);
        refreshBtn = (Button) findViewById(R.id.refresh);
        kakaoshareBtn = (Button) findViewById(R.id.kakaoshare);
        favoriteBtn = (Button) findViewById(R.id.favorite);

        //setTheme(android.R.style.Theme_Translucent_NoTitleBar);

        stationName = (TextView) findViewById(R.id.textView); //Station Name
        stationNum = (TextView) findViewById(R.id.textView4); //Station Number
        nextStation = (TextView) findViewById(R.id.nextStn); // Next Station Name
        prevStation = (TextView) findViewById(R.id.prevStn); // Prev Station Name
        littleLineNum = (TextView) findViewById(R.id.littleLine);

        //노선 별 사각형 선택을 위한 객체 형성
        linebox = (ImageView) findViewById(R.id.imageView2);

        //열차 그림
        trainPic1 = (ImageView) findViewById(R.id.train1);
        trainPic2 = (ImageView) findViewById(R.id.train2);

        trainPic1.setVisibility(View.VISIBLE);
        trainPic2.setVisibility(View.VISIBLE);

        lArrow = (TextView) findViewById(R.id.leftArrow);
        rArrow = (TextView) findViewById(R.id.rightArrow);
        refresh = (TextView) findViewById(R.id.refresh);
        nowTrain1 = (TextView) findViewById(R.id.nTrain1);
        nowTrain2 = (TextView) findViewById(R.id.nTrain2);
        nextTrain1 = (TextView) findViewById(R.id.nnTrain1);
        nextTrain2 = (TextView) findViewById(R.id.nnTrain2);

        //nn11 = (TextView) findViewById(R.id.nn11);
        //nn12 = (TextView) findViewById(R.id.nn12);
        //nn21 = (TextView) findViewById(R.id.nn21);
        //nn22 = (TextView) findViewById(R.id.nn22);

        textTime[0] = (TextView) findViewById(R.id.nn11);
        textTime[1] = (TextView) findViewById(R.id.nn12);
        textTime[2] = (TextView) findViewById(R.id.nn21);
        textTime[3] = (TextView) findViewById(R.id.nn22);

        hang[0] = (TextView) findViewById(R.id.hang1);
        hang[1] = (TextView) findViewById(R.id.hang2);
        hang[2] = (TextView) findViewById(R.id.hang3);
        hang[3] = (TextView) findViewById(R.id.hang4);

        timetable = (Button) findViewById(R.id.button);
        traininfo = (Button) findViewById(R.id.button2);

        //stationIndex = 738 + "";
        curStnName = sn;
        prevStnName = "이전역";
        nextStnName = "다음역";

        // Image Moving Test
        // Author Taein Kim ( Ulnamsong )
        mparam = (RelativeLayout.LayoutParams)trainPic1.getLayoutParams();
        mparam2 = (RelativeLayout.LayoutParams)trainPic2.getLayoutParams();
        mparam3 = (RelativeLayout.LayoutParams)trainN1.getLayoutParams();
        mparam4 = (RelativeLayout.LayoutParams)trainN2.getLayoutParams();

        //Set Custom Font Normal.
        setFontNormal(stationName);
        setFontNormal(stationNum);
        setFontNormal(nextStation);
        setFontNormal(prevStation);
        setFontNormal(littleLineNum);
        setFontNormal(lArrow);
        setFontNormal(rArrow);
        setFontNormal(refresh);
        setFontNormal(textTime[0]);
        setFontNormal(textTime[1]);
        setFontNormal(textTime[2]);
        setFontNormal(textTime[3]);
        setFontNormal(hang[0]);
        setFontNormal(hang[1]);
        setFontNormal(hang[2]);
        setFontNormal(hang[3]);

        //Set Custom Font Bold.
        setFontBold(nowTrain1);
        setFontBold(nowTrain2);
        setFontBold(nextTrain1);
        setFontBold(nextTrain2);

        ConnectivityManager cManager;
        NetworkInfo mobile;
        NetworkInfo wifi;

        cManager=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        mobile = cManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        wifi = cManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        if(mobile.isConnected() || wifi.isConnected()){
        } else {
            AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
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

        //파싱부분
        //final String urlStr = "http://swopenAPI.seoul.go.kr/api/subway/sample/xml/realtimeStationArrival/1/5/숭실대입구(살피재)/";

        //String urlStr = "http://swopenAPI.seoul.go.kr/api/subway/sample/xml/realtimeStationArrival/1/5/숭실대입구(살피재)/";
        Log.i("Test", "log test222");
        //new XmlTask().execute();

        String urlStr = "http://swopenAPI.seoul.go.kr/api/subway/585a7571646a796437325965554256/xml/realtimeStationArrival/1/20/";
        String station = "숭실대입구(살피재)";
        URL url = null;
        try {
            String tempsn = sn;
            tempsn = URLEncoder.encode(tempsn, "utf-8");
            urlStr = urlStr + tempsn;
            Log.e("URL : ", urlStr);
            url = new URL(urlStr);

            InputStream in = url.openStream();

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            parser = factory.newPullParser();
            trainPic1.setVisibility(View.INVISIBLE);
            trainPic2.setVisibility(View.INVISIBLE);

            parser.setInput(in, "utf-8");

            int timeNum = 0;
            int subwayIdNum = 0;
            int hangNum = 0;
            int numNum = 0;
            int oriNum = 0;
            int updnNum = 0;
            boolean flag = false;

            int eventType = parser.getEventType();
            boolean isItemTag = false;
            String tagName = "";
            String tempLine = "";

            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG) {
                    tagName = parser.getName();

                } else if (eventType == XmlPullParser.TEXT) {
                    if(tagName.equals("subwayId")) {
                        subwayId[subwayIdNum++] = parser.getText();
                    }else if (tagName.equals("arvlMsg2")) {
                        time[timeNum++] = parser.getText();
                    } else if (tagName.equals("bstatnNm")) {
                        hangStr[hangNum++] = parser.getText();
                    } else if (tagName.equals("btrainNo")) {
                        num[numNum++] = parser.getText();
                    } else if (tagName.equals("trainLineNm")) {
                        ori[oriNum++] = parser.getText();
                    } else if (tagName.equals("updnLine")) {
                        updn[updnNum++] = "(" + parser.getText() + ")";
                    }

                } else if (eventType == XmlPullParser.END_TAG) {

                }
                eventType = parser.next();
            }
            int cnt = 0;
            int cnt1 = 0;

            // Generate 2 vectors for upward, downward data.
            for (int i = 0; i < 20; i++) {
                String[] tempArr;
                String[] tempArr2;
                if(subwayId[i] != null) {
                    String temp = (Integer.parseInt(subwayId[i]) % 1000) + "";
                    if (temp.equals(LineName)) {
                        //상행과 하행을 구분하여 데이터 저장
                        if(updn[i].equals("(상행)") || updn[i].equals("(내선)")) {
                            if (ori[i] != null) {
                                tempArr = ori[i].split(" - ");
                                ori[cnt] = tempArr[0];
                                ori2[cnt] = tempArr[1];
                                tempArr2 = ori2[cnt].split("방면");
                                nextStnName = tempArr2[0];
                                if (tempArr.length > 0) {
                                    if (cnt < 4) {
                                        Log.e("ori[cnt] : ", ori[cnt]);
                                        ori[cnt] = tempArr[1];
                                        Log.e("ori[cnt], cnt : ", ori[cnt] + ", " + cnt);
                                        hang[cnt].setText(ori[cnt]);
                                        if (hangStr == null) {
                                            hangStr1[cnt % 2] = "";
                                        } else {
                                            if(hangStr[i].equals("1")) {
                                                hangStr[i] = "광운대";
                                            } else if(hangStr[i].equals("930")) {
                                                hangStr[i] = "종합운동장";
                                            }
                                            hangStr1[cnt % 2] = hangStr[i] + "행";
                                        }
                                        updn1[cnt % 2] = updn[i];
                                        time1[cnt % 2] = time[i];
                                        Log.e("hangStr1[cnt % 2] : ", hangStr1[cnt % 2]);
                                        if (hangStr1[cnt % 2].contains("(막차)")) {
                                            isLastTrain = true;
                                            time1[cnt % 2] = "열차 정보 없음";
                                            hangStr1[cnt % 2] = "";
                                            updn1[cnt % 2] = "";
                                        } else {
                                            isLastTrain = false;
                                        }
                                        cnt++;
                                    }
                                }
                            } else {
                                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                                alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        finish();
                                    }
                                });
                                alert.setMessage("API 정보 오류입니다.\n(Invalid API Data.)");
                                alert.show();
                                break;
                            }
                        } else {
                            if (ori[i] != null) {
                                tempArr = ori[i].split(" - ");
                                ori[cnt1] = tempArr[0];
                                ori22[cnt1] = tempArr[1];
                                tempArr2 = ori22[cnt1].split("방면");
                                prevStnName = tempArr2[0];
                                if (tempArr.length > 0) {
                                    if (cnt1 < 4) {
                                        Log.e("ori[cnt] : ", ori[cnt]);
                                        ori[cnt1] = tempArr[1];
                                        Log.e("ori[cnt1], cnt1 : ", ori[cnt1] + ", " + cnt1);
                                        hang[cnt1].setText(ori[cnt1]);
                                        if (hangStr == null) {
                                            hangStr2[cnt1] = "";
                                        } else {
                                            if(hangStr[i].equals("1")) {
                                                hangStr[i] = "광운대";
                                            } else if(hangStr[i].equals("930")) {
                                                hangStr[i] = "종합운동장";
                                            } else if(hangStr[i].equals("930 (급행)")) {
                                                hangStr[i] = "종합운동장 (급행)";
                                            }
                                            hangStr2[cnt1 % 2] = hangStr[i] + "행";
                                        }
                                        updn2[cnt1 % 2] = updn[i];
                                        time2[cnt1 % 2] = time[i];
                                        Log.e("hangStr2[cnt1 % 2] : ", hangStr2[cnt1 % 2]);
                                        if (hangStr2[cnt1 % 2].contains("(막차)")) {
                                            isLastTrain = true;
                                            time2[cnt1 % 2] = "열차 정보 없음";
                                            hangStr2[cnt1 % 2] = "";
                                            updn1[cnt1 % 2] = "";
                                        } else {
                                            isLastTrain = false;
                                        }
                                        cnt1++;
                                    }
                                }
                            } else {
                                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                                alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        finish();
                                    }
                                });
                                alert.setMessage("API 정보 오류입니다.\n(Invalid API Data.)");
                                alert.show();
                                break;
                            }
                        }
                    }
                }
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
        for (int i = 0; i < 4; i++) {
            if (i % 2 == 0) {
                if (textTime[0].getText().toString().equals("전역 도착")) {
                    trainPic1.setVisibility(View.VISIBLE);
                    trainN1.setVisibility(View.VISIBLE);
                    mparam.rightMargin = 0;
                    mparam3.rightMargin = 70;
                } else if (textTime[0].getText().toString().equals("전역 출발") || textTime[0].getText().toString().equals(stationName.getText().toString() + " 진입")) {
                    trainPic1.setVisibility(View.VISIBLE);
                    trainN1.setVisibility(View.VISIBLE);
                    mparam.rightMargin = 200;
                    mparam3.rightMargin = 270;
                } else if (textTime[0].getText().toString().equals(stationName.getText().toString() + " 진입")) {
                    trainPic1.setVisibility(View.VISIBLE);
                    trainN1.setVisibility(View.VISIBLE);
                    mparam.rightMargin = 200;
                    mparam3.rightMargin = 270;
                } else if (textTime[0].getText().toString().equals(stationName.getText().toString() + " 도착")) {
                    trainPic1.setVisibility(View.VISIBLE);
                    trainN1.setVisibility(View.VISIBLE);
                    mparam.rightMargin = 450;
                    mparam3.rightMargin = 520;
                } else if(textTime[0].getText().toString().equals(stationName.getText().toString() + " 출발")) {
                    trainPic1.setVisibility(View.INVISIBLE);
                    trainN1.setVisibility(View.INVISIBLE);
                    if (!isLastTrain) {
                        textTime[i].setText("열차 정보 없음");
                    }
                } else {
                    trainPic1.setVisibility(View.INVISIBLE);
                    trainN1.setVisibility(View.INVISIBLE);
                }
            } else if (i % 2 == 1) {
                if (textTime[2].getText().toString().equals("전역 도착")) {
                    trainPic2.setVisibility(View.VISIBLE);
                    trainN2.setVisibility(View.VISIBLE);
                    mparam2.leftMargin = 0;
                    mparam4.leftMargin = 70;
                } else if (textTime[2].getText().toString().equals("전역 출발") || textTime[2].getText().toString().equals(stationName.getText().toString() + " 진입")) {
                    trainPic2.setVisibility(View.VISIBLE);
                    trainN2.setVisibility(View.VISIBLE);
                    mparam2.leftMargin = 200;
                    mparam4.leftMargin = 270;
                } else if (textTime[2].getText().toString().equals(stationName.getText().toString() + " 진입")) {
                    trainPic2.setVisibility(View.VISIBLE);
                    trainN2.setVisibility(View.VISIBLE);
                    mparam2.leftMargin = 200;
                    mparam4.leftMargin = 270;
                } else if (textTime[2].getText().toString().equals(stationName.getText().toString() + " 도착")) {
                    trainPic2.setVisibility(View.VISIBLE);
                    trainN2.setVisibility(View.VISIBLE);
                    mparam2.leftMargin = 450;
                    mparam4.leftMargin = 520;
                } else if(textTime[2].getText().toString().equals(stationName.getText().toString() + " 출발")) {
                    trainPic2.setVisibility(View.INVISIBLE);
                    trainN2.setVisibility(View.INVISIBLE);
                    if (!isLastTrain) {
                        textTime[i].setText("열차 정보 없음");
                    }
                } else {
                    trainPic2.setVisibility(View.INVISIBLE);
                    trainN2.setVisibility(View.INVISIBLE);
                }
            }
            if(i < 2) {
                if(LineName.equals("67") ||LineName.equals("63")) {
                    int j = 0;
                    if (i == 0) j = 1;
                    else j = 0;
                    textTime[i].setText(time1[j]);
                } else {
                    textTime[i].setText(time1[i]);
                }
            } else {
                if(LineName.equals("67") ||LineName.equals("63")) {
                    int j = 0;
                    if (i == 2) j = 3;
                    else j = 2;
                    textTime[i].setText(time2[i - 2]);
                } else {
                    textTime[i].setText(time2[i - 2]);
                }
            }
            if (hangStr1[i % 2] == null || updn1[i % 2] == null || hangStr2[i % 2] == null || updn2[i % 2] == null) {
                hang[i].setText("");
            } else {
                if (i < 2) {
                    hang[i].setText(hangStr1[i] + updn1[i] + " - " + ori2[i]);
                } else {
                    hang[i].setText(hangStr2[i - 2] + updn2[i - 2] + " - " + ori22[i - 2]);
                }
            }
        }

        trainN1.setText(num[0]);
        trainN2.setText(num[1]);

        timetable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TimatableActivity.class);
                intent.putExtra("is", true);
                intent.putExtra("sn", curStnName);
                intent.putExtra("stationnumber", stationIndex);
                startActivity(intent);
            }
        });

        editName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intent);
                finish();
            }
        });

        traininfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, FirstLastActivity.class);
                i.putExtra("stationNumber", stationNum.getText().toString());
                i.putExtra("stationName", curStnName);
                i.putExtra("lineNumber", LineName);
                i.putExtra("originstncode", origin);
                i.putExtra("upstnname", ori2[0].replaceAll("(막차)", ""));
                i.putExtra("downstnname", ori22[0].replaceAll("(막차)", ""));
                //1 : 평일, 2 : 토요일, 3 : 공휴일/일요일
                i.putExtra("day", 1);
                startActivity(i);
            }
        });

        favoriteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "즐겨찾기 추가 버튼 눌려짐", Toast.LENGTH_SHORT).show();
            }
        });

        refreshBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager cManager;
                NetworkInfo mobile;
                NetworkInfo wifi;

                cManager=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
                mobile = cManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
                wifi = cManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

                if (mobile.isConnected() || wifi.isConnected()) {
                    ProgressDialog dialog = new ProgressDialog(MainActivity.this, ProgressDialog.THEME_DEVICE_DEFAULT_LIGHT);
                    dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    dialog.setTitle("새로고침중");
                    dialog.setMessage("잠시 기다려 주세요");
                    dialog.show();

                    //String urlStr = "http://swopenAPI.seoul.go.kr/api/subway/sample/xml/realtimeStationArrival/1/5/숭실대입구(살피재)/";
                    Log.i("Test", "log test222");
                    //new XmlTask().execute();

                    String urlStr = "http://swopenAPI.seoul.go.kr/api/subway/585a7571646a796437325965554256/xml/realtimeStationArrival/1/20/";
                    String station = "숭실대입구(살피재)";
                    URL url = null;
                    try {
                        String tempsn = sn;
                        tempsn = URLEncoder.encode(tempsn, "utf-8");
                        urlStr = urlStr + tempsn;
                        Log.e("URL : ", urlStr);
                        url = new URL(urlStr);

                        InputStream in = url.openStream();

                        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                        parser = factory.newPullParser();
                        trainPic1.setVisibility(View.INVISIBLE);
                        trainPic2.setVisibility(View.INVISIBLE);

                        parser.setInput(in, "utf-8");

                        int timeNum = 0;
                        int subwayIdNum = 0;
                        int hangNum = 0;
                        int numNum = 0;
                        int oriNum = 0;
                        int updnNum = 0;
                        boolean flag = false;

                        int eventType = parser.getEventType();
                        boolean isItemTag = false;
                        String tagName = "";
                        String tempLine = "";

                        while (eventType != XmlPullParser.END_DOCUMENT) {
                            if (eventType == XmlPullParser.START_TAG) {
                                tagName = parser.getName();

                            } else if (eventType == XmlPullParser.TEXT) {
                                if (tagName.equals("subwayId")) {
                                    subwayId[subwayIdNum++] = parser.getText();
                                } else if (tagName.equals("arvlMsg2")) {
                                    time[timeNum++] = parser.getText();
                                } else if (tagName.equals("bstatnNm")) {
                                    hangStr[hangNum++] = parser.getText();
                                } else if (tagName.equals("btrainNo")) {
                                    num[numNum++] = parser.getText();
                                } else if (tagName.equals("trainLineNm")) {
                                    ori[oriNum++] = parser.getText();
                                } else if (tagName.equals("updnLine")) {
                                    updn[updnNum++] = "(" + parser.getText() + ")";
                                }

                            } else if (eventType == XmlPullParser.END_TAG) {

                            }
                            eventType = parser.next();
                        }
                        int cnt = 0;
                        int cnt1 = 0;

                        // Generate 2 vectors for upward, downward data.
                        for (int i = 0; i < 20; i++) {
                            String[] tempArr;
                            String[] tempArr2;
                            Log.e("subwayId", "[i] : " + subwayId[i]);
                            if (subwayId[i] != null) {
                                String temp = (Integer.parseInt(subwayId[i]) % 1000) + "";
                                Log.e("temp, LineName : ", temp + ", " + LineName);
                                if (temp.equals(LineName)) {
                                    Log.e("temp", "equals to LineName.");
                                    Log.e("cnt : ", cnt + "");
                                    Log.e("ori[i], i, cnt1", ori[i] + ", " + i + ", " + cnt1);
                                    Log.e("updn[i]", updn[i]);

                                    //상행과 하행을 구분하여 데이터 저장
                                    if (updn[i].equals("(상행)") || updn[i].equals("(내선)")) {
                                        if (ori[i] != null) {
                                            tempArr = ori[i].split(" - ");
                                            ori[cnt] = tempArr[0];
                                            ori2[cnt] = tempArr[1];
                                            tempArr2 = ori2[cnt].split("방면");
                                            nextStnName = tempArr2[0];
                                            if (tempArr.length > 0) {
                                                if (cnt < 4) {
                                                    Log.e("ori[cnt] : ", ori[cnt]);
                                                    ori[cnt] = tempArr[1];
                                                    Log.e("ori[cnt], cnt : ", ori[cnt] + ", " + cnt);
                                                    hang[cnt].setText(ori[cnt]);
                                                    if (hangStr == null) {
                                                        hangStr1[cnt % 2] = "";
                                                    } else {
                                                        if (hangStr[i].equals("1")) {
                                                            hangStr[i] = "광운대";
                                                        } else if (hangStr[i].equals("930")) {
                                                            hangStr[i] = "종합운동장";
                                                        }
                                                        hangStr1[cnt % 2] = hangStr[i] + "행";
                                                    }
                                                    updn1[cnt % 2] = updn[i];
                                                    time1[cnt % 2] = time[i];
                                                    Log.e("hangStr1[cnt % 2] : ", hangStr1[cnt % 2]);
                                                    if (hangStr1[cnt % 2].contains("(막차)")) {
                                                        isLastTrain = true;
                                                        time1[cnt % 2] = "열차 정보 없음";
                                                        hangStr1[cnt % 2] = "";
                                                        updn1[cnt % 2] = "";
                                                    } else {
                                                        isLastTrain = false;
                                                    }
                                                    cnt++;
                                                }
                                            }
                                        } else {
                                            AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                                            alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.dismiss();
                                                    finish();
                                                }
                                            });
                                            alert.setMessage("API 정보 오류입니다.\n(Invalid API Data.)");
                                            alert.show();
                                            break;
                                        }
                                    } else {
                                        if (ori[i] != null) {
                                            tempArr = ori[i].split(" - ");
                                            ori[cnt1] = tempArr[0];
                                            ori22[cnt1] = tempArr[1];
                                            tempArr2 = ori22[cnt1].split("방면");
                                            prevStnName = tempArr2[0];
                                            if (tempArr.length > 0) {
                                                if (cnt1 < 4) {
                                                    Log.e("ori[cnt] : ", ori[cnt]);
                                                    ori[cnt1] = tempArr[1];
                                                    Log.e("ori[cnt1], cnt1 : ", ori[cnt1] + ", " + cnt1);
                                                    hang[cnt1].setText(ori[cnt1]);
                                                    if (hangStr == null) {
                                                        hangStr2[cnt1] = "";
                                                    } else {
                                                        if (hangStr[i].equals("1")) {
                                                            hangStr[i] = "광운대";
                                                        } else if (hangStr[i].equals("930")) {
                                                            hangStr[i] = "종합운동장";
                                                        } else if (hangStr[i].equals("930 (급행)")) {
                                                            hangStr[i] = "종합운동장 (급행)";
                                                        }
                                                        hangStr2[cnt1 % 2] = hangStr[i] + "행";
                                                    }
                                                    updn2[cnt1 % 2] = updn[i];
                                                    time2[cnt1 % 2] = time[i];
                                                    Log.e("hangStr2[cnt1 % 2] : ", hangStr2[cnt1 % 2]);
                                                    if (hangStr2[cnt1 % 2].contains("(막차)")) {
                                                        isLastTrain = true;
                                                        time2[cnt1 % 2] = "열차 정보 없음";
                                                        hangStr2[cnt1 % 2] = "";
                                                        updn1[cnt1 % 2] = "";
                                                    } else {
                                                        isLastTrain = false;
                                                    }
                                                    cnt1++;
                                                }
                                            }
                                        } else {
                                            AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                                            alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.dismiss();
                                                    finish();
                                                }
                                            });
                                            alert.setMessage("API 정보 오류입니다.\n(Invalid API Data.)");
                                            alert.show();
                                            break;
                                        }
                                    }
                                }
                            }
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
                    for (int i = 0; i < 4; i++) {
                        if (i % 2 == 0) {
                            if (textTime[0].getText().toString().equals("전역 도착")) {
                                trainPic1.setVisibility(View.VISIBLE);
                                trainN1.setVisibility(View.VISIBLE);
                                mparam.rightMargin = 0;
                                mparam3.rightMargin = 70;
                            } else if (textTime[0].getText().toString().equals("전역 출발") || textTime[0].getText().toString().equals(stationName.getText().toString() + " 진입")) {
                                trainPic1.setVisibility(View.VISIBLE);
                                trainN1.setVisibility(View.VISIBLE);
                                mparam.rightMargin = 200;
                                mparam3.rightMargin = 270;
                            } else if (textTime[0].getText().toString().equals(stationName.getText().toString() + " 진입")) {
                                trainPic1.setVisibility(View.VISIBLE);
                                trainN1.setVisibility(View.VISIBLE);
                                mparam.rightMargin = 200;
                                mparam3.rightMargin = 270;
                            } else if (textTime[0].getText().toString().equals(stationName.getText().toString() + " 도착")) {
                                trainPic1.setVisibility(View.VISIBLE);
                                trainN1.setVisibility(View.VISIBLE);
                                mparam.rightMargin = 450;
                                mparam3.rightMargin = 520;
                            } else if (textTime[0].getText().toString().equals(stationName.getText().toString() + " 출발")) {
                                trainPic1.setVisibility(View.INVISIBLE);
                                trainN1.setVisibility(View.INVISIBLE);
                                if (!isLastTrain) {
                                    textTime[i].setText("열차 정보 없음");
                                }
                            } else {
                                trainPic1.setVisibility(View.INVISIBLE);
                                trainN1.setVisibility(View.INVISIBLE);
                            }
                        } else if (i % 2 == 1) {
                            if (textTime[2].getText().toString().equals("전역 도착")) {
                                trainPic2.setVisibility(View.VISIBLE);
                                trainN2.setVisibility(View.VISIBLE);
                                mparam2.leftMargin = 0;
                                mparam4.leftMargin = 70;
                            } else if (textTime[2].getText().toString().equals("전역 출발") || textTime[2].getText().toString().equals(stationName.getText().toString() + " 진입")) {
                                trainPic2.setVisibility(View.VISIBLE);
                                trainN2.setVisibility(View.VISIBLE);
                                mparam2.leftMargin = 200;
                                mparam4.leftMargin = 270;
                            } else if (textTime[2].getText().toString().equals(stationName.getText().toString() + " 진입")) {
                                trainPic2.setVisibility(View.VISIBLE);
                                trainN2.setVisibility(View.VISIBLE);
                                mparam2.leftMargin = 200;
                                mparam4.leftMargin = 270;
                            } else if (textTime[2].getText().toString().equals(stationName.getText().toString() + " 도착")) {
                                trainPic2.setVisibility(View.VISIBLE);
                                trainN2.setVisibility(View.VISIBLE);
                                mparam2.leftMargin = 450;
                                mparam4.leftMargin = 520;
                            } else if (textTime[2].getText().toString().equals(stationName.getText().toString() + " 출발")) {
                                trainPic2.setVisibility(View.INVISIBLE);
                                trainN2.setVisibility(View.INVISIBLE);
                                if (!isLastTrain) {
                                    textTime[i].setText("열차 정보 없음");
                                }
                            } else {
                                trainPic2.setVisibility(View.INVISIBLE);
                                trainN2.setVisibility(View.INVISIBLE);
                            }
                        }
                        if (i < 2) {
                            if (LineName.equals("67") || LineName.equals("63")) {
                                int j = 0;
                                if (i == 0) j = 1;
                                else j = 0;
                                textTime[i].setText(time1[j]);
                            } else {
                                textTime[i].setText(time1[i]);
                            }
                        } else {
                            if (LineName.equals("67") || LineName.equals("63")) {
                                int j = 0;
                                if (i == 2) j = 3;
                                else j = 2;
                                textTime[i].setText(time2[i - 2]);
                            } else {
                                textTime[i].setText(time2[i - 2]);
                            }
                        }
                        if (hangStr1[i % 2] == null || updn1[i % 2] == null || hangStr2[i % 2] == null || updn2[i % 2] == null) {
                            hang[i].setText("");
                        } else {
                            if (i < 2) {
                                hang[i].setText(hangStr1[i] + updn1[i] + " - " + ori2[i]);
                            } else {
                                hang[i].setText(hangStr2[i - 2] + updn2[i - 2] + " - " + ori22[i - 2]);
                            }
                        }
                    }
                    Toast.makeText(getApplicationContext(), "새로고침 되었습니다.", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                } else {
                    AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
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
            }
        });

        // 테스트를 위한 그림선택 (역선택을 통한 인텐트가 형성되면 결과에 따라 선택할예정)

        if(LineName.equals("1")) {
            linebox.setImageResource(R.drawable.one_line);
            trainPic1.setImageResource(R.drawable.train_one);
            trainN1.setTextColor(Color.parseColor("#0d2d77"));
            trainPic2.setImageResource(R.drawable.train_one);
            trainN2.setTextColor(Color.parseColor("#0d2d77"));
            LineInfo = "1호선";
        } else if(LineName.equals("2")) {
            linebox.setImageResource(R.drawable.two_line);
            trainPic1.setImageResource(R.drawable.train_two);
            trainN1.setTextColor(Color.parseColor("#268045"));
            trainPic2.setImageResource(R.drawable.train_two);
            trainN2.setTextColor(Color.parseColor("#268045"));
            LineInfo = "2호선";
        } else if(LineName.equals("3")) {
            linebox.setImageResource(R.drawable.three_line);
            trainPic1.setImageResource(R.drawable.train_three);
            trainN1.setTextColor(Color.parseColor("#ae4a14"));
            trainPic2.setImageResource(R.drawable.train_three);
            trainN2.setTextColor(Color.parseColor("#ae4a14"));
            LineInfo = "3호선";
        } else if(LineName.equals("4")) {
            linebox.setImageResource(R.drawable.four_line);
            trainPic1.setImageResource(R.drawable.train_four);
            trainN1.setTextColor(Color.parseColor("#2e8fae"));
            trainPic2.setImageResource(R.drawable.train_four);
            trainN2.setTextColor(Color.parseColor("#2e8fae"));
            LineInfo = "4호선";
        } else if(LineName.equals("5")) {
            linebox.setImageResource(R.drawable.five_line);
            trainPic1.setImageResource(R.drawable.train_five);
            trainN1.setTextColor(Color.parseColor("#5438a5"));
            trainPic2.setImageResource(R.drawable.train_five);
            trainN2.setTextColor(Color.parseColor("#5438a5"));
            LineInfo = "5호선";
        } else if(LineName.equals("6")) {
            linebox.setImageResource(R.drawable.six_line);
            trainPic1.setImageResource(R.drawable.train_six);
            trainN1.setTextColor(Color.parseColor("#906036"));
            trainPic2.setImageResource(R.drawable.train_six);
            trainN2.setTextColor(Color.parseColor("#906036"));
            LineInfo = "6호선";
        } else if(LineName.equals("7")) {
            linebox.setImageResource(R.drawable.l_line);
            trainPic1.setImageResource(R.drawable.train_seven);
            trainN1.setTextColor(Color.parseColor("#7f8c46"));
            trainPic2.setImageResource(R.drawable.train_seven);
            trainN2.setTextColor(Color.parseColor("#7f8c46"));
            LineInfo = "7호선";
        } else if(LineName.equals("8")) {
            linebox.setImageResource(R.drawable.eight_line);
            trainPic1.setImageResource(R.drawable.train_eight);
            trainN1.setTextColor(Color.parseColor("#bc3065"));
            trainPic2.setImageResource(R.drawable.train_eight);
            trainN2.setTextColor(Color.parseColor("#bc3065"));
            LineInfo = "8호선";
        } else if(LineName.equals("9")) {
            linebox.setImageResource(R.drawable.nine_line);
            trainPic1.setImageResource(R.drawable.train_nine);
            trainN1.setTextColor(Color.parseColor("#675945"));
            trainPic2.setImageResource(R.drawable.train_nine);
            trainN2.setTextColor(Color.parseColor("#675945"));
            LineInfo = "9호선";
        } else if(LineName.equals("75")) {
            linebox.setImageResource(R.drawable.bd_line);
            trainPic1.setImageResource(R.drawable.train_bd);
            trainN2.setTextColor(Color.parseColor("#c4a201"));
            trainPic2.setImageResource(R.drawable.train_bd);
            trainN1.setTextColor(Color.parseColor("#c4a201"));
            LineInfo = "분당선";
        } else if(LineName.equals("67")) {
            linebox.setImageResource(R.drawable.gc_line);
            LineInfo = "경춘선";
        } else if(LineName.equals("63")) {
            linebox.setImageResource(R.drawable.ja_line);
            LineInfo = "경의중앙선";
        } else if(LineName.equals("77")) {
            linebox.setImageResource(R.drawable.sbd_line);
            trainPic1.setImageResource(R.drawable.train_sbd);
            trainN1.setTextColor(Color.parseColor("#a42c2c"));
            trainPic2.setImageResource(R.drawable.train_sbd);
            trainN2.setTextColor(Color.parseColor("#a42c2c"));
            LineInfo = "신분당선";
        } else {
            // 공항철도
            linebox.setImageResource(R.drawable.airport_line);
            trainPic1.setImageResource(R.drawable.train_airport);
            trainN1.setTextColor(Color.parseColor("#357e96"));
            trainPic2.setImageResource(R.drawable.train_airport);
            trainN2.setTextColor(Color.parseColor("#357e96"));
            LineInfo = "공항철도";
        }
        Log.e("LineName : ", LineName);

        // Set Station Information into TextView.
        stationNum.setText(stationIndex + "");
        stationName.setText(curStnName);
        prevStation.setText(prevStnName);
        nextStation.setText(nextStnName);
        littleLineNum.setText(LineInfo);

        timetable.setTypeface(typefacebold, Typeface.BOLD);
        traininfo.setTypeface(typefacebold, Typeface.BOLD);
        refreshBtn.setTypeface(typefacebold, Typeface.BOLD);
        kakaoshareBtn.setTypeface(typefacebold, Typeface.BOLD);
        favoriteBtn.setTypeface(typefacebold, Typeface.BOLD);

        if (textTime[0].getText().toString().equals(stationName.getText().toString() + " 도착")) {
            trainPic1.setVisibility(View.VISIBLE);
            trainN1.setVisibility(View.VISIBLE);
            mparam.rightMargin = 450;
            mparam3.rightMargin = 520;
        }
        if (textTime[2].getText().toString().equals(stationName.getText().toString() + " 도착")) {
            trainPic2.setVisibility(View.VISIBLE);
            trainN2.setVisibility(View.VISIBLE);
            mparam2.leftMargin = 450;
            mparam4.leftMargin = 520;
        }
        if (textTime[0].getText().toString().equals(stationName.getText().toString() + " 진입")) {
            trainPic1.setVisibility(View.VISIBLE);
            trainN1.setVisibility(View.VISIBLE);
            mparam.rightMargin = 200;
            mparam3.rightMargin = 270;
        }
        if(textTime[2].getText().toString().equals(stationName.getText().toString() + " 진입")) {
            trainPic2.setVisibility(View.VISIBLE);
            trainN2.setVisibility(View.VISIBLE);
            mparam2.leftMargin = 200;
            mparam4.leftMargin = 270;
        }

        kakaoshareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MyActivity.class);
                intent.putExtra("sn", stationName.getText().toString());
                intent.putExtra("lineNumber", LineName);
                intent.putExtra("hang[0]", hang[0].getText().toString());
                intent.putExtra("hang[1]", hang[2].getText().toString());
                intent.putExtra("texttime[0]", textTime[0].getText().toString());
                intent.putExtra("texttime[1]", textTime[1].getText().toString());
                intent.putExtra("texttime[2]", textTime[2].getText().toString());
                intent.putExtra("texttime[3]", textTime[3].getText().toString());
                startActivity(intent);
            }
        });

        favoriteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tempLine = linenumber+"호선";
                String line_station = tempLine+" "+sn;
                boolean overlap = false;        //중복여부
                String favoriteString="";
                try {
                    //즐겨찾기
                    //favorite_list.txt에 목록이 저장된다.
                    File file = new File("favorite_list.txt");
                    //파일이 존재하면 중복을 검사한다.
                    Log.e("file.exists()",""+file.exists());

                        try {
                            FileInputStream fileIn = openFileInput("favorite_list.txt");
                            InputStreamReader InputRead = new InputStreamReader(fileIn);
                            char[] inputBuffer = new char[1000];
                            String s = "";
                            int charRead;
                            while ((charRead = InputRead.read(inputBuffer)) > 0) {
                                String readstring = String.copyValueOf(inputBuffer, 0, charRead);
                                s += readstring;
                            }
                            favoriteString+=s;

                            Log.e("Read", s);
                            //Toast.makeText(getBaseContext(), s, Toast.LENGTH_SHORT).show();
                            String[] tempArr;
                            //★로 나뉘어져있어서 ★로 split한다. writeRecentSearchingList 함수 참고
                            tempArr = s.split("★");


                            for(int i=0;i<tempArr.length;i++){
                                if(tempArr[i].equals(line_station)){
                                    Log.e("중복","중복");
                                    overlap = true;
                                    break;
                                }
                            }
                        }catch(Exception e){
                            e.printStackTrace();
                        }


                    if(!overlap) {
                        FileOutputStream fileout = openFileOutput("favorite_list.txt", MODE_PRIVATE);
                        OutputStreamWriter outputWriter = new OutputStreamWriter(fileout);
                        //★은 split할 기준이다.
                        favoriteString += (line_station + "★");
                        outputWriter.write(favoriteString);

                        outputWriter.close();
                        //테스트용 성공메시지
                        Log.e("Write", "write");
                        Toast.makeText(getApplicationContext(), "즐겨찾기에 추가되었습니다.", Toast.LENGTH_SHORT).show();
                    }else
                        Toast.makeText(getApplicationContext(), "이미 즐겨찾기에 존재합니다.", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    // Methods for Custom Font Setting.
    private void setFontNormal(TextView view) {
        view.setTypeface(typeface);
    }
    private void setFontBold(TextView view) {
        view.setTypeface(typefacebold, Typeface.BOLD);
    }
}
