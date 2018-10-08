package com.example.dj.autodialog.view.adapter;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.dj.autodialog.R;
import com.example.dj.autodialog.view.listener.FilterViewClickListener;

/**
 * Created by dj on 2018/7/16.
 */

public class FilterView extends LinearLayout{

    private CircleImageView mCircleImageView;

    private TextView mTxetView;

    private FilterViewClickListener mListener;

    private int ID;

    private String mText;

    private String mImgPath;

    private int mImgWidth;

    private int mImgHight;

    private int mTextHight;

    public FilterView(Context context) {
        super(context);
        this.mTxetView = new TextView(context);
        this.mCircleImageView = new CircleImageView(context);
    }

    public FilterView(Context context, AttributeSet attrs) {
        super(context,attrs);
        this.mTxetView = new TextView(context);
        this.mCircleImageView = new CircleImageView(context);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.FilterView);
        mTextHight = typedArray.getInteger(R.styleable.FilterView_textHight,50);
        mImgHight = typedArray.getInteger(R.styleable.FilterView_imageDimension,220);
        mImgWidth = typedArray.getInteger(R.styleable.FilterView_imageDimension,220);
        mText=typedArray.getString(R.styleable.FilterView_text);
        mTxetView.setText(mText);
        mImgPath = typedArray.getString(R.styleable.FilterView_imageSrc);
        setCircleImage(mImgPath);
        typedArray.recycle();
        initFilterView();
    }

    public FilterView(Context context,AttributeSet attrs, int defStyleAttr) {
        super(context,attrs,defStyleAttr);
        this.mTxetView = new TextView(context);
        this.mCircleImageView = new CircleImageView(context);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.FilterView);
        mTextHight = typedArray.getInteger(R.styleable.FilterView_textHight,50);
        mImgHight = typedArray.getInteger(R.styleable.FilterView_imageDimension,220);
        mImgWidth = typedArray.getInteger(R.styleable.FilterView_imageDimension,220);
        mText=typedArray.getString(R.styleable.FilterView_text);
        mTxetView.setText(mText);
        mImgPath = typedArray.getString( R.styleable.FilterView_imageSrc);
        setCircleImage(mImgPath);
        typedArray.recycle();
        initFilterView();
    }

    public void initFilterView(){
        this.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams rImageParams =
                new LinearLayout.LayoutParams(mImgWidth,mImgHight);
        rImageParams.gravity = Gravity.CENTER_HORIZONTAL;
        this.addView(mCircleImageView,rImageParams);
        mTxetView.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams rTextParams =
                new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,mTextHight);
        rTextParams.gravity = Gravity.CENTER;
        this.addView(mTxetView,rTextParams);
        this.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    mListener.onFilterViewClick(ID);
                }
            }
        });
    }

    public void setImgDimension(int dimension) {
        if(dimension!=0){
            this.mImgWidth = dimension;
            this.mImgHight = dimension;
        }else{
            this.mImgWidth = 220;
            this.mImgHight = 220;
        }
    }

    public void setmTextHight(int textHight) {
        if(textHight!=0){
            this.mTextHight = textHight;
        }else{
            this.mTextHight = 50;
        }
    }

    public void setCircleImage(String imagepath){
        mImgPath = imagepath;
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mImgPath, options);
        options.inJustDecodeBounds = false;
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        options.inDither = true;
        Bitmap bitmap = BitmapFactory.decodeFile(mImgPath,options);
        this.mCircleImageView.setImageBitmap(bitmap);
    }

    public void setFilterViewClickListener(FilterViewClickListener listener) {
        this.mListener = listener;
    }

    public void setText(String text){
        this.mTxetView.setText(text);
    }

    public void setID(int id){
        this.ID =  id;
    }
}