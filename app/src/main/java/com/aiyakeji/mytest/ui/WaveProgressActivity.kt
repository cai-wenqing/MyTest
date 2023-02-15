package com.aiyakeji.mytest.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.aiyakeji.mytest.R
import com.aiyakeji.mytest.widgets.WaveProgressView

/**
 * @Author:CWQ
 * @DATE:2023/2/15
 * @DESC:
 */
class WaveProgressActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wave_progress)

        val waveProgressView = findViewById<WaveProgressView>(R.id.wpv)
        waveProgressView.startAnim()
    }
}