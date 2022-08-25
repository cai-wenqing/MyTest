package com.aiyakeji.mytest.utils

import android.media.AudioAttributes
import android.media.AudioFormat
import android.media.AudioManager
import android.media.AudioTrack
import android.util.Log

/**
 * @Author:CWQ
 * @DATE:2022/7/18
 * @DESC:
 */
object ByteUtils {
    private const val TAG = "ByteUtils"

    private var mAudioTrack: AudioTrack? = null

    /**
     * 播放字节流音频
     * @param buffer ByteArray?
     */
    fun playBuffer(buffer: ByteArray?) {
        Log.d(TAG, "playBuffer: buffer:${buffer?.size}")
        if (buffer == null) {
            return
        }
        val bufferSize = AudioTrack.getMinBufferSize(
            16000,
            AudioFormat.CHANNEL_OUT_MONO,
            AudioFormat.ENCODING_PCM_16BIT
        )

        mAudioTrack = AudioTrack(
            AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_MEDIA).build(),
            AudioFormat.Builder()
                .setSampleRate(16000)
                .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
                .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
                .build(),
            bufferSize,
            AudioTrack.MODE_STREAM,
            AudioManager.AUDIO_SESSION_ID_GENERATE
        )
        mAudioTrack?.play()
        mAudioTrack?.write(buffer, 0, buffer.size)
    }


    fun stopPlayBuffer() {
        if (mAudioTrack != null && mAudioTrack!!.state == AudioTrack.STATE_INITIALIZED) {
            mAudioTrack?.stop()
        }
        mAudioTrack = null
    }
}