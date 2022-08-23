package com.aiyakeji.mytest.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aiyakeji.mytest.R;
import com.aiyakeji.mytest.adapters.MainAdapter;
import com.aiyakeji.mytest.listener.OnClickItemListener;
import com.aiyakeji.mytest.widgets.FloatView;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    private String[] itemArr = {"折叠头部", "属性动画", "仿QQ步数圆形进度", "仿华为时钟",
            "滚动选择器", "手势解锁", "圆盘", "万能适配器", "滑动卡片", "语音动态", "波动圆", "轮播图",
            "声音波", "material design", "注解", "代理模式", "日历",
            "日历列表", "动态脚布局", "动态高度layout", "滑动指示器", "标签控件", "跑马灯及ViewFlipper",
            "权限申请封装", "侧滑SideBar", "档位选择", "阴影布局", "Scroller测试", "底部弹窗", "SnapHelper", "奖金进度条",
            "查看大图", "九宫格","粒子","黑客帝国","放大镜"};

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
                    case 9://语音动态view
                        startActivity(new Intent(MainActivity.this, VolumeViewActivity.class));
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
                    case 16://日历
                        startActivity(new Intent(MainActivity.this, CalendarActivity.class));
                        break;
                    case 17://日历列表
                        startActivity(new Intent(MainActivity.this, CalendarListActivity.class));
                        break;
                    case 18://动态脚布局
                        startActivity(new Intent(MainActivity.this, FootViewActivity.class));
                        break;
                    case 19://动态高度layout
                        startActivity(new Intent(MainActivity.this, MaxHeightLayoutActivity.class));
                        break;
                    case 20://滑动指示器

                        break;
                    case 21://标签控件
                        startActivity(new Intent(MainActivity.this, LabelActivity.class));
                        break;
                    case 22://跑马灯及ViewFlipper
                        startActivity(new Intent(MainActivity.this, MarqueeActivity.class));
                        break;
                    case 23://权限申请封装
                        startActivity(new Intent(MainActivity.this, PermissionRequestActivity.class));
                        break;
                    case 24://侧滑SideBar
                        startActivity(new Intent(MainActivity.this, SideBarActivity.class));
                        break;
                    case 25://档位选择
                        startActivity(new Intent(MainActivity.this, StepSlideActivity.class));
                        break;
                    case 26://阴影布局
                        startActivity(new Intent(MainActivity.this, ShadowLayoutTestActivity.class));
                        break;
                    case 27://Scroller测试
                        startActivity(new Intent(MainActivity.this, HorizontalScrollTestActivity.class));
                        break;
                    case 28://底部弹窗
                        startActivity(new Intent(MainActivity.this, BottomSheetActivity.class));
                        break;
                    case 29://SnapHelper
                        startActivity(new Intent(MainActivity.this, SnapHelperActivity.class));
                        break;
                    case 30://奖金进度条
                        startActivity(new Intent(MainActivity.this, RewardProgressActivity.class));
                        break;
                    case 31://查看大图
                        startActivity(new Intent(MainActivity.this, BrowsePictureTestActivity.class));
                        break;
                    case 32://九宫格
                        startActivity(new Intent(MainActivity.this, NineViewActivity.class));
                        break;
                    case 33://粒子
                        startActivity(new Intent(MainActivity.this,ParticleActivity.class));
                        break;
                    case 34://黑客帝国
                        startActivity(new Intent(MainActivity.this,HackerActivity.class));
                        break;
                    case 35://放大镜
                        startActivity(new Intent(MainActivity.this,MagnifierActivity.class));
                        break;
                }
            }
        });
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(contentAdapter);
    }


    private void startFloatService(){
        if (!Settings.canDrawOverlays(this)){
            Toast.makeText(this, "当前无权限，请授权", Toast.LENGTH_SHORT).show();
            startActivityForResult(new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName())), 0);
        }else {
            new FloatView(this).showFloatWindow();
        }
    }
}
