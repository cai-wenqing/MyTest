package com.aiyakeji.mytest.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.WindowManager
import android.widget.TextView
import androidx.viewpager.widget.ViewPager
import com.aiyakeji.mytest.R
import com.aiyakeji.mytest.adapters.BrowsePictureAdapter
import com.aiyakeji.mytest.photoview.PhotoUtil
import kotlin.math.abs


/**
 * @author CWQ
 * @date 2020-02-25
 * 图片查看器
 */

const val PARAMS_IMGS = "imgs"
const val PARAMS_INDEX = "index"


class BrowsePictureActivity : AppCompatActivity(), ViewPager.OnPageChangeListener {

    private lateinit var imgs: java.util.ArrayList<String>
    private var mCurrentIndex = 0
    private var mAdapter: BrowsePictureAdapter? = null
    private lateinit var viewPager: ViewPager
    private lateinit var tv_index: TextView

    companion object {

        fun start(context: Context, imgs: ArrayList<String>, index: Int) {
            val intent = Intent(context, BrowsePictureActivity::class.java)
            intent.putExtra(PARAMS_IMGS, imgs)
            intent.putExtra(PARAMS_INDEX, index)
            context.startActivity(intent)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val winParams: WindowManager.LayoutParams = window.attributes
        val bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
        winParams.flags = winParams.flags or bits
        window.attributes = winParams
        setContentView(R.layout.activity_borwse_picture)
        initData()
        initView()
    }


    private fun initData() {
        imgs = intent.getStringArrayListExtra(PARAMS_IMGS) ?: java.util.ArrayList()
        mCurrentIndex = intent.getIntExtra(PARAMS_INDEX, 0)
        mAdapter = BrowsePictureAdapter(imgs, object : BrowsePictureAdapter.OnPhotoMotionListener {

            override fun onPhotoClick() {
                finish()
            }

            override fun onScroll(x: Float, y: Float) {
                startDrag(x, y)
            }

            override fun onScrollFinish() {
                viewPager.translationX = 0f
                viewPager.translationY = 0f
                viewPager.scaleX = 1f
                viewPager.scaleY = 1f

//                if(viewPager.scaleX > 0.7f) {
//                    viewPager.translationX = 0f
//                    viewPager.translationY = 0f
//                    viewPager.scaleX = 1f
//                    viewPager.scaleY = 1f
//                }else{
//                    finish()
//                }
            }
        })
    }

    private fun initView() {
        tv_index = findViewById(R.id.tv_index)
        viewPager = findViewById(R.id.viewPager)
        viewPager.adapter = mAdapter
        viewPager.currentItem = mCurrentIndex
        viewPager.addOnPageChangeListener(this)
        setPage()
    }


    private fun startDrag(x: Float, y: Float) {
        viewPager.translationX = x
        viewPager.translationY = y
        if (y > 0) {
            viewPager.pivotX = PhotoUtil.getWindowWidth(this) / 2f
            viewPager.pivotY = PhotoUtil.getWindowHeight(this) / 2f
            val scale: Float = abs(y) / PhotoUtil.getWindowHeight(this)
            if (scale < 1 && scale > 0) {
                viewPager.scaleX = 1 - scale
                viewPager.scaleY = 1 - scale
            }
        }
    }


    /**
     * 设置当前页码
     */
    private fun setPage() {
        tv_index.text = "${mCurrentIndex + 1}/${imgs.size}"
    }


    override fun onPageScrollStateChanged(state: Int) {

    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
    }

    override fun onPageSelected(position: Int) {
        mCurrentIndex = position
        setPage()
    }
}