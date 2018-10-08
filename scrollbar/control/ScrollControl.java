package com.meitu.flu.scrollbar.control;

import com.meitu.flu.scrollbar.control.basecontrol.BaseControl;
import com.meitu.flu.scrollbar.runnable.ScrollTask;
import com.meitu.flu.scrollthumbar.Thumbutil.RecycleScrollView;

/**
 * Created by dj on 2018/8/23.
 */

public class ScrollControl implements BaseControl{

    private BaseControl mBaseControl;

    public ScrollControl(BaseControl baseControl) {
        this.mBaseControl = baseControl;
    }

    @Override
    public void initControl(){
        if(mBaseControl!=null){
            mBaseControl.initControl();
        }
    }

    @Override
    public void scrollStar(int direction) {
        if(mBaseControl!=null){
            mBaseControl.scrollStar(direction);
        }
    }

    @Override
    public void scrollStop() {
        if(mBaseControl!=null){
            mBaseControl.scrollStop();
        }
    }
}
