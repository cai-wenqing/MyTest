package com.aiyakeji.mytest.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.aiyakeji.mytest.R;
import com.nineoldandroids.view.ViewHelper;

import static com.aiyakeji.mytest.R.id.main_btn_one;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button button;
    private Button btn_two;
    private Button btn_three;
    private Button btn_four;
    private Button btn_five;
    private Button btn_six;
    private Button btn_seven;
    private Button btn_eight;
    private Button btn_slidingcard;
    private Button btn_mqtt;
    private Button btn_wavedcircle;
    private Button btn_lunbotu;
    private Button btn_voiceshake;
    private Button btn_material;
    private Button btn_kotlin;
    private Button btn_testLayout;
    private Button btn_annotation;
    private Button btn_proxy;
    private Button btn_alpha_title;
    private Button btn_calendar;

    private Toolbar toolbar;
    private ImageView iv_menu_toggle;
    private DrawerLayout mDrawerLayout;
    private ListView lvLeftMenu;
    private String[] lvs = {"List Item 01", "List Item 02", "List Item 03", "List Item 04"};
    private ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        initView();
        initEvents();
    }


    private void findViews() {
        toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        iv_menu_toggle = (ImageView) findViewById(R.id.main_iv__menu_toggle);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.main_drawerlayout);
        mDrawerLayout.setScrimColor(0x00000000);

        button = (Button) findViewById(main_btn_one);
        btn_two = (Button) findViewById(R.id.main_btn_two);
        btn_three = (Button) findViewById(R.id.main_btn_three);
        btn_four = (Button) findViewById(R.id.main_btn_four);
        btn_five = (Button) findViewById(R.id.main_btn_five);
        btn_six = (Button) findViewById(R.id.main_btn_six);
        btn_seven = (Button) findViewById(R.id.main_btn_seven);
        btn_eight = (Button) findViewById(R.id.main_btn_eight);
        btn_slidingcard = (Button) findViewById(R.id.main_btn_slidingcard);
        btn_mqtt = (Button) findViewById(R.id.main_btn_mqtt);
        btn_wavedcircle = (Button) findViewById(R.id.main_btn_wavedcircle);
        btn_lunbotu = (Button) findViewById(R.id.main_btn_lunbotu);
        btn_voiceshake = (Button) findViewById(R.id.main_btn_voiceshake);
        btn_material = (Button) findViewById(R.id.main_btn_material);
        btn_kotlin = findViewById(R.id.main_btn_kotlin);
        btn_testLayout = findViewById(R.id.main_btn_test_layout);
        btn_annotation = findViewById(R.id.main_btn_annotation);
        btn_proxy = findViewById(R.id.main_btn_proxy);
        btn_alpha_title = findViewById(R.id.main_btn_alphatitle);
        btn_calendar = findViewById(R.id.main_btn_calendar);

        lvLeftMenu = (ListView) findViewById(R.id.left_lv_menu);

        iv_menu_toggle.setOnClickListener(this);
        button.setOnClickListener(this);
        btn_two.setOnClickListener(this);
        btn_three.setOnClickListener(this);
        btn_four.setOnClickListener(this);
        btn_five.setOnClickListener(this);
        btn_six.setOnClickListener(this);
        btn_seven.setOnClickListener(this);
        btn_eight.setOnClickListener(this);
        btn_slidingcard.setOnClickListener(this);
        btn_mqtt.setOnClickListener(this);
        btn_wavedcircle.setOnClickListener(this);
        btn_lunbotu.setOnClickListener(this);
        btn_voiceshake.setOnClickListener(this);
        btn_material.setOnClickListener(this);
        btn_kotlin.setOnClickListener(this);
        btn_testLayout.setOnClickListener(this);
        btn_annotation.setOnClickListener(this);
        btn_proxy.setOnClickListener(this);
        btn_alpha_title.setOnClickListener(this);
        btn_calendar.setOnClickListener(this);
    }


    private void initView() {
        toolbar.setTitle("Toolbar");//设置Toolbar标题
        toolbar.setTitleTextColor(Color.parseColor("#ffffff")); //设置标题颜色

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            //去除默认Title显示
            actionBar.setDisplayShowTitleEnabled(false);
        }

        //设置菜单列表
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, lvs);
        lvLeftMenu.setAdapter(arrayAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_iv__menu_toggle:
                //侧边栏开关
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.main_btn_one:
                //打开新页面
                startActivity(new Intent(MainActivity.this, CoordinatorActivity.class));
                overridePendingTransition(R.anim.payactivity_open, 0);
                break;
            case R.id.main_btn_two:
                //属性动画
                startActivity(new Intent(MainActivity.this, AnimatorActivity.class));
                break;
            case R.id.main_btn_three:
                //仿QQ计步器
                startActivity(new Intent(MainActivity.this, CircleProgressActivity.class));
                break;
            case R.id.main_btn_four:
                //华为时钟
                startActivity(new Intent(this, HuaWeiClockActivity.class));
                break;
            case R.id.main_btn_five:
                //横向滑动选择器
                startActivity(new Intent(this, HorizontalScrollSelectorActivity.class));
                break;
            case R.id.main_btn_six:
                //手势解锁
                startActivity(new Intent(this, GestureUnlockActivity.class));
                break;
            case R.id.main_btn_seven:
                //手势转盘
                startActivity(new Intent(this, RotatePlateActivity.class));
                break;
            case R.id.main_btn_eight:
                //万能适配器
                startActivity(new Intent(this, UniversalAdapterActivity.class));
                break;
            case R.id.main_btn_slidingcard:
                //滑动卡片
                startActivity(new Intent(this, SlidingCardActivity.class));
                break;
            case R.id.main_btn_mqtt:
                //mqtt
                startActivity(new Intent(this, MqttActivity.class));
                break;
            case R.id.main_btn_wavedcircle:
                //波动圆
                startActivity(new Intent(this, WavedCircleActivity.class));
                break;
            case R.id.main_btn_lunbotu:
                //轮播图
                startActivity(new Intent(this, LunBoTuActivity.class));
                break;
            case R.id.main_btn_voiceshake:
                //声波显示
                startActivity(new Intent(this, VoiceShakeActivity.class));
                break;
            case R.id.main_btn_material:
                //MaterialDesign测试
                startActivity(new Intent(this, MaterialTestActivity.class));
                break;
            case R.id.main_btn_kotlin:
                //kotlin测试
                startActivity(new Intent(this, KotlinTestActivity.class));
                break;
            case R.id.main_btn_test_layout:
                //测试布局
                startActivity(new Intent(this, TestLayoutActivity.class));
                break;
            case R.id.main_btn_annotation:
                //注解
                startActivity(new Intent(this, AnnotationTestActivity.class));
                break;
            case R.id.main_btn_proxy:
                //代理模式
                startActivity(new Intent(this, ProxyTestActivity.class));
                break;
            case R.id.main_btn_alphatitle:
                //联动title
                startActivity(new Intent(this, AlphaScrollTitleActivity.class));
                break;
            case R.id.main_btn_calendar:
                //日历
                startActivity(new Intent(this, CalendarActivity.class));
                break;
            default:
                break;
        }
    }

    private void initEvents() {
        mDrawerLayout.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerStateChanged(int newState) {
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                View mContent = mDrawerLayout.getChildAt(0);
                View mMenu = drawerView;
                float scale = 1 - slideOffset;
                float rightScale = 0.8f + scale * 0.2f;

                float leftScale = 1 - 0.3f * scale;

                ViewHelper.setScaleX(mMenu, leftScale);
                ViewHelper.setScaleY(mMenu, leftScale);
                ViewHelper.setAlpha(mMenu, 0.6f + 0.4f * (1 - scale));
                ViewHelper.setTranslationX(mContent, mMenu.getMeasuredWidth() * (1 - scale));
                ViewHelper.setPivotX(mContent, 0);
                ViewHelper.setPivotY(mContent, mContent.getMeasuredHeight() / 2);
                mContent.invalidate();
                ViewHelper.setScaleX(mContent, rightScale);
                ViewHelper.setScaleY(mContent, rightScale);

            }

            @Override
            public void onDrawerOpened(View drawerView) {
            }

            @Override
            public void onDrawerClosed(View drawerView) {
//                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, Gravity.RIGHT);
            }
        });
    }
}
