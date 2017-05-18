package com.example.hasee.quweidou.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hasee.quweidou.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by HASEE on 2017/4/18.
 */

public class JXFragment extends Fragment {

    //利用ButterKnife这种依赖包，简便代码
    @Bind(R.id.tablayout)
    TabLayout tabLayout;
    @Bind(R.id.jx_viewpager)
    ViewPager viewPager;

    List<Fragment> fragments;
    static final String URL_KEY = "listUrl";
    private String[] titles = {"推荐", "视频", "段子", "秀秀", "图片", "趣事", "GAME", "娱乐"};

    //通过抓包获取的URL
    private String[] urls = {
            "http://s.budejie.com/topic/list/jingxuan/1/budejie-android-6.6.1/0-20.json?market=xiaomi&ver=6.6.1&visiting=&os=6.0.1&appname=baisibudejie&client=android&udid=868030020821367&mac=02%3A00%3A00%3A00%3A00%3A00",
            "http://s.budejie.com/topic/list/remen/1/budejie-android-6.6.1/0-20.json?market=xiaomi&ver=6.6.1&visiting=&os=6.0.1&appname=baisibudejie&client=android&udid=868030020821367&mac=02%3A00%3A00%3A00%3A00%3A00",
//            "http://s.budejie.com/topic/list/jingxuan/41/budejie-android-6.6.1/0-20.json?market=xiaomi&ver=6.6.1&visiting=&os=6.0.1&appname=baisibudejie&client=android&udid=868030020821367&mac=02%3A00%3A00%3A00%3A00%3A00",
            "http://s.budejie.com/topic/list/zuixin/29/budejie-android-6.6.1/0-20.json?market=xiaomi&ver=6.6.1&visiting=&os=6.0.1&appname=baisibudejie&client=android&udid=868030020821367&mac=02%3A00%3A00%3A00%3A00%3A00",
            "http://s.budejie.com/topic/list/zuixin/41/budejie-android-6.6.1/0-20.json?market=xiaomi&ver=6.6.1&visiting=&os=6.0.1&appname=baisibudejie&client=android&udid=868030020821367&mac=02%3A00%3A00%3A00%3A00%3A00",
            "http://s.budejie.com/topic/tag-topic/473/hot/budejie-android-6.6.1/0-20.json?market=xiaomi&ver=6.6.1&visiting=&os=6.0.1&appname=baisibudejie&client=android&udid=868030020821367&mac=02%3A00%3A00%3A00%3A00%3A00",
//            "http://s.budejie.com/topic/tag-topic/3176/hot/budejie-android-6.6.1/0-20.json?market=xiaomi&ver=6.6.1&visiting=&os=6.0.1&appname=baisibudejie&client=android&udid=868030020821367&mac=02%3A00%3A00%3A00%3A00%3A00",
            "http://s.budejie.com/topic/tag-topic/3176/hot/budejie-android-6.6.1/0-20.json?market=xiaomi&ver=6.6.1&visiting=&os=6.0.1&appname=baisibudejie&client=android&udid=868030020821367&mac=02%3A00%3A00%3A00%3A00%3A00",
            "http://s.budejie.com/topic/tag-topic/164/hot/budejie-android-6.6.1/0-20.json?market=xiaomi&ver=6.6.1&visiting=&os=6.0.1&appname=baisibudejie&client=android&udid=868030020821367&mac=02%3A00%3A00%3A00%3A00%3A00",
            "http://s.budejie.com/topic/tag-topic/3096/hot/budejie-android-6.6.1/0-20.json?market=xiaomi&ver=6.6.1&visiting=&os=6.0.1&appname=baisibudejie&client=android&udid=868030020821367&mac=02%3A00%3A00%3A00%3A00%3A00"
    };


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.jx_fragment, container, false);
        //在Fragment中利用ButterKnife一定要这样绑定，在Activity中就不需要view
        ButterKnife.bind(this, view);

        fragments = new ArrayList<>();

        setupViews();
        return view;

    }

    //填充进去那八个子Fragment
    private void setupViews() {
        for (int i = 0; i < titles.length; i++) {
            JXContentFragment jxcf = new JXContentFragment();
            Bundle bundle = new Bundle();
            bundle.putString(URL_KEY, urls[i]);
            jxcf.setArguments(bundle);
            fragments.add(jxcf);
        }

        //设置ViewPager关于Fragment的适配器，
        // 由于本身实在Fragment里面加载子Fragment，所以一定要getChildFragmentManager
        FragmentPagerAdapter adapter = new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }

            //设置列表的标题
            @Override
            public CharSequence getPageTitle(int position) {
                return titles[position];
            }
        };
        //关联适配器
        viewPager.setAdapter(adapter);
        //关联ViewPager
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
