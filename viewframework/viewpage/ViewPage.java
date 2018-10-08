package com.example.dj.viewframework.viewpage;

import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import com.example.dj.viewframework.MainActivity;
import com.example.dj.viewframework.mvp.presenter.Presenter;
import com.example.dj.viewframework.myinterface.ViewPageQuality;

import java.util.HashMap;

/**
 * Created by dj on 2018/8/27.
 */

public abstract class ViewPage<P extends Presenter,V extends ViewPage>extends LinearLayout implements ViewPageQuality{

    protected P mPresenter;

    public ViewPage(Context context) {
        super(context);
        this.setOrientation(VERTICAL);
        mPresenter = getPresenter();
    }

    @Override
    public HashMap<String, Object> savePageData() {
        return null;
    }

    @Override
    public void initSaveData(HashMap<String, Object> savaData) {

    }

    @Override
    public void onCreat() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onBack() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void initBackData(int ViewPageID, HashMap<String, Object> data) {

    }

    @Override
    public HashMap<String, Object> setBackData() {
        return null;
    }

    protected abstract P getPresenter();

    @Override
    public boolean canBack() {
        return true;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float startX,endX;
        startX=event.getX(MotionEvent.ACTION_DOWN);
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.d("startX", "onSlideBack: "+startX);
            case MotionEvent.ACTION_UP:
//                Log.d("up", "onTouchEvent: "+"~~~~~~~~~~!!!!!!!~~~~~~~~~~");
                endX = event.getX();
//                onSlideBack(startX,endX);
                Log.d("up", "onTouchEvent: "+"~~~~~~~~~~!!!!!!!~~~~~~~~~~"+endX);
                break;
        }
        return true;
    }

    private boolean onSlideBack(float startX,float endX){

        if(startX<=15){
            if(endX-startX>=250){
                Log.d("back", "onSlideBack: "+true+"      "+endX);
                MainActivity.getInstance().viewPageBack();
                return true;
            }
            Log.d("startX", "onSlideBack: "+startX);
            return true;
        }
        return true;
    }

   final protected void openViewPage(V s,int viewPageID,HashMap<String,Object> data){
        MainActivity.getInstance().openViewPage(s,viewPageID,data);
   }

   final protected void viewPageBack(){
       MainActivity.getInstance().viewPageBack();
   }
}
