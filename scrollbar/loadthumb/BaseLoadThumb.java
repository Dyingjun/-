package com.meitu.flu.scrollbar.loadthumb;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.util.LruCache;

import com.meitu.flu.process.lrucache.ThumLruCache;
import com.meitu.flu.scrollbar.adapter.ScrollAdapter;
import com.meitu.flu.scrollthumbar.Thumbutil.FrameThumbUtil;
import com.meitu.flu.scrollthumbar.adapter.ThumbAdapter;
import com.meitu.flu.scrollthumbar.scrollbarinterface.LoadThumbInterface;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dj on 2018/8/21.
 */

public class BaseLoadThumb implements LoadThumbInterface{

    private List<Bitmap> mThumbCover;

    private LruCache<String,Bitmap> mThumbLruCache;

    private ScrollAdapter mAdapter;

    private Handler mHandler;

    public BaseLoadThumb(Context context){
        mThumbCover=new ArrayList<>();
        mThumbLruCache = ThumLruCache.getInstance(context);
        mAdapter = new ScrollAdapter(context);
        mHandler = new Handler() ;
    }

    @Override
    public void loadThumbs(String videoPath,long videoDuration) {
        Bitmap bitmap=null;
        int thumbCount = mAdapter.getItemCount();
        long perPos = (videoDuration - 100) / thumbCount;
        for (int i = 0; i < thumbCount; i++) {
            if (mThumbLruCache.get(videoPath + i) != null) {
                mThumbCover.add(i, mThumbLruCache.get(videoPath + i));
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (mThumbCover != null && mThumbCover.size() > 0) {
                            mAdapter.setThumbCover(mThumbCover);
                            mAdapter.notifyDataSetChanged();
                        }
                    }
                });
            } else {
                bitmap = FrameThumbUtil.getFrameThumbAtpos(videoPath, i * perPos, 210, 118);
                mThumbCover.add(i, bitmap);
                mThumbLruCache.put(videoPath + i, bitmap);
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (mThumbCover != null && mThumbCover.size() > 0) {
                            mAdapter.setThumbCover(mThumbCover);
                            mAdapter.notifyDataSetChanged();
                        }
                    }
                });
            }
        }
    }

    public ScrollAdapter getAdapter(){
        return this.mAdapter;
    }

    public LruCache<String,Bitmap> getLruCache(){
        return mThumbLruCache;
    }

    public List<Bitmap> getThumbCover(){
        return this.mThumbCover;
    }

}
