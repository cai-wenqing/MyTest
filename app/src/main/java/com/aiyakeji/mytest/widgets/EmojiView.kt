package com.aiyakeji.mytest.widgets

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.aiyakeji.mytest.R
import com.aiyakeji.mytest.adapters.EmojiSymbolItemAdapter
import com.aiyakeji.mytest.adapters.EmojiSymbolSpan
import com.aiyakeji.mytest.utils.EmojiUtils
import com.sinovoice.hci.adapter.EmojiPageAdapter

/**
 * @Author:CWQ
 * @DATE:2022/12/29
 * @DESC:
 */
class EmojiView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr), OnClickListener {
    private val TAG = EmojiView::class.java.simpleName

    private var screenWidth = 0
    private lateinit var mRoot: View
    private lateinit var mViewPage: ViewPager
    private lateinit var mTvWeChatEmoji: TextView
    private lateinit var mTvQQEmoji: TextView
    private lateinit var mTvSymbolEmoji:TextView
    private lateinit var mIvDelete: ImageView
    private var mCurPageIndex = 0
    private var mEmojiListener: OnEmojiListener? = null

    init {
        initView()
        initData()
    }

    private fun initView() {
        mRoot = LayoutInflater.from(context).inflate(R.layout.emoji_view, this)
        mViewPage = mRoot.findViewById(R.id.view_page)
        mTvWeChatEmoji = mRoot.findViewById(R.id.tv_wechat_emoji)
        mTvQQEmoji = mRoot.findViewById(R.id.tv_qq_emoji)
        mTvSymbolEmoji = mRoot.findViewById(R.id.tv_symbol_emoji)
        mIvDelete = mRoot.findViewById(R.id.iv_delete)
        mTvWeChatEmoji.setOnClickListener(this)
        mTvQQEmoji.setOnClickListener(this)
        mIvDelete.setOnClickListener(this)
        mTvSymbolEmoji.setOnClickListener(this)
        screenWidth = resources.displayMetrics.widthPixels
    }

    fun setOnEmojiListener(listener: OnEmojiListener) {
        mEmojiListener = listener
    }


    private fun initData() {
        val pageAdapter = EmojiPageAdapter(getPagers())
        mViewPage.adapter = pageAdapter
        mViewPage.addOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                mCurPageIndex = position
                if (position == 0) {
                    mTvWeChatEmoji.isSelected = true
                    mTvQQEmoji.isSelected = false
                } else {
                    mTvWeChatEmoji.isSelected = false
                    mTvQQEmoji.isSelected = true
                }
            }

            override fun onPageScrollStateChanged(state: Int) {
            }

        })
    }

    private fun getPagers(): List<View> {
        val list = arrayListOf<View>()
        list.add(getListView(0))
        list.add(getListView(1))
        list.add(getListView(2))
        return list
    }

    private fun getListView(pageIndex: Int): View {
        val containerView = LayoutInflater.from(context).inflate(R.layout.layout_emoji_page, null)
        val rv = containerView.findViewById<RecyclerView>(R.id.rv_emoji)
        if (pageIndex == 2) {//颜文字
            val layoutManager = GridLayoutManager(context, 4)
            val fontSize = resources.getDimension(R.dimen.symbol_text_size)
            val symbolSpan = EmojiSymbolSpan(EmojiUtils.symbolList, screenWidth, fontSize)
            layoutManager.spanSizeLookup = symbolSpan
            rv.layoutManager = layoutManager
        } else {
            rv.layoutManager = GridLayoutManager(context, 7)
        }
        val adapter = EmojiSymbolItemAdapter(EmojiUtils.getEmojiList(pageIndex))
        adapter.setOnItemListener {
            Log.d(TAG, "getListView: symbol:$it")
            mEmojiListener?.onClickEmoji(it)
        }
        rv.adapter = adapter

        return containerView
    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_wechat_emoji -> {
                if (mCurPageIndex != 0) {
                    mViewPage.setCurrentItem(0, true)
                }
            }
            R.id.tv_qq_emoji -> {
                if (mCurPageIndex != 1) {
                    mViewPage.setCurrentItem(1, true)
                }
            }
            R.id.tv_symbol_emoji -> {
                if (mCurPageIndex != 2) {
                    mViewPage.setCurrentItem(2, true)
                }
            }
            R.id.iv_delete -> {
                mEmojiListener?.onDelete()
            }
        }
    }


    interface OnEmojiListener {

        /**
         * 点击emoji表情回调
         * @param emojiType Int emoji类型 0图片 1颜文字
         * @param imgResId Int? emoji图片资源ID
         * @param symbol String 颜文字文本
         */
        fun onClickEmoji(symbol:String)

        fun onDelete()
    }
}