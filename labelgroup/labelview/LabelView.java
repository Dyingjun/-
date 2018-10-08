package com.meitu.flu.labelgroup.labelview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Checkable;
import android.widget.TextView;

import com.meitu.flu.labelgroup.labelinfo.LabelInfo;
import com.meitu.flu.labelgroup.labelstyle.LabelStyle;
import com.meitu.flu.labelgroup.listener.GetCheckLabelListener;
import com.meitu.flu.labelgroup.listener.SimpleLabelClickListener;
import com.meitu.flu.screen.ScreenUtils;

/**
 * Created by dj on 2018/8/3.
 */

public class LabelView extends View implements Checkable, View.OnClickListener {

    private Paint mBorderPaint;

    private Paint mCheckPaint;

    private Paint mBGColorPaint;

    private TextPaint mTextPaint;

    private TextView mTextView;

    private RectF mRectF;

    private boolean isChecked;

    private int mLabelWidth;

    private int mLabelHight;

    private float mRadius;

    private int mUnCheckLineW;

    private int mCheckLineW;

    private int mUnCheckLineColor;

    private int mCheckLineColor;

    private int mBGColor;

    private float mTextSize;

    private int mTextColor;

    private LabelStyle mLabelStyle;

    private SimpleLabelClickListener mSimpleLabelClickListener;

    private int mMaxWidth;

    private int mTextWidth;

    private GetCheckLabelListener mGetCheckView;

    private LabelInfo mLabelInfo;

    public LabelView(Context context) {
        super(context);
        mTextView = new TextView(getContext());
        mRectF = new RectF();
        this.setOnClickListener(this);
        Log.d("LabelView", "LabelView: "+"初始化");

    }

