package com.example.ulnamsong.linemappractice;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

/**
 * Created by Ulnamsong on 2015-12-05.
 */
public class SearchActivity extends Activity {

        static int linenumberindex = 0;

        final String json="[\n" +
                "{\"STATION_CD\":\"0330\",\"STATION_NM\":\"교대\",\"LINE_NUM\":\"3\",\"FR_CODE\":\"340\"}\n" +
                ",{\"STATION_CD\":\"0331\",\"STATION_NM\":\"남부터미널\",\"LINE_NUM\":\"3\",\"FR_CODE\":\"341\"}\n" +
                ",{\"STATION_CD\":\"0332\",\"STATION_NM\":\"양재\",\"LINE_NUM\":\"3\",\"FR_CODE\":\"342\"}\n" +
                ",{\"STATION_CD\":\"0333\",\"STATION_NM\":\"매봉\",\"LINE_NUM\":\"3\",\"FR_CODE\":\"343\"}\n" +
                ",{\"STATION_CD\":\"0334\",\"STATION_NM\":\"도곡\",\"LINE_NUM\":\"3\",\"FR_CODE\":\"344\"}\n" +
                ",{\"STATION_CD\":\"0335\",\"STATION_NM\":\"대치\",\"LINE_NUM\":\"3\",\"FR_CODE\":\"345\"}\n" +
                ",{\"STATION_CD\":\"0336\",\"STATION_NM\":\"학여울\",\"LINE_NUM\":\"3\",\"FR_CODE\":\"346\"}\n" +
                ",{\"STATION_CD\":\"0337\",\"STATION_NM\":\"대청\",\"LINE_NUM\":\"3\",\"FR_CODE\":\"347\"}\n" +
                ",{\"STATION_CD\":\"0338\",\"STATION_NM\":\"일원\",\"LINE_NUM\":\"3\",\"FR_CODE\":\"348\"}\n" +
                ",{\"STATION_CD\":\"0339\",\"STATION_NM\":\"수서\",\"LINE_NUM\":\"3\",\"FR_CODE\":\"349\"}\n" +
                ",{\"STATION_CD\":\"0340\",\"STATION_NM\":\"가락시장\",\"LINE_NUM\":\"3\",\"FR_CODE\":\"350\"}\n" +
                ",{\"STATION_CD\":\"0341\",\"STATION_NM\":\"경찰병원\",\"LINE_NUM\":\"3\",\"FR_CODE\":\"351\"}\n" +
                ",{\"STATION_CD\":\"0342\",\"STATION_NM\":\"오금\",\"LINE_NUM\":\"3\",\"FR_CODE\":\"352\"}\n" +
                ",{\"STATION_CD\":\"0409\",\"STATION_NM\":\"당고개\",\"LINE_NUM\":\"4\",\"FR_CODE\":\"409\"}\n" +
                ",{\"STATION_CD\":\"0410\",\"STATION_NM\":\"상계\",\"LINE_NUM\":\"4\",\"FR_CODE\":\"410\"}\n" +
                ",{\"STATION_CD\":\"0411\",\"STATION_NM\":\"노원\",\"LINE_NUM\":\"4\",\"FR_CODE\":\"411\"}\n" +
                ",{\"STATION_CD\":\"0413\",\"STATION_NM\":\"쌍문\",\"LINE_NUM\":\"4\",\"FR_CODE\":\"413\"}\n" +
                ",{\"STATION_CD\":\"0414\",\"STATION_NM\":\"수유\",\"LINE_NUM\":\"4\",\"FR_CODE\":\"414\"}\n" +
                ",{\"STATION_CD\":\"0415\",\"STATION_NM\":\"미아\",\"LINE_NUM\":\"4\",\"FR_CODE\":\"415\"}\n" +
                ",{\"STATION_CD\":\"0416\",\"STATION_NM\":\"미아사거리\",\"LINE_NUM\":\"4\",\"FR_CODE\":\"416\"}\n" +
                ",{\"STATION_CD\":\"0417\",\"STATION_NM\":\"길음\",\"LINE_NUM\":\"4\",\"FR_CODE\":\"417\"}\n" +
                ",{\"STATION_CD\":\"0419\",\"STATION_NM\":\"한성대입구\",\"LINE_NUM\":\"4\",\"FR_CODE\":\"419\"}\n" +
                ",{\"STATION_CD\":\"0420\",\"STATION_NM\":\"혜화\",\"LINE_NUM\":\"4\",\"FR_CODE\":\"420\"}\n" +
                ",{\"STATION_CD\":\"0421\",\"STATION_NM\":\"동대문\",\"LINE_NUM\":\"4\",\"FR_CODE\":\"421\"}\n" +
                ",{\"STATION_CD\":\"0422\",\"STATION_NM\":\"동대문역사문화공원\",\"LINE_NUM\":\"4\",\"FR_CODE\":\"422\"}\n" +
                ",{\"STATION_CD\":\"0423\",\"STATION_NM\":\"충무로\",\"LINE_NUM\":\"4\",\"FR_CODE\":\"423\"}\n" +
                ",{\"STATION_CD\":\"0424\",\"STATION_NM\":\"명동\",\"LINE_NUM\":\"4\",\"FR_CODE\":\"424\"}\n" +
                ",{\"STATION_CD\":\"0425\",\"STATION_NM\":\"회현\",\"LINE_NUM\":\"4\",\"FR_CODE\":\"425\"}\n" +
                ",{\"STATION_CD\":\"0426\",\"STATION_NM\":\"서울\",\"LINE_NUM\":\"4\",\"FR_CODE\":\"426\"}\n" +
                ",{\"STATION_CD\":\"0428\",\"STATION_NM\":\"삼각지\",\"LINE_NUM\":\"4\",\"FR_CODE\":\"428\"}\n" +
                ",{\"STATION_CD\":\"0429\",\"STATION_NM\":\"신용산\",\"LINE_NUM\":\"4\",\"FR_CODE\":\"429\"}\n" +
                ",{\"STATION_CD\":\"0430\",\"STATION_NM\":\"이촌\",\"LINE_NUM\":\"4\",\"FR_CODE\":\"430\"}\n" +
                ",{\"STATION_CD\":\"0431\",\"STATION_NM\":\"동작\",\"LINE_NUM\":\"4\",\"FR_CODE\":\"431\"}\n" +
                ",{\"STATION_CD\":\"0432\",\"STATION_NM\":\"총신대입구(이수)\",\"LINE_NUM\":\"4\",\"FR_CODE\":\"432\"}\n" +
                ",{\"STATION_CD\":\"0434\",\"STATION_NM\":\"남태령\",\"LINE_NUM\":\"4\",\"FR_CODE\":\"434\"}\n" +
                ",{\"STATION_CD\":\"1002\",\"STATION_NM\":\"남영\",\"LINE_NUM\":\"1\",\"FR_CODE\":\"134\"}\n" +
                ",{\"STATION_CD\":\"1003\",\"STATION_NM\":\"용산\",\"LINE_NUM\":\"1\",\"FR_CODE\":\"135\"}\n" +
                ",{\"STATION_CD\":\"1004\",\"STATION_NM\":\"노량진\",\"LINE_NUM\":\"1\",\"FR_CODE\":\"136\"}\n" +
                ",{\"STATION_CD\":\"1006\",\"STATION_NM\":\"영등포\",\"LINE_NUM\":\"1\",\"FR_CODE\":\"139\"}\n" +
                ",{\"STATION_CD\":\"1007\",\"STATION_NM\":\"신도림\",\"LINE_NUM\":\"1\",\"FR_CODE\":\"140\"}\n" +
                ",{\"STATION_CD\":\"1008\",\"STATION_NM\":\"이촌\",\"LINE_NUM\":\"K\",\"FR_CODE\":\"K111\"}\n" +
                ",{\"STATION_CD\":\"1009\",\"STATION_NM\":\"서빙고\",\"LINE_NUM\":\"K\",\"FR_CODE\":\"K112\"}\n" +
                ",{\"STATION_CD\":\"1010\",\"STATION_NM\":\"한남\",\"LINE_NUM\":\"K\",\"FR_CODE\":\"K113\"}\n" +
                ",{\"STATION_CD\":\"1012\",\"STATION_NM\":\"응봉\",\"LINE_NUM\":\"K\",\"FR_CODE\":\"K115\"}\n" +
                ",{\"STATION_CD\":\"1013\",\"STATION_NM\":\"왕십리\",\"LINE_NUM\":\"K\",\"FR_CODE\":\"K116\"}\n" +
                ",{\"STATION_CD\":\"1016\",\"STATION_NM\":\"외대앞\",\"LINE_NUM\":\"1\",\"FR_CODE\":\"122\"}\n" +
                ",{\"STATION_CD\":\"1017\",\"STATION_NM\":\"신이문\",\"LINE_NUM\":\"1\",\"FR_CODE\":\"121\"}\n" +
                ",{\"STATION_CD\":\"1018\",\"STATION_NM\":\"석계\",\"LINE_NUM\":\"1\",\"FR_CODE\":\"120\"}\n" +
                ",{\"STATION_CD\":\"1020\",\"STATION_NM\":\"월계\",\"LINE_NUM\":\"1\",\"FR_CODE\":\"118\"}\n" +
                ",{\"STATION_CD\":\"1021\",\"STATION_NM\":\"녹천\",\"LINE_NUM\":\"1\",\"FR_CODE\":\"117\"}\n" +
                ",{\"STATION_CD\":\"1023\",\"STATION_NM\":\"선릉\",\"LINE_NUM\":\"B\",\"FR_CODE\":\"K215\"}\n" +
                ",{\"STATION_CD\":\"0150\",\"STATION_NM\":\"서울\",\"LINE_NUM\":\"1\",\"FR_CODE\":\"133\"}\n" +
                ",{\"STATION_CD\":\"0151\",\"STATION_NM\":\"시청\",\"LINE_NUM\":\"1\",\"FR_CODE\":\"132\"}\n" +
                ",{\"STATION_CD\":\"0152\",\"STATION_NM\":\"종각\",\"LINE_NUM\":\"1\",\"FR_CODE\":\"131\"}\n" +
                ",{\"STATION_CD\":\"0153\",\"STATION_NM\":\"종로3가\",\"LINE_NUM\":\"1\",\"FR_CODE\":\"130\"}\n" +
                ",{\"STATION_CD\":\"0154\",\"STATION_NM\":\"종로5가\",\"LINE_NUM\":\"1\",\"FR_CODE\":\"129\"}\n" +
                ",{\"STATION_CD\":\"0155\",\"STATION_NM\":\"동대문\",\"LINE_NUM\":\"1\",\"FR_CODE\":\"128\"}\n" +
                ",{\"STATION_CD\":\"0156\",\"STATION_NM\":\"신설동\",\"LINE_NUM\":\"1\",\"FR_CODE\":\"126\"}\n" +
                ",{\"STATION_CD\":\"0157\",\"STATION_NM\":\"제기동\",\"LINE_NUM\":\"1\",\"FR_CODE\":\"125\"}\n" +
                ",{\"STATION_CD\":\"0159\",\"STATION_NM\":\"동묘앞\",\"LINE_NUM\":\"1\",\"FR_CODE\":\"127\"}\n" +
                ",{\"STATION_CD\":\"0201\",\"STATION_NM\":\"시청\",\"LINE_NUM\":\"2\",\"FR_CODE\":\"201\"}\n" +
                ",{\"STATION_CD\":\"0202\",\"STATION_NM\":\"을지로입구\",\"LINE_NUM\":\"2\",\"FR_CODE\":\"202\"}\n" +
                ",{\"STATION_CD\":\"0203\",\"STATION_NM\":\"을지로3가\",\"LINE_NUM\":\"2\",\"FR_CODE\":\"203\"}\n" +
                ",{\"STATION_CD\":\"0204\",\"STATION_NM\":\"을지로4가\",\"LINE_NUM\":\"2\",\"FR_CODE\":\"204\"}\n" +
                ",{\"STATION_CD\":\"0205\",\"STATION_NM\":\"동대문역사문화공원\",\"LINE_NUM\":\"2\",\"FR_CODE\":\"205\"}\n" +
                ",{\"STATION_CD\":\"0207\",\"STATION_NM\":\"상왕십리\",\"LINE_NUM\":\"2\",\"FR_CODE\":\"207\"}\n" +
                ",{\"STATION_CD\":\"0208\",\"STATION_NM\":\"왕십리\",\"LINE_NUM\":\"2\",\"FR_CODE\":\"208\"}\n" +
                ",{\"STATION_CD\":\"0209\",\"STATION_NM\":\"한양대\",\"LINE_NUM\":\"2\",\"FR_CODE\":\"209\"}\n" +
                ",{\"STATION_CD\":\"0210\",\"STATION_NM\":\"뚝섬\",\"LINE_NUM\":\"2\",\"FR_CODE\":\"210\"}\n" +
                ",{\"STATION_CD\":\"0212\",\"STATION_NM\":\"건대입구\",\"LINE_NUM\":\"2\",\"FR_CODE\":\"212\"}\n" +
                ",{\"STATION_CD\":\"0213\",\"STATION_NM\":\"구의\",\"LINE_NUM\":\"2\",\"FR_CODE\":\"213\"}\n" +
                ",{\"STATION_CD\":\"0214\",\"STATION_NM\":\"강변\",\"LINE_NUM\":\"2\",\"FR_CODE\":\"214\"}\n" +
                ",{\"STATION_CD\":\"0215\",\"STATION_NM\":\"잠실나루\",\"LINE_NUM\":\"2\",\"FR_CODE\":\"215\"}\n" +
                ",{\"STATION_CD\":\"0217\",\"STATION_NM\":\"신천\",\"LINE_NUM\":\"2\",\"FR_CODE\":\"217\"}\n" +
                ",{\"STATION_CD\":\"0218\",\"STATION_NM\":\"종합운동장\",\"LINE_NUM\":\"2\",\"FR_CODE\":\"218\"}\n" +
                ",{\"STATION_CD\":\"0219\",\"STATION_NM\":\"삼성\",\"LINE_NUM\":\"2\",\"FR_CODE\":\"219\"}\n" +
                ",{\"STATION_CD\":\"0220\",\"STATION_NM\":\"선릉\",\"LINE_NUM\":\"2\",\"FR_CODE\":\"220\"}\n" +
                ",{\"STATION_CD\":\"0222\",\"STATION_NM\":\"강남\",\"LINE_NUM\":\"2\",\"FR_CODE\":\"222\"}\n" +
                ",{\"STATION_CD\":\"0223\",\"STATION_NM\":\"교대\",\"LINE_NUM\":\"2\",\"FR_CODE\":\"223\"}\n" +
                ",{\"STATION_CD\":\"0224\",\"STATION_NM\":\"서초\",\"LINE_NUM\":\"2\",\"FR_CODE\":\"224\"}\n" +
                ",{\"STATION_CD\":\"0225\",\"STATION_NM\":\"방배\",\"LINE_NUM\":\"2\",\"FR_CODE\":\"225\"}\n" +
                ",{\"STATION_CD\":\"0226\",\"STATION_NM\":\"사당\",\"LINE_NUM\":\"2\",\"FR_CODE\":\"226\"}\n" +
                ",{\"STATION_CD\":\"0228\",\"STATION_NM\":\"서울대입구\",\"LINE_NUM\":\"2\",\"FR_CODE\":\"228\"}\n" +
                ",{\"STATION_CD\":\"0229\",\"STATION_NM\":\"봉천\",\"LINE_NUM\":\"2\",\"FR_CODE\":\"229\"}\n" +
                ",{\"STATION_CD\":\"0230\",\"STATION_NM\":\"신림\",\"LINE_NUM\":\"2\",\"FR_CODE\":\"230\"}\n" +
                ",{\"STATION_CD\":\"0231\",\"STATION_NM\":\"신대방\",\"LINE_NUM\":\"2\",\"FR_CODE\":\"231\"}\n" +
                ",{\"STATION_CD\":\"0232\",\"STATION_NM\":\"구로디지털단지\",\"LINE_NUM\":\"2\",\"FR_CODE\":\"232\"}\n" +
                ",{\"STATION_CD\":\"0234\",\"STATION_NM\":\"신도림\",\"LINE_NUM\":\"2\",\"FR_CODE\":\"234\"}\n" +
                ",{\"STATION_CD\":\"0235\",\"STATION_NM\":\"문래\",\"LINE_NUM\":\"2\",\"FR_CODE\":\"235\"}\n" +
                ",{\"STATION_CD\":\"0236\",\"STATION_NM\":\"영등포구청\",\"LINE_NUM\":\"2\",\"FR_CODE\":\"236\"}\n" +
                ",{\"STATION_CD\":\"0237\",\"STATION_NM\":\"당산\",\"LINE_NUM\":\"2\",\"FR_CODE\":\"237\"}\n" +
                ",{\"STATION_CD\":\"0239\",\"STATION_NM\":\"홍대입구\",\"LINE_NUM\":\"2\",\"FR_CODE\":\"239\"}\n" +
                ",{\"STATION_CD\":\"0240\",\"STATION_NM\":\"신촌\",\"LINE_NUM\":\"2\",\"FR_CODE\":\"240\"}\n" +
                ",{\"STATION_CD\":\"0241\",\"STATION_NM\":\"이대\",\"LINE_NUM\":\"2\",\"FR_CODE\":\"241\"}\n" +
                ",{\"STATION_CD\":\"0242\",\"STATION_NM\":\"아현\",\"LINE_NUM\":\"2\",\"FR_CODE\":\"242\"}\n" +
                ",{\"STATION_CD\":\"0243\",\"STATION_NM\":\"충정로\",\"LINE_NUM\":\"2\",\"FR_CODE\":\"243\"}\n" +
                ",{\"STATION_CD\":\"0244\",\"STATION_NM\":\"용답\",\"LINE_NUM\":\"2\",\"FR_CODE\":\"211-1\"}\n" +
                ",{\"STATION_CD\":\"0245\",\"STATION_NM\":\"신답\",\"LINE_NUM\":\"2\",\"FR_CODE\":\"211-2\"}\n" +
                ",{\"STATION_CD\":\"0246\",\"STATION_NM\":\"신설동\",\"LINE_NUM\":\"2\",\"FR_CODE\":\"211-4\"}\n" +
                ",{\"STATION_CD\":\"0247\",\"STATION_NM\":\"도림천\",\"LINE_NUM\":\"2\",\"FR_CODE\":\"234-1\"}\n" +
                ",{\"STATION_CD\":\"0248\",\"STATION_NM\":\"양천구청\",\"LINE_NUM\":\"2\",\"FR_CODE\":\"234-2\"}\n" +
                ",{\"STATION_CD\":\"0249\",\"STATION_NM\":\"신정네거리\",\"LINE_NUM\":\"2\",\"FR_CODE\":\"234-3\"}\n" +
                ",{\"STATION_CD\":\"0250\",\"STATION_NM\":\"용두\",\"LINE_NUM\":\"2\",\"FR_CODE\":\"211-3\"}\n" +
                ",{\"STATION_CD\":\"0260\",\"STATION_NM\":\"까치산\",\"LINE_NUM\":\"2\",\"FR_CODE\":\"234-4\"}\n" +
                ",{\"STATION_CD\":\"0309\",\"STATION_NM\":\"지축\",\"LINE_NUM\":\"3\",\"FR_CODE\":\"319\"}\n" +
                ",{\"STATION_CD\":\"0310\",\"STATION_NM\":\"구파발\",\"LINE_NUM\":\"3\",\"FR_CODE\":\"320\"}\n" +
                ",{\"STATION_CD\":\"0311\",\"STATION_NM\":\"연신내\",\"LINE_NUM\":\"3\",\"FR_CODE\":\"321\"}\n" +
                ",{\"STATION_CD\":\"0312\",\"STATION_NM\":\"불광\",\"LINE_NUM\":\"3\",\"FR_CODE\":\"322\"}\n" +
                ",{\"STATION_CD\":\"0313\",\"STATION_NM\":\"녹번\",\"LINE_NUM\":\"3\",\"FR_CODE\":\"323\"}\n" +
                ",{\"STATION_CD\":\"0314\",\"STATION_NM\":\"홍제\",\"LINE_NUM\":\"3\",\"FR_CODE\":\"324\"}\n" +
                ",{\"STATION_CD\":\"0315\",\"STATION_NM\":\"무악재\",\"LINE_NUM\":\"3\",\"FR_CODE\":\"325\"}\n" +
                ",{\"STATION_CD\":\"0316\",\"STATION_NM\":\"독립문\",\"LINE_NUM\":\"3\",\"FR_CODE\":\"326\"}\n" +
                ",{\"STATION_CD\":\"0318\",\"STATION_NM\":\"안국\",\"LINE_NUM\":\"3\",\"FR_CODE\":\"328\"}\n" +
                ",{\"STATION_CD\":\"0319\",\"STATION_NM\":\"종로3가\",\"LINE_NUM\":\"3\",\"FR_CODE\":\"329\"}\n" +
                ",{\"STATION_CD\":\"0320\",\"STATION_NM\":\"을지로3가\",\"LINE_NUM\":\"3\",\"FR_CODE\":\"330\"}\n" +
                ",{\"STATION_CD\":\"0321\",\"STATION_NM\":\"충무로\",\"LINE_NUM\":\"3\",\"FR_CODE\":\"331\"}\n" +
                ",{\"STATION_CD\":\"0322\",\"STATION_NM\":\"동대입구\",\"LINE_NUM\":\"3\",\"FR_CODE\":\"332\"}\n" +
                ",{\"STATION_CD\":\"0324\",\"STATION_NM\":\"금호\",\"LINE_NUM\":\"3\",\"FR_CODE\":\"334\"}\n" +
                ",{\"STATION_CD\":\"0325\",\"STATION_NM\":\"옥수\",\"LINE_NUM\":\"3\",\"FR_CODE\":\"335\"}\n" +
                ",{\"STATION_CD\":\"0326\",\"STATION_NM\":\"압구정\",\"LINE_NUM\":\"3\",\"FR_CODE\":\"336\"}\n" +
                ",{\"STATION_CD\":\"0327\",\"STATION_NM\":\"신사\",\"LINE_NUM\":\"3\",\"FR_CODE\":\"337\"}\n" +
                ",{\"STATION_CD\":\"0329\",\"STATION_NM\":\"고속터미널\",\"LINE_NUM\":\"3\",\"FR_CODE\":\"339\"}\n" +
                ",{\"STATION_CD\":\"1275\",\"STATION_NM\":\"일산\",\"LINE_NUM\":\"K\",\"FR_CODE\":\"K326\"}\n" +
                ",{\"STATION_CD\":\"1276\",\"STATION_NM\":\"탄현\",\"LINE_NUM\":\"K\",\"FR_CODE\":\"K327\"}\n" +
                ",{\"STATION_CD\":\"1279\",\"STATION_NM\":\"금릉\",\"LINE_NUM\":\"K\",\"FR_CODE\":\"K330\"}\n" +
                ",{\"STATION_CD\":\"1280\",\"STATION_NM\":\"금촌\",\"LINE_NUM\":\"K\",\"FR_CODE\":\"K331\"}\n" +
                ",{\"STATION_CD\":\"1283\",\"STATION_NM\":\"파주\",\"LINE_NUM\":\"K\",\"FR_CODE\":\"K334\"}\n" +
                ",{\"STATION_CD\":\"1284\",\"STATION_NM\":\"문산\",\"LINE_NUM\":\"K\",\"FR_CODE\":\"K335\"}\n" +
                ",{\"STATION_CD\":\"1291\",\"STATION_NM\":\"서울\",\"LINE_NUM\":\"A\",\"FR_CODE\":\"A01\"}\n" +
                ",{\"STATION_CD\":\"1292\",\"STATION_NM\":\"공덕\",\"LINE_NUM\":\"A\",\"FR_CODE\":\"A02\"}\n" +
                ",{\"STATION_CD\":\"1293\",\"STATION_NM\":\"홍대입구\",\"LINE_NUM\":\"A\",\"FR_CODE\":\"A03\"}\n" +
                ",{\"STATION_CD\":\"1308\",\"STATION_NM\":\"광운대\",\"LINE_NUM\":\"G\",\"FR_CODE\":\"P119\"}\n" +
                ",{\"STATION_CD\":\"1309\",\"STATION_NM\":\"상봉\",\"LINE_NUM\":\"G\",\"FR_CODE\":\"P120\"}\n" +
                ",{\"STATION_CD\":\"1310\",\"STATION_NM\":\"망우\",\"LINE_NUM\":\"G\",\"FR_CODE\":\"P121\"}\n" +
                ",{\"STATION_CD\":\"1312\",\"STATION_NM\":\"갈매\",\"LINE_NUM\":\"G\",\"FR_CODE\":\"P123\"}\n" +
                ",{\"STATION_CD\":\"1313\",\"STATION_NM\":\"별내\",\"LINE_NUM\":\"G\",\"FR_CODE\":\"P124\"}\n" +
                ",{\"STATION_CD\":\"1315\",\"STATION_NM\":\"사릉\",\"LINE_NUM\":\"G\",\"FR_CODE\":\"P126\"}\n" +
                ",{\"STATION_CD\":\"1316\",\"STATION_NM\":\"금곡\",\"LINE_NUM\":\"G\",\"FR_CODE\":\"P127\"}\n" +
                ",{\"STATION_CD\":\"1318\",\"STATION_NM\":\"천마산\",\"LINE_NUM\":\"G\",\"FR_CODE\":\"P129\"}\n" +
                ",{\"STATION_CD\":\"1319\",\"STATION_NM\":\"마석\",\"LINE_NUM\":\"G\",\"FR_CODE\":\"P130\"}\n" +
                ",{\"STATION_CD\":\"1321\",\"STATION_NM\":\"청평\",\"LINE_NUM\":\"G\",\"FR_CODE\":\"P132\"}\n" +
                ",{\"STATION_CD\":\"1322\",\"STATION_NM\":\"상천\",\"LINE_NUM\":\"G\",\"FR_CODE\":\"P133\"}\n" +
                ",{\"STATION_CD\":\"1324\",\"STATION_NM\":\"굴봉산\",\"LINE_NUM\":\"G\",\"FR_CODE\":\"P135\"}\n" +
                ",{\"STATION_CD\":\"1325\",\"STATION_NM\":\"백양리\",\"LINE_NUM\":\"G\",\"FR_CODE\":\"P136\"}\n" +
                ",{\"STATION_CD\":\"1327\",\"STATION_NM\":\"김유정\",\"LINE_NUM\":\"G\",\"FR_CODE\":\"P138\"}\n" +
                ",{\"STATION_CD\":\"1328\",\"STATION_NM\":\"남춘천\",\"LINE_NUM\":\"G\",\"FR_CODE\":\"P139\"}\n" +
                ",{\"STATION_CD\":\"1402\",\"STATION_NM\":\"쌍용(나사렛대)\",\"LINE_NUM\":\"1\",\"FR_CODE\":\"P171\"}\n" +
                ",{\"STATION_CD\":\"1403\",\"STATION_NM\":\"아산\",\"LINE_NUM\":\"1\",\"FR_CODE\":\"P172\"}\n" +
                ",{\"STATION_CD\":\"1404\",\"STATION_NM\":\"탕정\",\"LINE_NUM\":\"1\",\"FR_CODE\":\"P173\"}\n" +
                ",{\"STATION_CD\":\"1406\",\"STATION_NM\":\"풍기\",\"LINE_NUM\":\"1\",\"FR_CODE\":\"P175\"}\n" +
                ",{\"STATION_CD\":\"1407\",\"STATION_NM\":\"온양온천\",\"LINE_NUM\":\"1\",\"FR_CODE\":\"P176\"}\n" +
                ",{\"STATION_CD\":\"1408\",\"STATION_NM\":\"신창\",\"LINE_NUM\":\"1\",\"FR_CODE\":\"P177\"}\n" +
                ",{\"STATION_CD\":\"1951\",\"STATION_NM\":\"원당\",\"LINE_NUM\":\"3\",\"FR_CODE\":\"317\"}\n" +
                ",{\"STATION_CD\":\"1952\",\"STATION_NM\":\"화정\",\"LINE_NUM\":\"3\",\"FR_CODE\":\"316\"}\n" +
                ",{\"STATION_CD\":\"1954\",\"STATION_NM\":\"백석\",\"LINE_NUM\":\"3\",\"FR_CODE\":\"314\"}\n" +
                ",{\"STATION_CD\":\"1955\",\"STATION_NM\":\"마두\",\"LINE_NUM\":\"3\",\"FR_CODE\":\"313\"}\n" +
                ",{\"STATION_CD\":\"1957\",\"STATION_NM\":\"주엽\",\"LINE_NUM\":\"3\",\"FR_CODE\":\"311\"}\n" +
                ",{\"STATION_CD\":\"1958\",\"STATION_NM\":\"대화\",\"LINE_NUM\":\"3\",\"FR_CODE\":\"310\"}\n" +
                ",{\"STATION_CD\":\"2511\",\"STATION_NM\":\"방화\",\"LINE_NUM\":\"5\",\"FR_CODE\":\"510\"}\n" +
                ",{\"STATION_CD\":\"2512\",\"STATION_NM\":\"개화산\",\"LINE_NUM\":\"5\",\"FR_CODE\":\"511\"}\n" +
                ",{\"STATION_CD\":\"2513\",\"STATION_NM\":\"김포공항\",\"LINE_NUM\":\"5\",\"FR_CODE\":\"512\"}\n" +
                ",{\"STATION_CD\":\"2515\",\"STATION_NM\":\"마곡\",\"LINE_NUM\":\"5\",\"FR_CODE\":\"514\"}\n" +
                ",{\"STATION_CD\":\"2516\",\"STATION_NM\":\"발산\",\"LINE_NUM\":\"5\",\"FR_CODE\":\"515\"}\n" +
                ",{\"STATION_CD\":\"2517\",\"STATION_NM\":\"우장산\",\"LINE_NUM\":\"5\",\"FR_CODE\":\"516\"}\n" +
                ",{\"STATION_CD\":\"2518\",\"STATION_NM\":\"화곡\",\"LINE_NUM\":\"5\",\"FR_CODE\":\"517\"}\n" +
                ",{\"STATION_CD\":\"2520\",\"STATION_NM\":\"신정(은행정)\",\"LINE_NUM\":\"5\",\"FR_CODE\":\"519\"}\n" +
                ",{\"STATION_CD\":\"2521\",\"STATION_NM\":\"목동\",\"LINE_NUM\":\"5\",\"FR_CODE\":\"520\"}\n" +
                ",{\"STATION_CD\":\"2522\",\"STATION_NM\":\"오목교\",\"LINE_NUM\":\"5\",\"FR_CODE\":\"521\"}\n" +
                ",{\"STATION_CD\":\"2524\",\"STATION_NM\":\"영등포구청\",\"LINE_NUM\":\"5\",\"FR_CODE\":\"523\"}\n" +
                ",{\"STATION_CD\":\"2525\",\"STATION_NM\":\"영등포시장\",\"LINE_NUM\":\"5\",\"FR_CODE\":\"524\"}\n" +
                ",{\"STATION_CD\":\"2526\",\"STATION_NM\":\"신길\",\"LINE_NUM\":\"5\",\"FR_CODE\":\"525\"}\n" +
                ",{\"STATION_CD\":\"2528\",\"STATION_NM\":\"여의나루\",\"LINE_NUM\":\"5\",\"FR_CODE\":\"527\"}\n" +
                ",{\"STATION_CD\":\"2529\",\"STATION_NM\":\"마포\",\"LINE_NUM\":\"5\",\"FR_CODE\":\"528\"}\n" +
                ",{\"STATION_CD\":\"2530\",\"STATION_NM\":\"공덕\",\"LINE_NUM\":\"5\",\"FR_CODE\":\"529\"}\n" +
                ",{\"STATION_CD\":\"2531\",\"STATION_NM\":\"애오개\",\"LINE_NUM\":\"5\",\"FR_CODE\":\"530\"}\n" +
                ",{\"STATION_CD\":\"2533\",\"STATION_NM\":\"서대문\",\"LINE_NUM\":\"5\",\"FR_CODE\":\"532\"}\n" +
                ",{\"STATION_CD\":\"2534\",\"STATION_NM\":\"광화문\",\"LINE_NUM\":\"5\",\"FR_CODE\":\"533\"}\n" +
                ",{\"STATION_CD\":\"2535\",\"STATION_NM\":\"종로3가\",\"LINE_NUM\":\"5\",\"FR_CODE\":\"534\"}\n" +
                ",{\"STATION_CD\":\"2536\",\"STATION_NM\":\"을지로4가\",\"LINE_NUM\":\"5\",\"FR_CODE\":\"535\"}\n" +
                ",{\"STATION_CD\":\"2620\",\"STATION_NM\":\"월드컵경기장(성산)\",\"LINE_NUM\":\"6\",\"FR_CODE\":\"619\"}\n" +
                ",{\"STATION_CD\":\"2621\",\"STATION_NM\":\"마포구청\",\"LINE_NUM\":\"6\",\"FR_CODE\":\"620\"}\n" +
                ",{\"STATION_CD\":\"2622\",\"STATION_NM\":\"망원\",\"LINE_NUM\":\"6\",\"FR_CODE\":\"621\"}\n" +
                ",{\"STATION_CD\":\"2623\",\"STATION_NM\":\"합정\",\"LINE_NUM\":\"6\",\"FR_CODE\":\"622\"}\n" +
                ",{\"STATION_CD\":\"2624\",\"STATION_NM\":\"상수\",\"LINE_NUM\":\"6\",\"FR_CODE\":\"623\"}\n" +
                ",{\"STATION_CD\":\"2626\",\"STATION_NM\":\"대흥\",\"LINE_NUM\":\"6\",\"FR_CODE\":\"625\"}\n" +
                ",{\"STATION_CD\":\"2627\",\"STATION_NM\":\"공덕\",\"LINE_NUM\":\"6\",\"FR_CODE\":\"626\"}\n" +
                ",{\"STATION_CD\":\"2628\",\"STATION_NM\":\"효창공원앞\",\"LINE_NUM\":\"6\",\"FR_CODE\":\"627\"}\n" +
                ",{\"STATION_CD\":\"2629\",\"STATION_NM\":\"삼각지\",\"LINE_NUM\":\"6\",\"FR_CODE\":\"628\"}\n" +
                ",{\"STATION_CD\":\"2631\",\"STATION_NM\":\"이태원\",\"LINE_NUM\":\"6\",\"FR_CODE\":\"630\"}\n" +
                ",{\"STATION_CD\":\"2632\",\"STATION_NM\":\"한강진\",\"LINE_NUM\":\"6\",\"FR_CODE\":\"631\"}\n" +
                ",{\"STATION_CD\":\"2633\",\"STATION_NM\":\"버티고개\",\"LINE_NUM\":\"6\",\"FR_CODE\":\"632\"}\n" +
                ",{\"STATION_CD\":\"2634\",\"STATION_NM\":\"약수\",\"LINE_NUM\":\"6\",\"FR_CODE\":\"633\"}\n" +
                ",{\"STATION_CD\":\"2636\",\"STATION_NM\":\"신당\",\"LINE_NUM\":\"6\",\"FR_CODE\":\"635\"}\n" +
                ",{\"STATION_CD\":\"2637\",\"STATION_NM\":\"동묘앞\",\"LINE_NUM\":\"6\",\"FR_CODE\":\"636\"}\n" +
                ",{\"STATION_CD\":\"2638\",\"STATION_NM\":\"창신\",\"LINE_NUM\":\"6\",\"FR_CODE\":\"637\"}\n" +
                ",{\"STATION_CD\":\"2639\",\"STATION_NM\":\"보문\",\"LINE_NUM\":\"6\",\"FR_CODE\":\"638\"}\n" +
                ",{\"STATION_CD\":\"2641\",\"STATION_NM\":\"고려대\",\"LINE_NUM\":\"6\",\"FR_CODE\":\"640\"}\n" +
                ",{\"STATION_CD\":\"2642\",\"STATION_NM\":\"월곡\",\"LINE_NUM\":\"6\",\"FR_CODE\":\"641\"}\n" +
                ",{\"STATION_CD\":\"2643\",\"STATION_NM\":\"상월곡\",\"LINE_NUM\":\"6\",\"FR_CODE\":\"642\"}\n" +
                ",{\"STATION_CD\":\"2645\",\"STATION_NM\":\"석계\",\"LINE_NUM\":\"6\",\"FR_CODE\":\"644\"}\n" +
                ",{\"STATION_CD\":\"2646\",\"STATION_NM\":\"태릉입구\",\"LINE_NUM\":\"6\",\"FR_CODE\":\"645\"}\n" +
                ",{\"STATION_CD\":\"2647\",\"STATION_NM\":\"화랑대\",\"LINE_NUM\":\"6\",\"FR_CODE\":\"646\"}\n" +
                ",{\"STATION_CD\":\"2711\",\"STATION_NM\":\"장암\",\"LINE_NUM\":\"7\",\"FR_CODE\":\"709\"}\n" +
                ",{\"STATION_CD\":\"2712\",\"STATION_NM\":\"도봉산\",\"LINE_NUM\":\"7\",\"FR_CODE\":\"710\"}\n" +
                ",{\"STATION_CD\":\"2713\",\"STATION_NM\":\"수락산\",\"LINE_NUM\":\"7\",\"FR_CODE\":\"711\"}\n" +
                ",{\"STATION_CD\":\"2714\",\"STATION_NM\":\"마들\",\"LINE_NUM\":\"7\",\"FR_CODE\":\"712\"}\n" +
                ",{\"STATION_CD\":\"2538\",\"STATION_NM\":\"청구\",\"LINE_NUM\":\"5\",\"FR_CODE\":\"537\"}\n" +
                ",{\"STATION_CD\":\"2539\",\"STATION_NM\":\"신금호\",\"LINE_NUM\":\"5\",\"FR_CODE\":\"538\"}\n" +
                ",{\"STATION_CD\":\"2540\",\"STATION_NM\":\"행당\",\"LINE_NUM\":\"5\",\"FR_CODE\":\"539\"}\n" +
                ",{\"STATION_CD\":\"2541\",\"STATION_NM\":\"왕십리\",\"LINE_NUM\":\"5\",\"FR_CODE\":\"540\"}\n" +
                ",{\"STATION_CD\":\"2542\",\"STATION_NM\":\"마장\",\"LINE_NUM\":\"5\",\"FR_CODE\":\"541\"}\n" +
                ",{\"STATION_CD\":\"2544\",\"STATION_NM\":\"장한평\",\"LINE_NUM\":\"5\",\"FR_CODE\":\"543\"}\n" +
                ",{\"STATION_CD\":\"2545\",\"STATION_NM\":\"군자\",\"LINE_NUM\":\"5\",\"FR_CODE\":\"544\"}\n" +
                ",{\"STATION_CD\":\"2546\",\"STATION_NM\":\"아차산\",\"LINE_NUM\":\"5\",\"FR_CODE\":\"545\"}\n" +
                ",{\"STATION_CD\":\"2548\",\"STATION_NM\":\"천호\",\"LINE_NUM\":\"5\",\"FR_CODE\":\"547\"}\n" +
                ",{\"STATION_CD\":\"2549\",\"STATION_NM\":\"강동\",\"LINE_NUM\":\"5\",\"FR_CODE\":\"548\"}\n" +
                ",{\"STATION_CD\":\"2550\",\"STATION_NM\":\"길동\",\"LINE_NUM\":\"5\",\"FR_CODE\":\"549\"}\n" +
                ",{\"STATION_CD\":\"2552\",\"STATION_NM\":\"명일\",\"LINE_NUM\":\"5\",\"FR_CODE\":\"551\"}\n" +
                ",{\"STATION_CD\":\"2553\",\"STATION_NM\":\"고덕\",\"LINE_NUM\":\"5\",\"FR_CODE\":\"552\"}\n" +
                ",{\"STATION_CD\":\"2554\",\"STATION_NM\":\"상일동\",\"LINE_NUM\":\"5\",\"FR_CODE\":\"553\"}\n" +
                ",{\"STATION_CD\":\"2555\",\"STATION_NM\":\"둔촌동\",\"LINE_NUM\":\"5\",\"FR_CODE\":\"P549\"}\n" +
                ",{\"STATION_CD\":\"2557\",\"STATION_NM\":\"방이\",\"LINE_NUM\":\"5\",\"FR_CODE\":\"P551\"}\n" +
                ",{\"STATION_CD\":\"2558\",\"STATION_NM\":\"오금\",\"LINE_NUM\":\"5\",\"FR_CODE\":\"P552\"}\n" +
                ",{\"STATION_CD\":\"2559\",\"STATION_NM\":\"개롱\",\"LINE_NUM\":\"5\",\"FR_CODE\":\"P553\"}\n" +
                ",{\"STATION_CD\":\"2560\",\"STATION_NM\":\"거여\",\"LINE_NUM\":\"5\",\"FR_CODE\":\"P554\"}\n" +
                ",{\"STATION_CD\":\"2611\",\"STATION_NM\":\"응암\",\"LINE_NUM\":\"6\",\"FR_CODE\":\"610\"}\n" +
                ",{\"STATION_CD\":\"2612\",\"STATION_NM\":\"역촌\",\"LINE_NUM\":\"6\",\"FR_CODE\":\"611\"}\n" +
                ",{\"STATION_CD\":\"2613\",\"STATION_NM\":\"불광\",\"LINE_NUM\":\"6\",\"FR_CODE\":\"612\"}\n" +
                ",{\"STATION_CD\":\"2615\",\"STATION_NM\":\"연신내\",\"LINE_NUM\":\"6\",\"FR_CODE\":\"614\"}\n" +
                ",{\"STATION_CD\":\"2616\",\"STATION_NM\":\"구산\",\"LINE_NUM\":\"6\",\"FR_CODE\":\"615\"}\n" +
                ",{\"STATION_CD\":\"2617\",\"STATION_NM\":\"새절\",\"LINE_NUM\":\"6\",\"FR_CODE\":\"616\"}\n" +
                ",{\"STATION_CD\":\"2618\",\"STATION_NM\":\"증산\",\"LINE_NUM\":\"6\",\"FR_CODE\":\"617\"}\n" +
                ",{\"STATION_CD\":\"1452\",\"STATION_NM\":\"대공원\",\"LINE_NUM\":\"4\",\"FR_CODE\":\"437\"}\n" +
                ",{\"STATION_CD\":\"1453\",\"STATION_NM\":\"과천\",\"LINE_NUM\":\"4\",\"FR_CODE\":\"438\"}\n" +
                ",{\"STATION_CD\":\"1455\",\"STATION_NM\":\"인덕원\",\"LINE_NUM\":\"4\",\"FR_CODE\":\"440\"}\n" +
                ",{\"STATION_CD\":\"1456\",\"STATION_NM\":\"평촌\",\"LINE_NUM\":\"4\",\"FR_CODE\":\"441\"}\n" +
                ",{\"STATION_CD\":\"1458\",\"STATION_NM\":\"금정\",\"LINE_NUM\":\"4\",\"FR_CODE\":\"443\"}\n" +
                ",{\"STATION_CD\":\"1702\",\"STATION_NM\":\"가산디지털단지\",\"LINE_NUM\":\"1\",\"FR_CODE\":\"P142\"}\n" +
                ",{\"STATION_CD\":\"1703\",\"STATION_NM\":\"금천구청\",\"LINE_NUM\":\"1\",\"FR_CODE\":\"P144\"}\n" +
                ",{\"STATION_CD\":\"1705\",\"STATION_NM\":\"관악\",\"LINE_NUM\":\"1\",\"FR_CODE\":\"P146\"}\n" +
                ",{\"STATION_CD\":\"1706\",\"STATION_NM\":\"안양\",\"LINE_NUM\":\"1\",\"FR_CODE\":\"P147\"}\n" +
                ",{\"STATION_CD\":\"1708\",\"STATION_NM\":\"금정\",\"LINE_NUM\":\"1\",\"FR_CODE\":\"P149\"}\n" +
                ",{\"STATION_CD\":\"1709\",\"STATION_NM\":\"군포\",\"LINE_NUM\":\"1\",\"FR_CODE\":\"P150\"}\n" +
                ",{\"STATION_CD\":\"1711\",\"STATION_NM\":\"성균관대\",\"LINE_NUM\":\"1\",\"FR_CODE\":\"P153\"}\n" +
                ",{\"STATION_CD\":\"1712\",\"STATION_NM\":\"화서\",\"LINE_NUM\":\"1\",\"FR_CODE\":\"P154\"}\n" +
                ",{\"STATION_CD\":\"1714\",\"STATION_NM\":\"독산\",\"LINE_NUM\":\"1\",\"FR_CODE\":\"P143\"}\n" +
                ",{\"STATION_CD\":\"1715\",\"STATION_NM\":\"세류\",\"LINE_NUM\":\"1\",\"FR_CODE\":\"P156\"}\n" +
                ",{\"STATION_CD\":\"1717\",\"STATION_NM\":\"세마\",\"LINE_NUM\":\"1\",\"FR_CODE\":\"P158\"}\n" +
                ",{\"STATION_CD\":\"1718\",\"STATION_NM\":\"오산대\",\"LINE_NUM\":\"1\",\"FR_CODE\":\"P159\"}\n" +
                ",{\"STATION_CD\":\"1719\",\"STATION_NM\":\"오산\",\"LINE_NUM\":\"1\",\"FR_CODE\":\"P160\"}\n" +
                ",{\"STATION_CD\":\"1721\",\"STATION_NM\":\"송탄\",\"LINE_NUM\":\"1\",\"FR_CODE\":\"P162\"}\n" +
                ",{\"STATION_CD\":\"1722\",\"STATION_NM\":\"서정리\",\"LINE_NUM\":\"1\",\"FR_CODE\":\"P163\"}\n" +
                ",{\"STATION_CD\":\"1724\",\"STATION_NM\":\"평택\",\"LINE_NUM\":\"1\",\"FR_CODE\":\"P165\"}\n" +
                ",{\"STATION_CD\":\"1726\",\"STATION_NM\":\"직산\",\"LINE_NUM\":\"1\",\"FR_CODE\":\"P167\"}\n" +
                ",{\"STATION_CD\":\"1727\",\"STATION_NM\":\"두정\",\"LINE_NUM\":\"1\",\"FR_CODE\":\"P168\"}\n" +
                ",{\"STATION_CD\":\"1729\",\"STATION_NM\":\"당정\",\"LINE_NUM\":\"1\",\"FR_CODE\":\"P151\"}\n" +
                ",{\"STATION_CD\":\"1749\",\"STATION_NM\":\"서동탄\",\"LINE_NUM\":\"1\",\"FR_CODE\":\"P157-1\"}\n" +
                ",{\"STATION_CD\":\"1750\",\"STATION_NM\":\"광명\",\"LINE_NUM\":\"1\",\"FR_CODE\":\"K410\"}\n" +
                ",{\"STATION_CD\":\"1751\",\"STATION_NM\":\"산본\",\"LINE_NUM\":\"4\",\"FR_CODE\":\"444\"}\n" +
                ",{\"STATION_CD\":\"1752\",\"STATION_NM\":\"대야미\",\"LINE_NUM\":\"4\",\"FR_CODE\":\"446\"}\n" +
                ",{\"STATION_CD\":\"1754\",\"STATION_NM\":\"상록수\",\"LINE_NUM\":\"4\",\"FR_CODE\":\"448\"}\n" +
                ",{\"STATION_CD\":\"1756\",\"STATION_NM\":\"중앙\",\"LINE_NUM\":\"4\",\"FR_CODE\":\"450\"}\n" +
                ",{\"STATION_CD\":\"1757\",\"STATION_NM\":\"고잔\",\"LINE_NUM\":\"4\",\"FR_CODE\":\"451\"}\n" +
                ",{\"STATION_CD\":\"1759\",\"STATION_NM\":\"안산\",\"LINE_NUM\":\"4\",\"FR_CODE\":\"453\"}\n" +
                ",{\"STATION_CD\":\"1760\",\"STATION_NM\":\"신길온천\",\"LINE_NUM\":\"4\",\"FR_CODE\":\"454\"}\n" +
                ",{\"STATION_CD\":\"1762\",\"STATION_NM\":\"오이도\",\"LINE_NUM\":\"4\",\"FR_CODE\":\"456\"}\n" +
                ",{\"STATION_CD\":\"1763\",\"STATION_NM\":\"수리산\",\"LINE_NUM\":\"4\",\"FR_CODE\":\"445\"}\n" +
                ",{\"STATION_CD\":\"1802\",\"STATION_NM\":\"오류동\",\"LINE_NUM\":\"1\",\"FR_CODE\":\"144\"}\n" +
                ",{\"STATION_CD\":\"1803\",\"STATION_NM\":\"역곡\",\"LINE_NUM\":\"1\",\"FR_CODE\":\"146\"}\n" +
                ",{\"STATION_CD\":\"1805\",\"STATION_NM\":\"송내\",\"LINE_NUM\":\"1\",\"FR_CODE\":\"150\"}\n" +
                ",{\"STATION_CD\":\"1806\",\"STATION_NM\":\"부평\",\"LINE_NUM\":\"1\",\"FR_CODE\":\"152\"}\n" +
                ",{\"STATION_CD\":\"1808\",\"STATION_NM\":\"동암\",\"LINE_NUM\":\"1\",\"FR_CODE\":\"154\"}\n" +
                ",{\"STATION_CD\":\"1809\",\"STATION_NM\":\"주안\",\"LINE_NUM\":\"1\",\"FR_CODE\":\"156\"}\n" +
                ",{\"STATION_CD\":\"1810\",\"STATION_NM\":\"제물포\",\"LINE_NUM\":\"1\",\"FR_CODE\":\"158\"}\n" +
                ",{\"STATION_CD\":\"1812\",\"STATION_NM\":\"인천\",\"LINE_NUM\":\"1\",\"FR_CODE\":\"161\"}\n" +
                ",{\"STATION_CD\":\"1813\",\"STATION_NM\":\"구일\",\"LINE_NUM\":\"1\",\"FR_CODE\":\"142\"}\n" +
                ",{\"STATION_CD\":\"1814\",\"STATION_NM\":\"소사\",\"LINE_NUM\":\"1\",\"FR_CODE\":\"147\"}\n" +
                ",{\"STATION_CD\":\"1816\",\"STATION_NM\":\"간석\",\"LINE_NUM\":\"1\",\"FR_CODE\":\"155\"}\n" +
                ",{\"STATION_CD\":\"1817\",\"STATION_NM\":\"도원\",\"LINE_NUM\":\"1\",\"FR_CODE\":\"159\"}\n" +
                ",{\"STATION_CD\":\"1822\",\"STATION_NM\":\"중동\",\"LINE_NUM\":\"1\",\"FR_CODE\":\"149\"}\n" +
                ",{\"STATION_CD\":\"1823\",\"STATION_NM\":\"도화\",\"LINE_NUM\":\"1\",\"FR_CODE\":\"157\"}\n" +
                ",{\"STATION_CD\":\"1846\",\"STATION_NM\":\"수원\",\"LINE_NUM\":\"B\",\"FR_CODE\":\"K245\"}\n" +
                ",{\"STATION_CD\":\"1847\",\"STATION_NM\":\"서울숲\",\"LINE_NUM\":\"B\",\"FR_CODE\":\"K211\"}\n" +
                ",{\"STATION_CD\":\"1848\",\"STATION_NM\":\"압구정로데오\",\"LINE_NUM\":\"B\",\"FR_CODE\":\"K212\"}\n" +
                ",{\"STATION_CD\":\"1850\",\"STATION_NM\":\"선정릉\",\"LINE_NUM\":\"B\",\"FR_CODE\":\"K214\"}\n" +
                ",{\"STATION_CD\":\"1851\",\"STATION_NM\":\"가천대\",\"LINE_NUM\":\"B\",\"FR_CODE\":\"K223\"}\n" +
                ",{\"STATION_CD\":\"1853\",\"STATION_NM\":\"모란\",\"LINE_NUM\":\"B\",\"FR_CODE\":\"K225\"}\n" +
                ",{\"STATION_CD\":\"1854\",\"STATION_NM\":\"야탑\",\"LINE_NUM\":\"B\",\"FR_CODE\":\"K226\"}\n" +
                ",{\"STATION_CD\":\"1856\",\"STATION_NM\":\"수내\",\"LINE_NUM\":\"B\",\"FR_CODE\":\"K229\"}\n" +
                ",{\"STATION_CD\":\"1857\",\"STATION_NM\":\"정자\",\"LINE_NUM\":\"B\",\"FR_CODE\":\"K230\"}\n" +
                ",{\"STATION_CD\":\"1859\",\"STATION_NM\":\"오리\",\"LINE_NUM\":\"B\",\"FR_CODE\":\"K232\"}\n" +
                ",{\"STATION_CD\":\"1860\",\"STATION_NM\":\"이매\",\"LINE_NUM\":\"B\",\"FR_CODE\":\"K227\"}\n" +
                ",{\"STATION_CD\":\"1862\",\"STATION_NM\":\"죽전\",\"LINE_NUM\":\"B\",\"FR_CODE\":\"K233\"}\n" +
                ",{\"STATION_CD\":\"1863\",\"STATION_NM\":\"구성\",\"LINE_NUM\":\"B\",\"FR_CODE\":\"K235\"}\n" +
                ",{\"STATION_CD\":\"1865\",\"STATION_NM\":\"기흥\",\"LINE_NUM\":\"B\",\"FR_CODE\":\"K237\"}\n" +
                ",{\"STATION_CD\":\"1866\",\"STATION_NM\":\"상갈\",\"LINE_NUM\":\"B\",\"FR_CODE\":\"K238\"}\n" +
                ",{\"STATION_CD\":\"1868\",\"STATION_NM\":\"영통\",\"LINE_NUM\":\"B\",\"FR_CODE\":\"K240\"}\n" +
                ",{\"STATION_CD\":\"1869\",\"STATION_NM\":\"망포\",\"LINE_NUM\":\"B\",\"FR_CODE\":\"K241\"}\n" +
                ",{\"STATION_CD\":\"1871\",\"STATION_NM\":\"수원시청\",\"LINE_NUM\":\"B\",\"FR_CODE\":\"K243\"}\n" +
                ",{\"STATION_CD\":\"1872\",\"STATION_NM\":\"매교\",\"LINE_NUM\":\"B\",\"FR_CODE\":\"K244\"}\n" +
                ",{\"STATION_CD\":\"1902\",\"STATION_NM\":\"도봉\",\"LINE_NUM\":\"1\",\"FR_CODE\":\"114\"}\n" +
                ",{\"STATION_CD\":\"1903\",\"STATION_NM\":\"도봉산\",\"LINE_NUM\":\"1\",\"FR_CODE\":\"113\"}\n" +
                ",{\"STATION_CD\":\"1905\",\"STATION_NM\":\"회룡\",\"LINE_NUM\":\"1\",\"FR_CODE\":\"111\"}\n" +
                ",{\"STATION_CD\":\"1906\",\"STATION_NM\":\"의정부\",\"LINE_NUM\":\"1\",\"FR_CODE\":\"110\"}\n" +
                ",{\"STATION_CD\":\"1908\",\"STATION_NM\":\"녹양\",\"LINE_NUM\":\"1\",\"FR_CODE\":\"108\"}\n" +
                ",{\"STATION_CD\":\"1909\",\"STATION_NM\":\"양주\",\"LINE_NUM\":\"1\",\"FR_CODE\":\"107\"}\n" +
                ",{\"STATION_CD\":\"1910\",\"STATION_NM\":\"덕계\",\"LINE_NUM\":\"1\",\"FR_CODE\":\"106\"}\n" +
                ",{\"STATION_CD\":\"1912\",\"STATION_NM\":\"지행\",\"LINE_NUM\":\"1\",\"FR_CODE\":\"104\"}\n" +
                ",{\"STATION_CD\":\"1913\",\"STATION_NM\":\"동두천중앙\",\"LINE_NUM\":\"1\",\"FR_CODE\":\"103\"}\n" +
                ",{\"STATION_CD\":\"1915\",\"STATION_NM\":\"동두천\",\"LINE_NUM\":\"1\",\"FR_CODE\":\"101\"}\n" +
                ",{\"STATION_CD\":\"1916\",\"STATION_NM\":\"소요산\",\"LINE_NUM\":\"1\",\"FR_CODE\":\"100\"}\n" +
                ",{\"STATION_CD\":\"1917\",\"STATION_NM\":\"마전(무정차)\",\"LINE_NUM\":\"1\",\"FR_CODE\":\"759\"}\n" +
                ",{\"STATION_CD\":\"1024\",\"STATION_NM\":\"한티\",\"LINE_NUM\":\"B\",\"FR_CODE\":\"k216\"}\n" +
                ",{\"STATION_CD\":\"1026\",\"STATION_NM\":\"구룡\",\"LINE_NUM\":\"B\",\"FR_CODE\":\"k218\"}\n" +
                ",{\"STATION_CD\":\"1027\",\"STATION_NM\":\"개포동\",\"LINE_NUM\":\"B\",\"FR_CODE\":\"k219\"}\n" +
                ",{\"STATION_CD\":\"1028\",\"STATION_NM\":\"대모산입구\",\"LINE_NUM\":\"B\",\"FR_CODE\":\"k220\"}\n" +
                ",{\"STATION_CD\":\"1031\",\"STATION_NM\":\"복정\",\"LINE_NUM\":\"B\",\"FR_CODE\":\"K222\"}\n" +
                ",{\"STATION_CD\":\"1032\",\"STATION_NM\":\"신길\",\"LINE_NUM\":\"1\",\"FR_CODE\":\"138\"}\n" +
                ",{\"STATION_CD\":\"1200\",\"STATION_NM\":\"회기\",\"LINE_NUM\":\"K\",\"FR_CODE\":\"K118\"}\n" +
                ",{\"STATION_CD\":\"1201\",\"STATION_NM\":\"중랑\",\"LINE_NUM\":\"K\",\"FR_CODE\":\"K119\"}\n" +
                ",{\"STATION_CD\":\"1202\",\"STATION_NM\":\"상봉\",\"LINE_NUM\":\"K\",\"FR_CODE\":\"K120\"}\n" +
                ",{\"STATION_CD\":\"1204\",\"STATION_NM\":\"양원\",\"LINE_NUM\":\"K\",\"FR_CODE\":\"K122\"}\n" +
                ",{\"STATION_CD\":\"1205\",\"STATION_NM\":\"구리\",\"LINE_NUM\":\"K\",\"FR_CODE\":\"K123\"}\n" +
                ",{\"STATION_CD\":\"1207\",\"STATION_NM\":\"양정\",\"LINE_NUM\":\"K\",\"FR_CODE\":\"K125\"}\n" +
                ",{\"STATION_CD\":\"1208\",\"STATION_NM\":\"덕소\",\"LINE_NUM\":\"K\",\"FR_CODE\":\"K126\"}\n" +
                ",{\"STATION_CD\":\"1210\",\"STATION_NM\":\"팔당\",\"LINE_NUM\":\"K\",\"FR_CODE\":\"K128\"}\n" +
                ",{\"STATION_CD\":\"1211\",\"STATION_NM\":\"운길산\",\"LINE_NUM\":\"K\",\"FR_CODE\":\"K129\"}\n" +
                ",{\"STATION_CD\":\"1213\",\"STATION_NM\":\"신원\",\"LINE_NUM\":\"K\",\"FR_CODE\":\"K131\"}\n" +
                ",{\"STATION_CD\":\"1215\",\"STATION_NM\":\"아신\",\"LINE_NUM\":\"K\",\"FR_CODE\":\"K133\"}\n" +
                ",{\"STATION_CD\":\"1216\",\"STATION_NM\":\"오빈\",\"LINE_NUM\":\"K\",\"FR_CODE\":\"K134\"}\n" +
                ",{\"STATION_CD\":\"1219\",\"STATION_NM\":\"용문\",\"LINE_NUM\":\"K\",\"FR_CODE\":\"K137\"}\n" +
                ",{\"STATION_CD\":\"1249\",\"STATION_NM\":\"대곡\",\"LINE_NUM\":\"K\",\"FR_CODE\":\"K322\"}\n" +
                ",{\"STATION_CD\":\"1252\",\"STATION_NM\":\"신촌(경의중앙선)\",\"LINE_NUM\":\"K\",\"FR_CODE\":\"P312\"}\n" +
                ",{\"STATION_CD\":\"1262\",\"STATION_NM\":\"공덕\",\"LINE_NUM\":\"K\",\"FR_CODE\":\"K312\"}\n" +
                ",{\"STATION_CD\":\"1263\",\"STATION_NM\":\"서강대\",\"LINE_NUM\":\"K\",\"FR_CODE\":\"K313\"}\n" +
                ",{\"STATION_CD\":\"1265\",\"STATION_NM\":\"가좌\",\"LINE_NUM\":\"K\",\"FR_CODE\":\"K315\"}\n" +
                ",{\"STATION_CD\":\"1266\",\"STATION_NM\":\"디지털미디어시티\",\"LINE_NUM\":\"K\",\"FR_CODE\":\"K316\"}\n" +
                ",{\"STATION_CD\":\"1268\",\"STATION_NM\":\"화전\",\"LINE_NUM\":\"K\",\"FR_CODE\":\"K318\"}\n" +
                ",{\"STATION_CD\":\"1270\",\"STATION_NM\":\"행신\",\"LINE_NUM\":\"K\",\"FR_CODE\":\"K320\"}\n" +
                ",{\"STATION_CD\":\"1272\",\"STATION_NM\":\"곡산\",\"LINE_NUM\":\"K\",\"FR_CODE\":\"K323\"}\n" +
                ",{\"STATION_CD\":\"1273\",\"STATION_NM\":\"백마\",\"LINE_NUM\":\"K\",\"FR_CODE\":\"K324\"}\n" +
                ",{\"STATION_CD\":\"1217\",\"STATION_NM\":\"양평(경의중앙선)\",\"LINE_NUM\":\"K\",\"FR_CODE\":\"K135\"}\n" +
                ",{\"STATION_CD\":\"1948\",\"STATION_NM\":\"원흥\",\"LINE_NUM\":\"3\",\"FR_CODE\":\"309\"}\n" +
                ",{\"STATION_CD\":\"0158\",\"STATION_NM\":\"청량리\",\"LINE_NUM\":\"1\",\"FR_CODE\":\"124\"}\n" +
                ",{\"STATION_CD\":\"0206\",\"STATION_NM\":\"신당\",\"LINE_NUM\":\"2\",\"FR_CODE\":\"206\"}\n" +
                ",{\"STATION_CD\":\"0211\",\"STATION_NM\":\"성수\",\"LINE_NUM\":\"2\",\"FR_CODE\":\"211\"}\n" +
                ",{\"STATION_CD\":\"0216\",\"STATION_NM\":\"잠실\",\"LINE_NUM\":\"2\",\"FR_CODE\":\"216\"}\n" +
                ",{\"STATION_CD\":\"0221\",\"STATION_NM\":\"역삼\",\"LINE_NUM\":\"2\",\"FR_CODE\":\"221\"}\n" +
                ",{\"STATION_CD\":\"0227\",\"STATION_NM\":\"낙성대\",\"LINE_NUM\":\"2\",\"FR_CODE\":\"227\"}\n" +
                ",{\"STATION_CD\":\"0233\",\"STATION_NM\":\"대림\",\"LINE_NUM\":\"2\",\"FR_CODE\":\"233\"}\n" +
                ",{\"STATION_CD\":\"0238\",\"STATION_NM\":\"합정\",\"LINE_NUM\":\"2\",\"FR_CODE\":\"238\"}\n" +
                ",{\"STATION_CD\":\"0317\",\"STATION_NM\":\"경복궁\",\"LINE_NUM\":\"3\",\"FR_CODE\":\"327\"}\n" +
                ",{\"STATION_CD\":\"0323\",\"STATION_NM\":\"약수\",\"LINE_NUM\":\"3\",\"FR_CODE\":\"333\"}\n" +
                ",{\"STATION_CD\":\"0328\",\"STATION_NM\":\"잠원\",\"LINE_NUM\":\"3\",\"FR_CODE\":\"338\"}\n" +
                ",{\"STATION_CD\":\"4210\",\"STATION_NM\":\"청라국제도시\",\"LINE_NUM\":\"A\",\"FR_CODE\":\"A071\"}\n" +
                ",{\"STATION_CD\":\"4126\",\"STATION_NM\":\"언주\",\"LINE_NUM\":\"9\",\"FR_CODE\":\"926\"}\n" +
                ",{\"STATION_CD\":\"4127\",\"STATION_NM\":\"선정릉\",\"LINE_NUM\":\"9\",\"FR_CODE\":\"927\"}\n" +
                ",{\"STATION_CD\":\"4128\",\"STATION_NM\":\"삼성중앙\",\"LINE_NUM\":\"9\",\"FR_CODE\":\"928\"}\n" +
                ",{\"STATION_CD\":\"4129\",\"STATION_NM\":\"봉은사\",\"LINE_NUM\":\"9\",\"FR_CODE\":\"929\"}\n" +
                ",{\"STATION_CD\":\"4130\",\"STATION_NM\":\"종합운동장\",\"LINE_NUM\":\"9\",\"FR_CODE\":\"930\"}\n" +
                ",{\"STATION_CD\":\"2812\",\"STATION_NM\":\"천호\",\"LINE_NUM\":\"8\",\"FR_CODE\":\"811\"}\n" +
                ",{\"STATION_CD\":\"1005\",\"STATION_NM\":\"대방\",\"LINE_NUM\":\"1\",\"FR_CODE\":\"137\"}\n" +
                ",{\"STATION_CD\":\"1015\",\"STATION_NM\":\"회기\",\"LINE_NUM\":\"1\",\"FR_CODE\":\"123\"}\n" +
                ",{\"STATION_CD\":\"1019\",\"STATION_NM\":\"광운대\",\"LINE_NUM\":\"1\",\"FR_CODE\":\"119\"}\n" +
                ",{\"STATION_CD\":\"1022\",\"STATION_NM\":\"창동\",\"LINE_NUM\":\"1\",\"FR_CODE\":\"116\"}\n" +
                ",{\"STATION_CD\":\"1401\",\"STATION_NM\":\"봉명\",\"LINE_NUM\":\"1\",\"FR_CODE\":\"P170\"}\n" +
                ",{\"STATION_CD\":\"1405\",\"STATION_NM\":\"배방\",\"LINE_NUM\":\"1\",\"FR_CODE\":\"P174\"}\n" +
                ",{\"STATION_CD\":\"1701\",\"STATION_NM\":\"구로\",\"LINE_NUM\":\"1\",\"FR_CODE\":\"141\"}\n" +
                ",{\"STATION_CD\":\"1704\",\"STATION_NM\":\"석수\",\"LINE_NUM\":\"1\",\"FR_CODE\":\"P145\"}\n" +
                ",{\"STATION_CD\":\"1707\",\"STATION_NM\":\"명학\",\"LINE_NUM\":\"1\",\"FR_CODE\":\"P148\"}\n" +
                ",{\"STATION_CD\":\"1710\",\"STATION_NM\":\"의왕\",\"LINE_NUM\":\"1\",\"FR_CODE\":\"P152\"}\n" +
                ",{\"STATION_CD\":\"1713\",\"STATION_NM\":\"수원\",\"LINE_NUM\":\"1\",\"FR_CODE\":\"P155\"}\n" +
                ",{\"STATION_CD\":\"1716\",\"STATION_NM\":\"병점\",\"LINE_NUM\":\"1\",\"FR_CODE\":\"P157\"}\n" +
                ",{\"STATION_CD\":\"1720\",\"STATION_NM\":\"진위\",\"LINE_NUM\":\"1\",\"FR_CODE\":\"P161\"}\n" +
                ",{\"STATION_CD\":\"1723\",\"STATION_NM\":\"지제\",\"LINE_NUM\":\"1\",\"FR_CODE\":\"P164\"}\n" +
                ",{\"STATION_CD\":\"1725\",\"STATION_NM\":\"성환\",\"LINE_NUM\":\"1\",\"FR_CODE\":\"P166\"}\n" +
                ",{\"STATION_CD\":\"1728\",\"STATION_NM\":\"천안\",\"LINE_NUM\":\"1\",\"FR_CODE\":\"P169\"}\n" +
                ",{\"STATION_CD\":\"1801\",\"STATION_NM\":\"개봉\",\"LINE_NUM\":\"1\",\"FR_CODE\":\"143\"}\n" +
                ",{\"STATION_CD\":\"1804\",\"STATION_NM\":\"부천\",\"LINE_NUM\":\"1\",\"FR_CODE\":\"148\"}\n" +
                ",{\"STATION_CD\":\"1807\",\"STATION_NM\":\"백운\",\"LINE_NUM\":\"1\",\"FR_CODE\":\"153\"}\n" +
                ",{\"STATION_CD\":\"1811\",\"STATION_NM\":\"동인천\",\"LINE_NUM\":\"1\",\"FR_CODE\":\"160\"}\n" +
                ",{\"STATION_CD\":\"1815\",\"STATION_NM\":\"부개\",\"LINE_NUM\":\"1\",\"FR_CODE\":\"151\"}\n" +
                ",{\"STATION_CD\":\"1269\",\"STATION_NM\":\"강매\",\"LINE_NUM\":\"K\",\"FR_CODE\":\"K319\"}\n" +
                ",{\"STATION_CD\":\"1821\",\"STATION_NM\":\"온수\",\"LINE_NUM\":\"1\",\"FR_CODE\":\"145\"}\n" +
                ",{\"STATION_CD\":\"1901\",\"STATION_NM\":\"방학\",\"LINE_NUM\":\"1\",\"FR_CODE\":\"115\"}\n" +
                ",{\"STATION_CD\":\"1904\",\"STATION_NM\":\"망월사\",\"LINE_NUM\":\"1\",\"FR_CODE\":\"112\"}\n" +
                ",{\"STATION_CD\":\"1907\",\"STATION_NM\":\"가능\",\"LINE_NUM\":\"1\",\"FR_CODE\":\"109\"}\n" +
                ",{\"STATION_CD\":\"1911\",\"STATION_NM\":\"덕정\",\"LINE_NUM\":\"1\",\"FR_CODE\":\"105\"}\n" +
                ",{\"STATION_CD\":\"1914\",\"STATION_NM\":\"보산\",\"LINE_NUM\":\"1\",\"FR_CODE\":\"102\"}\n" +
                ",{\"STATION_CD\":\"1950\",\"STATION_NM\":\"삼송\",\"LINE_NUM\":\"3\",\"FR_CODE\":\"318\"}\n" +
                ",{\"STATION_CD\":\"1953\",\"STATION_NM\":\"대곡\",\"LINE_NUM\":\"3\",\"FR_CODE\":\"315\"}\n" +
                ",{\"STATION_CD\":\"1956\",\"STATION_NM\":\"정발산\",\"LINE_NUM\":\"3\",\"FR_CODE\":\"312\"}\n" +
                ",{\"STATION_CD\":\"0412\",\"STATION_NM\":\"창동\",\"LINE_NUM\":\"4\",\"FR_CODE\":\"412\"}\n" +
                ",{\"STATION_CD\":\"0418\",\"STATION_NM\":\"성신여대입구\",\"LINE_NUM\":\"4\",\"FR_CODE\":\"418\"}\n" +
                ",{\"STATION_CD\":\"0427\",\"STATION_NM\":\"숙대입구\",\"LINE_NUM\":\"4\",\"FR_CODE\":\"427\"}\n" +
                ",{\"STATION_CD\":\"0433\",\"STATION_NM\":\"사당\",\"LINE_NUM\":\"4\",\"FR_CODE\":\"433\"}\n" +
                ",{\"STATION_CD\":\"1450\",\"STATION_NM\":\"선바위\",\"LINE_NUM\":\"4\",\"FR_CODE\":\"435\"}\n" +
                ",{\"STATION_CD\":\"1451\",\"STATION_NM\":\"경마공원\",\"LINE_NUM\":\"4\",\"FR_CODE\":\"436\"}\n" +
                ",{\"STATION_CD\":\"1454\",\"STATION_NM\":\"정부과천청사\",\"LINE_NUM\":\"4\",\"FR_CODE\":\"439\"}\n" +
                ",{\"STATION_CD\":\"1457\",\"STATION_NM\":\"범계\",\"LINE_NUM\":\"4\",\"FR_CODE\":\"442\"}\n" +
                ",{\"STATION_CD\":\"1753\",\"STATION_NM\":\"반월\",\"LINE_NUM\":\"4\",\"FR_CODE\":\"447\"}\n" +
                ",{\"STATION_CD\":\"1755\",\"STATION_NM\":\"한대앞\",\"LINE_NUM\":\"4\",\"FR_CODE\":\"449\"}\n" +
                ",{\"STATION_CD\":\"1758\",\"STATION_NM\":\"초지\",\"LINE_NUM\":\"4\",\"FR_CODE\":\"452\"}\n" +
                ",{\"STATION_CD\":\"1761\",\"STATION_NM\":\"정왕\",\"LINE_NUM\":\"4\",\"FR_CODE\":\"455\"}\n" +
                ",{\"STATION_CD\":\"2514\",\"STATION_NM\":\"송정\",\"LINE_NUM\":\"5\",\"FR_CODE\":\"513\"}\n" +
                ",{\"STATION_CD\":\"2519\",\"STATION_NM\":\"까치산\",\"LINE_NUM\":\"5\",\"FR_CODE\":\"518\"}\n" +
                ",{\"STATION_CD\":\"2523\",\"STATION_NM\":\"양평\",\"LINE_NUM\":\"5\",\"FR_CODE\":\"522\"}\n" +
                ",{\"STATION_CD\":\"2715\",\"STATION_NM\":\"노원\",\"LINE_NUM\":\"7\",\"FR_CODE\":\"713\"}\n" +
                ",{\"STATION_CD\":\"2716\",\"STATION_NM\":\"중계\",\"LINE_NUM\":\"7\",\"FR_CODE\":\"714\"}\n" +
                ",{\"STATION_CD\":\"2717\",\"STATION_NM\":\"하계\",\"LINE_NUM\":\"7\",\"FR_CODE\":\"715\"}\n" +
                ",{\"STATION_CD\":\"2718\",\"STATION_NM\":\"공릉\",\"LINE_NUM\":\"7\",\"FR_CODE\":\"716\"}\n" +
                ",{\"STATION_CD\":\"2719\",\"STATION_NM\":\"태릉입구\",\"LINE_NUM\":\"7\",\"FR_CODE\":\"717\"}\n" +
                ",{\"STATION_CD\":\"2721\",\"STATION_NM\":\"중화\",\"LINE_NUM\":\"7\",\"FR_CODE\":\"719\"}\n" +
                ",{\"STATION_CD\":\"2722\",\"STATION_NM\":\"상봉\",\"LINE_NUM\":\"7\",\"FR_CODE\":\"720\"}\n" +
                ",{\"STATION_CD\":\"2723\",\"STATION_NM\":\"면목\",\"LINE_NUM\":\"7\",\"FR_CODE\":\"721\"}\n" +
                ",{\"STATION_CD\":\"2725\",\"STATION_NM\":\"용마산\",\"LINE_NUM\":\"7\",\"FR_CODE\":\"723\"}\n" +
                ",{\"STATION_CD\":\"2726\",\"STATION_NM\":\"중곡\",\"LINE_NUM\":\"7\",\"FR_CODE\":\"724\"}\n" +
                ",{\"STATION_CD\":\"2727\",\"STATION_NM\":\"군자\",\"LINE_NUM\":\"7\",\"FR_CODE\":\"725\"}\n" +
                ",{\"STATION_CD\":\"2729\",\"STATION_NM\":\"건대입구\",\"LINE_NUM\":\"7\",\"FR_CODE\":\"727\"}\n" +
                ",{\"STATION_CD\":\"2730\",\"STATION_NM\":\"뚝섬유원지\",\"LINE_NUM\":\"7\",\"FR_CODE\":\"728\"}\n" +
                ",{\"STATION_CD\":\"2731\",\"STATION_NM\":\"청담\",\"LINE_NUM\":\"7\",\"FR_CODE\":\"729\"}\n" +
                ",{\"STATION_CD\":\"2732\",\"STATION_NM\":\"강남구청\",\"LINE_NUM\":\"7\",\"FR_CODE\":\"730\"}\n" +
                ",{\"STATION_CD\":\"2734\",\"STATION_NM\":\"논현\",\"LINE_NUM\":\"7\",\"FR_CODE\":\"732\"}\n" +
                ",{\"STATION_CD\":\"2735\",\"STATION_NM\":\"반포\",\"LINE_NUM\":\"7\",\"FR_CODE\":\"733\"}\n" +
                ",{\"STATION_CD\":\"2736\",\"STATION_NM\":\"고속터미널\",\"LINE_NUM\":\"7\",\"FR_CODE\":\"734\"}\n" +
                ",{\"STATION_CD\":\"2738\",\"STATION_NM\":\"총신대입구(이수)\",\"LINE_NUM\":\"7\",\"FR_CODE\":\"736\"}\n" +
                ",{\"STATION_CD\":\"2739\",\"STATION_NM\":\"남성\",\"LINE_NUM\":\"7\",\"FR_CODE\":\"737\"}\n" +
                ",{\"STATION_CD\":\"2740\",\"STATION_NM\":\"숭실대입구(살피재)\",\"LINE_NUM\":\"7\",\"FR_CODE\":\"738\"}\n" +
                ",{\"STATION_CD\":\"2742\",\"STATION_NM\":\"장승배기\",\"LINE_NUM\":\"7\",\"FR_CODE\":\"740\"}\n" +
                ",{\"STATION_CD\":\"2743\",\"STATION_NM\":\"신대방삼거리\",\"LINE_NUM\":\"7\",\"FR_CODE\":\"741\"}\n" +
                ",{\"STATION_CD\":\"2744\",\"STATION_NM\":\"보라매\",\"LINE_NUM\":\"7\",\"FR_CODE\":\"742\"}\n" +
                ",{\"STATION_CD\":\"2746\",\"STATION_NM\":\"대림\",\"LINE_NUM\":\"7\",\"FR_CODE\":\"744\"}\n" +
                ",{\"STATION_CD\":\"2747\",\"STATION_NM\":\"남구로\",\"LINE_NUM\":\"7\",\"FR_CODE\":\"745\"}\n" +
                ",{\"STATION_CD\":\"2748\",\"STATION_NM\":\"가산디지털단지\",\"LINE_NUM\":\"7\",\"FR_CODE\":\"746\"}\n" +
                ",{\"STATION_CD\":\"2749\",\"STATION_NM\":\"철산\",\"LINE_NUM\":\"7\",\"FR_CODE\":\"747\"}\n" +
                ",{\"STATION_CD\":\"2750\",\"STATION_NM\":\"광명사거리\",\"LINE_NUM\":\"7\",\"FR_CODE\":\"748\"}\n" +
                ",{\"STATION_CD\":\"2751\",\"STATION_NM\":\"천왕\",\"LINE_NUM\":\"7\",\"FR_CODE\":\"749\"}\n" +
                ",{\"STATION_CD\":\"2752\",\"STATION_NM\":\"온수\",\"LINE_NUM\":\"7\",\"FR_CODE\":\"750\"}\n" +
                ",{\"STATION_CD\":\"2754\",\"STATION_NM\":\"부천종합운동장\",\"LINE_NUM\":\"7\",\"FR_CODE\":\"752\"}\n" +
                ",{\"STATION_CD\":\"2755\",\"STATION_NM\":\"춘의\",\"LINE_NUM\":\"7\",\"FR_CODE\":\"753\"}\n" +
                ",{\"STATION_CD\":\"2757\",\"STATION_NM\":\"부천시청\",\"LINE_NUM\":\"7\",\"FR_CODE\":\"755\"}\n" +
                ",{\"STATION_CD\":\"2758\",\"STATION_NM\":\"상동\",\"LINE_NUM\":\"7\",\"FR_CODE\":\"756\"}\n" +
                ",{\"STATION_CD\":\"2759\",\"STATION_NM\":\"삼산체육관\",\"LINE_NUM\":\"7\",\"FR_CODE\":\"757\"}\n" +
                ",{\"STATION_CD\":\"2761\",\"STATION_NM\":\"부평구청\",\"LINE_NUM\":\"7\",\"FR_CODE\":\"759\"}\n" +
                ",{\"STATION_CD\":\"2811\",\"STATION_NM\":\"암사\",\"LINE_NUM\":\"8\",\"FR_CODE\":\"810\"}\n" +
                ",{\"STATION_CD\":\"2813\",\"STATION_NM\":\"강동구청\",\"LINE_NUM\":\"8\",\"FR_CODE\":\"812\"}\n" +
                ",{\"STATION_CD\":\"2815\",\"STATION_NM\":\"잠실\",\"LINE_NUM\":\"8\",\"FR_CODE\":\"814\"}\n" +
                ",{\"STATION_CD\":\"2816\",\"STATION_NM\":\"석촌\",\"LINE_NUM\":\"8\",\"FR_CODE\":\"815\"}\n" +
                ",{\"STATION_CD\":\"2817\",\"STATION_NM\":\"송파\",\"LINE_NUM\":\"8\",\"FR_CODE\":\"816\"}\n" +
                ",{\"STATION_CD\":\"2819\",\"STATION_NM\":\"문정\",\"LINE_NUM\":\"8\",\"FR_CODE\":\"818\"}\n" +
                ",{\"STATION_CD\":\"2820\",\"STATION_NM\":\"장지\",\"LINE_NUM\":\"8\",\"FR_CODE\":\"819\"}\n" +
                ",{\"STATION_CD\":\"2821\",\"STATION_NM\":\"복정\",\"LINE_NUM\":\"8\",\"FR_CODE\":\"820\"}\n" +
                ",{\"STATION_CD\":\"2822\",\"STATION_NM\":\"산성\",\"LINE_NUM\":\"8\",\"FR_CODE\":\"821\"}\n" +
                ",{\"STATION_CD\":\"2824\",\"STATION_NM\":\"단대오거리\",\"LINE_NUM\":\"8\",\"FR_CODE\":\"823\"}\n" +
                ",{\"STATION_CD\":\"2825\",\"STATION_NM\":\"신흥\",\"LINE_NUM\":\"8\",\"FR_CODE\":\"824\"}\n" +
                ",{\"STATION_CD\":\"2827\",\"STATION_NM\":\"모란\",\"LINE_NUM\":\"8\",\"FR_CODE\":\"826\"}\n" +
                ",{\"STATION_CD\":\"4101\",\"STATION_NM\":\"개화\",\"LINE_NUM\":\"9\",\"FR_CODE\":\"901\"}\n" +
                ",{\"STATION_CD\":\"4103\",\"STATION_NM\":\"공항시장\",\"LINE_NUM\":\"9\",\"FR_CODE\":\"903\"}\n" +
                ",{\"STATION_CD\":\"4104\",\"STATION_NM\":\"신방화\",\"LINE_NUM\":\"9\",\"FR_CODE\":\"904\"}\n" +
                ",{\"STATION_CD\":\"4105\",\"STATION_NM\":\"마곡나루\",\"LINE_NUM\":\"9\",\"FR_CODE\":\"905\"}\n" +
                ",{\"STATION_CD\":\"4107\",\"STATION_NM\":\"가양\",\"LINE_NUM\":\"9\",\"FR_CODE\":\"907\"}\n" +
                ",{\"STATION_CD\":\"4108\",\"STATION_NM\":\"증미\",\"LINE_NUM\":\"9\",\"FR_CODE\":\"908\"}\n" +
                ",{\"STATION_CD\":\"4109\",\"STATION_NM\":\"등촌\",\"LINE_NUM\":\"9\",\"FR_CODE\":\"909\"}\n" +
                ",{\"STATION_CD\":\"4111\",\"STATION_NM\":\"신목동\",\"LINE_NUM\":\"9\",\"FR_CODE\":\"911\"}\n" +
                ",{\"STATION_CD\":\"4112\",\"STATION_NM\":\"선유도\",\"LINE_NUM\":\"9\",\"FR_CODE\":\"912\"}\n" +
                ",{\"STATION_CD\":\"4113\",\"STATION_NM\":\"당산\",\"LINE_NUM\":\"9\",\"FR_CODE\":\"913\"}\n" +
                ",{\"STATION_CD\":\"4115\",\"STATION_NM\":\"여의도\",\"LINE_NUM\":\"9\",\"FR_CODE\":\"915\"}\n" +
                ",{\"STATION_CD\":\"4116\",\"STATION_NM\":\"샛강\",\"LINE_NUM\":\"9\",\"FR_CODE\":\"916\"}\n" +
                ",{\"STATION_CD\":\"4117\",\"STATION_NM\":\"노량진\",\"LINE_NUM\":\"9\",\"FR_CODE\":\"917\"}\n" +
                ",{\"STATION_CD\":\"4118\",\"STATION_NM\":\"노들\",\"LINE_NUM\":\"9\",\"FR_CODE\":\"918\"}\n" +
                ",{\"STATION_CD\":\"4119\",\"STATION_NM\":\"흑석\",\"LINE_NUM\":\"9\",\"FR_CODE\":\"919\"}\n" +
                ",{\"STATION_CD\":\"4120\",\"STATION_NM\":\"동작\",\"LINE_NUM\":\"9\",\"FR_CODE\":\"920\"}\n" +
                ",{\"STATION_CD\":\"4121\",\"STATION_NM\":\"구반포\",\"LINE_NUM\":\"9\",\"FR_CODE\":\"921\"}\n" +
                ",{\"STATION_CD\":\"4122\",\"STATION_NM\":\"신반포\",\"LINE_NUM\":\"9\",\"FR_CODE\":\"922\"}\n" +
                ",{\"STATION_CD\":\"4123\",\"STATION_NM\":\"고속터미널\",\"LINE_NUM\":\"9\",\"FR_CODE\":\"923\"}\n" +
                ",{\"STATION_CD\":\"4124\",\"STATION_NM\":\"사평\",\"LINE_NUM\":\"9\",\"FR_CODE\":\"924\"}\n" +
                ",{\"STATION_CD\":\"4125\",\"STATION_NM\":\"신논현\",\"LINE_NUM\":\"9\",\"FR_CODE\":\"925\"}\n" +
                ",{\"STATION_CD\":\"4207\",\"STATION_NM\":\"김포공항\",\"LINE_NUM\":\"A\",\"FR_CODE\":\"A05\"}\n" +
                ",{\"STATION_CD\":\"4209\",\"STATION_NM\":\"검암\",\"LINE_NUM\":\"A\",\"FR_CODE\":\"A07\"}\n" +
                ",{\"STATION_CD\":\"4211\",\"STATION_NM\":\"운서\",\"LINE_NUM\":\"A\",\"FR_CODE\":\"A08\"}\n" +
                ",{\"STATION_CD\":\"4213\",\"STATION_NM\":\"인천국제공항\",\"LINE_NUM\":\"A\",\"FR_CODE\":\"A10\"}\n" +
                ",{\"STATION_CD\":\"4307\",\"STATION_NM\":\"강남\",\"LINE_NUM\":\"S\",\"FR_CODE\":\"D7\"}\n" +
                ",{\"STATION_CD\":\"4308\",\"STATION_NM\":\"양재\",\"LINE_NUM\":\"S\",\"FR_CODE\":\"D8\"}\n" +
                ",{\"STATION_CD\":\"4309\",\"STATION_NM\":\"양재시민의숲\",\"LINE_NUM\":\"S\",\"FR_CODE\":\"D9\"}\n" +
                ",{\"STATION_CD\":\"4311\",\"STATION_NM\":\"판교\",\"LINE_NUM\":\"S\",\"FR_CODE\":\"D11\"}\n" +
                ",{\"STATION_CD\":\"4312\",\"STATION_NM\":\"정자\",\"LINE_NUM\":\"S\",\"FR_CODE\":\"D12\"}\n" +
                ",{\"STATION_CD\":\"1845\",\"STATION_NM\":\"왕십리\",\"LINE_NUM\":\"B\",\"FR_CODE\":\"K210\"}\n" +
                ",{\"STATION_CD\":\"1849\",\"STATION_NM\":\"강남구청\",\"LINE_NUM\":\"B\",\"FR_CODE\":\"K213\"}\n" +
                ",{\"STATION_CD\":\"1852\",\"STATION_NM\":\"태평\",\"LINE_NUM\":\"B\",\"FR_CODE\":\"K224\"}\n" +
                ",{\"STATION_CD\":\"1855\",\"STATION_NM\":\"서현\",\"LINE_NUM\":\"B\",\"FR_CODE\":\"K228\"}\n" +
                ",{\"STATION_CD\":\"1858\",\"STATION_NM\":\"미금\",\"LINE_NUM\":\"B\",\"FR_CODE\":\"K231\"}\n" +
                ",{\"STATION_CD\":\"1861\",\"STATION_NM\":\"보정\",\"LINE_NUM\":\"B\",\"FR_CODE\":\"K234\"}\n" +
                ",{\"STATION_CD\":\"1864\",\"STATION_NM\":\"신갈\",\"LINE_NUM\":\"B\",\"FR_CODE\":\"K236\"}\n" +
                ",{\"STATION_CD\":\"1867\",\"STATION_NM\":\"청명\",\"LINE_NUM\":\"B\",\"FR_CODE\":\"K239\"}\n" +
                ",{\"STATION_CD\":\"1870\",\"STATION_NM\":\"매탄권선\",\"LINE_NUM\":\"B\",\"FR_CODE\":\"K242\"}\n" +
                ",{\"STATION_CD\":\"2720\",\"STATION_NM\":\"먹골\",\"LINE_NUM\":\"7\",\"FR_CODE\":\"718\"}\n" +
                ",{\"STATION_CD\":\"2724\",\"STATION_NM\":\"사가정\",\"LINE_NUM\":\"7\",\"FR_CODE\":\"722\"}\n" +
                ",{\"STATION_CD\":\"2728\",\"STATION_NM\":\"어린이대공원(세종대)\",\"LINE_NUM\":\"7\",\"FR_CODE\":\"726\"}\n" +
                ",{\"STATION_CD\":\"2733\",\"STATION_NM\":\"학동\",\"LINE_NUM\":\"7\",\"FR_CODE\":\"731\"}\n" +
                ",{\"STATION_CD\":\"2737\",\"STATION_NM\":\"내방\",\"LINE_NUM\":\"7\",\"FR_CODE\":\"735\"}\n" +
                ",{\"STATION_CD\":\"2741\",\"STATION_NM\":\"상도(중앙대앞)\",\"LINE_NUM\":\"7\",\"FR_CODE\":\"739\"}\n" +
                ",{\"STATION_CD\":\"2745\",\"STATION_NM\":\"신풍\",\"LINE_NUM\":\"7\",\"FR_CODE\":\"743\"}\n" +
                ",{\"STATION_CD\":\"2753\",\"STATION_NM\":\"까치울\",\"LINE_NUM\":\"7\",\"FR_CODE\":\"751\"}\n" +
                ",{\"STATION_CD\":\"2756\",\"STATION_NM\":\"신중동\",\"LINE_NUM\":\"7\",\"FR_CODE\":\"754\"}\n" +
                ",{\"STATION_CD\":\"2760\",\"STATION_NM\":\"굴포천\",\"LINE_NUM\":\"7\",\"FR_CODE\":\"758\"}\n" +
                ",{\"STATION_CD\":\"2814\",\"STATION_NM\":\"몽촌토성(평화의문)\",\"LINE_NUM\":\"8\",\"FR_CODE\":\"813\"}\n" +
                ",{\"STATION_CD\":\"2818\",\"STATION_NM\":\"가락시장\",\"LINE_NUM\":\"8\",\"FR_CODE\":\"817\"}\n" +
                ",{\"STATION_CD\":\"2823\",\"STATION_NM\":\"남한산성입구(성남법원, 검찰청)\",\"LINE_NUM\":\"8\",\"FR_CODE\":\"822\"}\n" +
                ",{\"STATION_CD\":\"1277\",\"STATION_NM\":\"야당\",\"LINE_NUM\":\"K\",\"FR_CODE\":\"K328\"}\n" +
                ",{\"STATION_CD\":\"2527\",\"STATION_NM\":\"여의도\",\"LINE_NUM\":\"5\",\"FR_CODE\":\"526\"}\n" +
                ",{\"STATION_CD\":\"2532\",\"STATION_NM\":\"충정로\",\"LINE_NUM\":\"5\",\"FR_CODE\":\"531\"}\n" +
                ",{\"STATION_CD\":\"2537\",\"STATION_NM\":\"동대문역사문화공원\",\"LINE_NUM\":\"5\",\"FR_CODE\":\"536\"}\n" +
                ",{\"STATION_CD\":\"2543\",\"STATION_NM\":\"답십리\",\"LINE_NUM\":\"5\",\"FR_CODE\":\"542\"}\n" +
                ",{\"STATION_CD\":\"2547\",\"STATION_NM\":\"광나루\",\"LINE_NUM\":\"5\",\"FR_CODE\":\"546\"}\n" +
                ",{\"STATION_CD\":\"2551\",\"STATION_NM\":\"굽은다리\",\"LINE_NUM\":\"5\",\"FR_CODE\":\"550\"}\n" +
                ",{\"STATION_CD\":\"2556\",\"STATION_NM\":\"올림픽공원\",\"LINE_NUM\":\"5\",\"FR_CODE\":\"P550\"}\n" +
                ",{\"STATION_CD\":\"2561\",\"STATION_NM\":\"마천\",\"LINE_NUM\":\"5\",\"FR_CODE\":\"P555\"}\n" +
                ",{\"STATION_CD\":\"2614\",\"STATION_NM\":\"독바위\",\"LINE_NUM\":\"6\",\"FR_CODE\":\"613\"}\n" +
                ",{\"STATION_CD\":\"2619\",\"STATION_NM\":\"디지털미디어시티\",\"LINE_NUM\":\"6\",\"FR_CODE\":\"618\"}\n" +
                ",{\"STATION_CD\":\"2625\",\"STATION_NM\":\"광흥창\",\"LINE_NUM\":\"6\",\"FR_CODE\":\"624\"}\n" +
                ",{\"STATION_CD\":\"2630\",\"STATION_NM\":\"녹사평\",\"LINE_NUM\":\"6\",\"FR_CODE\":\"629\"}\n" +
                ",{\"STATION_CD\":\"2635\",\"STATION_NM\":\"청구\",\"LINE_NUM\":\"6\",\"FR_CODE\":\"634\"}\n" +
                ",{\"STATION_CD\":\"2640\",\"STATION_NM\":\"안암\",\"LINE_NUM\":\"6\",\"FR_CODE\":\"639\"}\n" +
                ",{\"STATION_CD\":\"2644\",\"STATION_NM\":\"돌곶이\",\"LINE_NUM\":\"6\",\"FR_CODE\":\"643\"}\n" +
                ",{\"STATION_CD\":\"2648\",\"STATION_NM\":\"봉화산\",\"LINE_NUM\":\"6\",\"FR_CODE\":\"647\"}\n" +
                ",{\"STATION_CD\":\"2826\",\"STATION_NM\":\"수진\",\"LINE_NUM\":\"8\",\"FR_CODE\":\"825\"}\n" +
                ",{\"STATION_CD\":\"4102\",\"STATION_NM\":\"김포공항\",\"LINE_NUM\":\"9\",\"FR_CODE\":\"902\"}\n" +
                ",{\"STATION_CD\":\"4106\",\"STATION_NM\":\"양천향교\",\"LINE_NUM\":\"9\",\"FR_CODE\":\"906\"}\n" +
                ",{\"STATION_CD\":\"4110\",\"STATION_NM\":\"염창\",\"LINE_NUM\":\"9\",\"FR_CODE\":\"910\"}\n" +
                ",{\"STATION_CD\":\"4114\",\"STATION_NM\":\"국회의사당\",\"LINE_NUM\":\"9\",\"FR_CODE\":\"914\"}\n" +
                ",{\"STATION_CD\":\"4208\",\"STATION_NM\":\"계양\",\"LINE_NUM\":\"A\",\"FR_CODE\":\"A06\"}\n" +
                ",{\"STATION_CD\":\"4212\",\"STATION_NM\":\"공항화물청사\",\"LINE_NUM\":\"A\",\"FR_CODE\":\"A09\"}\n" +
                ",{\"STATION_CD\":\"4310\",\"STATION_NM\":\"청계산입구\",\"LINE_NUM\":\"S\",\"FR_CODE\":\"D10\"}\n" +
                ",{\"STATION_CD\":\"4610\",\"STATION_NM\":\"경기도청북부청사\",\"LINE_NUM\":\"U\",\"FR_CODE\":\"U120\"}\n" +
                ",{\"STATION_CD\":\"1011\",\"STATION_NM\":\"옥수\",\"LINE_NUM\":\"K\",\"FR_CODE\":\"K114\"}\n" +
                ",{\"STATION_CD\":\"1014\",\"STATION_NM\":\"청량리\",\"LINE_NUM\":\"K\",\"FR_CODE\":\"K117\"}\n" +
                ",{\"STATION_CD\":\"1025\",\"STATION_NM\":\"도곡\",\"LINE_NUM\":\"B\",\"FR_CODE\":\"K217\"}\n" +
                ",{\"STATION_CD\":\"1030\",\"STATION_NM\":\"수서\",\"LINE_NUM\":\"B\",\"FR_CODE\":\"K221\"}\n" +
                ",{\"STATION_CD\":\"1203\",\"STATION_NM\":\"망우\",\"LINE_NUM\":\"K\",\"FR_CODE\":\"K121\"}\n" +
                ",{\"STATION_CD\":\"1206\",\"STATION_NM\":\"도농\",\"LINE_NUM\":\"K\",\"FR_CODE\":\"K124\"}\n" +
                ",{\"STATION_CD\":\"1209\",\"STATION_NM\":\"도심\",\"LINE_NUM\":\"K\",\"FR_CODE\":\"K127\"}\n" +
                ",{\"STATION_CD\":\"1212\",\"STATION_NM\":\"양수\",\"LINE_NUM\":\"K\",\"FR_CODE\":\"K130\"}\n" +
                ",{\"STATION_CD\":\"1214\",\"STATION_NM\":\"국수\",\"LINE_NUM\":\"K\",\"FR_CODE\":\"K132\"}\n" +
                ",{\"STATION_CD\":\"1218\",\"STATION_NM\":\"원덕\",\"LINE_NUM\":\"K\",\"FR_CODE\":\"K136\"}\n" +
                ",{\"STATION_CD\":\"1251\",\"STATION_NM\":\"서울\",\"LINE_NUM\":\"K\",\"FR_CODE\":\"P313\"}\n" +
                ",{\"STATION_CD\":\"1264\",\"STATION_NM\":\"홍대입구\",\"LINE_NUM\":\"K\",\"FR_CODE\":\"K314\"}\n" +
                ",{\"STATION_CD\":\"1267\",\"STATION_NM\":\"수색\",\"LINE_NUM\":\"K\",\"FR_CODE\":\"K317\"}\n" +
                ",{\"STATION_CD\":\"1271\",\"STATION_NM\":\"능곡\",\"LINE_NUM\":\"K\",\"FR_CODE\":\"K321\"}\n" +
                ",{\"STATION_CD\":\"1274\",\"STATION_NM\":\"풍산\",\"LINE_NUM\":\"K\",\"FR_CODE\":\"K325\"}\n" +
                ",{\"STATION_CD\":\"1278\",\"STATION_NM\":\"운정\",\"LINE_NUM\":\"K\",\"FR_CODE\":\"K329\"}\n" +
                ",{\"STATION_CD\":\"1282\",\"STATION_NM\":\"월롱\",\"LINE_NUM\":\"K\",\"FR_CODE\":\"K333\"}\n" +
                ",{\"STATION_CD\":\"1290\",\"STATION_NM\":\"용산\",\"LINE_NUM\":\"K\",\"FR_CODE\":\"K110\"}\n" +
                ",{\"STATION_CD\":\"1294\",\"STATION_NM\":\"디지털미디어시티\",\"LINE_NUM\":\"A\",\"FR_CODE\":\"A04\"}\n" +
                ",{\"STATION_CD\":\"1311\",\"STATION_NM\":\"신내\",\"LINE_NUM\":\"G\",\"FR_CODE\":\"P122\"}\n" +
                ",{\"STATION_CD\":\"1314\",\"STATION_NM\":\"퇴계원\",\"LINE_NUM\":\"G\",\"FR_CODE\":\"P125\"}\n" +
                ",{\"STATION_CD\":\"1317\",\"STATION_NM\":\"평내호평\",\"LINE_NUM\":\"G\",\"FR_CODE\":\"P128\"}\n" +
                ",{\"STATION_CD\":\"1320\",\"STATION_NM\":\"대성리\",\"LINE_NUM\":\"G\",\"FR_CODE\":\"P131\"}\n" +
                ",{\"STATION_CD\":\"1323\",\"STATION_NM\":\"가평\",\"LINE_NUM\":\"G\",\"FR_CODE\":\"P134\"}\n" +
                ",{\"STATION_CD\":\"1326\",\"STATION_NM\":\"강촌\",\"LINE_NUM\":\"G\",\"FR_CODE\":\"P137\"}\n" +
                ",{\"STATION_CD\":\"1329\",\"STATION_NM\":\"춘천\",\"LINE_NUM\":\"G\",\"FR_CODE\":\"P140\"}\n" +
                "]";

