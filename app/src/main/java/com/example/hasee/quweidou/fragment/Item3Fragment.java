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
 * Created by ken on 2017/1/11.
 */
public class Item3Fragment extends Fragment {

    //    @Bind(R.id.iv_bg)
    public ImageView iv;
    //    @Bind(R.id.tv_title)
    public TextView tvTitle;
    //    @Bind(R.id.tv_subtitle)
    public TextView tvSubtitle;

    public static Item3Fragment getInstance(SectionEntity.ItemListBean listBean) {
        Item3Fragment item3Fragment = new Item3Fragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("datas", listBean);
        item3Fragment.setArguments(bundle);
        return item3Fragment;
    }

    //    @Override
//    protected int getContentId() {
//        return R.layout.item3_viewpager_fragmentlayout;
//    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.item3_viewpager_fragmentlayout, container, false);
        iv = (ImageView) view.findViewById(R.id.iv_bg);
        tvTitle = (TextView) view.findViewById(R.id.tv_title);
        tvSubtitle = (TextView) view.findViewById(R.id.tv_subtitle);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

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
        SectionEntity.ItemListBean listBean = (SectionEntity.ItemListBean) bundle.getSerializable("datas");
        tvTitle.setText(listBean.getData().getCategory());

        tvSubtitle.setText(listBean.getData().getTitle());

        Glide.with(getActivity().getApplicationContext())
                .load(listBean.getData().getCover().getFeed())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .thumbnail(0.1f)
                .crossFade()
                .into(iv);
    }
}
