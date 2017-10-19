package com.museumbooking.activity;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.museumbooking.R;


public class WebViewActivity extends BaseActivity {
    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        setView();
        if (null != getIntent()) {
            loadURL(getIntent().getStringExtra("pdf_url"));
        }
    }

    private void setView() {
        mWebView = (WebView) findViewById(R.id.webView);
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setLoadsImagesAutomatically(true);
        webSettings.setDomStorageEnabled(true);
    }

    private void loadURL(String pdf_url) {
        mWebView.loadUrl(pdf_url);
    }
}
