package com.jess.wodtimer.presentation.record

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK
import android.content.Intent.FLAG_ACTIVITY_NO_ANIMATION
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.Observer
import com.jess.wodtimer.R
import com.jess.wodtimer.common.base.BaseActivity
import com.jess.wodtimer.common.constant.RecordConst
import com.jess.wodtimer.common.extension.setMargin
import com.jess.wodtimer.common.manager.MediaManager
import com.jess.wodtimer.common.manager.PermissionManager
import com.jess.wodtimer.common.manager.SoundPoolManager
import com.jess.wodtimer.common.util.DeviceUtils
import com.jess.wodtimer.databinding.RecordActivityBinding
import com.jess.wodtimer.presentation.media.VideoListActivity
import com.jess.wodtimer.presentation.setting.SettingActivity
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

    private val soundManager by lazy {
        SoundPoolManager(this)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        restartActivity()
    }

    override fun initLayout() {

        setOrientationMargin()

        // camera
        camera.run {
            setLifecycleOwner(this@RecordActivity)
            videoMaxDuration = RecordConst.MAX_RECORD_TIME * 1000 // max 60 min
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
                    }
                }
            })
        }

        arrayOf(cl_record, cl_stop, iv_videos, iv_setting, v_watermark).forEach {
            it.setOnClickListener(this)
        }
    }

    override fun onCreated(savedInstanceState: Bundle?) {
        initObserve()
        initListener()
        checkPermissions()
        vm.setData()
    }

    private fun initObserve() {
        vm.isPlay.observe(this, Observer {
            Timber.d("$it")
            if (it && !camera.isTakingVideo) {
                // 비프음
                soundManager.play(RecordConst.BEEP_PLAY)

                // 녹화시작
                camera.takeVideoSnapshot(MediaManager.getFile(this, MediaManager.MP4))

                // 화면 고정
                DeviceUtils.setOrientation(
                    this,
                    if (DeviceUtils.isOrientationPortrait(this)) {
                        ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                    } else {
                        ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                    }
                )
            } else {
                // 녹화종료
                camera.stopVideo()

                // 카메라 회전
                DeviceUtils.setOrientation(this, ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
            }
        })

        vm.isSound.observe(this, Observer {
            camera.playSounds = it
        })

        vm.isCountdownBeep.observe(this, Observer {
            soundManager.play(RecordConst.BEEP_COUNTDOWN)
        })
    }

    private fun initListener() {

    }

    /**
     * 액티비티 제시작
     */
    private fun restartActivity() {
        val intent = Intent(this, RecordActivity::class.java).apply {
            flags = FLAG_ACTIVITY_NO_ANIMATION or FLAG_ACTIVITY_CLEAR_TASK
        }
        startActivity(intent)
        finish()
    }

    override fun onStart() {
        super.onStart()
        soundManager.run {
            add(RecordConst.BEEP_COUNTDOWN, R.raw.beep_coundown)
            add(RecordConst.BEEP_PLAY, R.raw.beep_play)
        }
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
            cl_bottom.setMargin(bottom = 0)
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
                val intent = Intent(this, SettingActivity::class.java)
                registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                    if (result.resultCode == Activity.RESULT_OK) {
                        vm.setData()
                    }
                }.launch(intent)
            }

            R.id.iv_videos -> {
                startActivity(Intent(this, VideoListActivity::class.java))
            }

            R.id.v_watermark -> {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://www.instagram.com/jjang_se_jin/?hl=ko")
                    )
                )
            }
        }
    }
}
