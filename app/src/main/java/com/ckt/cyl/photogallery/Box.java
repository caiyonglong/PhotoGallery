package com.ckt.cyl.photogallery;

import android.graphics.PointF;

/**
 * Created by D22434 on 2017/7/31.
 */

public class Box {
    private PointF mOrigin;
    private PointF mCurrent;

    public Box(PointF mOrigin, PointF mCurrent) {
    }

    public Box(PointF current) {
        this.mOrigin = current;
        this.mCurrent = current;
    }

    public PointF getmOrigin() {
        return mOrigin;
    }

    public void setmOrigin(PointF mOrigin) {
        this.mOrigin = mOrigin;
    }

    public PointF getmCurrent() {
        return mCurrent;
    }

    public void setmCurrent(PointF mCurrent) {
        this.mCurrent = mCurrent;
    }
}
