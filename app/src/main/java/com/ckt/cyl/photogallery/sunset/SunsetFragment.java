package com.ckt.cyl.photogallery.sunset;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.res.Resources;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;

import com.ckt.cyl.photogallery.R;
import com.ckt.cyl.photogallery.databinding.FragmentSunsetBinding;

/**
 * Created by D22434 on 2017/7/31.
 */

public class SunsetFragment extends Fragment {
    private static final String TAG = "SunsetFragment";
    private FragmentSunsetBinding binding;

    private int mBlueSkyColor;
    private int mSunsetSkyColor;
    private int mNightSkyColor;
    private int MODE_SUN_STATUS = 2;
    private int SUN_UP = 1;
    private int SUN_DOWN = 2;

    public static SunsetFragment newInstance() {
        return new SunsetFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil
                .inflate(inflater, R.layout.fragment_sunset, container, false);

        Resources resources = getResources();
        mBlueSkyColor = resources.getColor(R.color.blue_sky);
        mNightSkyColor = resources.getColor(R.color.night_sky);
        mSunsetSkyColor = resources.getColor(R.color.sunset_sky);


        binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MODE_SUN_STATUS == SUN_DOWN) {
                    endSun();
                } else if (MODE_SUN_STATUS == SUN_UP) {
                    startSun();
                }
                Log.d(TAG, "onClick");
            }
        });
        return binding.getRoot();
    }

    public void endSun() {
        float sunYstart = binding.sun.getTop();
        float sunYend = binding.sky.getHeight();


        float refsunYstart = binding.sunReflection.getTop();
        float refsunYheight = binding.sunReflection.getHeight();

        Log.d(TAG, "太阳 - " + binding.sun.getTop() +
                "-" + binding.sun.getBottom() +
                "，高度=" + binding.sun.getHeight() +
                "，view - " + binding.sky.getHeight() +
                "view top =" + binding.sky.getTop() +
                "view bottom =" + binding.sky.getBottom());


        Log.d(TAG, "阴影 - " + binding.sunReflection.getTop() +
                "-" + binding.sunReflection.getBottom() +
                "，高度=" + binding.sunReflection.getHeight() +
                "，view - " + binding.sea.getHeight() +
                "view top =" + binding.sea.getTop() +
                "view bottom =" + binding.sea.getBottom());


        //太阳降落动画
        ObjectAnimator heightAnimator = ObjectAnimator
                .ofFloat(binding.sun, "y", sunYstart, sunYend)
                .setDuration(3200);
        heightAnimator.setInterpolator(new AccelerateInterpolator());


        //倒影上升动画
        ObjectAnimator reflectionAnimator = ObjectAnimator
                .ofFloat(binding.sunReflection, "y", refsunYstart, -refsunYheight)
                .setDuration(3200);
        reflectionAnimator.setInterpolator(new AccelerateInterpolator());


        //太阳缩小
        ObjectAnimator XAnimator = ObjectAnimator
                .ofFloat(binding.sun, "scaleX", 1f, 0.5f)
                .setDuration(3200);

        ObjectAnimator YAnimator = ObjectAnimator
                .ofFloat(binding.sun, "scaleY", 1f, 0.5f)
                .setDuration(3200);

        //倒影缩小
        ObjectAnimator RXAnimator = ObjectAnimator
                .ofFloat(binding.sunReflection, "scaleX", 1f, 0.5f)
                .setDuration(3200);

        ObjectAnimator RYAnimator = ObjectAnimator
                .ofFloat(binding.sunReflection, "scaleY", 1f, 0.5f)
                .setDuration(3200);


        XAnimator.setInterpolator(new AccelerateInterpolator());
        YAnimator.setInterpolator(new AccelerateInterpolator());
        RXAnimator.setInterpolator(new AccelerateInterpolator());
        RYAnimator.setInterpolator(new AccelerateInterpolator());


        //sky颜色变化
        ObjectAnimator colorAnimator = ObjectAnimator
                .ofInt(binding.sky, "backgroundColor", mBlueSkyColor, mSunsetSkyColor)
                .setDuration(3000);
        colorAnimator.setEvaluator(new ArgbEvaluator());

        //夜晚星空颜色变化
        ObjectAnimator nightSkyAnimator = ObjectAnimator
                .ofInt(binding.sky, "backgroundColor", mSunsetSkyColor, mNightSkyColor)
                .setDuration(1500);
        nightSkyAnimator.setEvaluator(new ArgbEvaluator());

        //日落
        AnimatorSet animatorSet = new AnimatorSet();

        animatorSet.play(heightAnimator)
                .with(reflectionAnimator)
                .with(colorAnimator)
                .with(XAnimator)
                .with(YAnimator)
                .with(RXAnimator)
                .with(RYAnimator)
                .before(nightSkyAnimator);

        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                Log.d(TAG, "onAnimationStart");
                binding.getRoot().setClickable(false);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                Log.d(TAG, "onAnimationEnd---");
                MODE_SUN_STATUS = SUN_UP;
                binding.getRoot().setClickable(true);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                Log.d(TAG, "onAnimationCancel---");
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                Log.d(TAG, "onAnimationRepeat---");
            }
        });

        animatorSet.start();

    }

    public void startSun() {
        float sunYstart = binding.sun.getTop();
        float sunYend = binding.sky.getHeight();


        float refsunYstart = binding.sunReflection.getTop();
        float refsunYheight = binding.sunReflection.getHeight();

        Log.d(TAG, "太阳 - " + binding.sun.getTop() +
                "-" + binding.sun.getBottom() +
                "，高度=" + binding.sun.getHeight() +
                "，view - " + binding.sky.getHeight() +
                "view top =" + binding.sky.getTop() +
                "view bottom =" + binding.sky.getBottom());


        Log.d(TAG, "阴影 - " + binding.sunReflection.getTop() +
                "-" + binding.sunReflection.getBottom() +
                "，高度=" + binding.sunReflection.getHeight() +
                "，view - " + binding.sea.getHeight() +
                "view top =" + binding.sea.getTop() +
                "view bottom =" + binding.sea.getBottom());


        //上升动画
        ObjectAnimator heightAnimator = ObjectAnimator
                .ofFloat(binding.sun, "y", sunYend, sunYstart)
                .setDuration(3200);
        heightAnimator.setInterpolator(new AccelerateInterpolator());

        //倒影下降动画
        ObjectAnimator reflectionAnimator = ObjectAnimator
                .ofFloat(binding.sunReflection, "y", -refsunYheight, refsunYstart)
                .setDuration(3200);
        reflectionAnimator.setInterpolator(new AccelerateInterpolator());


        //放大
        ObjectAnimator XAnimator = ObjectAnimator
                .ofFloat(binding.sun, "scaleX", 0.5f, 1f)
                .setDuration(3200);

        ObjectAnimator YAnimator = ObjectAnimator
                .ofFloat(binding.sun, "scaleY", 0.5f, 1f)
                .setDuration(3200);

        XAnimator.setInterpolator(new AccelerateInterpolator());
        YAnimator.setInterpolator(new AccelerateInterpolator());

        //倒影放大
        ObjectAnimator RXAnimator = ObjectAnimator
                .ofFloat(binding.sunReflection, "scaleX", 0.5f, 1f)
                .setDuration(3200);

        ObjectAnimator RYAnimator = ObjectAnimator
                .ofFloat(binding.sunReflection, "scaleY", 0.5f, 1f)
                .setDuration(3200);


        RXAnimator.setInterpolator(new AccelerateInterpolator());
        RYAnimator.setInterpolator(new AccelerateInterpolator());


        //sky颜色变化
        ObjectAnimator colorAnimator = ObjectAnimator
                .ofInt(binding.sky, "backgroundColor", mSunsetSkyColor, mBlueSkyColor)
                .setDuration(3000);
        colorAnimator.setEvaluator(new ArgbEvaluator());

        //夜晚星空颜色变化
        ObjectAnimator nightSkyAnimator = ObjectAnimator
                .ofInt(binding.sky, "backgroundColor", mNightSkyColor, mSunsetSkyColor)
                .setDuration(1500);
        nightSkyAnimator.setEvaluator(new ArgbEvaluator());

        //日落
        AnimatorSet animatorSet = new AnimatorSet();

        animatorSet.play(nightSkyAnimator)
                .before(colorAnimator)
                .with(reflectionAnimator)
                .with(XAnimator)
                .with(YAnimator)
                .with(RXAnimator)
                .with(RYAnimator)
                .with(heightAnimator);

        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                Log.d(TAG, "onAnimationStart");
                binding.getRoot().setClickable(false);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                Log.d(TAG, "onAnimationEnd---");
                MODE_SUN_STATUS = SUN_DOWN;
                binding.getRoot().setClickable(true);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                Log.d(TAG, "onAnimationCancel---");
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                Log.d(TAG, "onAnimationRepeat---");
            }
        });
        animatorSet.start();

    }
}
