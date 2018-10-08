package com.meitu.flu.labelgroup.labelstyle;

import com.meitu.flu.labelgroup.labelstyle.labelborder.LabelBorder;

/**
 * Created by dj on 2018/8/6.
 */

public class LabelStyle {

    private int mBGColor;

    private float mRadius;

    private int mTextSize;

    private int mTextColor;

    private int mLabelHight;

    private int mMaxWidth;

    private LabelBorder mLabelBorder;

    public LabelStyle() {
        this.mBGColor = 0;
        this.mRadius = -1;
        this.mTextSize = 0;
        this.mTextColor = 0;
        this.mLabelHight = 0;
        mMaxWidth =0;
        mLabelBorder =new LabelBorder();
    }

    public int getLabelHight() {
        return mLabelHight;
    }

    public void setLabelHight(int mTagHight) {
        this.mLabelHight = mTagHight;
    }

    public float getRadius() {
        return mRadius;
    }

    public void setRadius(float mRadius) {
        this.mRadius = mRadius;
    }

    public int getTextSize() {
        return mTextSize;
    }

    public void setTextSize(int mTextSize) {
        this.mTextSize = mTextSize;
    }

    public int getTextColor() {
        return mTextColor;
    }

    public void setTextColor(int mTextColor) {
        this.mTextColor = mTextColor;
    }

    public int getBGColor() {
        return mBGColor;
    }

    public void setBGColor(int bgColor) {
        this.mBGColor = bgColor;
    }

    public LabelBorder getLabelBorder() {
        return mLabelBorder;
    }

    public void setLabelBorder(LabelBorder labelBorder) {
        this.mLabelBorder = labelBorder;
    }

    public int getMaxWidth() {
        return mMaxWidth;
    }

    public void setMaxWidth(int mMaxWidth) {
        this.mMaxWidth = mMaxWidth;
    }
}
