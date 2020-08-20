package com.jess.wodtimer.common.constant

object RecordConst {

    const val MAX_RECORD_TIME = 60 // 최대 녹화 시간
    const val MIN_RECORD_TIME = 10 // 최대 녹화 시간
    const val DEFAULT_COUNTDOWN = 10 // 기본 카운트 다운
    const val MAX_COUNTDOWN = 60 // 최소 카운트 다운
    const val MIN_COUNTDOWN = 3 // 최소 카운트 다운

    const val PREF_RECORD_TITLE = "PREF_RECORD_TITLE" // 제목
    const val PREF_RECORD_COUNTDOWN = "PREF_RECORD_COUNTDOWN" // 카운트 다운
    const val PREF_RECORD_IS_SOUND = "PREF_RECORD_IS_SOUND" // 소리 녹음
    const val PREF_RECORD_IS_DATE = "PREF_RECORD_IS_DATE" // 날짜 노출 여부
    const val PREF_RECORD_FACING = "PREF_RECORD_FACING" // 카메라 방향
    const val PREF_RECORD_RATIO = "PREF_RECORD_RATIO" // 비율
    const val PREF_RECORD_TIMER_TYPE = "PREF_RECORD_TIMER_TYPE" // 타이머 타입
    const val PREF_RECORD_TIMER_MINUTE = "PREF_RECORD_TIMER_MINUTE" // 타이머 타입 시간

    enum class Ratio {
        GENERAL, INSTAGRAM
    }

    enum class TimerType {
        FOR_TIME, TIME_CAP, AMRAP, EMOM
    }

}