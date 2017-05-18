package com.example.hasee.quweidou.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.hasee.quweidou.R;
import com.example.hasee.quweidou.entity.SectionEntity;
import com.example.hasee.quweidou.utils.ScreenUtil;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ItemViewDelegate;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by HASEE on 2017/4/28.
 */

public class EyeItem2 implements ItemViewDelegate<SectionEntity> {

    private Context context;

    public EyeItem2(Context context) {
        this.context = context;
    }

    @Override
    public int getItemViewLayoutId() {
        return R.layout.item2;
    }

    @Override
    public boolean isForViewType(SectionEntity item, int position) {
        return item.getType().equals("lightTopicSection");
    }

    @Override
    public void convert(ViewHolder holder, SectionEntity sectionEntity, int position) {
        //加载图片
        final ImageView ivBg = holder.getView(R.id.iv_bg);
        Glide.with(context.getApplicationContext())
                .load(sectionEntity.getItemList().get(0).getData().getHeader().getCover())
                .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        int width = resource.getWidth();
                        int height = resource.getHeight();

                        int sWidth = ScreenUtil.getScreenWidth(context);
                        int mHeight = (int) (((float) height / width) * sWidth);

                        ViewGroup.LayoutParams layoutParams = ivBg.getLayoutParams();
                        layoutParams.height = mHeight;
                        ivBg.setLayoutParams(layoutParams);

                        ivBg.setImageBitmap(resource);
                    }
                });

        //设置RecyclerView
        RecyclerView recyclerView = holder.getView(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));

        //获得数据源
        List<SectionEntity.ItemListBean> itemList = sectionEntity.getItemList().get(0).getData().getItemList();
        Adapter adapter = new Adapter(context, R.layout.item2_recyclerviewitem, itemList.subList(0, itemList.size() - 1));
        recyclerView.setAdapter(adapter);
    }

    private static class Adapter extends CommonAdapter<SectionEntity.ItemListBean> {

        public Adapter(Context context, int layoutId, List<SectionEntity.ItemListBean> datas) {
            super(context, layoutId, datas);
        }

        @Override
        protected void convert(ViewHolder holder, SectionEntity.ItemListBean itemListBean, int position) {
            holder
                    .setText(R.id.tv_title, itemListBean.getData().getTitle())
                    .setText(R.id.tv_subtitle, itemListBean.getSubTitle());

            ImageView iv = holder.getView(R.id.iv_bg);
            Glide.with(mContext.getApplicationContext())
                    .load(itemListBean.getData().getCover().getFeed())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .thumbnail(0.1f)
                    .crossFade()
                    .into(iv);
        }
    }
}
