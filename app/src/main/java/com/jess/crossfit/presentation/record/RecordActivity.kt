package com.jess.crossfit.presentation.record

import android.Manifest
import android.os.Bundle
import androidx.lifecycle.Observer
import com.jess.crossfit.R
import com.jess.crossfit.common.base.BaseActivity
import com.jess.crossfit.common.manager.PermissionManager
import com.jess.crossfit.databinding.RecordActivityBinding
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
class RecordActivity : BaseActivity<RecordActivityBinding, RecordViewModel>() {

    override val layoutRes get() = R.layout.record_activity
    override val viewModelClass get() = RecordViewModel::class

    private val cameraExecutor: ExecutorService by lazy {
        Executors.newSingleThreadExecutor()
    }

    override fun initLayout() {

    }

    override fun onCreated(savedInstanceState: Bundle?) {
        vm.test().observe(this, Observer {
            Timber.d(it)
        })
    }

    override fun onResume() {
        super.onResume()
        PermissionManager.request(
            this, Manifest.permission.CAMERA
        ) {

        }
    }

    override fun onDestroy() {
        cameraExecutor.shutdown()
        super.onDestroy()
    }
}
