package com.ssuproject.ulnamsong.ssu_androidapplicationproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Do something
        Toast.makeText(this, "Test", Toast.LENGTH_SHORT).show();
    }
}
