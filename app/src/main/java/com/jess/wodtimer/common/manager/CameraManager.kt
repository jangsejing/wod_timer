//package com.jess.wodtimer.common.manager
//
//import androidx.activity.ComponentActivity
//import androidx.camera.core.CameraSelector
//import androidx.camera.core.Preview
//import androidx.camera.lifecycle.ProcessCameraProvider
//import androidx.camera.view.PreviewView
//import androidx.core.content.ContextCompat
//import com.google.common.util.concurrent.ListenableFuture
//import com.gun0912.tedpermission.TedPermission
//import timber.log.Timber
//import java.util.concurrent.ExecutorService
//import java.util.concurrent.Executors
//import java.util.jar.Manifest
//
///**
// * 카메라
// *
// * @author jess
// * @since 2020.08.11
// *
// * @property activity
// * @property preview
// */
//class CameraManager(
//    private val activity: ComponentActivity,
//    private val preview: PreviewView
//) {
//
//    private val cameraProviderFuture: ListenableFuture<ProcessCameraProvider> by lazy {
//        ProcessCameraProvider.getInstance(activity)
//    }
//
//    private val cameraExecutor = Executors.newSingleThreadExecutor()
//
//    val isDone: Boolean get() = cameraProviderFuture.isDone ?: false
////    val isCancelled: Boolean get() = cameraProviderFuture.isCancelled ?: false
//
//    fun onResume() {
//        Timber.d("onResume()")
//        init()
//    }
//
//    fun onPause() {
//        Timber.d("onPause()")
//    }
//
//    fun onDestroy() {
//        cameraExecutor.shutdown()
//    }
//
//    fun init() {
//
//        if (!PermissionManager.isGranted(activity, android.Manifest.permission.CAMERA)) {
//            return
//        }
//
//        cameraProviderFuture.run {
//
//            addListener(Runnable {
//                // Used to bind the lifecycle of cameras to the lifecycle owner
//                val cameraProvider: ProcessCameraProvider = this.get()
//
//                // Preview
//                val preview = Preview.Builder()
//                    .build()
//                    .also { view ->
//                        view.setSurfaceProvider(preview.createSurfaceProvider())
//                    }
//
//                // Select back camera as a default
//                val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
//
//                try {
//                    // Unbind use cases before rebinding
//                    cameraProvider.unbindAll()
//
//                    // Bind use cases to camera
//                    cameraProvider.bindToLifecycle(
//                        activity, cameraSelector, preview
//                    )
//
//                } catch (e: Exception) {
//                    Timber.e(e)
//                }
//
//            }, ContextCompat.getMainExecutor(activity))
//        }
//    }
//
//}