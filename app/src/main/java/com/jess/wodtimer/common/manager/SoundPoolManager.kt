package com.jess.wodtimer.common.manager

import android.content.Context
import android.media.AudioAttributes
import android.media.SoundPool

/**
 * 사운드 풀 매니저
 *
 * @author jess
 * @since 2020.08.13
 */
class SoundPoolManager(private val context: Context) {

    companion object {
        const val BEEP_SHORT = "BEEP_SHORT" // 비프음
        const val BEEP_LONG = "BEEP_LONG" // 비프음
    }

    private val soundPool by lazy {
        val attributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_NOTIFICATION)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()
        SoundPool.Builder()
            .setAudioAttributes(attributes)
            .setMaxStreams(8)
            .build()
    }

    private val soundMap = HashMap<String, Int>()

    fun add(key: String, raw: Int) {
        val soundId = soundPool.load(context, raw, 1)
        soundMap[key] = soundId
    }

    fun remove() {
        soundMap.clear()
    }

    fun play(key: String) {
        soundMap[key]?.let { soundId ->
            soundPool.play(soundId, 1f, 1f, 0, 0, 1f)
        }
    }
}