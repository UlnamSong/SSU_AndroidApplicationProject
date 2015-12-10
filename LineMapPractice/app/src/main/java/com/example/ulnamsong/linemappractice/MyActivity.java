package com.example.ulnamsong.linemappractice;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.kakao.kakaolink.AppActionBuilder;
import com.kakao.kakaolink.AppActionInfoBuilder;
import com.kakao.kakaolink.KakaoLink;
import com.kakao.kakaolink.KakaoTalkLinkMessageBuilder;
import com.kakao.util.KakaoParameterException;

/**
 * Created by Ulnamsong on 2015-12-07.
 */
public class MyActivity extends Activity {

    private KakaoLink kakaoLink;
    private KakaoTalkLinkMessageBuilder kakaoTalkLinkMessageBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        Intent intent = getIntent();
        final String sn = intent.getStringExtra("sn");
        final String line = intent.getStringExtra("lineNumber");
        final String hang0 = intent.getStringExtra("hang[0]");
        final String hang1 = intent.getStringExtra("hang[1]");
        final String textTime0 = intent.getStringExtra("texttime[0]");
        final String textTime1 = intent.getStringExtra("texttime[1]");
        final String textTime2 = intent.getStringExtra("texttime[2]");
        final String textTime3 = intent.getStringExtra("texttime[3]");

        Log.e("KaKaoLink", "KakaoLink");
        try {
            kakaoLink = KakaoLink.getKakaoLink(getApplicationContext());
            kakaoTalkLinkMessageBuilder = kakaoLink.createKakaoTalkLinkMessageBuilder();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.e("KaKaoTalkLinkMessage", "KaKaoTalkLinkMessageBuilder");
        try {
            kakaoTalkLinkMessageBuilder.addText("[I.SEOULSUBWAY.U]\n" + line + "호선 " + "열차 정보 안내\n" + sn + "역\n\n" + "<" + hang0 + ">" + "\n이번 열차 : " + textTime0 + "\n다음 열차 : " + textTime1 + "\n\n" + "<" + hang1 + ">" + "\n이번 열차 : " + textTime2 + "\n다음 열차 : " + textTime3);
            kakaoLink.sendMessage(kakaoTalkLinkMessageBuilder, this);
        } catch (KakaoParameterException e) {
            e.printStackTrace();
        }
        finish();
    }
}
