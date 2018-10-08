package com.meitu.flu.scrollbar.utils;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.MediaMetadataRetriever;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by dj on 2018/8/14.
 */

public class FrameThumbUtil {
    public static Bitmap getThumb(Bitmap srcBmp, int thumbWidth,int thumbHight) {
        Bitmap bmpResult = null;
        try {
//            bmpResult = bitmap.copy(Bitmap.Config.ARGB_8888, true);
            File file = new File("/storage/emulated/0/Download/tmp.txt");
            file.getParentFile().mkdirs();


            RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");


            int width = srcBmp.getWidth();
            int height = srcBmp.getHeight();


            FileChannel channel = randomAccessFile.getChannel();
            MappedByteBuffer map = channel.map(FileChannel.MapMode.READ_WRITE, 0, width*height*4);
//将位图信息写进buffer
            srcBmp.copyPixelsToBuffer(map);

//释放原位图占用的空间
            srcBmp.recycle();
//创建一个新的位图
            bmpResult = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_4444);
            map.position(0);
//从临时缓冲中拷贝位图信息
            bmpResult.copyPixelsFromBuffer(map);

            channel.close();
            randomAccessFile.close();
            float scaleWidth = (float) thumbWidth / (float) bmpResult.getWidth() != 1 ?  (float)thumbWidth /  (float)bmpResult.getWidth() : 1;
            float scaleHight = (float) thumbHight / (float) bmpResult.getHeight() != 1 ?  (float)thumbHight /  (float)bmpResult.getHeight() : 1;
            Matrix matrix = new Matrix();
            matrix.setScale(scaleWidth, scaleHight);
            bmpResult = Bitmap.createBitmap(bmpResult, 0, 0, bmpResult.getWidth(), bmpResult.getHeight(), matrix, false);
//            if (!bitmap.isRecycled()) {
//                bitmap.recycle();
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bmpResult;
    }

    public static Bitmap getFrameThumbAtpos(String videoPath, long pos, int thumbWidth,int thumbHight) {
        Object mFrameThumbLockObject = new Object();
        Bitmap bmpResult = null;
        long AtTime = pos * 1000;
        synchronized (mFrameThumbLockObject) {
            MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
            try {
                mediaMetadataRetriever.setDataSource(videoPath);
                Bitmap temp = mediaMetadataRetriever.getFrameAtTime(AtTime, MediaMetadataRetriever.OPTION_CLOSEST_SYNC);
                bmpResult = temp.copy(Bitmap.Config.ARGB_4444, true);
                if (bmpResult.getHeight() / thumbHight == 1) {
                    return bmpResult;
                }
                float scaleWidth = (float) thumbWidth / (float) bmpResult.getWidth() != 1 ?  (float)thumbWidth /  (float)bmpResult.getWidth() : 1;
                float scaleHight = (float) thumbHight / bmpResult.getHeight();
                Matrix matrix = new Matrix();
                matrix.setScale(scaleWidth, scaleHight);
                bmpResult = Bitmap.createBitmap(bmpResult, 0, 0, bmpResult.getWidth(), bmpResult.getHeight(), matrix, true);
                if (!temp.isRecycled()) {
                    temp.recycle();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                mediaMetadataRetriever.release();
            }
        }
        return bmpResult;
    }

}
