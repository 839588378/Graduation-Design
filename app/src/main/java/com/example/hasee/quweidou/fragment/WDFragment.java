package com.example.hasee.quweidou.fragment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.hasee.quweidou.R;
import com.example.hasee.quweidou.utils.DensityUtil;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.line.Line;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.weibo.TencentWeibo;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.yixin.friends.Yixin;

/**
 * Created by HASEE on 2017/4/18.
 */

public class WDFragment extends Fragment implements View.OnClickListener {
    private EditText et_name, et_password;
    private Button  btn_eye, btn_password_clear, btn_name_clear;
    private TextWatcher password_watcher, username_watcher;
    private TextView other_login,btn_login, btn_register;
    private ImageButton QQ_Login, Sina_Login, WeiXin_Login, YiXin_Login, tenxunweibo_Login, Line_Login;

    private int scrrenWidth;
    private View popupView;

    private PopupWindow popupWindow;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initWatcher();
        //获取屏幕宽高
        scrrenWidth = DensityUtil.getScreenWidth(getActivity());

    }


    /**
     * 登录帐号，密码输入控件公用这一个watcher
     */
    private void initWatcher() {
        username_watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                btn_name_clear.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() > 0) {
                    btn_name_clear.setVisibility(View.VISIBLE);
                } else {
                    btn_name_clear.setVisibility(View.INVISIBLE);
                }
            }
        };
        password_watcher = new TextWatcher() {
            //开始输入密码前，清楚这个控件不显示
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                btn_password_clear.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //密码有字符的时候，这个控件可以显示
                if (s.toString().length() > 0) {
                    btn_password_clear.setVisibility(View.VISIBLE);
                } else {
                    btn_password_clear.setVisibility(View.INVISIBLE);
                }
            }
        };
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.wd_fragment, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupViews(view);

        //获取PopupWindow的视图
        popupView = LayoutInflater.from(getActivity()).inflate(R.layout.otherlogin_pop, null);
        //设置相关属性
        popupWindow = new PopupWindow(popupView, scrrenWidth, 1050);
        //设置背景为灰色半透明
        popupWindow.setBackgroundDrawable(new ColorDrawable(0xaaC0C0C0));
        // 设置外部可触摸，即外部点击时，该pop消失
        popupWindow.setOutsideTouchable(true);

        //获取对应的控件
        QQ_Login = (ImageButton) popupView.findViewById(R.id.QQ_Login);
        Sina_Login = (ImageButton) popupView.findViewById(R.id.Sina_Login);
        WeiXin_Login = (ImageButton) popupView.findViewById(R.id.WeiXin_Login);
        YiXin_Login = (ImageButton) popupView.findViewById(R.id.YiXin_Login);
        tenxunweibo_Login = (ImageButton) popupView.findViewById(R.id.tenxunweibo_Login);
        Line_Login = (ImageButton) popupView.findViewById(R.id.Line_Login);

        //设置监听
        QQ_Login.setOnClickListener(this);
        Sina_Login.setOnClickListener(this);
        WeiXin_Login.setOnClickListener(this);
        YiXin_Login.setOnClickListener(this);
        tenxunweibo_Login.setOnClickListener(this);
        Line_Login.setOnClickListener(this);
    }

    private void setupViews(View view) {
        et_name = (EditText) view.findViewById(R.id.et_username);
        //各自关联改变
        et_name.addTextChangedListener(username_watcher);

        et_password = (EditText) view.findViewById(R.id.et_password);
        et_password.addTextChangedListener(password_watcher);

        btn_eye = (Button) view.findViewById(R.id.eye);
        btn_eye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //判断输入的密码是否为文字类型

                Log.d("TAG", ""+et_password.getInputType());
                if (et_password.getInputType() == 0x00000081) {
                    et_password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                }
                else  {
                    et_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
                //显示密码
                et_password.setSelection(et_password.getText().toString().length());
            }
        });

        btn_login = (TextView) view.findViewById(R.id.btn_login);
        btn_login.setBackgroundColor(Color.TRANSPARENT);
        btn_register = (TextView) view.findViewById(R.id.bt_register);

        btn_name_clear = (Button) view.findViewById(R.id.btn_name_clear);
        btn_name_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_name.setText("");
            }
        });

        btn_password_clear = (Button) view.findViewById(R.id.btn_password_clear);
        btn_password_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_password.setText("");
            }
        });

        //第三方登录
        other_login = (TextView) view.findViewById(R.id.other_login);
        other_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //设置弹出的地点
                popupWindow.showAtLocation(getActivity().findViewById(R.id.other_login), Gravity.BOTTOM, 0, 0);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.QQ_Login: {
                LoginName(QQ.NAME);
            }
            break;
            case R.id.Sina_Login: {
                LoginName(SinaWeibo.NAME);
            }
            break;
            case R.id.WeiXin_Login: {
                LoginName(Wechat.NAME);
            }
            break;
            case R.id.YiXin_Login: {
                LoginName(Yixin.NAME);
            }
            break;
            case R.id.tenxunweibo_Login: {
                LoginName(TencentWeibo.NAME);
            }
            break;
            case R.id.Line_Login: {
                LoginName(Line.NAME);
            }
            break;

        }
    }

    private void LoginName(String name) {
        Platform paltform = ShareSDK.getPlatform(name);
        //回调信息，可以在这里获取基本的授权返回的信息，但是注意如果做提示和UI操作要传到主线程handler里去执行
        paltform.setPlatformActionListener(new PlatformActionListener() {

            @Override
            public void onError(Platform arg0, int arg1, Throwable arg2) {
                // TODO Auto-generated method stub
                arg2.printStackTrace();
            }

            @Override
            public void onComplete(Platform arg0, int arg1, HashMap<String, Object> arg2) {
                // TODO Auto-generated method stub
                //输出所有授权信息
                arg0.getDb().exportData();
                //请求用户在此平台上的ID
                arg0.getDb().getUserId();
            }

            @Override
            public void onCancel(Platform arg0, int arg1) {
                // TODO Auto-generated method stub

            }
        });
        //authorize与showUser单独调用一个即可
        paltform.authorize();//单独授权,OnComplete返回的hashmap是空的
        paltform.showUser(null);//授权并获取用户信息
        //移除授权
        //weibo.removeAccount(true);
    }
}
