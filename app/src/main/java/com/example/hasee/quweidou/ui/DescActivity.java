package com.example.hasee.quweidou.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.hasee.quweidou.R;
import com.example.hasee.quweidou.entity.SectionEntity;
import com.example.hasee.quweidou.fragment.DescFragment;
import com.example.hasee.quweidou.utils.ScreenUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.Bind;

/**
 * Created by HASEE on 2017/4/28.
 */

public class DescActivity extends BaseActivity2 {
    @Bind(R.id.viewPager)
    public ViewPager viewPager;
    @Bind(R.id.iv_anim)
    public ImageView ivAnim;

    private List<SectionEntity.ItemListBean> datas;
    private int position;
    private int y;

    private int time = 500;

    public DescActivity() {
        Log.d("TAG", "进入DescActivity");
    }

    @Override
    protected int getContentId() {

        return R.layout.activity_descactivity;
    }

    @Override
    protected void init() {
        Log.d("TAG", "进入DescActivity的init");
        super.init();
        //设置共享动画的Imageview参数
        Intent intent = getIntent();
        //获得数据列表
        datas = (List<SectionEntity.ItemListBean>) intent.getSerializableExtra("datas");
        for (int i = 0; i < datas.size(); i++) {
            //判断是否类型是video，不是的话要删除这一部分
            if (!datas.get(i).getType().equals("video")) {
                datas.remove(i);
            }
        }
        //获得点击的数据下表
        position = intent.getIntExtra("position", -1);

        //获得点击控件的宽高
        int width = intent.getIntExtra("width", -1);
        int height = intent.getIntExtra("height", -1);

        //设置共享控件的宽高
        ViewGroup.LayoutParams layoutParams = ivAnim.getLayoutParams();
        layoutParams.width = width;
        layoutParams.height = height;
        ivAnim.setLayoutParams(layoutParams);

        //获得控件的坐标位置
        int x = intent.getIntExtra("x", -1);
        y = intent.getIntExtra("y", -1);
        ivAnim.setX(x);
        ivAnim.setY(y);

        //设置共享元素的控件内容
        Glide.with(getApplicationContext())
                .load(datas.get(position).getData().getCover().getFeed())
                .thumbnail(0.1f)
                .into(ivAnim);

        //计算ViewPager中的ImageView的高度
        float endHeight = ScreenUtil.getScreenHeight(this) * 0.5f;

        //执行属性动画（共享元素的转场动画）
        ivAnim.animate()
                .y((endHeight - height) / 2)
                .scaleX(endHeight / height)
                .scaleY(endHeight / height)
                .setDuration(time)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    //动画结束监听
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            //创建圆形揭示动画
                            Animator circularReveal = ViewAnimationUtils.createCircularReveal(
                                    viewPager,
                                    0,
                                    ScreenUtil.getScreenHeight(DescActivity.this),
                                    0,
                                    (float) Math.sqrt(ScreenUtil.getScreenHeight(DescActivity.this) * ScreenUtil.getScreenHeight(DescActivity.this)
                                            + ScreenUtil.getScreenWidth(DescActivity.this) * ScreenUtil.getScreenWidth(DescActivity.this))
                            );
                            circularReveal.setDuration(2000)
                                    .addListener(new AnimatorListenerAdapter() {
                                        @Override
                                        public void onAnimationStart(Animator animation) {
                                            viewPager.setVisibility(View.VISIBLE);
                                        }
                                    });
                            circularReveal.start();
                        } else {
                            viewPager.setVisibility(View.VISIBLE);
                        }
                    }
                })
                .start();

        //设置ViewPager的转场特效
        viewPager.setPageTransformer(false, new ViewPager.PageTransformer() {
            /**
             * viewpager中的每一个item都对应一个自己的transformPage方法
             * 第一个参数：page，代表当前对应这个方法的item对象
             * 第二个参数：position，代表当前item的位置，
             *      如果item在屏幕的左边，position值为-1
             *      如果item在屏幕的正中间，position的值为0
             *      如果item在屏幕的右边，position的值为1
             * @param page
             * @param position
             */
            @Override
            public void transformPage(View page, float position) {
                View vLayout = page.findViewById(R.id.fl);
                int vWidth = vLayout.getWidth();
                Log.d("TAG","执行转场特效");
                View r1Bottom = page.findViewById(R.id.rl_bottom);
                //属于不可见的
                if (position < -1) {
                    page.setAlpha(0);
                    r1Bottom.setAlpha(0);
                } else if (position >= -1 && position < 1) {
                    page.setAlpha(1);

                    //item整体不被移动
                    vLayout.setTranslationX(-(vWidth * position));

                    //头部进行一个移动
                    View vHeadView = page.findViewById(R.id.fl_head);
                    vHeadView.setTranslationX(vWidth * position);
                    //头部图片进行一个差量的移动
                    View vheadImageView = page.findViewById(R.id.iv_top);
                    vheadImageView.setTranslationX(-(vWidth * position * 1f));

                    //处理底部背景的过渡
                    View vBottom = page.findViewById(R.id.iv_bottom_bg);
                    if (position >= 0) {
                        vBottom.setAlpha(1 - position);
                    }

                    //处理文字
                    if (r1Bottom.getAlpha() != 0) {
                        r1Bottom.setAlpha(1 - Math.abs(position));
                    }
                    //屏幕正中间，文字显示透明度1
                    if (position == 0) {
                        r1Bottom.setAlpha(1);
                    }
                } else {
                    page.setAlpha(0);
                    r1Bottom.setAlpha(0);
                }
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            //当前界面
            @Override
            public void onPageSelected(int position) {
                if (DescActivity.this.position != position) {
                    y = 0;
                }

                //记录当前选中的item下标
                DescActivity.this.position = position;

                //元素共享动画的图片修改为当前选中的item的标题图片
                Glide.with(getApplicationContext())
                        .load(datas.get(position).getData().getCover().getFeed())
                        .thumbnail(0.1f)
                        .into(ivAnim);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    protected void loadDatas() {
        super.loadDatas();
        Adapter adapter = new Adapter(getSupportFragmentManager(), datas);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(position);
    }

    private static class Adapter extends FragmentPagerAdapter {

        private List<SectionEntity.ItemListBean> datas;

        public Adapter(FragmentManager fm, List<SectionEntity.ItemListBean> datasA) {
            super(fm);
            //
            for (int i = 0; i < datasA.size(); i++) {
                //判断是否类型是video，不是的话要删除这一部分
                if (!datasA.get(i).getType().equals("video")) {
                    datasA.remove(i);
                }
            }
            this.datas = datasA;
        }

        //当前填充的单个Fragment
        @Override
        public Fragment getItem(int position) {

            return DescFragment.getInstance(datas.get(position));
        }

        @Override
        public int getCount() {
            return datas.size();
        }
    }

    /**
     * 监听返回按钮
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            viewPager.setVisibility(View.GONE);

            ivAnim.animate()
                    .scaleX(1)
                    .scaleY(1)
                    .y(y)
                    .setDuration(time)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            finish();
                            overridePendingTransition(0, 0);
                        }
                    })
                    .start();

            //将当前选中的item的位置返回给前一个activity
            if(y == 0) {
                EventBus.getDefault().post(new Integer(position));
            }
        }
        return true;
    }
}
