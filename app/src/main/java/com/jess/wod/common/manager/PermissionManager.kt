package com.jess.wod.common.manager

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import com.jess.wod.R


/**
 * 퍼미션 매니저
 *
 * @author jess
 * @since 2020.08.06
 */
object PermissionManager {

    /**
     * 권한 요청
     *
     * @param activity
     * @param permission
     * @param deniedMessage
     * @param onGranted
     */
    fun request(
        activity: ComponentActivity?,
        permission: String?,
        deniedMessage: String? = null,
        onGranted: () -> Unit
    ) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            onGranted.invoke()
            return
        }

        if (activity == null || permission.isNullOrEmpty()) {
            return
        }

        activity.registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                onGranted.invoke()
            } else {
                onDenied(activity, deniedMessage)
            }
        }.launch(permission)
    }

    /**
     * 권한 거부
     *
     * @param activity
     * @param deniedMessage
     */
    private fun onDenied(activity: ComponentActivity?, deniedMessage: String?) {
        activity?.let {
            AlertDialog.Builder(activity).run {
                setMessage(
                    if (deniedMessage.isNullOrEmpty()) {
                        activity.getString(R.string.permission_should_allow)
                    } else {
                        deniedMessage
                    }
                )
                setPositiveButton(android.R.string.ok) { dialog, which ->
                    moveSystemSetting(it)
                    activity.finish()
                }
                show()
            }
        }
    }

    /**
     * 시스템 설정
     *
     * @param activity
     */
    private fun moveSystemSetting(activity: ComponentActivity?) {
        activity?.let {
            val intent =
                Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    .setData(Uri.parse("package:" + activity.packageName))
            activity.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->

            }.launch(intent)
        }
    }

}

