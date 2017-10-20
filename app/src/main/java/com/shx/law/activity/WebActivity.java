package com.shx.law.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.shx.law.R;
import com.shx.law.base.BaseActivity;
import com.shx.law.common.LogGloble;
import com.shx.law.common.SystemConfig;
import com.shx.law.libs.dialog.DialogManager;


public class WebActivity extends BaseActivity {
    private WebView webView;
    private String url;
    /**
     * 用来控制字体大小
     */
    int fontSize = 1;
    private int urlIndex;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview_activity);
        getTopbar().setTitle("详细内容");
        getTopbar().setLeftImageListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        webView = (WebView) findViewById(R.id.webView);
        url = getIntent().getStringExtra("URL");
        if(url.startsWith("0")){
            url=url.substring(url.indexOf("0")+1);
        }
        url=String.format(SystemConfig.URL,url);
        LogGloble.d("url",url+"==");
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        webView.loadUrl(url);
    }

    private void init(){
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDisplayZoomControls(false); //隐藏webview缩放按钮
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setSupportZoom(true);  //支持缩放
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webView.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
        settings.setBuiltInZoomControls(true); //设置支持缩放
        if (settings.getTextSize() == WebSettings.TextSize.SMALLEST) {
            fontSize = 50*2;
        } else if (settings.getTextSize() == WebSettings.TextSize.SMALLER) {
            fontSize = 75*2;
        } else if (settings.getTextSize() == WebSettings.TextSize.NORMAL) {
            fontSize = 100*2;
        } else if (settings.getTextSize() == WebSettings.TextSize.LARGER) {
            fontSize = 150*2;
        } else if (settings.getTextSize() == WebSettings.TextSize.LARGEST) {
            fontSize = 200*2;
        }
        settings.setTextZoom(fontSize);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                DialogManager.getInstance().showProgressDialog(WebActivity.this);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                DialogManager.getInstance().dissMissProgressDialog();
            }
        });
    }
    //改写物理按键——返回的逻辑
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if(keyCode== KeyEvent.KEYCODE_BACK)
        {
            if(webView.canGoBack())
            {
                webView.goBack();//返回上一页面
                return true;
            }
            else
            {
                onBackPressed();
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
