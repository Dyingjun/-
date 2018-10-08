package com.example.dj.viewframework.mvp.presenter;

import com.example.dj.viewframework.mvp.model.BaseModel;
import com.example.dj.viewframework.mvp.view.BaseView;

/**
 * Created by dj on 2018/8/27.
 */

public abstract class Presenter implements BasePresenter {

    private BaseView mMvpView;

    private BaseModel mBaseModel;

    public Presenter(BaseView mvpView) {
        this.mMvpView = mvpView;
    }

    public void getModel(BaseModel baseModel) {
        this.mBaseModel = baseModel;
    }
}
