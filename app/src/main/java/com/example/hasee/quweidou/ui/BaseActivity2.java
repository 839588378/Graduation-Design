package com.example.hasee.quweidou.ui;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.hasee.quweidou.R;
import com.example.hasee.quweidou.utils.ScreenUtil;

import butterknife.ButterKnife;


/**
 * Created by Ken on 2017/1/7.15:49
 */

public abstract class BaseActivity2 extends AppCompatActivity {

    protected FragmentManager fragmentManager;
    private Fragment showFragment;//当前正在显示的fragment


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentId());
        ButterKnife.bind(this);
        fragmentManager = getSupportFragmentManager();

        //开启沉浸式状态栏
        if(isOpenStatus()){
            //沉浸式状态栏（透明状态栏） -- android4.4以后提供
            Window window = getWindow();
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                //5.0以上
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);//让子控件填充整个屏幕
                window.setStatusBarColor(Color.TRANSPARENT);//把状态栏设置为透明
            } else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
                //4.4以上
                window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            }

            //找到指定的控件，设置paddingtop为状态栏的高度（如果找到一个id为actionbar的控件，则对它他设置paddingtop为状态栏的高度）
            View view = findViewByIds(R.id.actionbar);
            if(view != null){
                int statusHeight = ScreenUtil.getStatusHeight(this);
                if(statusHeight != -1) {
                    view.setPadding(0, statusHeight, 0, 0);
                }
            }
        }

        init();
        loadDatas();
    }

    protected void loadDatas(){}

    protected void init(){}

    protected abstract int getContentId();


    /**
     * 显示fragment
     */
    protected void showFragment(int frameLayoutId, Fragment fragment){
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        //检测当前是否有显示的fragment，如果有则隐藏
        if(showFragment != null){
            fragmentTransaction.hide(showFragment);
        }

        //判断需要显示的fragment是否原来出来过，如果有，则显示
        Fragment mfragment = fragmentManager.findFragmentByTag(fragment.getClass().getName());
        if(mfragment != null){
            fragmentTransaction.show(mfragment);
        } else {
            //如果没有则add
            mfragment = fragment;
            fragmentTransaction.add(frameLayoutId, mfragment, fragment.getClass().getName());
        }
        showFragment = mfragment;
        fragmentTransaction.commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);

    }

    /**
     * 避免强转的控件查找方法
     * @param id
     * @param <T>
     * @return
     */
    protected <T> T findViewByIds(int id){
        return (T)findViewById(id);
    }

    /**
     * 是否开启沉浸式状态栏
     * @return
     */
    protected boolean isOpenStatus(){
        return true;
    }
}
