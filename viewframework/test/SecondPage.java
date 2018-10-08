package com.example.dj.viewframework.test;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.dj.viewframework.MainActivity;
import com.example.dj.viewframework.R;
import com.example.dj.viewframework.mvp.presenter.Presenter;
import com.example.dj.viewframework.viewpage.ViewPage;

import java.util.HashMap;

import static com.example.dj.viewframework.viewpage.ViewPageID.THIRD_VIEPAGE_ID;

/**
 * Created by dj on 2018/8/28.
 */

public class SecondPage extends ViewPage {

    public SecondPage(Context context) {
        super(context);
    }

    @Override
    public void initData(HashMap<String, Object> data) {

    }

    @Override
    public ViewPage initView() {
        Button btn = new Button(getContext());
        Button bbtn = new Button(getContext());
        bbtn.setText("上一页");
        bbtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.getInstance().viewPageBack();
            }
        });
        btn.setText("我觉得可以");
        final HashMap<String,Object> data = new HashMap<>();
        data.put("abc","abc");
        btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
//                MainActivity.getInstance().openViewPage(THIRD_VIEPAGE_ID,data);
                openViewPage(new ThirdPage(getContext()),THIRD_VIEPAGE_ID,data);
            }
        });
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ImageView img = new ImageView(getContext());
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.test_big);
        bitmap = Bitmap.createBitmap(bitmap,0,0,400,300);
        img.setImageBitmap(bitmap);
        this.addView(img);
        this.addView(btn,layoutParams);
        this.addView(bbtn,layoutParams);
        return this;
    }

    @Override
    protected Presenter getPresenter() {
        return null;
    }
}
