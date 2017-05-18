package com.example.hasee.quweidou.fragment;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.example.hasee.quweidou.R;
import com.example.hasee.quweidou.adapter.EyeItem1;
import com.example.hasee.quweidou.adapter.EyeItem2;
import com.example.hasee.quweidou.adapter.EyeItem3;
import com.example.hasee.quweidou.entity.SectionEntity;
import com.example.hasee.quweidou.ui.DescActivity;
import com.example.hasee.quweidou.utils.Constants;
import com.example.hasee.quweidou.utils.JSONUtil;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.wrapper.HeaderAndFooterWrapper;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * Created by HASEE on 2017/4/18.
 */

public class EyeFragment extends Fragment implements EyeItem1.OnItemClickListener {
    //    @Bind(R.id.recyclerview)
    public RecyclerView recyclerView;

    private MultiItemTypeAdapter<SectionEntity> eyeAdapter;
    private HeaderAndFooterWrapper headerAndFooterWrapper;

    //头部控件
    private View headView;
    private View scaleView;

    //头部控件的高度
    private int headViewHeight;

    private List<SectionEntity> sectionEntity;

    //    @Override
//    protected int getContentId() {
//        return R.layout.xt_fragment;
//    }
    public EyeFragment() {
        Log.d("TAG", "进入EyeFragment");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d("TAG","调用onCreateView");
        View view = inflater.inflate(R.layout.fragment_layout, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        loadDatas();
    }

    int by = -1;

    //    @Override
    protected void init(View view) {
//        super.init(view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        headView = LayoutInflater.from(getActivity()).inflate(R.layout.item_header, recyclerView, false);
        //测量头部控件
        headView.measure(0, 0);
        //获取头部控件高度
        headViewHeight = headView.getMeasuredHeight();
        //获得需要缩放的控件对象
        scaleView = headView.findViewById(R.id.scaleView);


        //设置recyclerView的滚动监听,一旦滚动，就保持控件的头部一直顶着头部，一直0
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                headView.setY(0);
            }
        });


        //设置touch监听方法
        //OnTouchListener的方法会先与控件本身的OnTouchEvent所调用
        //如果OnTouch方法返回true，则touchEvent方法不再被调用
        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    //点击不操作
                    case MotionEvent.ACTION_DOWN:
                        break;
                    //移动就会操作，操作就是将头部视图逐渐被覆盖
                    case MotionEvent.ACTION_MOVE: {
                        if (isTop()) {
                            if (by == -1) {
                                //保存移动之前的Y点坐标
                                by = (int) event.getY();
                            }
                            //移动到最后的Y点坐标
                            int ey = (int) event.getY();
                            int my = (ey - by);//在y轴方向的拖动距离

                            ViewGroup.LayoutParams layoutParams = scaleView.getLayoutParams();

                            //如果正在向上移动,并且缩放的图高度大于头部控件高度，就结束跳出
                            if (my < 0 && layoutParams.height <= headViewHeight) {
                                return false;
                            }

                            layoutParams.height = headViewHeight + my / 3;
                            if (layoutParams.height < headViewHeight) {
                                layoutParams.height = headViewHeight;
                            }
                            scaleView.setLayoutParams(layoutParams);
                            return true;
                        }
                    }
                    break;
                    //松开手指，重置
                    case MotionEvent.ACTION_UP: {
                        reset();
                        by = -1;
                    }
                    break;
                }
                return false;
            }
        });
    }

    /**
     * 重置控件,设置返回的动画效果
     */
    private void reset() {
        final ViewGroup.LayoutParams layoutParams = scaleView.getLayoutParams();
        ValueAnimator valueAnimator = ValueAnimator.ofInt(layoutParams.height, headViewHeight);
        valueAnimator.setDuration(500);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int h = (int) animation.getAnimatedValue();
                layoutParams.height = h;
                scaleView.setLayoutParams(layoutParams);

                scaleView.setScaleX((float) h / headViewHeight);
            }
        });
        valueAnimator.start();
    }

    /**
     * 判断RecycleView是否置顶
     *
     * @return
     */
    private boolean isTop() {
        //获得布局管理器
        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        //获得第一个可视视图的位置
        int firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();
        //获得RecycleView的第一个视图
        View viewByPosition = linearLayoutManager.findViewByPosition(0);
        if (firstVisibleItemPosition == 0 && viewByPosition.getTop() == 0) {
            return true;
        }
        return false;
    }

    //    @Override
    protected void loadDatas() {
//        super.loadDatas();
        //通过OKHttp去下载数据JSON
        OkHttpUtils.get().url(Constants.URL_JINGXUAN).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                sectionEntity = JSONUtil.getListByJSON(response);

                //多布局的adapter
                eyeAdapter = new MultiItemTypeAdapter<>(getActivity(), sectionEntity);

                eyeAdapter.addItemViewDelegate(new EyeItem1(getActivity(), EyeFragment.this));
                eyeAdapter.addItemViewDelegate(new EyeItem2(getActivity()));
                eyeAdapter.addItemViewDelegate(new EyeItem3(getActivity(), getChildFragmentManager()));

                //设置头部的adapter
                headerAndFooterWrapper = new HeaderAndFooterWrapper(eyeAdapter);
                headerAndFooterWrapper.addHeaderView(headView);

                //设置adapter
                recyclerView.setAdapter(headerAndFooterWrapper);
            }
        });
    }

    @Subscribe
    public void onSelectPosition(Integer position) {
        View v = recyclerView.findViewWithTag("item" + position);
        if (v != null) {
            int[] sa = new int[2];
            v.getLocationOnScreen(sa);
            int y = sa[1];//获得指定控件的y的坐标
            recyclerView.scrollBy(0, y);
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        //启动详情页的Activity
        Intent intent = new Intent(getActivity(), DescActivity.class);
        ArrayList<SectionEntity.ItemListBean> itemList = (ArrayList<SectionEntity.ItemListBean>) sectionEntity.get(0).getItemList();
        intent.putExtra("datas", itemList);
        intent.putExtra("position", position);

        //获得一个控件在窗口上的位置坐标
        int[] intArray = new int[2];
        view.getLocationOnScreen(intArray);
        intent.putExtra("x", intArray[0]);
        intent.putExtra("y", intArray[1]);

        //传递点击控件的宽高
        intent.putExtra("width", view.getWidth());
        intent.putExtra("height", view.getHeight());

        startActivity(intent);
        //禁止Activity之间转场时的动画效果
        getActivity().overridePendingTransition(0, 0);
    }
}
