package com.aiyakeji.mytest.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.media.AudioAttributes
import android.media.AudioFormat
import android.media.AudioPlaybackCaptureConfiguration
import android.media.AudioRecord
import android.media.projection.MediaProjection
import android.media.projection.MediaProjectionManager
import android.os.Build
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.concurrent.thread

/**
 * @Author:CWQ
 * @DATE:2022/8/24
 * @DESC:音频捕获
 */
object AudioCaptureControl {
    const val TAG = "AudioCaptureControl"

    private var resultLauncher: ActivityResultLauncher<Intent>? = null
    private var screenCaptureIntent: Intent? = null
    private var mediaProjection: MediaProjection? = null
    private var isRecording = false
    private var audioRecorder: AudioRecord? = null


    /**
     * 在onCreate中初始化
     * @param context ComponentActivity
     */
    @SuppressLint("MissingPermission")
    fun initAudioCapture(context: ComponentActivity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val mediaProjectionManager =
                context.getSystemService(MediaProjectionManager::class.java)
            screenCaptureIntent = mediaProjectionManager.createScreenCaptureIntent()

            resultLauncher =
                context.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                    if (it.resultCode == AppCompatActivity.RESULT_OK && it.data != null) {
                        mediaProjection = mediaProjectionManager.getMediaProjection(
                            it.resultCode,
                            it.data!!
                        )

                        if (mediaProjection != null) {
                            val builder =
                                AudioPlaybackCaptureConfiguration.Builder(mediaProjection!!).apply {
                                    addMatchingUsage(AudioAttributes.USAGE_MEDIA)
                                    addMatchingUsage(AudioAttributes.USAGE_ALARM)
                                    addMatchingUsage(AudioAttributes.USAGE_GAME)
                                }
                            val audioPlaybackCaptureConfiguration = builder.build()

                            audioRecorder = AudioRecord.Builder()
                                .setAudioFormat(
                                    AudioFormat.Builder()
                                        .setSampleRate(16000)
                                        .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
                                        .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
                                        .build()
                                )
                                .setBufferSizeInBytes(1024 * 2)
                                .setAudioPlaybackCaptureConfig(audioPlaybackCaptureConfiguration)
                                .build()
                            audioRecorder!!.startRecording()
                            isRecording = true

                            thread {
                                writeAudioDataToFile(context)
                            }
                        } else {
                            Log.e(TAG, "startAudioCapture: mediaProjection为null")
                        }
                    } else {
                        Log.e(
                            TAG,
                            "startAudioCapture 返回数据有空 resultCode:${it.resultCode},data:${it.data}"
                        )
                    }
                }
        }
    }


    private fun writeAudioDataToFile(context: Context) {
        val sampleDir = File(context.getExternalFilesDir(null), "/TestRecordingDasa1")
        if (!sampleDir.exists()) {
            sampleDir.mkdirs()
        }
        val fileName =
            "Record-" + SimpleDateFormat("dd-MM-yyyy-hh-mm-ss").format(Date()).toString() + ".pcm"
        val filePath: String = sampleDir.getAbsolutePath().toString() + "/" + fileName
        val sData = ShortArray(1024)
        var os: FileOutputStream? = null
        try {
            os = FileOutputStream(filePath)
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }
        while (isRecording) {
            // gets the voice output from microphone to byte format
            audioRecorder?.read(sData, 0, 1024)
            println("Short wirting to file$sData")
            try {
                // // writes the data to file from buffer
                // // stores the voice buffer
                val bData: ByteArray = FileUtils.short2byte(sData)
                os?.write(bData, 0, 1024 * 2)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        try {
            os?.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        Log.i(
            TAG,
            String.format(
                "Recording finished. File saved to '%s'\nadb pull %s .",
                filePath,
                filePath
            )
        )
    }

    fun isRecording() = isRecording

    /**
     * 启动音频捕获
     */
    fun startRecording() {
        resultLauncher?.launch(screenCaptureIntent)
    }

    /**
     * 停止音频捕获
     */
    fun stopRecording() {
        audioRecorder?.let {
            isRecording = false
            it.stop()
            it.release()
            audioRecorder = null
        }
        mediaProjection?.stop()
    }


    fun playAudioFile(context: Context){
        thread {
            val sampleDir = File(context.getExternalFilesDir(null), "/TestRecordingDasa1")
            val fileName = "Record-24-08-2022-05-33-47.pcm"
            val filePath: String = sampleDir.getAbsolutePath().toString() + "/" + fileName

            val shortArray:ByteArray = FileUtils.readBytesFromFile(filePath)
            ByteUtils.playBuffer(shortArray)
        }
    }
}