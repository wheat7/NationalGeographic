package com.wheat7.nationalgeographic.ui.activity

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient

import com.wheat7.nationalgeographic.R
import com.wheat7.nationalgeographic.databinding.ActivityWebBinding
import kotlinx.android.synthetic.main.activity_web.*

/**
 * Created by wheat7 on 2017/8/19.
 */

class WebActivity : BaseActivity<ActivityWebBinding>() {

    override val layoutId: Int
        get() = R.layout.activity_web

    override fun onBackPressed() {
        if (getBinding().webView.canGoBack()) {
            getBinding().webView.goBack()
        } else {
            super.onBackPressed()
        }
    }

    override fun initView(savedInstanceState: Bundle?) {
        val intent = intent
        val url = intent.getStringExtra("URL")
        getBinding().webView.settings.javaScriptEnabled = true
        getBinding().webView.loadUrl(url)
        getBinding().webView.settings.setSupportZoom(true)
        getBinding().webView.settings.displayZoomControls = false
        getBinding().webView.settings.useWideViewPort = true
        getBinding().webView.settings.loadWithOverviewMode = true
        getBinding().webView.settings.setAppCacheEnabled(true)
//        setSupportActionBar(getBinding().toolBar)
//        getSupportActionBar().setDisplayShowTitleEnabled(false)
        val chromeClient = object : WebChromeClient() {
            override fun onReceivedTitle(view: WebView, title: String) {
                super.onReceivedTitle(view, title)
                //getBinding().webTitle.setText(title)
            }

            override fun onProgressChanged(view: WebView, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
                getBinding().webProgress.progress = newProgress
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

    /**
     * Back click
     */
    fun onIcBackClick() {
        onBackPressed()
    }

    /**
     * Close  click
     */

    fun onCloseClick() {
        this.finish()
    }

}
