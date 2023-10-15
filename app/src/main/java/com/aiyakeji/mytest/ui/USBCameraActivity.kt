package com.aiyakeji.mytest.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.aiyakeji.mytest.R
import com.aiyakeji.mytest.ui.fragments.USBCameraFragment
import com.jiangdg.ausbc.MultiCameraClient

/**
 * @Author:CWQ
 * @DATE:2023/5/29
 * @DESC:测试使用USB摄像头
 */
class USBCameraActivity : AppCompatActivity() {

    private var permissions =
        arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_usb_camera)

        jumpWithPermission(permissions)

//        initUSBCameraInfo()
    }


    private fun jumpWithPermission(permissions: Array<String>) {
        if (permissions.all {
                ContextCompat.checkSelfPermission(
                    this,
                    it
                ) == PackageManager.PERMISSION_GRANTED
            }) {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, USBCameraFragment())
            transaction.commitAllowingStateLoss()
        } else {
            permissionLauncher.launch(permissions)
        }
    }


    private val permissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            var permissionGranted = true
            permissions.entries.forEach {
                if (it.key in permissions && !it.value) {
                    permissionGranted = false
                }
            }
            if (permissionGranted) {
                val transaction = supportFragmentManager.beginTransaction()
                transaction.replace(R.id.fragment_container, USBCameraFragment())
                transaction.commitAllowingStateLoss()
            } else {
                Toast.makeText(this, "请先授权！", Toast.LENGTH_SHORT).show()
            }
        }


    /**
     * 获取USB摄像头信息
     */
    private fun initUSBCameraInfo() {
        val multiCameraClient = MultiCameraClient(this, null)
        multiCameraClient.register()
        multiCameraClient.requestPermission(null)
        val deviceList = multiCameraClient.getDeviceList()
        Log.d("USBCameraActivity", "initUSBCameraInfo: deviceList:$deviceList")
    }
}