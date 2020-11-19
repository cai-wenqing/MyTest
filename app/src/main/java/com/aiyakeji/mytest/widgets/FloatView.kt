package com.aiyakeji.mytest.widgets

import android.app.Service
import android.content.Context
import android.graphics.Color
import android.graphics.PixelFormat
import android.os.Build
import android.view.Gravity
import android.view.WindowManager
import android.widget.TextView

/**
 * @author CWQ
 * @date 11/17/20
 */
class FloatView(val context: Context) {

    fun showFloatWindow(){
        val windowManager = context.getSystemService(Service.WINDOW_SERVICE) as WindowManager

        val textView = TextView(context)
        textView.text = "悬浮窗口\n萨瓦迪卡"
        textView.setTextColor(Color.WHITE)
        textView.setBackgroundColor(Color.BLUE)
        textView.setOnClickListener {
            windowManager.removeView(textView)
        }

        val layoutParams = WindowManager.LayoutParams()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            layoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        }else{
            layoutParams.type = WindowManager.LayoutParams.TYPE_PHONE
        }
        val type = WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM and WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE and WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
        layoutParams.flags = type
        layoutParams.gravity = (Gravity.LEFT and Gravity.TOP)
        layoutParams.format = PixelFormat.TRANSLUCENT
        layoutParams.width = 500
        layoutParams.height = 200
        layoutParams.x = 300
        layoutParams.y = 300

        windowManager.addView(textView,layoutParams)
    }
}