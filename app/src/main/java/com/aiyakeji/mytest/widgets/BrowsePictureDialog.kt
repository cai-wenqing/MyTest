package com.aiyakeji.mytest.widgets

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import androidx.viewpager.widget.ViewPager
import com.aiyakeji.mytest.R
import com.aiyakeji.mytest.adapters.BrowsePictureAdapter
import com.aiyakeji.mytest.photoview.PhotoUtil
import com.aiyakeji.mytest.ui.PARAMS_IMGS
import com.aiyakeji.mytest.ui.PARAMS_INDEX
import kotlin.math.abs

/**
 * @Author:CWQ
 * @DATE:2022/11/2
 * @DESC:
 */
class BrowsePictureDialog : DialogFragment(), ViewPager.OnPageChangeListener {

    private val PARAMS_IMGS = "imgs"
    private val PARAMS_INDEX = "index"

    private lateinit var imgs: java.util.ArrayList<String>
    private var mCurrentIndex = 0
    private var mAdapter: BrowsePictureAdapter? = null
    private var viewPager: ViewPager? = null

    companion object {

        fun newInstance(imgs: ArrayList<String>, index: Int = 0): BrowsePictureDialog {
            val args = Bundle().apply {
                putStringArrayList(PARAMS_IMGS, imgs)
                putInt(PARAMS_INDEX, index)
            }
            return BrowsePictureDialog().apply {
                arguments = args
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_borwse_picture, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        initView()
    }

    override fun onStart() {
        super.onStart()
        initParams()
    }

    private fun initParams() {
        dialog?.apply {
            window?.apply {
                attributes.run {
                    width = WindowManager.LayoutParams.MATCH_PARENT
                    height = WindowManager.LayoutParams.WRAP_CONTENT
                    gravity = Gravity.CENTER
                }
                setCancelable(true)
                setCanceledOnTouchOutside(true)
                setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            }
        }
    }

    private fun initData() {
        imgs = arguments?.getStringArrayList(PARAMS_IMGS) ?: java.util.ArrayList()
        mCurrentIndex = arguments?.getInt(PARAMS_INDEX, 0) ?: 0
        mAdapter = BrowsePictureAdapter(imgs, object : BrowsePictureAdapter.OnPhotoMotionListener {

            override fun onPhotoClick() {
                dismiss()
            }

            override fun onScroll(x: Float, y: Float) {
                startDrag(x, y)
            }

            override fun onScrollFinish() {
                viewPager?.translationX = 0f
                viewPager?.translationY = 0f
                viewPager?.scaleX = 1f
                viewPager?.scaleY = 1f
            }
        })
    }

    private fun initView() {
        viewPager = view?.findViewById(R.id.viewPager)
        viewPager?.adapter = mAdapter
        viewPager?.currentItem = mCurrentIndex
        viewPager?.addOnPageChangeListener(this)
    }


    private fun startDrag(x: Float, y: Float) {
        viewPager?.translationX = x
        viewPager?.translationY = y
        if (y > 0) {
            viewPager?.pivotX = PhotoUtil.getWindowWidth(requireContext()) / 2f
            viewPager?.pivotY = PhotoUtil.getWindowHeight(requireContext()) / 2f
            val scale: Float = abs(y) / PhotoUtil.getWindowHeight(requireContext())
            if (scale < 1 && scale > 0) {
                viewPager?.scaleX = 1 - scale
                viewPager?.scaleY = 1 - scale
            }
        }
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

    }

    override fun onPageSelected(position: Int) {
        mCurrentIndex = position
    }

    override fun onPageScrollStateChanged(state: Int) {
    }
}