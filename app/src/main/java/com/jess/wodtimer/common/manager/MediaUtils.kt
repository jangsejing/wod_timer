package com.jess.wodtimer.common.manager

import android.content.Context
import android.os.Environment
import com.jess.wodtimer.R
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

/**
 * 미디어 관련
 *
 * @author jess
 * @since 2020.08.13
 */
object MediaUtils {

    const val MP4 = "mp4"

    /**
     * File 생성
     * Android 11 대응 필요
     */
    fun getFile(context: Context?, extension: String?): File {
        if (context == null || extension == null) {
            return File("")
        }

        val dateFormat = SimpleDateFormat("yyyyMMdd_HH_mm_ss", Locale.getDefault())
        val currentTimeStamp = dateFormat.format(Date())

        val path =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                .toString() + File.separator + context.getString(R.string.app_name)
        File(path).mkdirs()

        return File(path + File.separator + currentTimeStamp + "." + extension)
    }

}