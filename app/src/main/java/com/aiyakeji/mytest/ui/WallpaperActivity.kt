package com.aiyakeji.mytest.ui

import android.Manifest
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.aiyakeji.mytest.R
import com.aiyakeji.mytest.utils.PermissionHelper
import com.aiyakeji.mytest.wallpaper.*

/**
 * @Author:CWQ
 * @DATE:2022/9/1
 * @DESC:壁纸设置
 */
class WallpaperActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wallpaper)

        val pictureLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                it.data?.data?.run {
                    val bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(this))
                    WallpaperUtil.setPictureWallpaper(this@WallpaperActivity, bitmap)
                    Toast.makeText(this@WallpaperActivity, "设置成功！", Toast.LENGTH_SHORT).show()
                }
            }

        findViewById<Button>(R.id.btn_picture).setOnClickListener {
            //静态壁纸
            val intent =
                Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI).apply {
                    setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")
                }
            pictureLauncher.launch(intent)
        }

        findViewById<Button>(R.id.btn_video).setOnClickListener {
            //动态壁纸
            WallpaperUtil.setDynamicWallpaper(this, VideoWallpaperService::class.java.name)
        }

        findViewById<Button>(R.id.btn_camera).setOnClickListener {
            if (PermissionHelper.checkPermission(this, Manifest.permission.CAMERA)) {
                WallpaperUtil.setDynamicWallpaper(this, CameraWallpaperService::class.java.name)
            } else {
                PermissionHelper.init(this)
                    .requestPermissions(arrayOf(Manifest.permission.CAMERA)) {
                        if (it) {
                            WallpaperUtil.setDynamicWallpaper(
                                this,
                                CameraWallpaperService::class.java.name
                            )
                        } else {
                            Toast.makeText(this, "请允许权限", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }

        findViewById<Button>(R.id.btn_canvas).setOnClickListener {
            //canvas绘画壁纸
            WallpaperUtil.setDynamicWallpaper(this, DrawWallpaperService::class.java.name)
        }
    }
}