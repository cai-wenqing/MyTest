package com.aiyakeji.mytest.ui

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.aiyakeji.mytest.R
import com.aiyakeji.mytest.widgets.VolumeView

/**
 * @Author:CWQ
 * @DATE:2022/7/27
 * @DESC:
 */
class VolumeViewActivity:AppCompatActivity() {

    private lateinit var volumeView: VolumeView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_volume_view)

        volumeView = findViewById(R.id.volume_view)

        findViewById<Button>(R.id.btn_start).setOnClickListener {
            volumeView.startAnim()
        }

        findViewById<Button>(R.id.btn_stop).setOnClickListener {
            volumeView.stop()
        }
    }
}