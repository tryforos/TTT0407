package com.example.ttt0407projectnavigationapp;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ImageView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class WebpageDownloader {

    // constructor
    public WebpageDownloader() {
        super();
    }
    // END constructor

    ////////
    // HOLLER WEBPAGE
    //
    // load web page with external url
    public static void loadWeb(WebView web, String url){
        web.loadUrl(url);
    }

    // load web view with html  from resource
    public static void loadHtml(Context context, WebView web, int resFile){

        //WebView web = findViewById(R.id.webview);

        web.getSettings().setJavaScriptEnabled(true);
        web.addJavascriptInterface(new WebAppInterface(context), "Android");

        web.setWebChromeClient(new WebChromeClient());

        Resources res = context.getResources();
        InputStream inputStream = res.openRawResource(resFile);

        try {
            BufferedReader r = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder total = new StringBuilder();

            for (String line; (line = r.readLine()) != null; ) {
                total.append(line).append('\n');
            }

            inputStream.close();
            web.loadData(total.toString(), "text/html", "utf-8");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    //
    //
    ////////

}
