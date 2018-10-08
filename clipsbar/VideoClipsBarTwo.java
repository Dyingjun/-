package com.meitu.flu.partlyeditor.widgets;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.LruCache;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import static android.content.ContentValues.TAG;

import com.meitu.flu.R;
import com.meitu.flu.partlyeditor.utils.ConverTimeUtil;
import com.meitu.flu.process.callback.HandleCallBack;
import com.meitu.flu.process.lrucache.ThumLruCache;
import com.meitu.flu.screen.ScreenUtils;
import com.meitu.flu.videopicker.info.ClipInfo;
import com.meitu.media.tools.editor.MTMVVideoEditor;
import com.meitu.media.tools.editor.MVEditorTool;
import com.meitu.media.tools.editor.VideoEditorFactory;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by dj on 2018/7/26.
 */

public class VideoClipsBarTwo extends View {

    private static final int TOUCH_LEFTVIEW_ID = 0Xff100001;

    private static final int TOUCH_MIDVIEW_ID = 0Xff100002;

    private static final int TOUCH_RIGHTVIEW_ID = 0Xff100003;

    private static final int WHAT_SEEK_ID = 0Xff200001;

    private String mVideoPath;

    private long mVideoDuration;

    private float mLeftX, mRightX;

    private Bitmap mLeftBitmap, mRightBitmap;

    private int mSlideBitmapWidth;

    private int mThumbPicHight;

    private int mThumbPicWidth;

    private int mTouchView;

    private List<Bitmap> mCoverBitmap;

    private LruCache<String, Bitmap> mThumbCache;

    private Paint mPaint;

    private Paint mThumbPaint;

    private Paint mTextPaint;

    private TextView mStartTime, mEndTime;

    private Handler mHandler;

    private Handler mControlVideoHandle;

    private final int mThumbCount = 6;

    private float mTouchX;

    private float mRightMarin;

    private ClipInfo mClipInfo;

    private HandleCallBack mHandleCallBack;


    public VideoClipsBarTwo(Context context) {
        super(context);
        initVIew();
        initHandle();
    }

    public VideoClipsBarTwo(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initVIew();
        initHandle();
    }

    public VideoClipsBarTwo(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initVIew();
        initHandle();
    }

