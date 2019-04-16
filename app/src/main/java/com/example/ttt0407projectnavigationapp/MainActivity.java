package com.example.ttt0407projectnavigationapp;

import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // set custom toolbar
        Toolbar tlb = (Toolbar) findViewById(R.id.tlb_toolbar);
        setSupportActionBar(tlb);


        //getSupportActionBar().setDisplayShowTitleEnabled(false);

        /*
        //Request for custom title bar
        this.requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        //set to your layout file
        setContentView(R.layout.activity_main);
        //Set the titlebar layout
        this.getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title_watch_list);
*/

    }
}
