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
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
public class TimatableActivity extends Activity {

    TextView time;
    TextView info;
    TextView stnNm;

    XmlPullParser parser = null;

    int day;
    int updn;
    String stnNumber;

    EditText stnIn;

    Button timesearchBtn;

    static Spinner spinner1;
    boolean isFromInfo;
    String stationNm;
    String printContent = "";

    ArrayList<String> timeinformation = new ArrayList<String>();
    ArrayList<String> timeinformation2 = new ArrayList<String>();

    RadioButton option1;
    RadioButton option2;
    RadioButton option3;

    RadioButton option4;
    RadioButton option5;

    RadioGroup rg1;
    RadioGroup rg2;

    String[] timeinfo = new String[26];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        timesearchBtn = (Button) findViewById(R.id.timesearch);
        time = (TextView) findViewById(R.id.textView35);
        info = (TextView) findViewById(R.id.textView36);
        stnNm = (TextView) findViewById(R.id.textView37);
        stnIn = (EditText) findViewById(R.id.editText2);

        option1 = (RadioButton) findViewById(R.id.normalday);
        option2 = (RadioButton) findViewById(R.id.saturday);
        option3 = (RadioButton) findViewById(R.id.holliday);

        option4 = (RadioButton) findViewById(R.id.upward);
        option5 = (RadioButton) findViewById(R.id.downward);

        rg1 = (RadioGroup) findViewById(R.id.radioGroup);
        rg2 = (RadioGroup) findViewById(R.id.radioGroup2);

        Intent intent = getIntent();
        stationNm = intent.getStringExtra("sn");
        isFromInfo = intent.getBooleanExtra("is", false);
        day = intent.getIntExtra("day", 0);
        updn = intent.getIntExtra("updn", 1);
        stnNumber = intent.getStringExtra("stationnumber");

        ConnectivityManager cManager;
        NetworkInfo mobile;
        NetworkInfo wifi;

