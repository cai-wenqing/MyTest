package com.aiyakeji.mytest.utils

import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageProxy
import com.aiyakeji.mytest.widgets.FaceGraphic
import com.aiyakeji.mytest.widgets.GraphicOverlay
import com.google.android.gms.tasks.TaskExecutors
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetector
import com.google.mlkit.vision.face.FaceDetectorOptions


/**
 * @author CWQ
 * @date 2023/10/15
 */
class FaceDetectProcess {

    private val faceDetector: FaceDetector
    private val executor: ScopeExecutor = ScopeExecutor(TaskExecutors.MAIN_THREAD)
    private var isShutdown = false

    init {
        val option = FaceDetectorOptions.Builder()
            .setClassificationMode(FaceDetectorOptions.CLASSIFICATION_MODE_NONE)
            .setLandmarkMode(FaceDetectorOptions.LANDMARK_MODE_NONE)
            .setContourMode(FaceDetectorOptions.CONTOUR_MODE_ALL)
            .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_FAST)
            .setMinFaceSize(0.3f)
            .build()
        faceDetector = FaceDetection.getClient(option)
    }

    @ExperimentalGetImage
    fun processImageProxy(imageProxy: ImageProxy, graphicOverlay: GraphicOverlay) {
        if (isShutdown) {
            imageProxy.close()
            return
        }

        val imageInput =
            InputImage.fromMediaImage(imageProxy.image!!, imageProxy.imageInfo.rotationDegrees)
        faceDetector.process(imageInput)
            .addOnSuccessListener(executor) { faces ->
                graphicOverlay.clear()
                faces.forEach { face ->
                    graphicOverlay.add(FaceGraphic(graphicOverlay, face))
                }
                graphicOverlay.postInvalidate()
            }
            .addOnFailureListener(executor) {
                graphicOverlay.clear()
                graphicOverlay.postInvalidate()
            }
            .addOnCompleteListener {
                imageProxy.close()
            }
    }


    fun stop() {
        executor.shutdownn()
        isShutdown = true
        faceDetector.close()
    }
}