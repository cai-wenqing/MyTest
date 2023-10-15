package com.aiyakeji.mytest.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.util.Size
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.Camera
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.concurrent.futures.await
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.aiyakeji.mytest.databinding.ActivityFaceDetectBinding
import com.aiyakeji.mytest.utils.FaceDetectProcess
import com.aiyakeji.mytest.widgets.GraphicOverlay
import com.google.mlkit.common.MlKitException
import kotlinx.coroutines.launch
import java.lang.Exception

/**
 * @author CWQ
 * @date 2023/10/15
 * 人脸识别
 */
class FaceDetectActivity : AppCompatActivity() {
    private val TAG = "FaceDetectActivity"
    private lateinit var binding: ActivityFaceDetectBinding

    private var cameraProvider: ProcessCameraProvider? = null
    private val lensFacing = CameraSelector.LENS_FACING_FRONT
    private lateinit var cameraSelector: CameraSelector
    private var camera: Camera? = null
    private var imageAnalysis: ImageAnalysis? = null
    private var previewUseCase: Preview? = null
    private var imageProcessor: FaceDetectProcess? = null
    private var needUpdateGraphicOverlayImageSourceInfo = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFaceDetectBinding.inflate(layoutInflater)
        setContentView(binding.root)

        cameraSelector = CameraSelector.Builder().requireLensFacing(lensFacing).build()
        lifecycleScope.launch {
            cameraProvider = ProcessCameraProvider.getInstance(this@FaceDetectActivity).await()
            bindAllCameraUseCause()
        }
    }

    override fun onResume() {
        super.onResume()
        bindAllCameraUseCause()
    }

    override fun onPause() {
        super.onPause()
        imageProcessor?.stop()
    }

    override fun onDestroy() {
        super.onDestroy()
        imageProcessor?.stop()
    }


    private fun bindAllCameraUseCause() {
        if (cameraProvider != null) {
            cameraProvider!!.unbindAll()
            bindPreviewCase()
            bindAnalysisCase()
        }
    }

    private fun bindPreviewCase() {
        if (cameraProvider == null) {
            return
        }
        if (previewUseCase != null) {
            cameraProvider?.unbind(previewUseCase)
        }

        val builder = Preview.Builder()
        previewUseCase = builder.build()
        previewUseCase!!.setSurfaceProvider(binding.previewView.surfaceProvider)
        camera = cameraProvider!!.bindToLifecycle(
            this,
            cameraSelector,
            previewUseCase
        )
    }

    @SuppressLint("UnsafeOptInUsageError")
    private fun bindAnalysisCase() {
        if (cameraProvider == null) {
            return
        }
        if (imageAnalysis != null) {
            cameraProvider?.unbind(imageAnalysis)
        }
        imageProcessor?.stop()
        imageProcessor = FaceDetectProcess()

        val builder = ImageAnalysis.Builder()
        imageAnalysis = builder.build()
        needUpdateGraphicOverlayImageSourceInfo = true
        imageAnalysis?.setAnalyzer(ContextCompat.getMainExecutor(this)) { imageProxy ->
            if (needUpdateGraphicOverlayImageSourceInfo) {
                val isImageFlipped = lensFacing == CameraSelector.LENS_FACING_FRONT
                val rotationDegrees = imageProxy.imageInfo.rotationDegrees
                if (rotationDegrees == 0 || rotationDegrees == 180) {
                    binding.graphicOverlay.setImageSourceInfo(
                        imageProxy.width,
                        imageProxy.height,
                        isImageFlipped
                    )
                } else {
                    binding.graphicOverlay.setImageSourceInfo(
                        imageProxy.height,
                        imageProxy.width,
                        isImageFlipped
                    )
                }
                needUpdateGraphicOverlayImageSourceInfo = false
            }
            try {
                imageProcessor?.processImageProxy(imageProxy, binding.graphicOverlay)
            } catch (e: MlKitException) {
                Log.e(TAG, "Failed to process image. Error: " + e.localizedMessage)
            }
        }
        cameraProvider?.bindToLifecycle(this, cameraSelector, imageAnalysis)
    }

}