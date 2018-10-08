package com.example.dj.autodialog.build;

import android.content.Context;
import android.app.Dialog;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by dj on 2018/7/10.
 */

public class BuildDialog extends Dialog{

    private BtnClick mBtnClick;

    private View mView;

    private Context mContext;

    private String mMessage;

    private String mTitle;

    private String mRightBtnName;

    private String mLeftBtnName;

    private boolean mNeedLeftBtn;

    private boolean mNeedRightBtn;

    public BuildDialog(Context context, Builder builder) {
        super(context);
        this.mTitle = builder.mTitle;
        this.mRightBtnName = builder.mRightBtnName;
        this.mLeftBtnName = builder.mLeftBtnName;
        this.mMessage = builder.mMessage;
        this.mBtnClick = builder.mBtnClick;
        this.mNeedLeftBtn = builder.mNeedLeftBtn;
        this.mNeedRightBtn = builder.mNeedRightBtn;
        this.mContext = context;
        this.mView = builder.mView;
        initDialog();
        this.show();
    }

    private void initDialog(){

        if (mView != null) {
            this.requestWindowFeature(Window.FEATURE_NO_TITLE);
            LinearLayout.LayoutParams rParams =
                    new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            rParams.gravity = Gravity.CENTER;
            this.setContentView(mView,rParams);
        } else {

            this.requestWindowFeature(Window.FEATURE_NO_TITLE);
            LinearLayout container = new LinearLayout(mContext);
            container.setOrientation(LinearLayout.VERTICAL);
            LinearLayout.LayoutParams rParams =
                    new LinearLayout.LayoutParams((int)(mContext.getResources().getDisplayMetrics().widthPixels*0.6),LinearLayout.LayoutParams.WRAP_CONTENT);
            container.setGravity(Gravity.TOP);
//            this.setContentView(container,rParams);
            {
                TextView topTitle = new TextView(mContext);
                topTitle.setGravity(Gravity.CENTER);
                topTitle.setLines(1);
                topTitle.setText(mTitle);
                LinearLayout.LayoutParams rTopTitleParams =
                        new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 80);
                container.addView(topTitle, rTopTitleParams);


//                CircleImageView circleImageView = new CircleImageView(mContext);
//                circleImageView.setImageResource(R.mipmap.meitu222);
//
                TextView centerTitle = new TextView(mContext);
                centerTitle.setText(mMessage);
                centerTitle.setLines(3);

                LinearLayout.LayoutParams rCenterTitleParams =
                        new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 150);

                //rCenterTitleParams.graity = Gravity.CENTER;
                centerTitle.setGravity(Gravity.CENTER);
//                container.addView(circleImageView, rCenterTitleParams);
                container.addView(centerTitle, rCenterTitleParams);

                LinearLayout btnlayout = new LinearLayout(mContext);
                btnlayout.setOrientation(LinearLayout.HORIZONTAL);
                LinearLayout.LayoutParams rBtnParams =
                        new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 80);
                {
                    Button leftBtn = new Button(mContext);
                    leftBtn.setText(mLeftBtnName);
                    leftBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mBtnClick.leftBtnCallBack();
                        }
                    });
                    if (mNeedLeftBtn) {
                        leftBtn.setVisibility(View.VISIBLE);
                    } else {
                        leftBtn.setVisibility(View.INVISIBLE);
                    }

                    LinearLayout.LayoutParams rLeftParams =
                            new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT);
                    leftBtn.setGravity(Gravity.CENTER);
                    btnlayout.addView(leftBtn, rLeftParams);

                    Button rightBtn = new Button(mContext);
                    rightBtn.setText(mRightBtnName);
                    rightBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mBtnClick.rightBtnCallBack();
                        }
                    });
                    if (mNeedRightBtn) {
                        rightBtn.setVisibility(View.VISIBLE);
                    } else {
                        rightBtn.setVisibility(View.INVISIBLE);
                    }
                    LinearLayout.LayoutParams rRightParams =
                            new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT);
                    rRightParams.gravity = Gravity.CENTER_VERTICAL;
                    rightBtn.setGravity(Gravity.CENTER);
                    btnlayout.addView(rightBtn, rRightParams);

                    if (mNeedRightBtn && mNeedLeftBtn) {
                        rRightParams.weight = 1;
                        rLeftParams.weight = 1;
                    } else if (mNeedRightBtn && !mNeedLeftBtn) {
                        rRightParams.weight = 1;
                        rLeftParams.weight = 0;
                    } else if (!mNeedRightBtn && mNeedLeftBtn) {
                        rLeftParams.weight = 1;
                    }
                }
                container.addView(btnlayout, rBtnParams);
            }
        }
    }


    public static class Builder{

        private BtnClick mBtnClick;

        private View mView;

        private Context mContext;

        private String mMessage;

        private String mTitle;

        private String mRightBtnName;

        private String mLeftBtnName;

        private boolean mNeedLeftBtn;

        private boolean mNeedRightBtn;


        public Builder() {
            this.mNeedRightBtn = true;
            this.mNeedLeftBtn = true;
        }

        public Builder setTitle(String title) {
            this.mTitle=title;
            return this;
        }

        public Builder setMessage(String message) {
            this.mMessage=message;
            return this;
        }

        public Builder setLeftBtnName(String leftBtnName) {
            this.mLeftBtnName = leftBtnName;
            return this;
        }

        public Builder setRightBtnName(String  rightBtnName) {
            this.mRightBtnName =rightBtnName;
            return this;
        }

        public Builder setBtnClick(BtnClick btnClick) {
            this.mBtnClick = btnClick;
            return this;
        }

        public Builder setNeedLeftBtn(boolean flag){
            this.mNeedLeftBtn = flag;
            return this;
        }

        public Builder setNeedRightBtn(boolean flag){
            this.mNeedRightBtn = flag;
            return this;
        }

        public Builder setView(View View) {
            this.mView = View;
            return this;
        }

        public BuildDialog build(Context context){
            this.mContext = context;
            return new BuildDialog(this.mContext,this);
        }
    }
}
