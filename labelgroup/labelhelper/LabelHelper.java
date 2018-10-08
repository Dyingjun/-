package com.meitu.flu.labelgroup.labelhelper;

import android.util.Log;

import com.meitu.flu.labelgroup.labelinfo.LabelInfo;
import com.meitu.flu.labelgroup.labelstyle.LabelStyle;
import com.meitu.flu.labelgroup.listener.SimpleLabelClickListener;
import com.meitu.flu.labelgroup.labelmodel.LabelModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dj on 2018/8/6.
 */

public class LabelHelper {

    private LabelModel mModel;

    public LabelHelper(LabelModel.Builder builder) {
        this.mModel = builder.build();
    }

    public static LabelHelper getDefaultBuild(String title) {
        List<String> list = new ArrayList<>();
        list.add(title);
        return new Builder(list)
                .Build();
    }

    public List<LabelInfo> getLabelInfos() {
        return this.mModel.getLabelInfos();
    }

    public LabelStyle getLabelStyle() {
        return this.mModel.getmLabelStyle();
    }

    public SimpleLabelClickListener getLabelClickListener() {
        return this.mModel.getLabelListener();
    }

    static public class Builder {

        private LabelModel.Builder mLabelModelBuilder;

        public Builder(List<String> labelInfos) {
            mLabelModelBuilder = new LabelModel.Builder(labelInfos);
        }

        public Builder setLabelID(List<String> ids) {
            if (ids.size() != mLabelModelBuilder.getLabelInfos().size()) {
                Log.e("LabelHelper", "setLabelID: " + "ID count not equal LabelView count");
            } else {
                for (int i = 0; i < mLabelModelBuilder.getLabelInfos().size(); i++) {
                    mLabelModelBuilder.getLabelInfos().get(i).setID(Integer.parseInt(ids.get(i)));
                }

            }
            return this;
        }

        public Builder setLabelMessage(List<String> messages) {
            if (messages.size() != mLabelModelBuilder.getLabelInfos().size()) {
                Log.e("LabelHelper", "setLabelID: " + "ID count not equal LabelView count");
            } else {
                for (int i = 0; i < mLabelModelBuilder.getLabelInfos().size(); i++) {
                    mLabelModelBuilder.getLabelInfos().get(i).setMessage(messages.get(i));
                }
            }
            return this;
        }

        public Builder setRadius(float radius) {
            if (radius > this.mLabelModelBuilder.getLabelStyle().getLabelHight()) {
                Log.d("LabelHelper", "setRadius: " + "Radius can't bigger than TagHight");
                return this;
            } else {
                this.mLabelModelBuilder.getLabelStyle().setRadius(radius);
                return this;
            }
        }

        public Builder setBGColor(int bgColor) {
            this.mLabelModelBuilder.getLabelStyle().setBGColor(bgColor);
            return this;
        }

        public Builder setTextSize(int textSize) {
            this.mLabelModelBuilder.getLabelStyle().setTextSize(textSize);
            return this;
        }

        public Builder setTextColor(int textColor) {
            this.mLabelModelBuilder.getLabelStyle().setTextColor(textColor);
            return this;
        }

        public Builder setMaxWidth(int maxWidth) {
            if (maxWidth > 0) {
                this.mLabelModelBuilder.setMaxWidth(maxWidth);
            } else {
                Log.e("LabelHelper", "setMaxWidth: " + "maxWidth must > 0");
            }
            return this;
        }

        public Builder setUnCheckLineW(int unCheckLineW) {
            this.mLabelModelBuilder.getLabelStyle().getLabelBorder().setLineWidth_UnChecked(unCheckLineW);
            return this;
        }

        public Builder setUnCheckLineColor(int unCheckLineColor) {
            this.mLabelModelBuilder.getLabelStyle().getLabelBorder().setLineColor_UnChecked(unCheckLineColor);
            return this;
        }

        public Builder setCheckLineW(int checkLineW) {
            this.mLabelModelBuilder.getLabelStyle().getLabelBorder().setLineWidth_Chewcked(checkLineW);
            return this;
        }

        public Builder setCheckLineColor(int checkLineColor) {
            this.mLabelModelBuilder.getLabelStyle().getLabelBorder().setLineColor_Checked(checkLineColor);
            return this;
        }

        public Builder setLabelBorder(int unCheckLineW, int unCheckLineColor, int checkLineW, int checkLineColor) {
            this.mLabelModelBuilder.setLabelBorder(unCheckLineW, unCheckLineColor, checkLineW, checkLineColor);
            return this;
        }

        public Builder setLabelClickListener(SimpleLabelClickListener labelClickListener) {
            this.mLabelModelBuilder.setLabelListener(labelClickListener);
            return this;
        }

//        public Builder setLabelMargin(int left, int top, int right, int bottom) {
//            if (left >= 0 & right >= 0 & top >= 0 & bottom >= 0) {
//                this.mLabelModelBuilder.setLabelMargin(left, top, right, bottom);
//            } else {
//                Log.e("LabelHelper", "setLabelMargin: " + "Margin 必须大于0");
//            }
//            return this;
//        }
//
//        public Builder setLabelMargin(int margin) {
//            if (margin >= 0) {
//                this.mLabelModelBuilder.setLabelMargin(margin);
//            } else {
//                Log.e("LabelHelper", "setLabelMargin: " + "Margin 必须大于0");
//            }
//            return this;
//        }


        public Builder setLabelHight(int labelHight) {
            if (labelHight > 0) {
                this.mLabelModelBuilder.setLabelHight(labelHight);
            } else {
                Log.e("LabelHelper", "setLabelHight: " + "LabelHight 必须大于0");
            }
            return this;
        }

        public LabelHelper Build() {
            return new LabelHelper(this.mLabelModelBuilder);
        }
    }
}
