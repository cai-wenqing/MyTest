package com.aiyakeji.mytest.ui

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.aiyakeji.mytest.R

/**
 * Created by Administrator on 2018/1/22 0022.
 * kotlin test
 */
class KotlinTestActivity : AppCompatActivity(), View.OnClickListener {
    var btn_1: Button? = null
    var btn_2: Button? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlintest)

        btn_1 = findViewById(R.id.kotlin_btn_click1)
        btn_2 = findViewById(R.id.kotlin_btn_click2)

        btn_1?.setOnClickListener(this)
        btn_2?.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.kotlin_btn_click1 ->
                Toast.makeText(this, "点击按钮1", Toast.LENGTH_SHORT).show()
            R.id.kotlin_btn_click2 ->
                AlertDialog.Builder(this).setTitle("提示：").setMessage("点击了按钮2")
                        .setPositiveButton("确定") { dialogInterface, i -> dialogInterface.dismiss() }
                        .setNegativeButton("取消"){dialogInterface, i -> dialogInterface.dismiss() }
                        .create().show()
        }
    }
}