package com.example.dj.autodialog.exception;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.os.Looper;
import android.widget.Toast;
import android.content.Intent;

import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.text.DateFormat;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.lang.Thread.UncaughtExceptionHandler;
import java.io.File;

/**
 * Created by dj on 2018/7/12.
 */

public class CatchExceptionUtil implements UncaughtExceptionHandler {

    private Context mContext;

    private Thread.UncaughtExceptionHandler mDefaultHandle;

    private static CatchExceptionUtil INSTANCE;

    private Map<String, String> mErrorInfos;

    private DateFormat mDataFormat;

    private static String mExceptionLogpath;


    public CatchExceptionUtil(Context context) {
        this.mContext = context.getApplicationContext();
        this.mErrorInfos = new HashMap<>();
        this.mDataFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        mDefaultHandle = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    public static CatchExceptionUtil getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new CatchExceptionUtil(context);
        }
        return INSTANCE;
    }

    public static void setcrashPath(String path){
        mExceptionLogpath = path;
    }

    public void collectDeviceInfo(Context context) {

        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES);
            if (packageInfo != null) {
                String versionName = packageInfo.versionName;
                String versionCode = packageInfo.versionCode + "";
                mErrorInfos.put("versionName", versionName);
                mErrorInfos.put("versionCode", versionCode);
            }
        } catch (NameNotFoundException e) {
            Log.e("CatchExceptionUtil", "an error occured when collect package info", e);
        }

        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                mErrorInfos.put(field.getName(), field.get(null).toString());
                Log.d("CatchExceptionUtil", field.getName() + ":" + field.get(null));
            } catch (Exception e) {
                Log.e("CatchExceptionUtil", "an error occured when collect crash info", e);

            }
        }
    }

    private void saveUncatchExceptionToFile(Throwable throwable) {
        StringBuffer stringBuffer = new StringBuffer();
        for (Map.Entry<String, String> entry : mErrorInfos.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            stringBuffer.append(key + "=" + value + "\n");
        }
        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        throwable.printStackTrace(printWriter);
        Throwable cause = throwable.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        String result = writer.toString();
        stringBuffer.append(result + "\n");
        try {
            long timeStamp = System.currentTimeMillis();
            String time = mDataFormat.format(new Date());
            String fileName = "crash_" + time + "-" + timeStamp + ".log";
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                File dir = new File(mExceptionLogpath);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                FileOutputStream fileOutputStream = new FileOutputStream(mExceptionLogpath + fileName);
                fileOutputStream.write(stringBuffer.toString().getBytes());
                fileOutputStream.close();
            }
        } catch (Exception e) {
            Log.e("CatchExceptionUtil", "an error occured while writing file", e);
        }
    }

    private boolean handleException(Throwable throwable) {
        if (throwable == null) {
            return true;
        }
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                Toast.makeText(mContext, "抱歉，程序发生异常，即将退出", Toast.LENGTH_LONG).show();
                Looper.loop();
            }

            ;
        }.start();
        collectDeviceInfo(mContext);
        saveUncatchExceptionToFile(throwable);
        return false;
    }


    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
        if (handleException(throwable)) {
            if (mDefaultHandle != null) {
                mDefaultHandle.uncaughtException(thread, throwable);
                mDefaultHandle.uncaughtException(null, throwable);
            }
        } else {
                try {

                    Thread.sleep(3000);
                    Log.d("CatchExceptionUtil", "sleep");
                } catch (InterruptedException e) {
                    Log.e("CatchExceptionUtil", "error:", e);
                }
        }
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }
}