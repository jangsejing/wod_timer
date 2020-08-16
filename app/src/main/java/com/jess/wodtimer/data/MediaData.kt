package com.jess.wodtimer.data

import android.media.ThumbnailUtils
import android.provider.MediaStore
import java.io.File

data class MediaData(
    var id: Long,
    var path: String?,
    var displayName: String?,
    var mediaType: Int?,
    var mimeType: String?,
    var duration: Long
) {
    val uri = MediaStore.Files.getContentUri("external", id ?: 0)
    val file = File(path ?: "")
    val thumbnail =
        ThumbnailUtils.createVideoThumbnail(
            path ?: "",
            MediaStore.Images.Thumbnails.FULL_SCREEN_KIND
        )

}