package com.yu.pullrefresh;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.PtrUIHandlerHook;
import in.srain.cube.views.ptr.util.PtrLocalDisplay;

/**
 * Created by yu on 17-5-16.
 */

public class RefreshFrameLayout extends PtrFrameLayout{

    public RefreshFrameLayout(Context context) {
        this(context,null);
    }

    public RefreshFrameLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public RefreshFrameLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        init();
    }

    private void init() {
        setResistance(1.7f);
        setRatioOfHeaderHeightToRefresh(1.1f);
        setDurationToClose(300);
        setDurationToCloseHeader(800); //头部回弹时间
        setKeepHeaderWhenRefresh(true);
        setPullToRefresh(false);

        final RefreshHeaderView header = new RefreshHeaderView(getContext());
        header.setPadding(0, PtrLocalDisplay.dp2px(15),0,0);

        setHeaderView(header);

        addPtrUIHandler(header);//刷新时，保持内容不动，仅头部下移

        setRefreshCompleteHook(new PtrUIHandlerHook() {
            @Override
            public void run() {
                int windowWidth = DisplayUtil.getWindowWidth((Activity) getContext());
                int carViewX = (int) header.getCarView().getX();

                TranslateAnimation animation = new TranslateAnimation(0, windowWidth-carViewX, 0, 0);
                animation.setDuration(660);
                animation.setInterpolator(new DecelerateInterpolator());//小车加速度行驶
                header.getCarView().startAnimation(animation);
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        header.getCarView().setVisibility(View.INVISIBLE);
                        resume();
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
            }
        });

        setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame,content,header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                if (null != handler){
                    handler.refreshStart();
                }
            }
        });
    }

    OnRefreshHandler handler;

    public void setOnRefreshHandler(OnRefreshHandler handler){
        this.handler = handler;
    }
}
