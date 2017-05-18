package com.example.hasee.quweidou.ui;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.example.hasee.quweidou.R;
import com.example.hasee.quweidou.utils.ShareUtils;

/**
 * Created by HASEE on 2017/4/17.
 */

public class WelcomeActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_layout);

        //开启逐帧动画
        ImageView iv1 = (ImageView) findViewById(R.id.iv_up);
        AnimationDrawable anim1 = (AnimationDrawable) iv1.getDrawable();
        anim1.start();

        ImageView iv2 = (ImageView) findViewById(R.id.iv_down);
        AnimationDrawable anim2 = (AnimationDrawable) iv2.getDrawable();
        anim2.start();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                //判断是否是第一次登录
                if (ShareUtils.isFirstRun(WelcomeActivity.this)){
                    Intent intent = new Intent(WelcomeActivity.this,SplashActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    Intent intent = new Intent(WelcomeActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        },2000);
    }
}