    public VideoClipsBarTwo(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initVIew();
        initHandle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mCoverBitmap != null && mCoverBitmap.size() > 0) {
            for (int i = 0; i < mCoverBitmap.size(); i++) {
                if (mCoverBitmap.get(i) != null) {
                    canvas.drawBitmap(mCoverBitmap.get(i), i * mThumbPicWidth, 45, mThumbPaint);
                }
            }
        }
        canvas.drawBitmap(mLeftBitmap, mLeftX - mSlideBitmapWidth / 3, 1, mThumbPaint);
        canvas.drawBitmap(mRightBitmap, mRightX - 2 * mSlideBitmapWidth / 3, 44, mThumbPaint);
        mStartTime.setText(ConverTimeUtil.getFormatTime(String.valueOf(getStartTime())));
        mEndTime.setText(ConverTimeUtil.getFormatTime(String.valueOf(getEndTime())));
        canvas.drawText(mStartTime.getText().toString(), mLeftX + 2 * mSlideBitmapWidth / 3 + 5, 35, mTextPaint);
        canvas.drawText(mEndTime.getText().toString(), mRightX - 4 * mSlideBitmapWidth - 2 * mSlideBitmapWidth / 3, mRightBitmap.getHeight() + 35, mTextPaint);
        canvas.drawRect(0, 44, mLeftX, mThumbPicHight + 45, mPaint);
        canvas.drawRect(mRightX, 45, mRightMarin, mThumbPicHight + 45, mPaint);
    }

    private void initHandle() {
        mHandler = new Handler();
    }


    public void recycleBitmap() {
        if (mCoverBitmap != null) {
            for (int i = 0, c = mCoverBitmap.size(); i < c; i++) {
                Bitmap bitmap = mCoverBitmap.get(i);
                if (bitmap != null) {
                    if (bitmap != null && !bitmap.isRecycled()) {
                        try {
                            bitmap.recycle();
                        } catch (Exception e) {
                        }
                    }
                    bitmap = null;
                }
            }
        }
    }

    public void setVideoUri(String videoPath, long videoDuration) {
        this.mVideoPath = videoPath;
        this.mVideoDuration = videoDuration;
        this.mThumbPicHight = mLeftBitmap.getHeight() - 45;
        this.mThumbPicWidth = ScreenUtils.px1080dip(ScreenUtils.getScreenW()) / mThumbCount;
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                if (!TextUtils.isEmpty(mVideoPath) && mThumbPicHight != 0 & mThumbPicWidth != 0 & mVideoDuration != 0l) {
                    getThumb();
                }
            }
        });
        thread.start();
        invalidate();
    }

    private void initPain() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setARGB(150, 0, 0, 0);
        mThumbPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setARGB(255, 255, 255, 255);
        mTextPaint.setTextSize(40);
    }

    private void initVIew() {
        mLeftX = 0;
        mRightX = ScreenUtils.px1080dip(ScreenUtils.getScreenW());
        Matrix matrix = new Matrix();
        matrix.postScale(0.5f, 0.5f);
        mLeftBitmap = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.ic_slide_left_view);
        mRightBitmap = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.ic_slide_right_view);
        mLeftBitmap = Bitmap.createBitmap(mLeftBitmap, 0, 0, mLeftBitmap.getWidth(), mLeftBitmap.getHeight(), matrix, true);
        mRightBitmap = Bitmap.createBitmap(mRightBitmap, 0, 0, mRightBitmap.getWidth(), mRightBitmap.getHeight(), matrix, true);
        mSlideBitmapWidth = mLeftBitmap.getWidth();
        mStartTime = new TextView(getContext());
        mEndTime = new TextView(getContext());
        mRightMarin = ScreenUtils.px1080dip(ScreenUtils.getScreenW());
        mThumbCache = ThumLruCache.getInstance(getContext());
        mClipInfo = new ClipInfo();
        initPain();
    }


    private Bitmap getFrameThumbAtpos(String videoPath, long pos, int thumbWidth, int thumbHight) {
        Bitmap bmpResult = null;
        long AtTime = pos*1000;
        Object sGetFrameThumbLockObject = new Object();
        synchronized (sGetFrameThumbLockObject) {
            Log.d(TAG, "getFrameThumbAtPos Get sGetFrameThumbLockObject lock");
            MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
            try {
                mediaMetadataRetriever.setDataSource(videoPath);
                Bitmap temp = mediaMetadataRetriever.getFrameAtTime(AtTime, MediaMetadataRetriever.OPTION_CLOSEST_SYNC);
                Log.d(TAG, "getFrameThumbAtPos temp:" + temp);
                bmpResult = temp.copy(Bitmap.Config.ARGB_8888, true);
                float scaleWidth = 1;
                float scaleHight = 1;
                if(bmpResult.getWidth()/thumbWidth==1&&bmpResult.getHeight()/thumbHight==1){
                    return bmpResult;
                }
                scaleHight =(float) thumbHight/bmpResult.getHeight();
                scaleWidth = (float)thumbWidth/bmpResult.getWidth();
                Matrix matrix = new Matrix();
                matrix.setScale(scaleWidth,scaleHight);
                bmpResult = Bitmap.createBitmap(bmpResult,0,0,bmpResult.getWidth(),bmpResult.getHeight(),matrix,true);
                if (!temp.isRecycled()) {
                    temp.recycle();
                }
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            } finally {
                mediaMetadataRetriever.release();
            }
        }
        Log.d(TAG, "getFrameThumbAtPos result bitmap:" + bmpResult);
        return bmpResult;
    }

    ;

    private void getThumb() {
        Bitmap bitmap = null;
        mCoverBitmap = new ArrayList<>(mThumbCount);
        for (int i = 0; i < mThumbCount; i++) {
            mCoverBitmap.add(bitmap);
        }
        Long perDuration = (mVideoDuration - 100) / (long) mThumbCount;
        float oneStart = Float.valueOf(String.valueOf(perDuration));
        List<String> list = new ArrayList<>();
        for (int i = 0; i < mThumbCount; i++) {
            if (mThumbCache != null & mThumbCache.get(mVideoPath + i) != null) {
                mCoverBitmap.remove(i);
                mCoverBitmap.add(i, mThumbCache.get(mVideoPath + i));
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        VideoClipsBarTwo.this.invalidate();
                    }
                });
            } else {
                list.add(String.valueOf(i));
            }
        }

        for (int i = 0; i < list.size(); i++) {
            if (mVideoPath != null) {
                bitmap = getFrameThumbAtpos(mVideoPath, (long) (1 + (Integer.parseInt(list.get(i)) * oneStart)), mThumbPicWidth, mThumbPicHight);
            }
            if (bitmap != null) {
                mCoverBitmap.remove(Integer.parseInt(list.get(i)));
                mCoverBitmap.add(Integer.parseInt(list.get(i)), bitmap);
                mThumbCache.put(mVideoPath + Integer.parseInt(list.get(i)), bitmap);
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        VideoClipsBarTwo.this.invalidate();
                    }
                });
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float xMove = event.getX();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (xMove >= mLeftX - mSlideBitmapWidth / 3 && xMove <= mLeftX + 2 * mSlideBitmapWidth / 3) {
                    mTouchView = TOUCH_LEFTVIEW_ID;
                    countX(xMove);
                } else if (xMove >= mRightX - 2 * mSlideBitmapWidth / 3 && xMove <= mRightX + mSlideBitmapWidth / 3) {
                    mTouchView = TOUCH_RIGHTVIEW_ID;
                    countX(xMove);
                } else if (xMove >= mLeftX + 2 * mSlideBitmapWidth / 3 && xMove <= mRightX - 2 * mSlideBitmapWidth / 3) {
                    mTouchView = TOUCH_MIDVIEW_ID;
                    mTouchX = xMove;
                    countX(xMove);
                } else {
                    mTouchX = -1;
                    mTouchView = -1;
                }
            case MotionEvent.ACTION_MOVE:
                countX(xMove);
                saveSeekInfo();
                handleMessgae();
                break;
            case MotionEvent.ACTION_UP:
                mTouchX = -1;
                mTouchView = -1;
        }
        return true;
    }

    private void countX(float xmove) {
        if (mTouchView == TOUCH_LEFTVIEW_ID) {
            if (xmove >= mRightX - 2 * mSlideBitmapWidth / 3) {
                if (xmove <= mRightMarin - 2 * mSlideBitmapWidth / 3) {
                    mLeftX = xmove;
                    mRightX = xmove + 2 * mSlideBitmapWidth / 3;
                } else {
                    mLeftX = mRightMarin - 2 * mSlideBitmapWidth / 3;
                    mRightX = mRightMarin;
                }
            } else {
                mLeftX = xmove;
            }
        } else if (mTouchView == TOUCH_RIGHTVIEW_ID) {
            if (xmove >= mRightMarin) {
                mRightX = mRightMarin;
            } else if (xmove < mLeftX + 2 * mSlideBitmapWidth / 3) {
                if (xmove > 2 * mSlideBitmapWidth / 3) {
                    mRightX = xmove;
                    mLeftX = xmove - 2 * mSlideBitmapWidth / 3;
                } else {
                    mLeftX = 0;
                    mRightX = 2 * mSlideBitmapWidth / 3;
                }
            } else {
                mRightX = xmove;
            }
        } else if (mTouchView == TOUCH_MIDVIEW_ID) {
            float longth = mRightX - mLeftX;
            if (xmove > mTouchX) {
                if (mRightX + xmove - mTouchX >= mRightMarin) {
                    mRightX = mRightMarin;
                    mLeftX = mRightX - longth;
                } else {
                    mLeftX = mLeftX + xmove - mTouchX;
                    mRightX = mRightX + xmove - mTouchX;
                }
            } else if (xmove < mTouchX) {
                if (mLeftX - mTouchX + xmove <= 0) {
                    mLeftX = 0;
                    mRightX = mLeftX + longth;
                } else {
                    mLeftX = mLeftX + xmove - mTouchX;
                    mRightX = mRightX + xmove - mTouchX;
                }
            }
            mTouchX = xmove;
        } else {
            return;
        }
        invalidate();
    }

    public long getStartTime() {
        long startTime;
        startTime = (long) (mLeftX / mRightMarin * mVideoDuration);
        return startTime;
    }

    public long getEndTime() {
        long endTime;
        endTime = (long) (mRightX / mRightMarin * mVideoDuration);
        return endTime;
    }

    public long getDuration() {
        long duration;
        duration = getEndTime() - getStartTime();
        return duration;
    }

    private void handleMessgae() {
        Message msg = Message.obtain();
        msg.what = WHAT_SEEK_ID;
        msg.obj = mClipInfo;
        mControlVideoHandle.sendMessage(msg);
        mControlVideoHandle.post(new Runnable() {
            @Override
            public void run() {
                mHandleCallBack.callBack();
            }
        });
    }

    private void saveSeekInfo() {
        mClipInfo.setStartTime(getStartTime());
        mClipInfo.setEndTime(getEndTime());
        mClipInfo.setDuration(getDuration());
    }

    public void setHandler(Handler handler) {
        mControlVideoHandle = handler;
    }

    public void setCallback(HandleCallBack handleCallBack) {
        this.mHandleCallBack = handleCallBack;
    }

}
