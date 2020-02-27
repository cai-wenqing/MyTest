package com.aiyakeji.mytest.adapters

import android.support.v4.view.PagerAdapter
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.aiyakeji.mytest.R
import com.aiyakeji.mytest.photoview.OnViewScrollListener
import com.aiyakeji.mytest.photoview.PhotoView
import com.bumptech.glide.Glide
import kotlin.math.abs

/**
 * @author CWQ
 * @date 2020-02-25
 */
class BrowsePictureAdapter(private val imgs: ArrayList<String>, private val listener: OnPhotoMotionListener?) : PagerAdapter() {

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = LayoutInflater.from(container.context).inflate(R.layout.item_adapter_borwse_picture, null)
        val imageView = view.findViewById<PhotoView>(R.id.iv_pic)
        Glide.with(container.context).load(imgs[position]).into(imageView)
        view.setOnClickListener {
            listener?.onPhotoClick()
        }
        imageView.setOnPhotoTapListener { view, x, y ->
            listener?.onPhotoClick()
        }
        imageView.setOnViewScrollListener(object : OnViewScrollListener {
            override fun onScroll(x: Float, y: Float) {
                Log.e("adapter", "x:$x,y:$y")
                if (imageView.scale <= 1.01f && abs(y) > 60) {
                    listener?.onScroll(x, y)
                }
            }

            override fun onScrollFinish() {
                listener?.onScrollFinish()
            }
        })
        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun getCount() = imgs.size

    override fun isViewFromObject(view: View, `object`: Any) = view == `object`


    interface OnPhotoMotionListener {

        fun onPhotoClick()

        fun onScroll(x: Float, y: Float)

        fun onScrollFinish();
    }
}