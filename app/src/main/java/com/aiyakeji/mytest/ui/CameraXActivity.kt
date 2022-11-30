package com.aiyakeji.mytest.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.*
import androidx.camera.core.VideoCapture.OutputFileOptions
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.aiyakeji.mytest.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.Executors

/**
 * @author CWQ
 * @date 2022/11/30
 */
class CameraXActivity : AppCompatActivity() {
    private val TAG = "CameraX"
    private lateinit var preview: PreviewView

    private lateinit var camera: Camera
    private var imageCapture: ImageCapture? = null
    private var videoCapture: VideoCapture? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camerax)

        initView()
        initCamera()
    }

    @SuppressLint("RestrictedApi")
    private fun initView() {
        preview = findViewById<PreviewView>(R.id.preview)
        findViewById<Button>(R.id.btn_photo).setOnClickListener {
            takePhoto()
        }
        findViewById<Button>(R.id.btn_video).setOnClickListener {
            takeVideo()
            countDown(
                end = {
                    Log.d(TAG, "录像结束---")
                    videoCapture?.stopRecording()
                },
                next = {

                }
            )
        }
    }


    @SuppressLint("RestrictedApi")
    private fun initCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        cameraProviderFuture.addListener({
            val cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA
            val preview = Preview.Builder().build()
                .also { it.setSurfaceProvider(preview.surfaceProvider) }
            imageCapture = ImageCapture.Builder()
                .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
                .build()
            videoCapture = VideoCapture
                .Builder()
                .setTargetAspectRatio(AspectRatio.RATIO_4_3) //设置高宽比
//                .setVideoFrameRate(25) //每秒的帧数
//                .setBitRate(3 * 1024 * 1024) //比特率
                .build()
            val cameraProvider = cameraProviderFuture.get()
            cameraProvider.unbindAll()
            camera = cameraProvider.bindToLifecycle(
                this,
                cameraSelector,
                preview,
                imageCapture,
                videoCapture
            )
        }, ContextCompat.getMainExecutor(this))
    }


    private fun takePhoto() {
        Log.e(TAG, "takePhoto: 拍照")
        imageCapture!!.takePicture(ContextCompat.getMainExecutor(this),
            object : ImageCapture.OnImageCapturedCallback() {
                override fun onCaptureSuccess(image: ImageProxy) {
                    super.onCaptureSuccess(image)

                    val buffer = image.planes[0].buffer
                    val bytes = ByteArray(buffer.capacity())
                    buffer.get(bytes)
                    val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size, null)

                    val imgName = "IMG_" + SimpleDateFormat(
                        "yyyyMMdd_HHmmss",
                        Locale.CHINA
                    ).format(System.currentTimeMillis()) + ".jpg"
                    val filePath = getOutputDirectory().path + "/${imgName}"
                    Log.d(TAG, "onCaptureSuccess: filePath:$filePath")

                    val file = bitmap2File(bitmap, filePath)
                    //上传图像注册
                }

                override fun onError(exception: ImageCaptureException) {
                    super.onError(exception)
                }
            })
    }


    /**
     * 开始录像
     */
    @SuppressLint("RestrictedApi", "ClickableViewAccessibility")
    private fun takeVideo() {
        val file = File(
            getOutputDirectory(),
            SimpleDateFormat(
                "yyyyMMdd_HHmmss",
                Locale.CHINA
            ).format(System.currentTimeMillis()) + ".mp4"
        )
        Log.d(TAG, "videoPath:" + file.path)
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.RECORD_AUDIO
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        val option = OutputFileOptions
            .Builder(file)
            .build()
        videoCapture?.startRecording(
            option,
            Executors.newSingleThreadExecutor(),
            object : VideoCapture.OnVideoSavedCallback {
                override fun onVideoSaved(outputFileResults: VideoCapture.OutputFileResults) {
                    //保存视频成功回调，会在停止录制时被调用
                    Log.d(TAG, "录像成功 $file.absolutePath")
                }

                override fun onError(videoCaptureError: Int, message: String, cause: Throwable?) {
                    //保存失败的回调，可能在开始或结束录制时被调用
                    Log.e("", "onError: $message")
                }
            })
    }


    private fun bitmap2File(bitmap: Bitmap, filePath: String): File {
        val file = File(filePath)
        if (!file.exists()) {
            file.createNewFile()
        }
        try {
            val bos = BufferedOutputStream(FileOutputStream(file))
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos)
            bos.flush()
            bos.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return file
    }


    private fun getOutputDirectory(): File {
        val mediaDir = externalMediaDirs.firstOrNull()?.let {
            File(it, resources.getString(R.string.app_name)).apply { mkdirs() }
        }
        return if (mediaDir != null && mediaDir.exists()) mediaDir else filesDir
    }


    private fun countDown(
        time: Int = 10,
        end: () -> Unit,
        next: (time: Int) -> Unit
    ) {
        lifecycleScope.launch {
            flow {
                (time downTo 0).forEach {
                    delay(1000)
                    emit(it)
                }
            }.onCompletion {
                end.invoke()
            }.catch {
                Log.d("TAG_HQL", "countDown: 倒计时出错")
            }.collect {
                Log.d("TAG_HQL", "countDown: 倒计时 $it")
                next(it)
            }
        }
    }
}