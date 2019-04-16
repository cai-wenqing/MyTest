package com.aiyakeji.mytest.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.aiyakeji.mytest.R;
import com.aiyakeji.mytest.adapters.MainAdapter;
import com.aiyakeji.mytest.listener.OnClickItemListener;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    private String[] itemArr = {"折叠头部", "属性动画", "仿QQ步数圆形进度", "仿华为时钟",
            "滚动选择器", "手势解锁", "圆盘", "万能适配器", "滑动卡片", "MQTT", "波动圆", "轮播图",
            "声音波", "material design", "注解", "代理模式", "联动title", "日历",
            "日历列表", "动态脚布局", "动态高度layout", "滑动指示器", "标签控件","跑马灯及ViewFlipper",
            "权限申请封装","侧滑SideBar"};

    private MainAdapter contentAdapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        recyclerView = findViewById(R.id.recycleView);

        contentAdapter = new MainAdapter(this, Arrays.asList(itemArr));
        contentAdapter.setOnClickItemListener(new OnClickItemListener() {
            @Override
            public void onClickItem(int position) {
                switch (position) {
                    case 0://折叠头部
                        startActivity(new Intent(MainActivity.this, CoordinatorActivity.class));
                        overridePendingTransition(R.anim.payactivity_open, 0);
                        break;
                    case 1://属性动画
                        startActivity(new Intent(MainActivity.this, AnimatorActivity.class));
                        break;
                    case 2://仿QQ步数圆形进度
                        startActivity(new Intent(MainActivity.this, CircleProgressActivity.class));
                        break;
                    case 3://仿华为时钟
                        startActivity(new Intent(MainActivity.this, HuaWeiClockActivity.class));
                        break;
                    case 4://横向滑动选择器
                        startActivity(new Intent(MainActivity.this, HorizontalScrollSelectorActivity.class));
                        break;
                    case 5://手势解锁
                        startActivity(new Intent(MainActivity.this, GestureUnlockActivity.class));
                        break;
                    case 6://圆盘
                        startActivity(new Intent(MainActivity.this, RotatePlateActivity.class));
                        break;
                    case 7://万能适配器
                        startActivity(new Intent(MainActivity.this, UniversalAdapterActivity.class));
                        break;
                    case 8://滑动卡片
                        startActivity(new Intent(MainActivity.this, SlidingCardActivity.class));
                        break;
                    case 9://MQTT
                        startActivity(new Intent(MainActivity.this, MqttActivity.class));
                        break;
                    case 10://波动圆
                        startActivity(new Intent(MainActivity.this, WavedCircleActivity.class));
                        break;
                    case 11://轮播图
                        startActivity(new Intent(MainActivity.this, LunBoTuActivity.class));
                        break;
                    case 12://声音波
                        startActivity(new Intent(MainActivity.this, VoiceShakeActivity.class));
                        break;
                    case 13://material design
                        startActivity(new Intent(MainActivity.this, MaterialTestActivity.class));
                        break;
                    case 14://注解
                        startActivity(new Intent(MainActivity.this, AnnotationTestActivity.class));
                        break;
                    case 15://代理模式
                        startActivity(new Intent(MainActivity.this, ProxyTestActivity.class));
                        break;
                    case 16://联动title
                        startActivity(new Intent(MainActivity.this, AlphaScrollTitleActivity.class));
                        break;
                    case 17://日历
                        startActivity(new Intent(MainActivity.this, CalendarActivity.class));
                        break;
                    case 18://日历列表
                        startActivity(new Intent(MainActivity.this, CalendarListActivity.class));
                        break;
                    case 19://动态脚布局
                        startActivity(new Intent(MainActivity.this, FootViewActivity.class));
                        break;
                    case 20://动态高度layout
                        startActivity(new Intent(MainActivity.this, MaxHeightLayoutActivity.class));
                        break;
                    case 21://滑动指示器
                        startActivity(new Intent(MainActivity.this, WernerTabLayoutActivity.class));
                        break;
                    case 22://标签控件
                        startActivity(new Intent(MainActivity.this, LabelActivity.class));
                        break;
                    case 23://跑马灯及ViewFlipper
                        startActivity(new Intent(MainActivity.this,MarqueeActivity.class));
                        break;
                    case 24://权限申请封装
                        startActivity(new Intent(MainActivity.this,PermissionRequestActivity.class));
                        break;
                    case 25://侧滑SideBar
                        startActivity(new Intent(MainActivity.this,SideBarActivity.class));
                        break;
                }
            }
        });
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(contentAdapter);
    }
}
