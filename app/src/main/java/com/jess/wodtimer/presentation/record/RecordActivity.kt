package com.jess.wodtimer.presentation.record

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import com.jess.wodtimer.R
import com.jess.wodtimer.common.base.BaseActivity
import com.jess.wodtimer.common.manager.PermissionManager
import com.jess.wodtimer.common.util.DeviceUtils
import com.jess.wodtimer.databinding.RecordActivityBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.record_activity.*
import timber.log.Timber
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

/**
 * @author jess
 * @since 2020.06.12
 */
@AndroidEntryPoint
class RecordActivity : BaseActivity<RecordActivityBinding, RecordViewModel>(),
    View.OnClickListener {

    override val layoutRes get() = R.layout.record_activity
    override val viewModelClass get() = RecordViewModel::class

    private val cameraExecutor: ExecutorService by lazy {
        Executors.newSingleThreadExecutor()
    }

    override fun initLayout() {
        arrayOf(cl_record, iv_gallery, iv_setting).forEach {
            it.setOnClickListener(this)
        }
    }

    override fun onCreated(savedInstanceState: Bundle?) {
        checkPermission()
    }

    override fun onResume() {
        super.onResume()
        DeviceUtils.setImmersiveMode(this)
    }

    override fun onDestroy() {
        cameraExecutor.shutdown()
        super.onDestroy()
    }

    /**
     * 퍼미션 체크
     */
    private fun checkPermission(listener: (() -> Unit)? = null) {
        // 권한 요청
        PermissionManager.request(
            this,
            Manifest.permission.CAMERA,
            getString(R.string.permission_should_allow_camera)
        ) {
            startCamera()
            listener?.invoke()
        }
    }

    /**
     * 카메라
     */
    private fun startCamera() {

        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        if (cameraProviderFuture.isDone) {
            return
        }

        cameraProviderFuture.addListener(Runnable {
            // Used to bind the lifecycle of cameras to the lifecycle owner
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            // Preview
            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(camera.createSurfaceProvider())
                }

            // Select back camera as a default
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                // Unbind use cases before rebinding
                cameraProvider.unbindAll()

                // Bind use cases to camera
                cameraProvider.bindToLifecycle(
                    this, cameraSelector, preview
                )

            } catch (e: Exception) {
                Timber.e(e)
            }

        }, ContextCompat.getMainExecutor(this))
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.cl_record -> {
                // 녹화
                checkPermission {
                    vm.onRecord()
                }
            }

            R.id.iv_gallery -> {
                // 갤러리
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("content://media/internal/images/media")
                    )
                )
            }

            R.id.iv_setting -> {
                // 설정

            }

        }
    }
}
