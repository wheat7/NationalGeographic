package com.wheat7.nationalgeographic.ui.activity

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.support.v7.widget.Toolbar;

import com.wheat7.nationalgeographic.R
import com.wheat7.nationalgeographic.databinding.ActivityWebBinding
import kotlinx.android.synthetic.main.activity_web.*

/**
 * Created by wheat7 on 2017/8/19.
 */

//因为LifecycleActivity中没有了setSupportActionBar()和getSupportActionBar(),未解决，所以不能继承BaseActitivy
class WebActivity : AppCompatActivity() {

    override fun onBackPressed() {
        if (web_view.canGoBack()) {
            web_view.goBack()
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web)
        val intent = intent

        val url = intent.getStringExtra("URL")
        web_view.settings.javaScriptEnabled = true
        web_view.loadUrl(url)
        web_view.settings.setSupportZoom(true)
        web_view.settings.displayZoomControls = false
        web_view.settings.useWideViewPort = true
        web_view.settings.loadWithOverviewMode = true
        web_view.settings.setAppCacheEnabled(true)
        setSupportActionBar(toolbar_web)
        getSupportActionBar()!!.setDisplayShowTitleEnabled(false)
        val chromeClient = object : WebChromeClient() {
            override fun onReceivedTitle(view: WebView, title: String) {
                super.onReceivedTitle(view, title)
                //getBinding().webTitle.setText(title)
            }

            override fun onProgressChanged(view: WebView, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
                web_progress.progress = newProgress
            }
        }

        web_view.webChromeClient = chromeClient
        web_view.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
                tv_close.setVisibility(View.VISIBLE)
                return super.shouldOverrideUrlLoading(view, request)
            }

            override fun onPageStarted(view: WebView, url: String, favicon: Bitmap) {
                super.onPageStarted(view, url, favicon)
                web_progress.visibility = View.VISIBLE
            }

            override fun onPageFinished(view: WebView, url: String) {
                super.onPageFinished(view, url)
                web_progress.visibility = View.GONE
            }
        }
    }




}
