package com.example.hasee.quweidou.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hasee.quweidou.R;
import com.example.hasee.quweidou.fragment.JXFragment;
import com.example.hasee.quweidou.fragment.WDFragment;
import com.example.hasee.quweidou.fragment.EyeFragment;
import com.example.hasee.quweidou.utils.ShareUtils;

import cn.sharesdk.framework.ShareSDK;

public class MainActivity extends BaseActivity {

    private String[] tables = {"精华", "Eyepetizer", "我的"};
    private int[] imgIds = {R.drawable.tab_jinhua, R.drawable.tab_xintie, R.drawable.tab_mine};
    private Class[] fragments = {JXFragment.class, EyeFragment.class, WDFragment.class};

    private FragmentTabHost tabHost;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //第一次登录之后就保存状态
        ShareUtils.saveFirstRun(this);

        //Mob平台授权
        ShareSDK.initSDK(this);

        //这些都是固定写法
        tabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        tabHost.setup(this, getSupportFragmentManager(), R.id.framelayout);

        setupViews();
    }


    private void setupViews() {
        inflater = LayoutInflater.from(this);

        for (int i = 0; i < tables.length; i++) {
            //固定写法
            TabHost.TabSpec tabItem = tabHost.newTabSpec(i + "");
            //设置指示器
            tabItem.setIndicator(getItemView(i));
            //填充对应的Fragment，当点击某一个tabItem时候，就会跳转到对应的Fragment
            tabHost.addTab(tabItem, fragments[i], null);
            //tabHost去掉边线
            tabHost.getTabWidget().setDividerDrawable(android.R.color.transparent);
        }

    }

    LayoutInflater inflater;
    private View getItemView(int index) {
        //获取一个TabItem的视图，里面的东西可以替换
        View view = inflater.inflate(R.layout.tab_item, null);

        ImageView iv = (ImageView) view.findViewById(R.id.tab_img);
        TextView tv = (TextView) view.findViewById(R.id.tab_tv);
        //设置对应的视图和文字
        iv.setImageResource(imgIds[index]);
        tv.setText(tables[index]);

        return view;
    }

    /**
     *
     * @param keyCode  代表是哪一个按键
     * @param event    代表手势操作行为
     * @return
     */
    long exitTime = 0;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //点击后退键，弹出提示
        if (keyCode==KeyEvent.KEYCODE_BACK && event.getAction()==KeyEvent.ACTION_DOWN){
            //两次点击是否超过2秒
            if ((System.currentTimeMillis()-exitTime)>2000){
                Toast.makeText(getApplicationContext(), "再按一次退出程序",Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            }else{
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
