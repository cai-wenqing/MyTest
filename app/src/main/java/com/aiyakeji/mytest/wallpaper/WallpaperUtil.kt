package com.aiyakeji.mytest.wallpaper

import android.app.WallpaperManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import com.aiyakeji.mytest.R

/**
 * @Author:CWQ
 * @DATE:2022/9/1
 * @DESC:
 */
object WallpaperUtil {

    /**
     * 设置静态壁纸
     * @param context Context
     * @param id 图片资源ID
     */
    fun setPictureWallpaper(context: Context, @DrawableRes id: Int) {
        val wallpaperManager = WallpaperManager.getInstance(context)
        try {
            val bitmap = ContextCompat.getDrawable(context, id)?.toBitmap()
            wallpaperManager.setBitmap(bitmap)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 设置静态壁纸
     * @param context Context
     * @param bitmap Bitmap
     */
    fun setPictureWallpaper(context: Context, bitmap: Bitmap) {
        val wallpaperManager = WallpaperManager.getInstance(context)
        try {
            wallpaperManager.setBitmap(bitmap)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 设置动态壁纸
     * @param context Context
     */
    fun setDynamicWallpaper(context: Context,serviceClazzName:String) {
        val localIntent = Intent(WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER).apply {
            putExtra(
                WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT,
                ComponentName(
                    context.applicationContext.packageName,
                    serviceClazzName
                )
            )
        }
        context.startActivity(localIntent)
    }
}