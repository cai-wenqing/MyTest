package com.aiyakeji.mytest.ui

import android.Manifest
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.aiyakeji.mytest.R
import com.aiyakeji.mytest.utils.AudioCaptureControl
import com.aiyakeji.mytest.utils.PermissionHelper

/**
 * @Author:CWQ
 * @DATE:2022/8/24
 * @DESC:应用音频捕获
 */
class AudioPlaybackCaptureActivity : AppCompatActivity() {
    private val TAG = "AudioPlaybackCapture"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_audio_playback_capture)

        checkPermission()
        AudioCaptureControl.initAudioCapture(this)

        findViewById<Button>(R.id.btn_start).setOnClickListener {
//            AudioCaptureControl.startRecording()
        }
    }


    private fun checkPermission() {
        val granted = PermissionHelper.checkPermission(this, Manifest.permission.RECORD_AUDIO)
        if (granted) {
            AudioCaptureControl.initAudioCapture(this)
        } else {
            requestPermission()
        }
    }

    private fun requestPermission() {
        PermissionHelper.init(this).requestEachPermissions(
            arrayOf(Manifest.permission.RECORD_AUDIO)
        ) { permission ->
            if (permission.isGranted) {
                AudioCaptureControl.initAudioCapture(this)
            } else {
                if (permission.isShouldShowRequestPermissionRationale) {
                    Toast.makeText(
                        this,
                        "权限拒绝，可以再次申请~",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                } else {
                    Toast.makeText(
                        this,
                        "权限拒绝，不再询问~",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            }
        }
    }
}