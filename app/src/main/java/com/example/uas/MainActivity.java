package com.example.uas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void Activity2(View view) {
        Intent intent = new Intent(MainActivity.this, Activity2.class);
        startActivity(intent);
    }

    public void Activity3(View view) {
        Intent intent = new Intent(MainActivity.this, Activity3.class);
        startActivity(intent);
    }


}