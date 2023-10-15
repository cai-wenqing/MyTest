package com.aiyakeji.mytest.ui

import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.aiyakeji.mytest.R
import com.aiyakeji.mytest.utils.EmojiUtils
import com.aiyakeji.mytest.widgets.EmojiView


/**
 * @Author:CWQ
 * @DATE:2023/1/5
 * @DESC:
 */
class EmojiViewActivity : AppCompatActivity() {
    private val TAG = "EmojiViewActivity"

    lateinit var mEmojiView: EmojiView
    lateinit var mTvSymbol: TextView
    lateinit var mEtInput: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_emoji_view)

        initView()
    }


    private fun initView() {
        mEmojiView = findViewById(R.id.emoji_view)
        mTvSymbol = findViewById(R.id.tv_symbol)
        mEtInput = findViewById(R.id.et_input)

        mEmojiView.setOnEmojiListener(object : EmojiView.OnEmojiListener {
            override fun onClickEmoji(symbol: String) {
                mTvSymbol.text = symbol
            }


            override fun onDelete() {
                mTvSymbol.text = null
            }
        })

        mEtInput.addTextChangedListener {
            val unicodeStr = EmojiUtils.stringToUnicode1(it.toString())
            Log.d(TAG, "inputContent:${it},unicode:$unicodeStr")
        }
    }
}