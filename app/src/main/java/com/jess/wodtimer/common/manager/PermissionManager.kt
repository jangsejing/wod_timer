package com.jess.wodtimer.common.manager

import android.Manifest
import android.content.Context
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import com.jess.wodtimer.R


/**
 * 퍼미션 매니저
 *
 * @author jess
 * @since 2020.08.06
 */
object PermissionManager {

    /**
     * Permission이 허용되어 있는지 여부
     */
    fun isGranted(context: Context, vararg permissions: String): Boolean {
        return TedPermission.isGranted(context, *permissions)
    }

    /**
     * 권한 요청
     */
    fun request(context: Context, vararg permissions: String, listener: (() -> Unit)? = null) {
        TedPermission.with(context)
            .setPermissionListener(object : PermissionListener {
                override fun onPermissionGranted() {
                    listener?.invoke()
                }

                override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {

                }
            })
            .setDeniedTitle(R.string.permission_denied_title)
            .setDeniedMessage(R.string.permission_denied_message)
            .setGotoSettingButtonText(R.string.setting)
            .setPermissions(*permissions)
            .check();
    }

}

