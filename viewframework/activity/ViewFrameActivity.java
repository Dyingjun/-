package com.example.dj.viewframework.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.example.dj.viewframework.myinterface.ViewPageQuality;
import com.example.dj.viewframework.stackmanager.StackManager;
import com.example.dj.viewframework.stackmanager.viewpagedata.ViewPageData;
import com.example.dj.viewframework.test.FirstPage;
import com.example.dj.viewframework.viewpage.ViewPage;
import com.example.dj.viewframework.viewpage.ViewPageID;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * Created by dj on 2018/8/27.
 */

public abstract class ViewFrameActivity extends AppCompatActivity implements ViewPageID {

    private static final String STACK_KEY = "ViewFrame_Key";

    private List<Integer> hasOpenedPageID = new ArrayList<>();

    private HashMap<String, HashMap<String, Object>> hasOpenedPageData = new HashMap<>();

    private HashMap<String,ViewPageQuality> hasOpenedPage = new HashMap<>();

    private int mViewPageID;

    private ViewPage mViewPage;

    private FrameLayout mContainer;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        String key = UUID.randomUUID().toString();
        StackManager.getInstance().saveViewPageData(key, hasOpenedPageID, hasOpenedPageData);
        outState.putString(STACK_KEY, key);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null && savedInstanceState.containsKey(STACK_KEY)) {
            String key = savedInstanceState.getString(STACK_KEY);
            ViewPageData viewPageDatas = StackManager.getInstance().getViewPageData(key);
            if (viewPageDatas.getHasOpenedPageIDs() != null) {
                hasOpenedPageID = viewPageDatas.getHasOpenedPageIDs();
            }
            if (viewPageDatas.getViewPageDatas() != null) {
                hasOpenedPageData = viewPageDatas.getViewPageDatas();
            }
        }
        openViewPage(hasOpenedPage.get(hasOpenedPageID.get(getNowOpenViewPageID())),getNowOpenViewPageID(),hasOpenedPageData.get(hasOpenedPageID.get(getNowOpenViewPageID())));
//        openViewPage(hasOpenedPageID.get(getNowOpenViewPageID()), hasOpenedPageData.get(hasOpenedPageID.get(getNowOpenViewPageID())));
        System.gc();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContainer = new FrameLayout(this);
        setContentView(mContainer,getParams());
//        openViewPage(FIRST_VIEWPAGE_ID, null);
        openViewPage(new FirstPage(this),FIRST_VIEWPAGE_ID,null);
    }

    private int getNowOpenViewPageID() {
        return hasOpenedPageID.size() > 0 ? hasOpenedPageID.get(hasOpenedPageID.size() - 1) : -1;
    }

    private int getNextOpenViewPageID() {
        return hasOpenedPageID.size() >= 2 ? hasOpenedPageID.get(hasOpenedPageID.size() - 2) : -1;
    }

    private void removeStackTopID() {
        hasOpenedPageID.remove(hasOpenedPageID.size() - 1);
    }

    private void removeViewPageData(String ViewPageID){
        if(hasOpenedPageData.containsKey(ViewPageID)){
            hasOpenedPageData.remove(ViewPageID);
        }
    }

    private void addToIDStack(int ViewPageID) {
        if (hasOpenedPageID != null) {
            hasOpenedPageID.add(ViewPageID);
        }
    }

    private void addToViewPageData(String ViewPageID, HashMap<String, Object> data) {
        if (hasOpenedPageData != null) {
            if(data!=null){
                hasOpenedPageData.put(ViewPageID, data);
            }
        }
    }

    public void openViewPage(ViewPageQuality viewPage,int viewPageID, HashMap<String, Object> data){
        openViewPage(viewPage,viewPageID, data, true);
    }

    private void addToViewPage(int viewPageID,ViewPageQuality viewPageQuality){
        if(hasOpenedPage!=null){
            hasOpenedPage.put(String.valueOf(viewPageID),viewPageQuality);
        }
    }

    public void openViewPage(ViewPageQuality viewPage,int viewPageID, HashMap<String, Object> data,boolean isStack){
        viewPage.onCreat();
        ViewPage viewPage1 = viewPage.initView();
        if (data != null) {
            viewPage1.initData(data);
        }
        if (mViewPage != null) {
            mViewPage.onStop();
            mViewPage.setVisibility(View.GONE);
            if(hasOpenedPageID.contains(mViewPageID)){
                addToViewPageData(mViewPageID+"data",mViewPage.savePageData());
            }
            mViewPage.removeAllViews();
        }
        mContainer.removeView(mViewPage);
        if (viewPage1 != null && viewPage1 instanceof ViewPage) {
            mContainer.addView(viewPage1, getParams());
//            setContentView(viewPage1, getParams());
            mViewPage = viewPage1;
            mViewPageID = viewPageID;
        }
        if (isStack) {
            addToIDStack(viewPageID);
            addToViewPageData(String.valueOf(viewPageID), data);
            addToViewPage(viewPageID,viewPage);
        }
    }

    public void viewPageBack() {
        if (!mViewPage.canBack()) {
            return;
        }
        int backViewPageID = getNextOpenViewPageID();
        if (backViewPageID == -1) {
            this.finish();
            return;
        }
//        ViewPageQuality viewPageQuality = getViewPage(backViewPageID);
        if(!hasOpenedPage.containsKey(String.valueOf(backViewPageID))){
            Log.e("onBackViewPage", "viewPageBack: "+ "last page isn't exist");
            return;
        }
        ViewPageQuality viewPageQuality = hasOpenedPage.get(String.valueOf(backViewPageID));
        ViewPage viewPage = viewPageQuality.initView();
        viewPage.setVisibility(View.VISIBLE);
        if (hasOpenedPageData.containsKey(String.valueOf(backViewPageID))) {
            HashMap<String, Object> initData = hasOpenedPageData.get(String.valueOf(backViewPageID));
            viewPage.initData(initData);
        }
        if(hasOpenedPageData.containsKey(backViewPageID+"data")){
            HashMap<String, Object> saveData = hasOpenedPageData.get(backViewPageID+"data");
            viewPage.initSaveData(saveData);
        }
        HashMap<String, Object> backData = mViewPage.setBackData();
        if (backData != null) {
            viewPage.initBackData(mViewPageID, backData);
        }
        mViewPage.setVisibility(View.GONE);
        mViewPage.onDestroy();
        mContainer.removeView(mViewPage);
        mContainer.addView(viewPage, getParams());
//        setContentView(viewPage, getParams());
        viewPage.onBack();
        removeViewPageData(String.valueOf(mViewPageID));
        removeViewPageData(mViewPageID+"data");
        removeStackTopID();
        mViewPageID = backViewPageID;
        mViewPage = viewPage;
    }

