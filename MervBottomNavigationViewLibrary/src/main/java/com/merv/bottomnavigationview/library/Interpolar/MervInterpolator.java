package com.merv.bottomnavigationview.library.Interpolar;

import android.animation.TimeInterpolator;
import android.content.Context;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnimationUtils;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;

public class MervInterpolator {

    public static final int LINEAR = 0;
    public static final int ACCELERATE = 1;
    public static final int DECELERATE = 2;
    public static final int ACCELERATE_DECELERATE = 3;
    public static final int OVERSHOOT = 4;
    public static final int BOUNCE = 5;
    public static final int NONE = 6;
    public static final int ANTICIPATE = 7;
    public static final int ANTICIPATE_OVERSHOOT = 8;
    public static final int FAST_OUT_SLOW_IN = 9;

    public static TimeInterpolator get(int mType, Context mContext) {
        switch (mType) {
            case LINEAR:
                return new LinearInterpolator();
            case ACCELERATE:
                return new AccelerateInterpolator();
            case DECELERATE:
                return new DecelerateInterpolator();
            case ACCELERATE_DECELERATE:
                return new AccelerateDecelerateInterpolator();
            case OVERSHOOT:
                return new OvershootInterpolator();
            case BOUNCE:
                return new BounceInterpolator();
            case NONE:
                return mInput -> 1f;
            case ANTICIPATE:
                return new AnticipateInterpolator();
            case ANTICIPATE_OVERSHOOT:
                return new AnticipateOvershootInterpolator();
            case FAST_OUT_SLOW_IN:
                return AnimationUtils.loadInterpolator(mContext, android.R.interpolator.fast_out_slow_in);
            default:
                return new OvershootInterpolator();
        }
    }
}
