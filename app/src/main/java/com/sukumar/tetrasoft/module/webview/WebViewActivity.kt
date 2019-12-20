package com.sukumar.tetrasoft.module.webview

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.sukumar.tetrasoft.R
import kotlinx.android.synthetic.main.activity_web_view.*


class WebViewActivity : AppCompatActivity() {

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String?): Boolean {
                if (url == null || url.startsWith("http://") || url.startsWith("https://")) return false

                try {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    view.context.startActivity(intent)
                    return true
                } catch (e: Exception) {
                    Log.i("", "shouldOverrideUrlLoading Exception:$e")
                    return true
                }

            }
            override fun onLoadResource(view: WebView, url: String) {}
            override fun onPageFinished(view: WebView, url: String) {
                try {

                } catch (exception: Exception) {
                    exception.printStackTrace()
                }

            }

        }

        webView.settings.javaScriptEnabled = true
        webView.loadUrl("https://www.imdb.com/")
    }
}