//    public void openViewPage(int viewPageID, HashMap<String, Object> data) {
//        openViewPage(viewPageID, data, true);
//    }
//
//    public void openViewPage(int viewPageID,HashMap<String, Object> data, boolean isStack) {
//        ViewPageQuality viewPageQuality = getViewPage(viewPageID);
//        viewPageQuality.onCreat();
//        ViewPage viewPage = viewPageQuality.initView();
//        if (data != null) {
//            viewPage.initData(data);
//        }
//        if (mViewPage != null) {
//            mViewPage.onStop();
//            mViewPage.setVisibility(View.GONE);
//            if (!isStack) {
//                mViewPage.onDestroy();
//            }
//        }
//        if(hasOpenedPageID.contains(mViewPageID)){
//            addToViewPageData(mViewPageID+"data",mViewPage.savePageData());
//        }
//        if (viewPage != null && viewPage instanceof ViewPage) {
//            setContentView(viewPage, getParams());
//            mViewPage = viewPage;
//            mViewPageID = viewPageID;
//        }
//        if (isStack) {
//            addToIDStack(viewPageID);
//            addToViewPageData(String.valueOf(viewPageID), data);
//        }
//    }

//    public void viewPageBack() {
//        if (!mViewPage.canBack()) {
//            return;
//        }
//        int backViewPageID = getNextOpenViewPageID();
//        if (backViewPageID == -1) {
//            this.finish();
//            return;
//        }
//        ViewPageQuality viewPageQuality = getViewPage(backViewPageID);
//        ViewPage viewPage = viewPageQuality.initView();
//        if (hasOpenedPageData.containsKey(String.valueOf(backViewPageID))) {
//            HashMap<String, Object> initData = hasOpenedPageData.get(String.valueOf(backViewPageID));
//            viewPage.initData(initData);
//        }
//        if(hasOpenedPageData.containsKey(backViewPageID+"data")){
//            HashMap<String, Object> saveData = hasOpenedPageData.get(backViewPageID+"data");
//            viewPage.initSaveData(saveData);
//        }
//        HashMap<String, Object> backData = mViewPage.setBackData();
//        if (backData != null) {
//            viewPage.initBackData(mViewPageID, backData);
//        }
//        mViewPage.setVisibility(View.GONE);
//        mViewPage.onDestroy();
//        setContentView(viewPage, getParams());
//        viewPage.onBack();
//        if (hasOpenedPageData.containsKey(mViewPageID)) {
//            hasOpenedPageData.remove(mViewPageID);
//        }
//        mViewPageID = backViewPageID;
//        mViewPage = viewPage;
//        removeStackTopID();
//    }

    private LinearLayout.LayoutParams getParams() {
        LinearLayout.LayoutParams lParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        return lParams;
    }

    protected abstract ViewPageQuality getViewPage(int viewPageID);

    @Override
    public void onBackPressed() {
        viewPageBack();
    }
}
