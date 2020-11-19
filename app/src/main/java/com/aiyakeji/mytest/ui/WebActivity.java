package com.aiyakeji.mytest.ui;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.aiyakeji.mytest.R;

/**
 * @author CWQ
 * @date 2020/8/5
 */
public class WebActivity extends AppCompatActivity {

    private String url = "https://render.alipay.com/p/s/i/?scheme=alipays%3A%2F%2Fplatformapi%2Fstartapp%3FappId%3D2021001180672515%26page%3Dpages%252Ftblist%252Ftblist%26enbsv%3D0.2.2008031650.22%26chInfo%3Dch_share__chsub_CopyLink";
    private String url1 = "https://tb.ele.me/wow/ele-ad/act/elmtkhd?wh_biz=tm&from=cps_tk&es=S%2FIaJxiW19ibhUsf2ayXDHTscW9AhR%2FYimBbs62X5mFJes6jD0y5Wxdo7lYDw2tle9NMrhGBhgIpUFg8fbd93NpujppUOG5Q&ali_trackid=2:mm_35010278_6980183_109940100097:1591765930_119_125095089&e=-s02oRAydMJpwrxzAjZ7CfKksxbPan37YXMVGtisqrjAA3I794fMutJcKDUQ4uad8fZGt6clw4YYfSEd9aU12kfH5HudstF57KwtKSIVUZZ8jetEhCIBoiSowfdnUHnwhdDiio81MrCNUUx4IiPvWlGyObiOSJtcab9K49k8QMIFJCJc3QEP0xi1iV4pxIcrsT48Xb1lBmWcVaP782nbkM7emOrh7SRibHEZpFhPjHSXKhZHTa0F6lRmoNAwvczQxbQuWaxXnxQYD2QmpPmV8Mbbl1PXzp6WmqcHhPf2WinBm7WYy8he9387iHjHXSdzlTZgIgBmUeDIatQBg58A4I0B99UYno3oVGlD2U4ZL1JnIFB25I0mxuk9Wy9Z48BxRqOvcBMoMgFwUMBf3pFTR1JNRpqmzjHrowLRwf70JlwpUJnbMjDdCtll7nk4S5eDIajoeB9v3N8eC2xP3hAK6eOOGyUaU&union_lens=lensId:0b582459_0dea_1729ca48e04_4a23&ak=23233837&relationId=2402249496";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        WebView webView = findViewById(R.id.webview);
        initWebViewProperty(webView);

        webView.loadUrl(url1);
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
        webview.setWebViewClient(new MyWebClient());
    }


    class MyWebClient extends WebViewClient{

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Log.e("Web",url);
            if (url.startsWith("http")){
                return super.shouldOverrideUrlLoading(view, url);
            }else {
                return true;
            }
        }
    }
}
