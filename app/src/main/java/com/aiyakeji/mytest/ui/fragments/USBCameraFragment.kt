package com.aiyakeji.mytest.ui.fragments

import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aiyakeji.mytest.databinding.FragmentUsbCameraBinding
import com.jiangdg.ausbc.MultiCameraClient
import com.jiangdg.ausbc.base.CameraFragment
import com.jiangdg.ausbc.callback.ICameraStateCallBack
import com.jiangdg.ausbc.callback.ICaptureCallBack
import com.jiangdg.ausbc.camera.bean.CameraRequest
import com.jiangdg.ausbc.render.env.RotateType
import com.jiangdg.ausbc.utils.ToastUtils
import com.jiangdg.ausbc.widget.AspectRatioTextureView
import com.jiangdg.ausbc.widget.IAspectRatio
import java.io.File
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.Timer
import java.util.TimerTask

/**
 * @Author:CWQ
 * @DATE:2023/5/29
 * @DESC:
 */
class USBCameraFragment : CameraFragment() {
    private lateinit var binding: FragmentUsbCameraBinding
    private var isCapturingVideoOrAudio: Boolean = false

    private var mRecTimer: Timer? = null
    private var mRecSeconds = 0
    private var mRecMinute = 0
    private var mRecHours = 0


    private val mMainHandler: Handler by lazy {
        Handler(Looper.getMainLooper()) {
            when (it.what) {
                WHAT_START_TIMER -> {
                    binding.tvTimer.text = calculateTime(mRecSeconds, mRecMinute)
                }

                WHAT_STOP_TIMER -> {
                    binding.tvTimer.text = calculateTime(0, 0)
                }
            }
            true
        }
    }


    override fun getRootView(inflater: LayoutInflater, container: ViewGroup?): View? {
        binding = FragmentUsbCameraBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun getCameraView(): IAspectRatio? {
        return AspectRatioTextureView(requireContext())
    }

    override fun getCameraViewContainer(): ViewGroup? {
        return binding.flContainer
    }


    override fun onCameraState(
        self: MultiCameraClient.ICamera,
        code: ICameraStateCallBack.State,
        msg: String?
    ) {
        Log.d(TAG, "onCameraState: code:$code,msg:$msg")
        when (code) {
            ICameraStateCallBack.State.OPENED -> {

            }

            ICameraStateCallBack.State.CLOSED -> {

            }

            ICameraStateCallBack.State.ERROR -> {

            }
        }
    }


    override fun getCameraRequest(): CameraRequest {
        return CameraRequest.Builder()
            .setPreviewWidth(1280)
            .setPreviewHeight(720)
            .setRenderMode(CameraRequest.RenderMode.OPENGL)
            .setDefaultRotateType(RotateType.ANGLE_0)
            .setAudioSource(CameraRequest.AudioSource.NONE)
            .setAspectRatioShow(true)
            .setCaptureRawImage(false)
            .setRawPreviewData(false)
            .create()
    }

    override fun initView() {
        super.initView()

        binding.btnPhoto.setOnClickListener {
            captureImage()
        }
        binding.btnVideo.setOnClickListener {
            captureVideo()
        }
    }

    private fun captureImage() {
        val picName = SimpleDateFormat(FILENAME_FORMAT, Locale.US)
            .format(System.currentTimeMillis()) + ".jpg"
        val photoFile = File(requireContext().getExternalFilesDir("pictures"), picName)
        captureImage(object : ICaptureCallBack {
            override fun onBegin() {
                Log.d(TAG, "captureImage onBegin")
            }

            override fun onComplete(path: String?) {
                Log.d(TAG, "captureImage onComplete:$path")
            }

            override fun onError(error: String?) {
                Log.d(TAG, "captureImage onError:$error")
            }

        }, photoFile.path)
    }


    private fun captureVideo() {
        if (isCapturingVideoOrAudio) {
            captureVideoStop()
            return
        }
        val fileName = SimpleDateFormat(FILENAME_FORMAT, Locale.US)
            .format(System.currentTimeMillis())
        val videoFile = File(requireContext().getExternalFilesDir("videos"), fileName)
        captureVideoStart(object : ICaptureCallBack {
            override fun onBegin() {
                Log.d(TAG, "captureVideo onBegin")
                isCapturingVideoOrAudio = true
                startMediaTimer()
            }

            override fun onError(error: String?) {
                Log.d(TAG, "captureVideo onError:$error")
                ToastUtils.show(error ?: "未知异常")
                isCapturingVideoOrAudio = false
                stopMediaTimer()
            }

            override fun onComplete(path: String?) {
                Log.d(TAG, "captureVideo onComplete:$path")
                ToastUtils.show(path ?: "")
                isCapturingVideoOrAudio = false
                stopMediaTimer()
            }

        }, videoFile.path)
    }


    private fun startMediaTimer() {
        val pushTask: TimerTask = object : TimerTask() {
            override fun run() {
                //秒
                mRecSeconds++
                //分
                if (mRecSeconds >= 60) {
                    mRecSeconds = 0
                    mRecMinute++
                }
                //时
                if (mRecMinute >= 60) {
                    mRecMinute = 0
                    mRecHours++
                    if (mRecHours >= 24) {
                        mRecHours = 0
                        mRecMinute = 0
                        mRecSeconds = 0
                    }
                }
                mMainHandler.sendEmptyMessage(WHAT_START_TIMER)
            }
        }
        if (mRecTimer != null) {
            stopMediaTimer()
        }
        mRecTimer = Timer()
        //执行schedule后1s后运行run，之后每隔1s运行run
        mRecTimer?.schedule(pushTask, 1000, 1000)
    }

    private fun stopMediaTimer() {
        if (mRecTimer != null) {
            mRecTimer?.cancel()
            mRecTimer = null
        }
        mRecHours = 0
        mRecMinute = 0
        mRecSeconds = 0
        mMainHandler.sendEmptyMessage(WHAT_STOP_TIMER)
    }

    private fun calculateTime(seconds: Int, minute: Int, hour: Int? = null): String {
        val mBuilder = java.lang.StringBuilder()
        //时
        if (hour != null) {
            if (hour < 10) {
                mBuilder.append("0")
                mBuilder.append(hour)
            } else {
                mBuilder.append(hour)
            }
            mBuilder.append(":")
        }
        // 分
        if (minute < 10) {
            mBuilder.append("0")
            mBuilder.append(minute)
        } else {
            mBuilder.append(minute)
        }
        //秒
        mBuilder.append(":")
        if (seconds < 10) {
            mBuilder.append("0")
            mBuilder.append(seconds)
        } else {
            mBuilder.append(seconds)
        }
        return mBuilder.toString()
    }


    companion object {
        private const val TAG = "USBCameraFragment"
        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
        private const val WHAT_START_TIMER = 0x00
        private const val WHAT_STOP_TIMER = 0x01
    }
}