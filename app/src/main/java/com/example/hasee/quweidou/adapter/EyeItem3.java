package com.example.hasee.quweidou.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.FrameLayout;

import com.example.hasee.quweidou.R;
import com.example.hasee.quweidou.entity.SectionEntity;
import com.example.hasee.quweidou.fragment.Item3Fragment;
import com.example.hasee.quweidou.loopviewpager.LoopViewPager;
import com.zhy.adapter.recyclerview.base.ItemViewDelegate;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by HASEE on 2017/4/28.
 */

public class EyeItem3 implements ItemViewDelegate<SectionEntity> {

    private Context context;
    private FragmentManager fragmentManager;
    private int id = 1;

    public EyeItem3(Context context, FragmentManager fragmentManager){
        this.context = context;
        this.fragmentManager = fragmentManager;

    }
    @Override
    public int getItemViewLayoutId() {
        return R.layout.item3;
    }

    @Override
    public boolean isForViewType(SectionEntity item, int position) {
        return item.getType().equals("categorySection");
    }

    @Override
    public void convert(ViewHolder holder, SectionEntity sectionEntity, int position) {
        holder.setText(R.id.tv_title, sectionEntity.getItemList().get(0).getData().getHeader().getTitle())
                .setText(R.id.tv_subtitle, sectionEntity.getItemList().get(0).getData().getHeader().getSubTitle());

        FrameLayout frameLayout = holder.getView(R.id.fl_vp);
        ViewPager viewPager;
        if(frameLayout.getChildCount() > 0){
            viewPager = (ViewPager) frameLayout.getChildAt(0);
        } else {
            viewPager = new LoopViewPager(context);
            viewPager.setId(id++);
            viewPager.setPadding(context.getResources().getDimensionPixelSize(R.dimen.paddingvalue), 0, context.getResources().getDimensionPixelSize(R.dimen.paddingvalue), 0);
            viewPager.setClipToPadding(false);
            frameLayout.addView(viewPager);
        }
        Adapter adapter = new Adapter(fragmentManager, sectionEntity.getItemList().get(0).getData().getItemList());
        viewPager.setAdapter(adapter);
    }

    private static class Adapter extends FragmentStatePagerAdapter {

        private List<SectionEntity.ItemListBean> datas;

        public Adapter(FragmentManager fm, List<SectionEntity.ItemListBean> datas) {
            super(fm);
            this.datas = datas;
        }

        @Override
        public Fragment getItem(int position) {
            position = LoopViewPager.toRealPosition(position, getCount());
            return Item3Fragment.getInstance(datas.get(position % getCount()));
        }

        @Override
        public int getCount() {
            return datas.size();
        }
    }
}
