package com.meitu.flu.labelgroup;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.meitu.flu.labelgroup.labelhelper.LabelHelper;
import com.meitu.flu.labelgroup.labelview.LabelView;
import com.meitu.flu.labelgroup.listener.GetCheckLabelListener;

/**
 * Created by dj on 2018/8/3.
 */

public class LabelGroup extends ViewGroup {

    private String mCheckView;

    private int mGroupW;


    public LabelGroup(Context context) {
        super(context);
        this.setBackgroundColor(Color.BLACK);
    }

    public LabelGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LabelGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public LabelGroup(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (getChildCount() == 0) {
            return;
        }
        int parentWidth = MeasureSpec.getSize(widthMeasureSpec);
        int parentWidthMode = MeasureSpec.getMode(widthMeasureSpec);
        int parentHight = MeasureSpec.getSize(heightMeasureSpec);
        int parentHightMode = MeasureSpec.getMode(heightMeasureSpec);

        int curLineW = 0;
        int curLineH = 0;
        int finalLineW = 0;
        int finalLineH = 0;
        int childCount = getChildCount();
        MarginLayoutParams layoutParams = (MarginLayoutParams) this.getLayoutParams();
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            measureChildWithMargins(childView, widthMeasureSpec, layoutParams.width, heightMeasureSpec, layoutParams.height);
            int childWidth = childView.getMeasuredWidth() + layoutParams.leftMargin + layoutParams.rightMargin;
            int childHight = childView.getMeasuredHeight() + layoutParams.topMargin + layoutParams.bottomMargin;
            if (curLineW + childWidth <= parentWidth - getPaddingLeft() - getPaddingRight()) {
                curLineW += childWidth;
                curLineH = Math.max(childHight, curLineH);
                if (i == childCount - 1) {
                    finalLineW = Math.max(curLineW, finalLineW);
                    finalLineH += curLineH;
                }
            } else {
                finalLineW = Math.max(finalLineW, curLineW);
                curLineW = childWidth;
                finalLineH += curLineH;
                curLineH = childHight;
                if (i == childCount - 1) {
                    finalLineW = Math.max(curLineW, finalLineW);
                    finalLineH += curLineH;
                }
            }
        }
        finalLineH = finalLineH + getPaddingBottom() + getPaddingTop();
        finalLineW = finalLineW + getPaddingLeft() + getPaddingRight();
        mGroupW = parentWidthMode == MeasureSpec.EXACTLY ? parentWidth : finalLineW;
        setMeasuredDimension(parentWidthMode == MeasureSpec.EXACTLY ? parentWidth : finalLineW, parentHightMode == MeasureSpec.EXACTLY ? parentHight : finalLineH);
    }

    @Override
    protected void onLayout(boolean change, int l, int t, int r, int b) {
        int viewleft = 0;
        int viewright = 0;
        int viewbottom = 0;
        int viewtop = 0;
        int childCount = getChildCount();
        MarginLayoutParams layoutParams = (MarginLayoutParams) this.getLayoutParams();
        int curTop = getPaddingTop();
        int curLeft =getPaddingLeft();
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            viewleft = curLeft + layoutParams.leftMargin;
            viewtop = curTop + layoutParams.topMargin;
            viewbottom = viewtop + childView.getMeasuredHeight();
            viewright = viewleft + childView.getMeasuredWidth();
            if(viewright>mGroupW-getPaddingRight()){
                curTop = curTop+childView.getMeasuredHeight()+layoutParams.bottomMargin+layoutParams.topMargin;
                viewleft=getPaddingLeft()+layoutParams.leftMargin;
                viewtop = curTop+layoutParams.topMargin;
                viewbottom = viewtop + childView.getMeasuredHeight();
                viewright = viewleft + childView.getMeasuredWidth();
            }
            childView.layout(viewleft, viewtop, viewright, viewbottom);
            curLeft = viewright + layoutParams.rightMargin;
        }
    }

    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return super.generateLayoutParams(p);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return super.generateLayoutParams(attrs);
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return super.generateDefaultLayoutParams();
    }

    public void initLabelGroup(LabelHelper labelHelper) {
        LabelView labelView;
        LinearLayout.LayoutParams lParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        for (int i = 0; i < labelHelper.getLabelInfos().size(); i++) {
            labelView = new LabelView(getContext());
            labelView.initView(labelHelper.getLabelInfos().get(i), labelHelper.getLabelStyle());
            labelView.setLabelID(i);
            labelView.setCheckView(new GetCheckLabelListener() {
                @Override
                public void getCheckView(int labelID) {
                    super.getCheckView(labelID);
                    if (mCheckView == null) {
                        mCheckView = String.valueOf(labelID);
                    } else {
                        LabelView labelView = (LabelView) getChildAt(Integer.parseInt(mCheckView));
                        if (labelID != Integer.parseInt(mCheckView)) {
                            labelView.setChecked(false);
                        }
                        mCheckView = String.valueOf(labelID);
                    }

                }
            });
            this.addView(labelView, lParams);
        }
    }
}
