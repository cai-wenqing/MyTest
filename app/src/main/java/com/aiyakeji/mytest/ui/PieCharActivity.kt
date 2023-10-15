package com.aiyakeji.mytest.ui

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.aiyakeji.mytest.R
import com.aiyakeji.mytest.bean.PieData
import com.aiyakeji.mytest.widgets.PieChart

/**
 * @Author:CWQ
 * @DATE:2023/7/4
 * @DESC:
 */
class PieCharActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pie_chart)

        initView()
    }

    private fun initView() {
        val pieChart = findViewById<PieChart>(R.id.pie_chart)
        val list = listOf(
            PieData(3f, Color.RED),
            PieData(5f, Color.GREEN),
            PieData(9f, Color.BLUE),
            PieData(6f, Color.BLACK),
            PieData(14f, Color.YELLOW),
            PieData(10f, Color.GRAY),
            PieData(20f, Color.MAGENTA),
            PieData(12f, Color.DKGRAY)
        )
        pieChart.setData(list)
    }
}