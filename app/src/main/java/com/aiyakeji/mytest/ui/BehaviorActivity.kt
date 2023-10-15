package com.aiyakeji.mytest.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.aiyakeji.mytest.adapters.SlidingCardAdapter
import com.aiyakeji.mytest.behavior.OrderStatusBehavior
import com.aiyakeji.mytest.databinding.ActivityBehaviorBinding

/**
 * @Author:CWQ
 * @DATE:2023/7/25
 * @DESC:
 */
class BehaviorActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBehaviorBinding

    private val strings = listOf(
        "盖伦", "光辉", "火男", "男枪", "提莫", "加里奥", "奥巴马",
        "炼金", "猪女", "蜘蛛", "皇子", "赵信", "盲僧", "蛮王"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBehaviorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    private fun initView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = SlidingCardAdapter(this, strings)

        // 这个就是最大的PEEK
        binding.recyclerView.translationY = OrderStatusBehavior.MAX_PEEK
        val behavior = OrderStatusBehavior(this)
        behavior.listener = object : OrderStatusBehavior.OrderStatusListener {
            // 这里就是TitleBar和header的互动
            private val AIM_PERCENT = 0.7f

            override fun onHeaderMove(percent: Float, title: String) {
                // 这个监听顾名思义一下，header的移动程度，通过percent表示，上推过程中percent逐渐变大到1，下滑最小到固定时为0

                // 这里就是TitleBar中何时显示文字了，这里的阈值判断是header移动到70%
                if (percent >= AIM_PERCENT && binding.titleBar.text.isEmpty()) {
                    binding.titleBar.text = title
                } else if (percent < AIM_PERCENT && binding.titleBar.text.isNotEmpty()) {
                    binding.titleBar.text = ""
                }
                binding.titleBar.alpha = percent
            }
        }
        // 这里绑定behavior，当然xml中也是一样可以绑定的(原理：根据路径反射实例化并绑定)
        (binding.orderStatusLine.layoutParams as? CoordinatorLayout.LayoutParams)?.behavior =
            behavior
    }
}