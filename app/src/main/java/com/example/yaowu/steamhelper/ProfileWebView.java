package com.example.yaowu.steamhelper;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class ProfileWebView extends AppCompatActivity {
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_view);

        webView = (WebView)findViewById(R.id.profile_web);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());

        Intent url_intent = getIntent();
        String[] url = {url_intent.getStringExtra("profile_url"), url_intent.getStringExtra("search game")};

        if(url[0] != null) webView.loadUrl(url[0]);
        else  webView.loadUrl(url[1]);

    }
}
