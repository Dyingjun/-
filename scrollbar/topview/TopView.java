package com.meitu.flu.scrollbar.topview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.meitu.flu.R;
import com.meitu.flu.partlyeditor.utils.ConverTimeUtil;
import com.meitu.flu.screen.ScreenUtils;
import com.meitu.flu.scrollbar.callback.ScrollCallback;
import com.meitu.flu.scrollbar.control.basecontrol.BaseControl;
import com.meitu.flu.scrollthumbar.borderview.BorderView;
import com.meitu.flu.scrollthumbar.callback.CurRecyeleViewPositionCallback;
import com.meitu.flu.scrollthumbar.callback.SlideListener;

/**
 * Created by dj on 2018/8/23.
 */

public class TopView extends View {

    private BaseControl mBaseControl;

    private final int TOUCH_LEFT_BITMAP = 0xff000001;

    private final int TOUCH_RIGHT_BITMAP = 0xff000002;

    private final int TOUCH_MID_VIEW = 0xff000003;

    private final int TOUCH_BOTH_SIDE = 0xff00004;

    private Paint mShadowPaint;

    private Paint mTextPaint;

    private Paint mBitmapPaint;

    private int mBitmapW;

    private float mLeftX;

    private float mRightX;

    private int mTouchTaget;

    private float mRightMarin;

    private TextView mStartTimeView;

    private TextView mEndTimeView;

    private long mStartTime = 0;

    private long mEndTime = 15000;

    private Bitmap mLeftBitmap;

    private Bitmap mRightBitmap;

    private float mTouchPointX;

    private float mSlidePointX;

    private int mViewH;

    private float mRecycleViewX;

    private int mRecyclerViewLength;

    private long mViedoDuration;

    private ScrollCallback mScrollCallback;


    public TopView(Context context, long videoDuration) {
        super(context);
        init(videoDuration);
    }

    public void bindControl(BaseControl baseControl) {
        this.mBaseControl = baseControl;
    }

