package com.example.dj.autodialog.exception;

import android.app.Application;
import android.os.Environment;

import com.example.dj.autodialog.MainActivity;

/**
 * Created by dj on 2018/7/12.
 */

public class MyActivity  extends Application {
    CatchExceptionUtil catchExceptionUtil;
    public void onCreate() {
        super.onCreate();
//        catchExceptionUtil = CatchExceptionUtil.getInstance(getApplicationContext());
//        catchExceptionUtil.setcrashPath(Environment.getExternalStorageDirectory().getAbsolutePath()+"/aaa/");
    }
}