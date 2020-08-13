package com.jess.wodtimer.common.manager

import androidx.activity.ComponentActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import com.google.common.util.concurrent.ListenableFuture
import timber.log.Timber
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

/**
 * 카메라
 *
 * @author jess
 * @since 2020.08.11
 *
 * @property activity
 * @property preview
 */
class CameraManager(
    private val activity: ComponentActivity,
    private val preview: PreviewView
) {

    private val cameraProviderFuture: ListenableFuture<ProcessCameraProvider> by lazy {
        ProcessCameraProvider.getInstance(activity)
    }

    private val cameraExecutor: ExecutorService by lazy {
        Executors.newSingleThreadExecutor()
    }

    val isDone: Boolean get() = cameraProviderFuture.isDone ?: false
    val isCancelled: Boolean get() = cameraProviderFuture.isCancelled ?: false

    fun onResume() {
        Timber.d("onResume()")
        Timber.d("isDone $isDone")
        Timber.d("isCancelled $isCancelled")
        init()
    }

    fun onPause() {
        Timber.d("onPause()")
        Timber.d("isDone $isDone")
        Timber.d("isCancelled $isCancelled")
    }

    fun onDestroy() {
        cameraExecutor.shutdown()
    }

    fun init(isForce: Boolean = false) {

        cameraProviderFuture.run {

            if (isDone && !isForce) {
                return
            }

            addListener(Runnable {
                // Used to bind the lifecycle of cameras to the lifecycle owner
                val cameraProvider: ProcessCameraProvider = this.get()

                // Preview
                val preview = Preview.Builder()
                    .build()
                    .also { view ->
                        view.setSurfaceProvider(preview.createSurfaceProvider())
                    }

                // Select back camera as a default
                val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

                try {
                    // Unbind use cases before rebinding
                    cameraProvider.unbindAll()

                    // Bind use cases to camera
                    cameraProvider.bindToLifecycle(
                        activity, cameraSelector, preview
                    )

                } catch (e: Exception) {
                    Timber.e(e)
                }

            }, ContextCompat.getMainExecutor(activity))
        }
    }

}