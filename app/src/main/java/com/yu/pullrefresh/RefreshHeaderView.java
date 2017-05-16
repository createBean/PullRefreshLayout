package com.yu.pullrefresh;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrUIHandler;
import in.srain.cube.views.ptr.indicator.PtrIndicator;

/**
 * Created by yu on 17-5-16.
 */

public class RefreshHeaderView extends FrameLayout implements PtrUIHandler{

    private ImageView imageView;
    private AnimationDrawable animationDrawable;

    public RefreshHeaderView(@NonNull Context context) {
        this(context,null);
    }

    public RefreshHeaderView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public RefreshHeaderView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        View view = inflate(getContext(), R.layout.new_refresh_header_view,this);
        imageView = (ImageView) view.findViewById(R.id.imageview_car);

    }



    @Override
    public void onUIReset(PtrFrameLayout frame) {
        resetView();
    }

    private void resetView() {
        if (animationDrawable!=null){
            animationDrawable.stop();
        }
        imageView.setVisibility(VISIBLE);
    }

    @Override
    public void onUIRefreshPrepare(PtrFrameLayout frame) {
        imageView.setImageResource(R.drawable.new_refresh_view);
        animationDrawable = (AnimationDrawable) imageView.getDrawable();
    }

    @Override
    public void onUIRefreshBegin(PtrFrameLayout frame) {

    }

    @Override
    public void onUIRefreshComplete(final PtrFrameLayout frame) {
        if (animationDrawable.isRunning()){
            imageView.clearAnimation();
            animationDrawable.stop();
            animationDrawable = null;
        }
    }

    public View getCarView(){
        return imageView;
    }

    @Override
    public void onUIPositionChange(PtrFrameLayout frame, boolean isUnderTouch, byte status, PtrIndicator ptrIndicator) {
        final int mOffsetToRefresh = frame.getOffsetToRefresh();
        final int currentPos = ptrIndicator.getCurrentPosY();
        final int lastPos = ptrIndicator.getLastPosY();

        if (currentPos < mOffsetToRefresh && lastPos >= mOffsetToRefresh) {
            if (isUnderTouch && status == PtrFrameLayout.PTR_STATUS_PREPARE) {
                if(animationDrawable.isRunning()){
                    animationDrawable.stop();
                }
            }
        } else if (currentPos > mOffsetToRefresh && lastPos <= mOffsetToRefresh) {
            if (isUnderTouch && status == PtrFrameLayout.PTR_STATUS_PREPARE) {
                if (!animationDrawable.isRunning()){
                    animationDrawable.start();
                }
            }
        }
    }
}
