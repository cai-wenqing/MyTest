package com.aiyakeji.mytest.wallpaper

import android.media.MediaPlayer
import android.os.Bundle
import android.service.wallpaper.WallpaperService
import android.view.SurfaceHolder
import com.aiyakeji.mytest.R

/**
 * @Author:CWQ
 * @DATE:2022/9/1
 * @DESC:
 */
class VideoWallpaperService : WallpaperService() {

    override fun onCreateEngine(): Engine {
        return VideoEngine()
    }

    inner class VideoEngine : WallpaperService.Engine() {
        private lateinit var mediaPlayer: MediaPlayer

        override fun onSurfaceCreated(holder: SurfaceHolder?) {
            super.onSurfaceCreated(holder)
            mediaPlayer = MediaPlayer.create(applicationContext, R.raw.test).also {
                it.setSurface(holder!!.surface)
                it.isLooping = true
            }
        }

        override fun onCommand(
            action: String?,
            x: Int,
            y: Int,
            z: Int,
            extras: Bundle?,
            resultRequested: Boolean
        ): Bundle {
            return super.onCommand(action, x, y, z, extras, resultRequested)
        }

        override fun onVisibilityChanged(visible: Boolean) {
            if (visible) {
                mediaPlayer.start()
            } else {
                mediaPlayer.pause()
            }
        }

        override fun onDestroy() {
            super.onDestroy()
            if (mediaPlayer.isPlaying) {
                mediaPlayer.stop()
            }
            mediaPlayer.release()
        }
    }
}