    public LabelView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mTextView = new TextView(getContext());
        mRectF = new RectF();
        this.setOnClickListener(this);
    }

    public LabelView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mTextView = new TextView(getContext());
        mRectF = new RectF();
        this.setOnClickListener(this);
    }

    public LabelView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mTextView = new TextView(getContext());
        mRectF = new RectF();
        this.setOnClickListener(this);
        Log.d("LabelView", "LabelView: "+"初始化");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawRoundRect(mRectF, mRadius, mRadius, mBGColorPaint);
        canvas.drawRoundRect(mRectF, mRadius, mRadius, mBorderPaint);
        if (isChecked) {
            canvas.drawRoundRect(mRectF, mRadius, mRadius, mCheckPaint);
        }
        canvas.drawText(mTextView.getText().toString(),mLabelWidth/2,(float) (mTextSize+2.5),mTextPaint);
        Log.d("LabelView", "onDraw: ");
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (mTextView.getText() == null) {
            return;
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(mLabelWidth, mLabelHight);
        Log.d("LabelView", "onMeasure: "+widthMeasureSpec+"     "+"heightMeasureSpec");
    }

    public void initView(LabelInfo labelInfo, LabelStyle labelStyle) {
        mLabelInfo = labelInfo;
        this.mLabelStyle = labelStyle;
        mTextView.setText(labelInfo.getTitle());
        initData();
        isChecked = false;
        mRectF.set((float) mCheckLineW / 2, (float) mCheckLineW / 2, (float) (mLabelWidth - mCheckLineW / 2), (float) (mLabelHight - mCheckLineW / 2));
        initPaint();
        this.requestLayout();
        Log.d("LabelView", "initView: ");
    }

    private void initPaint() {
        mBorderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBorderPaint.setStyle(Paint.Style.STROKE);
        mBorderPaint.setColor(mUnCheckLineColor);
        mBorderPaint.setStrokeWidth(mUnCheckLineW);
        mCheckPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCheckPaint.setStrokeWidth(mCheckLineW);
        mCheckPaint.setColor(mCheckLineColor);
        mCheckPaint.setStyle(Paint.Style.STROKE);
        mBGColorPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBGColorPaint.setColor(mBGColor);
        mTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setColor(mTextColor);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
    }


    private void initData() {
        mMaxWidth = ScreenUtils.getScreenW();
        if (mLabelStyle != null) {
            if (mLabelStyle.getMaxWidth() != -1) {
                mMaxWidth = mLabelStyle.getMaxWidth();
            } else {
                mMaxWidth = ScreenUtils.getScreenW();
            }

            if (mLabelStyle.getLabelBorder().getLineWidth_Chewcked() != 0) {
                mCheckLineW = mLabelStyle.getLabelBorder().getLineWidth_Chewcked();
            } else {
                mCheckLineW = 5;
            }


            if (mLabelStyle.getLabelBorder().getLineWidth_UnChecked() != 0) {
                mUnCheckLineW = mLabelStyle.getLabelBorder().getLineWidth_UnChecked();
            } else {
                mUnCheckLineW = 3;
            }

            if (mLabelStyle.getRadius() != -1) {
                mRadius = mLabelStyle.getRadius();
            } else {
                mTextPaint = mTextView.getPaint();
                Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
                mLabelHight = (int) (fontMetrics.bottom - fontMetrics.top);
                mRadius = mLabelHight / 2;
            }

            if (mLabelStyle.getLabelHight() != 0) {
                mLabelHight = mLabelStyle.getLabelHight();
                mTextWidth = (int) android.text.Layout.getDesiredWidth(mTextView.getText(), mTextView.getPaint());
                if (mTextWidth > mMaxWidth - 2 * mRadius) {
                    mLabelWidth = mMaxWidth;
                } else {
                    mLabelWidth = (int) (2 * mRadius + mTextWidth);
                }
            } else {
                mTextWidth = (int) android.text.Layout.getDesiredWidth(mTextView.getText(), mTextView.getPaint());
                mTextPaint = mTextView.getPaint();
                Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
                mLabelHight = (int) (fontMetrics.bottom - fontMetrics.top) + 5;
                if (mTextWidth > mMaxWidth - 2 * mRadius) {
                    mLabelWidth = mMaxWidth;
                } else {
                    mLabelWidth = (int) (2 * mRadius + mTextWidth);
                }
            }

            if (mLabelStyle.getBGColor() != 0) {
                mBGColor = mLabelStyle.getBGColor();
            } else {
                mBGColor = 0xffffffff;
            }

            if (mLabelStyle.getTextSize() != 0) {
                mTextSize = mLabelStyle.getTextSize();
            } else {
                mTextSize = mTextView.getTextSize();
            }

            if (mLabelStyle.getTextColor() != 0) {
                mTextColor = mLabelStyle.getTextColor();
            } else {
                mTextColor = 0Xfff3456d;
            }

            if (mLabelStyle.getLabelBorder().getLineColor_Checked() != 0) {
                mCheckLineColor = mLabelStyle.getLabelBorder().getLineColor_Checked();
            } else {
                mCheckLineColor = 0xfff35a4a;
            }

            if (mLabelStyle.getLabelBorder().getLineColor_UnChecked() != 0) {
                mUnCheckLineColor = mLabelStyle.getLabelBorder().getLineColor_UnChecked();
            } else {
                mUnCheckLineColor = 0xfff29c9c;
            }
        } else {
            mMaxWidth = ScreenUtils.getScreenW();
            mUnCheckLineW = 3;
            mCheckLineW = 5;
            mUnCheckLineColor = 0xfff29c9c;
            mCheckLineColor = 0xfff35a4a;
            mTextColor = 0Xfff3456d;
            mBGColor = 0xffffffff;
            mTextPaint = mTextView.getPaint();
            Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
            mTextSize = mTextView.getTextSize();
            mLabelHight = (int) (fontMetrics.bottom - fontMetrics.top);
            mRadius = mLabelHight / 2;
            mTextWidth = (int) android.text.Layout.getDesiredWidth(mTextView.getText(), mTextView.getPaint());
            if (mTextWidth > mMaxWidth - 2 * mRadius) {
                mLabelWidth = mMaxWidth;
                mLabelHight = (int) Math.ceil(mTextWidth / (mMaxWidth - 2 * mRadius)) * mLabelHight;
            } else {
                mLabelWidth = (int) (2 * mRadius + mTextWidth);
            }
            mRadius = mLabelHight / 2;
        }
        invalidate();
    }

    public void setLabelID(int labelID) {
        this.mLabelInfo.setID(labelID);
    }

    public void setCheckView(GetCheckLabelListener getCheckLabel) {
        this.mGetCheckView = getCheckLabel;
    }

    public void setLabelClickListener(SimpleLabelClickListener labelClickListener) {
        this.mSimpleLabelClickListener = labelClickListener;
    }

    @Override
    public void setChecked(boolean checked) {
        if (this.isChecked != checked) {
            this.isChecked = checked;
        }
        invalidate();
    }

    @Override
    public boolean isChecked() {
        return isChecked;

    }

    @Override
    public void toggle() {
        setChecked(!isChecked);
    }

    @Override
    public void onClick(View view) {
        toggle();
        if (mSimpleLabelClickListener != null) {
            mSimpleLabelClickListener.onClick(isChecked, mLabelInfo);
        }
        if (isChecked) {
            if (mGetCheckView != null) {
                mGetCheckView.getCheckView(mLabelInfo.getID());
            }
        }
        Log.d("TagView", "onClick: " + isChecked);
    }
}
