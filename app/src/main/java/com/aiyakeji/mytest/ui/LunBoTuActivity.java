package com.aiyakeji.mytest.ui;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aiyakeji.mytest.R;
import com.aiyakeji.mytest.utils.DensityUtils;
import com.aiyakeji.mytest.widgets.ViewPagerIndicator;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/12/20 0020.
 * 轮播图
 */

public class LunBoTuActivity extends AppCompatActivity {
    private ViewPager mViewPager;
    private ViewPagerIndicator indicator1;
    private TextView tv_info;
    private TextView tv_info3;
    private ViewPager mViewPager2;
    private ViewPager mViewPager3;
    private ViewPager mViewPager4;


    private MyPagerAdapter1 adapter;
    private MyPagerAdapter2 adapter2;
    private MyPagerAdapter4 adapter4;
    private int[] imgRes = {R.mipmap.girl1, R.mipmap.girl2, R.mipmap.girl3, R.mipmap.girl4, R.mipmap.girl5};
    private String[] infos = {"说明一", "说明二", "说明三", "说明四", "说明五"};
    private ArrayList<View> itemViews;
    private ArrayList<View> itemViews3;
    private ArrayList<View> itemViews4;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lunbotu);
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        ActionBar actionBar = getSupportActionBar();
        if (null != actionBar) {
            actionBar.hide();
        }

        mViewPager = (ViewPager) findViewById(R.id.lunbotu_viewpager);
        indicator1 = (ViewPagerIndicator) findViewById(R.id.lunbotu_indicator1);
        tv_info = (TextView) findViewById(R.id.lunbotu_tv_info);
        mViewPager2 = (ViewPager) findViewById(R.id.lunbotu_viewpager2);
        mViewPager3 = (ViewPager) findViewById(R.id.lunbotu_viewpager3);
        mViewPager4 = (ViewPager) findViewById(R.id.lunbotu_viewpager4);
        tv_info3 = (TextView) findViewById(R.id.lunbotu_tv_info3);

        initViewPager1();
        initViewPager2();
        initViewPager3();
        initViewPager4();
    }

    //初始化第一个viewpager
    private void initViewPager1() {
        indicator1.setItemCount(imgRes.length);

        adapter = new MyPagerAdapter1();
        tv_info.setText(infos[0]);
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setAdapter(adapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                indicator1.setPositionAndOffset(position, positionOffset);
            }

            @Override
            public void onPageSelected(int position) {
                tv_info.setText(infos[position]);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mViewPager.setPageTransformer(false, new pagerTransformer1());
    }


    private void initViewPager2() {
        itemViews = new ArrayList<>();
        for (int i = 0; i < imgRes.length; i++) {
            itemViews.add(View.inflate(this, R.layout.item_lunbotu_viewpager2, null));
        }

        adapter2 = new MyPagerAdapter2();
        mViewPager2.setOffscreenPageLimit(imgRes.length);
        mViewPager2.setPageMargin(20);
        mViewPager2.setAdapter(adapter2);

        mViewPager2.setPageTransformer(true, new PagerTransformer2());
    }


    private void initViewPager3() {
        itemViews3 = new ArrayList<>();
        for (int i = 0; i < imgRes.length; i++) {
            itemViews3.add(View.inflate(this, R.layout.item_lunbotu_viewpager2, null));
        }
        mViewPager3.setOffscreenPageLimit(imgRes.length);
        mViewPager3.setAdapter(new MyPagerAdapter3());
        tv_info3.setText(infos[0]);
        mViewPager3.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tv_info3.setText(infos[position]);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mViewPager3.setPageTransformer(false, new CubeOutTransformer());
    }


    private void initViewPager4() {
        itemViews4 = new ArrayList<>();
        for (int i = 0; i < imgRes.length; i++) {
            itemViews4.add(View.inflate(this, R.layout.item_lunbotu_viewpager2, null));
        }

        adapter4 = new MyPagerAdapter4();
        mViewPager4.setOffscreenPageLimit(imgRes.length);
        mViewPager4.setAdapter(adapter4);

        mViewPager4.setPageTransformer(true, new PagerTransformer4());
    }


    class pagerTransformer1 implements ViewPager.PageTransformer {

        @Override
        public void transformPage(View page, float position) {
            int width = page.getWidth();
            if (position < -1) {//滑出的页面
                page.setScrollX((int) (width * 0.75 * -1));
            } else if (position <= 1) {//[-1,1]
                if (position < 0) {//[-1,0)
                    page.setScrollX((int) (width * 0.75 * position));
                } else {//[0,1]
                    page.setScrollX((int) (width * 0.75 * position));
                }
            } else {//即将滑入的页面
                page.setScrollX((int) (width * 0.75));
            }
        }
    }


    class PagerTransformer2 implements ViewPager.PageTransformer {

        @Override
        public void transformPage(View page, float position) {
            if (position < -1) {//滑出的页面
                page.setScaleX(0.75f);
                page.setScaleY(0.75f);
            } else if (position <= 1) {//[-1,1]
                if (position < 0) {//[-1,0)
                    page.setScaleX(1 + 0.25f * position);
                    page.setScaleY(1 + 0.25f * position);
                } else {//[0,1]
                    page.setScaleX(1 - 0.25f * position);
                    page.setScaleY(1 - 0.25f * position);
                }
            } else {//即将滑入的页面
                page.setScaleX(0.75f);
                page.setScaleY(0.75f);
            }
        }
    }


    class PagerTransformer4 implements ViewPager.PageTransformer {

        @Override
        public void transformPage(View page, float position) {
            int screenWidth = DensityUtils.getDisplayMetrics(LunBoTuActivity.this).widthPixels;
            page.setTranslationX(-(screenWidth - page.getWidth()) / 2);
            if (position >= -1 && position <= 1) {
                if (position < 0) {
                    page.setScaleX(1 + 0.15f * position);
                    page.setScaleY(1 + 0.15f * position);
                } else {
                    page.setScaleX(1 - 0.15f * position);
                    page.setScaleY(1 - 0.15f * position);
                }
            } else if (position > 1) {
                page.setScaleX(0.85f);
                page.setScaleY(0.85f);
            }
        }
    }

    class CubeOutTransformer implements ViewPager.PageTransformer {

        @Override
        public void transformPage(View page, float position) {
            page.setRotationX(0);
            page.setRotationY(0);
            page.setRotation(0);
            page.setScaleX(1);
            page.setScaleY(1);
            page.setPivotX(0);
            page.setPivotY(0);
            page.setTranslationY(0);
            page.setTranslationX(0f);

            page.setAlpha(position <= -1f || position >= 1f ? 0f : 1f);

            page.setPivotX(position < 0f ? page.getWidth() : 0f);
            page.setPivotY(page.getHeight() * 0.5f);
            page.setRotationY(90f * position);
        }
    }


    class MyPagerAdapter1 extends PagerAdapter {

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView view = new ImageView(LunBoTuActivity.this);
            view.setScaleType(ImageView.ScaleType.CENTER_CROP);
            view.setImageResource(imgRes[position]);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            return imgRes.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }


    class MyPagerAdapter2 extends PagerAdapter {

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = itemViews.get(position);
            ImageView iv = (ImageView) view.findViewById(R.id.item_lunbotu_iv);
            iv.setImageResource(imgRes[position]);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            return itemViews.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }


    class MyPagerAdapter3 extends PagerAdapter {

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = new ImageView(LunBoTuActivity.this);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setImageResource(imgRes[position]);
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            return itemViews3.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }


    class MyPagerAdapter4 extends PagerAdapter {

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = itemViews4.get(position);
            ImageView iv = (ImageView) view.findViewById(R.id.item_lunbotu_iv);
            iv.setImageResource(imgRes[position]);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            return itemViews4.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }
}
