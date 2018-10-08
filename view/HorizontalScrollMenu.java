package com.example.dj.autodialog.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.example.dj.autodialog.R;
import com.example.dj.autodialog.view.adapter.FilterView;
import com.example.dj.autodialog.view.listener.FilterViewClickListener;


/**
 * Created by dj on 2018/7/18.
 */

public class HorizontalScrollMenu extends HorizontalScrollView{

    private Context mContext;

    private FilterViewClickListener mListener;

    private int mLeft =10;

    private int mTop = 0;

    private int mRight = 5;

    private int mBottom = 0;

    private LinearLayout mContainer;


    public HorizontalScrollMenu(Context context,AttributeSet attrs) {
        super(context,attrs);
        this.mContext = context;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.FilterView);
        mLeft = typedArray.getInteger(R.styleable.HorizontalScrollMenu_margins_left,10);
        mRight = typedArray.getInteger(R.styleable.HorizontalScrollMenu_margins_right,5);
        mBottom = typedArray.getInteger(R.styleable.HorizontalScrollMenu_margins_bottom,0);
        mTop = typedArray.getInteger(R.styleable.HorizontalScrollMenu_margins_top,0);
        setMargins(mLeft,mTop,mRight,mBottom);
        typedArray.recycle();
    }

    public HorizontalScrollMenu(Context context){
        super(context);
        this.mContext = context;
    }

    public HorizontalScrollMenu(Context context,AttributeSet attrs, int defStyleAttr) {
        super(context,attrs,defStyleAttr);
        this.mContext = context;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.FilterView);
        mLeft = typedArray.getInteger(R.styleable.HorizontalScrollMenu_margins_left,10);
        mRight = typedArray.getInteger(R.styleable.HorizontalScrollMenu_margins_right,5);
        mBottom = typedArray.getInteger(R.styleable.HorizontalScrollMenu_margins_bottom,0);
        mTop = typedArray.getInteger(R.styleable.HorizontalScrollMenu_margins_top,0);
        setMargins(mLeft,mTop,mRight,mBottom);
        typedArray.recycle();
    }

    public void setFilterViewClickListener(FilterViewClickListener listener) {
        this.mListener = listener;
    }

    public void setContainer(String[] imagepath,String[] title,int imgDimention,int texthight){
        mContainer = new LinearLayout(mContext);
        mContainer.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER;
        params.setMargins(mLeft,mTop,mRight,mBottom);
        for(int i = 0;i <imagepath.length;i++){
            FilterView filterView = new FilterView(mContext);
            filterView.setmTextHight(texthight);
            filterView.setImgDimension(imgDimention);
            filterView.setFilterViewClickListener(mListener);
            filterView.setCircleImage(imagepath[i]);
            filterView.setText(title[i]);
            filterView.setID(i);
            filterView.initFilterView();
            mContainer.addView(filterView,params);
        }

        LinearLayout.LayoutParams rParams =
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        rParams.gravity = Gravity.CENTER_VERTICAL;
        this.addView(mContainer,rParams);
        this.setHorizontalScrollBarEnabled(false);
        this.setOverScrollMode(HorizontalScrollView.OVER_SCROLL_NEVER);

    }

    public void setContainer(String[] imagepath,String[] title,AttributeSet[] attrs,int imgDimention,int texthight){
        mContainer = new LinearLayout(mContext);
        mContainer.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER;
        params.setMargins(mLeft,mTop,mRight,mBottom);
        for(int i = 0;i <imagepath.length;i++){
            FilterView filterView;
            if(attrs!=null){
                filterView = new FilterView(mContext,attrs[i]);
            }else {
                filterView = new FilterView(mContext);
            }
            filterView.setmTextHight(texthight);
            filterView.setImgDimension(imgDimention);
            filterView.setFilterViewClickListener(mListener);
            filterView.setCircleImage(imagepath[i]);
            filterView.setText(title[i]);
            filterView.setID(i);
            filterView.initFilterView();
            mContainer.addView(filterView,params);
        }
        LinearLayout.LayoutParams rParams =
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        rParams.gravity = Gravity.CENTER_VERTICAL;
        this.addView(mContainer,rParams);
        this.setHorizontalScrollBarEnabled(false);
        this.setOverScrollMode(HorizontalScrollView.OVER_SCROLL_NEVER);

    }

    public void setMargins(int left,int top,int right,int bottom){
        this.mLeft = left;
        this.mTop = top;
        this.mRight = right;
        this.mBottom = bottom;
    }
}
