package com.meitu.flu.process.filter.itemview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.meitu.flu.R;

/**
 * Created by dj on 2018/7/16.
 */

public class FilterItemView extends LinearLayout {

    private CircleImageView mCircleImageView;

    private TextView mTxetView;

    private String mText;

    private String mImgPath;

    private int mImgWidth = 120;

    private int mImgHight = 120;

    private int mTextHight = 50;




    public FilterItemView(Context context) {
        super(context);
        this.mTxetView = new TextView(context);
        this.mCircleImageView = new CircleImageView(context);
    }

    public FilterItemView(Context context, AttributeSet attrs) {
        super(context,attrs);
        this.mTxetView = new TextView(context);
        this.mCircleImageView = new CircleImageView(context);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.FilterView);
        mTextHight = typedArray.getInteger(R.styleable.FilterView_textHight,50);
        mImgHight = typedArray.getInteger(R.styleable.FilterView_imageDimension,120);
        mImgWidth = typedArray.getInteger(R.styleable.FilterView_imageDimension,120);
        mText=typedArray.getString(R.styleable.FilterView_text);
        mTxetView.setText(mText);
        mImgPath = typedArray.getString(R.styleable.FilterView_imageSrc);
        setCircleImage(mImgPath);
        typedArray.recycle();
        initFilterView();
    }

    public FilterItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context,attrs,defStyleAttr);
        this.mTxetView = new TextView(context);
        this.mCircleImageView = new CircleImageView(context);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.FilterView);
        mTextHight = typedArray.getInteger(R.styleable.FilterView_textHight,50);
        mImgHight = typedArray.getInteger(R.styleable.FilterView_imageDimension,120);
        mImgWidth = typedArray.getInteger(R.styleable.FilterView_imageDimension,120);
        mText=typedArray.getString(R.styleable.FilterView_text);
        mTxetView.setText(mText);
        mImgPath = typedArray.getString( R.styleable.FilterView_imageSrc);
        setCircleImage(mImgPath);
        typedArray.recycle();
        initFilterView();
    }

    public void initFilterView(){
        ViewGroup viewGroup = (ViewGroup)mCircleImageView.getParent();
        if(viewGroup!=null){
            viewGroup.removeAllViews();
        }
        this.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams rImageParams =
                new LinearLayout.LayoutParams(mImgWidth,mImgHight);
        rImageParams.gravity = Gravity.CENTER_HORIZONTAL;
        this.addView(mCircleImageView,rImageParams);
        mTxetView.setGravity(Gravity.CENTER);
        mTxetView.setTextColor(0xffffffff);
        LinearLayout.LayoutParams rTextParams =
                new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,mTextHight);
        rTextParams.gravity = Gravity.BOTTOM;
        rTextParams.setMargins(0,10,0,0);
        this.addView(mTxetView, rTextParams);
    }

    public void setImgDimension(int dimension) {
        if(dimension!=0){
            this.mImgWidth = dimension;
            this.mImgHight = dimension;
        }
    }

    public void setmTextHight(int textHight) {
        if(textHight!=0){
            this.mTextHight = textHight;
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
        Bitmap bitmap = BitmapFactory.decodeFile(null,options);
        this.mCircleImageView.setImageBitmap(bitmap);
    }

    public void setText(String text){
        this.mTxetView.setText(text);
    }
}