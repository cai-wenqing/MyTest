package com.aiyakeji.mytest.ui;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.aiyakeji.mytest.R;

/**
 * @author CWQ
 * @date 2020/8/5
 */
public class WebActivity extends AppCompatActivity {

    private String url = "https://render.alipay.com/p/s/i/?scheme=alipays%3A%2F%2Fplatformapi%2Fstartapp%3FappId%3D2021001180672515%26page%3Dpages%252Ftblist%252Ftblist%26enbsv%3D0.2.2008031650.22%26chInfo%3Dch_share__chsub_CopyLink";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        WebView webView = findViewById(R.id.webview);
        initWebViewProperty(webView);

        webView.loadUrl(url);
    }



    private void initWebViewProperty(WebView webview) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP){
            webview.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        webview.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setSupportZoom(false);
        webview.getSettings().setBuiltInZoomControls(false);
        webview.getSettings().setUseWideViewPort(false);
        webview.getSettings().setLoadWithOverviewMode(false);
        webview.getSettings().setTextZoom(100);
        webview.getSettings().setSupportMultipleWindows(true);
        webview.getSettings().setAllowFileAccess(true);
        webview.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        webview.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webview.getSettings().setAppCacheEnabled(true);
        webview.getSettings().setDomStorageEnabled(true);
        webview.getSettings().setDatabaseEnabled(true);
        webview.getSettings().setBlockNetworkImage(false);
        webview.getSettings().setDefaultTextEncodingName("utf-8");
        webview.getSettings().setPluginState(WebSettings.PluginState.ON);
    }
}
