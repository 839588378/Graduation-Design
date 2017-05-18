package com.example.hasee.quweidou.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.hasee.quweidou.R;
import com.example.hasee.quweidou.entity.SectionEntity;

/**
 * Created by HASEE on 2017/4/29.
 */

public class DescFragment extends Fragment {
    //切换头部播放器
//    @Bind(R.id.iv_top)
    public ImageView ivTop;
    //    @Bind(R.id.iv_bottom_bg)
    public ImageView ivBottom;
    //    @Bind(R.id.tv_title)
    public TextView tvTitle;
    //    @Bind(R.id.tv_subtitle)
    public TextView tvSubtitle;
    //    @Bind(R.id.tv_content)
    public TextView tvContent;

    //单例模式
    public static DescFragment getInstance(SectionEntity.ItemListBean itemListBean) {
        DescFragment descFragment = new DescFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("data", itemListBean);
        descFragment.setArguments(bundle);
        return descFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_desc_viewpager_fragment, container, false);
        ivTop = (ImageView) view.findViewById(R.id.iv_top);
        ivBottom = (ImageView) view.findViewById(R.id.iv_bottom_bg);
        tvTitle = (TextView) view.findViewById(R.id.tv_title);
        tvSubtitle = (TextView) view.findViewById(R.id.tv_subtitle);
        tvContent = (TextView) view.findViewById(R.id.tv_content);
        return view;
    }

    //    @Override
//    protected int getContentId() {
//        return R.layout.activity_desc_viewpager_fragment;
//    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            getDatas(bundle);
        }
    }

    //    @Override
    protected void getDatas(Bundle bundle) {
//        super.getDatas(bundle);
        SectionEntity.ItemListBean data = (SectionEntity.ItemListBean) bundle.getSerializable("data");


        //设置主标题
        tvTitle.setText(data.getData().getTitle());
        //设置副标题
        tvSubtitle.setText(data.getSubTitle());
        //设置介绍内容
        tvContent.setText(data.getData().getDescription());
        //你要在这里设置播放器视图
        Glide.with(getActivity().getApplicationContext())
                .load(data.getData().getCover().getFeed())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .thumbnail(0.1f)
                .into(ivTop);

        Glide.with(getActivity().getApplicationContext())
                .load(data.getData().getCover().getBlurred())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .thumbnail(0.1f)
                .into(ivBottom);
    }
}
