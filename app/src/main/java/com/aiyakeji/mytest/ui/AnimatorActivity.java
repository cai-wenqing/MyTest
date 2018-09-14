package com.aiyakeji.mytest.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aiyakeji.mytest.R;
import com.aiyakeji.mytest.utils.DensityUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 页面二
 */

public class AnimatorActivity extends AppCompatActivity implements View.OnClickListener {
    private RelativeLayout rl_head;
    private ImageView iv_avatar;
    private LinearLayout ll_jiuba;
    private TextView tv_nickname;
    private TextView tv_sign;
    private TextView tv_rank;
    private ImageView iv_toggle;
    private ListView mListview;

    private int screenWidth;
    private int headViewHeight = 0;
    private float nicknameX = 0;
    private float avatarX = 0;
    private float avatarWidth = 0;
    private boolean head_isOpen = true;
    private float rankX = 0;
    private float rankWidth = 0;
    private List strList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two);
        initView();
        initData();
    }

    private void initView() {
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        screenWidth = wm.getDefaultDisplay().getWidth();

        rl_head = (RelativeLayout) findViewById(R.id.two_rl_header);
        iv_avatar = (ImageView) findViewById(R.id.two_iv_avatar);
        ll_jiuba = (LinearLayout) findViewById(R.id.two_ll_jiuba);
        tv_nickname = (TextView) findViewById(R.id.two_tv_nickname);
        tv_sign = (TextView) findViewById(R.id.two_tv_sign);
        tv_rank = (TextView) findViewById(R.id.two_tv_rank);
        iv_toggle = (ImageView) findViewById(R.id.two_iv_toggle);
        mListview = (ListView) findViewById(R.id.two_listview);

        iv_toggle.setOnClickListener(this);
    }

    private void initData() {
        strList = new ArrayList();
        for (int i = 0; i < 20; i++) {
            strList.add("测试数据" + i);
        }
        mListview.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, strList));

        setResult(2);
    }


    /**
     * 头部动画
     *
     * @param rl        添加动画的layout
     * @param maxHeight 最大高度
     * @param minHeight 最小高度
     * @param isOpen    目前的状态，如果是true，则会打开，如果是false，则会收缩
     */
    private void headViewAnim(final RelativeLayout rl, int maxHeight, int minHeight, final boolean isOpen) {
        //头布局动画
        ValueAnimator animator;
        //头像
        ObjectAnimator avatarTranslateXAnim;
        ObjectAnimator avatarTranslateYAnim;
        ObjectAnimator avatarScalYAnim;
        ObjectAnimator avatarScalXAnim;
        //昵称
        ObjectAnimator nicknameTransX;
        ObjectAnimator nicknameTransY;
        //排行榜
        ObjectAnimator rankTransX;
        ObjectAnimator rankTransY;
        ObjectAnimator rankScaleX;
        ObjectAnimator rankScaleY;
        ObjectAnimator rankAlpha;

        if (!isOpen) {
            //收缩
            animator = ValueAnimator.ofInt(maxHeight, minHeight);
            //头像动画
            avatarTranslateXAnim = ObjectAnimator.ofFloat(iv_avatar, "translationX", 0, -avatarX);
            avatarTranslateYAnim = ObjectAnimator.ofFloat(iv_avatar, "translationY", 0, -DensityUtils.dip2px(this, 25f));
            avatarScalYAnim = ObjectAnimator.ofFloat(iv_avatar, "scaleY", 1f, 0.6f);
            avatarScalXAnim = ObjectAnimator.ofFloat(iv_avatar, "scaleX", 1f, 0.6f);
            //昵称
            nicknameTransY = ObjectAnimator.ofFloat(tv_nickname, "translationY", 0, -DensityUtils.dip2px(this, 105f));
            nicknameTransX = ObjectAnimator.ofFloat(tv_nickname, "translationX", 0, -(float) (avatarX - avatarWidth * 0.6 - DensityUtils.dip2px(this, 15f)));
            //排行榜
            rankTransX = ObjectAnimator.ofFloat(tv_rank, "translationX", 0, screenWidth - rankX - rankWidth);
            rankTransY = ObjectAnimator.ofFloat(tv_rank, "translationY", 0, -DensityUtils.dip2px(this, 150f));
            rankScaleX = ObjectAnimator.ofFloat(tv_rank, "scaleX", 1f, 0.6f);
            rankScaleY = ObjectAnimator.ofFloat(tv_rank, "scaleY", 1f, 0.6f);
            rankAlpha = ObjectAnimator.ofFloat(tv_rank, "alpha", 0, 1f);
        } else {
            //张开
            animator = ValueAnimator.ofInt(minHeight, maxHeight);
            //头像动画
            avatarTranslateXAnim = ObjectAnimator.ofFloat(iv_avatar, "translationX", -avatarX, 0);
            avatarTranslateYAnim = ObjectAnimator.ofFloat(iv_avatar, "translationY", -DensityUtils.dip2px(this, 30f), 0);
            avatarScalYAnim = ObjectAnimator.ofFloat(iv_avatar, "scaleY", 0.6f, 1f);
            avatarScalXAnim = ObjectAnimator.ofFloat(iv_avatar, "scaleX", 0.6f, 1f);
            //昵称
            nicknameTransY = ObjectAnimator.ofFloat(tv_nickname, "translationY", -DensityUtils.dip2px(this, 105f), 0);
            nicknameTransX = ObjectAnimator.ofFloat(tv_nickname, "translationX", -(float) (avatarX - avatarWidth * 0.6 - DensityUtils.dip2px(this, 15f)), 0);
            //排行榜
            rankTransX = ObjectAnimator.ofFloat(tv_rank, "translationX", screenWidth - rankX - rankWidth, 0);
            rankTransY = ObjectAnimator.ofFloat(tv_rank, "translationY", -DensityUtils.dip2px(this, 150f), 0);
            rankScaleX = ObjectAnimator.ofFloat(tv_rank, "scaleX", 0.6f, 1f);
            rankScaleY = ObjectAnimator.ofFloat(tv_rank, "scaleY", 0.6f, 1f);
            rankAlpha = ObjectAnimator.ofFloat(tv_rank, "alpha", 1f, 0);
        }

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //获取当前的height值
                int h = (int) animation.getAnimatedValue();
                //动态更新headView的高度
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) rl.getLayoutParams();
                layoutParams.height = h;
                rl.setLayoutParams(layoutParams);
                rl.requestLayout();
            }
        });

        AnimatorSet animSet = new AnimatorSet();
        animSet.play(animator)
                .with(avatarTranslateXAnim).with(avatarTranslateYAnim).with(avatarScalYAnim).with(avatarScalXAnim)
                .with(nicknameTransY).with(nicknameTransX)
                .with(rankTransX).with(rankTransY).with(rankScaleX).with(rankScaleY).with(rankAlpha);
        animSet.setDuration(300);
        //动画监听
        animSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                if (!isOpen) {//收缩
                    tv_sign.setVisibility(View.GONE);
                    ll_jiuba.setVisibility(View.GONE);
                    tv_rank.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (isOpen) {//张开
                    ll_jiuba.setVisibility(View.VISIBLE);
                    tv_sign.setVisibility(View.VISIBLE);
                    tv_rank.setVisibility(View.GONE);
                }
            }
        });
        animSet.start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.two_iv_toggle:
                if (0 == headViewHeight)
                    headViewHeight = rl_head.getHeight();
                if (0 == nicknameX)
                    nicknameX = tv_nickname.getX();
                if (0 == avatarX) {
                    avatarX = iv_avatar.getX();
                    avatarWidth = iv_avatar.getWidth();
                }
                if (0 == rankX) {
                    rankX = tv_rank.getX();
                    rankWidth = tv_rank.getWidth();
                }

                if (head_isOpen) {
                    headViewAnim(rl_head, headViewHeight, (int) (headViewHeight / 1.8), false);
                    head_isOpen = false;
                } else {
                    headViewAnim(rl_head, headViewHeight, (int) (headViewHeight / 1.8), true);
                    head_isOpen = true;
                }
                break;
        }
    }
}
