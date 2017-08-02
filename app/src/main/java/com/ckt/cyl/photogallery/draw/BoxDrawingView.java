package com.ckt.cyl.photogallery.draw;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by D22434 on 2017/7/31.
 */

public class BoxDrawingView extends View {

    private static final String TAG = "BoxDrawingView";
    private static final String KEY_SUPER_DATA = "super_data";
    private static final String KEY_BOXEN = "boxlist";
    private int MODE = 1;//1 普通状态。2 旋转状态
    private Box mCurrentBox;
    private List<Box> mBoxen = new ArrayList<>();

    private Paint mBoxPaint;
    private PointF midF;
    private Paint mBackgroundPaint;

    public BoxDrawingView(Context context) {
        this(context, null);
    }

    public BoxDrawingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        mBoxPaint = new Paint();
        mBoxPaint.setColor(0x22ff0000);

        mBackgroundPaint = new Paint();
        mBackgroundPaint.setColor(0xfff8efe0);
    }

    @Override
    protected Parcelable onSaveInstanceState() {

        Bundle bundle = new Bundle();
        // 存储父类需要存储的内容
        Parcelable superData = super.onSaveInstanceState();
        bundle.putParcelable(KEY_SUPER_DATA, superData);
        // 存储所有的矩形
        bundle.putSerializable(KEY_BOXEN, (ArrayList) mBoxen);
        return bundle;

    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        Bundle bundle = (Bundle) state;
        //取出父类的内容
        Parcelable superData = bundle.getParcelable(KEY_SUPER_DATA);
        //取出所有的矩形
        mBoxen = (List<Box>) bundle.getSerializable(KEY_BOXEN);
        super.onRestoreInstanceState(superData);
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawPaint(mBackgroundPaint);

        for (Box box : mBoxen) {

            //保存当前画布大小即整屏
            canvas.save();
            //按照中心点旋转角度
            canvas.rotate(box.getRotatedAngle(), box.getCenter().x, box.getCenter().y);

            float left = Math.min(box.getmOrigin().x, box.getmCurrent().x);
            float right = Math.max(box.getmOrigin().x, box.getmCurrent().x);
            float top = Math.min(box.getmOrigin().y, box.getmCurrent().y);
            float bottom = Math.max(box.getmOrigin().y, box.getmCurrent().y);

            canvas.drawRect(left, top, right, bottom, mBoxPaint);

            //恢复整屏画布
            canvas.restore();
        }


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        PointF current = new PointF(event.getX(), event.getY());
        String action = "";

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                action = "ACTION_DOWN";
                mCurrentBox = new Box(current);
                mBoxen.add(mCurrentBox);


                break;
            case MotionEvent.ACTION_UP:
                action = "ACTION_UP";
                mCurrentBox = null;
                break;
            case MotionEvent.ACTION_MOVE:
                action = "ACTION_MOVE";
                if (mCurrentBox != null) {
                    // 如果只有一只手指按下，而且还未曾旋转过的话，就进行大小的缩放
                    if (event.getPointerCount() == 1 && mCurrentBox.getRotatedAngle() == 0) {
                        mCurrentBox.setCurrent(current);
                    }
                    // 如果按下了两根手指
                    if (event.getPointerCount() == 2) {
                        // 获取角度
                        float angle = (float) (Math.atan((event.getY(1) - event.getY(0)) /
                                (event.getX(1) - event.getX(0))) * 180 / Math.PI);
                        Log.i(TAG, "onTouchEvent: angle:" + (angle - mCurrentBox.getOriginAngle()));
                        // 已旋转的角度 = 之前旋转的角度 + 新旋转的角度
                        // 新旋转的角度 = 本次 move 到的角度 - 手指按下的角度
                        mCurrentBox.setRotatedAngle(mCurrentBox.getRotatedAngle() + angle
                                - mCurrentBox.getOriginAngle());
                        // 旋转角度变化后，初始角度也发生变化
                        mCurrentBox.setOriginAngle(angle);
                    }
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_CANCEL:
                action = "ACTION_CANCEL";
                mCurrentBox = null;
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                if (event.getPointerCount() == 2) {
                    // 首先获取按下时的角度（有一个弧度转角度的过程）
                    // 每次按下的时候将角度存入现在矩形的原始角度
                    float angle = (float) (Math.atan((event.getY(1) - event.getY(0)) /
                            (event.getX(1) - event.getX(0))) * 180 / Math.PI);
                    mCurrentBox.setOriginAngle(angle);
                }

                break;
            case MotionEvent.ACTION_POINTER_UP:
                MODE = 1;
                Log.i(TAG, MODE + " ==MODE");
                break;
        }

        Log.i(TAG, action + "at x =" + current.x
                + ", y = " + current.y);
        return true;
    }

}