        JSONArray jAr;
        Station[] stArr = new Station[1000];

        boolean[] flag = new boolean[1000];
        int[] index = new int[1000];
        int t=0;

        String s_n="";
        String writeString="";

        //static int[] line_nums = new int[10];
        ArrayList<String> line_nums = new ArrayList<String>();
        ArrayList<String> station_nums = new ArrayList<String>();
        ArrayList<String> origin_stnnums = new ArrayList<String>();



        SoundSearcher ss = new SoundSearcher();

        appNetwork appnet = null;

        ListView listView;
        ArrayList<String> list = new ArrayList<String>();
        ArrayAdapter<String> adapter;

        //최근검색목록 관련 변수들
        ListView r_listView;
        ArrayList<String> r_list = new ArrayList<String>();
        ArrayAdapter<String> r_adapter;

        EditText searching_edit;

        int tempSize;

        int mode = 1;
        //최근검색목록이 리스트에 보이는 모드
        private final static int RECENT_MODE = 1;
        //검색하는 리스트가 리스트에 보이는 모드
        private final static int SEARCHING_MODE = 2;

    //파일 경로
    //final String path = "/sdcard/iseoulsubwayu/";



        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_search);

                jsonParse();

                searching_edit = (EditText) findViewById(R.id.search_edit);

                listView = (ListView) findViewById(R.id.listView);
                adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(onClickListItem);

                //최근검색목록 관련 변수들
                r_listView = (ListView) findViewById(R.id.r_listView);
                r_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, r_list);
                r_listView.setAdapter(r_adapter);
                r_listView.setOnItemClickListener(r_onClickListItem);

                //처음에는 리센트모드
                mode = RECENT_MODE;
                readRecentSearchingList();

                //최근검색목록 visible하게
                r_listView.setVisibility(View.VISIBLE);
                listView.setVisibility(View.GONE);
                r_listView.invalidate();
                listView.invalidate();


                final Button btnErase = (Button)findViewById(R.id.btnErase);
                btnErase.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {


                                        //지우기
                                    /*
                                        File file = new File("recent_searching_list.txt");
                                        boolean deleted = file.delete();
                                        String path = file.getPath();
                                        Log.e("deleted",""+deleted+", "+path);
                                        writeString="";
                                        */
                                    eraseRecentSearchingList();

                                        r_list.clear();
                                        r_adapter.notifyDataSetChanged();

                                        //테스트용 성공메시지
                                        //Log.e("Erase", "erase");
                                       // Toast.makeText(getBaseContext(), "최근검색목록을 지웠습니다",Toast.LENGTH_SHORT).show();

                        }
                });


                appnet = new appNetwork(this);
                ConnectivityManager cManager;
                NetworkInfo mobile;
                NetworkInfo wifi;

                cManager=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
                mobile = cManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
                wifi = cManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

                if(mobile.isConnected() || wifi.isConnected()){
                } else {
                        AlertDialog.Builder alert = new AlertDialog.Builder(SearchActivity.this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
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



                for (int i = 0; i < 1; i++) {
                        Log.i("AAA", stArr[i].getName());
                        Log.i("AAA", stArr[i].getLine_num());
                        Log.i("AAA", "" + stArr[i].getFr_code());
                }

                TextWatcher watcher = new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                                if(searching_edit.getText().toString().equals(""))
                                        mode = RECENT_MODE;
                                else
                                        mode = SEARCHING_MODE;

                                if(mode == SEARCHING_MODE) {
                                        btnErase.setVisibility(View.INVISIBLE);
                                        r_listView.setVisibility(View.GONE);
                                        listView.setVisibility(View.VISIBLE);
                                        r_listView.invalidate();
                                        listView.invalidate();
                                }else if(mode == RECENT_MODE){
                                        btnErase.setVisibility(View.VISIBLE);
                                        r_listView.setVisibility(View.VISIBLE);
                                        listView.setVisibility(View.GONE);
                                        r_listView.invalidate();
                                        listView.invalidate();
                                }


                                boolean last = true;
                                for(int i=jAr.length()-1;i>=0;i--){
                                        String temp = String.valueOf(searching_edit.getText());

                                        if(temp.equals(""))
                                        {
                                                list.clear();
                                                t=0;
                                                flag[i]=false;
                                                index[i]=0;
                                                adapter.notifyDataSetChanged();
                                        }else if(!ss.matchString(stArr[i].getName(), temp) && flag[i]){

                                                if(last){
                                                        list.clear();
                                                        t=0;
                                                        last=false;
                                                }
                                                //list.remove(index[i]);
                                                //t--;
                                                flag[i] = false;
                                                //index[i] = 0;
                                                adapter.notifyDataSetChanged();
                                        }
                                }
                                boolean first=true;
                                for (int i = 0; i < jAr.length(); i++) {
                                        String temp = String.valueOf(searching_edit.getText());

                                        if(ss.matchString(stArr[i].getName(), temp) && !flag[i] && !temp.equals("")){
                                                flag[i] = true;
                                                index[i] = t;
                                                line_nums.clear();
                                                station_nums.clear();
                                                origin_stnnums.clear();
                                                Log.e("line&stationnum Clear.", "dd");
                                                String tempnum = stArr[i].getLine_num();
                                                Log.e("tempnum : ", tempnum);
                                                if(tempnum.equals("B")) {
                                                        list.add("분당선" + " " + stArr[i].getName());
                                                        line_nums.add(stArr[i].getLine_num());
                                                        station_nums.add(stArr[i].getFr_code());
                                                        origin_stnnums.add(stArr[i].getOrigin_stnnum());
                                                } else if(tempnum.equals("A")) {
                                                        list.add("공항철도" + " " + stArr[i].getName());
                                                        line_nums.add(stArr[i].getLine_num());
                                                        station_nums.add(stArr[i].getFr_code());
                                                        origin_stnnums.add(stArr[i].getOrigin_stnnum());
                                                } else if(tempnum.equals("K")) {
                                                        list.add("경의중앙선" + " " + stArr[i].getName());
                                                        line_nums.add(stArr[i].getLine_num());
                                                        station_nums.add(stArr[i].getFr_code());
                                                        origin_stnnums.add(stArr[i].getOrigin_stnnum());
                                                } else if(tempnum.equals("G")) {
                                                        list.add("경춘선" + " " + stArr[i].getName());
                                                        line_nums.add(stArr[i].getLine_num());
                                                        station_nums.add(stArr[i].getFr_code());
                                                        origin_stnnums.add(stArr[i].getOrigin_stnnum());
                                                } else if(tempnum.equals("S")) {
                                                        list.add("신분당선" + " " + stArr[i].getName());
                                                        line_nums.add(stArr[i].getLine_num());
                                                        station_nums.add(stArr[i].getFr_code());
                                                        origin_stnnums.add(stArr[i].getOrigin_stnnum());
                                                } else if(tempnum.equals("E")) {
                                                        list.add("에버라인" + " " + stArr[i].getName());
                                                        line_nums.add(stArr[i].getLine_num());
                                                        station_nums.add(stArr[i].getFr_code());
                                                        origin_stnnums.add(stArr[i].getOrigin_stnnum());
                                                } else if(tempnum.equals("I")) {
                                                        list.add("인천1호선" + " " + stArr[i].getName());
                                                        line_nums.add(stArr[i].getLine_num());
                                                        station_nums.add(stArr[i].getFr_code());
                                                        origin_stnnums.add(stArr[i].getOrigin_stnnum());
                                                } else {
                                                        list.add(tempnum + "호선" + " " + stArr[i].getName());
                                                        line_nums.add(stArr[i].getLine_num());
                                                        station_nums.add(stArr[i].getFr_code());
                                                        origin_stnnums.add(stArr[i].getOrigin_stnnum());
                                                }
                                                Log.e("line_nums Added : ", stArr[i].getLine_num());
                                                Log.e("List Size : ", line_nums.size() + "");
                                                t++;
                                                adapter.notifyDataSetChanged();


                                        }else if(ss.matchString(stArr[i].getName(), temp) && flag[i] && !temp.equals("")){
                                                Log.i("BBB", "BBB");

                                                if(first) {
                                                        list.clear();
                                                        first = false;
                                                        t=0;
                                                }

                                                index[i]=t;
                                                String tempnum = stArr[i].getLine_num();
                                                Log.e("tempnum : ", tempnum);
                                                if(tempnum.equals("B")) {
                                                        list.add("분당선" + " " + stArr[i].getName());
                                                        line_nums.add(stArr[i].getLine_num());
                                                        station_nums.add(stArr[i].getFr_code());
                                                        origin_stnnums.add(stArr[i].getOrigin_stnnum());
                                                } else if(tempnum.equals("A")) {
                                                        list.add("공항철도" + " " + stArr[i].getName());
                                                        line_nums.add(stArr[i].getLine_num());
                                                        station_nums.add(stArr[i].getFr_code());
                                                        origin_stnnums.add(stArr[i].getOrigin_stnnum());
                                                } else if(tempnum.equals("K")) {
                                                        list.add("경의중앙선" + " " + stArr[i].getName());
                                                        line_nums.add(stArr[i].getLine_num());
                                                        station_nums.add(stArr[i].getFr_code());
                                                        origin_stnnums.add(stArr[i].getOrigin_stnnum());
                                                } else if(tempnum.equals("G")) {
                                                        list.add("경춘선" + " " + stArr[i].getName());
                                                        line_nums.add(stArr[i].getLine_num());
                                                        station_nums.add(stArr[i].getFr_code());
                                                        origin_stnnums.add(stArr[i].getOrigin_stnnum());
                                                } else if(tempnum.equals("S")) {
                                                        list.add("신분당선" + " " + stArr[i].getName());
                                                        line_nums.add(stArr[i].getLine_num());
                                                        station_nums.add(stArr[i].getFr_code());
                                                        origin_stnnums.add(stArr[i].getOrigin_stnnum());
                                                } else if(tempnum.equals("E")) {
                                                        list.add("에버라인" + " " + stArr[i].getName());
                                                        line_nums.add(stArr[i].getLine_num());
                                                        station_nums.add(stArr[i].getFr_code());
                                                        origin_stnnums.add(stArr[i].getOrigin_stnnum());
                                                } else if(tempnum.equals("I")) {
                                                        list.add("인천1호선" + " " + stArr[i].getName());
                                                        line_nums.add(stArr[i].getLine_num());
                                                        station_nums.add(stArr[i].getFr_code());
                                                        origin_stnnums.add(stArr[i].getOrigin_stnnum());
                                                } else {
                                                        list.add(tempnum + "호선" + " " + stArr[i].getName());
                                                        line_nums.add(stArr[i].getLine_num());
                                                        station_nums.add(stArr[i].getFr_code());
                                                        origin_stnnums.add(stArr[i].getOrigin_stnnum());
                                                }
                                                Log.e("station_Number : ", stArr[i].getFr_code());
                                                Log.e("line_nums Added[2] : ", stArr[i].getLine_num());
                                                Log.e("List Size : ", line_nums.size() + "");
                                                Log.e("real_List_size : ", list.size() + "");
                                                t++;
                                                adapter.notifyDataSetChanged();
                                        }
                                }
                        }

                        @Override
                        public void afterTextChanged(Editable s) {

                        }
                };
                searching_edit.addTextChangedListener(watcher);



        }

        private AdapterView.OnItemClickListener onClickListItem = new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                    int lineindex = arg2;
                    Log.e("lineindex",""+lineindex);
                    s_n = adapter.getItem(arg2);
                    String ls = s_n;    //ex) 2호선 신도림 lineNum+StationName
                    writeRecentSearchingList(ls);
                    String[] tempArr;
                    tempArr = s_n.split(" ");
                    String temp = tempArr[0];
                    Log.e("temp", temp);
                    String temp2 = s_n.replaceFirst(temp, "");
                    temp2 = temp2.replaceFirst(" ", "");
                    s_n = temp2;
                    //String temp = tempArr[1];
                    //s_n = temp.substring(temp.length() + 1);
                    Log.e("s_n", s_n);
                    int sentint = 1;
                    String temptemp = line_nums.get(line_nums.size() - (list.size() - lineindex));
                    if(temptemp.equals("B")) {
                        sentint = 75;
                    } else if(temptemp.equals("G")) {
                        sentint = 67;
                    } else if(temptemp.equals("K")) {
                        sentint = 63;
                    } else if(temptemp.equals("A")) {
                        sentint = 65;
                    } else if(temptemp.equals("S")) {
                        sentint = 77;
                    } else {
                        sentint = Integer.parseInt(temptemp);
                    }
                    //Toast.makeText(getApplicationContext(), "lineindex : " + lineindex + "s_n : " + s_n, Toast.LENGTH_SHORT).show();
                    Log.e("lineNumber : ", line_nums.get(line_nums.size() - (list.size() - lineindex)) + "");
                    Log.e("sendstationName : ", s_n);
                    Intent intent = new Intent(SearchActivity.this, MainActivity.class);
                    intent.putExtra("lineindex", lineindex);
                    intent.putExtra("originstncode", origin_stnnums.get(station_nums.size() - (list.size() - lineindex)) + "");
                    intent.putExtra("stationnumber", station_nums.get(station_nums.size() - (list.size() - lineindex)) + "");
                    intent.putExtra("linenumber", sentint + "");
                    intent.putExtra("sn", s_n);
                    Log.e("stnName : ", s_n);
                    startActivity(intent);
                    line_nums.clear();
                    finish();
                }
        };

        public void jsonParse(){
                try{
                        jAr = new JSONArray(json);
                        for(int i=0;i<jAr.length();i++){
                                JSONObject st = jAr.getJSONObject(i);

                                stArr[i] = new Station();

                                stArr[i].setName(st.getString("STATION_NM"));
                                stArr[i].setLine_num(st.getString("LINE_NUM"));
                                stArr[i].setStnOriginNum(st.getString("STATION_CD"));
                                stArr[i].setFr_code(st.getString("FR_CODE"));
                        }

                } catch (JSONException e) {
                        e.printStackTrace();
                }
        }

  /*  @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        TextView station1 = (TextView)findViewById(R.id.station1);
        TextView station2 = (TextView)findViewById(R.id.station2);
        if(resultCode==RESULT_OK)
// 액티비티가 정상적으로 종료되었을 경우
        {
            if(requestCode==1111)
            {
                station1.setText(data.getStringExtra("s_n"));
                station2.setText(data.getStringExtra("s_n"));
            }
        }
    }*/

        /*
         * 최근검색기록을 텍스트파일을 통해 읽는 함수
         * 작성날짜: 2015.12.8
         * 작성자: 지준우
         * return void
         * param void
         */
        public void readRecentSearchingList() {
            //File file = new File(path+"recent_searching_list.txt");
                try {
                        FileInputStream r_fileIn = openFileInput("recent_searching_list.txt");
                    //FileInputStream r_fileIn = new FileInputStream(file);
                        InputStreamReader r_InputRead = new InputStreamReader(r_fileIn);
                        char[] inputBuffer = new char[1000];
                        String s = "";
                        int charRead;
                        while ((charRead = r_InputRead.read(inputBuffer)) > 0) {
                            String readstring = String.copyValueOf(inputBuffer, 0, charRead);
                            s += readstring;
                        }
                        writeString += s;
                        //테스트용 토스트

                        Log.e("Read", s);
                        //Toast.makeText(getBaseContext(), s, Toast.LENGTH_SHORT).show();
                        String[] tempArr;
                        //★로 나뉘어져있어서 ★로 split한다. writeRecentSearchingList 함수 참고
                        tempArr = s.split("★");
                        tempSize = tempArr.length;
                        for (int i = 0; i < tempSize; i++)
                            r_list.add(0, tempArr[i]);
                        r_adapter.notifyDataSetChanged();
                        for (int i = 0; i < tempSize; i++)
                            Log.e("Read", tempArr[i]);

                        r_InputRead.close();




                } catch (Exception e) {
                        e.printStackTrace();
                }

        }

        /*
         * 최근검색기록을 텍스트파일에 쓰는 함수
         * 작성날짜: 2015.12.8
         * 작성자: 지준우
         * return void
         * param line_station ex)문자열 "2호선 신도림"
         */
        public void writeRecentSearchingList(String line_station){
            //File file = new File(path+"recent_searching_list.txt");
            boolean overlap = false;
            try {
                FileInputStream fileIn = openFileInput("recent_searching_list.txt");
                //FileInputStream fileIn = new FileInputStream(file);
                InputStreamReader InputRead = new InputStreamReader(fileIn);
                char[] inputBuffer = new char[1000];
                String s = "";
                int charRead;
                while ((charRead = InputRead.read(inputBuffer)) > 0) {
                    String readstring = String.copyValueOf(inputBuffer, 0, charRead);
                    s += readstring;
                }

                Log.e("Read", s);
                //Toast.makeText(getBaseContext(), s, Toast.LENGTH_SHORT).show();
                String[] tempArr;
                //★로 나뉘어져있어서 ★로 split한다. writeRecentSearchingList 함수 참고
                tempArr = s.split("★");


                for(int i=0;i<tempArr.length;i++){
                    if(tempArr[i].equals(line_station)){
                        Log.e("중복","중복");
                        overlap = true;
                        //중복인 것들은 예전에 검색했던 거(중복된거) 빼고 새로 저장
                        s = s.replace(""+line_station+"★", "");

                        Log.e("replaceFirst",s);
                        writeString = s;
                        break;
                    }
                }
            }catch(Exception e){
                e.printStackTrace();
            }




                try {
                    //검색하고 역을 터치하면 실행된다.
                    //recent_searching_list.txt에 목록이 저장된다.
                    FileOutputStream r_fileout = openFileOutput("recent_searching_list.txt", MODE_PRIVATE);
                    //fileOutputStream r_fileout = new FileOutputStream(file);

                    OutputStreamWriter r_outputWriter = new OutputStreamWriter(r_fileout);
                    //★은 split할 기준이다.
                    writeString += (line_station + "★");
                    r_outputWriter.write(writeString);

                    r_outputWriter.close();
                    //테스트용 성공메시지
                    Log.e("Write", "write");
                    //Toast.makeText(getBaseContext(), "File saved successfully!", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

    /*
    * 최근검색기록을 전체지우는 함수
    * 작성자 지준우
    * 작성날짜 2015.12.9
    * param void
    * return void
     */
    private void eraseRecentSearchingList(){
        try {
            //recent_searching_list.txt에 공백저장한다.
            FileOutputStream r_fileout = openFileOutput("recent_searching_list.txt", MODE_PRIVATE);
            //fileOutputStream r_fileout = new FileOutputStream(file);

            OutputStreamWriter r_outputWriter = new OutputStreamWriter(r_fileout);
            //★은 split할 기준이다.
            writeString = "";
            r_outputWriter.write(writeString);

            r_outputWriter.close();
            //테스트용 성공메시지
            Log.e("Erase", "Erase");
            Toast.makeText(getBaseContext(), "최근검색기록을 지웠습니다.", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


        //최근검색기록 누르면 발동하는 함수
        private AdapterView.OnItemClickListener r_onClickListItem = new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                        Log.e("r_onitemclick called", "r_onitemclicked");

                        int lineindex = arg2;
                        Log.e("lineindex",""+lineindex);
                        s_n = r_adapter.getItem(arg2);
                        String ls = s_n;    //ex) 2호선 신도림 lineNum+StationName
                        writeRecentSearchingList(ls);
                        String[] tempArr;
                        tempArr = s_n.split(" ");
                        String temp = tempArr[0];
                        Log.e("temp", temp);
                        String temp2 = s_n.replaceFirst(temp, "");
                        temp2 = temp2.replaceFirst(" ", "");
                        s_n = temp2;
                        //String temp = tempArr[1];
                        //s_n = temp.substring(temp.length() + 1);
                        Log.e("s_n", s_n);

                        //Toast.makeText(getApplicationContext(), "lineindex : " + lineindex + "s_n : " + s_n, Toast.LENGTH_SHORT).show();

                    int ii = 0;
                    for(int i=0;i<jAr.length();i++){
                        String tempStr="";
                        if(stArr[i].getLine_num().equals("B"))
                            tempStr = "분당선"+" "+stArr[i].getName();
                        else if(stArr[i].getLine_num().equals("A"))
                            tempStr = "공항철도"+" "+stArr[i].getName();
                        else if(stArr[i].getLine_num().equals("K"))
                            tempStr = "경의중앙선"+" "+stArr[i].getName();
                        else if(stArr[i].getLine_num().equals("G"))
                            tempStr = "경춘선"+" "+stArr[i].getName();
                        else if(stArr[i].getLine_num().equals("S"))
                            tempStr = "신분당선"+" "+stArr[i].getName();
                        else if(stArr[i].getLine_num().equals("E"))
                            tempStr = "에버라인"+" "+stArr[i].getName();
                        else if(stArr[i].getLine_num().equals("I"))
                            tempStr = "인천1호선"+" "+stArr[i].getName();
                        else
                            tempStr = stArr[i].getLine_num()+"호선 "+stArr[i].getName();

                        Log.i("tempStr",tempStr);

                        //ex) "2호선 신도림".equals("5호선 목동").
                        if(ls.equals(tempStr)){
                            //i를 뽑아낸다
                            ii = i;

                            break;
                        }

                    }

                    int sentint = 1;
                    String temptemp = stArr[ii].getLine_num();
                    if(temptemp.equals("B")) {
                        sentint = 75;
                    } else if(temptemp.equals("G")) {
                        sentint = 67;
                    } else if(temptemp.equals("K")) {
                        sentint = 63;
                    } else if(temptemp.equals("A")) {
                        sentint = 65;
                    } else if(temptemp.equals("S")) {
                        sentint = 77;
                    } else {
                        sentint = Integer.parseInt(temptemp);
                    }

                        Log.e("lineNumber : ", stArr[ii].getLine_num());
                        Log.e("sendstationName : ", s_n);
                        Intent intent = new Intent(SearchActivity.this, MainActivity.class);
                        intent.putExtra("lineindex", lineindex);
                        intent.putExtra("originstncode", stArr[ii].getOrigin_stnnum());
                        intent.putExtra("stationnumber", stArr[ii].getFr_code());
                        intent.putExtra("linenumber", sentint + "");
                        intent.putExtra("sn", s_n);
                        Log.e("stnName : ", s_n);
                        startActivity(intent);

                        finish();
                }
        };


}