    private void init(long videoDuration) {
        initPaint();
        initView(videoDuration);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(mLeftBitmap, mLeftX - mBitmapW / 3, 1, mBitmapPaint);
        canvas.drawBitmap(mRightBitmap, mRightX - 2 * mBitmapW / 3, 24, mBitmapPaint);
        canvas.drawText(mStartTimeView.getText().toString(), mLeftX + 2 * mBitmapW / 3 + 5, 20, mTextPaint);
        canvas.drawText(mEndTimeView.getText().toString(), mRightX - 3 * mBitmapW - 2 * mBitmapW / 3, mRightBitmap.getHeight() + 19, mTextPaint);
        canvas.drawRect(0, 25, mLeftX, mLeftBitmap.getHeight(), mShadowPaint);
        canvas.drawRect(mRightX, 25, mRightMarin, mLeftBitmap.getHeight(), mShadowPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(ScreenUtils.getScreenW(), mViewH);
    }

    private void initPaint() {
        mBitmapPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mShadowPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mShadowPaint.setColor(0X5f000000);
        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(0xff000000);
        mTextPaint.setTextSize(20);
    }

    private void initView(long videoDuration) {
        this.mViedoDuration = videoDuration;
        this.mRecyclerViewLength = (int) Math.abs(videoDuration / 5000) * 210;
        mStartTimeView = new TextView(getContext());
        mEndTimeView = new TextView(getContext());
        mLeftBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_slide_left_view);
        mRightBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_slide_right_view);
        Matrix matrix = new Matrix();
        matrix.setScale(0.4f, 0.271f);
        mLeftBitmap = Bitmap.createBitmap(mLeftBitmap, 0, 0, mLeftBitmap.getWidth(), mLeftBitmap.getHeight(), matrix, false);
        mRightBitmap = Bitmap.createBitmap(mRightBitmap, 0, 0, mRightBitmap.getWidth(), mRightBitmap.getHeight(), matrix, false);
        mBitmapW = mLeftBitmap.getWidth();
        mViewH = mLeftBitmap.getHeight() + 27;
        mRightMarin = ScreenUtils.getScreenW();
        mLeftX = 0;
        mRightX = (15000f / mViedoDuration) * mRecyclerViewLength + 2 * mBitmapW / 3;
        mStartTime = (long) (((mRecycleViewX + mLeftX) / mRecyclerViewLength) * mViedoDuration);
        mEndTime = (long) (((mRecycleViewX + mRightX) / mRecyclerViewLength) * mViedoDuration);
        mStartTimeView.setText(ConverTimeUtil.getFormatTime(String.valueOf(mStartTime)));
        mEndTimeView.setText(ConverTimeUtil.getFormatTime(String.valueOf(mEndTime)));
        mScrollCallback = new ScrollCallback() {
            @Override
            public void ScrollLeftPoint(int leftPoint) {
                mRecycleViewX = leftPoint;
                mStartTime = (long) (((mRecycleViewX + mLeftX) / mRecyclerViewLength) * mViedoDuration);
                mEndTime = (long) (((mRecycleViewX + mRightX) / mRecyclerViewLength) * mViedoDuration);
                mStartTimeView.setText(ConverTimeUtil.getFormatTime(String.valueOf(mStartTime)));
                mEndTimeView.setText(ConverTimeUtil.getFormatTime(String.valueOf(mEndTime)));
                postInvalidate();
            }
        };
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float xMove = event.getX();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (xMove >= mLeftX - mBitmapW && xMove <= mLeftX + 4 * mBitmapW / 3) {
                    mTouchTaget = TOUCH_LEFT_BITMAP;
                } else if (xMove >= mRightX - 4 * mBitmapW / 3 && xMove <= mRightX + mBitmapW) {
                    mTouchTaget = TOUCH_RIGHT_BITMAP;
                } else if (xMove >= mLeftX + 4 * mBitmapW / 3 && xMove <= mRightX - 4 * mBitmapW / 3) {
                    mTouchTaget = TOUCH_MID_VIEW;
                    mTouchPointX = xMove;
                    mSlidePointX = xMove;
                } else {
                    mTouchTaget = TOUCH_BOTH_SIDE;
                    mTouchPointX = xMove;
                    mSlidePointX = xMove;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                mSlidePointX = xMove;
                controlScroll(true);
                onCountCurX(xMove);
                break;
            case MotionEvent.ACTION_UP:
                controlScroll(false);
                mTouchPointX = -1;
                mTouchTaget = -1;
                break;
            case MotionEvent.ACTION_OUTSIDE:
                mBaseControl.scrollStop();
                mTouchPointX = -1;
                mTouchTaget = -1;
                mSlidePointX = 0;
                break;

        }
        return true;
    }

    private void onCountCurX(float movex) {
        if (mTouchTaget == TOUCH_LEFT_BITMAP) {
            if (movex >= mRightX - 2 * mBitmapW / 3) {
                if (movex <= mRightMarin - 2 * mBitmapW / 3) {
                    mLeftX = movex;
                    mRightX = movex + 2 * mBitmapW / 3;
                } else {
                    mLeftX = mRightMarin - 2 * mBitmapW / 3;
                    mRightX = mRightMarin;
                }
            } else if ((mRightX - movex) / mRecyclerViewLength * mViedoDuration > 15000) {
                mLeftX = mRightX - (15000f / mViedoDuration) * mRecyclerViewLength - 2 * mBitmapW / 3;
            } else {
                mLeftX = movex;
            }
        } else if (mTouchTaget == TOUCH_RIGHT_BITMAP) {
            if (movex >= mRightMarin) {
                mRightX = mRightMarin;
            } else if (movex < mLeftX + 2 * mBitmapW / 3) {
                if (movex > 2 * mBitmapW / 3) {
                    mRightX = movex;
                    mLeftX = movex - 2 * mBitmapW / 3;
                } else {
                    mLeftX = 0;
                    mRightX = 2 * mBitmapW / 3;
                }
            } else if ((movex - mLeftX) / mRecyclerViewLength * mViedoDuration > 15000) {
                mRightX = mLeftX + (15000f / mViedoDuration) * mRecyclerViewLength + 2 * mBitmapW / 3;
            } else {
                mRightX = movex;
            }
        } else if (mTouchTaget == TOUCH_MID_VIEW) {
            float longth = mRightX - mLeftX;
            if (movex > mTouchPointX) {
                if (mRightX + movex - mTouchPointX >= mRightMarin) {
                    mRightX = mRightMarin;
                    mLeftX = mRightX - longth;
                } else {
                    mLeftX = mLeftX + movex - mTouchPointX;
                    mRightX = mRightX + movex - mTouchPointX;
                }
            } else if (movex < mTouchPointX) {
                if (mLeftX - mTouchPointX + movex <= 0) {
                    mLeftX = 0;
                    mRightX = mLeftX + longth;
                } else {
                    mLeftX = mLeftX + movex - mTouchPointX;
                    mRightX = mRightX + movex - mTouchPointX;
                }
            }
            mTouchPointX = movex;
        }
        mStartTime = (long) (((mRecycleViewX + mLeftX) / mRecyclerViewLength) * mViedoDuration);
        mEndTime = (long) (((mRecycleViewX + mRightX) / mRecyclerViewLength) * mViedoDuration);
        mStartTimeView.setText(ConverTimeUtil.getFormatTime(String.valueOf(mStartTime)));
        mEndTimeView.setText(ConverTimeUtil.getFormatTime(String.valueOf(mEndTime)));
        invalidate();
    }

    private void controlScroll(boolean isNeedScroll) {
        int direction = 0;
        if (!isNeedScroll) {
            mBaseControl.scrollStop();
            return;
        }

        if (mTouchTaget == TOUCH_MID_VIEW) {
            if (mSlidePointX > mTouchPointX) {
                direction = 1;
            } else if (mTouchPointX > mSlidePointX) {
                direction = -1;
            }
            if (mLeftX != 0 && mRightX != mRightMarin) {
                return;
            }
            if (direction == 1 && mRightX == mRightMarin) {
                mBaseControl.scrollStar(direction);
            } else if (direction == -1 && mLeftX == 0) {
                mBaseControl.scrollStar(direction);
            } else {
                mBaseControl.scrollStop();
            }
        } else if (mTouchTaget == TOUCH_BOTH_SIDE) {
            if (mSlidePointX > mTouchPointX) {
                direction = 1;
            } else if (mTouchPointX > mSlidePointX) {
                direction = -1;
            }
            mBaseControl.scrollStar(direction);
        }
        invalidate();
    }

    public long getStartime() {
        return mStartTime;
    }

    public long getEndtime() {
        return mEndTime;
    }

    public ScrollCallback getScrollCallback() {
        return mScrollCallback;
    }
}
