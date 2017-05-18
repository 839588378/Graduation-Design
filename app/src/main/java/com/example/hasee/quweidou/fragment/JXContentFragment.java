package com.example.hasee.quweidou.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.hasee.quweidou.R;
import com.example.hasee.quweidou.adapter.MyNeiHanAdapter;
import com.example.hasee.quweidou.bean.NeiHanBean;
import com.example.hasee.quweidou.ui.WebActivity;
import com.example.hasee.quweidou.utils.DensityUtil;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.sharesdk.onekeyshare.OnekeyShare;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.example.hasee.quweidou.R.drawable.cai_has_clicked_black;
import static com.example.hasee.quweidou.R.drawable.cai_not_clicked;
import static com.example.hasee.quweidou.R.drawable.shenhe_ding_pic_an;
import static com.example.hasee.quweidou.R.drawable.shenhe_ding_pic_night;

/**
 * Created by HASEE on 2017/4/18.
 */

public class JXContentFragment extends Fragment implements AbsListView.OnScrollListener {

    List<NeiHanBean> beanList;
    ListView listView;
    MyNeiHanAdapter<NeiHanBean> adapter;

    //下拉刷新
    SwipeRefreshLayout refresh;
    //点击刷新控件，就刷新并且返回顶部
    ImageView ivToTop;
    //动画
    Animation anim;
    //用来接收传过来的URL
    String url;
    //系统自带的媒体播放器
    MediaPlayer mediaPlayer;

    private int screenWidth;
    private int screenHeight;

