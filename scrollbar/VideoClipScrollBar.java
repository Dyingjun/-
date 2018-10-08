package com.meitu.flu.scrollbar;

import android.content.Context;
import android.widget.LinearLayout;

import com.meitu.flu.scrollbar.control.ScrollControl;
import com.meitu.flu.scrollbar.loadthumb.LoadThumbCover;
import com.meitu.flu.scrollbar.scrollrecycleview.ScrollRecycleVIew;
import com.meitu.flu.scrollbar.topview.TopView;

/**
 * Created by dj on 2018/8/23.
 */

public class VideoClipScrollBar extends LinearLayout {

    private ScrollRecycleVIew mScrollRecycleVIew;

    private ScrollControl mScrollControl;

    private TopView mTopView;

    private LoadThumbCover mLoadThumbCover;

    public VideoClipScrollBar(Context context) {
        super(context);
        this.setOrientation(VERTICAL);
        mScrollRecycleVIew = new ScrollRecycleVIew(getContext());
        mScrollControl = new ScrollControl(mScrollRecycleVIew);
        mLoadThumbCover = new LoadThumbCover(getContext());
        mScrollRecycleVIew.setScrollAdapter(mLoadThumbCover.getAdapter());
    }

    public void initView(String videoPath,long videoDuration){
        mTopView = new TopView(getContext(),videoDuration);
        mTopView.bindControl(mScrollControl);
        mLoadThumbCover.initLoadThumbCover(videoPath,videoDuration);
        mScrollRecycleVIew.setCallback(mTopView.getScrollCallback());
        new Thread(mLoadThumbCover).start();
        mScrollControl.initControl();
        LinearLayout.LayoutParams lParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lParams.setMargins(0,25,0,0);
        this.addView(mScrollRecycleVIew,lParams);
        lParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lParams.setMargins(0,-145,0,0);
        this.addView(mTopView,lParams);
    }

    public long getStartTime(){
        long startime = mTopView.getStartime();
        return startime;
    }

    public long getEndTime(){
        long endtime = mTopView.getEndtime();
        return endtime;
    }

    public long getDuration(){
        return mTopView.getEndtime()-mTopView.getStartime();
    }
}
