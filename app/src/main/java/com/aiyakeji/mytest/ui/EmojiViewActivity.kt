package com.aiyakeji.mytest.ui

import android.os.Bundle
import android.text.TextUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.aiyakeji.mytest.R
import com.aiyakeji.mytest.widgets.EmojiView

/**
 * @Author:CWQ
 * @DATE:2023/1/5
 * @DESC:
 */
class EmojiViewActivity : AppCompatActivity() {

    lateinit var mEmojiView: EmojiView
    lateinit var mIvShow: ImageView
    lateinit var mTvSymbol:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_emoji_view)

        mIvShow = findViewById(R.id.iv_show)
        mEmojiView = findViewById(R.id.emoji_view)
        mTvSymbol = findViewById(R.id.tv_symbol)

        mEmojiView.setOnEmojiListener(object : EmojiView.OnEmojiListener {
            override fun onClickEmoji(emojiType: Int, imgResId: Int?, symbol: String?) {
                if (emojiType == 0 && imgResId != null) {
                    mIvShow.setImageResource(imgResId)
                } else if (emojiType == 1 && !TextUtils.isEmpty(symbol)) {
                    mTvSymbol.text = symbol
                }
            }


            override fun onDelete() {
                mIvShow.setImageResource(0)
                mTvSymbol.text = null
            }

        })
    }
}