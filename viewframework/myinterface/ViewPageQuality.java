package com.example.dj.viewframework.myinterface;

import com.example.dj.viewframework.viewpage.ViewPage;

import java.util.HashMap;

/**
 * Created by dj on 2018/8/27.
 */

public interface ViewPageQuality {

//    void setNewViewPageData(HashMap<String,Object> data);

    //打开界面需要的数据初始化操作
    void initData(HashMap<String, Object> initData);

    //别的界面传递给该界面的数据的处理
    void initBackData(int ViewPageID, HashMap<String, Object> backData);

    //需要传递给上一个界面的数据
    HashMap<String,Object> setBackData();

    //这个界面需要保存的数据()
    HashMap<String,Object> savePageData();

    void initSaveData(HashMap<String,Object> savaData);

    ViewPage initView();

    void onCreat();

    void onBack();

    void onStop();

    void onDestroy();

    boolean canBack();
}
