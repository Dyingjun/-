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
 * Created by dj on 2018/8/29.
 */

public class ThirdPage extends ViewPage{

    TextView textView;


    public ThirdPage(Context context) {
        super(context);
        textView= new TextView(getContext());
    }

    @Override
    public void initData(HashMap<String, Object> data) {
        String data1 = (String) data.get("abc");
        textView.setText(data1);
    }

    @Override
    public ViewPage initView() {
        Button btn = new Button(getContext());
        btn.setText("上一页");
        btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.getInstance().viewPageBack();
            }
        });
        LinearLayout.LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ImageView img = new ImageView(getContext());
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.test_big);
        Matrix matrix = new Matrix();
        matrix.setScale(0.1f,0.1f);
        bitmap = Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,false);
        img.setImageBitmap(bitmap);
        this.addView(img);
        this.addView(btn,layoutParams);
        this.addView(textView,layoutParams);
        return this;
    }

    @Override
    protected Presenter getPresenter() {
        return null;
    }

}
