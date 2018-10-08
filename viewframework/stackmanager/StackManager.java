package com.example.dj.viewframework.stackmanager;

import com.example.dj.viewframework.stackmanager.viewpagedata.ViewPageData;

import java.util.HashMap;
import java.util.List;

/**
 * Created by dj on 2018/8/27.
 */

public class StackManager {

    private static StackManager mStackManager;

    private String mKey;

    private ViewPageData mViewPageDatas;

    public StackManager() {
        mViewPageDatas=new ViewPageData();
    }

    public static StackManager getInstance(){
        if(mStackManager==null){
            mStackManager = new StackManager();
        }
        return mStackManager;
    }

    public void saveViewPageData(String key,List<Integer> hasOpenedPageID ,HashMap<String,HashMap<String,Object>> datas){
        this.mKey=key;
        mViewPageDatas.bindViewPageData(hasOpenedPageID,datas);
    }

    public ViewPageData getViewPageData(String key){
        if(key==mKey){
            return mViewPageDatas;
        }
        return null;
    }
}
