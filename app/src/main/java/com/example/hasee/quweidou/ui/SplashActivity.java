package com.example.hasee.quweidou.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.hasee.quweidou.R;
import com.example.hasee.quweidou.fragment.SplashFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HASEE on 2017/4/17.
 */

public class SplashActivity extends AppCompatActivity {

    ViewPager viewPager;
    List<Fragment> fragments;
    LinearLayout dot_layout;
    //判断状态
    boolean isLeftScroll = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);
        //初始化控件
        setupView();
        //设置圆点
        initDot();

    }

    private void initDot() {
        //设置宽高
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(20, 20);
        //设置每个圆点的属性
        params.setMargins(5, 5, 5, 5);
        for (int i = 0; i < fragments.size(); i++) {
            ImageView iv = new ImageView(this);
            //设置第一个圆点为默认选中
            if (i == 0) {
                iv.setImageResource(R.drawable.page_now);
            } else {
                iv.setImageResource(R.drawable.page);
            }
            //将所有圆点放进LinearLayout
            dot_layout.addView(iv, params);
        }

    }

    private void setupView() {
        viewPager = (ViewPager) findViewById(R.id.splash_viewPager);
        dot_layout = (LinearLayout) findViewById(R.id.dot_layout);
        fragments = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            SplashFragment sf = new SplashFragment();
            //传入说明是第几个界面
            Bundle bundle = new Bundle();
            bundle.putInt(SplashFragment.INDEX_KEY, i);
            sf.setArguments(bundle);
            fragments.add(sf);
        }

        //设置适配器
        FragmentPagerAdapter fragmentPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            //获取当前的视图
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        };

        viewPager.setAdapter(fragmentPagerAdapter);

        //设置ViewOager的监听
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            //当前界面时调用该方法
            @Override
            public void onPageSelected(int position) {
                int childCount = dot_layout.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    //选中该界面,修改圆点
                    ImageView iv = (ImageView) dot_layout.getChildAt(i);
                    if (i == position) {
                        iv.setImageResource(R.drawable.page_now);
                    } else {
                        iv.setImageResource(R.drawable.page);
                    }
                }
            }

            //滚动状态改变时调用的方法
            @Override
            public void onPageScrollStateChanged(int state) {
                switch (state) {
                    //手指滑动
                    case ViewPager.SCROLL_STATE_DRAGGING: {
                        isLeftScroll = false;
                    }
                    break;

                    case ViewPager.SCROLL_STATE_IDLE: {
                        //如果是最后一页，且没有惯性滑动时候，就跳转
                        if (!isLeftScroll && viewPager.getCurrentItem() == viewPager.getAdapter().getCount() - 1) {
                            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        isLeftScroll = false;
                    }
                    break;

                    //惯性滑动，不切换
                    case ViewPager.SCROLL_STATE_SETTLING: {
                        isLeftScroll = true;
                    }
                    break;
                }
            }
        });

    }
}
