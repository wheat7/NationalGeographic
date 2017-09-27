package com.wheat7.nationalgeographic.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.wheat7.nationalgeographic.R;
import com.wheat7.nationalgeographic.databinding.ActivityWebBinding;

/**
 * Created by wheat7 on 2017/8/19.
 */

//WebView 使用kotlin会有问题，后续版本解决
public class WebActivity extends BaseActivity<ActivityWebBinding> {

    @Override
    public int getLayoutId() {
        return R.layout.activity_web;
    }

    @Override
    public void onBackPressed() {
        if (getBinding().webView.canGoBack()) {
            getBinding().webView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        Intent intent = getIntent();
        String url = intent.getStringExtra("URL");
        getBinding().webView.getSettings().setJavaScriptEnabled(true);
        getBinding().webView.loadUrl(url);
        getBinding().webView.getSettings().setSupportZoom(true);
        getBinding().webView.getSettings().setDisplayZoomControls(false);
        getBinding().webView.getSettings().setUseWideViewPort(true);
        getBinding().webView.getSettings().setLoadWithOverviewMode(true);
        getBinding().webView.getSettings().setAppCacheEnabled(true);
        WebChromeClient chromeClient = new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                getBinding().webProgress.setProgress(newProgress);
            }
        };

        getBinding().webView.setWebChromeClient(chromeClient);
        getBinding().webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                getBinding().tvClose.setVisibility(View.VISIBLE);
                return super.shouldOverrideUrlLoading(view, request);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                getBinding().webProgress.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                getBinding().webProgress.setVisibility(View.GONE);
            }
        });

        getBinding().webBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        getBinding().tvClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WebActivity.this.finish();
            }
        });
    }



}
