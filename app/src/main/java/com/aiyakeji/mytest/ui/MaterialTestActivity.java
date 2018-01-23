package com.aiyakeji.mytest.ui;

import android.animation.Animator;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Pair;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.aiyakeji.mytest.R;

/**
 * Created by Administrator on 2018/1/18 0018.
 * MaterialDesign测试
 */

public class MaterialTestActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btn_shareanimal;
    private ImageView iv_share;
    private TextView tv_share;
    private ImageView iv_oval;
    private ImageView iv_rect;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_materialtest);

        btn_shareanimal = (Button) findViewById(R.id.material_btn_shareanimal);
        iv_share = (ImageView) findViewById(R.id.material_iv_share);
        tv_share = (TextView) findViewById(R.id.material_tv_share);
        iv_oval = (ImageView) findViewById(R.id.material_iv_oval);
        iv_rect = (ImageView) findViewById(R.id.material_iv_rect);

        btn_shareanimal.setOnClickListener(this);
        iv_share.setOnClickListener(this);
        iv_oval.setOnClickListener(this);
        iv_rect.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.material_btn_shareanimal:
                break;
            case R.id.material_iv_share://共享图片
                if (Build.VERSION.SDK_INT >= 21)
                    startActivity(new Intent(this, MaterialTestBActivity.class),
                            ActivityOptions.makeSceneTransitionAnimation(this,
                                    new Pair<View, String>(iv_share, "MaterialShare_iv"),
                                    new Pair<View, String>(tv_share, "MaterialShare_tv")).toBundle());
                else
                    startActivity(new Intent(this, MaterialTestBActivity.class));
                break;
            case R.id.material_iv_oval://椭圆
                if (Build.VERSION.SDK_INT >= 21) {
                    Animator animator = ViewAnimationUtils.createCircularReveal(
                            iv_oval,
                            iv_oval.getWidth() / 2,
                            iv_oval.getHeight() / 2,
                            0,
                            iv_oval.getWidth());
                    animator.setInterpolator(new AccelerateDecelerateInterpolator());
                    animator.setDuration(2000);
                    animator.start();
                }

                break;
            case R.id.material_iv_rect://矩形
                if (Build.VERSION.SDK_INT >= 21) {
                    Animator animator = ViewAnimationUtils.createCircularReveal(
                            iv_rect,
                            0,
                            0,
                            0,
                            (float) Math.hypot(iv_rect.getWidth(), iv_rect.getHeight()));//计算对角线长度
                    animator.setInterpolator(new AccelerateDecelerateInterpolator());
                    animator.setDuration(2000);
                    animator.start();
                }
                break;
        }
    }
}
