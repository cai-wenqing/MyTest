package com.aiyakeji.mytest.ui

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.aiyakeji.mytest.R
import com.aiyakeji.mytest.utils.TTSUtils
import com.aiyakeji.mytest.widgets.WaveProgressView

/**
 * @Author:CWQ
 * @DATE:2023/2/15
 * @DESC:
 */
class WaveProgressActivity : AppCompatActivity() {

    private lateinit var ttsUtils: TTSUtils

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wave_progress)

        val waveProgressView = findViewById<WaveProgressView>(R.id.wpv)
        waveProgressView.startAnim()

        ttsUtils = TTSUtils(this)

        findViewById<Button>(R.id.btn_tts).setOnClickListener {
            ttsUtils.speak("今天天气怎么样？")
        }
        findViewById<Button>(R.id.btn_bell).setOnClickListener {
            ttsUtils.playEarcon()
        }
    }
}