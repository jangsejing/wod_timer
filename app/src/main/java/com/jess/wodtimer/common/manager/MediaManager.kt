package com.jess.wodtimer.common.manager

import android.content.Context
import android.os.Environment
import android.provider.MediaStore
import androidx.loader.content.CursorLoader
import com.jess.wodtimer.R
import com.jess.wodtimer.common.util.tryCatch
import com.jess.wodtimer.data.MediaData
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

/**
 * 미디어 관련
 *
 * @author jess
 * @since 2020.08.13
 */
object MediaManager {

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

    /**
     * 동영상 리턴
     */
    fun getVideoList(context: Context?): List<MediaData> {

        val list = arrayListOf<MediaData>()

        if (context == null) {
            return list
        }

        tryCatch {

            val contentUri = MediaStore.Files.getContentUri("external")
            val projection = arrayOf(
                MediaStore.Files.FileColumns._ID,
                MediaStore.Files.FileColumns.DATA,
                MediaStore.Files.FileColumns.DISPLAY_NAME,
                MediaStore.Files.FileColumns.MEDIA_TYPE,
                MediaStore.Files.FileColumns.MIME_TYPE,
                MediaStore.Video.Media.DURATION
            )

            // 동영상
            val selection = MediaStore.Files.FileColumns.MEDIA_TYPE +
                    "=" +
                    MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO

            val sort = MediaStore.Files.FileColumns.DATE_ADDED + " DESC LIMIT " + 0 + ", " + 10
            val cursorLoader =
                CursorLoader(
                    context,
                    contentUri,
                    projection,
                    selection,
                    null,
                    sort // 최신날짜순으로
                )

            val cursor = cursorLoader.loadInBackground()
            cursor?.let {
                if (cursor.moveToFirst()) {
                    do {
                        val mediaData = MediaData(
                            cursor.getLong(cursor.getColumnIndex(MediaStore.Files.FileColumns._ID)),
                            cursor.getString(cursor.getColumnIndex(MediaStore.Files.FileColumns.DATA)),
                            cursor.getString(cursor.getColumnIndex(MediaStore.Files.FileColumns.DISPLAY_NAME)),
                            cursor.getInt(cursor.getColumnIndex(MediaStore.Files.FileColumns.MEDIA_TYPE)),
                            cursor.getString(cursor.getColumnIndex(MediaStore.Files.FileColumns.MIME_TYPE)),
                            cursor.getLong(cursor.getColumnIndex(MediaStore.Video.Media.DURATION))
                        )
                        list.add(mediaData)
                    } while (cursor.moveToNext())
                }
                cursor.close()
            }
        }

        return list
    }

}