        cManager=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        mobile = cManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        wifi = cManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        if(mobile.isConnected() || wifi.isConnected()){
        } else {
            AlertDialog.Builder alert = new AlertDialog.Builder(TimatableActivity.this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
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

        spinner1 = (Spinner)findViewById(R.id.spinner);
        ArrayAdapter adapter1 = ArrayAdapter.createFromResource(this, R.array.time, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter1);

        RadioButton.OnClickListener optionOnClickListener
                = new RadioButton.OnClickListener() {

            public void onClick(View v) {
                if(option1.isChecked()) {
                    Toast.makeText(TimatableActivity.this, "평일", Toast.LENGTH_SHORT).show();
                    day = 0;
                } else if(option2.isChecked()) {
                    Toast.makeText(TimatableActivity.this, "토요일", Toast.LENGTH_SHORT).show();
                    day = 1;
                } else {
                    Toast.makeText(TimatableActivity.this, "일/공휴일", Toast.LENGTH_SHORT).show();
                    day = 2;
                }
            }
        };

        RadioButton.OnClickListener optionOnClickListener2
                = new RadioButton.OnClickListener() {

            public void onClick(View v) {
                if(option4.isChecked()) {
                    Toast.makeText(TimatableActivity.this, "상행", Toast.LENGTH_SHORT).show();
                    updn = 0;
                } else if(option5.isChecked()) {
                    Toast.makeText(TimatableActivity.this, "하행", Toast.LENGTH_SHORT).show();
                    updn = 1;
                }
            }
        };

        option1.setOnClickListener(optionOnClickListener);
        option2.setOnClickListener(optionOnClickListener);
        option3.setOnClickListener(optionOnClickListener);
        option5.setOnClickListener(optionOnClickListener2);
        option4.setOnClickListener(optionOnClickListener2);

        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(stnIn.getWindowToken(), 0);


        Log.e("stationName : ", stationNm);
        Log.e("isFromInfo : ", isFromInfo + "");
        Log.e("day : ", day + "");
        Log.e("updn : ", updn + "");

        switch(day) {
            case 0:
                option1.setChecked(true);
                option2.setChecked(false);
                option3.setChecked(false);
                Log.e("aa", "switch case0");
                break;
            case 1:
                option1.setChecked(false);
                option2.setChecked(true);
                option3.setChecked(false);
                Log.e("aa", "switch case1");
                break;
            case 2:
                option1.setChecked(false);
                option2.setChecked(false);
                option3.setChecked(true);
                Log.e("aa", "switch case2");
                break;
        }

        switch(updn) {
            case 0:
                option4.setChecked(true);
                option5.setChecked(false);
                Log.e("aa", "switch case1");
                break;
            case 1:
                option4.setChecked(false);
                option5.setChecked(true);
                Log.e("aa", "switch case0");
                break;
        }
        stnIn.setText(stationNm, TextView.BufferType.EDITABLE);

        stnIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TimatableActivity.this, Search2Activity.class);
                intent.putExtra("day", day);
                intent.putExtra("updn", updn);
                startActivity(intent);
                finish();
            }
        });

        timesearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                printContent = "";
                info.setText("");
                timeinformation.clear();
                timeinformation2.clear();
                if (!stationNm.equals("역 이름")) {
                    String time1 = "";
                    int temptime1 = (int)spinner1.getSelectedItemId() + 1;
                    // Normality Status
                    if(temptime1 < 10) {
                        time1 = "0" + temptime1;
                    } else {
                        time1 = temptime1 + "";
                    }
                    time.setText(time1 + "시");
                    stnNm.setText(stationNm);
                    Log.e("time : ", time1 + "");

                    //파싱부분
                    //final String urlStr = "http://swopenAPI.seoul.go.kr/api/subway/sample/xml/realtimeStationArrival/1/5/숭실대입구(살피재)/";

                    //String urlStr = "http://swopenAPI.seoul.go.kr/api/subway/sample/xml/realtimeStationArrival/1/5/숭실대입구(살피재)/";
                    Log.i("Test", "log test222");
                    //new XmlTask().execute();

                    String urlStr = "http://openapi.seoul.go.kr:8088/43794c45626779643838736b754f5a/xml/SearchSTNTimeTableByFRCodeService/1/400/";
                    String station = stnNumber + "";
                    URL url = null;
                    try {
                        int i = day + 1;
                        int j = updn + 1;
                        urlStr = urlStr + station + "/" + i + "/" + j;
                        Log.e("URL : ", urlStr);
                        url = new URL(urlStr);

                        InputStream in = url.openStream();

                        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                        parser = factory.newPullParser();
                        parser.setInput(in, "utf-8");
                        int eventType = parser.getEventType();
                        String tagName = "";
                        String tempText = "";

                        while (eventType != XmlPullParser.END_DOCUMENT) {
                            if (eventType == XmlPullParser.START_TAG) {
                                tagName = parser.getName();

                            } else if (eventType == XmlPullParser.TEXT) {
                                if(tagName.equals("ARRIVETIME")) {
                                    tempText = parser.getText();
                                    Log.e("tempText : ", tempText);
                                    timeinformation.add(tempText);
                                }else if (tagName.equals("SUBWAYENAME")) {
                                    tempText = parser.getText();
                                    Log.e("tempText : ", tempText);
                                    timeinformation2.add(tempText);
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

                    printContent  = "";

                    Log.e("timeinformation size : ", timeinformation.size() + "");
                    int x = timeinformation.size();

                    if(x < 1) {
                        printContent = "열차 정보가 없습니다.";
                    }
                    //info
                    for(int i = 0; i < x; ++i) {
                        int a = i + 1;
                        String temp1 = timeinformation.get(i);
                        String temp2 = timeinformation2.get(i);
                        Log.e("i % 2", (i % 2) + "");
                        if((i % 2) == 0) {
                            Log.e("timeinformation : ", temp1);

                            String[] temp = temp1.split(":");
                            temp[0].replaceAll("0", "");

                            Log.e("temp[0], time1 : ", temp[0] + ", " + time1);
                            Log.e("Content : ", temp1 + " - " + temp2);

                            if(temp[0].equals(time1 + "")) {
                                printContent += temp1 + " - " + temp2 + "\n";
                            }
                        }
                    }
                    Log.e("printContent : ", printContent);
                    info.setText(printContent);

                } else {
                    // Abnormal Status
                    AlertDialog.Builder alert = new AlertDialog.Builder(TimatableActivity.this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
                    alert.setMessage("역 이름을 입력하세요.");
                    alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    alert.create();
                    alert.show();
                }

                //For Debug
                //Toast.makeText(TimatableActivity.this, "day, updn : " + day + ", " + updn + "역 이름 : " + stnIn.getText().toString() + ", " + spinner1.getSelectedItem() + " " + "입니다.", Toast.LENGTH_SHORT).show();

            }
        });

    }
}
