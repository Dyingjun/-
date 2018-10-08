package com.meitu.flu.labelgroup.labelstyle.labelborder;

/**
 * Created by dj on 2018/8/10.
 */

public class LabelBorder {

    private int mLineColor_UnChecked;

    private int mLineColor_Checked;

    private int mLineWidth_UnChecked;

    private int mLineWidth_Chewcked;

    public LabelBorder() {
        this.mLineColor_UnChecked = 0;
        this.mLineColor_Checked = 0;
        this.mLineWidth_UnChecked = 0;
        this.mLineWidth_Chewcked = 0;
    }

    public int getLineColor_UnChecked() {
        return mLineColor_UnChecked;
    }

    public void setLineColor_UnChecked(int mLineColor_UnChecked) {
        this.mLineColor_UnChecked = mLineColor_UnChecked;
    }

    public int getLineColor_Checked() {
        return mLineColor_Checked;
    }

    public void setLineColor_Checked(int mLineColor_Checked) {
        this.mLineColor_Checked = mLineColor_Checked;
    }

    public int getLineWidth_UnChecked() {
        return mLineWidth_UnChecked;
    }

    public void setLineWidth_UnChecked(int mLineWidth_UnChecked) {
        this.mLineWidth_UnChecked = mLineWidth_UnChecked;
    }

    public int getLineWidth_Chewcked() {
        return mLineWidth_Chewcked;
    }

    public void setLineWidth_Chewcked(int mLineWidth_Chewcked) {
        this.mLineWidth_Chewcked = mLineWidth_Chewcked;
    }
}
