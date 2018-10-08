package com.example.dj.viewframework;


import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.dj.viewframework.activity.ViewFrameActivity;
import com.example.dj.viewframework.myinterface.ViewPageQuality;
import com.example.dj.viewframework.test.FirstPage;
import com.example.dj.viewframework.test.SecondPage;
import com.example.dj.viewframework.test.ThirdPage;

import java.util.HashMap;

public class MainActivity extends ViewFrameActivity {

    private static MainActivity mMainActivity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMainActivity = this;
    }

    public static MainActivity getInstance() {
        return mMainActivity;
    }

    @Override
    public void openViewPage(ViewPageQuality viewPage, int viewPageID, HashMap<String, Object> data) {
        super.openViewPage(viewPage,viewPageID, data);
    }

    @Override
    protected ViewPageQuality getViewPage(int viewPageID) {
        ViewPageQuality viewPageQuality = null;
        switch (viewPageID) {
            case FIRST_VIEWPAGE_ID:
                viewPageQuality = new FirstPage(this);
                break;

            case SECOND_VIEWPAGE_ID:
                viewPageQuality = new SecondPage(this);
                break;

            case THIRD_VIEPAGE_ID:
                viewPageQuality = new ThirdPage(this);
        }
        return viewPageQuality;
    }
}
