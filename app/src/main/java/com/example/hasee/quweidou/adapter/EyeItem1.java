package com.example.hasee.quweidou.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.hasee.quweidou.R;
import com.example.hasee.quweidou.entity.SectionEntity;
import com.zhy.adapter.recyclerview.base.ItemViewDelegate;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by HASEE on 2017/4/28.
 */

public class EyeItem1 implements ItemViewDelegate<SectionEntity> {

    private Context context;
    private OnItemClickListener onItemClickListener;

    public EyeItem1(Context context, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public int getItemViewLayoutId() {
        return R.layout.item1;
    }

    @Override
    public boolean isForViewType(SectionEntity item, int position) {
        return item.getType().equals("feedSection");
    }

    @Override
    public void convert(ViewHolder holder, SectionEntity sectionEntity, int position) {
        LinearLayout linearLayout = holder.getView(R.id.ll_item1);
        //清空线性布局
        linearLayout.removeAllViews();

        //循环处理数据
        List<SectionEntity.ItemListBean> itemList = sectionEntity.getItemList();
        //去除头部数据
//        itemList.remove(0);

        for (int i = 0; i < itemList.size(); i++) {
            SectionEntity.ItemListBean itemListBean = itemList.get(i);
            //判断类型是否为video,如果不是，则跳出当前一个循环，如果是，执行下列程序
            if (!itemListBean.getType().equals("video")) {
                Log.d("TAG", "当前跳出的i是: " + i);
                continue;
            }
            //加载背景图片
            View inflate = LayoutInflater.from(context).inflate(R.layout.item_layout, null);
            inflate.setTag(R.id.tagid, i);
            //点击事件
            inflate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(v, (Integer) v.getTag(R.id.tagid));
                    }
                }
            });

            //设置背景图片
            final ImageView ivBg = (ImageView) inflate.findViewById(R.id.iv_bg);
            Glide.with(context.getApplicationContext())
                    .load(itemListBean.getData().getCover().getFeed())
                    .asBitmap()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                            //获得图片的原始宽高
                            int width = resource.getWidth();
                            int heigth = resource.getHeight();
                            //获得手机屏幕的宽
                            int screenWidth = context.getResources().getDisplayMetrics().widthPixels;
                            //计算适应屏幕的高度
                            int mHeight = (int) (((float) heigth / width) * screenWidth);

                            ViewGroup.LayoutParams layoutParams = ivBg.getLayoutParams();
                            layoutParams.height = mHeight;
                            ivBg.setLayoutParams(layoutParams);
                            //设置图片
                            ivBg.setImageBitmap(resource);
                        }
                    });


            //加载标题
            TextView tvTitle = (TextView) inflate.findViewById(R.id.tv_title);
            tvTitle.setText(itemListBean.getData().getTitle());

            //加载副标题
            TextView tvSubTitle = (TextView) inflate.findViewById(R.id.tv_subtitle);
            tvSubTitle.setText(itemListBean.getSubTitle());

            //设置Tag
            inflate.setTag("item" + i);

            //加入线性布局
            linearLayout.addView(inflate);
        }
    }


    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
}
