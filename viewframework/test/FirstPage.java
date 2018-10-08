package com.example.dj.viewframework.test;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.dj.viewframework.MainActivity;
import com.example.dj.viewframework.R;
import com.example.dj.viewframework.mvp.presenter.Presenter;
import com.example.dj.viewframework.viewpage.ViewPage;

import java.util.HashMap;

/**
 * Created by dj on 2018/8/27.
 */

public class FirstPage extends ViewPage {

    public FirstPage(Context context) {
        super(context);
    }

    @Override
    protected Presenter getPresenter() {
        return null;
    }

    @Override
    public void initData(HashMap<String, Object> data) {

    }
    @Override
   public ViewPage initView() {
        Button nextBtn = new Button(getContext());
        TextView textView = new TextView(getContext());
        nextBtn.setText("下一个界面");
        final SecondPage secondPage = new SecondPage(getContext());
        nextBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.getInstance().openViewPage(new SecondPage(getContext()),MainActivity.SECOND_VIEWPAGE_ID,null);
            }
        });
        textView.setText("这是第一个界面");
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ImageView img = new ImageView(getContext());
        Matrix matrix =  new Matrix();
        matrix.setScale(0.3f,0.3f);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
         BitmapFactory.decodeResource(getResources(),R.drawable.test_big,options);
        options.inSampleSize = 4;
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        options.inJustDecodeBounds=false;
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.test_big,options);
        bitmap=Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,false);
        img.setImageBitmap(bitmap);
        this.addView(img);
        this.addView(textView,layoutParams);
        this.addView(nextBtn,layoutParams);
        return this;
    }
}