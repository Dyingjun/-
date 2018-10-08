package com.meitu.flu.labelgroup.labelmodel;

import com.meitu.flu.labelgroup.listener.SimpleLabelClickListener;
import com.meitu.flu.labelgroup.labelinfo.LabelInfo;
import com.meitu.flu.labelgroup.labelstyle.LabelStyle;


import java.util.ArrayList;
import java.util.List;


/**
 * Created by dj on 2018/8/6.
 */

public class LabelModel {

    private LabelStyle mLabelStyle;

    private List<LabelInfo> mLabelInfos;

    private SimpleLabelClickListener mLabelListener;

    public SimpleLabelClickListener getLabelListener() {
        return mLabelListener;
    }

    public List<LabelInfo> getLabelInfos() {
        return mLabelInfos;
    }

    public LabelStyle getmLabelStyle() {
        return mLabelStyle;
    }

    public LabelModel(Builder builder) {
        mLabelInfos = builder.mLabelInfos;
        mLabelListener=builder.mLabelListener;
        mLabelStyle=builder.mLabelStyle;
    }

    public static class Builder {

        private LabelStyle mLabelStyle;

        private List<LabelInfo> mLabelInfos;

        private SimpleLabelClickListener mLabelListener;

        public Builder(List<String> titles) {
            mLabelStyle = new LabelStyle();
            mLabelInfos =new ArrayList<>();
            for(int i=0;i<titles.size();i++){
                LabelInfo labelInfo = new LabelInfo(titles.get(i));
                mLabelInfos.add(i,labelInfo);
            }
        }
        public Builder setLabelID(List<String> ids){
            for(int i=0;i<ids.size();i++){
                mLabelInfos.get(i).setID(Integer.parseInt(ids.get(i)));
            }
            return this;
        }

        public Builder setLabelMessage(List<String> messages){
            for(int i=0;i<messages.size();i++){
                mLabelInfos.get(i).setMessage(messages.get(i));
            }
            return this;
        }

        public Builder setLabelInfo(List<LabelInfo> labelInfos){
            mLabelInfos =labelInfos;
            return this;
        }

        public Builder setRadius(float radius) {
            this.mLabelStyle.setRadius(radius);
            return this;
        }

        public Builder setBGColor(int bgColor) {
            this.mLabelStyle.setBGColor(bgColor);
            return this;
        }

        public Builder setTextSize(int textSize) {
            this.mLabelStyle.setTextSize(textSize);
            return this;
        }

        public Builder setTextColor(int textColor) {
            this.mLabelStyle.setTextColor(textColor);
            return this;
        }

        public Builder setMaxWidth(int maxWidth){
            this.mLabelStyle.setMaxWidth(maxWidth);
            return this;
        }

        public Builder setUnCheckLineW(int unCheckLineW) {
            this.mLabelStyle.getLabelBorder().setLineWidth_UnChecked(unCheckLineW);
            return this;
        }

        public Builder setUnCheckLineColor(int unCheckLineColor) {
            this.mLabelStyle.getLabelBorder().setLineColor_UnChecked(unCheckLineColor);
            return this;
        }

        public Builder setCheckLineW(int checkLineW) {
            this.mLabelStyle.getLabelBorder().setLineWidth_Chewcked(checkLineW);
            return this;
        }

        public Builder setCheckLineColor(int checkLineColor) {
            this.mLabelStyle.getLabelBorder().setLineColor_Checked(checkLineColor);
            return this;
        }

        public Builder setLabelBorder(int unCheckLineW, int unCheckLineColor, int checkLineW, int checkLineColor) {
            this.mLabelStyle.getLabelBorder().setLineWidth_UnChecked(unCheckLineW);
            this.mLabelStyle.getLabelBorder().setLineColor_UnChecked(unCheckLineColor);
            this.mLabelStyle.getLabelBorder().setLineWidth_Chewcked(checkLineW);
            this.mLabelStyle.getLabelBorder().setLineColor_Checked(checkLineColor);
            return this;
        }

        public Builder setLabelListener(SimpleLabelClickListener simpleLabelClickListener) {
            this.mLabelListener = simpleLabelClickListener;
            return this;
        }
//
//        public Builder setLabelMargin(int left, int top, int right, int bottom) {
//            LabelMargin labelMargin = new LabelMargin();
//            labelMargin.setLeftMargin(left);
//            labelMargin.setRightMargin(right);
//            labelMargin.setTopMargin(top);
//            labelMargin.setBottomMargin(bottom);
//            this.mLabelStyle.setLabelMargin(labelMargin);
//            return this;
//        }
//
//        public Builder setLabelMargin(int margin) {
//            LabelMargin labelMargin = new LabelMargin();
//            labelMargin.setLeftMargin(margin);
//            labelMargin.setRightMargin(margin);
//            labelMargin.setTopMargin(margin);
//            labelMargin.setBottomMargin(margin);
//            this.mLabelStyle.setLabelMargin(labelMargin);
//            return this;
//        }


        public Builder setLabelHight(int labelHight) {
            this.mLabelStyle.setLabelHight(labelHight);
            return this;
        }

        public LabelModel build() {
            return new LabelModel(this);
        }

        public LabelStyle getLabelStyle() {
            return mLabelStyle;
        }

        public List<LabelInfo> getLabelInfos(){
            return mLabelInfos;
        }
    }
}