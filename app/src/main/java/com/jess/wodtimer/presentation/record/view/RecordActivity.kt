package com.jess.wodtimer.presentation.record.view

import android.Manifest
import android.content.Intent
import android.content.res.Configuration
import android.hardware.SensorManager
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Bundle
import android.view.OrientationEventListener
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import com.jess.wodtimer.R
import com.jess.wodtimer.common.base.BaseActivity
import com.jess.wodtimer.common.extension.setMargin
import com.jess.wodtimer.common.manager.MediaUtils
import com.jess.wodtimer.common.manager.PermissionManager
import com.jess.wodtimer.common.util.DeviceUtils
import com.jess.wodtimer.databinding.RecordActivityBinding
import com.jess.wodtimer.presentation.record.viewmodel.RecordViewModel
import com.otaliastudios.cameraview.CameraListener
import com.otaliastudios.cameraview.PictureResult
import com.otaliastudios.cameraview.VideoResult
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.record_activity.*
import timber.log.Timber


/**
 * @author jess
 * @since 2020.08.11
 */
@AndroidEntryPoint
class RecordActivity : BaseActivity<RecordActivityBinding, RecordViewModel>(),
    View.OnClickListener {

    override val layoutRes get() = R.layout.record_activity
    override val viewModelClass get() = RecordViewModel::class

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        setOrientationMargin()
    }

    override fun initLayout() {

        // camera
        camera.run {
            setLifecycleOwner(this@RecordActivity)
            videoMaxDuration = 60 * 10 * 1000 // max 60 min
            addCameraListener(object : CameraListener() {
                override fun onPictureTaken(result: PictureResult) {
                    Timber.d("onPictureTaken")
                }

                override fun onVideoTaken(result: VideoResult) {
                    super.onVideoTaken(result)
                    Timber.d("onVideoTaken")
                    MediaScannerConnection.scanFile(
                        this@RecordActivity,
                        arrayOf(result.file.toString()),
                        null
                    ) { filePath: String, uri: Uri ->
                        Timber.d("filePath : $filePath")
                        Timber.d("uri : $uri")
                        startActivity(
                            Intent(
                                Intent.ACTION_VIEW,
                                uri
                            )
                        )
                    }
                }
            })
        }

        arrayOf(cl_record, cl_stop, iv_setting).forEach {
            it.setOnClickListener(this)
        }
    }

    override fun onCreated(savedInstanceState: Bundle?) {
        initObserve()
        initListener()
        checkPermissions()
    }

    private fun initObserve() {
        vm.isPlay.observe(this, Observer {
            Timber.d("$it")
            if (it && !camera.isTakingVideo) {
                camera.takeVideoSnapshot(MediaUtils.getFile(this, MediaUtils.MP4))
            } else {
                camera.stopVideo()
            }
        })
    }

    private fun initListener() {
        val orientEventListener = object : OrientationEventListener(
            this,
            SensorManager.SENSOR_DELAY_NORMAL
        ) {
            override fun onOrientationChanged(arg0: Int) {
//
//                //arg0: 기울기 값
//                mTextOrient.setText(
//                    "Orientation: "
//                            + arg0.toString()
//                )
//
//                // 0˚ (portrait)
//                if (arg0 >= 315 || arg0 < 45) {
//                    mTextArrow.setRotation(0)
//                } else if (arg0 >= 45 && arg0 < 135) {
//                    mTextArrow.setRotation(270)
//                } else if (arg0 >= 135 && arg0 < 225) {
//                    mTextArrow.setRotation(180)
//                } else if (arg0 >= 225 && arg0 < 315) {
//                    mTextArrow.setRotation(90)
//                }
            }
        }

        if (orientEventListener.canDetectOrientation()) {
            orientEventListener.enable()
        }
    }

    override fun onResume() {
        super.onResume()
        setOrientationMargin()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    /**
     * 가로, 세로에 따른 마진 설정
     */
    private fun setOrientationMargin() {
        // 네비게이션 바 높이 만큼 마진 설정
        if (DeviceUtils.isOrientationPortrait(this)) {
            // 세로
            cl_bottom.setMargin(bottom = DeviceUtils.getNavigationBarHeight(this))
        } else {
            // 가로
            cl_bottom.setMargin(
                right = DeviceUtils.getNavigationBarHeight(this)
            )
        }
    }

    /**
     * 퍼미션 체크
     */
    private fun checkPermissions(listener: (() -> Unit)? = null) {
        if (PermissionManager.isGranted(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA
            )
        ) {
            listener?.invoke()
            return
        }

        PermissionManager.request(
            this,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
        ) {
            listener?.invoke()
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.cl_record -> {
                checkPermissions {
                    vm.onRecord()
                }
            }

            R.id.cl_stop -> {
                // 정지
                vm.onStop()
            }

            R.id.iv_setting -> {
                // 설정
                SettingBottomDialog(
                    this
                ).run {
                    show(vm.title.value) { title ->
                        vm.setTitle(title)
                    }
                }
            }
        }
    }
}
