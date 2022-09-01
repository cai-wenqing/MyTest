package com.aiyakeji.mytest.wallpaper

import android.hardware.Camera
import android.service.wallpaper.WallpaperService
import android.view.SurfaceHolder
import java.io.IOException

/**
 * @Author:CWQ
 * @DATE:2022/9/1
 * @DESC:
 */
class CameraWallpaperService : WallpaperService() {
    override fun onCreateEngine(): Engine {
        return CameraEngine()
    }


    inner class CameraEngine : WallpaperService.Engine() {
        private lateinit var camera: Camera

        override fun onCreate(surfaceHolder: SurfaceHolder?) {
            super.onCreate(surfaceHolder)
        }

        override fun onVisibilityChanged(visible: Boolean) {
            if (visible) {
                startPreview()
            } else {
                stopPreview()
            }
        }


        override fun onDestroy() {
            super.onDestroy()
            stopPreview()
        }

        private fun startPreview() {
            camera = Camera.open()
            camera.setDisplayOrientation(90)
            try {
                camera.setPreviewDisplay(surfaceHolder)
                camera.startPreview()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

        private fun stopPreview() {
            try {
                camera.stopPreview()
                camera.release()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}