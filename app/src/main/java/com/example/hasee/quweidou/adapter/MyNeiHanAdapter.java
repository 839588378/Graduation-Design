package com.example.hasee.quweidou.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by HASEE on 2017/4/19.
 */

public abstract class MyNeiHanAdapter<T> extends BaseAdapter {

    List<T> data;
    LayoutInflater inflater;
    int[] layoutId;

    //通过构造函数传入数据，上下文对象，以及四种布局
    public MyNeiHanAdapter(List<T> data, Context context, int... layoutId) {
        this.data = data;
        this.layoutId = layoutId;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    //说明是总共有几种不同的视图
    @Override
    public int getViewTypeCount() {
        return layoutId.length;
    }

    public abstract void bangData(int position, ViewHolder holder);

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        //获取layout视图的是第几个类型,看源码都是返回0？
        int layoutType = getItemViewType(position);
        if (convertView == null) {
            convertView = inflater.inflate(layoutId[layoutType], parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        //绑定数据
        bangData(position,holder);

        return convertView;
    }

    public static class ViewHolder {
        private View view;

        public ViewHolder(View view) {
            this.view = view;
        }

        public View findViewById(int viewId) {
            return view.findViewById(viewId);
        }
    }
}
