package com.meitu.flu.process.filter.itemview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by dj on 2018/7/16.
 */

public class CircleImageView extends ImageView {

    private Paint mPaint;

    private Matrix mMatrix;

    private int mWidth;

    private int mRadius;

    private BitmapShader mBitmapShader;

    public CircleImageView(Context context) {
        super(context);
        mMatrix = new Matrix();
        this.mPaint = new Paint();
        mPaint.setAntiAlias(true);
    }

    public CircleImageView(Context context, AttributeSet attrs) {
        super(context,attrs);
        mMatrix = new Matrix();
        this.mPaint = new Paint();
        mPaint.setAntiAlias(true);
    }

    @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            mWidth = Math.min(2*getMeasuredWidth(), 2*getMeasuredHeight());
            mRadius = mWidth/2-10;
            setMeasuredDimension(mWidth, mWidth);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        Drawable mDrawable = getDrawable();
        mMatrix = getMatrix();
        if (mDrawable == null) {
            return;
        }

        Bitmap bitmap =((BitmapDrawable) this.getDrawable()).getBitmap();
        if (bitmap==null){
            return;
        }
        mBitmapShader = new BitmapShader(bitmap, TileMode.CLAMP, TileMode.CLAMP);
        float scale = 1.0f;
        int bSize = Math.min(bitmap.getHeight(),bitmap.getWidth());
        scale = mWidth * 1.0f/bSize;
        mMatrix.postScale(scale,scale);
        mMatrix.postTranslate(-((bitmap.getWidth()*scale-mWidth)/2)/scale,-((bitmap.getHeight()*scale-mWidth)/2)/scale);

        mBitmapShader.setLocalMatrix(mMatrix);
        mPaint.setShader(mBitmapShader);
        canvas.drawCircle(getMeasuredWidth()/2, getMeasuredHeight()/2, mRadius, mPaint);
    }


    private Bitmap drawableToBitamp(Drawable drawable) {
        if (drawable == null) {
            return null;
        }

        Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        if (mMatrix != null) {
            canvas.concat(mMatrix);
        }
        drawable.draw(canvas);
        return bitmap;
    }

}
