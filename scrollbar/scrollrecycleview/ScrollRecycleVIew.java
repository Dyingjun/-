package com.meitu.flu.scrollbar.scrollrecycleview;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.meitu.flu.scrollbar.adapter.ScrollAdapter;
import com.meitu.flu.scrollbar.callback.ScrollCallback;
import com.meitu.flu.scrollbar.control.basecontrol.BaseControl;
import com.meitu.flu.scrollbar.runnable.ScrollTask;

/**
 * Created by dj on 2018/8/23.
 */

public class ScrollRecycleVIew extends RecyclerView implements BaseControl {

    private ScrollAdapter mAdapter;

    private ScrollTask mScrollTask;

    private boolean isScrolled=false;

    private ScrollCallback mScrollCallback;

    public ScrollRecycleVIew(@NonNull Context context) {
        super(context);
        mScrollTask = new ScrollTask(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        linearLayoutManager.setSmoothScrollbarEnabled(true);
        linearLayoutManager.setRecycleChildrenOnDetach(true);
        this.setLayoutManager(linearLayoutManager);
        this.setHasFixedSize(true);
        this.setOverScrollMode(View.OVER_SCROLL_NEVER);
    }

    @Override
    public void initControl() {
        this.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                    mAdapter.setScrolled(true);
                } else if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    mAdapter.setScrolled(false);
                    mAdapter.notifyDataSetChanged();
                } else if (newState == RecyclerView.SCROLL_STATE_SETTLING) {
                    mAdapter.setScrolled(true);
                }
            }
        });
        this.setAdapter(mAdapter);
    }

    @Override
    public void scrollStar(int direction) {
        if (isScrolled) {
            return;
        }
        mScrollTask.setDirecction(direction);
        this.post(mScrollTask);
        mScrollCallback.ScrollLeftPoint(getVisibilityLeft());
        isScrolled=true;
    }

    @Override
    public void scrollStop() {
        if(!isScrolled){
            return;
        }
        mScrollCallback.ScrollLeftPoint(getVisibilityLeft());
        this.removeCallbacks(mScrollTask);
        isScrolled=false;
    }

    public int getVisibilityLeft() {
        int visibleItemPosition = ((LinearLayoutManager) this.getLayoutManager()).findFirstVisibleItemPosition();
        Rect rect = new Rect();
        if (this.getLayoutManager().getChildAt(0) != null) {
            this.getChildAt(0).getGlobalVisibleRect(rect);
        }
        int leftScreenX = 210 - rect.width() + visibleItemPosition * 210;
        return leftScreenX;
    }

    public void setScrollAdapter(ScrollAdapter scrollAdapter) {
        this.mAdapter = scrollAdapter;
    }

    public void setCallback(ScrollCallback scrollCallback){
        this.mScrollCallback=scrollCallback;
    }
}
