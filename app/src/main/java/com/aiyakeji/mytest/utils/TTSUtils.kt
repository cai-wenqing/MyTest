package com.aiyakeji.mytest.utils

import android.content.Context
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.text.TextUtils
import android.util.Log
import com.aiyakeji.mytest.R

/**
 * @Author:CWQ
 * @DATE:2023/2/16
 * @DESC:
 */
class TTSUtils(private val context: Context) {
    private val TAG = "TTSUtils"

    private var earconName = ""

    private val tts: TextToSpeech = TextToSpeech(context) { status ->
        Log.d(TAG, "init status: $status")
    }

    init {
        tts.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
            override fun onStart(utteranceId: String?) {
                Log.d(TAG, "onStart: $utteranceId")
            }

            override fun onDone(utteranceId: String?) {
                Log.d(TAG, "onDone: $utteranceId")
            }

            override fun onError(utteranceId: String?) {
                Log.d(TAG, "onError: $utteranceId")
            }

        })
    }


    fun speak(text: String) {
        tts.speak(text, TextToSpeech.QUEUE_ADD, Bundle(), "cwq")
    }

    //播放铃声
    fun playEarcon() {
        if (TextUtils.isEmpty(earconName)) {
            earconName = "testVoice"
            tts.addEarcon(earconName, context.packageName, R.raw.di)
        }

        tts.playEarcon(earconName, TextToSpeech.QUEUE_ADD, Bundle(), "cwq")
    }
}