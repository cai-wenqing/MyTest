package com.aiyakeji.mytest.widgets;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;

import com.aiyakeji.mytest.R;
import com.aiyakeji.mytest.utils.Utils;

/**
 * Author：CWQ
 * Date：2019-09-09
 * Desc：底部动画弹窗，避免因虚拟按键导致弹窗显示异常问题
 */
public class BottomSheet extends Dialog {
    private static final String TAG = "BottomSheet";
    private final static int mAnimationDuration = 200;//动画时长
    private View mContentView;
    private boolean mIsAnimating = false;

    public BottomSheet(@NonNull Context context) {
        super(context);
    }

    public BottomSheet(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().getDecorView().setPadding(0, 0, 0, 0);

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.BOTTOM | Gravity.CENTER;

        int screenWidth = Utils.getWindowWidth(getContext());
        int screenHeight = Utils.getWindowHeight(getContext());
        params.width = screenWidth < screenHeight ? screenWidth : screenHeight;
        getWindow().setAttributes(params);
        setCanceledOnTouchOutside(true);
    }


    @Override
    public void setContentView(int layoutResID) {
        mContentView = LayoutInflater.from(getContext()).inflate(layoutResID, null);
        super.setContentView(mContentView);
    }

    @Override
    public void setContentView(@NonNull View view, ViewGroup.LayoutParams params) {
        mContentView = view;
        super.setContentView(view, params);
    }

    public View getContentView() {
        return mContentView;
    }

    @Override
    public void setContentView(@NonNull View view) {
        mContentView = view;
        super.setContentView(view);
    }


    public static class BottomSheetBuilder {

        private Context mContext;
        private BottomSheet mDialog;
        private int mContentViewId;
        private View mContentView;

        public BottomSheetBuilder(Context context) {
            mContext = context;
        }

        public BottomSheetBuilder setContentId(int layoutId) {
            mContentViewId = layoutId;
            return this;
        }

        public BottomSheetBuilder setContentView(View contentView) {
            this.mContentView = contentView;
            return this;
        }


        public BottomSheet build() {
            return build(R.style.BottomSheet);
        }


        public BottomSheet build(int style) {
            if (mContentView == null && mContentViewId == 0) {
                return null;
            } else {
                if (mContentView != null) {
                    mDialog = new BottomSheet(mContext, style);
                    mDialog.setContentView(mContentView,
                            new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    return mDialog;
                } else {
                    mDialog = new BottomSheet(mContext, style);
                    mDialog.setContentView(View.inflate(mContext, mContentViewId, null),
                            new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    return mDialog;
                }
            }
        }
    }


    @Override
    public void show() {
        super.show();
        animateUp();
    }

    @Override
    public void dismiss() {
        if (mIsAnimating) {
            return;
        }
        animateDown();
    }


    /**
     * BottomSheet升起动画
     */
    private void animateUp() {
        if (mContentView == null) {
            return;
        }
        TranslateAnimation translate = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 1f, Animation.RELATIVE_TO_SELF, 0f
        );
        AlphaAnimation alpha = new AlphaAnimation(0, 1);
        AnimationSet set = new AnimationSet(true);
        set.addAnimation(translate);
        set.addAnimation(alpha);
        set.setInterpolator(new DecelerateInterpolator());
        set.setDuration(mAnimationDuration);
        set.setFillAfter(true);
        mContentView.startAnimation(set);
    }


    /**
     * BottomSheet降下动画
     */
    private void animateDown() {
        if (mContentView == null) {
            return;
        }
        final Runnable dismissTask = new Runnable() {
            @Override
            public void run() {
                try {
                    BottomSheet.super.dismiss();
                } catch (Exception e) {
                    Log.w(TAG, "dismiss error\n" + Log.getStackTraceString(e));
                }
            }
        };
        if (mContentView.getHeight() == 0) {
            dismissTask.run();
            return;
        }
        TranslateAnimation translate = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 1f
        );
        AlphaAnimation alpha = new AlphaAnimation(1, 0);
        AnimationSet set = new AnimationSet(true);
        set.addAnimation(translate);
        set.addAnimation(alpha);
        set.setInterpolator(new DecelerateInterpolator());
        set.setDuration(mAnimationDuration);
        set.setFillAfter(true);
        set.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                mIsAnimating = true;
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mIsAnimating = false;
                mContentView.post(dismissTask);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        mContentView.startAnimation(set);
    }
}
