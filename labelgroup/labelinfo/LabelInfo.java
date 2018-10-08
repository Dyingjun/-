package com.meitu.flu.labelgroup.labelinfo;

/**
 * Created by dj on 2018/8/6.
 */

public class LabelInfo {

    private String mTitle;

    private int mID = -1;

    private String mMessage;

    public LabelInfo(String titile) {
        this.mTitle = titile;
    }

    public String getTitle() {
        return mTitle;
    }

    public int getID() {
        return mID;
    }

    public void setID(int id) {
        this.mID = id;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        this.mMessage = message;
    }
}
