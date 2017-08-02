package com.ckt.cyl.photogallery.draw;

import android.graphics.PointF;

/**
 * Created by D22434 on 2017/7/31.
 */

public class Box {
    private PointF mOrigin;
    private PointF mCurrent;
    // 此次按下时的角度
    private float mOriginAngle;
    private float mRotatedAngle; // 已旋转的角度

    public Box(PointF origin) {
        mOrigin = origin;
        mCurrent = origin;
        mOriginAngle = 0;
        mRotatedAngle = 0;
    }

    /**
     * 省略 Getter 和 Setter
     **/

    // 获取矩形的中心点
    public PointF getCenter() {
        return new PointF(
                (mCurrent.x + mOrigin.x) / 2,
                (mCurrent.y + mOrigin.y) / 2);
    }


    public PointF getmOrigin() {
        return mOrigin;
    }

    public void setmOrigin(PointF mOrigin) {
        this.mOrigin = mOrigin;
    }

    public float getOriginAngle() {
        return mOriginAngle;
    }

    public void setOriginAngle(float mOriginAngle) {
        this.mOriginAngle = mOriginAngle;
    }

    public PointF getmCurrent() {
        return mCurrent;
    }

    public void setCurrent(PointF mCurrent) {
        this.mCurrent = mCurrent;
    }


    public float getRotatedAngle() {
        return mRotatedAngle;
    }

    public void setRotatedAngle(float mRotatedAngle) {
        this.mRotatedAngle = mRotatedAngle;
    }
}
