package com.jess.wodtimer.common.constant

object RecordConst {

    const val MAX_RECORD_TIME = 3600 // 최대 녹화 시간
    const val DEFAULT_COUNTDOWN = 10 // 기본 카운트 다운
    const val MAX_COUNTDOWN = 60 // 최소 카운트 다운
    const val MIN_COUNTDOWN = 3 // 최소 카운트 다운

    const val PREF_RECORD_TITLE = "PREF_RECORD_TITLE" // 제목
    const val PREF_RECORD_COUNTDOWN = "PREF_RECORD_COUNTDOWN" // 카운트 다운
    const val PREF_RECORD_IS_SOUND = "PREF_RECORD_IS_SOUND" // 소리 녹음
    const val PREF_RECORD_IS_DATE = "PREF_RECORD_출IS_DATE" // 날짜 노
    const val PREF_RECORD_RATIO = "PREF_RECORD_RATIO" // 비율

    const val BEEP_COUNTDOWN = "BEEP_COUNTDOWN" // 비프음
    const val BEEP_PLAY = "BEEP_PLAY" // 비프음

    enum class RATIO {
        GENERAL, INSTAGRAM
    }

}