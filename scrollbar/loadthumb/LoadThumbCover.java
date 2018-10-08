package com.meitu.flu.scrollbar.loadthumb;

import android.content.Context;
import android.graphics.Bitmap;

import com.meitu.flu.scrollbar.adapter.ScrollAdapter;
import java.util.List;

/**
 * Created by dj on 2018/8/22.
 */

public class LoadThumbCover implements Runnable {

    private String mVideoPath;

    private Long mVideoDuration;

    private BaseLoadThumb mBaseLoadThumb;

    public LoadThumbCover(Context context) {
        mBaseLoadThumb = new BaseLoadThumb(context);
    }

    public void initLoadThumbCover(String videoPath, long videoDuration) {
        mVideoPath = videoPath;
        mVideoDuration = videoDuration;
        int thumbCount=Math.abs((int) videoDuration / 5000);
        mBaseLoadThumb.getAdapter().setItemCount(thumbCount);
    }

    public ScrollAdapter getAdapter() {
        if (mBaseLoadThumb.getAdapter() == null) {
            return null;
        }
        return mBaseLoadThumb.getAdapter();
    }

    public List<Bitmap> getThumbCover() {
        if (mBaseLoadThumb.getThumbCover() == null || mBaseLoadThumb.getThumbCover().size() == 0) {
            return null;
        }
        return mBaseLoadThumb.getThumbCover();
    }

    @Override
    public void run() {
        if (mVideoPath == null) {
            return;
        }
        if (mVideoDuration <= 0) {
            return;
        }
        mBaseLoadThumb.loadThumbs(mVideoPath, mVideoDuration);
    }
}
