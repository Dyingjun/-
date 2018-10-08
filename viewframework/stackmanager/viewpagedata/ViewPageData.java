package com.example.dj.viewframework.stackmanager.viewpagedata;

import java.util.HashMap;
import java.util.List;

/**
 * Created by dj on 2018/8/27.
 */

public class ViewPageData {

    private List<Integer> mHasOpenedPageID;

    private HashMap<String,HashMap<String,Object>> mViewPageDatas;

    public ViewPageData() {

    }

    public void bindViewPageData(List<Integer> viewPageIDs, HashMap<String,HashMap<String,Object>> datas){
        this.mHasOpenedPageID=viewPageIDs;
        this.mViewPageDatas=datas;
    }

    public List<Integer> getHasOpenedPageIDs() {
        return mHasOpenedPageID;
    }

    public HashMap<String, HashMap<String, Object>> getViewPageDatas() {
        return mViewPageDatas;
    }
}
