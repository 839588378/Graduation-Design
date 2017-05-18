package com.example.hasee.quweidou.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.example.hasee.quweidou.R;

/**
 * Created by HASEE on 2017/4/20.
 */

public class WebActivity extends AppCompatActivity{
    WebView webView;
    ImageView iv;
    AnimationDrawable anim;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_layout);

        webView = (WebView) findViewById(R.id.webView);
        iv = (ImageView) findViewById(R.id.web_loading_img);

        Intent intent = getIntent();
        String url = intent.getStringExtra("to_url");

        //设置WebView
        //支持JS
        webView.getSettings().setJavaScriptEnabled(true);
        //监听
        webView.setWebChromeClient(new WebChromeClient());

        webView.setWebViewClient(new WebViewClient(){
            //在开始WebView界面之前
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                webView.setVisibility(View.GONE);
                iv.setVisibility(View.VISIBLE);
                //开启动画资源
                anim = (AnimationDrawable) iv.getDrawable();
                anim.start();
            }

            //动画结束后
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                webView.setVisibility(View.VISIBLE);
                iv.setVisibility(View.GONE);
                //停止动画
                anim = (AnimationDrawable) iv.getDrawable();
                anim.stop();
            }
        });

        //通过URL去加载网页
        webView.loadUrl(url);
    }

    //点击后退建，就回结束当前页面
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode==KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN){
            finish();
        }
        return super.onKeyDown(keyCode, event);

    }
}
