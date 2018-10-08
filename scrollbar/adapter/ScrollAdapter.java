package com.meitu.flu.scrollbar.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import java.util.List;

/**
 * Created by dj on 2018/8/23.
 */

public class ScrollAdapter extends RecyclerView.Adapter<ScrollAdapter.ViewHolder> {

    private List<Bitmap> mThumbCover;

    private Context mContext;

    private boolean isScrolled;

    private int mItemCount;

    private int mTag;

    public ScrollAdapter(Context mContext) {
        this.mContext = mContext;
        mTag=0;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ImageView imageView = new ImageView(mContext);
        return new ScrollAdapter.ViewHolder(imageView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.mImageView.setMinimumWidth(210);
        viewHolder.mImageView.setMinimumHeight(118);
        if (!isScrolled && mThumbCover != null && mThumbCover.size() > i && mThumbCover.get(i) != null) {
            viewHolder.mImageView.setImageBitmap(mThumbCover.get(i));
        }
    }

//    @NonNull
//    @Override
//    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
//        ImageView imageView = new ImageView(mContext);
//        imageView.setTag(mTag);
//        mTag++;
//        Log.d("onCreat", "onCreateViewHolder: "+mTag);
//        return new ScrollAdapter.ViewHolder(imageView);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
//        viewHolder.mImageView.setMinimumWidth(210);
//        viewHolder.mImageView.setMinimumHeight(118);
//        if (!isScrolled && mThumbCover != null && mThumbCover.size() > Integer.parseInt(String.valueOf(viewHolder.mImageView.getTag())) && mThumbCover.get(Integer.parseInt(String.valueOf(viewHolder.mImageView.getTag()))) != null) {
//            viewHolder.mImageView.setImageBitmap(mThumbCover.get(Integer.parseInt(String.valueOf(viewHolder.mImageView.getTag()))));
//            Integer.parseInt(String.valueOf(viewHolder.mImageView.getTag()));
//            Log.d("onBindViewHolder", "onBindViewHolder: "+Integer.parseInt(String.valueOf(viewHolder.mImageView.getTag())));
//        }
//    }

    @Override
    public int getItemCount() {
        Log.d("Iem", "getItemCount: "+mItemCount);
        return mItemCount;
    }

    public void setScrolled(boolean isScrolled) {
        this.isScrolled = isScrolled;
    }

    public void setThumbCover(List<Bitmap> bitmaps) {
        this.mThumbCover = bitmaps;
    }

    public void setItemCount(int itemCount) {
        this.mItemCount = itemCount;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView mImageView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView;
        }
    }
}
