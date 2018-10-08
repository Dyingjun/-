package com.meitu.flu.scrollbar.runnable;

import com.meitu.flu.scrollbar.control.ScrollControl;
import com.meitu.flu.scrollbar.control.basecontrol.BaseControl;
import com.meitu.flu.scrollbar.scrollrecycleview.ScrollRecycleVIew;
import com.meitu.flu.scrollthumbar.Thumbutil.RecycleScrollView;

import java.lang.ref.WeakReference;

/**
 * Created by dj on 2018/8/23.
 */

public class ScrollTask implements Runnable{

    private WeakReference<ScrollRecycleVIew> mReference;

    private int mDirecction;

    public ScrollTask(ScrollRecycleVIew recycleScrollView) {
        this.mReference = new WeakReference<>(recycleScrollView);
    }

    public void setDirecction(int direcction){
        this.mDirecction=direcction;
    }

    @Override
    public void run() {
        mReference.get().scrollBy(mDirecction*10,0);
        mReference.get().post(this);
    }
}