    //主线程中处理发送过来的信息,更新了URL，就马上更新adapter
    Handler handle = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0: {
                    adapter.notifyDataSetChanged();
                }
                break;
            }

        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //获取屏幕的宽高
        screenWidth = DensityUtil.getScreenWidth(getActivity());
        screenHeight = DensityUtil.getScreenHeight(getActivity());

        //获取传过来的URL
        url = getArguments().getString(JXFragment.URL_KEY);
        //从URL中获取对应的属性数据，并且保存在bean对象中
        getData(url);
        mediaPlayer = new MediaPlayer();
        //初始化数据源和adapter
        initData();

    }

    private void initData() {
        adapter = new MyNeiHanAdapter<NeiHanBean>(beanList, getActivity()
                , R.layout.jxcon_lv_item, R.layout.jxcon_lv_item_gif
                , R.layout.jxcon_lv_item_image, R.layout.jxcon_lv_item_text) {

            @Override
            public void bangData(int position, ViewHolder holder) {
                //得到数据类型，根据不同布局加载不同数据
                final NeiHanBean nhb = beanList.get(position);
                //获取是第几钟布局
                int type = Integer.valueOf(nhb.getType());

                //根据布局类型进行加载
                loadDataByLayoutType(nhb, type, holder, position);


            }

            //获取当前试图的类型，给adapter调用，决定是那一个试图
            @Override
            public int getItemViewType(int position) {
                String nextType = beanList.get(position).getType();
                return Integer.valueOf(nextType);
            }
        };
    }

    private int currentPosition = -1;

    private boolean clickVideoGood = true;
    private boolean clickVideoBad = true;

    private boolean clickGifGood = true;
    private boolean clickGifBad = true;

    private boolean clickImageGood = true;
    private boolean clickImageBad = true;

    private boolean clickTextGood = true;
    private boolean clickTextBad = true;

    private void loadDataByLayoutType(final NeiHanBean nhb, int type, MyNeiHanAdapter.ViewHolder holder, int position) {

        switch (type) {
            case 0: {
                //加载评论，分享等等
                final TextView video_good = (TextView) holder.findViewById(R.id.vido_good);
                final TextView video_bad = (TextView) holder.findViewById(R.id.vido_bad);
                final TextView video_share = (TextView) holder.findViewById(R.id.vido_share);
                final TextView video_say = (TextView) holder.findViewById(R.id.vido_say);

                final ImageView iv_good = (ImageView) holder.findViewById(R.id.iv_good);
                final ImageView iv_bad = (ImageView) holder.findViewById(R.id.iv_bad);
                final ImageView iv_share = (ImageView) holder.findViewById(R.id.iv_share);

                video_good.setText(nhb.getGood());
                //点击了赞+1
                iv_good.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //点击第一次，就+1，第二次就-1，回归原来的
                        if (clickVideoGood) {
                            video_good.setText(Integer.valueOf(nhb.getGood()) + 1 + "");
                            clickVideoGood = false;
                            iv_good.setBackgroundDrawable(getResources().getDrawable(shenhe_ding_pic_an));
                            Toast.makeText(getActivity(), "点赞+1~~", Toast.LENGTH_SHORT).show();
                        } else {
                            video_good.setText(nhb.getGood());
                            clickVideoGood = true;
                            iv_good.setBackgroundDrawable(getResources().getDrawable(shenhe_ding_pic_night));
                            Toast.makeText(getActivity(), "您取消了点赞", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                video_bad.setText(nhb.getBad());
                //点击了不支持+1
                iv_bad.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (clickVideoBad) {
                            video_bad.setText(Integer.valueOf(nhb.getBad()) + 1 + "");
                            clickVideoBad = false;
                            iv_bad.setBackgroundDrawable(getResources().getDrawable(cai_has_clicked_black));
                            Toast.makeText(getActivity(), "吐槽+1~~", Toast.LENGTH_SHORT).show();
                        } else {
                            video_bad.setText(nhb.getBad());
                            clickVideoBad = true;
                            iv_bad.setBackgroundDrawable(getResources().getDrawable(cai_not_clicked));
                            Toast.makeText(getActivity(), "吐槽-1~~", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                //这里你要跳转到第三方分享
                video_share.setText(nhb.getShare());
                iv_share.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showShare(nhb);
                    }
                });

                //这里跳转WebView
                video_say.setText(nhb.getSay());

                //加载头像，利用Picasso这个框架来加载图片进去
                ImageView iv_head = (ImageView) holder.findViewById(R.id.iv_head);
                Picasso.with(getActivity()).load(nhb.getImgHead()).into(iv_head);

                //加载名字
                TextView tv_name = (TextView) holder.findViewById(R.id.tv_name);
                tv_name.setText(nhb.getName());

                //加载通过时间
                TextView tv_passtime = (TextView) holder.findViewById(R.id.tv_passtime);
                tv_passtime.setText(nhb.getPasstime());

                //加载标题
                TextView tv_content = (TextView) holder.findViewById(R.id.tv_content);
                tv_content.setText(nhb.getContext());

                //加载视频图片
                ImageView iv_cover = (ImageView) holder.findViewById(R.id.iv_cover);
                ViewGroup.LayoutParams params = iv_cover.getLayoutParams();
                params.width = nhb.getWidth();
                params.height = nhb.getHeight();
                iv_cover.requestLayout();
                Picasso.with(getActivity()).load(nhb.getCover()).into(iv_cover);

                //加载视频
                SurfaceView surfaceView = (SurfaceView) holder.findViewById(R.id.surfaceview);
                surfaceView.setLayoutParams(params);

                Object tag = iv_cover.getTag();
                if (tag != null) {
                    Integer tag1 = (Integer) tag;
                    if (tag1 == currentPosition && tag1 != position) {
                        mediaPlayer.stop();
                        currentPosition = -1;
                    }
                }
                iv_cover.setTag(position);
                if (position == currentPosition) {
                    //设置不可见
                    iv_cover.setVisibility(View.INVISIBLE);
                    //设置为可见
                    surfaceView.setVisibility(View.VISIBLE);
                    //重置媒体播放器
                    mediaPlayer.reset();
                    try {
                        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                        mediaPlayer.setDisplay(surfaceView.getHolder());
                        Log.d("TAG", "videoUrl =" + nhb.getVideoUrl());
                        mediaPlayer.setDataSource(nhb.getVideoUrl());
                        mediaPlayer.prepareAsync();
                        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                            @Override
                            public void onPrepared(MediaPlayer mp) {
                                mp.start();
                            }
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    iv_cover.setVisibility(View.VISIBLE);
                    surfaceView.setVisibility(View.INVISIBLE);
                }
                //图片点击监听
                iv_cover.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("TAG", "点击了Video");
                        Integer tag = (Integer) v.getTag();
                        currentPosition = tag;
                        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                            mediaPlayer.stop();
                        }
                        mediaPlayer.start();
                        adapter.notifyDataSetChanged();
                    }
                });
            }
            break;
            case 1: {
                //加载评论，分享等等
                final TextView gif_good = (TextView) holder.findViewById(R.id.gif_good);
                final TextView gif_bad = (TextView) holder.findViewById(R.id.gif_bad);
                final TextView gif_share = (TextView) holder.findViewById(R.id.gif_share);
                final TextView gif_say = (TextView) holder.findViewById(R.id.gif_say);

                final ImageView iv_gif_good = (ImageView) holder.findViewById(R.id.iv_gif_good);
                final ImageView iv_gif_bad = (ImageView) holder.findViewById(R.id.iv_gif_bad);
                final ImageView iv_gif_share = (ImageView) holder.findViewById(R.id.iv_gif_share);

                gif_good.setText(nhb.getGood());
                gif_bad.setText(nhb.getBad());
                gif_share.setText(nhb.getShare());
                gif_say.setText(nhb.getSay());

                iv_gif_good.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (clickGifGood) {
                            gif_good.setText(Integer.valueOf(nhb.getGood()) + 1 + "");
                            clickGifGood = false;
                            iv_gif_good.setBackgroundDrawable(getResources().getDrawable(shenhe_ding_pic_an));
                            Toast.makeText(getActivity(), "点赞+1~~", Toast.LENGTH_SHORT).show();
                        } else {
                            gif_good.setText(nhb.getGood());
                            clickGifGood = true;
                            iv_gif_good.setBackgroundDrawable(getResources().getDrawable(shenhe_ding_pic_night));
                            Toast.makeText(getActivity(), "您取消了点赞", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                iv_gif_bad.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (clickGifBad) {
                            gif_bad.setText(Integer.valueOf(nhb.getBad()) + 1 + "");
                            clickGifBad = false;
                            iv_gif_bad.setBackgroundDrawable(getResources().getDrawable(cai_has_clicked_black));
                            Toast.makeText(getActivity(), "吐槽+1~~", Toast.LENGTH_SHORT).show();
                        } else {
                            gif_bad.setText(nhb.getBad());
                            clickGifBad = true;
                            iv_gif_bad.setBackgroundDrawable(getResources().getDrawable(cai_not_clicked));
                            Toast.makeText(getActivity(), "吐槽-1~~", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                //第三方分享
                iv_gif_share.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showShare(nhb);
                    }
                });

                //加载头像
                ImageView iv_head1 = (ImageView) holder.findViewById(R.id.iv_head1);
                Picasso.with(getActivity()).load(nhb.getImgHead()).into(iv_head1);

                //加载名字
                TextView tv_name1 = (TextView) holder.findViewById(R.id.tv_name1);
                tv_name1.setText(nhb.getName());

                //加载通过时间
                TextView tv_passtime = (TextView) holder.findViewById(R.id.tv_passtime1);
                tv_passtime.setText(nhb.getPasstime());

                //加载标题
                TextView tv_content1 = (TextView) holder.findViewById(R.id.tv_content1);
                tv_content1.setText(nhb.getContext());

                //加载图片
                String share_url = nhb.getShare_url();
                ImageView gif = (ImageView) holder.findViewById(R.id.gif);
                Glide.with(getActivity()).load(share_url).asGif().into(gif);
            }
            break;
            case 2: {

                //加载评论，分享等等
                final TextView image_good = (TextView) holder.findViewById(R.id.image_good);
                final TextView image_bad = (TextView) holder.findViewById(R.id.image_bad);
                final TextView image_share = (TextView) holder.findViewById(R.id.image_share);
                final TextView image_say = (TextView) holder.findViewById(R.id.image_say);

                final ImageView iv_iv_good = (ImageView) holder.findViewById(R.id.iv_iv_good);
                final ImageView iv_iv_bad = (ImageView) holder.findViewById(R.id.iv_iv_bad);
                final ImageView iv_iv_share = (ImageView) holder.findViewById(R.id.iv_iv_share);


                image_good.setText(nhb.getGood());
                iv_iv_good.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (clickImageGood) {
                            image_good.setText(Integer.valueOf(nhb.getGood()) + 1 + "");
                            clickImageGood = false;
                            iv_iv_good.setBackgroundDrawable(getResources().getDrawable(shenhe_ding_pic_an));
                            Toast.makeText(getActivity(), "点赞+1~~", Toast.LENGTH_SHORT).show();
                        } else {
                            image_good.setText(nhb.getGood());
                            clickImageGood = true;
                            iv_iv_good.setBackgroundDrawable(getResources().getDrawable(shenhe_ding_pic_night));
                            Toast.makeText(getActivity(), "您取消了点赞", Toast.LENGTH_SHORT).show();

                        }
                    }
                });

                image_bad.setText(nhb.getBad());
                iv_iv_bad.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (clickImageBad) {
                            image_bad.setText(Integer.valueOf(nhb.getBad()) + 1 + "");
                            clickImageBad = false;
                            iv_iv_bad.setBackgroundDrawable(getResources().getDrawable(cai_has_clicked_black));
                            Toast.makeText(getActivity(), "吐槽+1~~", Toast.LENGTH_SHORT).show();
                        } else {
                            image_bad.setText(nhb.getBad());
                            clickImageBad = true;
                            iv_iv_bad.setBackgroundDrawable(getResources().getDrawable(cai_not_clicked));
                            Toast.makeText(getActivity(), "吐槽-1~~", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                image_share.setText(nhb.getShare());
                iv_iv_share.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showShare(nhb);
                    }
                });

                image_say.setText(nhb.getSay());

                //加载头像
                ImageView iv_head2 = (ImageView) holder.findViewById(R.id.iv_head2);
                Picasso.with(getActivity()).load(nhb.getImgHead()).into(iv_head2);

                //加载名字
                TextView tv_name2 = (TextView) holder.findViewById(R.id.tv_name2);
                tv_name2.setText(nhb.getName());

                //加载通过时间
                TextView tv_passtime = (TextView) holder.findViewById(R.id.tv_passtime2);
                tv_passtime.setText(nhb.getPasstime());

                //加载标题
                TextView tv_content2 = (TextView) holder.findViewById(R.id.tv_content2);
                tv_content2.setText(nhb.getContext());

                //加载图片
                final ImageView iv_cover2 = (ImageView) holder.findViewById(R.id.iv_cover2);
                Glide.with(getActivity()).load(nhb.getImage()).into(iv_cover2);
                final String share_url = nhb.getShare_url();

                //点击这个图片，就回跳转WebView
                iv_cover2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("TAG", "点击了ImageView的iv_cover2");
                        Intent intent = new Intent(getActivity(), WebActivity.class);
                        intent.putExtra("to_url", share_url);
                        getActivity().startActivity(intent);
                    }
                });
            }
            break;
            case 3: {
                //加载评论，分享等等
                final TextView text_good = (TextView) holder.findViewById(R.id.text_good);
                final TextView text_bad = (TextView) holder.findViewById(R.id.text_bad);
                final TextView text_share = (TextView) holder.findViewById(R.id.text_share);
                final TextView text_say = (TextView) holder.findViewById(R.id.text_say);


                final ImageView iv_tv_good = (ImageView) holder.findViewById(R.id.iv_tv_good);
                final ImageView iv_tv_bad = (ImageView) holder.findViewById(R.id.iv_tv_bad);
                ImageView iv_tv_share = (ImageView) holder.findViewById(R.id.iv_tv_share);

                text_good.setText(nhb.getGood());
                iv_tv_good.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (clickTextGood) {
                            text_good.setText(Integer.valueOf(nhb.getGood()) + 1 + "");
                            clickTextGood = false;
                            iv_tv_good.setBackgroundDrawable(getResources().getDrawable(shenhe_ding_pic_an));
                            Toast.makeText(getActivity(), "点赞+1~~", Toast.LENGTH_SHORT).show();

                        } else {
                            text_good.setText(nhb.getGood());
                            clickTextGood = true;
                            iv_tv_good.setBackgroundDrawable(getResources().getDrawable(shenhe_ding_pic_night));
                            Toast.makeText(getActivity(), "您取消了点赞", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                text_bad.setText(nhb.getBad());
                iv_tv_bad.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (clickTextBad) {
                            text_bad.setText(Integer.valueOf(nhb.getGood()) + 1 + "");
                            clickTextBad = false;
                            iv_tv_bad.setBackgroundDrawable(getResources().getDrawable(cai_has_clicked_black));
                            Toast.makeText(getActivity(), "吐槽+1~~", Toast.LENGTH_SHORT).show();
                        } else {
                            text_bad.setText(nhb.getGood());
                            clickTextBad = true;
                            iv_tv_bad.setBackgroundDrawable(getResources().getDrawable(cai_not_clicked));
                            Toast.makeText(getActivity(), "吐槽-1~~", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                text_share.setText(nhb.getShare());
                iv_tv_share.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showShare(nhb);
                    }
                });
                text_say.setText(nhb.getSay());

                //加载头像
                ImageView iv_head3 = (ImageView) holder.findViewById(R.id.iv_head3);
                Picasso.with(getActivity()).load(nhb.getImgHead()).into(iv_head3);

                //加载名字
                TextView tv_name3 = (TextView) holder.findViewById(R.id.tv_name3);
                tv_name3.setText(nhb.getName());

                //加载通过时间
                TextView tv_passtime3 = (TextView) holder.findViewById(R.id.tv_passtime3);
                tv_passtime3.setText(nhb.getPasstime());

                //加载标题
                TextView tv_content3 = (TextView) holder.findViewById(R.id.tv_text3);
                tv_content3.setText(nhb.getContext());
                //点击这个Text文本就会跳转WebView
                final String share_url = nhb.getShare_url();
                tv_content3.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        Log.d("TAG", "点击了TextView的tv_content3");
                        Intent intent = new Intent(getActivity(), WebActivity.class);
                        intent.putExtra("to_url", share_url);
                        getActivity().startActivity(intent);
                    }
                });
            }
            break;

        }
    }

    private void getData(String url) {
        beanList = new ArrayList<>();
        //利用OkHttp请求数据
        OkHttpClient client = new OkHttpClient.Builder().build();
        //构造一个Request对象
        Request request = new Request.Builder()
                .url(url).build();
        //通过request的对象去构造得到一个Call对象
        Call call = client.newCall(request);
        //请求加入调度，这个是异步执行的方法
        call.enqueue(new Callback() {
            //请求失败
            @Override
            public void onFailure(Call call, IOException e) {

            }

            //成功请求数据
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //解析Json
                parseJson(response.body().string());
                //子线程向主线程发送消息，这里我调用Handle
                handle.sendEmptyMessage(0);
            }
        });
    }

    private void parseJson(String jsonString) {
        try {
            //转化成JSON对象
            JSONObject json = new JSONObject(jsonString);
            //获取JSON数组
            JSONArray array = json.getJSONArray("list");

            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                //从JSON对象中获取对应的属性
                String type = object.optString("type");
                String time = object.optString("passtime");
                String share_url = object.optString("share_url");
                String imgHead = object.optJSONObject("u").optJSONArray("header").optString(0);
                String name = object.optJSONObject("u").optString("name");
                String passtime = object.optString("passtime");

                NeiHanBean bean = new NeiHanBean();
                bean.setName(name);
                bean.setShare_url(share_url);
                bean.setImgHeag(imgHead);
                bean.setTime(time);
                bean.setPasstime(passtime);

                //筛选type
                JudgeType(type, object, bean);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void JudgeType(String type, JSONObject obj, final NeiHanBean bean) {
        switch (type) {
            case "video": {
                int videoWidth = obj.optJSONObject("video").optInt("width");
                int videoHeight = obj.optJSONObject("video").optInt("height");
                String videoUrl = obj.optJSONObject("video").optJSONArray("video").optString(0);
                String cover = obj.optJSONObject("video").optJSONArray("thumbnail_small").optString(0);
                String context = obj.optString("text");
                String good = obj.optString("up");
                String bad = obj.optString("down");
                String share = obj.optString("forward");
                String say = obj.optString("comment");
                bean.setGood(good);
                bean.setBad(bad);
                bean.setShare(share);
                bean.setSay(say);
                bean.setType("0");
                bean.setContext(context);
                bean.setWidth(videoWidth);
                bean.setHeight(videoHeight);
                bean.setVideoUrl(videoUrl);
                bean.setCover(cover);
                beanList.add(bean);
            }
            break;
            case "image": {
                int imageWidth = obj.optJSONObject("image").optInt("width");
                int imageHeight = obj.optJSONObject("image").optInt("height");
                String bigImag = obj.optJSONObject("image").optJSONArray("download_url").optString(0);
                String image = obj.optJSONObject("image").optJSONArray("thumbnail_small").optString(0);
                String context = obj.optString("text");
                String good = obj.optString("up");
                String bad = obj.optString("down");
                String share = obj.optString("forward");
                String say = obj.optString("comment");
                bean.setGood(good);
                bean.setBad(bad);
                bean.setShare(share);
                bean.setSay(say);
                bean.setType("2");
                bean.setContext(context);
                bean.setImage(image);
                bean.setImageWidth(imageWidth);
                bean.setImageHeight(imageHeight);
                beanList.add(bean);
            }
            break;
            case "gif": {
                int gifWidth = obj.optJSONObject("gif").optInt("width");
                int gifHeight = obj.optJSONObject("gif").optInt("height");
                String context = obj.optString("text");
                String gifUrl = obj.optJSONObject("gif").optJSONArray("images").optString(0);
                bean.setType("1");

                String good = obj.optString("up");
                String bad = obj.optString("down");
                String share = obj.optString("forward");
                String say = obj.optString("comment");
                bean.setGood(good);
                bean.setBad(bad);
                bean.setShare(share);
                bean.setSay(say);

                bean.setContext(context);
                bean.setGifUrl(gifUrl);
                bean.setGifWidth(gifWidth);
                bean.setGifHeight(gifHeight);
                beanList.add(bean);
            }
            break;
            case "text": {
                String context = obj.optString("text");
                String good = obj.optString("up");
                String bad = obj.optString("down");
                String share = obj.optString("forward");
                String say = obj.optString("comment");
                bean.setGood(good);
                bean.setBad(bad);
                bean.setShare(share);
                bean.setSay(say);

                bean.setType("3");
                bean.setContext(context);
                beanList.add(bean);
            }
            break;

        }
    }

    //建立列表试图
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.jxcontent_layout, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupViews(view);
    }

    public void setupViews(View view) {
        listView = (ListView) view.findViewById(R.id.lv_jxContent);

        //下拉刷新的控件
        refresh = (SwipeRefreshLayout) view.findViewById(R.id.refresh);

        refresh.setColorSchemeColors(new int[]{Color.RED, Color.BLUE, Color.YELLOW});
        //下拉刷新监听
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //开启刷新
                refresh.setRefreshing(true);
                //开启动画效果
                ivToTop.startAnimation(anim);
                //刷新完，重新获取数据
                getData(url);
                refresh.setRefreshing(false);
                ivToTop.clearAnimation();
            }
        });


        ivToTop = (ImageView) view.findViewById(R.id.iv_toTop);
        //点击监听，也会出现下拉刷新
        ivToTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //滚动到LIstView的头部
                setListViewPosition(0);
                //同时刷新操作
                refresh.setRefreshing(true);
                //两秒刷新操作,handle去更新adapter
                Handler handle = new Handler();
                handle.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //暂停刷新操作
                        ivToTop.clearAnimation();
                        refresh.setRefreshing(false);
                    }
                }, 2000);
            }
        });

        //动画初始化
        anim = AnimationUtils.loadAnimation(getContext(), R.anim.totop_rotage);

        listView.setAdapter(adapter);

        listView.setOnScrollListener(this);
    }

    //滚动到指定位置
    public void setListViewPosition(int pos) {
        //判断用户的SDK版本等级
        if (Build.VERSION.SDK_INT >= 8) {
            //滚动到LIstView的头部,平滑的滚动到Position
            listView.smoothScrollToPosition(pos);
        } else {
            //版本低是没有上面的方法，所以直接调用选择方法
            listView.setSelection(0);
        }
    }

    private boolean scrollFlag = false;

    //下面两个方法，都是LIstView的滚动监听
    //滚动状态的改变时调用这个方法
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        switch (scrollState) {
            //屏幕停止滚动式
            case AbsListView.OnScrollListener.SCROLL_STATE_IDLE: {
                scrollFlag = false;
                //判断是否滚动到了底部
                if (listView.getLastVisiblePosition() == listView.getCount() - 1) {
                    ivToTop.setVisibility(View.VISIBLE);
                } else if (listView.getFirstVisiblePosition() == 0) {//判断是否滚动到了顶部
                    ivToTop.clearAnimation();
                }
            }
            break;
            //滚动时
            case AbsListView.OnScrollListener.SCROLL_STATE_FLING:
                scrollFlag = true;
                break;
            //是当用户由于之前划动屏幕并抬起手指，屏幕产生惯性滑动时
            case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
                scrollFlag = false;
                break;
        }
    }


    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        //当开始滑动，且ListView底部的Y轴点超出屏幕的最大范围时，显示或隐藏底部按钮
        //获取屏幕中控件底部的高度，即控件底部的Y点
        int viewHeight = ((WindowManager) getActivity()
                .getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getHeight();//获取屏幕高度
        if (!scrollFlag) {
            ivToTop.clearAnimation();
        }
        //当回到头部的时候，进行刷新
        if (firstVisibleItem == 0) {
            refresh.setEnabled(true);
        } else {
            refresh.setEnabled(false);
        }
    }

    //释放媒体播放器
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
    }

    private void showShare(NeiHanBean neiHanBean) {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // title标题，印象笔记、邮箱、信息、微信、人人网、QQ和QQ空间使用
        oks.setTitle("标题");
        // titleUrl是标题的网络链接，仅在Linked-in,QQ和QQ空间使用
        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText(neiHanBean.getName());
        //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
        oks.setImageUrl(neiHanBean.getImgHead());
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite("ShareSDK");
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");

        // 启动分享GUI
        oks.show(getActivity());
    }
}
