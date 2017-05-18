package com.example.hasee.quweidou.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.hasee.quweidou.R;

/**
 * Created by HASEE on 2017/4/17.
 */

public class SplashFragment extends Fragment {

    ImageView iv;
    public static final String INDEX_KEY = "index";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.splash_fragmetn, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupView(view);
    }

    public void setupView(View view) {
        iv = (ImageView) view.findViewById(R.id.splash_iv);

        Bundle bundle = getArguments();
        int index = bundle.getInt(INDEX_KEY);
        //根据是第几张图填充图片
        switch (index) {
            case 0: {
                iv.setImageResource(R.drawable.hyjm1);
            }
            break;
            case 1: {
                iv.setImageResource(R.drawable.hyjm2);
            }
            break;

            case 2: {
                iv.setImageResource(R.drawable.hyjm3);
            }
            break;

            case 3: {
                iv.setImageResource(R.drawable.hyjm4);
            }
            break;


        }

    }
}
