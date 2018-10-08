package com.meitu.flu.process.lrucache;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.util.LruCache;

import com.meitu.flu.partlyeditor.ThumbCache;

import java.util.List;

/**
 * Created by dj on 2018/7/31.
 */

public class ThumLruCache extends LruCache {

    static ThumLruCache mThumCache;

    public ThumLruCache(int maxSize) {
        super(maxSize);
    }

    public static LruCache getInstance(Context context) {
        if (mThumCache == null) {
            int maxMemory = (int) (Runtime.getRuntime().totalMemory()/ 1024);
            int cacheSize = maxMemory / 8;
            mThumCache = new ThumLruCache(1500);
        }
        return mThumCache;
    }

    @Override
    protected int sizeOf(Object key, Object value) {
        return ((Bitmap) value).getByteCount() / 1024;
    }

    @Override
    protected void entryRemoved(boolean evicted, Object key, Object oldValue, Object newValue) {
        super.entryRemoved(evicted, key, oldValue, newValue);
        Log.d("memory", "entryRemoved: "+key);
    }
}
