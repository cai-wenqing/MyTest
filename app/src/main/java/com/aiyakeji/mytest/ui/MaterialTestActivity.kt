package com.aiyakeji.mytest.ui

import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Pair
import android.view.View
import android.view.ViewAnimationUtils
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.aiyakeji.mytest.R

/**
 * Created by Administrator on 2018/1/18 0018.
 * MaterialDesign测试
 */

class MaterialTestActivity : AppCompatActivity(), View.OnClickListener {
    private var btn_shareanimal: Button? = null
    private var iv_share: ImageView? = null
    private var tv_share: TextView? = null
    private var iv_oval: ImageView? = null
    private var iv_rect: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_materialtest)

        btn_shareanimal = findViewById<View>(R.id.material_btn_shareanimal) as Button
        iv_share = findViewById<View>(R.id.material_iv_share) as ImageView
        tv_share = findViewById<View>(R.id.material_tv_share) as TextView
        iv_oval = findViewById<View>(R.id.material_iv_oval) as ImageView
        iv_rect = findViewById<View>(R.id.material_iv_rect) as ImageView

        btn_shareanimal!!.setOnClickListener(this)
        iv_share!!.setOnClickListener(this)
        iv_oval!!.setOnClickListener(this)
        iv_rect!!.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.material_btn_shareanimal -> {
//                val builder = AlertDialog.Builder(this)
//                builder.setTitle("提示").setMessage("测试dialog")
//                        .setPositiveButton("确定") { dialogInterface, i -> dialogInterface.dismiss() }
//                        .setNegativeButton("取消") { dialogInterface, i -> dialogInterface.dismiss() }.create().show()
            }
            R.id.material_iv_share//共享图片
            -> if (Build.VERSION.SDK_INT >= 21)
                startActivity(Intent(this, MaterialTestBActivity::class.java),
                        ActivityOptions.makeSceneTransitionAnimation(this,
                                Pair<View, String>(iv_share, "MaterialShare_iv"),
                                Pair<View, String>(tv_share, "MaterialShare_tv")).toBundle())
            else
                startActivity(Intent(this, MaterialTestBActivity::class.java))

            R.id.material_iv_oval
            -> if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                val animator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ViewAnimationUtils.createCircularReveal(
                            iv_oval,
                            iv_oval!!.width / 2,
                            iv_oval!!.height / 2,
                            0f,
                            iv_oval!!.width.toFloat())
                } else {
                    TODO("VERSION.SDK_INT < LOLLIPOP")
                }
                animator.interpolator = AccelerateDecelerateInterpolator()
                animator.duration = 2000
                animator.start()
            }

            R.id.material_iv_rect//矩形
            -> if (Build.VERSION.SDK_INT >= 21) {
                val animator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ViewAnimationUtils.createCircularReveal(
                            iv_rect,
                            0,
                            0,
                            0f,
                            Math.hypot(iv_rect!!.width.toDouble(), iv_rect!!.height.toDouble()).toFloat())
                } else {
                    TODO("VERSION.SDK_INT < LOLLIPOP")
                }
                animator.interpolator = AccelerateDecelerateInterpolator()
                animator.duration = 2000
                animator.start()
            }
        }
    }
}
