package com.jess.wodtimer.common.util

import android.app.Activity
import android.content.Context
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.view.View
import android.view.inputmethod.InputMethodManager

/**
 * @since jess
 * @author 2020.08.12
 */
object DeviceUtils {

    /**
     * 네이게이션바 높이
     */
    fun getNavigationBarHeight(context: Context?): Int {
        return context?.let {
            val resourceIdBottom: Int =
                it.resources.getIdentifier("navigation_bar_height", "dimen", "android")
            return if (resourceIdBottom > 0) {
                it.resources.getDimensionPixelSize(resourceIdBottom)
            } else {
                0
            }
        } ?: run {
            0
        }
    }

    /**
     * 키보드 숨김
     */
    fun hideKeyboard(context: Context?, view: View?) {
        val inputManager =
            context?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        inputManager?.hideSoftInputFromWindow(
            view?.windowToken,
            InputMethodManager.HIDE_IMPLICIT_ONLY
        )
    }

    /**
     * 키보드 보임
     */
    fun showKeyboard(context: Context?, view: View?) {
        val inputManager =
            context?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        view?.requestFocus()
        inputManager?.showSoftInput(
            view,
            InputMethodManager.SHOW_IMPLICIT
        )
    }

    /**
     * 화면 세로 여부
     */
    fun isOrientationPortrait(context: Context?): Boolean {
        return context?.let {
            return it.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT
        } ?: run {
            false
        }
    }

    /**
     * 화면 가로 여부
     */
    fun isOrientationLandscape(context: Context?): Boolean {
        return context?.let {
            return it.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
        } ?: run {
            false
        }
    }

    /**
     * 화면 가로/세로/자유 설정
     */
    fun setOrientation(
        activity: Activity?,
        orientation: Int = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
    ) {
        activity?.requestedOrientation = orientation
    }

    /**
     * 스크린 너비
     *
     * @param context
     * @param percent 비율 (스크린의 비율 만큼 리턴)
     * @return
     */
    fun getDisplayWidth(
        context: Context?,
        percent: Int = 0
    ): Int {
        context?.let {
            val metrics = context.resources.displayMetrics
            return if (percent > 0) {
                metrics.widthPixels * percent / 100
            }
            else {
                metrics.widthPixels
            }
        }
        return 0
    }


    /**
     * 스크린 높이
     *
     * @param context
     * @param percent 비율 (스크린의 비율 만큼 리턴)
     * @return
     */
    fun getDisplayHeight(
        context: Context?,
        percent: Int = 0
    ): Int {
        context?.let {
            val metrics = context.resources.displayMetrics
            return if (percent > 0) {
                metrics.heightPixels * percent / 100
            }
            else {
                metrics.heightPixels
            }
        }
        return 0
    }
}